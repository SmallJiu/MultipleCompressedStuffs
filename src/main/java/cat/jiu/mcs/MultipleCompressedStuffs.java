package cat.jiu.mcs;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

import cat.jiu.core.JiuCore.LogOS;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.helpers.DayUtils;
import cat.jiu.mcs.command.MCSCommand;
import cat.jiu.mcs.proxy.ServerProxy;
import cat.jiu.mcs.util.init.*;

import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
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
	  "required-after:jiucore@[" + MCS.JIUCORE_VERSION + "," + MCS.JIUCORE_MAIN_VERSION + "-20221230125959" + "];"
	+ "after:thermalfoundation;"
	+ "after:projecte;"
	+ "after:botania;"
	+ "after:draconicevolution;"
	+ "after:environmentaltech;"
	+ "after:tconstruct;"
	+ "after:avaritia;"
	+ "after:ic2;"
	+ "after:appliedenergistics2;"
	+ "after:torcherino;"
	+ "after:waila",
	acceptedMinecraftVersions = "[1.12.2]")
public class MultipleCompressedStuffs {
	private static Logger logger = LogManager.getLogger(MCS.MODID);
	private static LogOS logos = new LogOS(getLogger());
	public static final String MODID = "mcs";
	public static final String NAME = "MultipleCompressedStuffs";
	public static final String OWNER = "small_jiu";
	protected static final String JIUCORE_MAIN_VERSION = "1.1.0";
	public static final String JIUCORE_VERSION = JIUCORE_MAIN_VERSION + "-20220604025014";
	public static final String VERSION = "3.0.3-20220610234154";
	public static final CreativeTabs COMPERESSED_BLOCKS = new CreativeTabCompressedStuffsBlocks();
	public static final CreativeTabs COMPERESSED_ITEMS = new CreativeTabCompressedStuffsItems();
	public static final CreativeTabs COMPERESSED_TOOLS = new CreativeTabCompressedStuffsTools();
	private static JsonObject Textures = null;
	public static JsonObject getTextures() {return Textures;}
	public static boolean setTextures(JsonObject texture) {
		if(Textures == null) {
			Textures = texture;
			return true;
		}else {
			LogManager.getLogger(MCS.MODID).error("Textures has setup, no need set");
			return false;
		}
	}
	
	private static Boolean isTest = null; // if is IDE, you can set to 'true' to enable some test stuff
	public static final boolean test() {
		if(isTest == null) {
			isTest = new File("./config/jiu/mcs_debug.jiu").exists();
		}
		return isTest;
	}
	
	@Mod.Instance(
		value = MCS.MODID,
		owner = MCS.OWNER)
	public static MultipleCompressedStuffs instance;

	@SidedProxy(
		clientSide = "cat.jiu.mcs.proxy.ClientProxy",
		serverSide = "cat.jiu.mcs.proxy.ServerProxy",
		modId = MCS.MODID)
	public static ServerProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		CriteriaTriggers.register(CraftCompressedStuffTrigger.instance);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	public static long startmodel = 0L;

	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		getLogOS().info("");

		getLogOS().info("Register Blocks (took " + (proxy.startblock - proxy.startcustom) + " ms)");
		getLogOS().info("Register Custom Entry (took " + proxy.startcustom + " ms)");
		getLogOS().info("Register Items (took " + proxy.startitem + " ms)");
		getLogOS().info("Register Models (took " + startmodel + " ms)");
		getLogOS().info("Register OreDictionarys (took " + proxy.startore + " ms)");
		getLogOS().info("Register Recipes (took " + proxy.startrecipe + " ms)");
		getLogOS().info("Load Complete (took " + (proxy.startblock + proxy.startitem + startmodel + proxy.startore + proxy.startrecipe) + " ms)");

		getLogOS().info("");

		getLogOS().info("###########################################");
		getLogOS().info("#                                         #");
		getLogOS().info("# MultipleCompressedStuffs Load Complete. #");
		getLogOS().info("#                                         #");
		getLogOS().info("#             " + this.getDayOfVersion() + "              #");
		getLogOS().info("#                                         #");
		getLogOS().info("###########################################");
	}

	private String getDayOfVersion() {
		DayUtils day = JiuUtils.day;
		return day.getYear() + ""
			+ (day.getMonth() < 10 ? "0" + day.getMonth() : day.getMonth() + "")
			+ (day.getDayOfMonth() < 10 ? "0" + day.getDayOfMonth() : day.getDayOfMonth() + "")
			+ (day.getHour() < 10 ? "0" + day.getHour() : day.getHour() + "")
			+ (day.getMinutes() < 10 ? "0" + day.getMinutes() : day.getMinutes() + "")
			+ (day.getSecond() < 10 ? "0" + day.getSecond() : day.getSecond() + "");
	}

	@Mod.EventHandler // 服务器启动中
	public void onServerStarting(FMLServerStartingEvent event) {
		event.registerServerCommand(new MCSCommand());

		if(Loader.isModLoaded("projecte")) {
			if(test()) {
				EMCMapper.emc.put(new SimpleStack(new ItemStack(MCSItems.normal.CAT_HAIR)), Long.MAX_VALUE);
			}else {
				EMCMapper.emc.put(new SimpleStack(new ItemStack(MCSItems.normal.CAT_HAIR)), 1024L);
			}
			EMCMapper.emc.put(new SimpleStack(new ItemStack(MCSItems.normal.CAT_INGOT)), 13376L);
			EMCMapper.emc.put(new SimpleStack(new ItemStack(MCSItems.normal.CAT_HAMMER)), 68448L);
		}
	}
	
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getLogger(MCS.MODID);;
		}
		return logger;
	}
	
	public static LogOS getLogOS() {
		if(logos == null) {
			logos = new LogOS(getLogger());
		}
		return logos; 
	}
}
