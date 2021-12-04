package cat.jiu.mcs.config;

import cat.jiu.mcs.MCS;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = MCS.MODID, name = "jiu/" + MCS.MODID + "/main", category = "config_main")
@Config.LangKey("config.mcs.main")
@Mod.EventBusSubscriber(modid = MCS.MODID)
public class Configs {
	
	@Config.LangKey("config.mcs.recipe.all")
	@Config.Comment("Enable All Recipe")
	@Config.RequiresMcRestart
	public static boolean use_default_recipes = true;
	
	@Config.LangKey("config.mcs.recipe.3x3")
	@Config.Comment("Use 3x3 Recipes, if is 'false', will use 2x2 recipes")
	@Config.RequiresMcRestart
	public static boolean use_3x3_recipes = true;
	
	@Config.LangKey("config.mcs.recipe.cancel_oredict_for_recipe")
	@Config.Comment("OreDictionary of not involved in recipes")
	@Config.RequiresMcRestart
	public static String[] cancel_oredict_for_recipe = new String[] {
			"blockMetal", "blockGlowstone", "blockGlowstone", "cropBeetroot", "blockWoolWhite", 
			"woolWhite", "blockWool", "leadHardenedGlass", "listAllmeatcooked", "fish",
			"dye", "dyeWhite", "clathrateEnder", "clathrateGlowstone", "clathrateRedstone", 
			"clathrateOil"
	};
	
	public static final CustomCompressedBlock custom = new CustomCompressedBlock();
	
	public static TooltipInformation tooltip_information = new TooltipInformation(); 
	
	public static class TooltipInformation {
		
		public final CustemnInformation custem_info = new CustemnInformation();
		
		@Config.RequiresMcRestart
		@Config.LangKey("config.mcs.can_custom_tab_background")
		@Config.Comment("set can custom creative_tab background")
		public boolean can_custom_creative_tab_background = false;
		
		@Config.LangKey("config.mcs.show_food_amount")
		@Config.Comment("show Food Amount in Tooltip Information")
		public boolean show_food_amount = true;
		
		@Config.RequiresMcRestart
		@Config.LangKey("config.mcs.get_real_food_amout")
		@Config.Comment("get real food amout")
		public boolean get_actual_food_amout = false;
		
		@Config.LangKey("config.mcs.show_oredict")
		@Config.Comment("show oredict in Tooltip Information")
		public boolean show_oredict = true;
		
		@Config.LangKey("config.mcs.show_burn_time")
		@Config.Comment("Show Burn Time in Tooltip Information")
		public boolean show_burn_time = false;
		
		@Config.LangKey("config.mcs.show_owner_mod")
		@Config.Comment("Show Owner Mod in Tooltip Information")
		public boolean show_owner_mod = false;
		
		@Config.LangKey("config.mcs.show_specific_number")
		@Config.Comment("Show Specific Number of unCompressedItem in Tooltip Information")
		public boolean show_specific_number = true;
		
		@Config.LangKey("config.mcs.can_custom_specific_number")
		@Config.Comment("Can Custom Specific Number of unCompressedItem in Tooltip Information")
		public boolean can_custom_specific_number = false;
	}
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MCS.MODID)) {
			ConfigManager.sync(MCS.MODID, Config.Type.INSTANCE);
		}
	}
}
