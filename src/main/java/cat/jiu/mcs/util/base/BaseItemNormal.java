package cat.jiu.mcs.util.base;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.util.init.MCSItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BaseItemNormal extends Item implements IHasModel {
	
	protected String name;
	protected CreativeTabs tab;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);
	
	public BaseItemNormal(String name, CreativeTabs tab) {
		this.name = name;
		this.tab = tab;
		this.setHasSubtypes(false);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName(this.name);
		MCSItems.ITEMS.add(this);
		MCSItems.ITEMS_NAME.add(this.name);
	}
	
	@Override
	public void registerItemModel() {
		model.registerItemModel(this, "normal/items", this.name);
	}
}
