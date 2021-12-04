package cat.jiu.mcs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cat.jiu.core.JiuCore.LogOS;
import cat.jiu.core.util.JiuCoreEvents;
import cat.jiu.mcs.blocks.net.GuiHandler;
import cat.jiu.mcs.blocks.net.TileEntityCompressor;
import cat.jiu.mcs.command.MCSCommand;
import cat.jiu.mcs.proxy.CommonProxy;
import cat.jiu.mcs.recipes.MCSRecipe;
import cat.jiu.mcs.util.CatEvent;
import cat.jiu.mcs.util.TestModel;
import cat.jiu.mcs.util.TileEntityChangeBlock;
import cat.jiu.mcs.util.init.*;
import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.SimpleStack;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
	modid = MCS.MODID,
	name = MCS.NAME,
	version = MCS.VERSION,
	useMetadata = true,
	guiFactory = "cat.jiu.mcs.config.ConfigGuiFactory",
	dependencies =
		  "required-after:jiucore@[1.0.3-202112050055,);"
		+ "after:thermalfoundation;"
		+ "after:projecte;"
		+ "after:botania;"
		+ "after:draconicevolution;"
		+ "after:environmentaltech;"
		+ "after:tconstruct;"
		+ "after:avaritia",
	acceptedMinecraftVersions = "[1.12.2]"
)
public class MultipleCompressedStuffs {
	protected static final Logger logger = LogManager.getLogger(MCS.MODID);
	public final LogOS log = new LogOS(logger);
	public static final String MODID = "mcs";
	public static final String NAME = "MultipleCompressedStuffs";
	public static final String OWNER = "small_jiu";
	public static final String VERSION = "2.9.3-202112050155";
	public final boolean test_model = false; // if is IDE, you can set to 'true' to enable some test stuff
	public static final CreativeTabs COMPERESSED_BLOCKS = new CreativeTabCompressedStuffsBlocks();
	public static final CreativeTabs COMPERESSED_ITEMS = new CreativeTabCompressedStuffsItems();
	
	@Mod.Instance(value = MCS.MODID, owner = MCS.OWNER)
	public static MultipleCompressedStuffs instance = new MultipleCompressedStuffs();
	
	@SidedProxy(
		clientSide = "cat.jiu.mcs.proxy.ClientProxy",
		serverSide = "cat.jiu.mcs.proxy.CommonProxy",
		modId = MCS.MODID
	)
	public static CommonProxy proxy;
	
	long startitem = 0L;
	long startblock = 0L;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		GuiHandler.register();
		
		JiuCoreEvents.addEvent(new CatEvent());
		JiuCoreEvents.addEvent(new TestModel());
		
		startitem = System.currentTimeMillis();
		new MCSItems();
		MCSItems.normal.CAT_HAMMER.setMaxStackSize(1);
		startitem = System.currentTimeMillis() - startitem;
		
		startblock = System.currentTimeMillis();
		new MCSBlocks();
		startblock = System.currentTimeMillis() - startblock;
		
		GameRegistry.registerTileEntity(TileEntityChangeBlock.class, new ResourceLocation(MCS.MODID + ":" + "change_block"));
		GameRegistry.registerTileEntity(TileEntityCompressor.class, new ResourceLocation(MCS.MODID + ":" + "compressor"));
	}
	
	long startore = 0L;
	long startrecipe = 0L;
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
		
		startore = System.currentTimeMillis();
		try {
			MCSOreDict.register();
		} catch (Exception e) {e.printStackTrace();}
		
		startore = (System.currentTimeMillis() - startore);
		
		startrecipe = System.currentTimeMillis();
		MCSRecipe.register();
		startrecipe = (System.currentTimeMillis() - startrecipe);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public static long startmodel = 0L;
	
	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		this.log.info("");
		
		this.log.info("Start Register Items");
		this.log.info("Register Items Successful, " + "(took " + startitem + " ms)");
		
		this.log.info("Start Register Blocks");
		this.log.info("Register Blocks Successful, " + "(took " + startblock + " ms)");
		
		this.log.info("Start Register Models");
		this.log.info("Register Models Successful, " + "(took " + startmodel + " ms)");
		
		this.log.info("Start Register OreDictionarys");
		this.log.info("Register OreDictionarys Successful, " + "(took " + startore + " ms)");
		
		this.log.info("Start Register Recipes");
		this.log.info("Register Recipes Successful, " + "(took " + startrecipe + " ms)");
		
		this.log.info("");
		
		this.log.info("###########################################");
		this.log.info("#                                         #");
		this.log.info("# MultipleCompressedStuffs Load Complete. #");
		this.log.info("#                                         #");
		this.log.info("###########################################");
	}
	
	@Mod.EventHandler // 服务器启动中
	public void onServerStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new MCSCommand());
		
		if(Loader.isModLoaded("projecte")) {
			if(this.test_model) {
				EMCMapper.emc.put(new SimpleStack(new ItemStack(MCSItems.normal.CAT_HAIR)), Long.MAX_VALUE);
			}else {
				EMCMapper.emc.put(new SimpleStack(new ItemStack(MCSItems.normal.CAT_HAIR)), 1024L);
			}
			EMCMapper.emc.put(new SimpleStack(new ItemStack(MCSItems.normal.CAT_INGOT)), 13376L);
			EMCMapper.emc.put(new SimpleStack(new ItemStack(MCSItems.normal.CAT_HAMMER)), 68448L);
		}
	}
	
	public static Logger getLogger() {
		return logger;
	}
}