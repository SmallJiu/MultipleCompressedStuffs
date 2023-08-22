package cat.jiu.mcs.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import appeng.api.implementations.items.IStorageCell;
import appeng.me.storage.AbstractCellInventory;

import cat.jiu.mcs.config.Configs;

import net.minecraft.item.ItemStack;

@Pseudo
@Mixin(value = AbstractCellInventory.class, remap = false)
public class MixinAEStorageCell {
	private MixinAEStorageCell() {
		throw new RuntimeException();
	}

	@Mutable
	@Final
	private static int MAX_ITEM_TYPES;

	@Mutable
	@Final
	private static String[] ITEM_SLOT_KEYS;

	@Mutable
	@Final
	private static String[] ITEM_SLOT_COUNT_KEYS;

	private static final String ITEM_SLOT = "#";
	private static final String ITEM_SLOT_COUNT = "@";

	@Inject(
		at = {@At(
			value = "RETURN")},
		method = {"<clinit>*"})
	private static void setMaxItemTypes_Static(CallbackInfo ci) {
		if(Configs.Custom.Mod_Stuff.AppliedEnergistics2) {
			MAX_ITEM_TYPES = 127;

			ITEM_SLOT_KEYS = new String[MAX_ITEM_TYPES];
			ITEM_SLOT_COUNT_KEYS = new String[MAX_ITEM_TYPES];

			for(int x = 0; x < MAX_ITEM_TYPES; x++) {
				ITEM_SLOT_KEYS[x] = ITEM_SLOT + x;
				ITEM_SLOT_COUNT_KEYS[x] = ITEM_SLOT_COUNT + x;
			}
		}
	}

	private int maxItemTypes;
	private ItemStack i;
	protected IStorageCell<?> cellType;

	@Inject(
		at = {@At(
			value = "RETURN")},
		method = {"<init>*"})
	private void setMaxItemTypes_Class(CallbackInfo ci) {
		if(Configs.Custom.Mod_Stuff.AppliedEnergistics2) {
			this.maxItemTypes = this.cellType.getTotalTypes(this.i);
			if(this.maxItemTypes > MAX_ITEM_TYPES) {
				this.maxItemTypes = MAX_ITEM_TYPES;
			}
			if(this.maxItemTypes < 1) {
				this.maxItemTypes = 1;
			}
		}
	}
}
