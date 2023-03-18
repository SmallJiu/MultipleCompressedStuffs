package cat.jiu.mcs.items.compressed.ae2;

import appeng.api.definitions.IItemDefinition;
import appeng.api.storage.data.IAEItemStack;
import net.minecraft.item.ItemStack;

public class AEItemStorageCell extends AEStorageCell<IAEItemStack> {
	public AEItemStorageCell(String name, ItemStack stack) {
		super(name, stack);
	}

	public AEItemStorageCell(String name, IItemDefinition item) {
		super(name, item);
	}
	
	@Override
	protected double getBaseTypes() {
		return 1.5259;
	}
}
