//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.util.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brandon3055.draconicevolution.DEFeatures;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import cofh.thermalfoundation.init.TFItems;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.exception.JsonException;
import cat.jiu.mcs.exception.JsonElementNotFoundException;
import cat.jiu.mcs.exception.NonItemException;
import cat.jiu.mcs.exception.UnknownTypeException;
import cat.jiu.mcs.items.*;
import cat.jiu.mcs.items.compressed.CompressedEgg;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseItemFood;
import cat.jiu.mcs.util.type.CustomType;
import cat.jiu.mcs.util.type.PotionEffectType;
import cat.jiu.mcs.util.base.BaseItemNormal;
import cat.jiu.mcs.util.base.BaseItemSub;
import cat.jiu.mcs.util.base.BaseItemTool;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.common.Loader;

@SuppressWarnings("static-access")
public class MCSItems {
	public static final int UNBREAK = -1;
	public static final int INT_MAX = Integer.MAX_VALUE;
	public static final float FLOAT_MAX = Float.MAX_VALUE;
	public static final long LONG_MAX = Long.MAX_VALUE;
	public static final double DOUBLE_MAX = Double.MAX_VALUE;
	
	public static final Map<String, BaseItemSub> SUB_ITEMS_MAP = new HashMap<String, BaseItemSub>();
	public static final Map<String, BaseItemFood> FOODS_MAP = new HashMap<String, BaseItemFood>();
	public static final Map<String, BaseItemTool> TOOLS_MAP = new HashMap<String, BaseItemTool>();
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final List<String> ITEMS_NAME = new ArrayList<String>();
	public static final List<BaseItemFood> FOODS = new ArrayList<BaseItemFood>();
	public static final List<String> FOODS_NAME = new ArrayList<String>();
	public static final List<BaseItemSub> SUB_ITEMS = new ArrayList<BaseItemSub>();
	public static final List<String> SUB_ITEMS_NAME = new ArrayList<String>();
	public static final List<BaseItemTool> TOOLS = new ArrayList<BaseItemTool>();
	public static final List<String> TOOLS_NAME = new ArrayList<String>();
	
	public static final MCSItem normal = new MCSItem();
	public static final MinecraftItem minecraft = new MinecraftItem();
	public static ThermalFoundationItem thermal_foundation = null;
	public static DraconicEvolutionItems draconic_evolution = null;
	public static AvaritiaItems avaritia = null;
	
	static {
		if(Configs.Custom.Enable_Mod_Stuff) {
			try {
				thermal_foundation = Configs.Custom.ModStuff.ThermalFoundation ? new ThermalFoundationItem() : null;
				draconic_evolution = Configs.Custom.ModStuff.DraconicEvolution ? new DraconicEvolutionItems() : null;
				avaritia = Configs.Custom.ModStuff.Avaritia ? new AvaritiaItems() : null;
			} catch (Exception e) {
				MCS.instance.log.error("Has a error, this is message: ");
				MCS.instance.log.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	public MCSItems() {}
	
	public static final void registerOreDict() {
		for(BaseItemFood food : FOODS) {
			JiuUtils.item.registerCompressedOre(food.getUnCompressedName(), food, false);
		}
		for(BaseItemSub item : SUB_ITEMS) {
			JiuUtils.item.registerCompressedOre(item.getUnCompressedName(), item, false);
		}
	}
	
	public static class MinecraftItem {
		public final Normal normal = new Normal();
		public final Food food = new Food();
		
		public class Normal{
			public final BaseItemSub C_STICK_F 					= new BaseItemSub("compressed_stick", new ItemStack(Items.STICK));
			public final BaseItemSub C_REEDS_F 					= new BaseItemSub("compressed_reeds", new ItemStack(Items.REEDS));
			public final BaseItemSub C_BLAZE_POWDER_F 			= new BaseItemSub("compressed_blaze_powder", new ItemStack(Items.BLAZE_POWDER));
			public final BaseItemSub C_GUNPOWDER_F 				= new BaseItemSub("compressed_gunpowder", new ItemStack(Items.GUNPOWDER));
			public final BaseItemSub C_DRAGON_BREATH_F 			= new BaseItemSub("compressed_dragon_breath", new ItemStack(Items.DRAGON_BREATH));
			public final BaseItemSub C_FEATHER_F 				= new BaseItemSub("compressed_feather", new ItemStack(Items.FEATHER));
			public final BaseItemSub C_STRING_F 				= new BaseItemSub("compressed_string", new ItemStack(Items.STRING));
			public final BaseItemSub C_MAGMA_CREAM_F 			= new BaseItemSub("compressed_magma_cream", new ItemStack(Items.MAGMA_CREAM));
			public final BaseItemSub C_GLASS_BOTTLE_F 			= new BaseItemSub("compressed_glass_bottle", new ItemStack(Items.GLASS_BOTTLE));
			public final BaseItemSub C_BOWL_F 					= new BaseItemSub("compressed_bowl", new ItemStack(Items.BOWL));
			public final BaseItemSub C_EGG_F 					= new CompressedEgg("compressed_egg", new ItemStack(Items.EGG));
		}
		
		public class Food {
			public final BaseItemFood C_BEEF_F 					= new BaseItemFood("compressed_beef", Items.BEEF);
			public final BaseItemFood C_COOKED_BEEF_F 			= new BaseItemFood("compressed_cooked_beef", Items.COOKED_BEEF);
			public final BaseItemFood C_CHICKEN_F		 		= new BaseItemFood("compressed_chicken", Items.CHICKEN);
			public final BaseItemFood C_COOKED_CHICKEN_F 		= new BaseItemFood("compressed_cooked_chicken", Items.COOKED_CHICKEN);
			public final BaseItemFood C_POTATO_F 				= new BaseItemFood("compressed_potato", Items.POTATO);
			public final BaseItemFood C_BAKED_POTATO_F 			= new BaseItemFood("compressed_baked_potato", Items.BAKED_POTATO);
			public final BaseItemFood C_RABBIT_F 				= new BaseItemFood("compressed_rabbit", Items.RABBIT);
			public final BaseItemFood C_COOKED_RABBIT_F 		= new BaseItemFood("compressed_cooked_rabbit", Items.COOKED_RABBIT);
			public final BaseItemFood C_PORKCHOP_F 				= new BaseItemFood("compressed_porkchop", Items.PORKCHOP);
			public final BaseItemFood C_COOKED_PORKCHOP_F 		= new BaseItemFood("compressed_cooked_prokchop", Items.COOKED_PORKCHOP);
			public final BaseItemFood C_MUTTON_F 				= new BaseItemFood("compressed_mutton", Items.MUTTON);
			public final BaseItemFood C_COOKED_MUTTON_F 		= new BaseItemFood("compressed_cooked_mutton", Items.COOKED_MUTTON);
			public final BaseItemFood C_FISH_F 					= new BaseItemFood("compressed_fish", Items.FISH);
			public final BaseItemFood C_COOKED_FISH_F 			= new BaseItemFood("compressed_cooked_fish", Items.COOKED_FISH);
			public final BaseItemFood C_SALMON_FISH_F 			= new BaseItemFood("compressed_salmon_fish", Items.FISH, 1);
			public final BaseItemFood C_COOKED_SALMON_FISH_F	= new BaseItemFood("compressed_cooked_salmon_fish", Items.COOKED_FISH, 1);
			public final BaseItemFood C_APPLE_F 				= new BaseItemFood("compressed_apple", Items.APPLE);
			public final BaseItemFood C_BREAD_F 				= new BaseItemFood("compressed_bread", Items.BREAD);
			public final BaseItemFood C_CLOWN_FISH_F 			= new BaseItemFood("compressed_clown_fish", Items.FISH, 2);
			public final BaseItemFood C_COOKIE_F 				= new BaseItemFood("compressed_cookie", Items.COOKIE);
			public final BaseItemFood C_ROTTEN_FLESH_F 			= new BaseItemFood("compressed_rotten_flesh", Items.ROTTEN_FLESH);
			public final BaseItemFood C_CARROT_F 				= new BaseItemFood("compressed_carrot", Items.CARROT);
			public final BaseItemFood C_GOLDEN_CARROT_F 		= new BaseItemFood("compressed_golden_carrot", Items.GOLDEN_CARROT);
			public final BaseItemFood C_PUMPKIN_PIE_F			= new BaseItemFood("compressed_pumpkin_pie", Items.PUMPKIN_PIE);
			public final BaseItemFood C_BEETROOT_F 				= new BaseItemFood("compressed_beetroot", Items.BEETROOT);
			public final BaseItemFood C_SPECKLED_MELON_F 		= new BaseItemFood("compressed_speckled_melon", Items.SPECKLED_MELON).setFoodEntry(3, 0.9F, false);
			
			public final BaseItemFood C_MUSHROOM_STEW_F 		= new BaseItemFood("compressed_mushron_stew", Items.MUSHROOM_STEW).setContainer(new ItemStack(normal.C_BOWL_F));
			public final BaseItemFood C_RABBIT_STEW_F 			= new BaseItemFood("compressed_rabbit_stew", Items.RABBIT_STEW).setContainer(new ItemStack(normal.C_BOWL_F));
			public final BaseItemFood C_BEETROOT_SOUP_F 		= new BaseItemFood("compressed_beetroot_soup", Items.BEETROOT_SOUP).setContainer(new ItemStack(normal.C_BOWL_F));
			
			public final BaseItemFood C_PUFFER_FISH_F 			= new BaseItemFood("compressed_puffer_fish", Items.FISH, 3)
					.addPotionEffect(new int[] {-1},new PotionEffectType[] {
							new PotionEffectType(MobEffects.POISON, 2280, 3),
							new PotionEffectType(MobEffects.HUNGER, 570, 2),
							new PotionEffectType(MobEffects.NAUSEA, 570, 1)
						}
					);
			public final BaseItemFood C_GOLD_APPLE_F			= new BaseItemFood("compressed_gold_apple", Items.GOLDEN_APPLE, 0)
					.addPotionEffect(new int[] {-1},new PotionEffectType[] {
							new PotionEffectType(MobEffects.REGENERATION, 220, 1),
							new PotionEffectType(MobEffects.ABSORPTION, 4560, 0)
						}
					);
			public final BaseItemFood C_ENCHANTED_GOLD_APPLE_F	= new BaseItemFood("compressed_enchanted_gold_apple", Items.GOLDEN_APPLE, 1)
					.addPotionEffect(new int[] {-1}, new PotionEffectType[] {
							new PotionEffectType(MobEffects.REGENERATION, 760, 1),
							new PotionEffectType(MobEffects.RESISTANCE, 11400, 0),
							new PotionEffectType(MobEffects.FIRE_RESISTANCE, 11400, 0),
							new PotionEffectType(MobEffects.ABSORPTION, 4560, 3)
						}
					);
			public final BaseItemFood C_SPIDER_EYE_F	= new BaseItemFood("compressed_spider_eye", Items.SPIDER_EYE)
					.addPotionEffect(new int[] {-1}, new PotionEffectType[] {
							new PotionEffectType(MobEffects.POISON, 180, 1)
						}
					).setContainer(new ItemStack(C_ENCHANTED_GOLD_APPLE_F));
		}
	}
	
	public static class ThermalFoundationItem{
		public final Normal normal = new Normal();
		
		public static class Normal{
			public static BaseItemSub C_DYE_I 				= null;
			public static BaseItemSub C_SAWDUST_I			= null;
			// Crystal 络合�?
			public static BaseItemSub C_CRYSTAL_ENDER_I 	= null;
			public static BaseItemSub C_CRYSTAL_GLOWSTONE_I = null;
			public static BaseItemSub C_CRYSTAL_REDSTONE_I 	= null;
			
			// Slag 炉渣
			public static BaseItemSub C_CRYSTAL_SLAG_I		= null;
			public static BaseItemSub C_CRYSTAL_SLAG_RICH_I	= null;
			public static BaseItemSub C_CRYSTAL_CINNABAR_I	= null;
			
			//Rod 棒子
			public static BaseItemSub C_ROD_BASALZ_I 		= null;
			public static BaseItemSub C_ROD_BLITZ_I 		= null;
			public static BaseItemSub C_ROD_BLIZZ_I 		= null;
			
			// Plate 板子
			public static BaseItemSub C_PLATE_ALUMINUM_I 	= null;
			public static BaseItemSub C_PLATE_BRONZE_I 		= null;
			public static BaseItemSub C_PLATE_CONSTANTAN_I 	= null;
			public static BaseItemSub C_PLATE_COPPER_I 		= null;
			public static BaseItemSub C_PLATE_ELECTRUM_I 	= null;
			public static BaseItemSub C_PLATE_ENDERIUM_I 	= null;
			public static BaseItemSub C_PLATE_GOLD_I 		= null;
			public static BaseItemSub C_PLATE_INVAR_I 		= null;
			public static BaseItemSub C_PLATE_IRIDIUM_I 	= null;
			public static BaseItemSub C_PLATE_IRON_I 		= null;
			public static BaseItemSub C_PLATE_LEAD_I 		= null;
			public static BaseItemSub C_PLATE_LUMIUM_I 		= null;
			public static BaseItemSub C_PLATE_MITHRIL_I 	= null;
			public static BaseItemSub C_PLATE_NICKL_I 		= null;
			public static BaseItemSub C_PLATE_PLATINUM_I 	= null;
			public static BaseItemSub C_PLATE_SIGNALUM_I 	= null;
			public static BaseItemSub C_PLATE_SILVER_I 		= null;
			public static BaseItemSub C_PLATE_STEEL_I 		= null;
			public static BaseItemSub C_PLATE_TIN_I 		= null;
			
			// Gear 齿轮
			public static BaseItemSub C_GEAR_ALUMINUM_I		= null;
			public static BaseItemSub C_GEAR_BRONZE_I		= null;
			public static BaseItemSub C_GEAR_CONSTANTAN_I	= null;
			public static BaseItemSub C_GEAR_COPPER_I		= null;
			public static BaseItemSub C_GEAR_DIAMOND_I		= null;
			public static BaseItemSub C_GEAR_ELECTRUM_I		= null;
			public static BaseItemSub C_GEAR_EMERALD_I		= null;
			public static BaseItemSub C_GEAR_ENDERIUM_I		= null;
			public static BaseItemSub C_GEAR_GOLD_I			= null;
			public static BaseItemSub C_GEAR_INVAR_I		= null;
			public static BaseItemSub C_GEAR_IRIDIUM_I		= null;
			public static BaseItemSub C_GEAR_IRON_I			= null;
			public static BaseItemSub C_GEAR_LEAD_I			= null;
			public static BaseItemSub C_GEAR_LUMIUM_I		= null;
			public static BaseItemSub C_GEAR_MITHRIL_I		= null;
			public static BaseItemSub C_GEAR_NICKEL_I		= null;
			public static BaseItemSub C_GEAR_PLATINUM_I		= null;
			public static BaseItemSub C_GEAR_SIGNALUM_I		= null;
			public static BaseItemSub C_GEAR_SILVER_I		= null;
			public static BaseItemSub C_GEAR_STEEL_I		= null;
			public static BaseItemSub C_GEAR_STONE_I		= null;
			public static BaseItemSub C_GEAR_TIN_I			= null;
			public static BaseItemSub C_GEAR_WOOD_I			= null;
			
			// Dust 金属�?
			public static BaseItemSub C_DUST_ALUMINUM_I 	= null;
			public static BaseItemSub C_DUST_BRONZE_I 		= null;
			public static BaseItemSub C_DUST_CONSTANTAN_I 	= null;
			public static BaseItemSub C_DUST_COPPER_I 		= null;
			public static BaseItemSub C_DUST_ELECTRUM_I 	= null;
			public static BaseItemSub C_DUST_ENDERIUM_I 	= null;
			public static BaseItemSub C_DUST_GOLD_I 		= null;
			public static BaseItemSub C_DUST_INVAR_I 		= null;
			public static BaseItemSub C_DUST_IRIDIUM_I 		= null;
			public static BaseItemSub C_DUST_IRON_I 		= null;
			public static BaseItemSub C_DUST_LEAD_I 		= null;
			public static BaseItemSub C_DUST_LUMIUM_I 		= null;
			public static BaseItemSub C_DUST_MITHRIL_I 		= null;
			public static BaseItemSub C_DUST_NICKL_I 		= null;
			public static BaseItemSub C_DUST_PLATINUM_I 	= null;
			public static BaseItemSub C_DUST_SIGNALUM_I 	= null;
			public static BaseItemSub C_DUST_SILVER_I 		= null;
			public static BaseItemSub C_DUST_STEEL_I 		= null;
			public static BaseItemSub C_DUST_TIN_I 			= null;
			
			static {
				try {
					if(Loader.isModLoaded("thermalfoundation")) {
						C_DYE_I 				= register("compressed_tf_dye", TFItems.itemDye.dyeWhite);
						C_SAWDUST_I 			= register("compressed_wood_dust", TFItems.itemMaterial.dustWoodCompressed);
						
						C_CRYSTAL_SLAG_I 		= register("compressed_crystal_slag", TFItems.itemMaterial.crystalSlag);
						C_CRYSTAL_SLAG_RICH_I 	= register("compressed_crystal_slag_rich", TFItems.itemMaterial.crystalSlagRich);
						C_CRYSTAL_CINNABAR_I 	= register("compressed_crystal_cinnabar", TFItems.itemMaterial.crystalCinnabar);
						
						// Crystal 络合�?
						C_CRYSTAL_ENDER_I 		= register("compressed_crystal_ender", TFItems.itemMaterial.crystalEnder);
						C_CRYSTAL_GLOWSTONE_I 	= register("compressed_crystal_glowstone", TFItems.itemMaterial.crystalGlowstone);
						C_CRYSTAL_REDSTONE_I 	= register("compressed_crystal_redstone", TFItems.itemMaterial.crystalRedstone);
						
						//Rod 棒子
						C_ROD_BASALZ_I 			= register("compressed_rod_basalz", TFItems.itemMaterial.rodBasalz);
						C_ROD_BLITZ_I 			= register("compressed_rod_blitz", TFItems.itemMaterial.rodBlitz);
						C_ROD_BLIZZ_I 			= register("compressed_rod_blizz", TFItems.itemMaterial.rodBlizz);
						
						// Plate 板子
						C_PLATE_ALUMINUM_I 		= register("compressed_plate_aluminum", TFItems.itemMaterial.plateAluminum).setModelMaterial("plate");
						C_PLATE_BRONZE_I 		= register("compressed_plate_bronze", TFItems.itemMaterial.plateBronze).setModelMaterial("plate");
						C_PLATE_CONSTANTAN_I 	= register("compressed_plate_constantan", TFItems.itemMaterial.plateConstantan).setModelMaterial("plate");
						C_PLATE_COPPER_I 		= register("compressed_plate_copper", TFItems.itemMaterial.plateCopper).setModelMaterial("plate");
						C_PLATE_ELECTRUM_I 		= register("compressed_plate_electrum", TFItems.itemMaterial.plateElectrum).setModelMaterial("plate");
						C_PLATE_ENDERIUM_I 		= register("compressed_plate_enderium", TFItems.itemMaterial.plateEnderium).setModelMaterial("plate");
						C_PLATE_GOLD_I 			= register("compressed_plate_gold", TFItems.itemMaterial.plateGold).setModelMaterial("plate");
						C_PLATE_INVAR_I 		= register("compressed_plate_invar", TFItems.itemMaterial.plateInvar).setModelMaterial("plate");
						C_PLATE_IRIDIUM_I 		= register("compressed_plate_iridium", TFItems.itemMaterial.plateIridium).setModelMaterial("plate");
						C_PLATE_IRON_I 			= register("compressed_plate_iron", TFItems.itemMaterial.plateIron).setModelMaterial("plate");
						C_PLATE_LEAD_I 			= register("compressed_plate_lead", TFItems.itemMaterial.plateLead).setModelMaterial("plate");
						C_PLATE_LUMIUM_I 		= register("compressed_plate_lumium", TFItems.itemMaterial.plateLumium).setModelMaterial("plate");
						C_PLATE_MITHRIL_I 		= register("compressed_plate_mithri", TFItems.itemMaterial.plateMithril).setModelMaterial("plate");
						C_PLATE_NICKL_I 		= register("compressed_plate_nickel", TFItems.itemMaterial.plateNickel).setModelMaterial("plate");
						C_PLATE_PLATINUM_I 		= register("compressed_plate_platinum", TFItems.itemMaterial.platePlatinum).setModelMaterial("plate");
						C_PLATE_SIGNALUM_I 		= register("compressed_plate_signalum", TFItems.itemMaterial.plateSignalum).setModelMaterial("plate");
						C_PLATE_SILVER_I 		= register("compressed_plate_silver", TFItems.itemMaterial.plateSilver).setModelMaterial("plate");
						C_PLATE_STEEL_I 		= register("compressed_plate_steel", TFItems.itemMaterial.plateSteel).setModelMaterial("plate");
						C_PLATE_TIN_I 			= register("compressed_plate_tin", TFItems.itemMaterial.plateTin).setModelMaterial("plate");
						
						// Gear 齿轮
						C_GEAR_ALUMINUM_I 		= register("compressed_gear_aluminum", TFItems.itemMaterial.gearAluminum).setModelMaterial("gear");
						C_GEAR_BRONZE_I 		= register("compressed_gear_bronze", TFItems.itemMaterial.gearBronze).setModelMaterial("gear");
						C_GEAR_CONSTANTAN_I 	= register("compressed_gear_constantan", TFItems.itemMaterial.gearConstantan).setModelMaterial("gear");
						C_GEAR_COPPER_I 		= register("compressed_gear_copper", TFItems.itemMaterial.gearCopper).setModelMaterial("gear");
						C_GEAR_DIAMOND_I 		= register("compressed_gear_diamond", TFItems.itemMaterial.gearDiamond).setModelMaterial("gear");
						C_GEAR_ELECTRUM_I 		= register("compressed_gear_electrum", TFItems.itemMaterial.gearElectrum).setModelMaterial("gear");
						C_GEAR_EMERALD_I 		= register("compressed_gear_emerald", TFItems.itemMaterial.gearEmerald).setModelMaterial("gear");
						C_GEAR_ENDERIUM_I 		= register("compressed_gear_enderium", TFItems.itemMaterial.gearEnderium).setModelMaterial("gear");
						C_GEAR_GOLD_I 			= register("compressed_gear_gold", TFItems.itemMaterial.gearGold).setModelMaterial("gear");
						C_GEAR_INVAR_I 			= register("compressed_gear_invar", TFItems.itemMaterial.gearInvar).setModelMaterial("gear");
						C_GEAR_IRIDIUM_I 		= register("compressed_gear_iridium", TFItems.itemMaterial.gearIridium).setModelMaterial("gear");
						C_GEAR_IRON_I 			= register("compressed_gear_iron", TFItems.itemMaterial.gearIron).setModelMaterial("gear");
						C_GEAR_LEAD_I 			= register("compressed_gear_lead", TFItems.itemMaterial.gearLead).setModelMaterial("gear");
						C_GEAR_LUMIUM_I 		= register("compressed_gear_lumium", TFItems.itemMaterial.gearLumium).setModelMaterial("gear");
						C_GEAR_MITHRIL_I 		= register("compressed_gear_mithril", TFItems.itemMaterial.gearMithril).setModelMaterial("gear");
						C_GEAR_NICKEL_I 		= register("compressed_gear_nickel", TFItems.itemMaterial.gearNickel).setModelMaterial("gear");
						C_GEAR_PLATINUM_I 		= register("compressed_gear_platinum", TFItems.itemMaterial.gearPlatinum).setModelMaterial("gear");
						C_GEAR_SIGNALUM_I 		= register("compressed_gear_signalum", TFItems.itemMaterial.gearSignalum).setModelMaterial("gear");
						C_GEAR_SILVER_I 		= register("compressed_gear_silver", TFItems.itemMaterial.gearSilver).setModelMaterial("gear");
						C_GEAR_STEEL_I 			= register("compressed_gear_steel", TFItems.itemMaterial.gearSteel).setModelMaterial("gear");
						C_GEAR_STONE_I 			= register("compressed_gear_stone", TFItems.itemMaterial.gearStone).setModelMaterial("gear");
						C_GEAR_TIN_I 			= register("compressed_gear_tin", TFItems.itemMaterial.gearTin).setModelMaterial("gear");
						C_GEAR_WOOD_I 			= register("compressed_gear_wood", TFItems.itemMaterial.gearWood).setModelMaterial("gear");
						
						// Dust 金属�?
						C_DUST_ALUMINUM_I 		= register("compressed_dust_aluminum", TFItems.itemMaterial.dustAluminum).setModelMaterial("dust");
						C_DUST_BRONZE_I 		= register("compressed_dust_bronze", TFItems.itemMaterial.dustBronze).setModelMaterial("dust");
						C_DUST_CONSTANTAN_I 	= register("compressed_dust_constantan", TFItems.itemMaterial.dustConstantan).setModelMaterial("dust");
						C_DUST_COPPER_I 		= register("compressed_dust_copper", TFItems.itemMaterial.dustCopper).setModelMaterial("dust");
						C_DUST_ELECTRUM_I 		= register("compressed_dust_electrum", TFItems.itemMaterial.dustElectrum).setModelMaterial("dust");
						C_DUST_ENDERIUM_I 		= register("compressed_dust_enderium", TFItems.itemMaterial.dustEnderium).setModelMaterial("dust");
						C_DUST_GOLD_I 			= register("compressed_dust_gold", TFItems.itemMaterial.dustGold).setModelMaterial("dust");
						C_DUST_INVAR_I 			= register("compressed_dust_invar", TFItems.itemMaterial.dustInvar).setModelMaterial("dust");
						C_DUST_IRIDIUM_I 		= register("compressed_dust_iridium", TFItems.itemMaterial.dustIridium).setModelMaterial("dust");
						C_DUST_IRON_I 			= register("compressed_dust_iron", TFItems.itemMaterial.dustIron).setModelMaterial("dust");
						C_DUST_LEAD_I 			= register("compressed_dust_lead", TFItems.itemMaterial.dustLead).setModelMaterial("dust");
						C_DUST_LUMIUM_I 		= register("compressed_dust_lumium", TFItems.itemMaterial.dustLumium).setModelMaterial("dust");
						C_DUST_MITHRIL_I 		= register("compressed_dust_mithril", TFItems.itemMaterial.dustMithril).setModelMaterial("dust");
						C_DUST_NICKL_I 			= register("compressed_dust_nickel", TFItems.itemMaterial.dustNickel).setModelMaterial("dust");
						C_DUST_PLATINUM_I 		= register("compressed_dust_platinum", TFItems.itemMaterial.dustPlatinum).setModelMaterial("dust");
						C_DUST_SIGNALUM_I 		= register("compressed_dust_signalum", TFItems.itemMaterial.dustSignalum).setModelMaterial("dust");
						C_DUST_SILVER_I 		= register("compressed_dust_silver", TFItems.itemMaterial.dustSilver).setModelMaterial("dust");
						C_DUST_STEEL_I 			= register("compressed_dust_steel", TFItems.itemMaterial.dustSteel).setModelMaterial("dust");
						C_DUST_TIN_I 			= register("compressed_dust_tin", TFItems.itemMaterial.dustTin).setModelMaterial("dust");
						
					}
				}catch (Throwable e) {e.printStackTrace();}
			}
		}
		
		private static BaseItemSub register(String name, ItemStack baseItem) {
			return BaseItemSub.register(name, baseItem, "thermalfoundation");
		}
	}
	
	public static class DraconicEvolutionItems {
		public final Normal normal = new Normal();
		
		public static class Normal {
			public static BaseItemSub C_CORE_DRACONIC_I			= null;
			public static BaseItemSub C_CORE_WYVERN_I			= null;
			public static BaseItemSub C_CORE_AWAKENED_I			= null;
			public static BaseItemSub C_CORE_CHAOTIC_I			= null;
			
			public static BaseItemSub C_CORE_ENERGY_WYVERN_I	= null;
			public static BaseItemSub C_CORE_ENERGY_AWAKENED_I	= null;
			
			public static BaseItemSub C_CHAOS_SHARD_I			= null;
			public static BaseItemSub C_DRAGON_HEART_I			= null;
			public static BaseItemSub C_DRACONIUM_DUST_I			= null;
			
			static {
				try {
					if(Loader.isModLoaded("draconicevolution")) {
						C_CORE_DRACONIC_I 			= register("compressed_core_draconic", new ItemStack(DEFeatures.draconicCore));
						C_CORE_WYVERN_I 			= register("compressed_core_wyvern", new ItemStack(DEFeatures.wyvernCore));
						C_CORE_AWAKENED_I 			= register("compressed_core_awakened", new ItemStack(DEFeatures.awakenedCore));
						C_CORE_CHAOTIC_I 			= register("compressed_core_chaotic", new ItemStack(DEFeatures.chaoticCore));
						
						C_CORE_ENERGY_WYVERN_I 		= register("compressed_energy_core_wyvern", new ItemStack(DEFeatures.wyvernEnergyCore));
						C_CORE_ENERGY_AWAKENED_I 	= register("compressed_energy_core_awakened", new ItemStack(DEFeatures.draconicEnergyCore));
						
						C_CHAOS_SHARD_I 			= register("compressed_chaos_shard", new ItemStack(DEFeatures.chaosShard));
						C_DRAGON_HEART_I			= register("compressed_dragon_heart", new ItemStack(DEFeatures.dragonHeart));
						C_DRACONIUM_DUST_I			= register("compressed_draconium_dust", new ItemStack(DEFeatures.draconiumDust));
						
					}
				} catch (Throwable e) {e.printStackTrace();}
			}
		}
		
		private static BaseItemSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseItemSub.register(nameIn, unCompressedItem, "draconicevolution");
		}
	}
	
	public static class AvaritiaItems {
		protected static final morph.avaritia.init.ModItems items = new morph.avaritia.init.ModItems();
		public final Normal normal = new Normal();
		
		public static class Normal {
			public static BaseItemFood C_COSMIC_MEATBALLS_I			= null;
			
			static {
				try {
					if(Loader.isModLoaded("avaritia")) {
						C_COSMIC_MEATBALLS_I = new BaseItemFood("compressed_cosmic_meatballs", items.cosmic_meatballs, 0, "avaritia")
						.addPotionEffect(new int[] {-1},new PotionEffectType[] {
								new PotionEffectType(MobEffects.STRENGTH, 760, 2)
							}
						);
					}
				} catch (Throwable e) {e.printStackTrace();}
			}
		}
		
		@SuppressWarnings("unused")
		private static BaseItemSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseItemSub.register(nameIn, unCompressedItem, "avaritia");
		}
	}
	
	public static class MCSItem {
		public final BaseItemNormal CAT_HAIR 		= new BaseItemNormal("cat_hair", CreativeTabs.MISC);
		public final BaseItemNormal CAT_INGOT 		= new BaseItemNormal("cat_ingot", CreativeTabs.MISC);
		public final BaseItemNormal CAT_HAMMER 		= new ItemCatHammer("cat_hammer", CreativeTabs.TOOLS);
		public final BaseItemNormal DESTROYER		= new ItemDestroyer("destroyer", CreativeTabs.TOOLS);
	}
	
	public static void registerCustom() {
		File config = new File("./config/jiu/mcs/custom.json");
		if(config.exists()) {
			try {
				JsonObject file = new JsonParser().parse(new FileReader(config)).getAsJsonObject();
				
				for(Map.Entry<String, JsonElement> fileObject : file.entrySet()) {
					JsonArray mainArray = fileObject.getValue().getAsJsonArray();// 主清�?
					for(int i = 0; i < mainArray.size(); ++i) {
						JsonObject subObject = mainArray.get(i).getAsJsonObject();//子清�?
						
						String type_tmp = subObject.get("type").getAsString();
						String[] main_type = type_tmp.indexOf(":") != -1 ? JiuUtils.other.custemSplitString(type_tmp, ":") : new String[]{type_tmp};
						
						CustomType type = CustomType.getType(main_type);
						if(type == CustomType.UNKNOWN) {
							String crashMsg = "\n\ncustom.json -> nunknown type: \n -> " + fileObject.getKey() + ": \n  -> (" + i + "):\n   -> \"type\": \"" + type_tmp + "\"\n";
							throw new UnknownTypeException(crashMsg);
						}else {
							JsonArray entries = subObject.get("entries").getAsJsonArray();// 方块清单
							for(int m = 0; m < entries.size(); ++m) {
								JsonObject itemObject = entries.get(m).getAsJsonObject();
								
								String name = itemObject.get("id").getAsString();
								ItemStack unItem = InitChangeBlock.getStack(itemObject.get("unItem").getAsString());
								if(unItem == null || unItem.getItem() == Items.AIR || unItem == ItemStack.EMPTY) {
									String crashMsg = "\n\ncustom.json -> unknown item:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"unItem\": \"" + itemObject.get("unItem").getAsString() + "\"\n";
									throw new NonItemException(crashMsg);
								}
								CreativeTabs tab = MCS.COMPERESSED_ITEMS;
								if(type == CustomType.ITEM_NORMA) {
									BaseItemSub item = BaseItemSub.register(name, unItem, "custom", tab);
									
									if(itemObject.has("enableDefaultRecipe")) {
										item.setMakeDefaultStackRecipe(itemObject.get("enableDefaultRecipe").getAsBoolean());
									}
									
									Map<Integer, ItemStack> InfoStackMap = InitCustomBlock.initInfoStack(itemObject);
									if(InfoStackMap != null) {
										item.setInfoStack(InfoStackMap);
									}
									
									Map<Integer, List<String>> infos = InitCustomBlock.initInfos(itemObject);
									if(infos != null) {
										item.addCustemInformation(infos);
									}
									
									Map<Integer, List<String>> shiftInfos = InitCustomBlock.initShiftInfos(itemObject);
									if(shiftInfos != null) {
										item.addCustemShiftInformation(shiftInfos);
									}
									
									Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(itemObject);
									if(HasEffectMap != null) {
										item.setHasEffectMap(HasEffectMap);
									}
									
									MCSItems.ITEMS.add(item);
									MCSItems.ITEMS_NAME.add(name);
									MCSItems.SUB_ITEMS.add(item);
									MCSItems.SUB_ITEMS_NAME.add(name);
									MCSItems.SUB_ITEMS_MAP.put(name, item);
								}else if(type == CustomType.ITEM_FOOD) {
									BaseItemFood item = BaseItemFood.register(name, unItem, "custom", tab);
									
									if(itemObject.has("enableDefaultRecipe")) {
										item.setMakeDefaultStackRecipe(itemObject.get("enableDefaultRecipe").getAsBoolean());
										MCS.instance.log.info(itemObject.get("enableDefaultRecipe").getAsBoolean()+"");
									}
									
									if(!BaseItemFood.isFood(unItem)) {
										if(!itemObject.has("healAmount")) {
											String crashMsg = "\n\ncustom.json -> element not found:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"healAmount\": <Number>\n";
											throw new JsonElementNotFoundException(crashMsg);
										}
										if(!itemObject.has("saturation")) {
											String crashMsg = "\n\ncustom.json -> element not found:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"saturation\": <Number>\n";
											throw new JsonElementNotFoundException(crashMsg);
										}
										if(!itemObject.has("isWolfFood")) {
											String crashMsg = "\n\ncustom.json -> element not found:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"isWolfFood\": <Boolean>\n";
											throw new JsonElementNotFoundException(crashMsg);
										}
										item.setFoodEntry(itemObject.get("healAmount").getAsInt(), itemObject.get("saturation").getAsFloat(), itemObject.get("isWolfFood").getAsBoolean());
									}
									
									Map<Integer, ItemStack> InfoStackMap = InitCustomBlock.initInfoStack(itemObject);
									if(InfoStackMap != null) {
										item.setInfoStack(InfoStackMap);
									}
									
									Map<Integer, List<String>> infos = InitCustomBlock.initInfos(itemObject);
									if(infos != null) {
										item.addCustemInformation(infos);
									}
									
									Map<Integer, List<String>> shiftInfos = InitCustomBlock.initShiftInfos(itemObject);
									if(shiftInfos != null) {
										item.addCustemShiftInformation(shiftInfos);
									}
									
									Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(itemObject);
									if(HasEffectMap != null) {
										item.setHasEffectMap(HasEffectMap);
									}
									
									Map<Integer, ItemStack> ContainerMap = InitCustomItem.initContainerMap(itemObject);
									if(ContainerMap != null) {
										item.setContainerMap(ContainerMap);
									}
									
									Map<Integer, List<PotionEffectType>> PotionEffectMap = InitCustomItem.initPotionEffectMap(itemObject);
									if(PotionEffectMap != null) {
										item.addPotionEffect(PotionEffectMap);
									}
									
									Map<Integer, Integer> HealAmountMap = InitCustomItem.initHealAmountMap(itemObject);
									if(HealAmountMap != null) {
										item.setHealAmountMap(HealAmountMap);
									}
									
									Map<Integer, Float> SaturationModifierMap = InitCustomItem.initSaturationModifierMap(itemObject);
									if(SaturationModifierMap != null) {
										item.setSaturationModifierMap(SaturationModifierMap);
									}
									
									MCSItems.ITEMS_NAME.add(name);
									MCSItems.FOODS_NAME.add(name);
									MCSItems.ITEMS.add(item);
									MCSItems.FOODS.add(item);
									MCSItems.FOODS_MAP.put(name, item);
								}
							}
						}
					}
				}
			}catch(JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				e.printStackTrace();
				throw new JsonException("custom.json -> " + e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf(":")+2));
			}
		}
	}
}
