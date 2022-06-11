package cat.jiu.mcs.util;

import cat.jiu.mcs.api.ICompressedStuff;
import net.minecraft.item.ItemStack;

public final class CompressedLevel {
	private final ItemStack Level_1;
	private final ItemStack Level_2;
	private final ItemStack Level_3;
	private final ItemStack Level_4;
	private final ItemStack Level_5;
	private final ItemStack Level_6;
	private final ItemStack Level_7;
	private final ItemStack Level_8;
	private final ItemStack Level_9;
	private final ItemStack Level_10;
	private final ItemStack Level_11;
	private final ItemStack Level_12;
	private final ItemStack Level_13;
	private final ItemStack Level_14;
	private final ItemStack Level_15;
	private final ItemStack Level_16;
	private final ItemStack Level_32767;
	
	public CompressedLevel(ICompressedStuff stuff) {
		this.Level_1 = new ItemStack(stuff.getItem(), 1, 0);
		this.Level_2 = new ItemStack(stuff.getItem(), 1, 1);
		this.Level_3 = new ItemStack(stuff.getItem(), 1, 2);
		this.Level_4 = new ItemStack(stuff.getItem(), 1, 3);
		this.Level_5 = new ItemStack(stuff.getItem(), 1, 4);
		this.Level_6 = new ItemStack(stuff.getItem(), 1, 5);
		this.Level_7 = new ItemStack(stuff.getItem(), 1, 6);
		this.Level_8 = new ItemStack(stuff.getItem(), 1, 7);
		this.Level_9 = new ItemStack(stuff.getItem(), 1, 8);
		this.Level_10 = new ItemStack(stuff.getItem(), 1, 9);
		this.Level_11 = new ItemStack(stuff.getItem(), 1, 10);
		this.Level_12 = new ItemStack(stuff.getItem(), 1, 11);
		this.Level_13 = new ItemStack(stuff.getItem(), 1, 12);
		this.Level_14 = new ItemStack(stuff.getItem(), 1, 13);
		this.Level_15 = new ItemStack(stuff.getItem(), 1, 14);
		this.Level_16 = new ItemStack(stuff.getItem(), 1, 15);
		if(stuff.isItem()) {
			this.Level_32767 = new ItemStack(stuff.getItem(), 1, 32766);
		}else {
			this.Level_32767 = null;
		}
	}
	
	public ItemStack getStack(int meta) {
		switch(meta) {
			default: return this.Level_1;
			case 1: return this.Level_2;
			case 2: return this.Level_3;
			case 3: return this.Level_4;
			case 4: return this.Level_5;
			case 5: return this.Level_6;
			case 6: return this.Level_7;
			case 7: return this.Level_8;
			case 8: return this.Level_9;
			case 9: return this.Level_10;
			case 10: return this.Level_11;
			case 11: return this.Level_12;
			case 12: return this.Level_13;
			case 13: return this.Level_14;
			case 14: return this.Level_15;
			case 15: return this.Level_16;
			case 32766: return this.Level_32767;
		}
	}
}
