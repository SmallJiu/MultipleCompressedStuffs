package cat.jiu.mcs.items.compressed.ic.upgrade;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import ic2.api.upgrade.IEnergyStorageUpgrade;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.upgrade.UpgradeRegistry;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ICEnergyStorageUpgrade extends BaseCompressedItem implements IEnergyStorageUpgrade {
	protected final IEnergyStorageUpgrade base;
	
	public ICEnergyStorageUpgrade(String name, ItemStack baseItem) {
		super(name, baseItem);
		if(baseItem.getItem() instanceof IEnergyStorageUpgrade) {
			this.base = (IEnergyStorageUpgrade) baseItem.getItem();
		}else {
			throw new RuntimeException(String.format("%s not a Energy Storage Upgrade!", baseItem));
		}
		this.setCanShowBaseStackInfo(false);
		for(ItemStack item : ModSubtypes.getAllCompressedStack(this)) {
			UpgradeRegistry.register(item);
		}
	}

	@Override
	public boolean createOreDictionary() {
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("ic2.tooltip.upgrade.storage", this.getExtraEnergyStorage(stack, null) * stack.getCount()));
		super.addInformation(stack, world, tooltip, advanced);
	}

	@Override
	public boolean isSuitableFor(ItemStack stack, Set<UpgradableProperty> p1) {
		return this.base.isSuitableFor(getUnCompressedStack(), p1);
	}

	@Override
	public boolean onTick(ItemStack stack, IUpgradableBlock te) {
		return false;
	}

	@Override
	public Collection<ItemStack> onProcessEnd(ItemStack stack, IUpgradableBlock te, Collection<ItemStack> p2) {
		return p2;
	}

	@Override
	public int getExtraEnergyStorage(ItemStack stack, IUpgradableBlock te) {
		int base = this.base.getExtraEnergyStorage(super.getUnCompressedStack(), te);
		return (int) MCSUtil.item.getMetaValue(base, stack);
	}

	@Override
	public double getEnergyStorageMultiplier(ItemStack stack, IUpgradableBlock te) {
		return 1.0;
	}
}
