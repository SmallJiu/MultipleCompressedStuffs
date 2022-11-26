package cat.jiu.mcs.util.init;

import cat.jiu.core.api.ITimer;
import cat.jiu.core.util.timer.Timer;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CreativeTabCompressedStuffsItems extends CreativeTabs {

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
	private ITimer time = new Timer(0,15,0);
	private ItemStack stack;
	
	@Override
	public ItemStack getIconItemStack() {
		return getTabIconItem();
	}
	@Override
	public ItemStack getTabIconItem() {
		if(!MCSResources.ITEMS.isEmpty()) {
			time.update();
			if(time.getTicks()<=0) {
				time.setTicks(Timer.parseTick(15, 0));
				this.stack = getStack();
			}
			if(this.stack==null || this.stack.isEmpty()) 
				this.stack = getStack();
		}else {
			if(this.stack==null || this.stack.isEmpty()) 
				this.stack = new ItemStack(Blocks.BEDROCK);
		}
		return this.stack;
	}
	private ItemStack getStack() {
		while(true) {
			Item b = MCSResources.ITEMS.get(CreativeTabCompressedStuffsBlocks.rand.nextInt(MCSResources.ITEMS.size()));
			if(b instanceof BaseCompressedItem) {
				return ((BaseCompressedItem) b).getStack(CreativeTabCompressedStuffsBlocks.rand.nextInt(15));
			}
		}
	}
}
