package cat.jiu.mcs.blocks.net.container;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseUI;
import cat.jiu.mcs.blocks.net.NetworkHandler;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorPageChest;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressedChest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCompressedPageChest extends BaseUI.BaseContainer<TileEntityCompressedChest> {
	protected final int meta;
	protected final int slots;
	private final int maxPage;
	private final SoundEvent closeSound;

	public ContainerCompressedPageChest(EntityPlayer player, World world, BlockPos pos) {
		super(player, world, pos);
		this.meta = JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos));
		
		if(this.te != null) {
			this.closeSound = this.te.getCloseSound();
			this.slots = this.te.getSlotSize();
			this.maxPage = this.slots / 54;
			super.addHandlerSlot(this.te.getSlots(), 8, 18, 9, 6, args->new SlotItemHandler((IItemHandler)args[0], (int)args[1], (int)args[2], (int)args[3]));
			super.addPlayerInventorySlot(8, 140);
			this.toPage(0);
		}else {
			throw new RuntimeException("It is not Compressed Chest! : " + pos.toString());
		}
	}
	
	public boolean toPage(int page) {
		if(!this.canNextPage()) return false; // 检查能不能翻页
		if(page < 0) return false; // 检查页数是不是小于0
		if(page > this.maxPage) return false; // 检查页数是不是大于最大页数
		
		if(this.world.isRemote) {
			NetworkHandler.INSTANCE.sendToServer(new MsgCompressorPageChest(page));
		}
		TileEntity te = this.world.getTileEntity(this.pos);
		if(te instanceof TileEntityCompressedChest) {
			this.te = (TileEntityCompressedChest) te;
			int stackIndex = page * 54;
			System.out.println("Page: " + page + ", StartIndex: " + stackIndex);
			for(int slotY = 0; slotY < 6; ++slotY) {
				for(int slotX = 0; slotX < 9; ++slotX) {
					int slotIndex = slotX + slotY * 9;
					System.out.println("Page: " + page + ", StackIndex: " + stackIndex + ", SlotIndex: " + slotIndex);
					Slot selectSlot = this.getSlot(slotIndex);
					if(stackIndex >= 0 && stackIndex < this.slots) {
						selectSlot.putStack(this.te.getStack(stackIndex));
					}else {
						selectSlot.putStack(ItemStack.EMPTY);
					}
					stackIndex += 1;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public int getMaxPage() {
		return this.maxPage;
	}

	public boolean canNextPage() {
		return this.slots > 54;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		TileEntity te = this.world.getTileEntity(this.pos);
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
}
