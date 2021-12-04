package cat.jiu.mcs.blocks.net;

import javax.annotation.Nonnull;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.BigItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class BigItemStackHandler extends ItemStackHandler {
	protected NonNullList<BigItemStack> stacks;

	public BigItemStackHandler() {
		this(1);
	}

	public BigItemStackHandler(int size) {
		this.stacks = NonNullList.withSize(size, BigItemStack.EMPTY);
	}

	public BigItemStackHandler(NonNullList<BigItemStack> stacks) {
		this.stacks = stacks;
	}

	public void setStackInSlot(int slot, @Nonnull BigItemStack stack) {
		validateSlotIndex(slot);
		this.stacks.set(slot, stack);
		onContentsChanged(slot);
	}

	@Override
	public int getSlots() {
		return this.stacks.size();
	}

	@Nonnull
	public BigItemStack getStackInSlot0(int slot) {
		validateSlotIndex(slot);
		return this.stacks.get(slot);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		BigItemStack stack0 = this.stacks.get(slot);
		stack0.add(stack.getCount());
		MCS.instance.log.info(stack.toString());
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		BigItemStack stack = this.stacks.get(slot+1);
		if(stack.getCountWithLong() >= amount) {
			stack.shrink(amount);
			return new ItemStack(stack.getItem(), 64, stack.getMeta(), stack.getNbt());
		}else {
			this.stacks.set(slot+1, BigItemStack.EMPTY);
			return new ItemStack(stack.getItem(), (int) stack.getCountWithLong(), stack.getMeta(), stack.getNbt());
		}
	}
}
