package cat.jiu.mcs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cat.jiu.core.JiuCore.LogOS;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.helpers.DayUtils;
import cat.jiu.mcs.command.MCSCommand;
import cat.jiu.mcs.proxy.CommonProxy;
import cat.jiu.mcs.util.init.*;

import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.SimpleStack;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
	modid = MCS.MODID,
	name = MCS.NAME,
	version = MCS.VERSION,
	useMetadata = true,
	guiFactory = "cat.jiu.mcs.config.ConfigGuiFactory",
	dependencies =
		  "required-after:jiucore@[1.0.7-20220201000000,);"
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
	public static final String VERSION = "2.9.4-20220201000000";
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
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Throwable {
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public static long startmodel = 0L;
	
	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		this.log.info("");
		
		this.log.info("Start Register Items");
		this.log.info("Register Items Successful, " + "(took " + proxy.startitem + " ms)");
		
		this.log.info("Start Register Blocks");
		this.log.info("Register Blocks Successful, " + "(took " + proxy.startblock + " ms)");
		
		this.log.info("Start Register Models");
		this.log.info("Register Models Successful, " + "(took " + startmodel + " ms)");
		
		this.log.info("Start Register OreDictionarys");
		this.log.info("Register OreDictionarys Successful, " + "(took " + proxy.startore + " ms)");
		
		this.log.info("Start Register Recipes");
		this.log.info("Register Recipes Successful, " + "(took " + proxy.startrecipe + " ms)");
		
		this.log.info("");
		
		this.log.info("###########################################");
		this.log.info("#                                         #");
		this.log.info("# MultipleCompressedStuffs Load Complete. #");
		this.log.info("#                                         #");
		this.log.info("#             "+this.getDayOfVersion()+"              #");
		this.log.info("#                                         #");
		this.log.info("###########################################");
	}
	
	private String getDayOfVersion() {
		DayUtils day = JiuUtils.day;
		return day.getYear()+""+
				(day.getMonth() < 10 ? "0"+day.getMonth() : day.getMonth()+"")+
				(day.getDayOfMonth() < 10 ? "0"+day.getDayOfMonth() : day.getDayOfMonth()+"")+
				(day.getHour() < 10 ? "0"+day.getHour() : day.getHour()+"")+
				(day.getMinutes() < 10 ? "0"+day.getMinutes() : day.getMinutes()+"")+
				(day.getSecond() < 10 ? "0"+day.getSecond() : day.getSecond()+"");
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