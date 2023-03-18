package cat.jiu.mcs.util.init;

import java.util.List;

import com.brandon3055.draconicevolution.DEFeatures;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItemDefinition;

import cofh.thermalfoundation.init.TFItems;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.items.*;
import cat.jiu.mcs.items.compressed.*;
import cat.jiu.mcs.items.compressed.ae2.*;
import cat.jiu.mcs.items.compressed.ic.*;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseItem;
import cat.jiu.mcs.util.type.CustomStuffType;
import cat.jiu.mcs.util.base.*;
import cat.jiu.mcs.util.base.sub.*;
import cat.jiu.mcs.util.base.sub.tool.*;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

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
	public static AppliedEnergistics2 ae = null;

	public MCSItems() {
		if(Configs.Custom.Enable_Mod_Stuff) {
			thermal_foundation = Configs.Custom.Mod_Stuff.ThermalFoundation ? new ThermalFoundationItem() : null;
			draconic_evolution = Configs.Custom.Mod_Stuff.DraconicEvolution ? new DraconicEvolutionItems() : null;
			avaritia = Configs.Custom.Mod_Stuff.Avaritia ? new AvaritiaItems() : null;
			ic2 = Configs.Custom.Mod_Stuff.IndustrialCraft ? new IndustrialCraft() : null;
			ae = Configs.Custom.Mod_Stuff.AppliedEnergistics2 ? new AppliedEnergistics2() : null;

		}
	}

	public static final void registerOreDict() {
		for(Item ii : MCSResources.ITEMS) {
			if(ii instanceof BaseCompressedItem) {
				BaseCompressedItem item = (BaseCompressedItem) ii;
				if(item.createOreDictionary()) {
					if(!item.getOtherOreDictionary().isEmpty()) {
						List<String> ores = item.getOtherOreDictionary();
						for(int i = 0; i < 16; i++) {
							String ore = ores.get(i);
							OreDictionary.registerOre((i + 1) + "x" + ore, new ItemStack(item, 1, i));
						}
					}
					JiuUtils.item.registerCompressedOre(item.getUnCompressedName(), item, false);
				}
			}else if(ii instanceof BaseCompressedFood) {
				BaseCompressedFood food = (BaseCompressedFood) ii;
				if(food.createOreDictionary()) {
					if(!food.getOtherOreDictionary().isEmpty()) {
						List<String> ores = food.getOtherOreDictionary();
						for(int i = 0; i < 16; i++) {
							String ore = ores.get(i);
							OreDictionary.registerOre((i + 1) + "x" + ore, new ItemStack(food, 1, i));
						}
					}
					JiuUtils.item.registerCompressedOre(food.getUnCompressedName(), food, false);
				}
			}
		}
	}

	public static class MinecraftItem {
		public final Normal normal = new Normal();
		public final Food food = new Food();
		public final Tool tool = new Tool();

		public class Normal {
			public final BaseCompressedItem C_STICK_I = new BaseCompressedItem("compressed_stick", new ItemStack(Items.STICK));
			public final BaseCompressedItem C_REEDS_I = new BaseCompressedItem("compressed_reeds", new ItemStack(Items.REEDS));
			public final BaseCompressedItem C_BLAZE_POWDER_I = new BaseCompressedItem("compressed_blaze_powder", new ItemStack(Items.BLAZE_POWDER));
			public final BaseCompressedItem C_GUNPOWDER_I = new BaseCompressedItem("compressed_gunpowder", new ItemStack(Items.GUNPOWDER));
			public final BaseCompressedItem C_DRAGON_BREATH_I = new BaseCompressedItem("compressed_dragon_breath", new ItemStack(Items.DRAGON_BREATH));
			public final BaseCompressedItem C_FEATHER_I = new BaseCompressedItem("compressed_feather", new ItemStack(Items.FEATHER));
			public final BaseCompressedItem C_STRING_I = new BaseCompressedItem("compressed_string", new ItemStack(Items.STRING));
			public final BaseCompressedItem C_MAGMA_CREAM_I = new BaseCompressedItem("compressed_magma_cream", new ItemStack(Items.MAGMA_CREAM));
			public final BaseCompressedItem C_GLASS_BOTTLE_I = new BaseCompressedItem("compressed_glass_bottle", new ItemStack(Items.GLASS_BOTTLE));
			public final BaseCompressedItem C_BOWL_I = new BaseCompressedItem("compressed_bowl", new ItemStack(Items.BOWL));
			public final BaseCompressedItem C_EGG_I = new CompressedEgg("compressed_egg", new ItemStack(Items.EGG));

			public final BaseCompressedItem C_cocoa_beans_I = new BaseCompressedItem("compressed_cocoa_beans", new ItemStack(Items.DYE, 1, 3));
			public final BaseCompressedItem C_cactus_green_I = new BaseCompressedItem("compressed_cactus_green", new ItemStack(Items.DYE, 1, 2));
			public final BaseCompressedItem C_ink_sac_I = new BaseCompressedItem("compressed_ink_sac", new ItemStack(Items.DYE, 1, 0));
			public final BaseCompressedItem C_seed_melon_I = new BaseCompressedItem("compressed_seed_melon", new ItemStack(Items.MELON_SEEDS));
			public final BaseCompressedItem C_seed_pumpkin_I = new BaseCompressedItem("compressed_seed_pumpkin", new ItemStack(Items.PUMPKIN_SEEDS));
			public final BaseCompressedItem C_seed_wheat_I = new BaseCompressedItem("compressed_seed_wheat", new ItemStack(Items.WHEAT_SEEDS));
			public final BaseCompressedItem C_seed_beetroot_I = new BaseCompressedItem("compressed_beetroot_wheat", new ItemStack(Items.BEETROOT_SEEDS));
			public final BaseCompressedItem C_sugar_I = new BaseCompressedItem("compressed_sugar", new ItemStack(Items.SUGAR));
			public final BaseCompressedItem C_ender_eye_I = new BaseCompressedItem("compressed_ender_eye", new ItemStack(Items.ENDER_EYE));
			public final BaseCompressedItem C_brick_I = new BaseCompressedItem("compressed_brick", new ItemStack(Items.BRICK));
			public final BaseCompressedItem C_dust_glowstone_I = new BaseCompressedItem("compressed_dust_glowstone", new ItemStack(Items.GLOWSTONE_DUST));
			public final BaseCompressedItem C_spider_eye_fermented_I = new BaseCompressedItem("compressed_spider_eye_fermented", new ItemStack(Items.FERMENTED_SPIDER_EYE));
			public final BaseCompressedItem C_rabbit_foot_I = new BaseCompressedItem("compressed_rabbit_foot", new ItemStack(Items.RABBIT_FOOT));
			public final BaseCompressedItem C_rabbit_hide_I = new BaseCompressedItem("compressed_rabbit_hide", new ItemStack(Items.RABBIT_HIDE));

		}

		public class Food {
			public final BaseCompressedFood C_COOKED_BEEF_F = new BaseCompressedFood("compressed_cooked_beef", Items.COOKED_BEEF);
			public final BaseCompressedFood C_BEEF_F = new BaseCompressedFood("compressed_beef", Items.BEEF).setSmeltingOutput(C_COOKED_BEEF_F);
			public final BaseCompressedFood C_COOKED_CHICKEN_F = new BaseCompressedFood("compressed_cooked_chicken", Items.COOKED_CHICKEN);
			public final BaseCompressedFood C_CHICKEN_F = new BaseCompressedFood("compressed_chicken", Items.CHICKEN).setSmeltingOutput(C_COOKED_CHICKEN_F);
			public final BaseCompressedFood C_BAKED_POTATO_F = new BaseCompressedFood("compressed_baked_potato", Items.BAKED_POTATO);
			public final BaseCompressedFood C_POTATO_F = new BaseCompressedFood("compressed_potato", Items.POTATO).setSmeltingOutput(C_BAKED_POTATO_F);
			public final BaseCompressedFood C_COOKED_RABBIT_F = new BaseCompressedFood("compressed_cooked_rabbit", Items.COOKED_RABBIT);
			public final BaseCompressedFood C_RABBIT_F = new BaseCompressedFood("compressed_rabbit", Items.RABBIT).setSmeltingOutput(C_COOKED_RABBIT_F);
			public final BaseCompressedFood C_COOKED_PORKCHOP_F = new BaseCompressedFood("compressed_cooked_prokchop", Items.COOKED_PORKCHOP);
			public final BaseCompressedFood C_PORKCHOP_F = new BaseCompressedFood("compressed_porkchop", Items.PORKCHOP).setSmeltingOutput(C_COOKED_PORKCHOP_F);
			public final BaseCompressedFood C_COOKED_MUTTON_F = new BaseCompressedFood("compressed_cooked_mutton", Items.COOKED_MUTTON);
			public final BaseCompressedFood C_MUTTON_F = new BaseCompressedFood("compressed_mutton", Items.MUTTON).setSmeltingOutput(C_COOKED_MUTTON_F);
			public final BaseCompressedFood C_COOKED_FISH_F = new BaseCompressedFood("compressed_cooked_fish", Items.COOKED_FISH);
			public final BaseCompressedFood C_FISH_F = new BaseCompressedFood("compressed_fish", Items.FISH).setSmeltingOutput(C_COOKED_FISH_F);
			public final BaseCompressedFood C_COOKED_SALMON_FISH_F = new BaseCompressedFood("compressed_cooked_salmon_fish", Items.COOKED_FISH, 1);
			public final BaseCompressedFood C_SALMON_FISH_F = new BaseCompressedFood("compressed_salmon_fish", Items.FISH, 1).setSmeltingOutput(C_COOKED_SALMON_FISH_F);
			public final BaseCompressedFood C_APPLE_F = new BaseCompressedFood("compressed_apple", Items.APPLE);
			public final BaseCompressedFood C_BREAD_F = new BaseCompressedFood("compressed_bread", Items.BREAD);
			public final BaseCompressedFood C_CLOWN_FISH_F = new BaseCompressedFood("compressed_clown_fish", Items.FISH, 2);
			public final BaseCompressedFood C_COOKIE_F = new BaseCompressedFood("compressed_cookie", Items.COOKIE);
			public final BaseCompressedFood C_ROTTEN_FLESH_F = new BaseCompressedFood("compressed_rotten_flesh", Items.ROTTEN_FLESH);
			public final BaseCompressedFood C_CARROT_F = new BaseCompressedFood("compressed_carrot", Items.CARROT);
			public final BaseCompressedFood C_goldEN_CARROT_F = new BaseCompressedFood("compressed_golden_carrot", Items.GOLDEN_CARROT);
			public final BaseCompressedFood C_PUMPKIN_PIE_F = new BaseCompressedFood("compressed_pumpkin_pie", Items.PUMPKIN_PIE);
			public final BaseCompressedFood C_BEETROOT_F = new BaseCompressedFood("compressed_beetroot", Items.BEETROOT);
			public final BaseCompressedFood C_SPECKLED_MELON_F = new BaseCompressedFood("compressed_speckled_melon", Items.SPECKLED_MELON).setFoodEntry(3, 0.9F, false);
			public final BaseCompressedFood C_MUSHROOM_STEW_F = new BaseCompressedFood("compressed_mushron_stew", Items.MUSHROOM_STEW).setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseCompressedFood C_RABBIT_STEW_F = new BaseCompressedFood("compressed_rabbit_stew", Items.RABBIT_STEW).setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseCompressedFood C_BEETROOT_SOUP_F = new BaseCompressedFood("compressed_beetroot_soup", Items.BEETROOT_SOUP).setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseCompressedFood C_PUFFER_FISH_F = new BaseCompressedFood("compressed_puffer_fish", Items.FISH, 3).addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.POISON, 2280, 3), new CustomStuffType.PotionEffectType(MobEffects.HUNGER, 570, 2), new CustomStuffType.PotionEffectType(MobEffects.NAUSEA, 570, 1)});
			public final BaseCompressedFood C_GOLD_APPLE_F = new BaseCompressedFood("compressed_gold_apple", Items.GOLDEN_APPLE, 0).addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.REGENERATION, 220, 1), new CustomStuffType.PotionEffectType(MobEffects.ABSORPTION, 4560, 0)});
			public final BaseCompressedFood C_ENCHANTED_GOLD_APPLE_F = new BaseCompressedFood("compressed_enchanted_gold_apple", Items.GOLDEN_APPLE, 1).addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.REGENERATION, 760, 1), new CustomStuffType.PotionEffectType(MobEffects.RESISTANCE, 11400, 0), new CustomStuffType.PotionEffectType(MobEffects.FIRE_RESISTANCE, 11400, 0), new CustomStuffType.PotionEffectType(MobEffects.ABSORPTION, 4560, 3)});
			public final BaseCompressedFood C_SPIDER_EYE_F = new BaseCompressedFood("compressed_spider_eye", Items.SPIDER_EYE).addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.POISON, 180, 1)}).setContainer(new ItemStack(C_ENCHANTED_GOLD_APPLE_F));
		}

		public class Tool {
			private final MCSBlocks.MinecraftBlock mc = MCSBlocks.minecraft;
			public final Sword sword = new Sword();
			public final Pickaxe pickaxe = new Pickaxe();
			public final Shovel shovel = new Shovel();
			public final Axe axe = new Axe();
			public final Hoe hoe = new Hoe();

			public class Sword {
				public final BaseCompressedSword C_DIAMOND_SWORD_I = new BaseCompressedSword("compressed_diamond_sword", new ItemStack(Items.DIAMOND_SWORD), mc.normal.C_DIAMOND_B, normal.C_STICK_I);
				public final BaseCompressedSword C_IRON_SWORD_I = new BaseCompressedSword("compressed_iron_sword", new ItemStack(Items.IRON_SWORD), mc.normal.C_IRON_B, normal.C_STICK_I);
				public final BaseCompressedSword C_STONE_SWORD_I = new BaseCompressedSword("compressed_stone_sword", new ItemStack(Items.STONE_SWORD), mc.normal.C_COBBLE_STONE_B, normal.C_STICK_I);
				public final BaseCompressedSword C_WOODEN_SWORD_I = new BaseCompressedSword("compressed_wooden_sword", new ItemStack(Items.WOODEN_SWORD), mc.normal.C_PLANKS_B, normal.C_STICK_I);
				public final BaseCompressedSword C_goldEN_SWORD_I = new BaseCompressedSword("compressed_golden_sword", new ItemStack(Items.GOLDEN_SWORD), mc.normal.C_GOLD_B, normal.C_STICK_I);

			}
			public class Pickaxe {
				public final BaseCompressedPickaxe C_DIAMOND_PICKAXE_I = new BaseCompressedPickaxe("compressed_diamond_pickaxe", new ItemStack(Items.DIAMOND_PICKAXE), mc.normal.C_DIAMOND_B, normal.C_STICK_I);
				public final BaseCompressedPickaxe C_IRON_PICKAXE_I = new BaseCompressedPickaxe("compressed_iron_pickaxe", new ItemStack(Items.IRON_PICKAXE), mc.normal.C_IRON_B, normal.C_STICK_I);
				public final BaseCompressedPickaxe C_STONE_PICKAXE_I = new BaseCompressedPickaxe("compressed_stone_pickaxe", new ItemStack(Items.STONE_PICKAXE), mc.normal.C_COBBLE_STONE_B, normal.C_STICK_I);
				public final BaseCompressedPickaxe C_WOODEN_PICKAXE_I = new BaseCompressedPickaxe("compressed_wooden_pickaxe", new ItemStack(Items.WOODEN_PICKAXE), mc.normal.C_PLANKS_B, normal.C_STICK_I);
				public final BaseCompressedPickaxe C_goldEN_PICKAXE_I = new BaseCompressedPickaxe("compressed_golden_pickaxe", new ItemStack(Items.GOLDEN_PICKAXE), mc.normal.C_GOLD_B, normal.C_STICK_I);
			}
			public class Shovel {
				public final BaseCompressedShovel C_DIAMOND_SHOVEL_I = new BaseCompressedShovel("compressed_diamond_shovel", new ItemStack(Items.DIAMOND_SHOVEL), mc.normal.C_DIAMOND_B, normal.C_STICK_I);
				public final BaseCompressedShovel C_IRON_SHOVEL_I = new BaseCompressedShovel("compressed_iron_shovel", new ItemStack(Items.IRON_SHOVEL), mc.normal.C_IRON_B, normal.C_STICK_I);
				public final BaseCompressedShovel C_STONE_SHOVEL_I = new BaseCompressedShovel("compressed_stone_shovel", new ItemStack(Items.STONE_SHOVEL), mc.normal.C_COBBLE_STONE_B, normal.C_STICK_I);
				public final BaseCompressedShovel C_WOODEN_SHOVEL_I = new BaseCompressedShovel("compressed_wooden_shovel", new ItemStack(Items.WOODEN_SHOVEL), mc.normal.C_PLANKS_B, normal.C_STICK_I);
				public final BaseCompressedShovel C_goldEN_SHOVEL_I = new BaseCompressedShovel("compressed_golden_shovel", new ItemStack(Items.GOLDEN_SHOVEL), mc.normal.C_GOLD_B, normal.C_STICK_I);

			}
			public class Axe {
				public final BaseCompressedAxe C_DIAMOND_AXE_I = new BaseCompressedAxe("compressed_diamond_axe", new ItemStack(Items.DIAMOND_AXE), mc.normal.C_DIAMOND_B, normal.C_STICK_I);
				public final BaseCompressedAxe C_IRON_AXE_I = new BaseCompressedAxe("compressed_iron_axe", new ItemStack(Items.IRON_AXE), mc.normal.C_IRON_B, normal.C_STICK_I);
				public final BaseCompressedAxe C_STONE_AXE_I = new BaseCompressedAxe("compressed_stone_axe", new ItemStack(Items.STONE_AXE), mc.normal.C_COBBLE_STONE_B, normal.C_STICK_I);
				public final BaseCompressedAxe C_WOODEN_AXE_I = new BaseCompressedAxe("compressed_wooden_axe", new ItemStack(Items.WOODEN_AXE), mc.normal.C_PLANKS_B, normal.C_STICK_I);
				public final BaseCompressedAxe C_goldEN_AXE_I = new BaseCompressedAxe("compressed_golden_axe", new ItemStack(Items.GOLDEN_AXE), mc.normal.C_GOLD_B, normal.C_STICK_I);

			}
			public class Hoe {
				public final BaseCompressedHoe C_DIAMOND_HOE_I = new BaseCompressedHoe("compressed_diamond_hoe", new ItemStack(Items.DIAMOND_HOE), mc.normal.C_DIAMOND_B, normal.C_STICK_I);
				public final BaseCompressedHoe C_IRON_HOE_I = new BaseCompressedHoe("compressed_iron_hoe", new ItemStack(Items.IRON_HOE), mc.normal.C_IRON_B, normal.C_STICK_I);
				public final BaseCompressedHoe C_STONE_HOE_I = new BaseCompressedHoe("compressed_stone_hoe", new ItemStack(Items.STONE_HOE), mc.normal.C_COBBLE_STONE_B, normal.C_STICK_I);
				public final BaseCompressedHoe C_WOODEN_HOE_I = new BaseCompressedHoe("compressed_wooden_hoe", new ItemStack(Items.WOODEN_HOE), mc.normal.C_PLANKS_B, normal.C_STICK_I);
				public final BaseCompressedHoe C_goldEN_HOE_I = new BaseCompressedHoe("compressed_golden_hoe", new ItemStack(Items.GOLDEN_HOE), mc.normal.C_GOLD_B, normal.C_STICK_I);

			}
		}
	}

	public static class OreStuff {
		private final boolean TF = Configs.Custom.Mod_Stuff.ThermalFoundation;
		private final boolean IC = Configs.Custom.Mod_Stuff.IndustrialCraft;
		private final boolean AE = Configs.Custom.Mod_Stuff.AppliedEnergistics2;

		public final Plate plate = new Plate();
		public final Dust dust = new Dust();
		public final Gear gear = new Gear();

		public boolean isEnable() {
			return TF || IC || AE;
		}

		public class Plate {
			public final BaseCompressedItem C_plate_aluminum_I = TF ? register("aluminum", TFItems.itemMaterial.plateAluminum, "thermalfoundation") : null;
			public final BaseCompressedItem C_plate_bronze_I = TF ? register("bronze", TFItems.itemMaterial.plateBronze, "thermalfoundation") : IC ? register("bronze", "plate", "ic2") : null;
			public final BaseCompressedItem C_plate_constantan_I = TF ? register("constantan", TFItems.itemMaterial.plateConstantan, "thermalfoundation") : null;
			public final BaseCompressedItem C_plate_copper_I = TF ? register("copper", TFItems.itemMaterial.plateCopper, "thermalfoundation") : IC ? register("copper", "plate", 1, "ic2") : null;
			public final BaseCompressedItem C_plate_electrum_I = TF ? register("electrum", TFItems.itemMaterial.plateElectrum, "thermalfoundation") : null;
			public final BaseCompressedItem C_plate_gold_I = TF ? register("gold", TFItems.itemMaterial.plateGold, "thermalfoundation") : IC ? register("gold", "plate", 2, "ic2") : null;
			public final BaseCompressedItem C_plate_invar_I = TF ? register("invar", TFItems.itemMaterial.plateInvar, "thermalfoundation") : null;
			public final BaseCompressedItem C_plate_iridium_I = TF ? register("iridium", TFItems.itemMaterial.plateIridium, "thermalfoundation") : null;
			public final BaseCompressedItem C_plate_iron_I = TF ? register("iron", TFItems.itemMaterial.plateIron, "thermalfoundation") : IC ? register("iron", "plate", 3, "ic2") : null;
			public final BaseCompressedItem C_plate_lead_I = TF ? register("lead", TFItems.itemMaterial.plateLead, "thermalfoundation") : IC ? register("lead", "plate", 5, "ic2") : null;
			public final BaseCompressedItem C_plate_nickel_I = TF ? register("nickel", TFItems.itemMaterial.plateNickel, "thermalfoundation") : null;
			public final BaseCompressedItem C_plate_platinum_I = TF ? register("platinum", TFItems.itemMaterial.platePlatinum, "thermalfoundation") : null;
			public final BaseCompressedItem C_plate_silver_I = TF ? register("silver", TFItems.itemMaterial.plateSilver, "thermalfoundation") : null;
			public final BaseCompressedItem C_plate_steel_I = TF ? register("steel", TFItems.itemMaterial.plateSteel, "thermalfoundation") : IC ? register("steel", "plate", 7, "ic2") : null;
			public final BaseCompressedItem C_plate_tin_I = TF ? register("tin", TFItems.itemMaterial.plateTin, "thermalfoundation") : IC ? register("tin", "plate", 8, "ic2") : null;

			private BaseCompressedItem register(String ore, String unCompressed, String ownerMod) {
				return this.register(ore, unCompressed, 0, ownerMod);
			}

			private BaseCompressedItem register(String ore, String unCompressed, int meta, String ownerMod) {
				return this.register(ore, new ItemStack(Item.getByNameOrId(ownerMod + ":" + unCompressed), 1, meta), ownerMod);
			}

			private BaseCompressedItem register(String ore, ItemStack unCompressed, String ownerMod) {
				BaseCompressedItem item = BaseCompressedItem.register("compressed_plate_" + ore, unCompressed, ownerMod);
				if(item == null) {
					return null;
				}
				return item.setModelMaterial("plate");
			}
		}
		public class Gear {
			public final BaseCompressedItem C_gear_wood_I = TF ? register("wood", TFItems.itemMaterial.gearWood, "thermalfoundation") : AE ? register("wood", AEApi.instance().definitions().materials().woodenGear().maybeStack(1).get(), "appliedenergistics2") : null;
			public final BaseCompressedItem C_gear_stone_I = TF ? register("stone", TFItems.itemMaterial.gearStone, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_iron_I = TF ? register("iron", TFItems.itemMaterial.gearIron, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_gold_I = TF ? register("gold", TFItems.itemMaterial.gearGold, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_diamond_I = TF ? register("diamond", TFItems.itemMaterial.gearDiamond, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_emerald_I = TF ? register("emerald", TFItems.itemMaterial.gearEmerald, "thermalfoundation") : null;

			public final BaseCompressedItem C_gear_aluminum_I = TF ? register("aluminum", TFItems.itemMaterial.gearAluminum, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_bronze_I = TF ? register("bronze", TFItems.itemMaterial.gearBronze, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_constantan_I = TF ? register("constantan", TFItems.itemMaterial.gearConstantan, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_copper_I = TF ? register("copper", TFItems.itemMaterial.gearCopper, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_electrum_I = TF ? register("electrum", TFItems.itemMaterial.gearElectrum, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_invar_I = TF ? register("invar", TFItems.itemMaterial.gearInvar, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_iridium_I = TF ? register("iridium", TFItems.itemMaterial.gearIridium, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_lead_I = TF ? register("lead", TFItems.itemMaterial.gearLead, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_nickel_I = TF ? register("nickel", TFItems.itemMaterial.gearNickel, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_platinum_I = TF ? register("platinum", TFItems.itemMaterial.gearPlatinum, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_silver_I = TF ? register("silver", TFItems.itemMaterial.gearSilver, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_steel_I = TF ? register("steel", TFItems.itemMaterial.gearSteel, "thermalfoundation") : null;
			public final BaseCompressedItem C_gear_tin_I = TF ? register("tin", TFItems.itemMaterial.gearTin, "thermalfoundation") : null;

			@SuppressWarnings("unused")
			private BaseCompressedItem register(String ore, String unCompressed, String ownerMod) {
				return this.register(ore, unCompressed, 0, ownerMod);
			}

			private BaseCompressedItem register(String ore, String unCompressed, int meta, String ownerMod) {
				return this.register(ore, new ItemStack(Item.getByNameOrId(ownerMod + ":" + unCompressed), 1, meta), ownerMod);
			}

			private BaseCompressedItem register(String ore, ItemStack unCompressed, String ownerMod) {
				BaseCompressedItem item = BaseCompressedItem.register("compressed_gear_" + ore, unCompressed, ownerMod);
				if(item == null) {
					return null;
				}
				return item.setModelMaterial("gear");
			}
		}
		public class Dust {
			private final MCSBlocks.OreStuff.OreBlock block = MCSBlocks.ore_stuff.block;
			public final BaseCompressedItem C_dust_aluminum_I = TF ? register("aluminum", TFItems.itemMaterial.dustAluminum, "thermalfoundation", block.C_aluminum_B) : null;
			public final BaseCompressedItem C_dust_bronze_I = TF ? register("bronze", TFItems.itemMaterial.dustBronze, "thermalfoundation", block.C_bronze_B) : IC ? register("bronze", "dust", "ic2", block.C_bronze_B) : null;
			public final BaseCompressedItem C_dust_constantan_I = TF ? register("constantan", TFItems.itemMaterial.dustConstantan, "thermalfoundation", block.C_constantan_B) : null;
			public final BaseCompressedItem C_dust_copper_I = TF ? register("copper", TFItems.itemMaterial.dustCopper, "thermalfoundation", block.C_copper_B) : IC ? register("copper", "dust", 4, "ic2", block.C_copper_B) : null;
			public final BaseCompressedItem C_dust_electrum_I = TF ? register("electrum", TFItems.itemMaterial.dustElectrum, "thermalfoundation", block.C_electrum_B) : null;
			public final BaseCompressedItem C_dust_gold_I = TF ? register("gold", TFItems.itemMaterial.dustGold, "thermalfoundation", MCSBlocks.minecraft.normal.C_GOLD_B) : IC ? register("gold", "dust", 7, "ic2", MCSBlocks.minecraft.normal.C_GOLD_B) : AE ? register("gold", AEApi.instance().definitions().materials().goldDust().maybeStack(1).get(), "appliedenergistics2", MCSBlocks.minecraft.normal.C_GOLD_B) : null;
			public final BaseCompressedItem C_dust_invar_I = TF ? register("invar", TFItems.itemMaterial.dustInvar, "thermalfoundation", block.C_invar_B) : null;
			public final BaseCompressedItem C_dust_iridium_I = TF ? register("iridium", TFItems.itemMaterial.dustIridium, "thermalfoundation", block.C_iridium_B) : null;
			public final BaseCompressedItem C_dust_iron_I = TF ? register("iron", TFItems.itemMaterial.dustIron, "thermalfoundation", MCSBlocks.minecraft.normal.C_IRON_B) : IC ? register("iron", "dust", 8, "ic2", MCSBlocks.minecraft.normal.C_IRON_B) : AE ? register("iron", AEApi.instance().definitions().materials().ironDust().maybeStack(1).get(), "appliedenergistics2", MCSBlocks.minecraft.normal.C_IRON_B) : null;
			public final BaseCompressedItem C_dust_lead_I = TF ? register("lead", TFItems.itemMaterial.dustLead, "thermalfoundation", block.C_lead_B) : IC ? register("lead", "dust", 10, "ic2", block.C_lead_B) : null;
			public final BaseCompressedItem C_dust_nickel_I = TF ? register("nickel", TFItems.itemMaterial.dustNickel, "thermalfoundation", block.C_nickel_B) : null;
			public final BaseCompressedItem C_dust_platinum_I = TF ? register("platinum", TFItems.itemMaterial.dustPlatinum, "thermalfoundation", block.C_platinum_B) : null;
			public final BaseCompressedItem C_dust_silver_I = TF ? register("silver", TFItems.itemMaterial.dustSilver, "thermalfoundation", block.C_silver_B) : IC ? register("silver", "dust", 14, "ic2", block.C_silver_B) : null;
			public final BaseCompressedItem C_dust_steel_I = TF ? register("steel", TFItems.itemMaterial.dustSteel, "thermalfoundation", block.C_steel_B) : null;
			public final BaseCompressedItem C_dust_tin_I = TF ? register("tin", TFItems.itemMaterial.dustTin, "thermalfoundation", block.C_tin_B) : IC ? register("tin", "dust", 17, "ic2", block.C_tin_B) : null;
			public final BaseCompressedItem C_dust_coal_I = TF ? register("coal", TFItems.itemMaterial.dustCoal, "thermalfoundation", null) : IC ? register("coal", "dust", 2, "ic2", null) : null;
			public final BaseCompressedItem C_dust_obsidian_I = TF ? register("obsidian", TFItems.itemMaterial.dustObsidian, "thermalfoundation", null) : IC ? register("obsidian", "dust", 12, "ic2", null) : null;
			public final BaseCompressedItem C_dust_sulfur_I = TF ? register("sulfur", TFItems.itemMaterial.dustSulfur, "thermalfoundation", null) : IC ? register("sulfur", "dust", 16, "ic2", null) : null;

			private BaseCompressedItem register(String ore, String unCompressed, String ownerMod, ICompressedStuff stuff) {
				return this.register(ore, unCompressed, 0, ownerMod, stuff);
			}

			private BaseCompressedItem register(String ore, String unCompressed, int meta, String ownerMod, ICompressedStuff stuff) {
				return this.register(ore, new ItemStack(Item.getByNameOrId(ownerMod + ":" + unCompressed), 1, meta), ownerMod, stuff);
			}

			private BaseCompressedItem register(String ore, ItemStack unCompressed, String ownerMod, ICompressedStuff stuff) {
				BaseCompressedItem item = BaseCompressedItem.register("compressed_dust_" + ore, unCompressed, ownerMod);
				if(item == null) {
					return null;
				}
				return item.setModelMaterial("dust").setSmeltingOutput(stuff);
			}
		}
	}

	public static class ThermalFoundationItem {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseCompressedItem C_DYE_I = register("compressed_tf_dye", TFItems.itemDye.dyeWhite);
			public final BaseCompressedItem C_SAWDUST_I = register("compressed_wood_dust", TFItems.itemMaterial.dustWoodCompressed);
			// Crystal 络合物
			public final BaseCompressedItem C_CRYSTAL_ENDER_I = register("compressed_crystal_ender", TFItems.itemMaterial.crystalEnder);
			public final BaseCompressedItem C_CRYSTAL_GLOWSTONE_I = register("compressed_crystal_glowstone", TFItems.itemMaterial.crystalGlowstone);
			public final BaseCompressedItem C_CRYSTAL_REDSTONE_I = register("compressed_crystal_redstone", TFItems.itemMaterial.crystalRedstone);

			// Slag 炉渣
			public final BaseCompressedItem C_CRYSTAL_SLAG_I = register("compressed_crystal_slag", TFItems.itemMaterial.crystalSlag);
			public final BaseCompressedItem C_CRYSTAL_SLAG_RICH_I = register("compressed_crystal_slag_rich", TFItems.itemMaterial.crystalSlagRich);
			public final BaseCompressedItem C_CRYSTAL_CINNABAR_I = register("compressed_crystal_cinnabar", TFItems.itemMaterial.crystalCinnabar);

			// Rod 棒子
			public final BaseCompressedItem C_ROD_BASALZ_I = register("compressed_rod_basalz", TFItems.itemMaterial.rodBasalz);
			public final BaseCompressedItem C_ROD_BLITZ_I = register("compressed_rod_blitz", TFItems.itemMaterial.rodBlitz);
			public final BaseCompressedItem C_ROD_BLIZZ_I = register("compressed_rod_blizz", TFItems.itemMaterial.rodBlizz);

			// Plate 板子
			public final BaseCompressedItem C_PLATE_LUMIUM_I = register("compressed_plate_lumium", TFItems.itemMaterial.plateLumium).setModelMaterial("plate");
			public final BaseCompressedItem C_PLATE_ENDERIUM_I = register("compressed_plate_enderium", TFItems.itemMaterial.plateEnderium).setModelMaterial("plate");
			public final BaseCompressedItem C_PLATE_SIGNALUM_I = register("compressed_plate_signalum", TFItems.itemMaterial.plateSignalum).setModelMaterial("plate");
			public final BaseCompressedItem C_PLATE_MITHRIL_I = register("compressed_plate_mithri", TFItems.itemMaterial.plateMithril).setModelMaterial("plate");

			// Gear 齿轮
			public final BaseCompressedItem C_GEAR_LUMIUM_I = register("compressed_gear_lumium", TFItems.itemMaterial.gearLumium).setModelMaterial("gear");
			public final BaseCompressedItem C_GEAR_ENDERIUM_I = register("compressed_gear_enderium", TFItems.itemMaterial.gearEnderium).setModelMaterial("gear");
			public final BaseCompressedItem C_GEAR_MITHRIL_I = register("compressed_gear_mithril", TFItems.itemMaterial.gearMithril).setModelMaterial("gear");
			public final BaseCompressedItem C_GEAR_SIGNALUM_I = register("compressed_gear_signalum", TFItems.itemMaterial.gearSignalum).setModelMaterial("gear");

			// Dust 金属粉
			public final BaseCompressedItem C_DUST_LUMIUM_I = register("compressed_dust_lumium", TFItems.itemMaterial.dustLumium).setModelMaterial("dust").setSmeltingOutput(MCSBlocks.thermal_foundation.normal.C_LUMIUM_B);
			public final BaseCompressedItem C_DUST_ENDERIUM_I = register("compressed_dust_enderium", TFItems.itemMaterial.dustEnderium).setModelMaterial("dust").setSmeltingOutput(MCSBlocks.thermal_foundation.normal.C_ENDERIUM_B);
			public final BaseCompressedItem C_DUST_MITHRIL_I = register("compressed_dust_mithril", TFItems.itemMaterial.dustMithril).setModelMaterial("dust").setSmeltingOutput(MCSBlocks.thermal_foundation.normal.C_MITHRIL_B);
			public final BaseCompressedItem C_DUST_SIGNALUM_I = register("compressed_dust_signalum", TFItems.itemMaterial.dustSignalum).setModelMaterial("dust").setSmeltingOutput(MCSBlocks.thermal_foundation.normal.C_SIGNALUM_B);

		}

		private BaseCompressedItem register(String name, ItemStack baseItem) {
			return BaseCompressedItem.register(name, baseItem, "thermalfoundation");
		}
	}

	public static class DraconicEvolutionItems {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseCompressedItem C_CORE_DRACONIC_I = register("compressed_core_draconic", new ItemStack(DEFeatures.draconicCore));
			public final BaseCompressedItem C_CORE_WYVERN_I = register("compressed_core_wyvern", new ItemStack(DEFeatures.wyvernCore));
			public final BaseCompressedItem C_CORE_AWAKENED_I = register("compressed_core_awakened", new ItemStack(DEFeatures.awakenedCore));
			public final BaseCompressedItem C_CORE_CHAOTIC_I = register("compressed_core_chaotic", new ItemStack(DEFeatures.chaoticCore));

			public final BaseCompressedItem C_CORE_ENERGY_WYVERN_I = register("compressed_energy_core_wyvern", new ItemStack(DEFeatures.wyvernEnergyCore));
			public final BaseCompressedItem C_CORE_ENERGY_AWAKENED_I = register("compressed_energy_core_awakened", new ItemStack(DEFeatures.draconicEnergyCore));

			public final BaseCompressedItem C_CHAOS_SHARD_I = register("compressed_chaos_shard", new ItemStack(DEFeatures.chaosShard));
			public final BaseCompressedItem C_DRAGON_HEART_I = register("compressed_dragon_heart", new ItemStack(DEFeatures.dragonHeart));
			public final BaseCompressedItem C_DRACONIUM_DUST_I = register("compressed_draconium_dust", new ItemStack(DEFeatures.draconiumDust)).setSmeltingOutput(MCSBlocks.draconic_evolution.normal.C_DRACONIUM_BLOCK_B);
		}

		private BaseCompressedItem register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedItem.register(nameIn, unCompressedItem, "draconicevolution");
		}
	}

	public static class AvaritiaItems {
		protected static final morph.avaritia.init.ModItems items = new morph.avaritia.init.ModItems();
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseCompressedFood C_COSMIC_MEATBALLS_I = new BaseCompressedFood("compressed_cosmic_meatballs", items.cosmic_meatballs, 0, "avaritia").addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.STRENGTH, 760, 2)});
		}

		@SuppressWarnings("unused")
		private BaseCompressedItem register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedItem.register(nameIn, unCompressedItem, "avaritia");
		}
	}

	public static class IndustrialCraft {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseCompressedItem C_URANIUM_I = register("compressed_uranium", "nuclear", true);
			public final BaseCompressedItem C_URANIUM_235_I = register("compressed_uranium_235", "nuclear", 1, true);
			public final BaseCompressedItem C_URANIUM_238_I = register("compressed_uranium_238", "nuclear", 2, true);
			public final BaseCompressedItem C_plutonium_I = register("compressed_plutonium", "nuclear", 3, true);
			public final BaseCompressedItem C_MOX_I = register("compressed_mox", "nuclear", 4, true);
			public final BaseCompressedItem C_URANIUM_PELLET_I = register("compressed_uranium_pellet", "nuclear", 8, true);
			public final BaseCompressedItem C_MOX_PELLET_I = register("compressed_mox_pellet", "nuclear", 9, true);
			public final BaseCompressedItem C_RTG_PELLET_I = register("compressed_rtg_pellet", "nuclear", 10, true);

			// IC Dust
			public final BaseCompressedItem C_dust_clay_I = register("compressed_dust_clay", "dust", 1).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_coal_fuel_I = register("compressed_dust_coal_fuel", "dust", 3).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_diamond_I = register("compressed_dust_diamond", "dust", 5).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_energium_I = register("compressed_dust_energium", "dust", 6).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_lapis_I = register("compressed_dust_lapis", "dust", 9).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_lithium_I = register("compressed_dust_lithium", "dust", 11).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_silicon_dioxide_I = register("compressed_dust_silicon_dioxide", "dust", 13).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_stone_I = register("compressed_dust_stone", "dust", 15).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_tin_hydrated_I = register("compressed_dust_tin_hydrated", "dust", 29).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_cf_powder_I = register("compressed_dust_cf_powder", "crafting", 25).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_coffee_powder_I = register("compressed_dust_coffee_powder", "crop_res", 1).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_fertilizer_I = register("compressed_dust_fertilizer", "crop_res", 2).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_grin_powder_I = register("compressed_dust_grin_powder", "crop_res", 3).setModelMaterial("dust");
			public final BaseCompressedItem C_dust_ashes_I = register("compressed_dust_ashes", "misc_resource").setModelMaterial("dust");

			// IC Dense Plate and Plate
			public final BaseCompressedItem C_plate_lapis_I = register("compressed_plate_lapis", "plate", 4).setModelMaterial("plate");
			public final BaseCompressedItem C_plate_alloy_I = register("compressed_plate_alloy", "crafting", 3).setModelMaterial("plate");
			public final BaseCompressedItem C_plate_obsidian_I = register("compressed_plate_obsidian", "plate", 6).setModelMaterial("plate");
			public final BaseCompressedItem C_plate_iridium_reinforcing_I = register("compressed_plate_iridium_reinforcing", "crafting", 4).setModelMaterial("plate");
			public final BaseCompressedItem C_dense_plate_bronze_I = register("compressed_dense_plate_bronze", "plate", 9).setModelMaterial("plate/dense");
			public final BaseCompressedItem C_dense_plate_copper_I = register("compressed_dense_plate_copper", "plate", 10).setModelMaterial("plate/dense");
			public final BaseCompressedItem C_dense_plate_gold_I = register("compressed_dense_plate_gold", "plate", 11).setModelMaterial("plate/dense");
			public final BaseCompressedItem C_dense_plate_iron_I = register("compressed_dense_plate_iron", "plate", 12).setModelMaterial("plate/dense");
			public final BaseCompressedItem C_dense_plate_lapis_I = register("compressed_dense_plate_lapis", "plate", 13).setModelMaterial("plate/dense");
			public final BaseCompressedItem C_dense_plate_lead_I = register("compressed_dense_plate_lead", "plate", 14).setModelMaterial("plate/dense");
			public final BaseCompressedItem C_dense_plate_obsidian_I = register("compressed_dense_plate_obsidian", "plate", 15).setModelMaterial("plate/dense");
			public final BaseCompressedItem C_dense_plate_steel_I = register("compressed_dense_plate_steel", "plate", 16).setModelMaterial("plate/dense");
			public final BaseCompressedItem C_dense_plate_tin_I = register("compressed_dense_plate_tin", "plate", 17).setModelMaterial("plate/dense");

			// IC Casing
			public final BaseCompressedItem C_casing_bronze_I = register("compressed_casing_bronze", "casing", 0).setModelMaterial("casing");
			public final BaseCompressedItem C_casing_copper_I = register("compressed_casing_copper", "casing", 1).setModelMaterial("casing");
			public final BaseCompressedItem C_casing_gold_I = register("compressed_casing_gold", "casing", 2).setModelMaterial("casing");
			public final BaseCompressedItem C_casing_iron_I = register("compressed_casing_iron", "casing", 3).setModelMaterial("casing");
			public final BaseCompressedItem C_casing_lead_I = register("compressed_casing_lead", "casing", 4).setModelMaterial("casing");
			public final BaseCompressedItem C_casing_steel_I = register("compressed_casing_steel", "casing", 5).setModelMaterial("casing");
			public final BaseCompressedItem C_casing_tin_I = register("compressed_casing_tin", "casing", 6).setModelMaterial("casing");

			// IC Normal
			public final BaseCompressedItem C_ic_alloy_ingot_I = register("compressed_ic_alloy_ingot", "ingot");
			public final BaseCompressedItem C_terra_wart_I = register("compressed_terra_wart", "terra_wart");
			public final BaseCompressedItem C_rubber_I = register("compressed_rubber", "crafting", 0);
			public final BaseCompressedItem C_circuit_I = register("compressed_circuit", "crafting", 1);
			public final BaseCompressedItem C_advanced_circuit_I = register("compressed_advanced_circuit", "crafting", 2);
			public final BaseCompressedItem C_coil_I = register("compressed_coil", "crafting", 5);
			public final BaseCompressedItem C_electric_motor_I = register("compressed_electric_motor", "crafting", 6);
			public final BaseCompressedItem C_heat_conductor_I = register("compressed_heat_conductor", "crafting", 7);
			public final BaseCompressedItem C_copper_boiler_I = register("compressed_copper_boiler", "crafting", 8);
			public final BaseCompressedItem C_coal_chunk_I = register("compressed_coal_chunk", "crafting", 18);
			public final BaseCompressedItem C_plant_ball_I = register("compressed_plant_ball", "crafting", 20);
			public final BaseCompressedItem C_scrap_I = register("compressed_scrap", "crafting", 23);
			public final BaseCompressedItem C_ic_coin_I = register("compressed_ic_coin", "crafting", 38);
			public final BaseCompressedItem C_iridium_ore_I = register("compressed_iridium_ore", "misc_resource", 1);
			public final BaseCompressedItem C_matter_I = register("compressed_uu_matter", "misc_resource", 3);
			public final BaseCompressedItem C_resin_I = register("compressed_resin", "misc_resource", 4);
			public final BaseCompressedItem C_slag_I = register("compressed_slag", "misc_resource", 5);
			public final BaseCompressedItem C_iodine_I = register("compressed_iodine", "misc_resource", 6);
			public final BaseCompressedItem C_fuel_rod_I = register("compressed_fuel_rod", "crafting", 9);

			// Plating 隔板
			public final BaseCompressedItem C_containment_plating_I = new IC2HeatPlating("compressed_containment_plating", "ic2:containment_plating");
			public final BaseCompressedItem C_heat_plating_I = new IC2HeatPlating("compressed_heat_plating", "ic2:heat_plating");
			public final BaseCompressedItem C_plating_I = new IC2HeatPlating("compressed_plating", "ic2:plating");

			// 枯竭燃料棒
			public final BaseCompressedItem C_DEPLETED_URANIUM_I = register("compressed_depleted_uranium", "nuclear", 11, true);
			public final BaseCompressedItem C_DEPLETED_DUAL_URANIUM_I = register("compressed_depleted_dual_uranium", "nuclear", 12, true);
			public final BaseCompressedItem C_DEPLETED_QUAD_URANIUM_I = register("compressed_depleted_quad_uranium", "nuclear", 13, true);
			// 枯竭燃料棒(mox)
			public final BaseCompressedItem C_DEPLETED_MOX_I = register("compressed_depleted_mox", "nuclear", 14, true);
			public final BaseCompressedItem C_DEPLETED_DUAL_MOX_I = register("compressed_depleted_dual_mox", "nuclear", 15, true);
			public final BaseCompressedItem C_DEPLETED_QUAD_MOX_I = register("compressed_depleted_quad_mox", "nuclear", 16, true);

			// 燃料棒
			public final BaseCompressedItem C_URANIUM_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_uranium_fuel_rod", "ic2:uranium_fuel_rod", C_DEPLETED_URANIUM_I);
			public final BaseCompressedItem C_DUAL_URANIUM_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_dual_uranium_fuel_rod", "ic2:dual_uranium_fuel_rod", C_DEPLETED_DUAL_URANIUM_I);
			public final BaseCompressedItem C_QUAD_URANIUM_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_quad_uranium_fuel_rod", "ic2:quad_uranium_fuel_rod", C_DEPLETED_QUAD_URANIUM_I);
			// 燃料棒(mox)
			public final BaseCompressedItem C_MOX_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_mox_fuel_rod", "ic2:mox_fuel_rod", C_DEPLETED_MOX_I);
			public final BaseCompressedItem C_DUAL_MOX_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_dual_mox_fuel_rod", "ic2:dual_mox_fuel_rod", C_DEPLETED_DUAL_MOX_I);
			public final BaseCompressedItem C_QUAD_MOX_FUEL_ROD_I = new IC2ReactorFuelRod("compressed_quad_mox_fuel_rod", "ic2:quad_mox_fuel_rod", C_DEPLETED_QUAD_MOX_I);

			public final BaseCompressedItem C_energy_crystal_I = new IC2EnergyCrystal("compressed_energy_crystal", "energy_crystal");
			public final BaseCompressedItem C_lapotron_crystal_I = new IC2EnergyCrystal("compressed_lapotron_crystal", "lapotron_crystal");
			public final BaseCompressedItem C_re_battery_I = new IC2EnergyCrystal("compressed_re_battery", "re_battery");
			public final BaseCompressedItem C_advanced_re_battery_I = new IC2EnergyCrystal("compressed_advanced_re_battery", "advanced_re_battery");

			public final BaseCompressedItem C_charging_re_battery_I = new IC2BatteryCharge("compressed_charging_re_battery", "charging_re_battery");
			public final BaseCompressedItem C_charging_advanced_re_battery_I = new IC2BatteryCharge("compressed_charging_advanced_re_battery", "advanced_charging_re_battery");
			public final BaseCompressedItem C_charging_energy_crystal_I = new IC2BatteryCharge("compressed_charging_energy_crystal", "charging_energy_crystal");
			public final BaseCompressedItem C_charging_lapotron_crystal_I = new IC2BatteryCharge("compressed_charging_lapotron_crystal", "charging_lapotron_crystal");

			// IC Condensator
			public final BaseCompressedItem C_lzh_condensator_I = new IC2Condensator("compressed_lzh_condensator", "ic2:lzh_condensator");
			public final BaseCompressedItem C_rsh_condensator_I = new IC2Condensator("compressed_rsh_condensator", "ic2:rsh_condensator");

			// IC Reflector
			public final BaseCompressedItem C_neutron_reflector_I = new IC2Reflector("compressed_neutron_reflector", "ic2:neutron_reflector");
			public final BaseCompressedItem C_thick_neutron_reflector_I = new IC2Reflector("compressed_thick_neutron_reflector", "ic2:thick_neutron_reflector");
			public final BaseCompressedItem C_iridium_reflector_I = new IC2Reflector("compressed_iridium_reflector", "ic2:iridium_reflector");

			// IC Exchanger
			public final BaseCompressedItem C_heat_exchanger_I = new IC2HeatExchanger("compressed_heat_exchanger", "ic2:heat_exchanger");
			public final BaseCompressedItem C_reactor_heat_exchanger_I = new IC2HeatExchanger("compressed_reactor_heat_exchanger", "ic2:reactor_heat_exchanger");
			public final BaseCompressedItem C_component_heat_exchanger_I = new IC2HeatExchanger("compressed_component_heat_exchanger", "ic2:component_heat_exchanger");
			public final BaseCompressedItem C_advanced_heat_exchanger_I = new IC2HeatExchanger("compressed_advanced_heat_exchanger", "ic2:advanced_heat_exchanger");

			// IC Vent
			public final BaseCompressedItem C_heat_vent_I = new IC2HeatVent("compressed_heat_vent", "ic2:heat_vent");
			public final BaseCompressedItem C_reactor_heat_vent_I = new IC2HeatVent("compressed_reactor_heat_vent", "ic2:reactor_heat_vent");
			public final BaseCompressedItem C_overclocked_heat_vent_I = new IC2HeatVent("compressed_overclocked_heat_vent", "ic2:overclocked_heat_vent");
			public final BaseCompressedItem C_component_heat_vent_I = new IC2HeatVent("compressed_component_heat_vent", "ic2:component_heat_vent");
			public final BaseCompressedItem C_advanced_heat_vent_I = new IC2HeatVent("compressed_advanced_heat_ventr", "ic2:advanced_heat_vent");

			// IC Heat Storage
			public final BaseCompressedItem C_heat_storage_I = new IC2HeatStorage("compressed_heat_storage", "ic2:heat_storage");
			public final BaseCompressedItem C_tri_heat_storage_I = new IC2HeatStorage("compressed_tri_heat_storage", "ic2:tri_heat_storage");
			public final BaseCompressedItem C_hex_heat_storage_I = new IC2HeatStorage("compressed_hex_heat_storage", "ic2:hex_heat_storage");

		}

		private BaseCompressedItem register(String nameIn, String unCompressedItem) {
			return register(nameIn, unCompressedItem, 0);
		}

		private BaseCompressedItem register(String nameIn, String unCompressedItem, boolean hasRadiation) {
			return register(nameIn, unCompressedItem, 0, hasRadiation);
		}

		private BaseCompressedItem register(String nameIn, String unCompressedItem, int meta) {
			return register(nameIn, unCompressedItem, meta, false);
		}

		private BaseCompressedItem register(String nameIn, String unCompressedItem, int meta, boolean hasRadiation) {
			if(hasRadiation) {
				return IC2RadiationItem.register(nameIn, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, meta), "ic2");
			}else {
				return BaseCompressedItem.register(nameIn, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, meta), "ic2");
			}
		}
	}

	public static class AppliedEnergistics2 {
		private final IDefinitions items = AEApi.instance().definitions();
		public final Normal normal = new Normal();

		public class Normal {
			// Storage Cell 存储元件
			public final BaseCompressedItem C_item_cell_1k_I = new AEItemStorageCell("compressed_item_cell_1k", items.items().cell1k()).setModelMaterial("storage_cell/cell");
			public final BaseCompressedItem C_item_cell_4k_I = new AEItemStorageCell("compressed_item_cell_4k", items.items().cell4k()).setModelMaterial("storage_cell/cell");
			public final BaseCompressedItem C_item_cell_16k_I = new AEItemStorageCell("compressed_item_cell_16k", items.items().cell16k()).setModelMaterial("storage_cell/cell");
			public final BaseCompressedItem C_item_cell_64k_I = new AEItemStorageCell("compressed_item_cell_64k", items.items().cell64k()).setModelMaterial("storage_cell/cell");

			public final BaseCompressedItem C_fluid_cell_1k_I = new AEFluidStorageCell("compressed_fluid_cell_1k", items.items().fluidCell1k()).setModelMaterial("storage_cell/cell");
			public final BaseCompressedItem C_fluid_cell_4k_I = new AEFluidStorageCell("compressed_fluid_cell_4k", items.items().fluidCell4k()).setModelMaterial("storage_cell/cell");
			public final BaseCompressedItem C_fluid_cell_16k_I = new AEFluidStorageCell("compressed_fluid_cell_16k", items.items().fluidCell16k()).setModelMaterial("storage_cell/cell");
			public final BaseCompressedItem C_fluid_cell_64k_I = new AEFluidStorageCell("compressed_fluid_cell_64k", items.items().fluidCell64k()).setModelMaterial("storage_cell/cell");

			// Part 存储组件
			public final BaseCompressedItem C_item_cell_part_1k_I = register("compressed_item_cell_part_1k", items.materials().cell1kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseCompressedItem C_item_cell_part_4k_I = register("compressed_item_cell_part_4k", items.materials().cell4kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseCompressedItem C_item_cell_part_16k_I = register("compressed_item_cell_part_16k", items.materials().cell16kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseCompressedItem C_item_cell_part_64k_I = register("compressed_item_cell_part_64k", items.materials().cell64kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");

			public final BaseCompressedItem C_fluid_cell_part_1k_I = register("compressed_fluid_cell_part_1k", items.materials().fluidCell1kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseCompressedItem C_fluid_cell_part_4k_I = register("compressed_fluid_cell_part_4k", items.materials().fluidCell4kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseCompressedItem C_fluid_cell_part_16k_I = register("compressed_fluid_cell_part_16k", items.materials().fluidCell16kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseCompressedItem C_fluid_cell_part_64k_I = register("compressed_fluid_cell_part_64k", items.materials().fluidCell64kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");

			// Processor 处理器
			public final BaseCompressedItem C_calc_processor_I = register("compressed_calc_processor", items.materials().calcProcessor()).createOreDictionary(false).setModelMaterial("processor");
			public final BaseCompressedItem C_eng_processor_I = register("compressed_eng_processor", items.materials().engProcessor()).createOreDictionary(false).setModelMaterial("processor");
			public final BaseCompressedItem C_logic_processor_I = register("compressed_logic_processor", items.materials().logicProcessor()).createOreDictionary(false).setModelMaterial("processor");
			// ProcessorPress 模板
			public final BaseCompressedItem C_calc_processor_press_I = register("compressed_calc_processor_press", items.materials().calcProcessorPress()).createOreDictionary(false).setModelMaterial("processor/press");
			public final BaseCompressedItem C_eng_processor_press_I = register("compressed_eng_processor_press", items.materials().engProcessorPress()).createOreDictionary(false).setModelMaterial("processor/press");
			public final BaseCompressedItem C_logic_processor_press_I = register("compressed_logic_processor_press", items.materials().logicProcessorPress()).createOreDictionary(false).setModelMaterial("processor/press");
			public final BaseCompressedItem C_silicon_press_I = register("compressed_silicon_press", items.materials().siliconPress()).createOreDictionary(false).setModelMaterial("processor/press");
			// ProcessorPrint 电路板
			public final BaseCompressedItem C_calc_processor_print_I = register("compressed_calc_processor_print", items.materials().calcProcessorPrint()).createOreDictionary(false).setModelMaterial("processor/print");
			public final BaseCompressedItem C_eng_processor_print_I = register("compressed_eng_processor_print", items.materials().engProcessorPrint()).createOreDictionary(false).setModelMaterial("processor/print");
			public final BaseCompressedItem C_logic_processor_print_I = register("compressed_logic_processor_print", items.materials().logicProcessorPrint()).createOreDictionary(false).setModelMaterial("processor/print");
			public final BaseCompressedItem C_silicon_print_I = register("compressed_silicon_print", items.materials().siliconPrint()).createOreDictionary(false).setModelMaterial("processor/print");

			// Pure Crystal 高纯水晶
			public final BaseCompressedItem C_pure_fluix_crystal_I = register("compressed_pure_fluix_crystal", items.materials().purifiedFluixCrystal()).createOreDictionary(false).setModelMaterial("crystal/pure");
			public final BaseCompressedItem C_pure_nether_crystal_I = register("compressed_pure_nether_crystal", items.materials().purifiedNetherQuartzCrystal()).createOreDictionary(false).setModelMaterial("crystal/pure");
			public final BaseCompressedItem C_pure_certus_crystal_I = register("compressed_pure_certus_crystal", items.materials().purifiedCertusQuartzCrystal()).createOreDictionary(false).setModelMaterial("crystal/pure");
			// Pure Crystal Seed 高纯水晶种子
			public final BaseCompressedItem C_fluix_seed_I = new AECrystalSeed("compressed_fluix_seed", items.items().crystalSeed(), 1200, C_pure_fluix_crystal_I).setModelMaterial("crystal/seed");
			public final BaseCompressedItem C_nether_seed_I = new AECrystalSeed("compressed_nether_seed", items.items().crystalSeed(), 600, C_pure_nether_crystal_I).setModelMaterial("crystal/seed");
			public final BaseCompressedItem C_certus_seed_I = new AECrystalSeed("compressed_certus_seed", items.items().crystalSeed(), 0, C_pure_certus_crystal_I).setModelMaterial("crystal/seed");

			// Normal 其他
			public final BaseCompressedItem C_empty_storage_cell_I = register("compressed_empty_storage_cell", items.materials().emptyStorageCell());
			public final BaseCompressedItem C_silicon_I = register("compressed_silicon", items.materials().silicon());
			public final BaseCompressedItem C_ender_dust_I = register("compressed_ender_dust", items.materials().enderDust()).setModelMaterial("dust");
			public final BaseCompressedItem C_certus_dust_I = register("compressed_certus_dust", items.materials().certusQuartzDust()).setModelMaterial("dust").setSmeltingOutput(C_silicon_I);
			public final BaseCompressedItem C_matter_ball_I = register("compressed_matter_ball", items.materials().matterBall());
			public final BaseCompressedItem C_annihilation_core_I = register("compressed_annihilation_core", items.materials().annihilationCore()).createOreDictionary(false);
			public final BaseCompressedItem C_formation_core_I = register("compressed_formation_core", items.materials().formationCore()).createOreDictionary(false);
			public final BaseCompressedItem C_certus_quartz_crystal_I = register("compressed_quartz_crystal", items.materials().certusQuartzCrystal()).setModelMaterial("crystal");
			public final BaseCompressedItem C_certus_quartz_crystal_charged_I = register("compressed_quartz_crystal_charged", items.materials().certusQuartzCrystalCharged()).setModelMaterial("crystal");
			public final BaseCompressedItem C_fluix_crystal_I = register("compressed_fluix_crystal", items.materials().fluixCrystal()).setModelMaterial("crystal");
			public final BaseCompressedItem C_sky_dust_I = register("compressed_sky_dust", items.materials().skyDust()).setModelMaterial("dust");
			public final BaseCompressedItem C_fluix_dust_I = register("compressed_fluix_dust", items.materials().fluixDust()).setModelMaterial("dust");
			public final BaseCompressedItem C_singularity_I = register("compressed_ae_singularity", items.materials().singularity()).createOreDictionary(false);
			public final BaseCompressedItem C_fluix_pearl_I = register("compressed_fluix_pearl", items.materials().fluixPearl()).createOreDictionary(false);
			public final BaseCompressedItem C_nether_quartz_dust_I = register("compressed_nether_quartz_dust", items.materials().netherQuartzDust()).setModelMaterial("dust").setSmeltingOutput(C_silicon_I);

		}

		private BaseCompressedItem register(String name, IItemDefinition item) {
			return this.register(name, item.maybeStack(1).get());
		}

		private BaseCompressedItem register(String nameIn, ItemStack stack) {
			return BaseCompressedItem.register(nameIn, stack, "appliedenergistics2");
		}
	}

	public static class MCSItem {
		public final BaseItemNormal CAT_HAIR = new BaseItemNormal("cat_hair", CreativeTabs.MISC);
		public final BaseItemNormal CAT_INGOT = new BaseItemNormal("cat_ingot", CreativeTabs.MISC).addI18nInfo("info.mcs.cat_ingot");
		public final BaseItemNormal CAT_HAMMER = new ItemCatHammer("cat_hammer", CreativeTabs.TOOLS);
		public final BaseItemNormal DESTROYER = new ItemDestroyer("destroyer", CreativeTabs.TOOLS);
		public final BaseItem.Normal debug = new ItemDeBug("debug").setModelResourceLocation("normal/items", "debug");
	}
}
