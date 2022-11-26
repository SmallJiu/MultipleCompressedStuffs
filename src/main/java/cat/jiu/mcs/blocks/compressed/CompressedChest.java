package cat.jiu.mcs.blocks.compressed;

import cat.jiu.core.api.events.iface.player.IPlayerCraftedItemEvent;
import cat.jiu.core.util.JiuCoreEvents;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.GuiHandler;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressedChest;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.base.sub.BaseCompressedBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.items.ItemStackHandler;

public class CompressedChest extends BaseCompressedBlock implements IPlayerCraftedItemEvent {
	protected final int baseSlot;
	protected final SoundEvent openSound;
	protected final SoundEvent closeSound;

	public CompressedChest(String nameIn, ItemStack unCompressedItem, int baseSlot) {
		this(nameIn, unCompressedItem, baseSlot, getOpen(unCompressedItem), getClose(unCompressedItem));
	}

	static SoundEvent getOpen(ItemStack unCompressedItem) {
		if(JiuUtils.item.isBlock(unCompressedItem)) {
			Block b = JiuUtils.item.getBlockFromItemStack(unCompressedItem);
			if(b == Blocks.CHEST) {
				return SoundEvents.BLOCK_CHEST_OPEN;
			}else if(b instanceof BlockShulkerBox) {
				return SoundEvents.BLOCK_SHULKER_BOX_OPEN;
			}
		}
		return null;
	}

	static SoundEvent getClose(ItemStack unCompressedItem) {
		if(JiuUtils.item.isBlock(unCompressedItem)) {
			Block b = JiuUtils.item.getBlockFromItemStack(unCompressedItem);
			if(b == Blocks.CHEST) {
				return SoundEvents.BLOCK_CHEST_CLOSE;
			}else if(b instanceof BlockShulkerBox) {
				return SoundEvents.BLOCK_SHULKER_BOX_CLOSE;
			}
		}
		return null;
	}

	public CompressedChest(String nameIn, ItemStack unCompressedItem, int baseSlot, SoundEvent openSound, SoundEvent closeSound) {
		super(nameIn, unCompressedItem);
		this.baseSlot = baseSlot;
		this.openSound = openSound;
		this.closeSound = closeSound;
		this.setInfoStack(new ItemStack(Items.AIR));
		JiuCoreEvents.addEvent(this);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			if(this.useWrenchBreak(world, pos, state, player, hand, false)) return true;
		}
		player.openGui(MCS.MODID, Configs.use_scrool_gui ? GuiHandler.CHEST_SCROOL_GUI : GuiHandler.CHEST_PAGE_GUI, world, pos.getX(), pos.getY(), pos.getZ());
		if(this.openSound != null) {
			world.playSound(null, pos, this.openSound, SoundCategory.BLOCKS, 1, 1);
		}
		return true;
	}
	
	@Override
	protected boolean wrenchBreak(World world, BlockPos pos, IBlockState state, boolean useMap) {
		if(world.getTileEntity(pos) != null) {
			ItemStack stack = new ItemStack(this, 1, JiuUtils.item.getMetaFromBlockState(state));
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setTag("BlockEntityTag", world.getTileEntity(pos).writeToNBT(new NBTTagCompound()));
			world.setBlockToAir(pos);
			JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.nbt.setNBT(stack, nbt));
			return true;
		}
		return false;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if(world.isRemote) return;
		if(world.getTileEntity(pos) instanceof TileEntityCompressedChest) {
			JiuUtils.item.spawnAsEntity(world, pos, ((TileEntityCompressedChest) world.getTileEntity(pos)).getSlots());
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCompressedChest(meta, this.baseSlot, this.closeSound);
	}

	@Override
	public void onPlayerCraftedItemInGui(EntityPlayer player, IInventory gui, ItemStack stack) {
		ItemStack in = null;
		for(int i = 0; i < gui.getSizeInventory(); i++) {
			ItemStack s = gui.getStackInSlot(i);
			if(!s.isEmpty()) {
				in = s;
				break;
			}
		}
		if(in != null && in.getItem() == Item.getItemFromBlock(this)) {
			if(JiuUtils.nbt.getItemNBT(in).getSize() > 0) {
				ItemStackHandler stacks = new ItemStackHandler(((TileEntityCompressedChest) this.createNewTileEntity(player.world, in.getMetadata())).getSlotSize());
				stacks.deserializeNBT(JiuUtils.nbt.getItemNBT(in).getCompoundTag("SlotItems"));
				JiuUtils.item.spawnAsEntity(player.world, player.getPosition(), stacks);
			}
		}
	}
}
