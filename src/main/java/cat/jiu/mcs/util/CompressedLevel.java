package cat.jiu.mcs.util;

import java.util.Map;

import com.google.common.collect.Maps;

import cat.jiu.mcs.api.ICompressedStuff;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class CompressedLevel {
	private final Map<Integer, ItemStack> levels = Maps.newHashMap();
	private final ICompressedStuff stuff;
	public CompressedLevel(ICompressedStuff stuff) {
		this.stuff = stuff;
		Item item = stuff.getAsItem();
		for(int meta = 0; meta < 16; meta++) {
			this.levels.put(meta, new ItemStack(item, 1, meta));
		}
		if(stuff.isItem()) {
			this.levels.put(32766, new ItemStack(item, 1, 32766));
		}
	}
	
	public ItemStack getStack(int meta) {
		if(meta < 0) return this.levels.get(0);
		if(meta > 32766) return this.levels.get(32766);
		if(meta > 15 && (meta < 32766 || this.stuff.isBlock())) return this.levels.get(15);
		return this.levels.get(meta);
	}
}
