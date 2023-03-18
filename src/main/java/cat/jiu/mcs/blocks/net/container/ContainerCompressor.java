package cat.jiu.mcs.blocks.net.container;

import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCompressor extends Container {
	long energy = 0;
	int shrinkCount = 10;
	boolean debug = false;
	private static final int shrinkCountID = 1;
	private static final int debugID = 2;
	private final InventoryPlayer inventory;
	private final World world;
	private final BlockPos pos;
	private TileEntityCompressor te = null;
	private List<Boolean> activate = NonNullList.withSize(16, true);

	public ContainerCompressor(EntityPlayer player, World world, BlockPos pos) {
		this.inventory = player.inventory;
		this.world = world;
		this.pos = pos;

		TileEntity posTe = world.getTileEntity(pos);
		if(posTe instanceof TileEntityCompressor) {
			this.te = (TileEntityCompressor) posTe;
		}

		if(this.te != null) {
			this.energy = this.te.storage.getEnergyStoredWithBigInteger().intValue();
			this.activate = this.te.getActivateList();

			this.addSlotToContainer(new SlotItemHandler(this.te.energySlot, 0, 10, 70));

			this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, 0, 95, 7));

			for(int i = 1; i <= 8; i++) {
				this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, i, 14 + 18 * i, 36));
				this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, i + 8, 14 + 18 * i, 65));
			}

			int[] range = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
			for(int i : range) {
				this.addSlotToContainer(new Slot(this.inventory, i + 9, 14 + 18 * i, 96));
				this.addSlotToContainer(new Slot(this.inventory, i + 18, 14 + 18 * i, 114));
				this.addSlotToContainer(new Slot(this.inventory, i + 27, 14 + 18 * i, 132));
				this.addSlotToContainer(new Slot(this.inventory, i, 14 + 18 * i, 154));
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		Slot slot = inventorySlots.get(index);

		if(slot == null || !slot.getHasStack()) {
			return ItemStack.EMPTY;
		}

		ItemStack newStack = slot.getStack(), oldStack = newStack.copy();

		boolean isMerged = false;

		if(index >= 1 && index <= 17) {
			isMerged = slot.getHasStack() && this.mergeItemStack(newStack, 18, 54, false);
		}else if(index >= 18 && index <= 53) {
			for(int machineSlotIndex = 1; machineSlotIndex < 18; machineSlotIndex++) {
				Slot machineSlot = this.inventorySlots.get(machineSlotIndex);
				if(JiuUtils.item.equalsStack(newStack, machineSlot.getStack())) {
					machineSlot.getStack().grow(newStack.getCount());
					newStack.setCount(0);
					isMerged = true;
					break;
				}
			}
		}

		if(!isMerged) {
			return ItemStack.EMPTY;
		}

		if(newStack.getCount() == 0) {
			slot.putStack(ItemStack.EMPTY);
		}else {
			slot.onSlotChanged();
		}

		slot.onTake(player, newStack);

		return oldStack;
	}
	
	public void checkChanged() {
		if(this.world.getTileEntity(this.pos) instanceof TileEntityCompressor) {
			this.te = (TileEntityCompressor) this.world.getTileEntity(this.pos);
			this.activate = this.te.getActivateList();
		}

		if(!this.world.isRemote) {
			if(this.shrinkCount != this.te.getShrinkCount()) {
				this.shrinkCount = this.te.getShrinkCount();
				for(IContainerListener listener : this.listeners) {
					listener.sendWindowProperty(this, ContainerCompressor.shrinkCountID, this.shrinkCount);
				}
			}
			if(this.debug != this.te.debug) {
				this.debug = this.te.debug;
				for(IContainerListener listener : this.listeners) {
					listener.sendWindowProperty(this, ContainerCompressor.debugID, this.te.debug ? 1 : 0);
				}
			}
			for(int i = 0; i < this.activate.size(); i++) {
				boolean slot = this.activate.get(i);
				if(slot != this.te.slotCanCraft(i)) {
					slot = this.te.slotCanCraft(i);
					this.activate.set(i, this.te.slotCanCraft(i));
					for(IContainerListener listener : this.listeners) {
						listener.sendWindowProperty(this, 10000 + i, slot ? 1 : 0);
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		if(id >= 10000 && id <= 10015) {
			int activateID = id - 10000;
			this.activate.set(activateID, data == 1);
			return;
		}
		switch(id) {
			case shrinkCountID:
				this.shrinkCount = data;
				break;
			case debugID:
				this.debug = data == 1;
				break;
		}
	}

	public TileEntityCompressor getTileEntity() {
		return this.te;
	}
	
	public boolean isDebug() {
		return this.debug;
	}
	
	public void setEnergy(long energy) {
		this.energy = energy;
	}

	public long getEnergy() {
		return this.energy;
	}

	public int getShrinkCount() {
		return this.shrinkCount;
	}

	public boolean slotCanCraft(int slotID) {
		return this.activate.get(slotID);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		this.checkChanged();
		boolean haveBlock = player.world.getBlockState(this.pos).getBlock() != Blocks.AIR;
		return player.world.equals(this.world) && player.getDistanceSq(this.pos) <= 32.0 && haveBlock;
	}
}
