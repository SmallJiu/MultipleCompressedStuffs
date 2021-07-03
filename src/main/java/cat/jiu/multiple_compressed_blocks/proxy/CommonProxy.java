package cat.jiu.multiple_compressed_blocks.proxy;

import cat.jiu.multiple_compressed_blocks.server.CreativeTabCompressedBlocks;
import cat.jiu.multiple_compressed_blocks.server.init.InitBlock;
import cat.jiu.multiple_compressed_blocks.server.init.InitItem;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		PreInit.preInit();
		
		new InitItem();
		new InitBlock();
		new CreativeTabCompressedBlocks.InitCreativeTabs();
	}
}
