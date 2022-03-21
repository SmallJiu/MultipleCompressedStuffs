package cat.jiu.mcs.util.init;

import java.util.Random;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CreativeTabCompressedStuffsItems extends CreativeTabs{
	
	public CreativeTabCompressedStuffsItems() {
		super("compressed_stuffs_items");
		
	}

	@Override
	public ResourceLocation getBackgroundImage() {
		if(Configs.Tooltip_Information.can_custom_creative_tab_background) {
			return new ResourceLocation(MCS.MODID + ":textures/gui/container/creative_inventory/tab_items.png");
		}else {
			return super.getBackgroundImage();
		}
	}

	@Override
	public ItemStack getTabIconItem() {
		Random rand = new Random();
		
		if(!MCSResources.SUB_ITEMS.isEmpty()) {
			int item = rand.nextInt(MCSResources.SUB_ITEMS.size());
			return new ItemStack(MCSResources.SUB_ITEMS.get(item), 1, rand.nextInt(15));
		}else{
			return new ItemStack(Items.APPLE);
		}
	}
}
