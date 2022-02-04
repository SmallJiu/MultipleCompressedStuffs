//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.util.base;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.util.init.MCSItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemNormal extends Item implements IHasModel {
	
	protected String name;
	protected CreativeTabs tab;
	
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
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModel() {
		new RegisterModel(MCS.MODID).registerItemModel(this, "normal/items", this.name);
	}
}
