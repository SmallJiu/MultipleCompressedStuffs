package cat.jiu.mcs.util.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.brandon3055.draconicevolution.DEFeatures;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.items.*;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseItemFood;
import cat.jiu.mcs.util.base.BaseItemNormal;
import cat.jiu.mcs.util.base.BaseItemSub;
import cofh.thermalfoundation.init.TFItems;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("static-access")
@EventBusSubscriber(modid = MCS.MODID)
public class MCSItems {
	public static final int UNBREAK = -1;
	public static final int INT_MAX = Integer.MAX_VALUE;
	public static final float FLOAT_MAX = Float.MAX_VALUE;
	public static final long LONG_MAX = Long.MAX_VALUE;
	public static final double DOUBLE_MAX = Double.MAX_VALUE;
	
	public static final Map<String, BaseItemSub> SUB_ITEMS_MAP = new HashMap<String, BaseItemSub>();
	public static final Map<String, BaseItemFood> FOODS_MAP = new HashMap<String, BaseItemFood>();
	
	public static final List<Item> ITEMS = new ArrayList<Item>();
	public static final List<String> ITEMS_NAME = new ArrayList<String>();
	public static final List<BaseItemFood> FOODS = new ArrayList<BaseItemFood>();
	public static final List<String> FOODS_NAME = new ArrayList<String>();
	public static final List<BaseItemSub> SUB_ITEMS = new ArrayList<BaseItemSub>();
	public static final List<String> SUB_ITEMS_NAME = new ArrayList<String>();
	
	public static final MCSItems instance = new MCSItems();
	public static final MCSItem normal = new MCSItem();
	public static final MinecraftItem minecraft = new MinecraftItem();
	public static final ThermalFoundationItem thermal_foundation = new ThermalFoundationItem();
	public static final DraconicEvolutionItems draconic_evolution = new DraconicEvolutionItems();
	
	public MCSItems() {
		
	}
	
	public static final void registerOreDict() {
		for(BaseItemFood food : FOODS) {
			JiuUtils.item.registerCompressedOre(food.getUnCompressedName(), food, false);
		}
		for(BaseItemSub item : SUB_ITEMS) {
			JiuUtils.item.registerCompressedOre(item.getUnCompressedName(), item, false);
		}
	}
	
	public static class MinecraftItem{
		public final Normal normal = new Normal();
		
		public class Normal{
			public final Food food = new Food();
			
			public class Food{
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
				public final BaseItemFood C_SPECKLED_MELON_F 		= new BaseItemFood("compressed_speckled_melon", Items.SPECKLED_MELON, 3, 0.9F);
				
				public final BaseItemFood C_MUSHROOM_STEW_F 		= new BaseItemFood("compressed_mushron_stew", Items.MUSHROOM_STEW);
				public final BaseItemFood C_RABBIT_STEW_F 			= new BaseItemFood("compressed_rabbit_stew", Items.RABBIT_STEW);
				public final BaseItemFood C_BEETROOT_SOUP_F 		= new BaseItemFood("compressed_beetroot_soup", Items.BEETROOT_SOUP);
				
				public final BaseItemFood C_PUFFER_FISH_F 			= new BaseItemFood("compressed_puffer_fish", Items.FISH, 3)
						.canAddPotionEffect(true)
						.setPotionEffect(new PotionEffect[] {
								new PotionEffect(MobEffects.POISON, 2280, 3),
								new PotionEffect(MobEffects.HUNGER, 570, 2),
								new PotionEffect(MobEffects.HUNGER, 570, 2),
								new PotionEffect(MobEffects.NAUSEA, 570, 1)
							}
						);
				public final BaseItemFood C_GOLD_APPLE_F			= new BaseItemFood("compressed_gold_apple", Items.GOLDEN_APPLE, 0)
						.canAddPotionEffect(true)
						.setPotionEffect(new PotionEffect[] {
								new PotionEffect(MobEffects.REGENERATION, 220, 1),
								new PotionEffect(MobEffects.ABSORPTION, 4560, 0)
							}
						);
				public final BaseItemFood C_ENCHANTED_GOLD_APPLE_F	= new BaseItemFood("compressed_enchanted_gold_apple", Items.GOLDEN_APPLE, 1)
						.canAddPotionEffect(true)
						.setPotionEffect(new PotionEffect[] {
								new PotionEffect(MobEffects.REGENERATION, 760, 1),
								new PotionEffect(MobEffects.RESISTANCE, 11400, 0),
								new PotionEffect(MobEffects.FIRE_RESISTANCE, 11400, 0),
								new PotionEffect(MobEffects.ABSORPTION, 4560, 3)
							}
						);
			}
		}
	}
	
	public static class ThermalFoundationItem{
		public final Normal normal = new Normal();
		
		public static class Normal{
			public static BaseItemSub C_DYE_I 				= null;
			
			// Crystal 络合物
			public static BaseItemSub C_CRYSTAL_ENDER_I 	= null;
			public static BaseItemSub C_CRYSTAL_GLOWSTONE_I = null;
			public static BaseItemSub C_CRYSTAL_REDSTONE_I 	= null;
			
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
			
			static {
				try {
					C_DYE_I 				= register("compressed_dye", TFItems.itemDye.dyeWhite);
					
					// Crystal 络合物
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
					
				} catch (Exception e) {}
			}
		}
		
		private static BaseItemSub register(String name, ItemStack baseItem) {
			return BaseItemSub.register(name, baseItem, "thermalfoundation");
		}
	}
	
	public static class DraconicEvolutionItems {
		public final Normal normal = new Normal();
		public final Has has = new Has();
		
		public static class Normal {
			public static BaseItemSub C_CORE_DRACONIC_I			= null;
			public static BaseItemSub C_CORE_WYVERN_I			= null;
			public static BaseItemSub C_CORE_AWAKENED_I			= null;
			public static BaseItemSub C_CORE_CHAOTIC_I			= null;
			
			public static BaseItemSub C_CORE_ENERGY_WYVERN_I	= null;
			public static BaseItemSub C_CORE_ENERGY_AWAKENED_I	= null;
			
			public static BaseItemSub C_CHAOS_SHARD_I			= null;
			public static BaseItemSub C_DRAGON_HEART_I			= null;
			
			static {
				try {
					C_CORE_DRACONIC_I 			= register("compressed_core_draconic", new ItemStack(DEFeatures.draconicCore));
					C_CORE_WYVERN_I 			= register("compressed_core_wyvern", new ItemStack(DEFeatures.wyvernCore));
					C_CORE_AWAKENED_I 			= register("compressed_core_awakened", new ItemStack(DEFeatures.awakenedCore));
					C_CORE_CHAOTIC_I 			= register("compressed_core_chaotic", new ItemStack(DEFeatures.chaoticCore));
					
					C_CORE_ENERGY_WYVERN_I 		= register("compressed_energy_core_wyvern", new ItemStack(DEFeatures.wyvernEnergyCore));
					C_CORE_ENERGY_AWAKENED_I 	= register("compressed_energy_core_awakened", new ItemStack(DEFeatures.draconicEnergyCore));
					
					C_CHAOS_SHARD_I 			= register("compressed_chaos_shard", new ItemStack(DEFeatures.chaosShard));
					C_DRAGON_HEART_I			= register("compressed_dragon_heart", new ItemStack(DEFeatures.dragonHeart));
					
				}catch(Exception e) {}
			}
		}
		
		public static class Has {
			
			
			static {
				try {
					
					
				}catch(Exception e) {}
			}
		}
		
		private static BaseItemSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseItemSub.register(nameIn, unCompressedItem, "draconicevolution");
		}
	}
	
	public static class MCSItem {
		public final BaseItemNormal CAT_HAIR 			= new BaseItemNormal("cat_hair", MCS.COMPERESSED_ITEMS);
		public final BaseItemNormal CAT_INGOT 			= new BaseItemNormal("cat_ingot", MCS.COMPERESSED_ITEMS);
		public final BaseItemNormal CAT_HAMMER 			= new ItemCatHammer("cat_hammer", MCS.COMPERESSED_ITEMS);
		public final BaseItemNormal DESTROYER			= new ItemDestroyer("destroyer", MCS.COMPERESSED_ITEMS);
	}
	
	// 创建/注册所有在ModItems(物品集合)里的物品对象
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ITEMS.toArray(new Item[0]));
	}
}
