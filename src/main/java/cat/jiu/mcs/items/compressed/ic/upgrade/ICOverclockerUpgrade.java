package cat.jiu.mcs.items.compressed.ic.upgrade;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import ic2.api.upgrade.IProcessingUpgrade;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.UpgradableProperty;
import ic2.api.upgrade.UpgradeRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ICOverclockerUpgrade extends BaseCompressedItem implements IProcessingUpgrade {
	protected static final DecimalFormat decimalformat = new DecimalFormat("0.##");
	
	protected final IProcessingUpgrade base;
	
	public ICOverclockerUpgrade(String name, ItemStack baseItem) {
		super(name, baseItem);
		if(baseItem.getItem() instanceof IProcessingUpgrade) {
			this.base = (IProcessingUpgrade) baseItem.getItem();
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
		tooltip.add(I18n.format("ic2.tooltip.upgrade.overclocker.time", decimalformat.format(100.0 * Math.pow(this.getProcessTimeMultiplier(stack, null), stack.getCount()))));
        tooltip.add(I18n.format("ic2.tooltip.upgrade.overclocker.power", decimalformat.format(100.0 * Math.pow(this.getEnergyDemandMultiplier(stack, null), stack.getCount()))));
		super.addInformation(stack, world, tooltip, advanced);
	}
	
	@Override
	public boolean isSuitableFor(ItemStack p0, Set<UpgradableProperty> p1) {
		return this.base.isSuitableFor(getUnCompressedStack(), p1);
	}
	@Override
	public boolean onTick(ItemStack p0, IUpgradableBlock p1) {
		return false;
	}
	@Override
	public Collection<ItemStack> onProcessEnd(ItemStack p0, IUpgradableBlock p1, Collection<ItemStack> p2) {
		return p2;
	}
	
	@Override
	public double getProcessTimeMultiplier(ItemStack p0, IUpgradableBlock p1) {
		double base = this.base.getProcessTimeMultiplier(getUnCompressedStack(), p1);
		return Math.max(0, base - MCSUtil.item.getMetaValue(base, p0, 0.179));
	}
	
	@Override
	public double getEnergyDemandMultiplier(ItemStack p0, IUpgradableBlock p1) {
		double base = this.base.getEnergyDemandMultiplier(getUnCompressedStack(), p1);
		return base + MCSUtil.item.getMetaValue(base, p0) * 5;
	}
	
	@Override
	public int getExtraProcessTime(ItemStack p0, IUpgradableBlock p1) {
		return 0;
	}
	@Override
	public int getExtraEnergyDemand(ItemStack p0, IUpgradableBlock p1) {
		return 0;
	}
}
