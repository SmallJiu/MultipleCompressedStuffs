package cat.jiu.mcs.items.compressed.ae2;

import appeng.api.AEApi;
import appeng.api.definitions.IItemDefinition;
import appeng.api.storage.IStorageChannel;
import appeng.api.storage.channels.IFluidStorageChannel;
import appeng.api.storage.data.IAEFluidStack;
import appeng.fluids.helper.FluidCellConfig;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class AEFluidStorageCell extends AEStorageCell<IAEFluidStack> {
	public AEFluidStorageCell(String name, IItemDefinition item) {
		super(name, item);
	}

	public AEFluidStorageCell(String name, ItemStack stack) {
		super(name, stack);
	}

	@Override
	public int getTotalTypes(ItemStack cellItem) {
		return 15;
	}

	@Override
	public IItemHandler getConfigInventory(ItemStack is) {
		return new FluidCellConfig(is);
	}

	@Override
	public IStorageChannel<IAEFluidStack> getChannel() {
		return AEApi.instance().storage().getStorageChannel(IFluidStorageChannel.class);
	}
}
