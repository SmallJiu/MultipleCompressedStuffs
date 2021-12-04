package cat.jiu.mcs.util.init;

import java.util.Random;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CreativeTabCompressedStuffsBlocks extends CreativeTabs{
	
	public CreativeTabCompressedStuffsBlocks() {
		super("compressed_stuffs_blocks");
		
	}

	@Override
	public ResourceLocation getBackgroundImage() {
		if(Configs.tooltip_information.can_custom_creative_tab_background) {
			return new ResourceLocation(MCS.MODID + ":textures/gui/container/creative_inventory/tab_items.png");
		}else {
			return super.getBackgroundImage();
		}
	}

	@Override
	public ItemStack getTabIconItem() {
		Random rand = new Random();
		
		if(!MCSBlocks.BLOCKS.isEmpty()) {
			int block = rand.nextInt(MCSBlocks.BLOCKS.size());
				
			return new ItemStack(MCSBlocks.BLOCKS.get(block), 1, rand.nextInt(15));
		}else{
			return this.getTabIconItem();
		}
	}
}
