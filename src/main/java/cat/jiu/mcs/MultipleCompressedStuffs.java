package cat.jiu.mcs;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import cat.jiu.core.JiuCore.LogOS;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.helpers.DayUtils;
import cat.jiu.mcs.blocks.net.NetworkHandler;
import cat.jiu.mcs.command.MCSCommand;
import cat.jiu.mcs.proxy.CommonProxy;
import cat.jiu.mcs.util.init.*;

import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.SimpleStack;
import net.minecraft.advancements.CriteriaTriggers;
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
	dependencies = "required-after:jiucore@[" + MCS.JIUCORE_VERSION + "," + MCS.JIUCORE_MAIN_VERSION + "-20221230125959" + "];" + "after:thermalfoundation;" + "after:projecte;" + "after:botania;" + "after:draconicevolution;" + "after:environmentaltech;" + "after:tconstruct;" + "after:avaritia;" + "after:ic2;" + "after:appliedenergistics2",
	acceptedMinecraftVersions = "[1.12.2]")
public class MultipleCompressedStuffs {
	protected static final Logger logger = LogManager.getLogger(MCS.MODID);
	public final LogOS log = new LogOS(logger);
	public static final String MODID = "mcs";
	public static final String NAME = "MultipleCompressedStuffs";
	public static final String OWNER = "small_jiu";
	protected static final String JIUCORE_MAIN_VERSION = "1.0.9";
	public static final String JIUCORE_VERSION = JIUCORE_MAIN_VERSION + "-20220407201518";
	public static final String VERSION = "3.0.1-20220322000126";
	public final boolean test_model = false; // if is IDE, you can set to 'true' to enable some test stuff
	public static final CreativeTabs COMPERESSED_BLOCKS = new CreativeTabCompressedStuffsBlocks();
	public static final CreativeTabs COMPERESSED_ITEMS = new CreativeTabCompressedStuffsItems();
	public static final CreativeTabs COMPERESSED_TOOLS = new CreativeTabCompressedStuffsTools();
	public static final JsonObject Textures;
	static {
		long time = System.currentTimeMillis();
		JsonObject obj = null;
		try {
			obj = new JsonParser().parse(new InputStreamReader(MCS.class.getResourceAsStream("/assets/mcs/textures/mode_textures.json"), StandardCharsets.UTF_8)).getAsJsonObject();
		}catch(JsonIOException e) {
			e.printStackTrace();
		}catch(JsonSyntaxException e) {
			e.printStackTrace();
		}
		Textures = obj;
		logger.info("Format Textures json. (took " + (System.currentTimeMillis() - time) + " ms)");
	}
	@Mod.Instance(
		value = MCS.MODID,
		owner = MCS.OWNER)
	public static MultipleCompressedStuffs instance = new MultipleCompressedStuffs();

	@SidedProxy(
		clientSide = "cat.jiu.mcs.proxy.ClientProxy",
		serverSide = "cat.jiu.mcs.proxy.CommonProxy",
		modId = MCS.MODID)
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
		NetworkHandler.registerMessages();
		CriteriaTriggers.register(CraftCompressedStuffTrigger.instance);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	public static long startmodel = 0L;

	@Mod.EventHandler
	public void onLoadComplete(FMLLoadCompleteEvent event) {
		this.log.info("");

		this.log.info("Register Blocks (took " + (proxy.startblock - proxy.startcustom) + " ms)");
		this.log.info("Register Custom Entry (took " + proxy.startcustom + " ms)");
		this.log.info("Register Items (took " + proxy.startitem + " ms)");
		this.log.info("Register Models (took " + startmodel + " ms)");
		this.log.info("Register OreDictionarys (took " + proxy.startore + " ms)");
		this.log.info("Register Recipes (took " + proxy.startrecipe + " ms)");
		this.log.info("Load Complete (took " + (proxy.startblock + proxy.startitem + startmodel + proxy.startore + proxy.startrecipe) + " ms)");

		this.log.info("");

		this.log.info("###########################################");
		this.log.info("#                                         #");
		this.log.info("# MultipleCompressedStuffs Load Complete. #");
		this.log.info("#                                         #");
		this.log.info("#             " + this.getDayOfVersion() + "              #");
		this.log.info("#                                         #");
		this.log.info("###########################################");
	}

	private String getDayOfVersion() {
		DayUtils day = JiuUtils.day;
		return day.getYear() + "" + (day.getMonth() < 10 ? "0" + day.getMonth() : day.getMonth() + "") + (day.getDayOfMonth() < 10 ? "0" + day.getDayOfMonth() : day.getDayOfMonth() + "") + (day.getHour() < 10 ? "0" + day.getHour() : day.getHour() + "") + (day.getMinutes() < 10 ? "0" + day.getMinutes() : day.getMinutes() + "") + (day.getSecond() < 10 ? "0" + day.getSecond() : day.getSecond() + "");
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
