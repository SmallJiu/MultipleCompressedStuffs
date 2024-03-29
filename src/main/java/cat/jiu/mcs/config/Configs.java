package cat.jiu.mcs.config;

import cat.jiu.mcs.MCS;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(
	modid = MCS.MODID,
	name = "jiu/" + MCS.MODID + "/main",
	category = "config_main")
@Config.LangKey("config.mcs.main")
@Mod.EventBusSubscriber(modid = MCS.MODID)
public class Configs {
	@Config.LangKey("config.mcs.recipe.all")
	@Config.Comment("Enable Default Recipe")
	@Config.RequiresMcRestart
	public static boolean use_default_recipes = true;

	@Config.LangKey("config.mcs.recipe.ore")
	@Config.Comment("Enable Default Recipe")
	@Config.RequiresMcRestart
	public static boolean use_default_oredict = true;

	@Config.LangKey("config.mcs.recipe.3x3")
	@Config.Comment("Use 3x3 Recipes, if is 'false', will use 2x2 recipes")
	@Config.RequiresMcRestart
	public static boolean use_3x3_recipes = true;

	@Config.LangKey("config.mcs.recipe.infinite")
	@Config.Comment("enable infinite compressed state recipe")
	@Config.RequiresMcRestart
	public static boolean enable_infinite_recipe = false;
	
	@Config.LangKey("config.mcs.max_meta")
	@Config.Comment("set max compressed stuff meta")
	@Config.RangeInt(min = 4, max = Short.MAX_VALUE-2)
	@Config.RequiresMcRestart
	public static int Max_Compressed_Stuff = 16;

	@Config.LangKey("config.mcs.recipe.cancel_oredict_for_recipe")
	@Config.Comment("OreDictionary of not involved in recipes")
	@Config.RequiresMcRestart
	public static String[] cancel_oredict_for_recipe = {
			"blockMetal", "blockGlowstone", "blockGlowstone", "cropBeetroot", "blockWoolWhite",
			"woolWhite", "blockWool", "leadHardenedGlass", "listAllmeatcooked", "fish",
			"dye", "dyeWhite", "clathrateEnder", "clathrateGlowstone", "clathrateRedstone",
			"clathrateOil", "machineBlockCasing", "machineBlockAdvanced", "machineBlockAdvancedCasing", "sandstone",
			"dyeBrown", "dyeGreen", "dyeBlack", "chest", "sandstone",
			"dirt", "chestWood"
	};
	
	@Config.LangKey("config.mcs.custom")
	@Config.Comment("Custon stuff")
	public static final CustomCompressed Custom = new CustomCompressed();
	
	@Config.LangKey("config.mcs.tooltip")
	@Config.Comment("Tooltip Information")
	public static final TooltipInformation Tooltip_Information = new TooltipInformation();
//	public static final MaxCompressed Max_Compressed = new MaxCompressed();
	
	public static class MaxCompressed {
		@Config.LangKey("config.mcs.max_meta")
		@Config.Comment("set max compressed stuff meta")
		@Config.RangeInt(min = 8, max = Short.MAX_VALUE-2)
		@Config.RequiresMcRestart
		public int Max_Compressed_Stuff = 16;
		
		@Config.LangKey("config.mcs.max_meta.enable")
		@Config.Comment("set max compressed stuff meta")
		@Config.RequiresMcRestart
		public String[] Enable_Max_Compressed = {};
	}
	
	public static class TooltipInformation {
		@Config.LangKey("config.mcs.show_owner_type")
		@Config.Comment("show the item owner type")
		public boolean show_owner_type = true;

		@Config.LangKey("config.mcs.can_custom_tab_background")
		@Config.Comment("set can custom creative_tab background")
		@Config.RequiresMcRestart
		public boolean can_custom_creative_tab_background = false;

		@Config.LangKey("config.mcs.show_food_amount")
		@Config.Comment("show Food Amount in Tooltip Information")
		public boolean show_food_amount = false;

		@Config.LangKey("config.mcs.get_real_food_amout")
		@Config.Comment("get real food amout")
		@Config.RequiresMcRestart
		public boolean get_actual_food_amout = false;

		@Config.LangKey("config.mcs.show_oredict")
		@Config.Comment("show oredict in Tooltip Information")
		public boolean show_oredict = false;

		@Config.LangKey("config.mcs.show_burn_time")
		@Config.Comment("Show Burn Time in Tooltip Information")
		public boolean show_burn_time = false;

		@Config.LangKey("config.mcs.show_owner_mod")
		@Config.Comment("Show Owner Mod in Tooltip Information")
		public boolean show_owner_mod = true;

		@Config.LangKey("config.mcs.show_specific_number")
		@Config.Comment("Show Specific Number of unCompressedItem in Tooltip Information")
		public boolean show_specific_number = true;

		@Config.LangKey("config.mcs.can_custom_specific_number")
		@Config.Comment("Can Custom Specific Number of unCompressedItem in Tooltip Information")
		public boolean can_custom_specific_number = false;

		@Config.LangKey("config.mcs.waila.remaining_time")
		@Config.Comment("static to show the change block remaining time")
		public boolean Static_Show_Change_Block_Remaining_Time = true;
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(MCS.MODID)) {
			ConfigManager.sync(MCS.MODID, Config.Type.INSTANCE);
		}
	}
}
