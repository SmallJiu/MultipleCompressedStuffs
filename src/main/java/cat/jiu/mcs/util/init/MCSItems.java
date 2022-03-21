package cat.jiu.mcs.util.init;

import com.brandon3055.draconicevolution.DEFeatures;

import cofh.thermalfoundation.init.TFItems;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.items.*;
import cat.jiu.mcs.items.compressed.CompressedEgg;
import cat.jiu.mcs.items.compressed.ic.IC2RadiationItem;
import cat.jiu.mcs.items.compressed.ic.IC2ReactorFuelRod;
import cat.jiu.mcs.items.compressed.ic.ICBatteryCharge;
import cat.jiu.mcs.items.compressed.ic.ICCondensator;
import cat.jiu.mcs.items.compressed.ic.ICEnergyCrystal;
import cat.jiu.mcs.items.compressed.ic.ICHeatExchanger;
import cat.jiu.mcs.items.compressed.ic.ICHeatStorage;
import cat.jiu.mcs.items.compressed.ic.ICHeatVent;
import cat.jiu.mcs.items.compressed.ic.ICReflector;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseItem;
import cat.jiu.mcs.util.type.PotionEffectType;
import cat.jiu.mcs.util.base.BaseItemNormal;
import cat.jiu.mcs.util.base.sub.BaseItemFood;
import cat.jiu.mcs.util.base.sub.BaseItemSub;
import cat.jiu.mcs.util.base.sub.tool.BaseItemAxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemHoe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemPickaxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemShovel;
import cat.jiu.mcs.util.base.sub.tool.BaseItemSword;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SuppressWarnings("static-access")
public class MCSItems {
	public static final int UNBREAK = -1;
	public static final int INT_MAX = Integer.MAX_VALUE;
	public static final float FLOAT_MAX = Float.MAX_VALUE;
	public static final long LONG_MAX = Long.MAX_VALUE;
	public static final double DOUBLE_MAX = Double.MAX_VALUE;

	public static final MCSItem normal = new MCSItem();
	public static final MinecraftItem minecraft = new MinecraftItem();
	public static final OreStuff ore_stuff = new OreStuff();
	public static ThermalFoundationItem thermal_foundation = null;
	public static DraconicEvolutionItems draconic_evolution = null;
	public static AvaritiaItems avaritia = null;
	public static IndustrialCraft ic2 = null;

	public MCSItems() {
		if(Configs.Custom.Enable_Mod_Stuff) {
			try {
				thermal_foundation = Configs.Custom.Mod_Stuff.ThermalFoundation ? new ThermalFoundationItem() : null;
				draconic_evolution = Configs.Custom.Mod_Stuff.DraconicEvolution ? new DraconicEvolutionItems() : null;
				avaritia = Configs.Custom.Mod_Stuff.Avaritia ? new AvaritiaItems() : null;
				ic2 = Configs.Custom.Mod_Stuff.IndustrialCraft ? new IndustrialCraft() : null;
			}catch(Throwable e) {
				MCS.instance.log.error("Has a error, this is message: ");
				e.printStackTrace();
			}
		}
	}

	public static final void registerOreDict() {
		for(BaseItemFood food : MCSResources.FOODS) {
			JiuUtils.item.registerCompressedOre(food.getUnCompressedName(), food, false);
		}
		for(BaseItemSub item : MCSResources.SUB_ITEMS) {
			JiuUtils.item.registerCompressedOre(item.getUnCompressedName(), item, false);
		}
	}

	public static class MinecraftItem {
		public final Normal normal = new Normal();
		public final Food food = new Food();
		public final Tool tool = new Tool();

		public class Normal {
			public final BaseItemSub C_STICK_I = new BaseItemSub("compressed_stick", new ItemStack(Items.STICK));
			public final BaseItemSub C_REEDS_I = new BaseItemSub("compressed_reeds", new ItemStack(Items.REEDS));
			public final BaseItemSub C_BLAZE_POWDER_I = new BaseItemSub("compressed_blaze_powder", new ItemStack(Items.BLAZE_POWDER));
			public final BaseItemSub C_GUNPOWDER_I = new BaseItemSub("compressed_gunpowder", new ItemStack(Items.GUNPOWDER));
			public final BaseItemSub C_DRAGON_BREATH_I = new BaseItemSub("compressed_dragon_breath", new ItemStack(Items.DRAGON_BREATH));
			public final BaseItemSub C_FEATHER_I = new BaseItemSub("compressed_feather", new ItemStack(Items.FEATHER));
			public final BaseItemSub C_STRING_I = new BaseItemSub("compressed_string", new ItemStack(Items.STRING));
			public final BaseItemSub C_MAGMA_CREAM_I = new BaseItemSub("compressed_magma_cream", new ItemStack(Items.MAGMA_CREAM));
			public final BaseItemSub C_GLASS_BOTTLE_I = new BaseItemSub("compressed_glass_bottle", new ItemStack(Items.GLASS_BOTTLE));
			public final BaseItemSub C_BOWL_I = new BaseItemSub("compressed_bowl", new ItemStack(Items.BOWL));
			public final BaseItemSub C_EGG_I = new CompressedEgg("compressed_egg", new ItemStack(Items.EGG));
			
			public final BaseItemSub C_cocoa_beans_I = new BaseItemSub("compressed_cocoa_beans", new ItemStack(Items.DYE, 1, 3));
			public final BaseItemSub C_cactus_green_I = new BaseItemSub("compressed_cactus_green", new ItemStack(Items.DYE, 1, 2));
			public final BaseItemSub C_ink_sac_I = new BaseItemSub("compressed_ink_sac", new ItemStack(Items.DYE, 1, 0));
			public final BaseItemSub C_seed_melon_I = new BaseItemSub("compressed_seed_melon", new ItemStack(Items.MELON_SEEDS));
			public final BaseItemSub C_seed_pumpkin_I = new BaseItemSub("compressed_seed_pumpkin", new ItemStack(Items.PUMPKIN_SEEDS));
			public final BaseItemSub C_seed_wheat_I = new BaseItemSub("compressed_seed_wheat", new ItemStack(Items.WHEAT_SEEDS));
			public final BaseItemSub C_seed_beetroot_I = new BaseItemSub("compressed_beetroot_wheat", new ItemStack(Items.BEETROOT_SEEDS));
			public final BaseItemSub C_sugar_I = new BaseItemSub("compressed_sugar", new ItemStack(Items.SUGAR));
			public final BaseItemSub C_ender_eye_I = new BaseItemSub("compressed_ender_eye", new ItemStack(Items.ENDER_EYE));
			public final BaseItemSub C_brick_I = new BaseItemSub("compressed_brick", new ItemStack(Items.BRICK));
			public final BaseItemSub C_dust_glowstone_I = new BaseItemSub("compressed_dust_glowstone", new ItemStack(Items.GLOWSTONE_DUST));
			public final BaseItemSub C_spider_eye_fermented_I = new BaseItemSub("compressed_spider_eye_fermented", new ItemStack(Items.FERMENTED_SPIDER_EYE));
			public final BaseItemSub C_rabbit_foot_I = new BaseItemSub("compressed_rabbit_foot", new ItemStack(Items.RABBIT_FOOT));
			public final BaseItemSub C_rabbit_hide_I = new BaseItemSub("compressed_rabbit_hide", new ItemStack(Items.RABBIT_HIDE));
			
		}

		public class Food {
			public final BaseItemFood C_BEEF_F = new BaseItemFood("compressed_beef", Items.BEEF);
			public final BaseItemFood C_COOKED_BEEF_F = new BaseItemFood("compressed_cooked_beef", Items.COOKED_BEEF);
			public final BaseItemFood C_CHICKEN_F = new BaseItemFood("compressed_chicken", Items.CHICKEN);
			public final BaseItemFood C_COOKED_CHICKEN_F = new BaseItemFood("compressed_cooked_chicken", Items.COOKED_CHICKEN);
			public final BaseItemFood C_POTATO_F = new BaseItemFood("compressed_potato", Items.POTATO);
			public final BaseItemFood C_BAKED_POTATO_F = new BaseItemFood("compressed_baked_potato", Items.BAKED_POTATO);
			public final BaseItemFood C_RABBIT_F = new BaseItemFood("compressed_rabbit", Items.RABBIT);
			public final BaseItemFood C_COOKED_RABBIT_F = new BaseItemFood("compressed_cooked_rabbit", Items.COOKED_RABBIT);
			public final BaseItemFood C_PORKCHOP_F = new BaseItemFood("compressed_porkchop", Items.PORKCHOP);
			public final BaseItemFood C_COOKED_PORKCHOP_F = new BaseItemFood("compressed_cooked_prokchop", Items.COOKED_PORKCHOP);
			public final BaseItemFood C_MUTTON_F = new BaseItemFood("compressed_mutton", Items.MUTTON);
			public final BaseItemFood C_COOKED_MUTTON_F = new BaseItemFood("compressed_cooked_mutton", Items.COOKED_MUTTON);
			public final BaseItemFood C_FISH_F = new BaseItemFood("compressed_fish", Items.FISH);
			public final BaseItemFood C_COOKED_FISH_F = new BaseItemFood("compressed_cooked_fish", Items.COOKED_FISH);
			public final BaseItemFood C_SALMON_FISH_F = new BaseItemFood("compressed_salmon_fish", Items.FISH, 1);
			public final BaseItemFood C_COOKED_SALMON_FISH_F = new BaseItemFood("compressed_cooked_salmon_fish", Items.COOKED_FISH, 1);
			public final BaseItemFood C_APPLE_F = new BaseItemFood("compressed_apple", Items.APPLE);
			public final BaseItemFood C_BREAD_F = new BaseItemFood("compressed_bread", Items.BREAD);
			public final BaseItemFood C_CLOWN_FISH_F = new BaseItemFood("compressed_clown_fish", Items.FISH, 2);
			public final BaseItemFood C_COOKIE_F = new BaseItemFood("compressed_cookie", Items.COOKIE);
			public final BaseItemFood C_ROTTEN_FLESH_F = new BaseItemFood("compressed_rotten_flesh", Items.ROTTEN_FLESH);
			public final BaseItemFood C_CARROT_F = new BaseItemFood("compressed_carrot", Items.CARROT);
			public final BaseItemFood C_GOLDEN_CARROT_F = new BaseItemFood("compressed_golden_carrot", Items.GOLDEN_CARROT);
			public final BaseItemFood C_PUMPKIN_PIE_F = new BaseItemFood("compressed_pumpkin_pie", Items.PUMPKIN_PIE);
			public final BaseItemFood C_BEETROOT_F = new BaseItemFood("compressed_beetroot", Items.BEETROOT);
			public final BaseItemFood C_SPECKLED_MELON_F = new BaseItemFood("compressed_speckled_melon", Items.SPECKLED_MELON)
					.setFoodEntry(3, 0.9F, false);
			public final BaseItemFood C_MUSHROOM_STEW_F = new BaseItemFood("compressed_mushron_stew", Items.MUSHROOM_STEW)
					.setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseItemFood C_RABBIT_STEW_F = new BaseItemFood("compressed_rabbit_stew", Items.RABBIT_STEW)
					.setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseItemFood C_BEETROOT_SOUP_F = new BaseItemFood("compressed_beetroot_soup", Items.BEETROOT_SOUP)
					.setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseItemFood C_PUFFER_FISH_F = new BaseItemFood("compressed_puffer_fish", Items.FISH, 3)
					.addPotionEffect(new int[]{-1}, new PotionEffectType[]{
							new PotionEffectType(MobEffects.POISON, 2280, 3),
							new PotionEffectType(MobEffects.HUNGER, 570, 2),
							new PotionEffectType(MobEffects.NAUSEA, 570, 1)});
			public final BaseItemFood C_GOLD_APPLE_F = new BaseItemFood("compressed_gold_apple", Items.GOLDEN_APPLE, 0)
					.addPotionEffect(new int[]{-1}, new PotionEffectType[]{
							new PotionEffectType(MobEffects.REGENERATION, 220, 1),
							new PotionEffectType(MobEffects.ABSORPTION, 4560, 0)});
			public final BaseItemFood C_ENCHANTED_GOLD_APPLE_F = new BaseItemFood("compressed_enchanted_gold_apple", Items.GOLDEN_APPLE, 1)
					.addPotionEffect(new int[]{-1}, new PotionEffectType[]{
							new PotionEffectType(MobEffects.REGENERATION, 760, 1),
							new PotionEffectType(MobEffects.RESISTANCE, 11400, 0),
							new PotionEffectType(MobEffects.FIRE_RESISTANCE, 11400, 0),
							new PotionEffectType(MobEffects.ABSORPTION, 4560, 3)});
			public final BaseItemFood C_SPIDER_EYE_F = new BaseItemFood("compressed_spider_eye", Items.SPIDER_EYE)
					.addPotionEffect(new int[]{-1}, new PotionEffectType[]{new PotionEffectType(MobEffects.POISON, 180, 1)})
					.setContainer(new ItemStack(C_ENCHANTED_GOLD_APPLE_F));
		}

		public class Tool {
			public final Sword sword = new Sword();
			public final Pickaxe pickaxe = new Pickaxe();
			public final Shovel shovel = new Shovel();
			public final Axe axe = new Axe();
			public final Hoe hoe = new Hoe();

			public class Sword {
				public final BaseItemSword C_DIAMOND_SWORD_I = new BaseItemSword(new ItemStack(Items.DIAMOND_SWORD), "compressed_diamond_sword");
				public final BaseItemSword C_IRON_SWORD_I = new BaseItemSword(new ItemStack(Items.IRON_SWORD), "compressed_iron_sword");
				public final BaseItemSword C_STONE_SWORD_I = new BaseItemSword(new ItemStack(Items.STONE_SWORD), "compressed_stone_sword");
				public final BaseItemSword C_WOODEN_SWORD_I = new BaseItemSword(new ItemStack(Items.WOODEN_SWORD), "compressed_wooden_sword");
				public final BaseItemSword C_GOLDEN_SWORD_I = new BaseItemSword(new ItemStack(Items.GOLDEN_SWORD), "compressed_golden_sword");

			}
			public class Pickaxe {
				public final BaseItemPickaxe C_DIAMOND_PICKAXE_I = new BaseItemPickaxe(new ItemStack(Items.DIAMOND_PICKAXE), "compressed_diamond_pickaxe");
				public final BaseItemPickaxe C_IRON_PICKAXE_I = new BaseItemPickaxe(new ItemStack(Items.IRON_PICKAXE), "compressed_iron_pickaxe");
				public final BaseItemPickaxe C_STONE_PICKAXE_I = new BaseItemPickaxe(new ItemStack(Items.STONE_PICKAXE), "compressed_stone_pickaxe");
				public final BaseItemPickaxe C_WOODEN_PICKAXE_I = new BaseItemPickaxe(new ItemStack(Items.WOODEN_PICKAXE), "compressed_wooden_pickaxe");
				public final BaseItemPickaxe C_GOLDEN_PICKAXE_I = new BaseItemPickaxe(new ItemStack(Items.GOLDEN_PICKAXE), "compressed_golden_pickaxe");

			}
			public class Shovel {
				public final BaseItemShovel C_DIAMOND_SHOVEL_I = new BaseItemShovel(new ItemStack(Items.DIAMOND_SHOVEL), "compressed_diamond_shovel");
				public final BaseItemShovel C_IRON_SHOVEL_I = new BaseItemShovel(new ItemStack(Items.IRON_SHOVEL), "compressed_iron_shovel");
				public final BaseItemShovel C_STONE_SHOVEL_I = new BaseItemShovel(new ItemStack(Items.STONE_SHOVEL), "compressed_stone_shovel");
				public final BaseItemShovel C_WOODEN_SHOVEL_I = new BaseItemShovel(new ItemStack(Items.WOODEN_SHOVEL), "compressed_wooden_shovel");
				public final BaseItemShovel C_GOLDEN_SHOVEL_I = new BaseItemShovel(new ItemStack(Items.GOLDEN_SHOVEL), "compressed_golden_shovel");

			}
			public class Axe {
				public final BaseItemAxe C_DIAMOND_AXE_I = new BaseItemAxe(new ItemStack(Items.DIAMOND_AXE), "compressed_diamond_axe");
				public final BaseItemAxe C_IRON_AXE_I = new BaseItemAxe(new ItemStack(Items.IRON_AXE), "compressed_iron_axe");
				public final BaseItemAxe C_STONE_AXE_I = new BaseItemAxe(new ItemStack(Items.STONE_AXE), "compressed_stone_axe");
				public final BaseItemAxe C_WOODEN_AXE_I = new BaseItemAxe(new ItemStack(Items.WOODEN_AXE), "compressed_wooden_axe");
				public final BaseItemAxe C_GOLDEN_AXE_I = new BaseItemAxe(new ItemStack(Items.GOLDEN_AXE), "compressed_golden_axe");

			}
			public class Hoe {
				public final BaseItemHoe C_DIAMOND_HOE_I = new BaseItemHoe(new ItemStack(Items.DIAMOND_HOE), "compressed_diamond_hoe");
				public final BaseItemHoe C_IRON_HOE_I = new BaseItemHoe(new ItemStack(Items.IRON_HOE), "compressed_iron_hoe");
				public final BaseItemHoe C_STONE_HOE_I = new BaseItemHoe(new ItemStack(Items.STONE_HOE), "compressed_stone_hoe");
				public final BaseItemHoe C_WOODEN_HOE_I = new BaseItemHoe(new ItemStack(Items.WOODEN_HOE), "compressed_wooden_hoe");
				public final BaseItemHoe C_GOLDEN_HOE_I = new BaseItemHoe(new ItemStack(Items.GOLDEN_HOE), "compressed_golden_hoe");

			}
		}
	}
	
	public static class OreStuff {
		public final Plate plate = new Plate();
		public final Dust dust = new Dust();
		public final Gear gear = new Gear();
		
		public class Plate {
			public final BaseItemSub C_PLATE_ALUMINUM_I;
			public final BaseItemSub C_PLATE_BRONZE_I;
			public final BaseItemSub C_PLATE_CONSTANTAN_I;
			public final BaseItemSub C_PLATE_COPPER_I;
			public final BaseItemSub C_PLATE_ELECTRUM_I;
			public final BaseItemSub C_PLATE_GOLD_I;
			public final BaseItemSub C_PLATE_INVAR_I;
			public final BaseItemSub C_PLATE_IRIDIUM_I;
			public final BaseItemSub C_PLATE_IRON_I;
			public final BaseItemSub C_PLATE_LEAD_I;
			public final BaseItemSub C_PLATE_MITHRIL_I;
			public final BaseItemSub C_PLATE_NICKL_I;
			public final BaseItemSub C_PLATE_PLATINUM_I;
			public final BaseItemSub C_PLATE_SIGNALUM_I;
			public final BaseItemSub C_PLATE_SILVER_I;
			public final BaseItemSub C_PLATE_STEEL_I;
			public final BaseItemSub C_PLATE_TIN_I;
			public Plate() {
				if(Configs.Custom.Mod_Stuff.ThermalFoundation) {
					
				}else if(Configs.Custom.Mod_Stuff.IndustrialCraft) {
					
				}else {
					
				}
				C_PLATE_ALUMINUM_I = null;
				C_PLATE_BRONZE_I = null;
				C_PLATE_CONSTANTAN_I = null;
				C_PLATE_COPPER_I = null;
				C_PLATE_ELECTRUM_I = null;
				C_PLATE_GOLD_I = null;
				C_PLATE_INVAR_I = null;
				C_PLATE_IRIDIUM_I = null;
				C_PLATE_IRON_I = null;
				C_PLATE_LEAD_I = null;
				C_PLATE_MITHRIL_I = null;
				C_PLATE_NICKL_I = null;
				C_PLATE_PLATINUM_I = null;
				C_PLATE_SIGNALUM_I = null;
				C_PLATE_SILVER_I = null;
				C_PLATE_STEEL_I = null;
				C_PLATE_TIN_I = null;
			}
		}
		public class Dust {
			public final BaseItemSub C_GEAR_ALUMINUM_I;
			public final BaseItemSub C_GEAR_BRONZE_I;
			public final BaseItemSub C_GEAR_CONSTANTAN_I;
			public final BaseItemSub C_GEAR_COPPER_I;
			public final BaseItemSub C_GEAR_DIAMOND_I;
			public final BaseItemSub C_GEAR_ELECTRUM_I;
			public final BaseItemSub C_GEAR_EMERALD_I;
			public final BaseItemSub C_GEAR_GOLD_I;
			public final BaseItemSub C_GEAR_INVAR_I;
			public final BaseItemSub C_GEAR_IRIDIUM_I;
			public final BaseItemSub C_GEAR_IRON_I;
			public final BaseItemSub C_GEAR_LEAD_I;
			public final BaseItemSub C_GEAR_MITHRIL_I;
			public final BaseItemSub C_GEAR_NICKEL_I;
			public final BaseItemSub C_GEAR_PLATINUM_I;
			public final BaseItemSub C_GEAR_SIGNALUM_I;
			public final BaseItemSub C_GEAR_SILVER_I;
			public final BaseItemSub C_GEAR_STEEL_I;
			public final BaseItemSub C_GEAR_STONE_I;
			public final BaseItemSub C_GEAR_TIN_I;
			public final BaseItemSub C_GEAR_WOOD_I;
			
			public Dust() {
				C_GEAR_ALUMINUM_I = null;
				C_GEAR_BRONZE_I = null;
				C_GEAR_CONSTANTAN_I = null;
				C_GEAR_COPPER_I = null;
				C_GEAR_DIAMOND_I = null;
				C_GEAR_ELECTRUM_I = null;
				C_GEAR_EMERALD_I = null;
				C_GEAR_GOLD_I = null;
				C_GEAR_INVAR_I = null;
				C_GEAR_IRIDIUM_I = null;
				C_GEAR_IRON_I = null;
				C_GEAR_LEAD_I = null;
				C_GEAR_MITHRIL_I = null;
				C_GEAR_NICKEL_I = null;
				C_GEAR_PLATINUM_I = null;
				C_GEAR_SIGNALUM_I = null;
				C_GEAR_SILVER_I = null;
				C_GEAR_STEEL_I = null;
				C_GEAR_STONE_I = null;
				C_GEAR_TIN_I = null;
				C_GEAR_WOOD_I = null;
			}
		}
		public class Gear {
			public final BaseItemSub C_DUST_ALUMINUM_I;
			public final BaseItemSub C_DUST_BRONZE_I;
			public final BaseItemSub C_DUST_CONSTANTAN_I;
			public final BaseItemSub C_DUST_COPPER_I;
			public final BaseItemSub C_DUST_ELECTRUM_I;
			public final BaseItemSub C_DUST_GOLD_I;
			public final BaseItemSub C_DUST_INVAR_I;
			public final BaseItemSub C_DUST_IRIDIUM_I;
			public final BaseItemSub C_DUST_IRON_I;
			public final BaseItemSub C_DUST_LEAD_I;
			public final BaseItemSub C_DUST_MITHRIL_I;
			public final BaseItemSub C_DUST_NICKL_I;
			public final BaseItemSub C_DUST_PLATINUM_I;
			public final BaseItemSub C_DUST_SIGNALUM_I;
			public final BaseItemSub C_DUST_SILVER_I;
			public final BaseItemSub C_DUST_STEEL_I;
			public final BaseItemSub C_DUST_TIN_I;
			public Gear() {
				C_DUST_ALUMINUM_I = null;
				C_DUST_BRONZE_I = null;
				C_DUST_CONSTANTAN_I = null;
				C_DUST_COPPER_I = null;
				C_DUST_ELECTRUM_I = null;
				C_DUST_GOLD_I = null;
				C_DUST_INVAR_I = null;
				C_DUST_IRIDIUM_I = null;
				C_DUST_IRON_I = null;
				C_DUST_LEAD_I = null;
				C_DUST_MITHRIL_I = null;
				C_DUST_NICKL_I = null;
				C_DUST_PLATINUM_I = null;
				C_DUST_SIGNALUM_I = null;
				C_DUST_SILVER_I = null;
				C_DUST_STEEL_I = null;
				C_DUST_TIN_I = null;
			}
		}
	}

	public static class ThermalFoundationItem {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseItemSub C_DYE_I= register("compressed_tf_dye", TFItems.itemDye.dyeWhite);
			public final BaseItemSub C_SAWDUST_I = register("compressed_wood_dust", TFItems.itemMaterial.dustWoodCompressed);
			// Crystal 络合物
			public final BaseItemSub C_CRYSTAL_ENDER_I= register("compressed_crystal_ender", TFItems.itemMaterial.crystalEnder);
			public final BaseItemSub C_CRYSTAL_GLOWSTONE_I = register("compressed_crystal_glowstone", TFItems.itemMaterial.crystalGlowstone);
			public final BaseItemSub C_CRYSTAL_REDSTONE_I = register("compressed_crystal_redstone", TFItems.itemMaterial.crystalRedstone);

			// Slag 炉渣
			public final BaseItemSub C_CRYSTAL_SLAG_I = register("compressed_crystal_slag", TFItems.itemMaterial.crystalSlag);
			public final BaseItemSub C_CRYSTAL_SLAG_RICH_I = register("compressed_crystal_slag_rich", TFItems.itemMaterial.crystalSlagRich);
			public final BaseItemSub C_CRYSTAL_CINNABAR_I = register("compressed_crystal_cinnabar", TFItems.itemMaterial.crystalCinnabar);

			// Rod 棒子
			public final BaseItemSub C_ROD_BASALZ_I = register("compressed_rod_basalz", TFItems.itemMaterial.rodBasalz);
			public final BaseItemSub C_ROD_BLITZ_I = register("compressed_rod_blitz", TFItems.itemMaterial.rodBlitz);
			public final BaseItemSub C_ROD_BLIZZ_I = register("compressed_rod_blizz", TFItems.itemMaterial.rodBlizz);

			// Plate 板子
			public final BaseItemSub C_PLATE_LUMIUM_I;
			public final BaseItemSub C_PLATE_ENDERIUM_I;
			@Deprecated public final BaseItemSub C_PLATE_ALUMINUM_I;
			@Deprecated public final BaseItemSub C_PLATE_BRONZE_I;
			@Deprecated public final BaseItemSub C_PLATE_CONSTANTAN_I;
			@Deprecated public final BaseItemSub C_PLATE_COPPER_I;
			@Deprecated public final BaseItemSub C_PLATE_ELECTRUM_I;
			@Deprecated public final BaseItemSub C_PLATE_GOLD_I;
			@Deprecated public final BaseItemSub C_PLATE_INVAR_I;
			@Deprecated public final BaseItemSub C_PLATE_IRIDIUM_I;
			@Deprecated public final BaseItemSub C_PLATE_IRON_I;
			@Deprecated public final BaseItemSub C_PLATE_LEAD_I;
			@Deprecated public final BaseItemSub C_PLATE_MITHRIL_I;
			@Deprecated public final BaseItemSub C_PLATE_NICKL_I;
			@Deprecated public final BaseItemSub C_PLATE_PLATINUM_I;
			@Deprecated public final BaseItemSub C_PLATE_SIGNALUM_I;
			@Deprecated public final BaseItemSub C_PLATE_SILVER_I;
			@Deprecated public final BaseItemSub C_PLATE_STEEL_I;
			@Deprecated public final BaseItemSub C_PLATE_TIN_I;

			// Gear 齿轮
			public final BaseItemSub C_GEAR_LUMIUM_I;
			public final BaseItemSub C_GEAR_ENDERIUM_I;
			@Deprecated public final BaseItemSub C_GEAR_ALUMINUM_I;
			@Deprecated public final BaseItemSub C_GEAR_BRONZE_I;
			@Deprecated public final BaseItemSub C_GEAR_CONSTANTAN_I;
			@Deprecated public final BaseItemSub C_GEAR_COPPER_I;
			@Deprecated public final BaseItemSub C_GEAR_DIAMOND_I;
			@Deprecated public final BaseItemSub C_GEAR_ELECTRUM_I;
			@Deprecated public final BaseItemSub C_GEAR_EMERALD_I;
			@Deprecated public final BaseItemSub C_GEAR_GOLD_I;
			@Deprecated public final BaseItemSub C_GEAR_INVAR_I;
			@Deprecated public final BaseItemSub C_GEAR_IRIDIUM_I;
			@Deprecated public final BaseItemSub C_GEAR_IRON_I;
			@Deprecated public final BaseItemSub C_GEAR_LEAD_I;
			@Deprecated public final BaseItemSub C_GEAR_MITHRIL_I;
			@Deprecated public final BaseItemSub C_GEAR_NICKEL_I;
			@Deprecated public final BaseItemSub C_GEAR_PLATINUM_I;
			@Deprecated public final BaseItemSub C_GEAR_SIGNALUM_I;
			@Deprecated public final BaseItemSub C_GEAR_SILVER_I;
			@Deprecated public final BaseItemSub C_GEAR_STEEL_I;
			@Deprecated public final BaseItemSub C_GEAR_STONE_I;
			@Deprecated public final BaseItemSub C_GEAR_TIN_I;
			@Deprecated public final BaseItemSub C_GEAR_WOOD_I;

			// Dust 金属粉
			public final BaseItemSub C_DUST_LUMIUM_I;
			public final BaseItemSub C_DUST_ENDERIUM_I;
			@Deprecated public final BaseItemSub C_DUST_ALUMINUM_I;
			@Deprecated public final BaseItemSub C_DUST_BRONZE_I;
			@Deprecated public final BaseItemSub C_DUST_CONSTANTAN_I;
			@Deprecated public final BaseItemSub C_DUST_COPPER_I;
			@Deprecated public final BaseItemSub C_DUST_ELECTRUM_I;
			@Deprecated public final BaseItemSub C_DUST_GOLD_I;
			@Deprecated public final BaseItemSub C_DUST_INVAR_I;
			@Deprecated public final BaseItemSub C_DUST_IRIDIUM_I;
			@Deprecated public final BaseItemSub C_DUST_IRON_I;
			@Deprecated public final BaseItemSub C_DUST_LEAD_I;
			@Deprecated public final BaseItemSub C_DUST_MITHRIL_I;
			@Deprecated public final BaseItemSub C_DUST_NICKL_I;
			@Deprecated public final BaseItemSub C_DUST_PLATINUM_I;
			@Deprecated public final BaseItemSub C_DUST_SIGNALUM_I;
			@Deprecated public final BaseItemSub C_DUST_SILVER_I;
			@Deprecated public final BaseItemSub C_DUST_STEEL_I;
			@Deprecated public final BaseItemSub C_DUST_TIN_I;

			public Normal() {
				// Plate 板子
				C_PLATE_LUMIUM_I = register("compressed_plate_lumium", TFItems.itemMaterial.plateLumium).setModelMaterial("plate");
				C_PLATE_ENDERIUM_I = register("compressed_plate_enderium", TFItems.itemMaterial.plateEnderium).setModelMaterial("plate");
				C_PLATE_ALUMINUM_I = register("compressed_plate_aluminum", TFItems.itemMaterial.plateAluminum).setModelMaterial("plate");
				C_PLATE_BRONZE_I = register("compressed_plate_bronze", TFItems.itemMaterial.plateBronze).setModelMaterial("plate");
				C_PLATE_CONSTANTAN_I = register("compressed_plate_constantan", TFItems.itemMaterial.plateConstantan).setModelMaterial("plate");
				C_PLATE_COPPER_I = register("compressed_plate_copper", TFItems.itemMaterial.plateCopper).setModelMaterial("plate");
				C_PLATE_ELECTRUM_I = register("compressed_plate_electrum", TFItems.itemMaterial.plateElectrum).setModelMaterial("plate");
				C_PLATE_GOLD_I = register("compressed_plate_gold", TFItems.itemMaterial.plateGold).setModelMaterial("plate");
				C_PLATE_INVAR_I = register("compressed_plate_invar", TFItems.itemMaterial.plateInvar).setModelMaterial("plate");
				C_PLATE_IRIDIUM_I = register("compressed_plate_iridium", TFItems.itemMaterial.plateIridium).setModelMaterial("plate");
				C_PLATE_IRON_I = register("compressed_plate_iron", TFItems.itemMaterial.plateIron).setModelMaterial("plate");
				C_PLATE_LEAD_I = register("compressed_plate_lead", TFItems.itemMaterial.plateLead).setModelMaterial("plate");
				C_PLATE_MITHRIL_I = register("compressed_plate_mithri", TFItems.itemMaterial.plateMithril).setModelMaterial("plate");
				C_PLATE_NICKL_I = register("compressed_plate_nickel", TFItems.itemMaterial.plateNickel).setModelMaterial("plate");
				C_PLATE_PLATINUM_I = register("compressed_plate_platinum", TFItems.itemMaterial.platePlatinum).setModelMaterial("plate");
				C_PLATE_SIGNALUM_I = register("compressed_plate_signalum", TFItems.itemMaterial.plateSignalum).setModelMaterial("plate");
				C_PLATE_SILVER_I = register("compressed_plate_silver", TFItems.itemMaterial.plateSilver).setModelMaterial("plate");
				C_PLATE_STEEL_I = register("compressed_plate_steel", TFItems.itemMaterial.plateSteel).setModelMaterial("plate");
				C_PLATE_TIN_I = register("compressed_plate_tin", TFItems.itemMaterial.plateTin).setModelMaterial("plate");

				// Gear 齿轮
				C_GEAR_LUMIUM_I = register("compressed_gear_lumium", TFItems.itemMaterial.gearLumium).setModelMaterial("gear");
				C_GEAR_ENDERIUM_I = register("compressed_gear_enderium", TFItems.itemMaterial.gearEnderium).setModelMaterial("gear");
				C_GEAR_ALUMINUM_I = register("compressed_gear_aluminum", TFItems.itemMaterial.gearAluminum).setModelMaterial("gear");
				C_GEAR_BRONZE_I = register("compressed_gear_bronze", TFItems.itemMaterial.gearBronze).setModelMaterial("gear");
				C_GEAR_CONSTANTAN_I = register("compressed_gear_constantan", TFItems.itemMaterial.gearConstantan).setModelMaterial("gear");
				C_GEAR_COPPER_I = register("compressed_gear_copper", TFItems.itemMaterial.gearCopper).setModelMaterial("gear");
				C_GEAR_DIAMOND_I = register("compressed_gear_diamond", TFItems.itemMaterial.gearDiamond).setModelMaterial("gear");
				C_GEAR_ELECTRUM_I = register("compressed_gear_electrum", TFItems.itemMaterial.gearElectrum).setModelMaterial("gear");
				C_GEAR_EMERALD_I = register("compressed_gear_emerald", TFItems.itemMaterial.gearEmerald).setModelMaterial("gear");
				C_GEAR_GOLD_I = register("compressed_gear_gold", TFItems.itemMaterial.gearGold).setModelMaterial("gear");
				C_GEAR_INVAR_I = register("compressed_gear_invar", TFItems.itemMaterial.gearInvar).setModelMaterial("gear");
				C_GEAR_IRIDIUM_I = register("compressed_gear_iridium", TFItems.itemMaterial.gearIridium).setModelMaterial("gear");
				C_GEAR_IRON_I = register("compressed_gear_iron", TFItems.itemMaterial.gearIron).setModelMaterial("gear");
				C_GEAR_LEAD_I = register("compressed_gear_lead", TFItems.itemMaterial.gearLead).setModelMaterial("gear");
				C_GEAR_MITHRIL_I = register("compressed_gear_mithril", TFItems.itemMaterial.gearMithril).setModelMaterial("gear");
				C_GEAR_NICKEL_I = register("compressed_gear_nickel", TFItems.itemMaterial.gearNickel).setModelMaterial("gear");
				C_GEAR_PLATINUM_I = register("compressed_gear_platinum", TFItems.itemMaterial.gearPlatinum).setModelMaterial("gear");
				C_GEAR_SIGNALUM_I = register("compressed_gear_signalum", TFItems.itemMaterial.gearSignalum).setModelMaterial("gear");
				C_GEAR_SILVER_I = register("compressed_gear_silver", TFItems.itemMaterial.gearSilver).setModelMaterial("gear");
				C_GEAR_STEEL_I = register("compressed_gear_steel", TFItems.itemMaterial.gearSteel).setModelMaterial("gear");
				C_GEAR_STONE_I = register("compressed_gear_stone", TFItems.itemMaterial.gearStone).setModelMaterial("gear");
				C_GEAR_TIN_I = register("compressed_gear_tin", TFItems.itemMaterial.gearTin).setModelMaterial("gear");
				C_GEAR_WOOD_I = register("compressed_gear_wood", TFItems.itemMaterial.gearWood).setModelMaterial("gear");

				// Dust 金属粉
				C_DUST_ENDERIUM_I = register("compressed_dust_enderium", TFItems.itemMaterial.dustEnderium).setModelMaterial("dust");
				C_DUST_LUMIUM_I = register("compressed_dust_lumium", TFItems.itemMaterial.dustLumium).setModelMaterial("dust");
				C_DUST_ALUMINUM_I = register("compressed_dust_aluminum", TFItems.itemMaterial.dustAluminum).setModelMaterial("dust");
				C_DUST_BRONZE_I = register("compressed_dust_bronze", TFItems.itemMaterial.dustBronze).setModelMaterial("dust");
				C_DUST_CONSTANTAN_I = register("compressed_dust_constantan", TFItems.itemMaterial.dustConstantan).setModelMaterial("dust");
				C_DUST_COPPER_I = register("compressed_dust_copper", TFItems.itemMaterial.dustCopper).setModelMaterial("dust");
				C_DUST_ELECTRUM_I = register("compressed_dust_electrum", TFItems.itemMaterial.dustElectrum).setModelMaterial("dust");
				C_DUST_GOLD_I = register("compressed_dust_gold", TFItems.itemMaterial.dustGold).setModelMaterial("dust");
				C_DUST_INVAR_I = register("compressed_dust_invar", TFItems.itemMaterial.dustInvar).setModelMaterial("dust");
				C_DUST_IRIDIUM_I = register("compressed_dust_iridium", TFItems.itemMaterial.dustIridium).setModelMaterial("dust");
				C_DUST_IRON_I = register("compressed_dust_iron", TFItems.itemMaterial.dustIron).setModelMaterial("dust");
				C_DUST_LEAD_I = register("compressed_dust_lead", TFItems.itemMaterial.dustLead).setModelMaterial("dust");
				C_DUST_MITHRIL_I = register("compressed_dust_mithril", TFItems.itemMaterial.dustMithril).setModelMaterial("dust");
				C_DUST_NICKL_I = register("compressed_dust_nickel", TFItems.itemMaterial.dustNickel).setModelMaterial("dust");
				C_DUST_PLATINUM_I = register("compressed_dust_platinum", TFItems.itemMaterial.dustPlatinum).setModelMaterial("dust");
				C_DUST_SIGNALUM_I = register("compressed_dust_signalum", TFItems.itemMaterial.dustSignalum).setModelMaterial("dust");
				C_DUST_SILVER_I = register("compressed_dust_silver", TFItems.itemMaterial.dustSilver).setModelMaterial("dust");
				C_DUST_STEEL_I = register("compressed_dust_steel", TFItems.itemMaterial.dustSteel).setModelMaterial("dust");
				C_DUST_TIN_I = register("compressed_dust_tin", TFItems.itemMaterial.dustTin).setModelMaterial("dust");

			}
		}

		private BaseItemSub register(String name, ItemStack baseItem) {
			return BaseItemSub.register(name, baseItem, "thermalfoundation");
		}
	}

	public static class DraconicEvolutionItems {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseItemSub C_CORE_DRACONIC_I = register("compressed_core_draconic", new ItemStack(DEFeatures.draconicCore));
			public final BaseItemSub C_CORE_WYVERN_I = register("compressed_core_wyvern", new ItemStack(DEFeatures.wyvernCore));
			public final BaseItemSub C_CORE_AWAKENED_I = register("compressed_core_awakened", new ItemStack(DEFeatures.awakenedCore));
			public final BaseItemSub C_CORE_CHAOTIC_I = register("compressed_core_chaotic", new ItemStack(DEFeatures.chaoticCore));

			public final BaseItemSub C_CORE_ENERGY_WYVERN_I = register("compressed_energy_core_wyvern", new ItemStack(DEFeatures.wyvernEnergyCore));
			public final BaseItemSub C_CORE_ENERGY_AWAKENED_I = register("compressed_energy_core_awakened", new ItemStack(DEFeatures.draconicEnergyCore));

			public final BaseItemSub C_CHAOS_SHARD_I = register("compressed_chaos_shard", new ItemStack(DEFeatures.chaosShard));
			public final BaseItemSub C_DRAGON_HEART_I = register("compressed_dragon_heart", new ItemStack(DEFeatures.dragonHeart));
			public final BaseItemSub C_DRACONIUM_DUST_I = register("compressed_draconium_dust", new ItemStack(DEFeatures.draconiumDust));
		}

		private BaseItemSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseItemSub.register(nameIn, unCompressedItem, "draconicevolution");
		}
	}

	public static class AvaritiaItems {
		protected static final morph.avaritia.init.ModItems items = new morph.avaritia.init.ModItems();
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseItemFood C_COSMIC_MEATBALLS_I = new BaseItemFood("compressed_cosmic_meatballs", items.cosmic_meatballs, 0, "avaritia")
					.addPotionEffect(new int[]{-1}, new PotionEffectType[]{new PotionEffectType(MobEffects.STRENGTH, 760, 2)});
		}

		@SuppressWarnings("unused")
		private BaseItemSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseItemSub.register(nameIn, unCompressedItem, "avaritia");
		}
	}
	
	public static class IndustrialCraft {
		public final Normal normal = new Normal();
		
		public class Normal {
			public final BaseItemSub C_URANIUM_I = register("compressed_uranium", "nuclear", true);
			public final BaseItemSub C_URANIUM_235_I = register("compressed_uranium_235", "nuclear", 1, true);
			public final BaseItemSub C_URANIUM_238_I = register("compressed_uranium_238", "nuclear", 2, true);
			public final BaseItemSub C_plutonium_I = register("compressed_plutonium", "nuclear", 3, true);
			public final BaseItemSub C_MOX_I = register("compressed_mox", "nuclear", 4, true);
			public final BaseItemSub C_URANIUM_PELLET_I = register("compressed_uranium_pellet", "nuclear", 8, true);
			public final BaseItemSub C_MOX_PELLET_I = register("compressed_mox_pellet", "nuclear", 9, true);
			public final BaseItemSub C_RTG_PELLET_I = register("compressed_rtg_pellet", "nuclear", 10, true);
			
			//IC Dust
			public final BaseItemSub C_dust_clay_I = register("compressed_dust_clay", "dust", 1).setModelMaterial("dust");
			public final BaseItemSub C_dust_coal_I = register("compressed_dust_coal", "dust", 2).setModelMaterial("dust");
			public final BaseItemSub C_dust_coal_fuel_I = register("compressed_dust_coal_fuel", "dust", 3).setModelMaterial("dust");
			public final BaseItemSub C_dust_diamond_I = register("compressed_dust_diamond", "dust", 5).setModelMaterial("dust");
			public final BaseItemSub C_dust_energium_I = register("compressed_dust_energium", "dust", 6).setModelMaterial("dust");
			public final BaseItemSub C_dust_lapis_I = register("compressed_dust_lapis", "dust", 9).setModelMaterial("dust");
			public final BaseItemSub C_dust_lithium_I = register("compressed_dust_lithium", "dust", 11).setModelMaterial("dust");
			public final BaseItemSub C_dust_obsidian_I = register("compressed_dust_obsidian", "dust", 12).setModelMaterial("dust");
			public final BaseItemSub C_dust_silicon_dioxide_I = register("compressed_dust_silicon_dioxide", "dust", 13).setModelMaterial("dust");
			public final BaseItemSub C_dust_stone_I = register("compressed_dust_stone", "dust", 15).setModelMaterial("dust");
			public final BaseItemSub C_dust_sulfur_I = register("compressed_dust_sulfur", "dust", 16).setModelMaterial("dust");
			public final BaseItemSub C_dust_tin_hydrated_I = register("compressed_dust_tin_hydrated", "dust", 29).setModelMaterial("dust");
			public final BaseItemSub C_dust_cf_powder_I = register("compressed_dust_cf_powder", "crafting", 25).setModelMaterial("dust");
			public final BaseItemSub C_dust_coffee_powder_I = register("compressed_dust_coffee_powder", "crop_res", 1).setModelMaterial("dust");
			public final BaseItemSub C_dust_fertilizer_I = register("compressed_dust_fertilizer", "crop_res", 2).setModelMaterial("dust");
			public final BaseItemSub C_dust_grin_powder_I = register("compressed_dust_grin_powder", "crop_res", 3).setModelMaterial("dust");
			public final BaseItemSub C_dust_ashes_I = register("compressed_dust_ashes", "misc_resource").setModelMaterial("dust");
			
			//IC Plate
			public final BaseItemSub C_plate_lapis_I = register("compressed_plate_lapis", "plate", 4).setModelMaterial("plate");
			public final BaseItemSub C_plate_alloy_I = register("compressed_plate_alloy", "crafting", 3).setModelMaterial("plate");
			public final BaseItemSub C_plate_obsidian_I = register("compressed_plate_obsidian", "plate", 6).setModelMaterial("plate");
			public final BaseItemSub C_plate_iridium_reinforcing_I = register("compressed_plate_iridium_reinforcing", "crafting", 4).setModelMaterial("plate");
			public final BaseItemSub C_dense_plate_bronze_I = register("compressed_dense_plate_bronze", "plate", 9).setModelMaterial("plate/dense");
			public final BaseItemSub C_dense_plate_copper_I = register("compressed_dense_plate_copper", "plate", 10).setModelMaterial("plate/dense");
			public final BaseItemSub C_dense_plate_gold_I = register("compressed_dense_plate_gold", "plate", 11).setModelMaterial("plate/dense");
			public final BaseItemSub C_dense_plate_iron_I = register("compressed_dense_plate_iron", "plate", 12).setModelMaterial("plate/dense");
			public final BaseItemSub C_dense_plate_lapis_I = register("compressed_dense_plate_lapis", "plate", 13).setModelMaterial("plate/dense");
			public final BaseItemSub C_dense_plate_lead_I = register("compressed_dense_plate_lead", "plate", 14).setModelMaterial("plate/dense");
			public final BaseItemSub C_dense_plate_obsidian_I = register("compressed_dense_plate_obsidian", "plate", 15).setModelMaterial("plate/dense");
			public final BaseItemSub C_dense_plate_steel_I = register("compressed_dense_plate_steel", "plate", 16).setModelMaterial("plate/dense");
			public final BaseItemSub C_dense_plate_tin_I = register("compressed_dense_plate_tin", "plate", 17).setModelMaterial("plate/dense");
			
			//IC Casing
			public final BaseItemSub C_casing_bronze_I = register("compressed_casing_bronze", "casing", 0).setModelMaterial("casing");
			public final BaseItemSub C_casing_copper_I = register("compressed_casing_copper", "casing", 1).setModelMaterial("casing");
			public final BaseItemSub C_casing_gold_I = register("compressed_casing_gold", "casing", 2).setModelMaterial("casing");
			public final BaseItemSub C_casing_iron_I = register("compressed_casing_iron", "casing", 3).setModelMaterial("casing");
			public final BaseItemSub C_casing_lead_I = register("compressed_casing_lead", "casing", 4).setModelMaterial("casing");
			public final BaseItemSub C_casing_steel_I = register("compressed_casing_steel", "casing", 5).setModelMaterial("casing");
			public final BaseItemSub C_casing_tin_I = register("compressed_casing_tin", "casing", 6).setModelMaterial("casing");
			
			//IC Normal 
			public final BaseItemSub C_rubber_I = register("compressed_rubber", "crafting", 0);
			public final BaseItemSub C_circuit_I = register("compressed_circuit", "crafting", 1);
			public final BaseItemSub C_advanced_circuit_I = register("compressed_advanced_circuit", "crafting", 2);
			public final BaseItemSub C_coil_I = register("compressed_coil", "crafting", 5);
			public final BaseItemSub C_electric_motor_I = register("compressed_electric_motor", "crafting", 6);
			public final BaseItemSub C_heat_conductor_I = register("compressed_heat_conductor", "crafting", 7);
			public final BaseItemSub C_copper_boiler_I = register("compressed_copper_boiler", "crafting", 8);
			public final BaseItemSub C_coal_chunk_I = register("compressed_coal_chunk", "crafting", 18);
			public final BaseItemSub C_plant_ball_I = register("compressed_plant_ball", "crafting", 20);
			public final BaseItemSub C_scrap_I = register("compressed_scrap", "crafting", 23);
			public final BaseItemSub C_ic_coin_I = register("compressed_ic_coin", "crafting", 38);
			public final BaseItemSub C_iridium_ore_I = register("compressed_iridium_ore", "misc_resource", 1);
			public final BaseItemSub C_matter_I = register("compressed_uu_matter", "misc_resource", 3);
			public final BaseItemSub C_resin_I = register("compressed_resin", "misc_resource", 4);
			public final BaseItemSub C_slag_I = register("compressed_slag", "misc_resource", 5);
			public final BaseItemSub C_iodine_I = register("compressed_iodine", "misc_resource", 6);
			public final BaseItemSub C_fuel_rod_I = register("compressed_fuel_rod", "crafting", 9);
			
			public final BaseItemSub C_terra_wart_I = register("compressed_terra_wart", "terra_wart");
			public final BaseItemSub C_heat_plating_I = register("compressed_heat_plating", "heat_plating");
			public final BaseItemSub C_plating_I = register("compressed_plating", "plating");
			
			//枯竭燃料棒
			public final BaseItemSub C_DEPLETED_URANIUM_I = register("compressed_depleted_uranium", "nuclear", 11, true);
			public final BaseItemSub C_DEPLETED_DUAL_URANIUM_I = register("compressed_depleted_dual_uranium", "nuclear", 12, true);
			public final BaseItemSub C_DEPLETED_QUAD_URANIUM_I = register("compressed_depleted_quad_uranium", "nuclear", 13, true);
			//枯竭燃料棒(mox)
			public final BaseItemSub C_DEPLETED_MOX_I = register("compressed_depleted_mox", "nuclear", 14, true);
			public final BaseItemSub C_DEPLETED_DUAL_MOX_I = register("compressed_depleted_dual_mox", "nuclear", 15, true);
			public final BaseItemSub C_DEPLETED_QUAD_MOX_I = register("compressed_depleted_quad_mox", "nuclear", 16, true);
			
			// 燃料棒
			public final BaseItemSub C_URANIUM_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_uranium_fuel_rod", "ic2:uranium_fuel_rod", C_DEPLETED_URANIUM_I);
			public final BaseItemSub C_DUAL_URANIUM_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_dual_uranium_fuel_rod", "ic2:dual_uranium_fuel_rod", C_DEPLETED_DUAL_URANIUM_I);
			public final BaseItemSub C_QUAD_URANIUM_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_quad_uranium_fuel_rod", "ic2:quad_uranium_fuel_rod", C_DEPLETED_QUAD_URANIUM_I);
			// 燃料棒(mox)
			public final BaseItemSub C_MOX_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_mox_fuel_rod", "ic2:mox_fuel_rod", C_DEPLETED_MOX_I);
			public final BaseItemSub C_DUAL_MOX_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_dual_mox_fuel_rod", "ic2:dual_mox_fuel_rod", C_DEPLETED_DUAL_MOX_I);
			public final BaseItemSub C_QUAD_MOX_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_quad_mox_fuel_rod", "ic2:quad_mox_fuel_rod", C_DEPLETED_QUAD_MOX_I);
			
			public final BaseItemSub C_energy_crystal_I = new ICEnergyCrystal("compressed_energy_crystal", "energy_crystal");
			public final BaseItemSub C_lapotron_crystal_I = new ICEnergyCrystal("compressed_lapotron_crystal", "lapotron_crystal");
			public final BaseItemSub C_re_battery_I = new ICEnergyCrystal("compressed_re_battery", "re_battery");
			public final BaseItemSub C_advanced_re_battery_I = new ICEnergyCrystal("compressed_advanced_re_battery", "advanced_re_battery");
			
			public final BaseItemSub C_charging_re_battery_I = new ICBatteryCharge("compressed_charging_re_battery", "charging_re_battery");
			public final BaseItemSub C_charging_advanced_re_battery_I = new ICBatteryCharge("compressed_charging_advanced_re_battery", "advanced_charging_re_battery");
			public final BaseItemSub C_charging_energy_crystal_I = new ICBatteryCharge("compressed_charging_energy_crystal", "charging_energy_crystal");
			public final BaseItemSub C_charging_lapotron_crystal_I = new ICBatteryCharge("compressed_charging_lapotron_crystal", "charging_lapotron_crystal");
			
			// IC Condensator
			public final BaseItemSub C_lzh_condensator_I = new ICCondensator("compressed_lzh_condensator", "ic2:lzh_condensator");
			public final BaseItemSub C_rsh_condensator_I = new ICCondensator("compressed_rsh_condensator", "ic2:rsh_condensator");
			
			// IC Reflector
			public final BaseItemSub C_neutron_reflector_I = new ICReflector("compressed_neutron_reflector", "ic2:neutron_reflector");		
			public final BaseItemSub C_thick_neutron_reflector_I = new ICReflector("compressed_thick_neutron_reflector", "ic2:thick_neutron_reflector");		
			public final BaseItemSub C_iridium_reflector_I = new ICReflector("compressed_iridium_reflector", "ic2:iridium_reflector");		
			
			// IC Exchanger
			public final BaseItemSub C_heat_exchanger_I = new ICHeatExchanger("compressed_heat_exchanger", "ic2:heat_exchanger");		
			public final BaseItemSub C_reactor_heat_exchanger_I = new ICHeatExchanger("compressed_reactor_heat_exchanger", "ic2:reactor_heat_exchanger");		
			public final BaseItemSub C_component_heat_exchanger_I = new ICHeatExchanger("compressed_component_heat_exchanger", "ic2:component_heat_exchanger");		
			public final BaseItemSub C_advanced_heat_exchanger_I = new ICHeatExchanger("compressed_advanced_heat_exchanger", "ic2:advanced_heat_exchanger");		
			
			// IC Vent
			public final BaseItemSub C_heat_vent_I = new ICHeatVent("compressed_heat_vent", "ic2:heat_vent");
			public final BaseItemSub C_reactor_heat_vent_I = new ICHeatVent("compressed_reactor_heat_vent", "ic2:reactor_heat_vent");		
			public final BaseItemSub C_overclocked_heat_vent_I = new ICHeatVent("compressed_overclocked_heat_vent", "ic2:overclocked_heat_vent");		
			public final BaseItemSub C_component_heat_vent_I = new ICHeatVent("compressed_component_heat_vent", "ic2:component_heat_vent");		
			public final BaseItemSub C_advanced_heat_vent_I = new ICHeatVent("compressed_advanced_heat_ventr", "ic2:advanced_heat_vent");		
			
			// IC Heat Storage
			public final BaseItemSub C_heat_storage_I = new ICHeatStorage("compressed_heat_storage", "ic2:heat_storage");
			public final BaseItemSub C_tri_heat_storage_I = new ICHeatStorage("compressed_tri_heat_storage", "ic2:tri_heat_storage");
			public final BaseItemSub C_hex_heat_storage_I = new ICHeatStorage("compressed_hex_heat_storage", "ic2:hex_heat_storage");
			
		}
		private BaseItemSub register(String nameIn, String unCompressedItem) {
			return register(nameIn, unCompressedItem, 0);
		}
		private BaseItemSub register(String nameIn, String unCompressedItem, boolean hasRadiation) {
			return register(nameIn, unCompressedItem, 0, hasRadiation);
		}
		private BaseItemSub register(String nameIn, String unCompressedItem, int meta) {
			return register(nameIn, unCompressedItem, meta, false);
		}
		private BaseItemSub register(String nameIn, String unCompressedItem, int meta, boolean hasRadiation) {
			if(hasRadiation) {
				return IC2RadiationItem.register(nameIn, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, meta), "ic2");
			}else {
				return BaseItemSub.register(nameIn, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, meta), "ic2");
			}
		}
	}

	public static class MCSItem {
		public final BaseItemNormal CAT_HAIR = new BaseItemNormal("cat_hair", CreativeTabs.MISC);
		public final BaseItemNormal CAT_INGOT = new BaseItemNormal("cat_ingot", CreativeTabs.MISC);
		public final BaseItemNormal CAT_HAMMER = new ItemCatHammer("cat_hammer", CreativeTabs.TOOLS);
		public final BaseItemNormal DESTROYER = new ItemDestroyer("destroyer", CreativeTabs.TOOLS);
		public final BaseItem.Normal debug = new ItemDeBug();
	}
}
