package cat.jiu.mcs.util.init;

import java.util.List;

import com.brandon3055.draconicevolution.DEFeatures;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItemDefinition;

import cofh.thermalfoundation.init.TFItems;

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
		for(BaseItemFood food : MCSResources.FOODS) {
			if(food.createOreDictionary()) {
				if(!food.addOtherOreDictionary().isEmpty()) {
					List<String> ores = food.addOtherOreDictionary();
					for(int i = 0; i < 16; i++) {
						String ore = ores.get(i);
						OreDictionary.registerOre((i + 1) + "x" + ore, new ItemStack(food, 1, i));
					}
				}
				JiuUtils.item.registerCompressedOre(food.getUnCompressedName(), food, false);
			}
		}
		for(BaseItemSub item : MCSResources.SUB_ITEMS) {
			if(item.createOreDictionary()) {
				if(!item.addOtherOreDictionary().isEmpty()) {
					List<String> ores = item.addOtherOreDictionary();
					for(int i = 0; i < 16; i++) {
						String ore = ores.get(i);
						OreDictionary.registerOre((i + 1) + "x" + ore, new ItemStack(item, 1, i));
					}
				}
				JiuUtils.item.registerCompressedOre(item.getUnCompressedName(), item, false);
			}
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
			public final BaseItemFood C_goldEN_CARROT_F = new BaseItemFood("compressed_golden_carrot", Items.GOLDEN_CARROT);
			public final BaseItemFood C_PUMPKIN_PIE_F = new BaseItemFood("compressed_pumpkin_pie", Items.PUMPKIN_PIE);
			public final BaseItemFood C_BEETROOT_F = new BaseItemFood("compressed_beetroot", Items.BEETROOT);
			public final BaseItemFood C_SPECKLED_MELON_F = new BaseItemFood("compressed_speckled_melon", Items.SPECKLED_MELON).setFoodEntry(3, 0.9F, false);
			public final BaseItemFood C_MUSHROOM_STEW_F = new BaseItemFood("compressed_mushron_stew", Items.MUSHROOM_STEW).setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseItemFood C_RABBIT_STEW_F = new BaseItemFood("compressed_rabbit_stew", Items.RABBIT_STEW).setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseItemFood C_BEETROOT_SOUP_F = new BaseItemFood("compressed_beetroot_soup", Items.BEETROOT_SOUP).setContainer(new ItemStack(normal.C_BOWL_I));
			public final BaseItemFood C_PUFFER_FISH_F = new BaseItemFood("compressed_puffer_fish", Items.FISH, 3).addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.POISON, 2280, 3), new CustomStuffType.PotionEffectType(MobEffects.HUNGER, 570, 2), new CustomStuffType.PotionEffectType(MobEffects.NAUSEA, 570, 1)});
			public final BaseItemFood C_GOLD_APPLE_F = new BaseItemFood("compressed_gold_apple", Items.GOLDEN_APPLE, 0).addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.REGENERATION, 220, 1), new CustomStuffType.PotionEffectType(MobEffects.ABSORPTION, 4560, 0)});
			public final BaseItemFood C_ENCHANTED_GOLD_APPLE_F = new BaseItemFood("compressed_enchanted_gold_apple", Items.GOLDEN_APPLE, 1).addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.REGENERATION, 760, 1), new CustomStuffType.PotionEffectType(MobEffects.RESISTANCE, 11400, 0), new CustomStuffType.PotionEffectType(MobEffects.FIRE_RESISTANCE, 11400, 0), new CustomStuffType.PotionEffectType(MobEffects.ABSORPTION, 4560, 3)});
			public final BaseItemFood C_SPIDER_EYE_F = new BaseItemFood("compressed_spider_eye", Items.SPIDER_EYE).addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.POISON, 180, 1)}).setContainer(new ItemStack(C_ENCHANTED_GOLD_APPLE_F));
		}

		public class Tool {
			public final Sword sword = new Sword();
			public final Pickaxe pickaxe = new Pickaxe();
			public final Shovel shovel = new Shovel();
			public final Axe axe = new Axe();
			public final Hoe hoe = new Hoe();

			public class Sword {
				public final BaseItemSword C_DIAMOND_SWORD_I = new BaseItemSword("compressed_diamond_sword", new ItemStack(Items.DIAMOND_SWORD));
				public final BaseItemSword C_IRON_SWORD_I = new BaseItemSword("compressed_iron_sword", new ItemStack(Items.IRON_SWORD));
				public final BaseItemSword C_STONE_SWORD_I = new BaseItemSword("compressed_stone_sword", new ItemStack(Items.STONE_SWORD));
				public final BaseItemSword C_WOODEN_SWORD_I = new BaseItemSword("compressed_wooden_sword", new ItemStack(Items.WOODEN_SWORD));
				public final BaseItemSword C_goldEN_SWORD_I = new BaseItemSword("compressed_golden_sword", new ItemStack(Items.GOLDEN_SWORD));

			}
			public class Pickaxe {
				public final BaseItemPickaxe C_DIAMOND_PICKAXE_I = new BaseItemPickaxe("compressed_diamond_pickaxe", new ItemStack(Items.DIAMOND_PICKAXE));
				public final BaseItemPickaxe C_IRON_PICKAXE_I = new BaseItemPickaxe("compressed_iron_pickaxe", new ItemStack(Items.IRON_PICKAXE));
				public final BaseItemPickaxe C_STONE_PICKAXE_I = new BaseItemPickaxe("compressed_stone_pickaxe", new ItemStack(Items.STONE_PICKAXE));
				public final BaseItemPickaxe C_WOODEN_PICKAXE_I = new BaseItemPickaxe("compressed_wooden_pickaxe", new ItemStack(Items.WOODEN_PICKAXE));
				public final BaseItemPickaxe C_goldEN_PICKAXE_I = new BaseItemPickaxe("compressed_golden_pickaxe", new ItemStack(Items.GOLDEN_PICKAXE));

			}
			public class Shovel {
				public final BaseItemShovel C_DIAMOND_SHOVEL_I = new BaseItemShovel("compressed_diamond_shovel", new ItemStack(Items.DIAMOND_SHOVEL));
				public final BaseItemShovel C_IRON_SHOVEL_I = new BaseItemShovel("compressed_iron_shovel", new ItemStack(Items.IRON_SHOVEL));
				public final BaseItemShovel C_STONE_SHOVEL_I = new BaseItemShovel("compressed_stone_shovel", new ItemStack(Items.STONE_SHOVEL));
				public final BaseItemShovel C_WOODEN_SHOVEL_I = new BaseItemShovel("compressed_wooden_shovel", new ItemStack(Items.WOODEN_SHOVEL));
				public final BaseItemShovel C_goldEN_SHOVEL_I = new BaseItemShovel("compressed_golden_shovel", new ItemStack(Items.GOLDEN_SHOVEL));

			}
			public class Axe {
				public final BaseItemAxe C_DIAMOND_AXE_I = new BaseItemAxe("compressed_diamond_axe", new ItemStack(Items.DIAMOND_AXE));
				public final BaseItemAxe C_IRON_AXE_I = new BaseItemAxe("compressed_iron_axe", new ItemStack(Items.IRON_AXE));
				public final BaseItemAxe C_STONE_AXE_I = new BaseItemAxe("compressed_stone_axe", new ItemStack(Items.STONE_AXE));
				public final BaseItemAxe C_WOODEN_AXE_I = new BaseItemAxe("compressed_wooden_axe", new ItemStack(Items.WOODEN_AXE));
				public final BaseItemAxe C_goldEN_AXE_I = new BaseItemAxe("compressed_golden_axe", new ItemStack(Items.GOLDEN_AXE));

			}
			public class Hoe {
				public final BaseItemHoe C_DIAMOND_HOE_I = new BaseItemHoe("compressed_diamond_hoe", new ItemStack(Items.DIAMOND_HOE));
				public final BaseItemHoe C_IRON_HOE_I = new BaseItemHoe("compressed_iron_hoe", new ItemStack(Items.IRON_HOE));
				public final BaseItemHoe C_STONE_HOE_I = new BaseItemHoe("compressed_stone_hoe", new ItemStack(Items.STONE_HOE));
				public final BaseItemHoe C_WOODEN_HOE_I = new BaseItemHoe("compressed_wooden_hoe", new ItemStack(Items.WOODEN_HOE));
				public final BaseItemHoe C_goldEN_HOE_I = new BaseItemHoe("compressed_golden_hoe", new ItemStack(Items.GOLDEN_HOE));

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
			public final BaseItemSub C_plate_aluminum_I = TF ? register("aluminum", TFItems.itemMaterial.plateAluminum, "thermalfoundation") : null;
			public final BaseItemSub C_plate_bronze_I = TF ? register("bronze", TFItems.itemMaterial.plateBronze, "thermalfoundation") : IC ? register("bronze", "plate", "ic2") : null;
			public final BaseItemSub C_plate_constantan_I = TF ? register("constantan", TFItems.itemMaterial.plateConstantan, "thermalfoundation") : null;
			public final BaseItemSub C_plate_copper_I = TF ? register("copper", TFItems.itemMaterial.plateCopper, "thermalfoundation") : IC ? register("copper", "plate", 1, "ic2") : null;
			public final BaseItemSub C_plate_electrum_I = TF ? register("electrum", TFItems.itemMaterial.plateElectrum, "thermalfoundation") : null;
			public final BaseItemSub C_plate_gold_I = TF ? register("gold", TFItems.itemMaterial.plateGold, "thermalfoundation") : IC ? register("gold", "plate", 2, "ic2") : null;
			public final BaseItemSub C_plate_invar_I = TF ? register("invar", TFItems.itemMaterial.plateInvar, "thermalfoundation") : null;
			public final BaseItemSub C_plate_iridium_I = TF ? register("iridium", TFItems.itemMaterial.plateIridium, "thermalfoundation") : null;
			public final BaseItemSub C_plate_iron_I = TF ? register("iron", TFItems.itemMaterial.plateIron, "thermalfoundation") : IC ? register("iron", "plate", 3, "ic2") : null;
			public final BaseItemSub C_plate_lead_I = TF ? register("lead", TFItems.itemMaterial.plateLead, "thermalfoundation") : IC ? register("lead", "plate", 5, "ic2") : null;
			public final BaseItemSub C_plate_nickel_I = TF ? register("nickel", TFItems.itemMaterial.plateNickel, "thermalfoundation") : null;
			public final BaseItemSub C_plate_platinum_I = TF ? register("platinum", TFItems.itemMaterial.platePlatinum, "thermalfoundation") : null;
			public final BaseItemSub C_plate_silver_I = TF ? register("silver", TFItems.itemMaterial.plateSilver, "thermalfoundation") : null;
			public final BaseItemSub C_plate_steel_I = TF ? register("steel", TFItems.itemMaterial.plateSteel, "thermalfoundation") : IC ? register("steel", "plate", 7, "ic2") : null;
			public final BaseItemSub C_plate_tin_I = TF ? register("tin", TFItems.itemMaterial.plateTin, "thermalfoundation") : IC ? register("tin", "plate", 8, "ic2") : null;

			private BaseItemSub register(String ore, String unCompressed, String ownerMod) {
				return this.register(ore, unCompressed, 0, ownerMod);
			}

			private BaseItemSub register(String ore, String unCompressed, int meta, String ownerMod) {
				return this.register(ore, new ItemStack(Item.getByNameOrId(ownerMod + ":" + unCompressed), 1, meta), ownerMod);
			}

			private BaseItemSub register(String ore, ItemStack unCompressed, String ownerMod) {
				BaseItemSub item = BaseItemSub.register("compressed_plate_" + ore, unCompressed, ownerMod);
				if(item == null) {
					return null;
				}
				return item.setModelMaterial("plate");
			}
		}
		public class Gear {
			public final BaseItemSub C_gear_wood_I = TF ? register("wood", TFItems.itemMaterial.gearWood, "thermalfoundation") : AE ? register("wood", AEApi.instance().definitions().materials().woodenGear().maybeStack(1).get(), "appliedenergistics2") : null;
			public final BaseItemSub C_gear_stone_I = TF ? register("stone", TFItems.itemMaterial.gearStone, "thermalfoundation") : null;
			public final BaseItemSub C_gear_iron_I = TF ? register("iron", TFItems.itemMaterial.gearIron, "thermalfoundation") : null;
			public final BaseItemSub C_gear_gold_I = TF ? register("gold", TFItems.itemMaterial.gearGold, "thermalfoundation") : null;
			public final BaseItemSub C_gear_diamond_I = TF ? register("diamond", TFItems.itemMaterial.gearDiamond, "thermalfoundation") : null;
			public final BaseItemSub C_gear_emerald_I = TF ? register("emerald", TFItems.itemMaterial.gearEmerald, "thermalfoundation") : null;

			public final BaseItemSub C_gear_aluminum_I = TF ? register("aluminum", TFItems.itemMaterial.gearAluminum, "thermalfoundation") : null;
			public final BaseItemSub C_gear_bronze_I = TF ? register("bronze", TFItems.itemMaterial.gearBronze, "thermalfoundation") : null;
			public final BaseItemSub C_gear_constantan_I = TF ? register("constantan", TFItems.itemMaterial.gearConstantan, "thermalfoundation") : null;
			public final BaseItemSub C_gear_copper_I = TF ? register("copper", TFItems.itemMaterial.gearCopper, "thermalfoundation") : null;
			public final BaseItemSub C_gear_electrum_I = TF ? register("electrum", TFItems.itemMaterial.gearElectrum, "thermalfoundation") : null;
			public final BaseItemSub C_gear_invar_I = TF ? register("invar", TFItems.itemMaterial.gearInvar, "thermalfoundation") : null;
			public final BaseItemSub C_gear_iridium_I = TF ? register("iridium", TFItems.itemMaterial.gearIridium, "thermalfoundation") : null;
			public final BaseItemSub C_gear_lead_I = TF ? register("lead", TFItems.itemMaterial.gearLead, "thermalfoundation") : null;
			public final BaseItemSub C_gear_nickel_I = TF ? register("nickel", TFItems.itemMaterial.gearNickel, "thermalfoundation") : null;
			public final BaseItemSub C_gear_platinum_I = TF ? register("platinum", TFItems.itemMaterial.gearPlatinum, "thermalfoundation") : null;
			public final BaseItemSub C_gear_silver_I = TF ? register("silver", TFItems.itemMaterial.gearSilver, "thermalfoundation") : null;
			public final BaseItemSub C_gear_steel_I = TF ? register("steel", TFItems.itemMaterial.gearSteel, "thermalfoundation") : null;
			public final BaseItemSub C_gear_tin_I = TF ? register("tin", TFItems.itemMaterial.gearTin, "thermalfoundation") : null;

			@SuppressWarnings("unused")
			private BaseItemSub register(String ore, String unCompressed, String ownerMod) {
				return this.register(ore, unCompressed, 0, ownerMod);
			}

			private BaseItemSub register(String ore, String unCompressed, int meta, String ownerMod) {
				return this.register(ore, new ItemStack(Item.getByNameOrId(ownerMod + ":" + unCompressed), 1, meta), ownerMod);
			}

			private BaseItemSub register(String ore, ItemStack unCompressed, String ownerMod) {
				BaseItemSub item = BaseItemSub.register("compressed_gear_" + ore, unCompressed, ownerMod);
				if(item == null) {
					return null;
				}
				return item.setModelMaterial("gear");
			}
		}
		public class Dust {
			public final BaseItemSub C_dust_aluminum_I = TF ? register("aluminum", TFItems.itemMaterial.dustAluminum, "thermalfoundation") : null;
			public final BaseItemSub C_dust_bronze_I = TF ? register("bronze", TFItems.itemMaterial.dustBronze, "thermalfoundation") : IC ? register("bronze", "dust", "ic2") : null;
			public final BaseItemSub C_dust_constantan_I = TF ? register("constantan", TFItems.itemMaterial.dustConstantan, "thermalfoundation") : null;
			public final BaseItemSub C_dust_copper_I = TF ? register("copper", TFItems.itemMaterial.dustCopper, "thermalfoundation") : IC ? register("copper", "dust", 4, "ic2") : null;
			public final BaseItemSub C_dust_electrum_I = TF ? register("electrum", TFItems.itemMaterial.dustElectrum, "thermalfoundation") : null;
			public final BaseItemSub C_dust_gold_I = TF ? register("gold", TFItems.itemMaterial.dustGold, "thermalfoundation") : IC ? register("gold", "dust", 7, "ic2") : AE ? register("gold", AEApi.instance().definitions().materials().goldDust().maybeStack(1).get(), "appliedenergistics2") : null;
			public final BaseItemSub C_dust_invar_I = TF ? register("invar", TFItems.itemMaterial.dustInvar, "thermalfoundation") : null;
			public final BaseItemSub C_dust_iridium_I = TF ? register("iridium", TFItems.itemMaterial.dustIridium, "thermalfoundation") : null;
			public final BaseItemSub C_dust_iron_I = TF ? register("iron", TFItems.itemMaterial.dustIron, "thermalfoundation") : IC ? register("iron", "dust", 8, "ic2") : AE ? register("iron", AEApi.instance().definitions().materials().ironDust().maybeStack(1).get(), "appliedenergistics2") : null;
			public final BaseItemSub C_dust_lead_I = TF ? register("lead", TFItems.itemMaterial.dustLead, "thermalfoundation") : IC ? register("lead", "dust", 10, "ic2") : null;
			public final BaseItemSub C_dust_nickel_I = TF ? register("nickel", TFItems.itemMaterial.dustNickel, "thermalfoundation") : null;
			public final BaseItemSub C_dust_platinum_I = TF ? register("platinum", TFItems.itemMaterial.dustPlatinum, "thermalfoundation") : null;
			public final BaseItemSub C_dust_silver_I = TF ? register("silver", TFItems.itemMaterial.dustSilver, "thermalfoundation") : IC ? register("silver", "dust", 14, "ic2") : null;
			public final BaseItemSub C_dust_steel_I = TF ? register("steel", TFItems.itemMaterial.dustSteel, "thermalfoundation") : null;
			public final BaseItemSub C_dust_tin_I = TF ? register("tin", TFItems.itemMaterial.dustTin, "thermalfoundation") : IC ? register("tin", "dust", 17, "ic2") : null;
			public final BaseItemSub C_dust_coal_I = TF ? register("coal", TFItems.itemMaterial.dustCoal, "thermalfoundation") : IC ? register("coal", "dust", 2, "ic2") : null;
			public final BaseItemSub C_dust_obsidian_I = TF ? register("obsidian", TFItems.itemMaterial.dustObsidian, "thermalfoundation") : IC ? register("obsidian", "dust", 12, "ic2") : null;
			public final BaseItemSub C_dust_sulfur_I = TF ? register("sulfur", TFItems.itemMaterial.dustSulfur, "thermalfoundation") : IC ? register("sulfur", "dust", 16, "ic2") : null;

			private BaseItemSub register(String ore, String unCompressed, String ownerMod) {
				return this.register(ore, unCompressed, 0, ownerMod);
			}

			private BaseItemSub register(String ore, String unCompressed, int meta, String ownerMod) {
				return this.register(ore, new ItemStack(Item.getByNameOrId(ownerMod + ":" + unCompressed), 1, meta), ownerMod);
			}

			private BaseItemSub register(String ore, ItemStack unCompressed, String ownerMod) {
				BaseItemSub item = BaseItemSub.register("compressed_dust_" + ore, unCompressed, ownerMod);
				if(item == null) {
					return null;
				}
				return item.setModelMaterial("dust");
			}
		}
	}

	public static class ThermalFoundationItem {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseItemSub C_DYE_I = register("compressed_tf_dye", TFItems.itemDye.dyeWhite);
			public final BaseItemSub C_SAWDUST_I = register("compressed_wood_dust", TFItems.itemMaterial.dustWoodCompressed);
			// Crystal 络合物
			public final BaseItemSub C_CRYSTAL_ENDER_I = register("compressed_crystal_ender", TFItems.itemMaterial.crystalEnder);
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
			public final BaseItemSub C_PLATE_LUMIUM_I = register("compressed_plate_lumium", TFItems.itemMaterial.plateLumium).setModelMaterial("plate");
			public final BaseItemSub C_PLATE_ENDERIUM_I = register("compressed_plate_enderium", TFItems.itemMaterial.plateEnderium).setModelMaterial("plate");
			public final BaseItemSub C_PLATE_SIGNALUM_I = register("compressed_plate_signalum", TFItems.itemMaterial.plateSignalum).setModelMaterial("plate");
			public final BaseItemSub C_PLATE_MITHRIL_I = register("compressed_plate_mithri", TFItems.itemMaterial.plateMithril).setModelMaterial("plate");

			// Gear 齿轮
			public final BaseItemSub C_GEAR_LUMIUM_I = register("compressed_gear_lumium", TFItems.itemMaterial.gearLumium).setModelMaterial("gear");
			public final BaseItemSub C_GEAR_ENDERIUM_I = register("compressed_gear_enderium", TFItems.itemMaterial.gearEnderium).setModelMaterial("gear");
			public final BaseItemSub C_GEAR_MITHRIL_I = register("compressed_gear_mithril", TFItems.itemMaterial.gearMithril).setModelMaterial("gear");
			public final BaseItemSub C_GEAR_SIGNALUM_I = register("compressed_gear_signalum", TFItems.itemMaterial.gearSignalum).setModelMaterial("gear");

			// Dust 金属粉
			public final BaseItemSub C_DUST_LUMIUM_I = register("compressed_dust_lumium", TFItems.itemMaterial.dustLumium).setModelMaterial("dust");
			public final BaseItemSub C_DUST_ENDERIUM_I = register("compressed_dust_enderium", TFItems.itemMaterial.dustEnderium).setModelMaterial("dust");
			public final BaseItemSub C_DUST_MITHRIL_I = register("compressed_dust_mithril", TFItems.itemMaterial.dustMithril).setModelMaterial("dust");
			public final BaseItemSub C_DUST_SIGNALUM_I = register("compressed_dust_signalum", TFItems.itemMaterial.dustSignalum).setModelMaterial("dust");

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
			public final BaseItemFood C_COSMIC_MEATBALLS_I = new BaseItemFood("compressed_cosmic_meatballs", items.cosmic_meatballs, 0, "avaritia").addPotionEffect(new int[]{-1}, new CustomStuffType.PotionEffectType[]{new CustomStuffType.PotionEffectType(MobEffects.STRENGTH, 760, 2)});
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

			// IC Dust
			public final BaseItemSub C_dust_clay_I = register("compressed_dust_clay", "dust", 1).setModelMaterial("dust");
			public final BaseItemSub C_dust_coal_fuel_I = register("compressed_dust_coal_fuel", "dust", 3).setModelMaterial("dust");
			public final BaseItemSub C_dust_diamond_I = register("compressed_dust_diamond", "dust", 5).setModelMaterial("dust");
			public final BaseItemSub C_dust_energium_I = register("compressed_dust_energium", "dust", 6).setModelMaterial("dust");
			public final BaseItemSub C_dust_lapis_I = register("compressed_dust_lapis", "dust", 9).setModelMaterial("dust");
			public final BaseItemSub C_dust_lithium_I = register("compressed_dust_lithium", "dust", 11).setModelMaterial("dust");
			public final BaseItemSub C_dust_silicon_dioxide_I = register("compressed_dust_silicon_dioxide", "dust", 13).setModelMaterial("dust");
			public final BaseItemSub C_dust_stone_I = register("compressed_dust_stone", "dust", 15).setModelMaterial("dust");
			public final BaseItemSub C_dust_tin_hydrated_I = register("compressed_dust_tin_hydrated", "dust", 29).setModelMaterial("dust");
			public final BaseItemSub C_dust_cf_powder_I = register("compressed_dust_cf_powder", "crafting", 25).setModelMaterial("dust");
			public final BaseItemSub C_dust_coffee_powder_I = register("compressed_dust_coffee_powder", "crop_res", 1).setModelMaterial("dust");
			public final BaseItemSub C_dust_fertilizer_I = register("compressed_dust_fertilizer", "crop_res", 2).setModelMaterial("dust");
			public final BaseItemSub C_dust_grin_powder_I = register("compressed_dust_grin_powder", "crop_res", 3).setModelMaterial("dust");
			public final BaseItemSub C_dust_ashes_I = register("compressed_dust_ashes", "misc_resource").setModelMaterial("dust");

			// IC Dense Plate and Plate
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

			// IC Casing
			public final BaseItemSub C_casing_bronze_I = register("compressed_casing_bronze", "casing", 0).setModelMaterial("casing");
			public final BaseItemSub C_casing_copper_I = register("compressed_casing_copper", "casing", 1).setModelMaterial("casing");
			public final BaseItemSub C_casing_gold_I = register("compressed_casing_gold", "casing", 2).setModelMaterial("casing");
			public final BaseItemSub C_casing_iron_I = register("compressed_casing_iron", "casing", 3).setModelMaterial("casing");
			public final BaseItemSub C_casing_lead_I = register("compressed_casing_lead", "casing", 4).setModelMaterial("casing");
			public final BaseItemSub C_casing_steel_I = register("compressed_casing_steel", "casing", 5).setModelMaterial("casing");
			public final BaseItemSub C_casing_tin_I = register("compressed_casing_tin", "casing", 6).setModelMaterial("casing");

			// IC Normal
			public final BaseItemSub C_ic_alloy_ingot_I = register("compressed_ic_alloy_ingot", "ingot");
			public final BaseItemSub C_terra_wart_I = register("compressed_terra_wart", "terra_wart");
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

			// Plating 隔板
			public final BaseItemSub C_containment_plating_I = new IC2HeatPlating("compressed_containment_plating", "ic2:containment_plating");
			public final BaseItemSub C_heat_plating_I = new IC2HeatPlating("compressed_heat_plating", "ic2:heat_plating");
			public final BaseItemSub C_plating_I = new IC2HeatPlating("compressed_plating", "ic2:plating");

			// 枯竭燃料棒
			public final BaseItemSub C_DEPLETED_URANIUM_I = register("compressed_depleted_uranium", "nuclear", 11, true);
			public final BaseItemSub C_DEPLETED_DUAL_URANIUM_I = register("compressed_depleted_dual_uranium", "nuclear", 12, true);
			public final BaseItemSub C_DEPLETED_QUAD_URANIUM_I = register("compressed_depleted_quad_uranium", "nuclear", 13, true);
			// 枯竭燃料棒(mox)
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

			public final BaseItemSub C_energy_crystal_I = new IC2EnergyCrystal("compressed_energy_crystal", "energy_crystal");
			public final BaseItemSub C_lapotron_crystal_I = new IC2EnergyCrystal("compressed_lapotron_crystal", "lapotron_crystal");
			public final BaseItemSub C_re_battery_I = new IC2EnergyCrystal("compressed_re_battery", "re_battery");
			public final BaseItemSub C_advanced_re_battery_I = new IC2EnergyCrystal("compressed_advanced_re_battery", "advanced_re_battery");

			public final BaseItemSub C_charging_re_battery_I = new IC2BatteryCharge("compressed_charging_re_battery", "charging_re_battery");
			public final BaseItemSub C_charging_advanced_re_battery_I = new IC2BatteryCharge("compressed_charging_advanced_re_battery", "advanced_charging_re_battery");
			public final BaseItemSub C_charging_energy_crystal_I = new IC2BatteryCharge("compressed_charging_energy_crystal", "charging_energy_crystal");
			public final BaseItemSub C_charging_lapotron_crystal_I = new IC2BatteryCharge("compressed_charging_lapotron_crystal", "charging_lapotron_crystal");

			// IC Condensator
			public final BaseItemSub C_lzh_condensator_I = new IC2Condensator("compressed_lzh_condensator", "ic2:lzh_condensator");
			public final BaseItemSub C_rsh_condensator_I = new IC2Condensator("compressed_rsh_condensator", "ic2:rsh_condensator");

			// IC Reflector
			public final BaseItemSub C_neutron_reflector_I = new IC2Reflector("compressed_neutron_reflector", "ic2:neutron_reflector");
			public final BaseItemSub C_thick_neutron_reflector_I = new IC2Reflector("compressed_thick_neutron_reflector", "ic2:thick_neutron_reflector");
			public final BaseItemSub C_iridium_reflector_I = new IC2Reflector("compressed_iridium_reflector", "ic2:iridium_reflector");

			// IC Exchanger
			public final BaseItemSub C_heat_exchanger_I = new IC2HeatExchanger("compressed_heat_exchanger", "ic2:heat_exchanger");
			public final BaseItemSub C_reactor_heat_exchanger_I = new IC2HeatExchanger("compressed_reactor_heat_exchanger", "ic2:reactor_heat_exchanger");
			public final BaseItemSub C_component_heat_exchanger_I = new IC2HeatExchanger("compressed_component_heat_exchanger", "ic2:component_heat_exchanger");
			public final BaseItemSub C_advanced_heat_exchanger_I = new IC2HeatExchanger("compressed_advanced_heat_exchanger", "ic2:advanced_heat_exchanger");

			// IC Vent
			public final BaseItemSub C_heat_vent_I = new IC2HeatVent("compressed_heat_vent", "ic2:heat_vent");
			public final BaseItemSub C_reactor_heat_vent_I = new IC2HeatVent("compressed_reactor_heat_vent", "ic2:reactor_heat_vent");
			public final BaseItemSub C_overclocked_heat_vent_I = new IC2HeatVent("compressed_overclocked_heat_vent", "ic2:overclocked_heat_vent");
			public final BaseItemSub C_component_heat_vent_I = new IC2HeatVent("compressed_component_heat_vent", "ic2:component_heat_vent");
			public final BaseItemSub C_advanced_heat_vent_I = new IC2HeatVent("compressed_advanced_heat_ventr", "ic2:advanced_heat_vent");

			// IC Heat Storage
			public final BaseItemSub C_heat_storage_I = new IC2HeatStorage("compressed_heat_storage", "ic2:heat_storage");
			public final BaseItemSub C_tri_heat_storage_I = new IC2HeatStorage("compressed_tri_heat_storage", "ic2:tri_heat_storage");
			public final BaseItemSub C_hex_heat_storage_I = new IC2HeatStorage("compressed_hex_heat_storage", "ic2:hex_heat_storage");

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

	public static class AppliedEnergistics2 {
		private final IDefinitions items = AEApi.instance().definitions();
		public final Normal normal = new Normal();

		public class Normal {
			// Storage Cell 存储元件
			public final BaseItemSub C_item_cell_1k_I = new AEItemStorageCell("compressed_item_cell_1k", items.items().cell1k()).setModelMaterial("storage_cell/cell");
			public final BaseItemSub C_item_cell_4k_I = new AEItemStorageCell("compressed_item_cell_4k", items.items().cell4k()).setModelMaterial("storage_cell/cell");
			public final BaseItemSub C_item_cell_16k_I = new AEItemStorageCell("compressed_item_cell_16k", items.items().cell16k()).setModelMaterial("storage_cell/cell");
			public final BaseItemSub C_item_cell_64k_I = new AEItemStorageCell("compressed_item_cell_64k", items.items().cell64k()).setModelMaterial("storage_cell/cell");

			public final BaseItemSub C_fluid_cell_1k_I = new AEItemStorageCell("compressed_fluid_cell_1k", items.items().fluidCell1k()).setModelMaterial("storage_cell/cell");
			public final BaseItemSub C_fluid_cell_4k_I = new AEItemStorageCell("compressed_fluid_cell_4k", items.items().fluidCell4k()).setModelMaterial("storage_cell/cell");
			public final BaseItemSub C_fluid_cell_16k_I = new AEItemStorageCell("compressed_fluid_cell_16k", items.items().fluidCell16k()).setModelMaterial("storage_cell/cell");
			public final BaseItemSub C_fluid_cell_64k_I = new AEItemStorageCell("compressed_fluid_cell_64k", items.items().fluidCell64k()).setModelMaterial("storage_cell/cell");

			// Part 存储组件
			public final BaseItemSub C_item_cell_part_1k_I = register("compressed_item_cell_part_1k", items.materials().cell1kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseItemSub C_item_cell_part_4k_I = register("compressed_item_cell_part_4k", items.materials().cell4kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseItemSub C_item_cell_part_16k_I = register("compressed_item_cell_part_16k", items.materials().cell16kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseItemSub C_item_cell_part_64k_I = register("compressed_item_cell_part_64k", items.materials().cell64kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");

			public final BaseItemSub C_fluid_cell_part_1k_I = register("compressed_fluid_cell_part_1k", items.materials().fluidCell1kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseItemSub C_fluid_cell_part_4k_I = register("compressed_fluid_cell_part_4k", items.materials().fluidCell4kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseItemSub C_fluid_cell_part_16k_I = register("compressed_fluid_cell_part_16k", items.materials().fluidCell16kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");
			public final BaseItemSub C_fluid_cell_part_64k_I = register("compressed_fluid_cell_part_64k", items.materials().fluidCell64kPart()).createOreDictionary(false).setModelMaterial("storage_cell/part");

			// Processor 处理器
			public final BaseItemSub C_calc_processor_I = register("compressed_calc_processor", items.materials().calcProcessor()).createOreDictionary(false).setModelMaterial("processor");
			public final BaseItemSub C_eng_processor_I = register("compressed_eng_processor", items.materials().engProcessor()).createOreDictionary(false).setModelMaterial("processor");
			public final BaseItemSub C_logic_processor_I = register("compressed_logic_processor", items.materials().logicProcessor()).createOreDictionary(false).setModelMaterial("processor");
			// ProcessorPress 模板
			public final BaseItemSub C_calc_processor_press_I = register("compressed_calc_processor_press", items.materials().calcProcessorPress()).createOreDictionary(false).setModelMaterial("processor/press");
			public final BaseItemSub C_eng_processor_press_I = register("compressed_eng_processor_press", items.materials().engProcessorPress()).createOreDictionary(false).setModelMaterial("processor/press");
			public final BaseItemSub C_logic_processor_press_I = register("compressed_logic_processor_press", items.materials().logicProcessorPress()).createOreDictionary(false).setModelMaterial("processor/press");
			public final BaseItemSub C_silicon_press_I = register("compressed_silicon_press", items.materials().siliconPress()).createOreDictionary(false).setModelMaterial("processor/press");
			// ProcessorPrint 电路板
			public final BaseItemSub C_calc_processor_print_I = register("compressed_calc_processor_print", items.materials().calcProcessorPrint()).createOreDictionary(false).setModelMaterial("processor/print");
			public final BaseItemSub C_eng_processor_print_I = register("compressed_eng_processor_print", items.materials().engProcessorPrint()).createOreDictionary(false).setModelMaterial("processor/print");
			public final BaseItemSub C_logic_processor_print_I = register("compressed_logic_processor_print", items.materials().logicProcessorPrint()).createOreDictionary(false).setModelMaterial("processor/print");
			public final BaseItemSub C_silicon_print_I = register("compressed_silicon_print", items.materials().siliconPrint()).createOreDictionary(false).setModelMaterial("processor/print");

			// Pure Crystal 高纯水晶
			public final BaseItemSub C_pure_fluix_crystal_I = register("compressed_pure_fluix_crystal", items.materials().purifiedFluixCrystal()).createOreDictionary(false).setModelMaterial("crystal/pure");
			public final BaseItemSub C_pure_nether_crystal_I = register("compressed_pure_nether_crystal", items.materials().purifiedNetherQuartzCrystal()).createOreDictionary(false).setModelMaterial("crystal/pure");
			public final BaseItemSub C_pure_certus_crystal_I = register("compressed_pure_certus_crystal", items.materials().purifiedCertusQuartzCrystal()).createOreDictionary(false).setModelMaterial("crystal/pure");
			// Pure Crystal Seed 高纯水晶种子
			public final BaseItemSub C_fluix_seed_I = new AECrystalSeed("compressed_fluix_seed", items.items().crystalSeed(), 1200, C_pure_fluix_crystal_I).setModelMaterial("crystal/seed");
			public final BaseItemSub C_nether_seed_I = new AECrystalSeed("compressed_nether_seed", items.items().crystalSeed(), 600, C_pure_nether_crystal_I).setModelMaterial("crystal/seed");
			public final BaseItemSub C_certus_seed_I = new AECrystalSeed("compressed_certus_seed", items.items().crystalSeed(), 0, C_pure_certus_crystal_I).setModelMaterial("crystal/seed");

			// Normal 其他
			public final BaseItemSub C_empty_storage_cell_I = register("compressed_empty_storage_cell", items.materials().emptyStorageCell());
			public final BaseItemSub C_silicon_I = register("compressed_silicon", items.materials().silicon());
			public final BaseItemSub C_ender_dust_I = register("compressed_ender_dust", items.materials().enderDust()).setModelMaterial("dust");
			public final BaseItemSub C_certus_dust_I = register("compressed_certus_dust", items.materials().certusQuartzDust()).setModelMaterial("dust");
			public final BaseItemSub C_matter_ball_I = register("compressed_matter_ball", items.materials().matterBall());
			public final BaseItemSub C_annihilation_core_I = register("compressed_annihilation_core", items.materials().annihilationCore()).createOreDictionary(false);
			public final BaseItemSub C_formation_core_I = register("compressed_formation_core", items.materials().formationCore()).createOreDictionary(false);
			public final BaseItemSub C_certus_quartz_crystal_I = register("compressed_quartz_crystal", items.materials().certusQuartzCrystal()).setModelMaterial("crystal");
			public final BaseItemSub C_certus_quartz_crystal_charged_I = register("compressed_quartz_crystal_charged", items.materials().certusQuartzCrystalCharged()).setModelMaterial("crystal");
			public final BaseItemSub C_fluix_crystal_I = register("compressed_fluix_crystal", items.materials().fluixCrystal()).setModelMaterial("crystal");
			public final BaseItemSub C_sky_dust_I = register("compressed_sky_dust", items.materials().skyDust()).setModelMaterial("dust");
			public final BaseItemSub C_fluix_dust_I = register("compressed_fluix_dust", items.materials().fluixDust()).setModelMaterial("dust");
			public final BaseItemSub C_singularity_I = register("compressed_ae_singularity", items.materials().singularity()).createOreDictionary(false);
			public final BaseItemSub C_fluix_pearl_I = register("compressed_fluix_pearl", items.materials().fluixPearl()).createOreDictionary(false);
			public final BaseItemSub C_nether_quartz_dust_I = register("compressed_nether_quartz_dust", items.materials().netherQuartzDust()).setModelMaterial("dust");

		}

		private BaseItemSub register(String name, IItemDefinition item) {
			return this.register(name, item.maybeStack(1).get());
		}

		private BaseItemSub register(String nameIn, ItemStack stack) {
			return BaseItemSub.register(nameIn, stack, "appliedenergistics2");
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
