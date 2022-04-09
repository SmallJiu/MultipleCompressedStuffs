package cat.jiu.mcs.items.compressed.ic;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.MCSUtil;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.ItemReactorVent;
import ic2.core.item.reactor.ItemReactorVentSpread;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IC2HeatVent extends IC2HeatStorage {
	protected final ItemReactorVent baseVent;
	protected final int reactorVent;
	protected final int selfVent;
	protected final int sideVent;

	public IC2HeatVent(String name, String baseItem) {
		this(name, baseItem, 0);
	}

	public IC2HeatVent(String name, String baseItem, int meta) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta));
	}

	public IC2HeatVent(String name, ItemStack baseItem) {
		super(name, baseItem);
		if(this.baseComponent instanceof ItemReactorVent) {
			this.baseVent = (ItemReactorVent) this.baseComponent;
			this.reactorVent = this.baseVent.reactorVent;
			this.selfVent = this.baseVent.selfVent;
			this.sideVent = -1;
		}else {
			this.baseVent = null;
			this.reactorVent = -1;
			this.selfVent = -1;
			if(this.baseComponent instanceof ItemReactorVentSpread) {
				this.sideVent = ((ItemReactorVentSpread) this.baseComponent).sideVent;
			}else {
				this.sideVent = -1;
			}
			MCS.instance.log.error(baseItem.toString() + " is NOT ReactorVent");
		}
	}

	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
		if(heatrun) {
			if(this.baseComponent instanceof ItemReactorVentSpread) {
				this.cool(reactor, x - 1, y);
				this.cool(reactor, x + 1, y);
				this.cool(reactor, x, y - 1);
				this.cool(reactor, x, y + 1);
			}else {
				int reactorVent = (int) MCSUtil.item.getMetaValue(this.reactorVent, stack.getMetadata());
				int selfVent = (int) MCSUtil.item.getMetaValue(this.selfVent, stack.getMetadata());

				if(reactorVent > 0) {
					int reactorDrain;
					int rheat = reactorDrain = reactor.getHeat();
					if(reactorDrain > reactorVent) {
						reactorDrain = reactorVent;
					}
					rheat -= reactorDrain;
					if((reactorDrain = this.alterHeat(stack, reactor, x, y, reactorDrain)) > 0) {
						return;
					}
					reactor.setHeat((int) rheat);
				}
				int self = this.alterHeat(stack, reactor, x, y, -selfVent);
				if(self <= 0) {
					reactor.addEmitHeat((int) (self + selfVent));
				}
			}
		}
	}

	private void cool(IReactor reactor, int x, int y) {
		ItemStack stack = reactor.getItemAt(x, y);
		if(stack != null && stack.getItem() instanceof IReactorComponent) {
			IReactorComponent comp = (IReactorComponent) stack.getItem();
			if(comp.canStoreHeat(stack, reactor, x, y)) {
				int self = comp.alterHeat(stack, reactor, x, y, -this.sideVent);
				if(self <= 0) {
					reactor.addEmitHeat(self + this.sideVent);
				}
			}
		}
	}

	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		if(this.baseComponent instanceof ItemReactorVentSpread) {
			return heat;
		}
		return super.alterHeat(stack, reactor, x, y, heat);
	}
}
