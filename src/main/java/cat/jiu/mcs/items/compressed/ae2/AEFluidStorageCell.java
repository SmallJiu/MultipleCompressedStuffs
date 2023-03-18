package cat.jiu.mcs.items.compressed.ae2;

import appeng.api.definitions.IItemDefinition;
import appeng.api.storage.data.IAEFluidStack;
import net.minecraft.item.ItemStack;

public class AEFluidStorageCell extends AEStorageCell<IAEFluidStack> {
	public AEFluidStorageCell(String name, IItemDefinition item) {
		super(name, item);
	}

	public AEFluidStorageCell(String name, ItemStack stack) {
		super(name, stack);
	}
	
	@Override
	protected double getBaseTypes() {
		return 2.9798;
	}
}
