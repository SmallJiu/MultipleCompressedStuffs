package cat.jiu.multiple_compressed_blocks.server.util.base;

import cat.jiu.multiple_compressed_blocks.server.init.InitItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BaseItem extends Item {
	
	protected String name;
	protected CreativeTabs tab;
	
	public BaseItem(String name, CreativeTabs tab, boolean hasSubtypes) {
		this.name = name;
		this.tab = tab;
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcb." + this.name);
		this.setCreativeTab(this.tab);
		ForgeRegistries.ITEMS.register(this.setRegistryName(this.name));
		InitItem.ITEMS.add(this);
	}
}
