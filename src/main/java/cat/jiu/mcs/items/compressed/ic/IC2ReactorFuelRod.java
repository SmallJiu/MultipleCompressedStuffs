package cat.jiu.mcs.items.compressed.ic;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import cat.jiu.core.api.events.item.IItemInPlayerInventoryTick;
import cat.jiu.core.util.JiuCoreEvents;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.base.sub.BaseItemSub;

import ic2.api.item.ICustomDamageItem;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.init.Localization;
import ic2.core.item.BaseElectricItem;
import ic2.core.item.armor.ItemArmorHazmat;
import ic2.core.item.reactor.ItemReactorUranium;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class IC2ReactorFuelRod extends BaseItemSub implements IReactorComponent, IItemInPlayerInventoryTick, ICustomDamageItem {
	
	public final int numberOfCells;
	protected final ItemReactorUranium base;
	protected final BaseItemSub depletedItem;

	public IC2ReactorFuelRod(String name, ItemStack baseItem, CreativeTabs tab, BaseItemSub depletedItem) {
		super(name, baseItem, tab);
		this.setInfoStack(ItemStack.EMPTY);
		if(baseItem.getItem() instanceof ItemReactorUranium) {
			this.base = (ItemReactorUranium) baseItem.getItem();
		}else {
			throw new RuntimeException(baseItem.toString() + " is NOT Reactor Fuel Rod");
		}
		this.numberOfCells = this.base.numberOfCells;
		this.depletedItem = depletedItem;
		JiuCoreEvents.addEvent(this);
	}

	public IC2ReactorFuelRod(String name, ItemStack baseItem, BaseItemSub depletedItem) {
		this(name, baseItem, MCS.COMPERESSED_ITEMS, depletedItem);
	}

	public IC2ReactorFuelRod(String name, String baseItem, int meta, CreativeTabs tab, BaseItemSub depletedItem) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta), tab, depletedItem);
	}

	public IC2ReactorFuelRod(String name, String baseItem, CreativeTabs tab, BaseItemSub depletedItem) {
		this(name, baseItem, 0, tab, depletedItem);
	}

	public IC2ReactorFuelRod(String name, String baseItem, BaseItemSub depletedItem) {
		this(name, baseItem, MCS.COMPERESSED_ITEMS, depletedItem);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if(this.base != null) {
			tooltip.add(Localization.translate("ic2.reactoritem.durability") + " " + (this.getMaxCustomDamage(stack) - this.getCustomDamage(stack)) + "/" + this.getMaxCustomDamage(stack));
		}
		super.addInformation(stack, world, tooltip, advanced);
	}

	@Override
	public void onItemInPlayerInventoryTick(EntityPlayer player, ItemStack invStack, int slot) {
		if(invStack.getItem() instanceof IC2RadiationItem) {
			if(!ItemArmorHazmat.hasCompleteHazmat(player)) {
				IC2Potion.radiation.applyTo(player, (int) (200 + (200 * ((invStack.getMetadata() + 1) * 0.3469))), 100);
			}
		}
	}

	// 是否可放置于反应堆
	@Override
	public boolean canBePlacedIn(ItemStack stack, IReactor reactor) {
		return true;
	}

	// 处理逻辑
	@Override
	public void processChamber(ItemStack stack, IReactor reactor, int x, final int y, boolean heatRun) {
		if(!reactor.produceEnergy()) {
			return;
		}
		double cells = (this.numberOfCells + (this.numberOfCells * ((stack.getMetadata()+1) * 0.007687)));
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
				int heat = (int) ((((pulses * pulses + pulses) / 2) * 4) * ((stack.getMetadata()+1) * 0.979));
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
			reactor.setItemAt(x, y, new ItemStack(this.depletedItem, 1, stack.getMetadata()));
		}else if(!heatRun) {
			this.applyCustomDamage(stack, 1, null);
		}
	}
	
	public static boolean isMOX(ItemStack stack) {
		Item item = stack.getItem();
		return item == Item.getByNameOrId("ic2:mox_fuel_rod")
			|| item == Item.getByNameOrId("ic2:dual_mox_fuel_rod")
			|| item == Item.getByNameOrId("ic2:quad_mox_fuel_rod");
	}
	
	public static int triangularNumber(int x) {
        return (x * x + x) / 2;
    }
	
	public int getFinalHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
        if(isMOX(this.unCompressedItem)) {
        	if (reactor.isFluidCooled()) {
                final float breedereffectiveness = reactor.getHeat() / (float)reactor.getMaxHeat();
                if (breedereffectiveness > 0.5) {
                    heat *= 2;
                }
            }
        }
		return heat;
    }
	
	public static int checkPulseable(IReactor reactor, int x, int y, ItemStack stack, int mex, int mey, boolean heatrun) {
        final ItemStack other = reactor.getItemAt(x, y);
        if (other != null && other.getItem() instanceof IReactorComponent && ((IReactorComponent)other.getItem()).acceptUraniumPulse(other, reactor, stack, x, y, mex, mey, heatrun)) {
            return 1;
        }
        return 0;
    }
	
	public void checkHeatAcceptor(final IReactor reactor, final int x, final int y, final Collection<ItemStackCoord> heatAcceptors) {
        final ItemStack stack = reactor.getItemAt(x, y);
        if (stack != null && stack.getItem() instanceof IReactorComponent && ((IReactorComponent)stack.getItem()).canStoreHeat(stack, reactor, x, y)) {
            heatAcceptors.add(new ItemStackCoord(stack, x, y));
        }
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return this.getCustomDamage(stack) > 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
        return (double)this.getCustomDamage(stack) / (double)this.getMaxCustomDamage(stack);
    }
	
	@Override
    public int getCustomDamage(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return 0;
        }
        return stack.getTagCompound().getInteger("advDmg");
    }
	
	@Override
	public int getMaxCustomDamage(ItemStack stack) {
		int base = this.base.getMaxCustomDamage(this.unCompressedItem);
		return (int) (base + (base * ((stack.getMetadata()+1) * 1.968)));
	}

	@Override
	public void setCustomDamage(ItemStack stack, int damage) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        nbt.setInteger("advDmg", damage);
	}

	@Override
	public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase p2) {
		this.setCustomDamage(stack, this.getCustomDamage(stack) + damage);
        return true;
	}
	
	public void setDamage(ItemStack stack, int damage) {
        final int prev = this.getCustomDamage(stack);
        if (damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
            IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid gradual item damage application (%d):", damage - prev);
        }
    }

	// 接受铀脉冲
	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor reactor, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
		if (!heatrun) {
			if(isMOX(this.unCompressedItem)) {
				float breedereffectiveness = reactor.getHeat() / (float)reactor.getMaxHeat();
				float ReaktorOutput = 4.0f * breedereffectiveness + 2.0f;
				float out = ReaktorOutput + (ReaktorOutput * (0.5F * (stack.getMetadata()+1) * 3.337F));
				reactor.addOutput(out);
			}else {
				reactor.addOutput(1.0f + (0.9F * ((stack.getMetadata()+1) * 0.639F)));
			}
		}
		return true;
	}

	// 可存储热量
	@Override
	public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return this.base.canStoreHeat(stack, reactor, x, y);
	}

	// 最大热量
	@Override
	public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return this.getMaxCustomDamage(stack);
	}

	// 获取当前热量
	@Override
	public int getCurrentHeat(ItemStack stack, IReactor reactor, final int x, int y) {
		return this.getCustomDamage(stack);
	}

	// 热量交换
	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		int base = this.base.alterHeat(this.unCompressedItem, reactor, x, y, heat);
		return (int) (base + (base * ((stack.getMetadata()+1)*0.579)));
	}

	// 爆炸影响(爆炸力)
	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		float base = this.base.influenceExplosion(stack, reactor);
		return (float) (base + (base * (((stack.getMetadata()+1))*1.921) * (this.numberOfCells + stack.getMetadata())));
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
