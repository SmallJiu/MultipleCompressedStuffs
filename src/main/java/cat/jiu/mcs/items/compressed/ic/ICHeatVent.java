package cat.jiu.mcs.items.compressed.ic;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.MCSUtil;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.ItemReactorVent;
import ic2.core.item.reactor.ItemReactorVentSpread;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ICHeatVent extends ICReactorAssembly {
	protected final ItemReactorVent baseVent;
	protected final int reactorVent;
	protected final int selfVent;
	protected final int sideVent;
	
	public ICHeatVent(String name, String baseItem) {
		this(name, baseItem, 0);
	}
	
	public ICHeatVent(String name, String baseItem, int meta) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta));
	}
	
	public ICHeatVent(String name, ItemStack baseItem) {
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
			if(this.baseVent != null) {
				int reactorVent = (int) MCSUtil.item.getMetaValue(this.reactorVent, stack.getMetadata(), 0.3379F);
				int selfVent = (int) MCSUtil.item.getMetaValue(this.selfVent, stack.getMetadata(), 0.3794F);
				
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
					reactor.setHeat(rheat);
				}
				int self = this.alterHeat(stack, reactor, x, y, -selfVent);
				if(self <= 0) {
					reactor.addEmitHeat(self + selfVent);
				}
			}else {
				this.cool(reactor, x - 1, y);
	            this.cool(reactor, x + 1, y);
	            this.cool(reactor, x, y - 1);
	            this.cool(reactor, x, y + 1);
			}
		}
	}
	
	public void cool(final IReactor reactor, final int x, final int y) {
        final ItemStack stack = reactor.getItemAt(x, y);
        if (stack != null && stack.getItem() instanceof IReactorComponent) {
            final IReactorComponent comp = (IReactorComponent)stack.getItem();
            if (comp.canStoreHeat(stack, reactor, x, y)) {
                final int self = comp.alterHeat(stack, reactor, x, y, -this.sideVent);
                if (self <= 0) {
                    reactor.addEmitHeat(self + this.sideVent);
                }
            }
        }
    }
}
