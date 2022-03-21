package cat.jiu.mcs.util;

import cat.jiu.core.api.IMetadataToolMaterial;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum ModSubtypes implements IStringSerializable, IMetadataToolMaterial {
	LEVEL_1(0),
	LEVEL_2(1),
	LEVEL_3(2),
	LEVEL_4(3),
	LEVEL_5(4),
	LEVEL_6(5),
	LEVEL_7(6),
	LEVEL_8(7),
	LEVEL_9(8),
	LEVEL_10(9),
	LEVEL_11(10),
	LEVEL_12(11),
	LEVEL_13(12),
	LEVEL_14(13),
	LEVEL_15(14),
	LEVEL_16(15);
	
	private final int meta;
	private static final ModSubtypes[] METADATA_LOOKUP = new ModSubtypes[values().length];
	
	ModSubtypes(int meta) {
		this.meta = meta;
	}
	
	public int getMeta() {
		return meta;
	}
	
	@Override
	public String getName() {
		return "level_" + meta;
	}
	
	public static ModSubtypes byMetadata(int meta) {
		return METADATA_LOOKUP[meta];
	}
	
	public int getMaxDamage() {return 0;}
	public float getAttackDamage() {return 0;}
	public float getAttackSpeed() {return 0;}
	public ItemStack getRepairItemStack() {return ItemStack.EMPTY;}
	
	static {
		for (ModSubtypes type : values()) {
			METADATA_LOOKUP[type.getMeta()] = type;
		}
	}
}