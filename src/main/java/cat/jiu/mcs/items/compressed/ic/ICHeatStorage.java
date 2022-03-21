package cat.jiu.mcs.items.compressed.ic;

import java.util.List;

import ic2.api.reactor.IReactor;
import ic2.core.init.Localization;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ICHeatStorage extends ICReactorAssembly {
	public ICHeatStorage(String name, String baseItem) {
		this(name, baseItem, 0);
	}
	
	public ICHeatStorage(String name, String baseItem, int meta) {
		this(name, new ItemStack(Item.getByNameOrId(baseItem), 1, meta));
	}
	
	public ICHeatStorage(String name, ItemStack baseItem) {
		super(name, baseItem);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, world, tooltip, advanced);
		if(this.getCustomDamage(stack) > 0) {
			tooltip.add(Localization.translate("ic2.reactoritem.heatwarning.line1"));
			tooltip.add(Localization.translate("ic2.reactoritem.heatwarning.line2"));
		}
	}

	@Override
	public boolean canStoreHeat(ItemStack p0, IReactor p1, int p2, int p3) {
		return true;
	}
	
	@Override
	public int getMaxHeat(ItemStack p0, IReactor p1, int p2, int p3) {
		return this.getMaxCustomDamage(p0);
	}
	
	@Override
	public int getCurrentHeat(ItemStack p0, IReactor p1, int p2, int p3) {
		return this.getCustomDamage(p0);
	}
	
	@Override
	public int alterHeat(ItemStack stack, IReactor reactor, int x, int y, int heat) {
		int myHeat = this.getCurrentHeat(stack, reactor, x, y);
		myHeat += heat;
		
		int max = this.getMaxHeat(stack, reactor, x, y);
		if(myHeat > max) {
			reactor.setItemAt(x, y, null);
			heat = max - myHeat + 1;
		}else {
			if(myHeat < 0) {
				heat = myHeat;
				myHeat = 0;
			}else {
				heat = 0;
			}
			this.setCustomDamage(stack, myHeat);
		}
		return heat;
	}
}
