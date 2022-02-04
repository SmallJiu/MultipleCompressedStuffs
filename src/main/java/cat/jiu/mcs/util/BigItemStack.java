//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.util;

import java.math.BigInteger;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BigItemStack {
	public static final BigItemStack EMPTY = new BigItemStack(Items.AIR, 0, 0);
	private final Item item;
	private BigInteger amount;
	private int meta;
	private NBTTagCompound nbt;

	public BigItemStack(ItemStack stack) {
		this(stack.getItem(), new BigInteger(Integer.toString(stack.getCount())), stack.getMetadata(), stack.getTagCompound());
	}

	public BigItemStack(Block block, long amout) {
		this(block, amout, 0);
	}

	public BigItemStack(Block block, long amout, int meta) {
		this(block, amout, meta, null);
	}

	public BigItemStack(Block block, long amout, int meta, NBTTagCompound nbt) {
		this(Item.getItemFromBlock(block), amout, meta, nbt);
	}

	public BigItemStack(Item item, long amout) {
		this(item, amout, 0);
	}

	public BigItemStack(Item item, long amout, int meta) {
		this(item, amout, meta, null);
	}

	public BigItemStack(Item item, long amount, int meta, NBTTagCompound nbt) {
		this.item = item;
		this.amount = new BigInteger(amount+"");
		this.meta = meta;
		this.nbt = nbt;
	}

	public BigItemStack(Block block) {
		this(block, new BigInteger("1"));
	}

	public BigItemStack(Block block, BigInteger amout) {
		this(block, amout, 0);
	}

	public BigItemStack(Block block, BigInteger amout, int meta) {
		this(block, amout, meta, null);
	}

	public BigItemStack(Block block, BigInteger amout, int meta, NBTTagCompound nbt) {
		this(Item.getItemFromBlock(block), amout, meta, nbt);
	}

	public BigItemStack(Item item) {
		this(item, new BigInteger("1"));
	}

	public BigItemStack(Item item, BigInteger amout) {
		this(item, amout, 0);
	}

	public BigItemStack(Item item, BigInteger amout, int meta) {
		this(item, amout, meta, null);
	}

	public BigItemStack(Item item, BigInteger amout, int meta, NBTTagCompound nbt) {
		this.item = item;
		this.amount = amout;
		this.meta = meta;
		this.nbt = nbt;
	}

	public void setCount(BigInteger amout) {
		this.amount = amout;
	}

	public void setCountWithLong(long amout) {
		this.amount = new BigInteger(Long.toString(amout));
	}

	public BigInteger getCount() {
		return amount;
	}

	public long getCountWithLong() {
		return amount.longValue();
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public int getMeta() {
		return meta;
	}

	public void setNbt(NBTTagCompound nbt) {
		this.nbt = nbt;
	}

	public NBTTagCompound getNbt() {
		return nbt;
	}

	public Item getItem() {
		return this.item;
	}

	public BigItemStack add(int amount) {
		this.amount.add(new BigInteger(Integer.toString(amount)));
		return this;
	}

	public BigItemStack shrink(int amount) {
		this.amount.subtract(new BigInteger(Integer.toString(amount)));
		return this;
	}

	public boolean isEmpty() {
		if (this == EMPTY) {
			return true;
		} else if (this.getItem() != null && this.getItem() != Items.AIR) {
			if (this.amount.longValue() <= 0) {
				return true;
			} else {
				return this.meta < -32768 || this.meta > 65535;
			}
		} else {
			return true;
		}
	}

	public void grow(long l) {
		this.setCount(this.amount.add(new BigInteger(l+"")));
	}
	
	public static BigItemStack copyWithSize(BigItemStack stack, long size){
		if (size == 0)
            return BigItemStack.EMPTY;
		BigItemStack copy = stack.copy();
        copy.setCountWithLong(size);
        return copy;
	}

	public BigItemStack copy() {
		return new BigItemStack(this.item, this.amount, this.meta, this.nbt);
	}
}
