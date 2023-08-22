package cat.jiu.mcs.util.init;

import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.core.util.timer.MillisTimer;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.ModSubtypes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CreativeTabCompressedStuffsTools extends CreativeTabs {
	public CreativeTabCompressedStuffsTools() {
		super("compressed_stuffs_tools");
	}

	@Override
	public ResourceLocation getBackgroundImage() {
		if(Configs.Tooltip_Information.can_custom_creative_tab_background) {
			return new ResourceLocation(MCS.MODID + ":textures/gui/container/creative_inventory/tab_items.png");
		}else {
			return super.getBackgroundImage();
		}
	}
	private MillisTimer time = new MillisTimer(5,0).start();
	private ItemStack stack;
	
	@Override
	public ItemStack getIconItemStack() {
		return getTabIconItem();
	}
	@Override
	public ItemStack getTabIconItem() {
		if(!MCSResources.ITEMS.isEmpty()) {
			if(time.isDone()) {
				time.reset();
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
			if(b instanceof BaseItemTool.MetaTool && b instanceof ICompressedStuff) {
				return ((ICompressedStuff) b).getStack(CreativeTabCompressedStuffsBlocks.rand.nextInt(ModSubtypes.MAX));
			}
		}
	}
}
