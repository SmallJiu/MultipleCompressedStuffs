package cat.jiu.mcs.util.type;

import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class ChangeBlockJsonType {
	private final Map<Integer, Integer[]> time;
	private final Map<Integer, List<ItemStack>> drops;
	private final Map<Integer, Boolean> canDrop;
	
	public ChangeBlockJsonType(Map<Integer,Integer[]> time, Map<Integer,List<ItemStack>> drops, Map<Integer, Boolean> canDrop) {
		this.time = time;
		this.drops = drops;
		this.canDrop = canDrop;
	}
	public Map<Integer,Integer[]> getTime() {return this.time;}
	public Map<Integer, List<ItemStack>> getDrops() { return drops; }
	public Map<Integer, Boolean> canDrop() {return this.canDrop;}
	
	public static class ChanegeBlockType {
		private final int meta;
		private final int[] time;
		private final List<ItemStack> drops;
		private final boolean canDrop;
		
		public ChanegeBlockType(int meta, int[] time, List<ItemStack> drops, boolean canDrop) {
			this.meta = meta;
			this.time = time;
			this.drops = drops;
			this.canDrop = canDrop;
		}
		
		public int getMeta() { return meta; }
		public int[] getTime() { return time; }
		public List<ItemStack> getDrops() { return drops; }
		public boolean canDrop() { return canDrop; }
	}
}
