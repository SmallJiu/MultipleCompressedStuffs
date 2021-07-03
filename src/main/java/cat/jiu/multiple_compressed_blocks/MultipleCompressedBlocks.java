package cat.jiu.multiple_compressed_blocks;

import org.apache.logging.log4j.Logger;

import cat.jiu.multiple_compressed_blocks.proxy.CommonProxy;
import cat.jiu.multiple_compressed_blocks.recipes.RecipeWorkBench;
import cat.jiu.multiple_compressed_blocks.server.init.InitOreDict;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
	modid = MultipleCompressedBlocks.MODID,
	name = MultipleCompressedBlocks.NAME,
	version = MultipleCompressedBlocks.VERSION,
	useMetadata = true,
	guiFactory = MultipleCompressedBlocks.GUI_FACTORY_CLASS
)
public class MultipleCompressedBlocks {
	
	private static Logger logger;
	public static final String MODID = "mcb";
	public static final String NAME = "MultipleCompressedBlocks";
	public static final String VERSION = "1.0.0";
	public static final String CLIENT_PROXY_CLASS = "cat.jiu.multiple_compressed_blocks.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "cat.jiu.multiple_compressed_blocks.proxy.CommonProxy";
	public static final String GUI_FACTORY_CLASS = "cat.jiu.multiple_compressed_blocks.config.ConfigGuiFactory";
	
	@Mod.Instance(MultipleCompressedBlocks.MODID)
	public static MultipleCompressedBlocks instance;
	
	@SidedProxy(
		clientSide = MultipleCompressedBlocks.CLIENT_PROXY_CLASS,
		serverSide = MultipleCompressedBlocks.SERVER_PROXY_CLASS
	)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		InitOreDict.register();
		RecipeWorkBench.register();
		proxy.preInit(event);
	}
	
	public static Logger getLogger() {
		return logger;
	}
}
