package cat.jiu.mcs.util.base;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class BaseCreativeTab extends CreativeTabs{
	
	protected final ItemStack icon; 
	protected final boolean hasSearchBar;
	
	public BaseCreativeTab(String name, ItemStack icon, boolean hasSearchBar) {
		super(name);
		this.icon = icon;
		this.hasSearchBar = hasSearchBar;
	}

	@Override
	public ItemStack getTabIconItem() {
		return this.icon;
	}

	@Override
	public boolean hasSearchBar() {
		return this.hasSearchBar;
	}
}
