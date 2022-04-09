package cat.jiu.mcs.items.compressed.ic;

import ic2.api.reactor.IReactor;
import ic2.core.item.reactor.ItemReactorCondensator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IC2Condensator extends IC2ReactorAssembly {
	protected final ItemReactorCondensator baseCondensator;

	public IC2Condensator(String name, String baseItem) {
		this(name, baseItem, 0);
	}

	public IC2Condensator(String name, String baseItem, int meta) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta));
	}

	public IC2Condensator(String name, ItemStack baseItem) {
		super(name, baseItem);
		if(baseItem.getItem() instanceof ItemReactorCondensator) {
			this.baseCondensator = (ItemReactorCondensator) baseItem.getItem();
		}else {
			throw new RuntimeException(baseItem.toString() + " is NOT Reactor Condensator");
		}
	}

	@Override
	public boolean canStoreHeat(final ItemStack stack, final IReactor reactor, final int x, final int y) {
		return this.getBigDamage(stack) < this.getMaxBigDamage(stack);
	}

	@Override
	public int getMaxHeat(final ItemStack stack, final IReactor reactor, final int x, final int y) {
		return this.getMaxCustomDamage(stack);
	}

	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		if(heat < 0) {
			return heat;
		}
		long currentHeat = this.getBigDamage(stack);
		long amount = Math.min(heat, this.getMaxBigDamage(stack) - currentHeat);
		heat -= amount;
		this.setBigDamage(stack, currentHeat + amount);
		return heat;
	}
}
