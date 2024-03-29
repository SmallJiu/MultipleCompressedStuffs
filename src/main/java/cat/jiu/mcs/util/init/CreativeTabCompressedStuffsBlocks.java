package cat.jiu.mcs.util.init;

import java.util.Random;

import cat.jiu.core.util.timer.MillisTimer;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.ModSubtypes;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CreativeTabCompressedStuffsBlocks extends CreativeTabs {
	public CreativeTabCompressedStuffsBlocks() {
		super("compressed_stuffs_blocks");
	}

	@Override
	public ResourceLocation getBackgroundImage() {
		if(Configs.Tooltip_Information.can_custom_creative_tab_background) {
			return new ResourceLocation(MCS.MODID + ":textures/gui/container/creative_inventory/tab_items.png");
		}else {
			return super.getBackgroundImage();
		}
	}
	static final Random rand = new Random();
	private MillisTimer time = new MillisTimer(5,0).start();
	private ItemStack stack;
	
	@Override
	public ItemStack getIconItemStack() {
		return getTabIconItem();
	}
	@Override
	public ItemStack getTabIconItem() {
		if(!MCSResources.BLOCKS.isEmpty()) {
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
			Block b = MCSResources.BLOCKS.get(rand.nextInt(MCSResources.BLOCKS.size()));
			if(b instanceof ICompressedStuff) {
				return ((ICompressedStuff) b).getStack(rand.nextInt(ModSubtypes.MAX));
			}
		}
	}
}
