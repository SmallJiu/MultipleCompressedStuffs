package cat.jiu.mcs.blocks;

import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.GuiHandler;
import cat.jiu.mcs.blocks.net.TileEntityCompressor;
import cat.jiu.mcs.util.init.MCSBlocks;

import cofh.api.item.IToolHammer;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class BlockCompressor extends BaseBlock.Normal implements ITileEntityProvider{
	public BlockCompressor() {
		super(MCS.MODID, "compressor", Material.ANVIL, SoundType.METAL, CreativeTabs.TRANSPORTATION, 10F);
		this.setBlockModelResourceLocation(MCS.MODID + "/block", this.name);
		MCSBlocks.BLOCKS0.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("NBTTags: ");
		tooltip.add(JiuUtils.nbt.getItemNBT(stack).toString());
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn,EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if(playerIn.isSneaking()) {
				if(playerIn.getHeldItemMainhand().getItem() instanceof IToolHammer) {
//					ItemStack dropBlock = JiuUtils.item.getStackFormBlockState(state);
//					NBTTagCompound nbt = new NBTTagCompound();
//					nbt.setTag("BlockEntityTag", world.getTileEntity(pos).writeToNBT(new NBTTagCompound()));
//					dropBlock.setTagCompound(nbt);
//					JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.item.getStackFormBlockState(state));
//					
					world.setBlockState(pos, Blocks.AIR.getDefaultState());
				}
			}else {
				int x = pos.getX(), y = pos.getY(), z = pos.getZ();
				playerIn.openGui(MCS.MODID, GuiHandler.COMPRESSOR, world, x, y, z);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
//		super.breakBlock(world, pos, state);
		TileEntity posTe = world.getTileEntity(pos);
		
		if(posTe instanceof TileEntityCompressor) {
			TileEntityCompressor te = (TileEntityCompressor)posTe;
			super.breakBlock(world, pos, state);
//			ItemStack dropBlock = JiuUtils.item.getStackFormBlockState(state);
//			NBTTagCompound nbt = new NBTTagCompound();
//			NBTTagCompound postNBT = te.writeToNBT(new NBTTagCompound());
//			
//			postNBT.removeTag("x");
//			postNBT.removeTag("y");
//			postNBT.removeTag("z");
//			postNBT.removeTag("id");
//			nbt.setTag("BlockEntityTag", postNBT);
//			dropBlock.setTagCompound(nbt);
//			JiuUtils.item.spawnAsEntity(world, pos, dropBlock);
//			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			
			JiuUtils.item.spawnAsEntity(world, pos, te.getEnergySlotItems());
			JiuUtils.item.spawnAsEntity(world, pos, te.getUnCompressedSlotSlotItems());
			JiuUtils.item.spawnAsEntity(world, pos, te.getCompressedSlotItems());
			
			if(!Loader.isModLoaded("redstoneflux")) {
				int i = te.energy;
				
				if(i >= 9000) {
					while (i >= 9000) {
						i -= 9000;
						JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(Blocks.REDSTONE_BLOCK));
					}
				}
				
				if(i >= 1000) {
					while (i >= 1000) {
						i -= 1000;
						JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(Items.REDSTONE));
					}
				}
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCompressor();
	}
}
