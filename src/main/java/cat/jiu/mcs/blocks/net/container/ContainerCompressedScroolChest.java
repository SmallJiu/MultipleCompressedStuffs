package cat.jiu.mcs.blocks.net.container;

import javax.annotation.Nonnull;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseUI;
import cat.jiu.mcs.blocks.net.NetworkHandler;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorScroolChest;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressedChest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCompressedScroolChest extends BaseUI.BaseContainer {
	protected final int meta;
	protected final int slots;
	protected TileEntityCompressedChest te = null;
	private final SoundEvent closeSound;

	public ContainerCompressedScroolChest(EntityPlayer player, World world, BlockPos pos) {
		super(player, world, pos);
		this.meta = JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos));
		TileEntity te = world.getTileEntity(pos);
		
		if(te instanceof TileEntityCompressedChest) this.te = (TileEntityCompressedChest) te;
		if(this.te != null) {
			this.closeSound = this.te.getCloseSound();
			this.slots = this.te.getSlotSize();
			this.outRows = (this.slots + 9 - 1) / 9 - 6;
			this.selectRows = (int) ((double) (currentScroll * (float) outRows) + 0.5D);
			if(this.selectRows < 0) {
				this.selectRows = 0;
			}

			int slotIndex = 0;
			for(int y = 0; y < 6; y++) {
				for(int x = 0; x < 9; x++) {
					this.addSlotToContainer(new HandlerSlot(this.te.getSlots(), slotIndex, 8 + 18 * x, 18 + 18 * y));
					slotIndex += 1;
				}
			}
			int x = 8;
			int y = 140;
			
			slotIndex = 0;
			for(int slotX = 0; slotX < 9; slotX++) {
				this.addSlotToContainer(new Slot(this.inventory, slotIndex, x + 18 * slotX, y + (18 * 2) + 22));
				slotIndex += 1;
			}
			
			for(int slotY = 0; slotY < 3; slotY++) {
				for(int slotX = 0; slotX < 9; slotX++) {
					this.addSlotToContainer(new Slot(this.inventory, slotIndex, x + 18 * slotX, y + (18 * slotY)));
					slotIndex += 1;
				}
			}
			this.scrollTo(0.0F);
		}else {
			throw new RuntimeException("It is not Compressed Chest! : " + pos.toString());
		}
	}

	float currentScroll = 0.0F;// 滚动的位置
	int outRows = 0;// 超出物品栏的栏数
	int selectRows = 0;// 已展示的超出物品栏的栏数

	public void scrollTo(float currentScroll) {
		if(this.world.isRemote) {
			NetworkHandler.INSTANCE.sendToServer(new MsgCompressorScroolChest(currentScroll));
		}
		TileEntity te = this.world.getTileEntity(this.blockPos);
		if(te instanceof TileEntityCompressedChest) this.te = (TileEntityCompressedChest) te;
		
		this.currentScroll = currentScroll;

		this.outRows = (this.slots + 9 - 1) / 9 - 6;
		this.selectRows = MathHelper.clamp((int) ((double) (currentScroll * (float) outRows) + 0.5D), 0, outRows);

		for(int slotY = 0; slotY < 6; ++slotY) {
			for(int slotX = 0; slotX < 9; ++slotX) {
				int stackIndex = slotX + (slotY + selectRows) * 9;
				int slotIndex = slotX + slotY * 9;

				Slot selectSlot = this.getSlot(slotIndex);
				if(selectSlot instanceof HandlerSlot) {
					((HandlerSlot) selectSlot).setIndex(stackIndex);
				}

				if(stackIndex >= 0 && stackIndex < this.slots) {
					selectSlot.putStack(this.te.getStack(stackIndex));
				}else {
					selectSlot.putStack(ItemStack.EMPTY);
				}
			}
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		TileEntity te = this.world.getTileEntity(blockPos);
		if(te instanceof TileEntityCompressedChest)
			this.te = (TileEntityCompressedChest) te;
		Slot slot = this.inventorySlots.get(index);

		if(slot == null || !slot.getHasStack()) {
			return ItemStack.EMPTY;
		}

		ItemStack newStack = slot.getStack(),
				  oldStack = newStack.copy();

		boolean isMerged = false;

		if(index < 54) {
			isMerged = super.mergeItemStack(newStack, 54, 89, false);
		}else {
			isMerged = super.mergeItemStack(this.te.getSlots(), newStack, 0, this.slots, false);
		}

		if(!isMerged) {
			return ItemStack.EMPTY;
		}
		
		return oldStack;
	}

	public boolean canScroll() {
		return this.slots > 54;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		if(this.closeSound != null) {
			this.world.playSound(null, this.blockPos, this.closeSound, SoundCategory.BLOCKS, 1, 1);
		}
		super.onContainerClosed(playerIn);
	}
	private int emptySlots = 0;

	@Override
	public void sendChanges() {
		if(this.world.getTileEntity(this.blockPos) instanceof TileEntityCompressedChest) {
			this.te = (TileEntityCompressedChest) this.world.getTileEntity(this.blockPos);
		}
		if(!this.te.getWorld().isRemote) {
			int emptySlot = this.te.getEmptySlots();
			if(this.emptySlots != emptySlot) {
				this.emptySlots = emptySlot;
				for(IContainerListener listener : this.listeners) {
					listener.sendWindowProperty(this, 1001, this.te.getEmptySlots());
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	protected void updateChanges(int id, int data) {
		switch(id) {
			case 1001: this.emptySlots = data; break;
		}
	}
	public int getEmptySlots() {return emptySlots;}
	public TileEntityCompressedChest getTileEntity() { return te; }

	class HandlerSlot extends SlotItemHandler {
		private int index;

		public HandlerSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
			super(itemHandler, index, xPosition, yPosition);
			this.index = index;
		}

		@Override
		public boolean isItemValid(@Nonnull ItemStack stack) {
			if(stack.isEmpty()
			|| !getItemHandler().isItemValid(index, stack)
			|| index >= this.getItemHandler().getSlots()) {
				return false;
			}

			IItemHandler handler = this.getItemHandler();
			ItemStack remainder;
			if(handler instanceof IItemHandlerModifiable) {
				IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;
				ItemStack currentStack = handlerModifiable.getStackInSlot(index);

				handlerModifiable.setStackInSlot(index, ItemStack.EMPTY);

				remainder = handlerModifiable.insertItem(index, stack, true);

				handlerModifiable.setStackInSlot(index, currentStack);
			}else {
				remainder = handler.insertItem(index, stack, true);
			}
			return remainder.getCount() < stack.getCount();
		}

		@Override
		public int getItemStackLimit(@Nonnull ItemStack stack) {
			if(index >= this.getItemHandler().getSlots()) {
				return 0;
			}
			ItemStack maxAdd = stack.copy();
			int maxInput = stack.getMaxStackSize();
			maxAdd.setCount(maxInput);

			IItemHandler handler = this.getItemHandler();
			ItemStack currentStack = handler.getStackInSlot(index);
			if(handler instanceof IItemHandlerModifiable) {
				IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;

				handlerModifiable.setStackInSlot(index, ItemStack.EMPTY);

				ItemStack remainder = handlerModifiable.insertItem(index, maxAdd, true);

				handlerModifiable.setStackInSlot(index, currentStack);

				return maxInput - remainder.getCount();
			}else {
				ItemStack remainder = handler.insertItem(index, maxAdd, true);

				int current = currentStack.getCount();
				int added = maxInput - remainder.getCount();
				return current + added;
			}
		}

		@Override
		@Nonnull
		public ItemStack decrStackSize(int amount) {
			if(index >= this.getItemHandler().getSlots()) {
				return ItemStack.EMPTY;
			}
			return this.getItemHandler().extractItem(index, amount, false);
		}

		@Override
		public boolean canTakeStack(EntityPlayer playerIn) {
			if(index >= this.getItemHandler().getSlots()) {
				return false;
			}
			return !this.getItemHandler().extractItem(index, 1, true).isEmpty();
		}

		@Override
		public int getSlotStackLimit() {
			if(index >= this.getItemHandler().getSlots()) {
				return 0;
			}
			return this.getItemHandler().getSlotLimit(this.index);
		}

		@Override
		public void putStack(@Nonnull ItemStack stack) {
			if(index < this.getItemHandler().getSlots()) {
				((IItemHandlerModifiable) this.getItemHandler()).setStackInSlot(index, stack);
				this.onSlotChanged();
			}
		}

		@Override
		@Nonnull
		public ItemStack getStack() {
			if(index >= this.getItemHandler().getSlots()) {
				return ItemStack.EMPTY;
			}
			return this.getItemHandler().getStackInSlot(index);
		}
		
		@Override
		public int getSlotIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}
}
