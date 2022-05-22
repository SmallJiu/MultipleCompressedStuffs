package cat.jiu.mcs.blocks.tileentity;

import cat.jiu.core.util.base.BaseTileEntity;
import cat.jiu.mcs.blocks.compressed.CompressedChest;
import cat.jiu.mcs.util.MCSUtil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCompressedChest extends BaseTileEntity.Normal {
	private ItemStackHandler slot;
	private SoundEvent closeSound;
	
	public TileEntityCompressedChest() {}
	public TileEntityCompressedChest(int meta, int baseSlots, SoundEvent closeSound) {
		this.slot = new ItemStackHandler((int) MCSUtil.item.getMetaValue(baseSlots, meta));
		this.closeSound = closeSound;
	}
	
	@Override
	public void tick(World world, BlockPos pos, IBlockState state) {}
	
	public ItemStack getStack(int slot) {
		return this.slot.getStackInSlot(slot);
	}
	public int getEmptySlots() {
		int slots = 0;
		for(int i = 0; i < this.slot.getSlots(); i++) {
			ItemStack stack = this.slot.getStackInSlot(i);
			if(stack.isEmpty()) slots += 1;
		}
		return slots;
	}
	public ItemStackHandler getSlots() {
		return this.slot;
	}
	
	public int getSlotSize() {
		return this.slot.getSlots();
	}
	
	public boolean isFull() {
		return this.isEmpty(true);
	}
	
	public boolean isEmpty() {
		return this.isEmpty(false);
	}
	
	private boolean isEmpty(boolean checkFull) {
		for (int i = 0; i < this.slot.getSlots(); i++) {
			boolean b = this.slot.getStackInSlot(i).isEmpty();
			if(checkFull ? !b : b) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		if(this.slot == null) {
			this.slot = new ItemStackHandler(nbt.getInteger("SlotSize"));
		}
		this.slot.deserializeNBT(nbt.getCompoundTag("SlotItems"));
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		nbt.setTag("SlotItems", this.slot.serializeNBT());
		nbt.setInteger("SlotSize", this.getSlotSize());
	}
	
	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing side) {
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(cap, side);
	}
	
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing side) {
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.slot);
		}
		return super.getCapability(cap, side);
	}
	public SoundEvent getCloseSound() {
		if(this.closeSound == null && this.blockType instanceof CompressedChest) {
			this.closeSound = ((TileEntityCompressedChest) this.blockType.createTileEntity(this.world, this.world.getBlockState(this.pos))).getCloseSound();
		}
		return this.closeSound;
	}
}
