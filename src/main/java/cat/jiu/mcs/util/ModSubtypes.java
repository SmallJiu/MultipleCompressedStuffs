package cat.jiu.mcs.util;

import cat.jiu.core.api.IMetadataToolMaterial;

import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public enum ModSubtypes implements IStringSerializable, IMetadataToolMaterial {
	LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5, LEVEL_6, LEVEL_7, LEVEL_8, LEVEL_9, LEVEL_10, LEVEL_11, LEVEL_12, LEVEL_13, LEVEL_14, LEVEL_15, LEVEL_16;

	private static final ModSubtypes[] METADATA_LOOKUP = new ModSubtypes[values().length];

	public int getMeta() {
		return this.ordinal();
	}

	@Override
	public String getName() {
		return "level_" + this.ordinal();
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public static ModSubtypes byMetadata(int meta) {
		return METADATA_LOOKUP[meta];
	}

	public int getMaxDamage() {
		return 0;
	}

	public float getAttackDamage() {
		return 0;
	}

	public float getAttackSpeed() {
		return 0;
	}

	public ItemStack getRepairItemStack() {
		return ItemStack.EMPTY;
	}

	static {
		for(ModSubtypes type : values()) {
			METADATA_LOOKUP[type.getMeta()] = type;
		}
	}
}