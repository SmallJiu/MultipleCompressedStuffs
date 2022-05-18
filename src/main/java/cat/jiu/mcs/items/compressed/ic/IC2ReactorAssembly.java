package cat.jiu.mcs.items.compressed.ic;

import java.util.List;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.base.sub.BaseItemSub;

import ic2.api.item.ICustomDamageItem;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.BaseElectricItem;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class IC2ReactorAssembly extends BaseItemSub implements IReactorComponent, ICustomDamageItem {
	protected final IReactorComponent baseComponent;
	protected final ICustomDamageItem baseDamageItem;

	public IC2ReactorAssembly(String name, ItemStack baseItem) {
		super(name, baseItem);
		this.setInfoStack(ItemStack.EMPTY);
		if(baseItem.getItem() instanceof IReactorComponent) {
			this.baseComponent = (IReactorComponent) baseItem.getItem();
		}else {
			throw new RuntimeException(baseItem.toString() + " is NOT ReactorComponent");
		}
		if(baseItem.getItem() instanceof ICustomDamageItem) {
			this.baseDamageItem = (ICustomDamageItem) baseItem.getItem();
		}else {
			this.baseDamageItem = null;
			MCS.instance.log.error(baseItem.toString() + " has NOT Damage");
		}
	}

	@Override
	public boolean createOreDictionary() {
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if(this.baseDamageItem != null) {
			tooltip.add(Localization.translate("ic2.reactoritem.durability") + " " + (this.getMaxBigDamage(stack) - this.getBigDamage(stack)) + "/" + this.getMaxBigDamage(stack));
		}
		super.addInformation(stack, world, tooltip, advanced);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return this.getBigDamage(stack) > 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (double) this.getBigDamage(stack) / (double) this.getMaxBigDamage(stack);
	}

	public long getBigDamage(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			return 0;
		}
		return stack.getTagCompound().getLong("advDmg");
	}

	@Override
	public int getCustomDamage(ItemStack stack) {
		return (int) this.getBigDamage(stack);
	}

	public long getMaxBigDamage(ItemStack stack) {
		if(this.baseDamageItem != null) {
			int base = this.baseDamageItem.getMaxCustomDamage(this.unCompressedItem);
			return (long) (base + (base * ((stack.getMetadata() + 1) * 1.968)));
		}
		return Long.MAX_VALUE;
	}

	@Override
	public int getMaxCustomDamage(ItemStack stack) {
		return (int) this.getMaxBigDamage(stack);
	}

	public void setBigDamage(ItemStack stack, long damage) {
		NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		nbt.setLong("advDmg", damage);
	}

	@Override
	public void setCustomDamage(ItemStack stack, int damage) {
		this.setBigDamage(stack, damage);
	}

	public boolean applyBigDamage(ItemStack stack, long damage, EntityLivingBase p2) {
		this.setBigDamage(stack, this.getBigDamage(stack) + damage);
		return true;
	}

	@Override
	public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase p2) {
		this.applyBigDamage(stack, damage, p2);
		return true;
	}

	public void setDamage(ItemStack stack, int damage) {
		final int prev = this.getCustomDamage(stack);
		if(damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
			IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid gradual item damage application (%d):", damage - prev);
		}
	}

	@Override
	public boolean canBePlacedIn(ItemStack stack, IReactor p1) {
		return this.baseComponent.canBePlacedIn(this.unCompressedItem, p1);
	}

	@Override
	public void processChamber(ItemStack stack, IReactor p1, int p2, int p3, boolean p4) {
		if(this.baseDamageItem == null) {
			return;
		}
		this.baseComponent.processChamber(this.unCompressedItem, p1, p2, p3, p4);
	}

	@Override
	public boolean acceptUraniumPulse(ItemStack stack, IReactor p1, ItemStack p2, int p3, int p4, int p5, int p6, boolean p7) {
		return this.baseComponent.acceptUraniumPulse(this.unCompressedItem, p1, p2, p3, p4, p5, p6, p7);
	}

	@Override
	public boolean canStoreHeat(ItemStack stack, IReactor reactor, int x, int y) {
		if(this.unCompressedItem.getItem() == Item.getByNameOrId("ic2:iridium_reflector") || this.unCompressedItem.getItem() == Item.getByNameOrId("ic2:component_heat_vent")) {
			return false;
		}
		return this.baseComponent.canStoreHeat(this.unCompressedItem, reactor, x, y);
	}

	@Override
	public int getMaxHeat(ItemStack stack, IReactor reactor, int x, int y) {
		if(this.baseDamageItem != null) {
			return this.baseDamageItem.getMaxCustomDamage(this.unCompressedItem);
		}
		return -1;
	}

	@Override
	public int getCurrentHeat(ItemStack stack, IReactor reactor, int x, int y) {
		return (int) this.getBigDamage(this.unCompressedItem);
	}

	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		return this.baseComponent.alterHeat(this.unCompressedItem, reactor, x, y, heat);
	}

	@Override
	public float influenceExplosion(ItemStack stack, IReactor reactor) {
		return this.baseComponent.influenceExplosion(this.unCompressedItem, reactor);
	}
}