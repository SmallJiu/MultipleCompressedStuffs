package cat.jiu.mcs.blocks.net.container;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseUI.*;
import cat.jiu.mcs.blocks.net.NetworkHandler;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorChest;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressedChest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCompressedChest extends BaseTileEntityContainer<TileEntityCompressedChest> {
	protected final int meta;
	protected int slots;
	private final SoundEvent closeSound;

	public ContainerCompressedChest(EntityPlayer player, World world, BlockPos pos) {
		super(player, world, pos);
		this.meta = JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos));
		
		if(this.getTileEntity() != null) {
			this.closeSound = this.getTileEntity().getCloseSound();
			this.slots = this.getTileEntity().getSlotSize();
			this.outRows = (this.slots + 9 - 1) / 9 - 6;
			this.selectRows = (int) ((double) (currentScroll * (float) outRows) + 0.5D);
			if(this.selectRows < 0) {
				this.selectRows = 0;
			}

			int slotIndex = 0;
			for(int y = 0; y < 6; y++) {
				for(int x = 0; x < 9; x++) {
					this.addSlotToContainer(new UndefinedIndexSlot(getTileEntity().getSlots(), slotIndex, 8 + 18 * x, 18 + 18 * y));
					slotIndex += 1;
				}
			}
//			super.addHandlerSlot(getTileEntity().getSlots(), 8, 18, 9, 6, args -> {
//				return new UndefinedIndexSlot((IItemHandler)args[0], (int)args[1], (int)args[2], (int)args[3]);
//			});
			super.addPlayerInventorySlot(8, 140);
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
			NetworkHandler.INSTANCE.sendToServer(new MsgCompressorChest(currentScroll));
		}
		this.currentScroll = currentScroll;

		this.outRows = (this.slots + 9 - 1) / 9 - 6;
		this.selectRows = MathHelper.clamp((int) ((double) (currentScroll * (float) outRows) + 0.5D), 0, outRows);

		for(int slotY = 0; slotY < 6; ++slotY) {
			for(int slotX = 0; slotX < 9; ++slotX) {
				int stackIndex = slotX + (slotY + selectRows) * 9;
				int slotIndex = slotX + slotY * 9;

				Slot selectSlot = this.getSlot(slotIndex);
				if(selectSlot instanceof UndefinedIndexSlot) {
					((UndefinedIndexSlot) selectSlot).setIndex(stackIndex);
				}

				if(this.canAddItemToSlot(slotX, slotY)) {
					selectSlot.putStack(getTileEntity().getStack(stackIndex));
				}else {
					selectSlot.putStack(ItemStack.EMPTY);
				}
			}
		}
	}
	
	public boolean canAddItemToSlot(int slotX, int slotY) {
		int stackIndex = slotX + (slotY + selectRows) * 9;
		return stackIndex >= 0 && stackIndex < this.slots;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
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
			isMerged = super.mergeItemStack(getTileEntity().getSlots(), newStack, 0, this.slots, false);
		}

		if(!isMerged) {
			return ItemStack.EMPTY;
		}
		
		return oldStack;
	}

	public boolean canScroll() {
		return this.slots > 54;
	}
	public int getSlots() {
		return slots;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		if(this.closeSound != null) {
			this.world.playSound(null, this.pos, this.closeSound, SoundCategory.BLOCKS, 1, 1);
		}
		super.onContainerClosed(playerIn);
	}
	private int emptySlots = 0;

	@Override
	public void sendChanges() {
		if(!getTileEntity().getWorld().isRemote) {
			int emptySlot = getTileEntity().getEmptySlots();
			if(this.emptySlots != emptySlot) {
				this.emptySlots = emptySlot;
				for(IContainerListener listener : this.listeners) {
					listener.sendWindowProperty(this, 1001, getTileEntity().getEmptySlots());
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
}
