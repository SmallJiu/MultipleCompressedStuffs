package cat.jiu.mcs.util;

import java.util.Arrays;
import java.util.Map;

import com.google.common.collect.Maps;

import cat.jiu.core.api.IMetadataToolMaterial;
import cat.jiu.core.api.ISubBlockSerializable;
import cat.jiu.mcs.config.Configs;

import net.minecraft.item.ItemStack;

public final class ModSubtypes implements ISubBlockSerializable, IMetadataToolMaterial, Comparable<ModSubtypes> {
	public static final int MAX = Configs.Max_Compressed_Stuff;
	public static final short INFINITY = Short.MAX_VALUE - 1;
	private static final ModSubtypes[] METADATA_LOOKUP;
	private static final Map<String, ModSubtypes> Name_Map;
	
	public final int meta;
	private ModSubtypes(int meta) {
		this.meta = meta;
	}
	public int getMeta() {
		return this.meta;
	}

	@Override
	public String getName() {
		return "level_" + this.getMeta();
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public int getMaxDamage() { return 0; }
	public float getAttackDamage() { return 0; }
	public float getAttackSpeed() { return 0; }
	public ItemStack getRepairItemStack() { return ItemStack.EMPTY; }
	
	@Override
	public int compareTo(ModSubtypes o) {
		int other = o.getMeta();
		if(this.meta > other) {
			return 1;
		}else if(this.meta < other) {
			return -1;
		}
		return 0;
	}
	
	public static ModSubtypes byMetadata(int meta) {
		return METADATA_LOOKUP[meta];
	}
	public static ModSubtypes byName(String name) {
		return Name_Map.get(name);
	}
	
	public static ModSubtypes[] values() {
		return values(MAX);
	}
	public static ModSubtypes[] values(int length) {
		if(METADATA_LOOKUP == null) {
			ModSubtypes[] values = new ModSubtypes[MAX];
			for(int i = 0; i < values.length; i++) {
				values[i] = new ModSubtypes(i);
			}
			return values;
		}else {
			return Arrays.copyOf(METADATA_LOOKUP, length);
		}
	}
	static {
		Map<String, ModSubtypes> map = Maps.newHashMap();
		ModSubtypes[] types = values();
		for(int i = 0; i < types.length; i++) {
			ModSubtypes type = types[i];
			map.put(type.getName(), type);
		}
		METADATA_LOOKUP = types;
		Name_Map = map;
	}
}
