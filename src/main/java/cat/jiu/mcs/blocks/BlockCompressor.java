package cat.jiu.mcs.blocks;

import java.util.List;
import java.util.Random;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.GuiHandler;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.ItemStackHandler;

public class BlockCompressor extends BaseBlock.Normal {
	public BlockCompressor() {
		super(MCS.MODID, "compressor", Material.ANVIL, SoundType.METAL, CreativeTabs.TRANSPORTATION, 10F);
		this.setBlockModelResourceLocation(MCS.MODID + "/block", this.name);
		this.setTickRandomly(true);
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(GuiScreen.isShiftKeyDown()) {
			tooltip.add("NBTTags: ");
			tooltip.add(JiuUtils.nbt.getItemNBT(stack).toString());
		}
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileEntityCompressor) {
			if(!((TileEntityCompressor) tile).canBreak()) {
				return Float.MAX_VALUE;
			}
		}
		return super.getExplosionResistance(world, pos, exploder, explosion);
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile instanceof TileEntityCompressor) {
			if(!((TileEntityCompressor) tile).canBreak()) {
				return Float.MAX_VALUE;
			}
		}
		return super.getBlockHardness(blockState, world, pos);
	}

	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		double x = (double) pos.getX() + 0.5D;
		double y = (double) pos.getY() + 1.2D;
		double z = (double) pos.getZ() + 0.5D;
		
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.0D, 0.0D);
		world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0.0D, 0.02D, 0.0D);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			if(this.useWrenchBreak(world, pos, state, player, hand, facing, hitX, hitY, hitZ)) return true;
		}else {
			int x = pos.getX(), y = pos.getY(), z = pos.getZ();
			player.openGui(MCS.MODID, GuiHandler.COMPRESSOR, world, x, y, z);
		}
		return true;
	}
	
	private boolean useWrenchBreak(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Item item = player.getHeldItem(hand).getItem();
		if(Loader.isModLoaded("thermalfoundation")) {
			if(item instanceof cofh.api.item.IToolHammer) {
				return this.wrenchBreak(world, pos, state);
			}
		}
		return false;
	}
	
	private boolean wrenchBreak(World world, BlockPos pos, IBlockState state) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null) {
			ItemStack stack = JiuUtils.item.getStackFromBlockState(state);
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setTag("BlockEntityTag", tile.writeToNBT(new NBTTagCompound()));
			world.setBlockToAir(pos);
			JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.nbt.setNBT(stack, nbt));
			return true;
		}
		return false;
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if(world.isRemote) return;
		TileEntity posTe = world.getTileEntity(pos);
		if(posTe instanceof TileEntityCompressor) {
			NonNullList<ItemStack> drops = NonNullList.create();
			TileEntityCompressor te = (TileEntityCompressor) posTe;
			
			drops.add(te.getEnergySlotItems().getStackInSlot(0));
			
			ItemStackHandler compressedSlot = te.getCompressedSlotItems();
			for(int i = 0; i < compressedSlot.getSlots(); i++) {
				drops.add(compressedSlot.getStackInSlot(i));
			}
			
			if(!Loader.isModLoaded("redstoneflux")) {
				this.addEnergyDrops(te.storage.getEnergyStoredWithLong(), drops);
			}
			JiuUtils.item.spawnAsEntity(world, pos, drops);
		}
	}
	
	private void addEnergyDrops(long energy, NonNullList<ItemStack> drops) {
		while(energy >= 9000) {
			energy -= 9000;
			drops.add(new ItemStack(Blocks.REDSTONE_BLOCK));
		}
		while(energy >= 1000) {
			energy -= 1000;
			drops.add(new ItemStack(Items.REDSTONE));
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCompressor();
	}

	@Override
	public ItemBlock getRegisterItemBlock() {
		return new ItemBlock(this);
	}
}
