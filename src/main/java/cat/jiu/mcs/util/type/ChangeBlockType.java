package cat.jiu.mcs.util.type;

import java.util.List;

import cat.jiu.core.util.JiuUtils;
import net.minecraft.item.ItemStack;

public class ChangeBlockType {
	private final List<ItemStack> drops;
	private final int[] time;
	private final boolean dropBlock;
	
	public ChangeBlockType(ItemStack[] drops, int[] time, boolean dropBlock) {
		this(JiuUtils.other.copyArrayToList(drops), time, dropBlock);
	}
	
	public ChangeBlockType(List<ItemStack> drops, int[] time, boolean dropBlock) {
		this.drops = drops;
		this.time = time;
		this.dropBlock = dropBlock;
	}
	
	public List<ItemStack> getDrops() { return drops; }
	public int[] getTime() { return time; }
	public boolean canDropBlock() { return dropBlock; }
}
