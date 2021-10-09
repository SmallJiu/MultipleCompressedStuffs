package cat.jiu.mcs.util.init;

import java.util.Random;

import cat.jiu.mcs.MultipleCompressedStuffs;
import cat.jiu.mcs.config.Configs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CreativeTabCompressedStuffsItems extends CreativeTabs{
	
	public CreativeTabCompressedStuffsItems() {
		super("compressed_stuffs_items");
		
	}

	@Override
	public ResourceLocation getBackgroundImage() {
		if(Configs.tooltip_information.can_custom_creative_tab_background) {
			return new ResourceLocation(MultipleCompressedStuffs.MODID + ":textures/gui/container/creative_inventory/tab_items.png");
		}else {
			return super.getBackgroundImage();
		}
	}

	@Override
	public ItemStack getTabIconItem() {
		Random rand = new Random();
		
		if(!MCSItems.ITEMS.isEmpty()) {
			int item = rand.nextInt(MCSItems.ITEMS.size());
				
			return new ItemStack(MCSItems.ITEMS.get(item), 1, rand.nextInt(15));
		}else{
			return this.getTabIconItem();
		}
	}
}
