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
	
	public static final CustomCompressedBlock custom = new CustomCompressedBlock();
	
	public static class CustomCompressedBlock{
		
		@Config.RequiresMcRestart
		@Config.LangKey("config.mcs.name_of_uncompressed_block")
		@Config.Comment("UnFinish !\nList of uncompressed block of custom compressed block name")
		public String[] name_of_uncompressed_block = new String[] { "jiu_tech:data_base", "jiu_tech:magic_dirt" };
		
		@Config.RequiresMcRestart
		@Config.LangKey("config.mcs.enable_test_stuff")
		@Config.Comment("Enable Test Mod Stuffs")
		public boolean enable_test_stuff = false;
		
		public final OreDict register_ore = new OreDict();
		
		public class OreDict{
			@Config.RequiresMcRestart
			@Config.LangKey("config.mcs.ore_dict_register")
			@Config.Comment("register OreDict")
			public String[] ore_dict_register = new String[] {
				"ItemName, ItemMeta, OreDict",
				"minecraft:obsidian|0|obsidian",
				"minecraft:end_stone|0|endstone" };
		}
		
		public final Custem custem_already_stuff = new Custem();
		
		public class Custem {
			@Config.LangKey("config.mcs.logging_give_food")
			@Config.Comment("Player loggedIn give a random food")
			public boolean logging_give_food = true;
			
			public final CustemBlock block = new CustemBlock();
			
			public class CustemBlock{
				@Config.LangKey("config.mcs.custem_change_block")
				@Config.Comment(
						"Custem what the block can change blocks, Use | to split value\n"
						+ "don't need restarting game or rejoin save\n"
						+ "block_name:who block can be change\n"
						+ "meta:meta of who block can change\n"
						+ "change_name:when the block is change,is change or give to block or item name\n"
						+ "amout:change stuff amout,only item valid\n"
						+ "meta:meta of change stuff,if is block,it must be >= 15\n"
						+ "tick:after ticks\n"
						+ "s:after seconds\n"
						+ "m:after minutes\n"
						+ "drop_block:can drop change stuff,only block valid")
				public String[] custem_change_block = new String[] { 
					"block_name, meta, change_name, amout, meta, tick, s, m, drop_block?",
					"compressed_ghast_tear|0|minecraft:diamond_block|99|0|0|2|0|true",
					"compressed_ender_pearl|1|minecraft:diamond|99|0|0|3|0|true"
				};
				
				@Config.LangKey("config.mcs.custem_can_use_wrench_break")
				@Config.Comment("Custem block can use mod wrench to break, Use | to split value\n"
						+ "food_name: food name, don't need 'mcs:'\n"
						+ "meta: food meta")
				public String[] custem_can_use_wrench_break = new String[] { 
					"block_name, meta",
					"compressed_tin_block|3",
					"compressed_bronze_block|3"
				};
				
				@Config.LangKey("config.mcs.custem_break_drop_item")
				@Config.Comment("Custem destroyer Right Click block drop items\n"
						+ "drop items you can add many\n"
						+ "Use # and | to split value\n"
						
						+ "block_name: block name, need modid\n"
						+ "meta: food meta\n"
						+ "dName: drop item name, need modid\n"
						+ "dAmout: drop item amout\n"
						+ "aMeta: drop item meta")
				public String[] custem_break_drop_item = new String[] { 
					"block_name, meta, dName, dAmout, dMeta,...,...",
					"mcs:compressed_tin_block#3#mcs:compressed_beef|6|1|mcs:compressed_beef|5|9",
					"minecraft:grass#0#mcs:compressed_beef|1|0|mcs:compressed_beef|5|65535"
				};
			}
			
			public final CustemItem item = new CustemItem();
			
			public class CustemItem{
				@Config.LangKey("config.mcs.custem_eat_effect")
				@Config.Comment("Custem player eat the food give the effects, Use # and | to split value\n"
						+ "food_name: food name, don't need 'mcs:'\n"
						+ "meta: food meta\n"
						+ "effect_name: effect name, need modid\n"
						+ "time: effect time, measure time by the second, max value is 107374182\n"
						+ "level: effect level")
				public String[] custem_eat_effect = new String[] { 
					"food_name, meta, effects, effect_name, time(seconds), level",
					"compressed_cooked_beef#1#minecraft:luck|107374182|2|minecraft:speed|10|2",
					"compressed_cooked_beef#3#minecraft:speed|10|2|minecraft:luck|107374182|2"
				};
				
				@Config.LangKey("config.mcs.can_drop_cat_hair")
				@Config.Comment("set cat drop hair")
				public boolean can_drop_cat_hair = true;
				
				@Config.RangeDouble(min = 0.001D, max = 0.999D)
				@Config.LangKey("config.mcs.drop_cat_hair_chance")
				@Config.Comment("set cat drop hair chance")
				public double drop_cat_hair_chance = 0.950D;
				
				@Config.RangeDouble(min = 0.1D, max = 0.9D)
				@Config.LangKey("config.mcs.break_bedrock_chance")
				@Config.Comment("set cat hammer break bedrock chance")
				public double break_bedrock_chance = 0.1D;
			}
		}
	}
	
	public static TooltipInformation tooltip_information = new TooltipInformation(); 
	
	public static class TooltipInformation {
		
		public final CustemnInformation custem_info = new CustemnInformation();
		
		public class CustemnInformation {
			@Config.LangKey("config.mcs.custem_info.item.food")
			@Config.Comment("Custem Item Tooltip Information, Use # to split value, Use | to create new line\n"
					+ "name: name for item, don't need 'mcs:'\n"
					+ "meta: item meta\n"
					+ "info: custem information, please use | to create new line")
			public String[] item_food = new String[] { 
				"name, meta, info",
				"compressed_cooked_beef#2#Hi, this is custem information,|You can use config to change it.",
				"compressed_cooked_beef#0#Hi, this is custem information,|You can use config to change it."
			};
			
			@Config.LangKey("config.mcs.custem_info.item")
			@Config.Comment("Custem Item Tooltip Information, Use # to split value, Use | to create new line\n"
					+ "name: name for item, don't need 'mcs:'\n"
					+ "meta: item meta\n"
					+ "info: custem information, please use | to create new line")
			public String[] item = new String[] { 
				"name, meta, info",
				"compressed_plate_iron#2#Hi, this is custem information,|You can use config to change it.",
				"compressed_plate_iron#1#Hi, this is custem information,|You can use config to change it."
			};
			
			@Config.LangKey("config.mcs.custem_info.block")
			@Config.Comment("Custem Block Tooltip Information, Use # to split value, Use | to create new line\n"
					+ "name: name for block, don't need 'mcs:'\n"
					+ "meta: block meta\n" 
					+ "info: custem information, please use | to create new line")
			public String[] block = new String[] { 
				"name, meta, info",
				"compressed_bone_block#1#Hi, this is custem information,|You can use config to change it.",
				"compressed_diamond_block#3#Hi, this is custem information,|You can use config to change it."
			};
		}
		
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
	
	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MCS.MODID)) {
			ConfigManager.sync(MCS.MODID, Config.Type.INSTANCE);
		}
	}
}
