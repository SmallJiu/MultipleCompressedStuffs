package cat.jiu.mcs.items.compressed.ic;

import java.util.ArrayList;

import cat.jiu.mcs.util.MCSUtil;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.item.reactor.ItemReactorHeatSwitch;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class IC2HeatExchanger extends IC2HeatStorage {
	protected final ItemReactorHeatSwitch baseExchanger;

	public IC2HeatExchanger(String name, String baseItem) {
		this(name, baseItem, 0);
	}

	public IC2HeatExchanger(String name, String baseItem, int meta) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta));
	}

	public IC2HeatExchanger(String name, ItemStack baseItem) {
		super(name, baseItem);
		if(this.baseComponent instanceof ItemReactorHeatSwitch) {
			this.baseExchanger = (ItemReactorHeatSwitch) this.baseComponent;
		}else {
			throw new RuntimeException(baseItem.toString() + " is NOT ReactorHeatSwitch");
		}
	}

	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, int y, boolean heatrun) {
		if(!heatrun) {
			return;
		}
		int switchSide = (int) MCSUtil.item.getMetaValue(this.baseExchanger.switchSide, stack.getMetadata());
		int switchReactor = (int) MCSUtil.item.getMetaValue(this.baseExchanger.switchReactor, stack.getMetadata());

		int myHeat = 0;
		ArrayList<ItemStackCoord> heatAcceptors = new ArrayList<ItemStackCoord>();
		if(switchSide > 0) {
			this.checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
			this.checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
			this.checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
			this.checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
		}
		if(switchSide > 0) {
			for(ItemStackCoord stackcoord : heatAcceptors) {
				IReactorComponent heatable = (IReactorComponent) stackcoord.stack.getItem();
				double mymed = this.getBigDamage(stack) * 100.0 / this.getMaxBigDamage(stack);
				double heatablemed = heatable.getCurrentHeat(stackcoord.stack, reactor, stackcoord.x, stackcoord.y) * 100.0 / heatable.getMaxHeat(stackcoord.stack, reactor, stackcoord.x, stackcoord.y);
				int add = (int) (heatable.getMaxHeat(stackcoord.stack, reactor, stackcoord.x, stackcoord.y) / 100.0 * (heatablemed + mymed / 2.0));
				if(add > switchSide) {
					add = switchSide;
				}
				if(heatablemed + mymed / 2.0 < 1.0) {
					add = switchSide / 2;
				}
				if(heatablemed + mymed / 2.0 < 0.75) {
					add = switchSide / 4;
				}
				if(heatablemed + mymed / 2.0 < 0.5) {
					add = switchSide / 8;
				}
				if(heatablemed + mymed / 2.0 < 0.25) {
					add = 1;
				}
				if(Math.round(heatablemed * 10.0) / 10.0 > Math.round(mymed * 10.0) / 10.0) {
					add -= 2 * add;
				}else if(Math.round(heatablemed * 10.0) / 10.0 == Math.round(mymed * 10.0) / 10.0) {
					add = 0;
				}
				myHeat -= add;
				add = heatable.alterHeat(stackcoord.stack, reactor, stackcoord.x, stackcoord.y, add);
				myHeat += add;
			}
		}
		if(switchReactor > 0) {
			double mymed2 = this.getBigDamage(stack) * 100.0 / this.getMaxBigDamage(stack);
			double Reactormed = reactor.getHeat() * 100.0 / reactor.getMaxHeat();
			int add2 = (int) Math.round(reactor.getMaxHeat() / 100.0 * (Reactormed + mymed2 / 2.0));
			if(add2 > switchReactor) {
				add2 = switchReactor;
			}
			if(Reactormed + mymed2 / 2.0 < 1.0) {
				add2 = switchSide / 2;
			}
			if(Reactormed + mymed2 / 2.0 < 0.75) {
				add2 = switchSide / 4;
			}
			if(Reactormed + mymed2 / 2.0 < 0.5) {
				add2 = switchSide / 8;
			}
			if(Reactormed + mymed2 / 2.0 < 0.25) {
				add2 = 1;
			}
			if(Math.round(Reactormed * 10.0) / 10.0 > Math.round(mymed2 * 10.0) / 10.0) {
				add2 -= 2 * add2;
			}else if(Math.round(Reactormed * 10.0) / 10.0 == Math.round(mymed2 * 10.0) / 10.0) {
				add2 = 0;
			}
			myHeat -= add2;
			reactor.setHeat(reactor.getHeat() + add2);
		}
		this.alterHeat(stack, reactor, x, y, myHeat);
	}

	private void checkHeatAcceptor(final IReactor reactor, final int x, final int y, final ArrayList<ItemStackCoord> heatAcceptors) {
		final ItemStack stack = reactor.getItemAt(x, y);
		if(stack != null && stack.getItem() instanceof IReactorComponent) {
			final IReactorComponent comp = (IReactorComponent) stack.getItem();
			if(comp.canStoreHeat(stack, reactor, x, y)) {
				heatAcceptors.add(new ItemStackCoord(stack, x, y));
			}
		}
	}

	private class ItemStackCoord {
		public ItemStack stack;
		public int x;
		public int y;

		public ItemStackCoord(final ItemStack stack1, final int x1, final int y1) {
			this.stack = stack1;
			this.x = x1;
			this.y = y1;
		}
	}
}
