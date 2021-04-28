package cat.jiu.multiple_compressed_blocks.server;

import java.util.Random;

import cat.jiu.multiple_compressed_blocks.server.init.InitBlock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCompressedBlocks extends CreativeTabs{
	
	public CreativeTabCompressedBlocks(String name) {
		super(name);
	}
	
	@Override
	public ItemStack getTabIconItem() {
		Random rand = new Random(15);
		int blocks = rand.nextInt(InitBlock.BLOCKS.size());
		
		return new ItemStack(InitBlock.BLOCKS.get(blocks), 1, rand.nextInt(15));
	}
	
	public static class InitCreativeTabs {
		public static final CreativeTabs COMPERESSED_BLOCKS = new CreativeTabCompressedBlocks("compressed_block");
	}
}
