package cat.jiu.mcs.items.compressed.ic;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;

import cat.jiu.core.events.item.ItemInPlayerEvent;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.item.reactor.ItemReactorUranium;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IC2ReactorFuelRod extends IC2ReactorAssembly {
	public final int numberOfCells;
	protected final ItemReactorUranium base;
	protected final BaseCompressedItem depletedItem;
	
	public IC2ReactorFuelRod(String name, String baseItem, BaseCompressedItem depletedItem) {
		this(name, baseItem, 0, depletedItem);
	}
	public IC2ReactorFuelRod(String name, String baseItem, int meta, BaseCompressedItem depletedItem) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta), depletedItem);
	}
	public IC2ReactorFuelRod(String name, ItemStack baseItem, BaseCompressedItem depletedItem) {
		super(name, baseItem);
		if(this.baseComponent instanceof ItemReactorUranium) {
			this.base = (ItemReactorUranium) baseItem.getItem();
		}else {
			throw new RuntimeException(baseItem.toString() + " is NOT Reactor Fuel Rod");
		}
		this.numberOfCells = this.base.numberOfCells;
		this.depletedItem = depletedItem;
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onItemInPlayerHandTick(ItemInPlayerEvent.InHand event) {
		if(event.isMainHand) {
			onItemInPlayerHandTick(event.getEntityPlayer(), event.stack, null);
		}else {
			onItemInPlayerHandTick(event.getEntityPlayer(), null, event.stack);
		}
	}

	private void onItemInPlayerHandTick(EntityPlayer player, ItemStack mainHand, ItemStack offHand) {
		if(mainHand != null && !mainHand.isEmpty() && mainHand.getItem() instanceof IC2ReactorFuelRod) {
			if(!ItemArmorHazmat.hasCompleteHazmat(player)) {
				IC2Potion.radiation.applyTo(player, (int) MCSUtil.item.getMetaValue(200, mainHand.getMetadata()), 100);
			}
		}else if(offHand != null && !offHand.isEmpty() && offHand.getItem() instanceof IC2ReactorFuelRod) {
			if(!ItemArmorHazmat.hasCompleteHazmat(player)) {
				IC2Potion.radiation.applyTo(player, (int) MCSUtil.item.getMetaValue(200, offHand.getMetadata()), 100);
			}
		}
	}

	@SubscribeEvent
	public void onItemInPlayerInventoryTick(ItemInPlayerEvent.InInventory event) {
		onItemInPlayerInventoryTick(event.getEntityPlayer(), event.stack, event.slot);
	}

	private void onItemInPlayerInventoryTick(EntityPlayer player, ItemStack invStack, int slot) {
		if(invStack.getItem() instanceof IC2ReactorFuelRod) {
			if(!ItemArmorHazmat.hasCompleteHazmat(player)) {
				IC2Potion.radiation.applyTo(player, (int) MCSUtil.item.getMetaValue(200, invStack.getMetadata()), 100);
			}
		}
	}

	// 处理逻辑
	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, final int y, boolean heatRun) {
		if(this.base == null) return;
		if(!reactor.produceEnergy()) {
			return;
		}
		double cells = (this.numberOfCells + (this.numberOfCells * ((stack.getMetadata() + 1) * 0.007687)));
		int basePulses = 1 + this.numberOfCells / 2;
		for(int iteration = 0; iteration < cells; ++iteration) {
			int pulses = basePulses;
			if(!heatRun) {
				for(int i = 0; i < pulses; ++i) {
					this.acceptUraniumPulse(stack, reactor, stack, x, y, x, y, heatRun);
				}
				pulses += checkPulseable(reactor, x - 1, y, stack, x, y, heatRun) + checkPulseable(reactor, x + 1, y, stack, x, y, heatRun) + checkPulseable(reactor, x, y - 1, stack, x, y, heatRun) + checkPulseable(reactor, x, y + 1, stack, x, y, heatRun);
			}else {
				pulses += checkPulseable(reactor, x - 1, y, stack, x, y, heatRun) + checkPulseable(reactor, x + 1, y, stack, x, y, heatRun) + checkPulseable(reactor, x, y - 1, stack, x, y, heatRun) + checkPulseable(reactor, x, y + 1, stack, x, y, heatRun);
				// 热值
				int heat = (int) ((((pulses * pulses + pulses) / 2) * 4) * ((stack.getMetadata() + 1) * 0.979));
				heat = this.getFinalHeat(stack, reactor, x, y, heat);
				Queue<ItemStackCoord> heatAcceptors = new ArrayDeque<ItemStackCoord>();
				this.checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
				this.checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
				this.checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
				this.checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);

				while(!heatAcceptors.isEmpty() && heat > 0) {
					int dheat = heat / heatAcceptors.size();
					heat -= dheat;
					ItemStackCoord acceptor = heatAcceptors.remove();
					IReactorComponent acceptorComp = (IReactorComponent) acceptor.stack.getItem();
					dheat = acceptorComp.alterHeat(acceptor.stack, reactor, acceptor.x, acceptor.y, dheat);
					heat += dheat;
				}
				if(heat > 0) {
					reactor.addHeat(heat);
				}
			}
		}
		if(!heatRun && this.getCustomDamage(stack) >= this.getMaxCustomDamage(stack) - 1) {
			reactor.setItemAt(x, y, this.depletedItem.getStack(stack.getMetadata()));
		}else if(!heatRun) {
			this.applyCustomDamage(stack, 1, null);
		}
	}

	public static boolean isMOX(ItemStack stack) {
		Item item = stack.getItem();
		return item == Item.getByNameOrId("ic2:mox_fuel_rod") || item == Item.getByNameOrId("ic2:dual_mox_fuel_rod") || item == Item.getByNameOrId("ic2:quad_mox_fuel_rod");
	}

	public static int triangularNumber(int x) {
		return (x * x + x) / 2;
	}

	public int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		if(isMOX(this.unCompressedItem)) {
			if(reactor.isFluidCooled()) {
				final float breedereffectiveness = reactor.getHeat() / (float) reactor.getMaxHeat();
				if(breedereffectiveness > 0.5) {
					heat *= 2;
				}
			}
		}
		return heat;
	}

	public static int checkPulseable(IReactor reactor, int x, int y, ItemStack stack, int mex, int mey, boolean heatrun) {
		final ItemStack other = reactor.getItemAt(x, y);
		if(other != null && other.getItem() instanceof IReactorComponent && ((IReactorComponent) other.getItem()).acceptUraniumPulse(other, reactor, stack, x, y, mex, mey, heatrun)) {
			return 1;
		}
		return 0;
	}

	public void checkHeatAcceptor(final IReactor reactor, final int x, final int y, final Collection<ItemStackCoord> heatAcceptors) {
		final ItemStack stack = reactor.getItemAt(x, y);
		if(stack != null && stack.getItem() instanceof IReactorComponent && ((IReactorComponent) stack.getItem()).canStoreHeat(stack, reactor, x, y)) {
			heatAcceptors.add(new ItemStackCoord(stack, x, y));
		}
	}

	// 接受铀脉冲
	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
		if(this.base == null) return false;
		if(!heatrun) {
			if(isMOX(this.unCompressedItem)) {
				float breedereffectiveness = reactor.getHeat() / (float) reactor.getMaxHeat();
				float ReaktorOutput = 4.0f * breedereffectiveness + 2.0f;
				float out = ReaktorOutput + (ReaktorOutput * (0.5F * (stack.getMetadata() + 1) * 3.337F));
				reactor.addOutput(out);
			}else {
				reactor.addOutput(1.0f + (0.9F * ((stack.getMetadata() + 1) * 0.639F)));
			}
		}
		return true;
	}

	// 热量交换
	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		if(this.base == null) return 0;
		int base = this.base.alterHeat(this.unCompressedItem, reactor, x, y, heat);
		return (int) (base + (base * ((stack.getMetadata() + 1) * 0.579)));
	}

	// 爆炸影响(爆炸力)
	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		if(this.base == null) return 0;
		float base = this.base.influenceExplosion(stack, reactor);
		float i = (float) (base + (base * (((stack.getMetadata() + 1)) * 1.921) * (this.numberOfCells + stack.getMetadata())));
		System.out.println(i);
		return i;
	}

	public static class ItemStackCoord {
		public final ItemStack stack;
		public final int x;
		public final int y;

		public ItemStackCoord(final ItemStack stack, final int x, final int y) {
			this.stack = stack;
			this.x = x;
			this.y = y;
		}
	}
}
