package cat.jiu.mcs.util.base;

import cat.jiu.mcs.MCS;

import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.core.api.IHasModel;
import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
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
		RegisterModel.NeedToRegistryModel.add(this);
		MCSResources.ITEMS.add(this);
		MCSResources.ITEMS_NAME.add(this.name);
	}

	List<String> I18nInfo = Lists.newArrayList();

	public BaseItemNormal addI18nInfo(String... keys) {
		for(String key : keys) {
			this.I18nInfo.add(key);
		}
		return this;
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flagIn) {
		if(!I18nInfo.isEmpty()) {
			for(String key : I18nInfo) {
				tooltip.add(I18n.format(key));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getItemModel() {
		new RegisterModel(MCS.MODID).registerItemModel(this, "normal/items", this.name);
	}
}
