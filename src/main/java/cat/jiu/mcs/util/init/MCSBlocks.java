package cat.jiu.mcs.util.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import com.brandon3055.draconicevolution.DEFeatures;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import appeng.api.AEApi;
import appeng.api.definitions.IDefinitions;
import appeng.api.definitions.IItemDefinition;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.BlockCompressor;
import cat.jiu.mcs.blocks.BlockCreativeEnergy;
import cat.jiu.mcs.blocks.BlockTest;
import cat.jiu.mcs.blocks.compressed.*;
import cat.jiu.mcs.config.Configs;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import cat.jiu.mcs.util.type.CustomStuffType;

import cofh.thermalfoundation.init.TFBlocks;

import moze_intel.projecte.gameObjs.ObjHandler;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import vazkii.botania.common.block.ModFluffBlocks;

@SuppressWarnings("static-access")
public class MCSBlocks {
	public static final BlockCompressor compressor = new BlockCompressor();
	public static final BlockCreativeEnergy creative_energy = new BlockCreativeEnergy();
	public static final BaseBlockSub C_creative_energy_B = new CompressedCreativeEnergy("compressed_creative_energy", new ItemStack(creative_energy));
	public static cat.jiu.core.util.base.BaseBlock.Normal TEST_BLOCK = null;
	static {
		if(MCS.test()) {
			TEST_BLOCK = new BlockTest().setBlockModelResourceLocation("mcs/block/test", "test_block");
		}
	}

	public static final MinecraftBlock minecraft = new MinecraftBlock();
	public static final OreStuff ore_stuff = new OreStuff();
	public static ThermalFoundationBlock thermal_foundation = null;
	public static EnderIOBlock enderio = null;
	public static ProjectEBlock projecte = null;
	public static DraconicEvolutionBlock draconic_evolution;
	public static EnvironmentalTechBlock environmental_tech = null;
	public static AvaritiaBlock avaritia = null;
	public static TconstructBlock tconstruct = null;
	public static BotaniaBlock botania = null;
	public static IndustrialCraft ic2 = null;
	public static AppliedEnergistics2 ae2 = null;

	public MCSBlocks() {
		if(Configs.Custom.Enable_Mod_Stuff) {
			thermal_foundation = Configs.Custom.Mod_Stuff.ThermalFoundation ? new ThermalFoundationBlock() : null;
			draconic_evolution = Configs.Custom.Mod_Stuff.DraconicEvolution ? new DraconicEvolutionBlock() : null;
			avaritia = Configs.Custom.Mod_Stuff.Avaritia ? new AvaritiaBlock() : null;
			ic2 = Configs.Custom.Mod_Stuff.IndustrialCraft ? new IndustrialCraft() : null;
			ae2 = Configs.Custom.Mod_Stuff.AppliedEnergistics2 ? new AppliedEnergistics2() : null;
			
			enderio = Configs.Custom.Mod_Stuff.EnderIO ? new EnderIOBlock() : null;
			projecte = Configs.Custom.Mod_Stuff.ProjectE ? new ProjectEBlock() : null;
			environmental_tech = Configs.Custom.Mod_Stuff.EnvironmentalTech ? new EnvironmentalTechBlock() : null;
			tconstruct = Configs.Custom.Mod_Stuff.Tconstruct ? new TconstructBlock() : null;
			botania = Configs.Custom.Mod_Stuff.Botania ? new BotaniaBlock() : null;
		}
	}

	public static final void registerOreDict() {
		for(BaseBlockSub block : MCSResources.SUB_BLOCKS) {
			if(block.createOreDictionary()) {
				if(!block.addOtherOreDictionary().isEmpty()) {
					List<String> ores = block.addOtherOreDictionary();
					for(int i = 0; i < 16; i++) {
						String ore = ores.get(i);
						OreDictionary.registerOre((i + 1) + "x" + ore, new ItemStack(block, 1, i));
					}
				}
				JiuUtils.item.registerCompressedOre(block.getUnCompressedName(), block, block.isHas());
			}
		}
	}

	public static class MinecraftBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public class Normal {
			public final BaseBlockSub C_BONE_B = new BaseBlockSub("compressed_bone_block", new ItemStack(Blocks.BONE_BLOCK));
			public final BaseBlockSub C_DIAMOND_B = new BaseBlockSub("compressed_diamond_block", new ItemStack(Blocks.DIAMOND_BLOCK));
			public final BaseBlockSub C_DIRT_B = new BaseBlockSub("compressed_dirt_block", new ItemStack(Blocks.DIRT));
			public final BaseBlockSub C_EMERALD_B = new BaseBlockSub("compressed_emerald_block", new ItemStack(Blocks.EMERALD_BLOCK));
			public final BaseBlockSub C_GLASS_B = new BaseBlockSub("compressed_glass_block", new ItemStack(Blocks.GLASS));
			public final BaseBlockSub C_GLOW_STONE_B = new BaseBlockSub("compressed_glowstone_block", new ItemStack(Blocks.GLOWSTONE));
			public final BaseBlockSub C_GOLD_B = new BaseBlockSub("compressed_gold_block", new ItemStack(Blocks.GOLD_BLOCK));
			public final BaseBlockSub C_GRAVEL_B = new BaseBlockSub("compressed_gravel_block", new ItemStack(Blocks.GRAVEL));
			public final BaseBlockSub C_ICE_B = new BaseBlockSub("compressed_ice_block", new ItemStack(Blocks.ICE)).setIsTransparentCube();
			public final BaseBlockSub C_IRON_B = new BaseBlockSub("compressed_iron_block", new ItemStack(Blocks.IRON_BLOCK));
			public final BaseBlockSub C_MELON_B = new BaseBlockSub("compressed_melon_block", new ItemStack(Blocks.MELON_BLOCK));
			public final BaseBlockSub C_MAGMA_B = new BaseBlockSub("compressed_magma_block", new ItemStack(Blocks.MAGMA));
			public final BaseBlockSub C_NETHERRACK_B = new BaseBlockSub("compressed_netherrack_block", new ItemStack(Blocks.NETHERRACK));
			public final BaseBlockSub C_PUMPKIN_B = new BaseBlockSub("compressed_pumpkin_block", new ItemStack(Blocks.PUMPKIN));
			public final BaseBlockSub C_RED_STONE_B = new BaseBlockSub("compressed_redstone_block", new ItemStack(Blocks.REDSTONE_BLOCK));
			public final BaseBlockSub C_SAND_B = new BaseBlockSub("compressed_sand_block", new ItemStack(Blocks.SAND));
			public final BaseBlockSub C_SNOW_B = new BaseBlockSub("compressed_snow_block", new ItemStack(Blocks.SNOW));
			public final BaseBlockSub C_STONE_B = new BaseBlockSub("compressed_stone_block", new ItemStack(Blocks.STONE));
			public final BaseBlockSub C_WOOL_B = new BaseBlockSub("compressed_wool_block", new ItemStack(Blocks.WOOL));
			public final BaseBlockSub C_QUARTZ_BLOCK_B = new BaseBlockSub("compressed_quartz_block", new ItemStack(Blocks.QUARTZ_BLOCK));
			public final BaseBlockSub C_RED_MUSHROOM_B = new BaseBlockSub("compressed_red_mushroom_block", new ItemStack(Blocks.RED_MUSHROOM));
			public final BaseBlockSub C_BROWN_MUSHROOM_B = new BaseBlockSub("compressed_brown_mushroom_block", new ItemStack(Blocks.BROWN_MUSHROOM));
			public final BaseBlockSub C_PRISMARINE_B = new BaseBlockSub("compressed_prismarine_block", new ItemStack(Blocks.PRISMARINE));
			public final BaseBlockSub C_BRICK_BLOCK_B = new BaseBlockSub("compressed_brick_block", new ItemStack(Blocks.BRICK_BLOCK));
			public final BaseBlockSub C_SOUL_SAND_B = new BaseBlockSub("compressed_soul_sand_block", new ItemStack(Blocks.SOUL_SAND));
			public final BaseBlockSub C_STONE_BRICK_B = new BaseBlockSub("compressed_stone_brick_block", new ItemStack(Blocks.STONEBRICK));
			public final BaseBlockSub C_CLAY_B = new BaseBlockSub("compressed_clay_block", new ItemStack(Blocks.CLAY));
			public final BaseBlockSub C_PURPUR_BLOCK_B = new BaseBlockSub("compressed_purpur_block", new ItemStack(Blocks.PURPUR_BLOCK));
			public final BaseBlockSub C_CONCRETE_POWDER_B = new BaseBlockSub("compressed_concrete_powder_block", new ItemStack(Blocks.CONCRETE_POWDER));
			public final BaseBlockSub C_CONCRETE_B = new BaseBlockSub("compressed_concrete_block", new ItemStack(Blocks.CONCRETE));
			public final BaseBlockSub C_HARDENED_CLAY_B = new BaseBlockSub("compressed_hardened_clay_block", new ItemStack(Blocks.HARDENED_CLAY));
			public final BaseBlockSub C_HAY_B = new BaseBlockSub("compressed_hay_block", new ItemStack(Blocks.HAY_BLOCK));
			public final BaseBlockSub C_END_STONE_B = new BaseBlockSub("compressed_end_stone_block", new ItemStack(Blocks.END_STONE));
			public final BaseBlockSub C_NETHER_BRICK_B = new BaseBlockSub("compressed_nether_brick_block", new ItemStack(Blocks.NETHER_BRICK));
			public final BaseBlockSub C_NETHER_WART_B = new BaseBlockSub("compressed_nether_wart", new ItemStack(Blocks.NETHER_WART_BLOCK));
			public final BaseBlockSub C_SPONGE_B = new BaseBlockSub("compressed_sponge_block", new ItemStack(Blocks.SPONGE));
			public final BaseBlockSub C_PLANKS_B = new BaseBlockSub("compressed_planks_block", new ItemStack(Blocks.PLANKS));
			public final BaseBlockSub C_LOG_B = new BaseBlockSub("compressed_log_block", new ItemStack(Blocks.LOG));
			public final BaseBlockSub C_OBSIDIAN_B = new BaseBlockSub("compressed_obsidian_block", new ItemStack(Blocks.OBSIDIAN));
			public final BaseBlockSub C_LAPIS_B = new BaseBlockSub("compressed_lapis_block", new ItemStack(Blocks.LAPIS_BLOCK));
			public final BaseBlockSub C_BEDROCK_B = new BaseBlockSub("compressed_bedrock_block", new ItemStack(Blocks.BEDROCK)).addChangeBlock(7, new int[]{0, 0, 30}, true, new ItemStack(Blocks.COMMAND_BLOCK));
			public final BaseBlockSub C_COAL_B = new BaseBlockSub("compressed_coal_block", new ItemStack(Blocks.COAL_BLOCK)).addChangeBlock(7, new int[]{0, 0, 10}, true, new ItemStack(C_DIAMOND_B, 1, 6));
			public final BaseBlockSub C_COBBLE_STONE_B = new BaseBlockSub("compressed_cobblestone_block", new ItemStack(Blocks.COBBLESTONE)).addChangeBlock(15, new int[]{0, 20, 0}, true, new ItemStack(C_BEDROCK_B, 1, 6));

			public final BaseBlockSub C_SLIME_BLOCK_B = new CompressedSlimeBlock("compressed_slime_block", new ItemStack(Blocks.SLIME_BLOCK)).setNoToolBreak();
			public final BaseBlockSub C_TNT_B = new CompressedTNT("compressed_tnt_block", new ItemStack(Blocks.TNT)).setNoToolBreak();

			// Update 3.0.0
			public final BaseBlockSub C_stone_granite_B = new BaseBlockSub("compressed_stone_granite", new ItemStack(Blocks.STONE, 1, 1));
			public final BaseBlockSub C_stone_granite_polished_B = new BaseBlockSub("compressed_stone_granite_polished", new ItemStack(Blocks.STONE, 1, 2));
			public final BaseBlockSub C_stone_diorite_B = new BaseBlockSub("compressed_stone_diorite", new ItemStack(Blocks.STONE, 1, 3));
			public final BaseBlockSub C_stone_diorite_polished_B = new BaseBlockSub("compressed_stone_diorite_polished", new ItemStack(Blocks.STONE, 1, 4));
			public final BaseBlockSub C_stone_andesite_B = new BaseBlockSub("compressed_stone_andesite", new ItemStack(Blocks.STONE, 1, 5));
			public final BaseBlockSub C_stone_andesite_polished_B = new BaseBlockSub("compressed_stone_andesite_polished", new ItemStack(Blocks.STONE, 1, 6));
			public final BaseBlockSub C_end_bricks_B = new BaseBlockSub("compressed_end_bricks", new ItemStack(Blocks.END_BRICKS));
			public final BaseBlockSub C_red_nether_brick_B = new BaseBlockSub("compressed_red_nether_brick", new ItemStack(Blocks.RED_NETHER_BRICK));
			public final BaseBlockSub C_sandstone_B = new BaseBlockSub("compressed_sandstone", new ItemStack(Blocks.SANDSTONE));
			public final BaseBlockSub C_sandstone_carved_B = new BaseBlockSub("compressed_sandstone_carved", new ItemStack(Blocks.SANDSTONE, 1, 1));
			public final BaseBlockSub C_sandstone_smooth_B = new BaseBlockSub("compressed_sandstone_smooth", new ItemStack(Blocks.SANDSTONE, 1, 2));
			public final BaseBlockSub C_sea_lantern_B = new BaseBlockSub("compressed_sea_lantern", new ItemStack(Blocks.SEA_LANTERN));
			public final BaseBlockSub C_prismarine_bricks_B = new BaseBlockSub("compressed_prismarine_bricks", new ItemStack(Blocks.PRISMARINE, 1, 1));
			public final BaseBlockSub C_prismarine_dark_B = new BaseBlockSub("compressed_prismarine_dark", new ItemStack(Blocks.PRISMARINE, 1, 2));
			public final BaseBlockSub C_diamond_ore_B = new BaseBlockSub("compressed_diamond_ore", new ItemStack(Blocks.DIAMOND_ORE));
			public final BaseBlockSub C_gold_ore_B = new BaseBlockSub("compressed_gold_ore", new ItemStack(Blocks.GOLD_ORE));
			public final BaseBlockSub C_iron_ore_B = new BaseBlockSub("compressed_iron_ore", new ItemStack(Blocks.IRON_ORE));
			public final BaseBlockSub C_coal_ore_B = new BaseBlockSub("compressed_coal_ore", new ItemStack(Blocks.COAL_ORE));
			public final BaseBlockSub C_lapis_ore_B = new BaseBlockSub("compressed_lapis_ore", new ItemStack(Blocks.LAPIS_ORE));
			public final BaseBlockSub C_redstone_ore_B = new BaseBlockSub("compressed_redstone_ore", new ItemStack(Blocks.REDSTONE_ORE));
			public final BaseBlockSub C_emerald_ore_B = new BaseBlockSub("compressed_emerald_ore", new ItemStack(Blocks.EMERALD_ORE));
			public final BaseBlockSub C_quartz_ore_B = new BaseBlockSub("compressed_quartz_ore", new ItemStack(Blocks.QUARTZ_ORE));

			// Update 3.0.1
			public final BaseBlockSub C_chest_B = new CompressedChest("compressed_chest", new ItemStack(Blocks.CHEST), 54);
			public final BaseBlockSub C_shulker_box_B = new CompressedChest("compressed_shulker_box", new ItemStack(Blocks.PURPLE_SHULKER_BOX), 32);
			
		}

		public class Has {
			public final BaseBlockSub C_NETHER_STAR_B = new BaseBlockSub("compressed_nether_star", new ItemStack(Items.NETHER_STAR));
			public final BaseBlockSub C_CHAR_COAL_B = new BaseBlockSub("compressed_charcoal", new ItemStack(Items.COAL, 1, 1));
			public final BaseBlockSub C_BLAZE_B = new BaseBlockSub("compressed_blaze", new ItemStack(Items.BLAZE_ROD));
			public final BaseBlockSub C_CHORUS_FRUIT_B = new BaseBlockSub("compressed_chorus_fruit", new ItemStack(Items.CHORUS_FRUIT));
			public final BaseBlockSub C_ENDER_PEARL_B = new BaseBlockSub("compressed_ender_pearl", new ItemStack(Items.ENDER_PEARL));
			public final BaseBlockSub C_FLINT_B = new BaseBlockSub("compressed_flint", new ItemStack(Items.FLINT));
			public final BaseBlockSub C_GHAST_TEAR_B = new BaseBlockSub("compressed_ghast_tear", new ItemStack(Items.GHAST_TEAR));
		}
	}

	public static class OreStuff {
		private final boolean TF = Configs.Custom.Mod_Stuff.ThermalFoundation;
		private final boolean IC = Configs.Custom.Mod_Stuff.IndustrialCraft;
		@SuppressWarnings("unused")
		private final boolean AE = Configs.Custom.Mod_Stuff.AppliedEnergistics2;

		public final OreBlock block = new OreBlock();
		public final Ore ore = new Ore();

		public class OreBlock {
			public final BaseBlockSub C_aluminum_B = TF ? register("aluminum", TFBlocks.blockStorage.blockAluminum, "thermalfoundation", false) : null;
			public final BaseBlockSub C_copper_B = TF ? register("copper", TFBlocks.blockStorage.blockCopper, "thermalfoundation", false) : IC ? register("copper", "resource", 6, "ic2", false) : null;
			public final BaseBlockSub C_iridium_B = TF ? register("iridium", TFBlocks.blockStorage.blockIridium, "thermalfoundation", false) : null;
			public final BaseBlockSub C_lead_B = TF ? register("lead", TFBlocks.blockStorage.blockLead, "thermalfoundation", false) : IC ? register("lead", "resource", 7, "ic2", false) : null;
			public final BaseBlockSub C_nickel_B = TF ? register("nickel", TFBlocks.blockStorage.blockNickel, "thermalfoundation", false) : null;
			public final BaseBlockSub C_platinum_B = TF ? register("platinum", TFBlocks.blockStorage.blockPlatinum, "thermalfoundation", false) : null;
			public final BaseBlockSub C_silver_B = TF ? register("silver", TFBlocks.blockStorage.blockSilver, "thermalfoundation", false) : IC ? register("silver", "resource", 15, "ic2", false) : null;
			public final BaseBlockSub C_tin_B = TF ? register("tin", TFBlocks.blockStorage.blockTin, "thermalfoundation", false) : IC ? register("tin", "resource", 9, "ic2", false) : null;
			public final BaseBlockSub C_uranium_B = IC ? register("uranium", "resource", 10, "ic2", false) : null;
			// compressed__block
			public final BaseBlockSub C_bronze_B = TF ? register("bronze", TFBlocks.blockStorageAlloy.blockBronze, "thermalfoundation", false) : IC ? register("bronze", "resource", 5, "ic2", false) : null;
			public final BaseBlockSub C_constantan_B = TF ? register("constantan", TFBlocks.blockStorageAlloy.blockConstantan, "thermalfoundation", false) : null;
			public final BaseBlockSub C_electrum_B = TF ? register("electrum", TFBlocks.blockStorageAlloy.blockElectrum, "thermalfoundation", false) : null;
			public final BaseBlockSub C_invar_B = TF ? register("invar", TFBlocks.blockStorageAlloy.blockInvar, "thermalfoundation", false) : null;
			public final BaseBlockSub C_steel_B = TF ? register("steel", TFBlocks.blockStorageAlloy.blockSteel, "thermalfoundation", false) : IC ? register("steel", "resource", 8, "ic2", false) : null;

		}
		public class Ore {
			public final BaseBlockSub C_aluminum_B = TF ? register("aluminum", TFBlocks.blockOre.oreAluminum, "thermalfoundation", true) : null;
			public final BaseBlockSub C_copper_B = TF ? register("copper", TFBlocks.blockOre.oreCopper, "thermalfoundation", true) : IC ? register("copper", "resource", 1, "ic2", true) : null;
			public final BaseBlockSub C_iridium_B = TF ? register("iridium", TFBlocks.blockOre.oreIridium, "thermalfoundation", true) : null;
			public final BaseBlockSub C_lead_B = TF ? register("lead", TFBlocks.blockOre.oreLead, "thermalfoundation", true) : IC ? register("lead", "resource", 2, "ic2", true) : null;
			public final BaseBlockSub C_nickel_B = TF ? register("nickel", TFBlocks.blockOre.oreNickel, "thermalfoundation", true) : null;
			public final BaseBlockSub C_platinum_B = TF ? register("platinum", TFBlocks.blockOre.orePlatinum, "thermalfoundation", true) : null;
			public final BaseBlockSub C_silver_B = TF ? register("silver", TFBlocks.blockOre.oreSilver, "thermalfoundation", true) : null;
			public final BaseBlockSub C_tin_B = TF ? register("tin", TFBlocks.blockOre.oreTin, "thermalfoundation", true) : IC ? register("tin", "resource", 3, "ic2", true) : null;
			public final BaseBlockSub C_uranium_B = IC ? register("uranium", "resource", 4, "ic2", true) : null;
			// compressed__ore_block
		}

		@SuppressWarnings("unused")
		private BaseBlockSub register(String ore, String unCompressed, String ownerModid, boolean isOre) {
			return this.register(ore, unCompressed, 0, ownerModid, isOre);
		}

		private BaseBlockSub register(String ore, String unCompressed, int meta, String ownerModid, boolean isOre) {
			return this.register(ore, new ItemStack(Item.getByNameOrId(ownerModid + ":" + unCompressed), 1, meta), ownerModid, isOre);
		}

		private BaseBlockSub register(String ore, ItemStack unCompressedItem, String ownerModid, boolean isOre) {
			return BaseBlockSub.register("compressed_" + ore + (isOre ? "_ore" : "") + "_block", unCompressedItem, ownerModid);
		}
	}

	public static class ThermalFoundationBlock {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseBlockSub C_ROCKWOOL_B = register("compressed_rockwool_block", TFBlocks.blockRockwool.rockwoolWhite);
			public final BaseBlockSub C_HARDENED_GLASS_B = ((BaseBlockSub) register("compressed_hardened_glass_block", TFBlocks.blockGlass.glassLead).canUseWrenchBreak(true)).setIsTransparentCube();
			public final BaseBlockSub C_FUEL_COKE_B = register("compressed_fuel_coke_block", TFBlocks.blockStorageResource.blockCoke);

			public final BaseBlockSub C_LUMIUM_B = register("compressed_lumium_block", TFBlocks.blockStorageAlloy.blockLumium);
			public final BaseBlockSub C_SIGNALUM_B = register("compressed_signalum_block", TFBlocks.blockStorageAlloy.blockSignalum);
			public final BaseBlockSub C_ENDERIUM_B = register("compressed_enderium_block", TFBlocks.blockStorageAlloy.blockEnderium);
			public final BaseBlockSub C_MITHRIL_B = register("compressed_mithril_block", TFBlocks.blockStorage.blockMithril);

		}

		private BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseBlockSub.register(nameIn, unCompressedItem, "thermalfoundation");
		}
	}

	public static class EnderIOBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public static class Normal {
			public Normal() {

			}
		}

		public static class Has {
			public Has() {

			}
		}

		@SuppressWarnings("unused")
		private BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseBlockSub.register(nameIn, unCompressedItem, "enderio");
		}
	}

	// Fix 3.0.1
	public static class ProjectEBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public class Normal {
			public final BaseBlockSub C_DARK_MATTER_B = register("compressed_dark_matter_block", new ItemStack(Item.getByNameOrId("projecte:matter_block"), 1, 0)).setModelState("normal");
			public final BaseBlockSub C_RED_MATTER_B = register("compressed_red_matter_block", new ItemStack(Item.getByNameOrId("projecte:matter_block"), 1, 1)).setModelState("normal");
			public final BaseBlockSub C_ALCHEMICAL_COAL_B = register("compressed_alchemical_coal_block", new ItemStack(Item.getByNameOrId("projecte:pe_fuel_block"), 1, 0)).setModelState("normal");
			public final BaseBlockSub C_MOBIUS_FUEL_B = register("compressed_mobius_fuel_block", new ItemStack(Item.getByNameOrId("projecte:pe_fuel_block"), 1, 1)).setModelState("normal");
			public final BaseBlockSub C_AETERNALIS_FULE_B = register("compressed_aeternalis_fuel_block", new ItemStack(Item.getByNameOrId("projecte:pe_fuel_block"), 1, 2)).setModelState("normal");

		}

		public class Has {
			public final BaseBlockSub C_LOW_COVALENCE_B = register("compressed_low_covalence_dust_block", new ItemStack(ObjHandler.covalence, 1, 0));
			public final BaseBlockSub C_MEDIUM_COVALENCE_B = register("compressed_medium_covalence_dust_block", new ItemStack(ObjHandler.covalence, 1, 1));
			public final BaseBlockSub C_HIGH_COVALENCE_B = register("compressed_high_covalence_dust_block", new ItemStack(ObjHandler.covalence, 1, 2));

		}

		private BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
			if(!JiuUtils.item.equalsStack(unCompressedItem, new ItemStack(Items.AIR), false)) {
				return BaseBlockSub.register(nameIn, unCompressedItem, "projecte");
			}else {
				return null;
			}
		}
	}

	public static class DraconicEvolutionBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public class Normal {
			public final BaseBlockSub C_DRACONIUM_BLOCK_B;
			public final BaseBlockSub C_DRACONIC_BLOCK_B;
			public final BaseBlockSub C_INFUSED_OBSIDIAN_BLOCK_B;
			public final BaseBlockSub C_CREATIVE_RF_SOURCE_B;

			public Normal() {
				C_DRACONIUM_BLOCK_B = register("compressed_draconium_block", new ItemStack(DEFeatures.draconiumBlock, 1, 0));
				C_DRACONIC_BLOCK_B = register("compressed_draconic_block", new ItemStack(DEFeatures.draconicBlock));
				C_INFUSED_OBSIDIAN_BLOCK_B = register("compressed_infused_obsidian_block", new ItemStack(DEFeatures.infusedObsidian));
				C_CREATIVE_RF_SOURCE_B = new CompressedCreativeRFSource("compressed_creative_rf_source", new ItemStack(DEFeatures.creativeRFSource));

			}
		}

		public static class Has {
			public Has() {

			}
		}

		private BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseBlockSub.register(nameIn, unCompressedItem, "draconicevolution");
		}
	}

	// Fix 3.0.1
	public static class EnvironmentalTechBlock {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseBlockSub C_AETHIUM_BLOCK_B = register("compressed_aethium_block", "environmentaltech:aethium").setModelState("normal");
			public final BaseBlockSub C_MICA_BLOCK_B = register("compressed_mica_block", "environmentaltech:mica").setModelState("normal");
			public final BaseBlockSub C_LITHERITE_BLOCK_B = register("compressed_litherite_block", "environmentaltech:litherite").setModelState("normal");
			public final BaseBlockSub C_ERODIUM_BLOCK_B = register("compressed_erodium_block", "environmentaltech:erodium").setModelState("normal");
			public final BaseBlockSub C_KYRONITE_BLOCK_B = register("compressed_kyronite_block", "environmentaltech:kyronite").setModelState("normal");
			public final BaseBlockSub C_PLADIUM_BLOCK_B = register("compressed_pladium_block", "environmentaltech:pladium").setModelState("normal");
			public final BaseBlockSub C_IONITE_BLOCK_B = register("compressed_ionite_block", "environmentaltech:ionite").setModelState("normal");
		}

		private BaseBlockSub register(String nameIn, String unCompressedItem) {
			return register(nameIn, unCompressedItem, 0);
		}

		private BaseBlockSub register(String nameIn, String unCompressedItem, int meta) {
			return register(nameIn, new ItemStack(Block.getBlockFromName(unCompressedItem), 1, meta));
		}

		private static BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseBlockSub.register(nameIn, unCompressedItem, "environmentaltech");
		}
	}

	public static class AvaritiaBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();
		protected static final morph.avaritia.init.ModBlocks blocks = new morph.avaritia.init.ModBlocks();
		protected static final morph.avaritia.init.ModItems items = new morph.avaritia.init.ModItems();

		public class Normal {
			public final BaseBlockSub C_NEUTRONIUM_BLOCK_B = register("compressed_neutronium_block", new ItemStack(blocks.resource)).setInfoStack(new ItemStack(items.resource, 1, 4));
			public final BaseBlockSub C_INFINITY_BLOCK_B = register("compressed_infinity_block", new ItemStack(blocks.resource, 1, 1)).setInfoStack(new ItemStack(items.resource, 1, 6));
			public final BaseBlockSub C_CRYSTAL_MATRIX_BLOCK_B = register("compressed_crystal_matrix_block", new ItemStack(blocks.resource, 1, 2)).setInfoStack(new ItemStack(items.resource, 1, 1));
		}

		public class Has {
			public final BaseBlockSub C_INFINITY_CATALYST_B = register("compressed_infinity_catalyst", items.infinity_catalyst).setIsTransparentCube().setInfoStack(items.infinity_catalyst);

		}

		private BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseBlockSub.register(nameIn, unCompressedItem, "avaritia");
		}
	}

	// Fix in 3.0.1
	public static class TconstructBlock {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseBlockSub C_SOIL_B = register("compressed_soil_block", new ItemStack(TinkerCommons.blockSoil)).setModelState("normal");
			public final BaseBlockSub C_SEARED_B = register("compressed_seared_block", new ItemStack(TinkerSmeltery.searedBlock, 1, 3)).setModelState("normal");

			public final BaseBlockSub C_Cobalt_B = register("compressed_cobalt_block", new ItemStack(TinkerCommons.blockMetal, 1, 0)).setModelState("normal");
			public final BaseBlockSub C_Ardite_B = register("compressed_ardite_block", new ItemStack(TinkerCommons.blockMetal, 1, 1)).setModelState("normal");
			public final BaseBlockSub C_Manyullyn_B = register("compressed_manyullyn_block", new ItemStack(TinkerCommons.blockMetal, 1, 2)).setModelState("normal");
			public final BaseBlockSub C_Knightslime_B = register("compressed_knightslime_block", new ItemStack(TinkerCommons.blockMetal, 1, 3)).setModelState("normal");
			public final BaseBlockSub C_Pigiron_B = register("compressed_pigiron_block", new ItemStack(TinkerCommons.blockMetal, 1, 4)).setModelState("normal");
			public final BaseBlockSub C_Alubrass_B = register("compressed_alubrass_block", new ItemStack(TinkerCommons.blockMetal, 1, 5)).setModelState("normal");
			public final BaseBlockSub C_pearl_B = register("compressed_silky_jewel_block", new ItemStack(TinkerCommons.blockMetal, 1, 6)).setModelState("normal");

		}

		private BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseBlockSub.register(nameIn, unCompressedItem, "tconstruct");
		}
	}

	// Fix in 3.0.1
	public static class BotaniaBlock {
		public final Normal normal = new Normal();
		private static vazkii.botania.common.block.ModBlocks blocks = new vazkii.botania.common.block.ModBlocks();

		public class Normal {
			public final BaseBlockSub C_MANA_STEEL_B = register("compressed_mana_stell_block", new ItemStack(blocks.storage, 1, 0)).setModelState("normal");
			public final BaseBlockSub C_TERRASTELL_STEEL_B = register("compressed_terrasteel_stell_block", new ItemStack(blocks.storage, 1, 1)).setModelState("normal");
			public final BaseBlockSub C_ELEMENTIUM_STELL_B = register("compressed_elementium_block", new ItemStack(blocks.storage, 1, 2)).setModelState("normal");
			public final BaseBlockSub C_MANA_DIAMOND_B = register("compressed_mana_diamond_block", new ItemStack(blocks.storage, 1, 3)).setModelState("normal");
			public final BaseBlockSub C_DRAGONSTONE_B = register("compressed_dragonstone_block", new ItemStack(blocks.storage, 1, 4)).setModelState("normal");

			public final BaseBlockSub C_LIVING_WOOD_B = register("compressed_livingwood_block", new ItemStack(blocks.livingwood)).setModelState("normal");
			public final BaseBlockSub C_LIVING_ROCK_B = register("compressed_livingrock_block", new ItemStack(blocks.livingrock)).setModelState("normal");
			public final BaseBlockSub C_DREAMWOOD_B = register("compressed_dreamwood_block", new ItemStack(blocks.dreamwood)).setModelState("normal");
			public final BaseBlockSub C_ELFGLASS_B = register("compressed_elfglass_block", new ItemStack(blocks.elfGlass)).setModelState("normal");
			public final BaseBlockSub C_MANA_GLASS_B = register("compressed_managlass_block", new ItemStack(blocks.manaGlass)).setModelState("normal");
			public final BaseBlockSub C_SHIMMERROCK_B = register("compressed_shimmerrock_block", new ItemStack(blocks.shimmerrock)).setModelState("normal");

			public final BaseBlockSub C_QUARTZ_TYPE_DARK_B = register("compressed_quartz_dark_block", new ItemStack(ModFluffBlocks.darkQuartz)).setModelState("normal");
			public final BaseBlockSub C_QUARTZ_TYPE_MANA_B = register("compressed_quartz_mana_block", new ItemStack(ModFluffBlocks.manaQuartz)).setModelState("normal");
			public final BaseBlockSub C_QUARTZ_TYPE_BLAZE_B = register("compressed_quartz_blaze_block", new ItemStack(ModFluffBlocks.blazeQuartz)).setModelState("normal");
			public final BaseBlockSub C_QUARTZ_TYPE_LAVENDER_B = register("compressed_quartz_lavender_block", new ItemStack(ModFluffBlocks.lavenderQuartz)).setModelState("normal");
			public final BaseBlockSub C_QUARTZ_TYPE_RED_B = register("compressed_quartz_red_block", new ItemStack(ModFluffBlocks.redQuartz)).setModelState("normal");
			public final BaseBlockSub C_QUARTZ_TYPE_EELF_B = register("compressed_quartz_eelf_block", new ItemStack(ModFluffBlocks.elfQuartz)).setModelState("normal");
			public final BaseBlockSub C_QUARTZ_TYPE_SUNNY_B = register("compressed_quartz_sunny_block", new ItemStack(ModFluffBlocks.sunnyQuartz)).setModelState("normal");
		}

		private BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
			return BaseBlockSub.register(nameIn, unCompressedItem, "botania");
		}
	}

	public static class IndustrialCraft {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public class Normal {
			// Update 3.0.0
			public final BaseBlockSub C_REINFORCED_GLASS_B = register("compressed_reinforced_glass", "glass");
			public final BaseBlockSub C_BASALT_B = register("compressed_basalt", "resource");
			public final BaseBlockSub C_REINFORCED_STONE_B = register("compressed_reinforced_stone", "resource", 11);
			public final BaseBlockSub C_BASIC_MACHINE_B = register("compressed_basic_machine", "resource", 12);
			public final BaseBlockSub C_Advanced_machine_B = register("compressed_advanced_machine", "resource", 13);
			public final BaseBlockSub C_reactor_vessel_B = register("compressed_reactor_vessel", "resource", 14);
			public final BaseBlockSub C_REFRACTORY_BRICKS_B = register("compressed_refractory_bricks", "refractory_bricks");
			public final BaseBlockSub C_CONSTRUCTION_FOAM_B = register("compressed_construction_foam", "foam").setNoToolBreak();
			public final BaseBlockSub C_CONSTRUCTION_FOAM_REINFORCED_B = register("compressed_construction_foam_reinforced", "foam", 1).setNoToolBreak();

			public final BaseBlockSub C_rubber_wood_B = register("compressed_rubber_wood", "rubber_wood");
		}

		public class Has {
		}

		private BaseBlockSub register(String nameIn, String unCompressedItem) {
			return register(nameIn, unCompressedItem, 0);
		}

		private BaseBlockSub register(String nameIn, String unCompressedItem, int meta) {
			return BaseBlockSub.register(nameIn, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, meta), "ic2");
		}
	}

	public static class AppliedEnergistics2 {
		private final IDefinitions ae = AEApi.instance().definitions();
		public final Normal normal = new Normal();

		public class Normal {
			// Updata 3.0.1
			public final BaseBlockSub C_certus_quartz_glass_B = register("compressed_certus_quartz_glass", ae.blocks().quartzGlass()).setModelState("normal");
			public final BaseBlockSub C_certus_quartz_vibrant_glass_B = register("compressed_certus_quartz_vibrant_glass", ae.blocks().quartzVibrantGlass()).setModelState("normal");
			public final BaseBlockSub C_fluix_block_B = register("compressed_fluix_block", ae.blocks().fluixBlock()).setModelState("normal");
			public final BaseBlockSub C_certus_quartz_block_B = register("compressed_certus_quartz_block", ae.blocks().quartzBlock()).setModelState("normal");
			public final BaseBlockSub C_sky_stone_block_B = register("compressed_sky_stone_block", ae.blocks().skyStoneBlock()).setModelState("normal");
			public final BaseBlockSub C_smooth_sky_stone_block_B = register("compressed_smooth_sky_stone_block", ae.blocks().smoothSkyStoneBlock()).setModelState("normal");

		}

		private BaseBlockSub register(String name, IItemDefinition item) {
			return this.register(name, item.maybeStack(1).get());
		}

		private BaseBlockSub register(String nameIn, ItemStack stack) {
			return BaseBlockSub.register(nameIn, stack, "appliedenergistics2");
		}
	}

	private static final Map<String, Map<Integer, CustomStuffType.ChangeBlockType>> CHANGE_BLOCK_MAP = Maps.newHashMap();

	public static final Map<String, Map<Integer, CustomStuffType.ChangeBlockType>> CHANGE_MCS_BLOCK_MAP = Maps.newHashMap();
	public static final Map<String, Map<Integer, CustomStuffType.ChangeBlockType>> CHANGE_OTHER_BLOCK_MAP = Maps.newHashMap();

	public static void reinitChangeBlock() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		for(int i = 0; i < CHANGE_BLOCK_MAP.size() + 2; i++) {
			CHANGE_BLOCK_MAP.clear();
			CHANGE_MCS_BLOCK_MAP.clear();
			CHANGE_OTHER_BLOCK_MAP.clear();
		}

		initChangeBlock();
	}

	public static void initChangeBlock() throws JsonIOException, JsonSyntaxException, FileNotFoundException, NumberFormatException {
		File config = new File("./config/jiu/mcs/changed.json");
		if(config.exists()) {
			JsonObject file = new JsonParser().parse(new FileReader(config)).getAsJsonObject();
			for(Map.Entry<String, JsonElement> jobj : file.entrySet()) {

				JsonArray arr = (JsonArray) jobj.getValue();// 主清单
				for(int i = 0; i < arr.size(); ++i) {
					JsonObject obj = (JsonObject) arr.get(i);// 子清单

					String name = obj.get("block").getAsString();
					int[] metas = parseMeta("meta", obj);
					Map<Integer, CustomStuffType.ChangeBlockType> typeMap = Maps.newHashMap();

					InitChangeBlock.init(typeMap, obj, name, metas);
					CHANGE_BLOCK_MAP.put(name, typeMap);
				}
			}
		}

		for(String name : CHANGE_BLOCK_MAP.keySet()) {
			if(MCSResources.SUB_BLOCKS_NAME.contains(name)) {
				CHANGE_MCS_BLOCK_MAP.put(name, CHANGE_BLOCK_MAP.get(name));
			}else {
				CHANGE_OTHER_BLOCK_MAP.put(name, CHANGE_BLOCK_MAP.get(name));
			}
		}
	}

	private static int[] parseMeta(String name, JsonObject obj) {
		int[] metas = {0};
		if(obj.has(name)) {
			JsonArray metasArray = obj.get("meta").getAsJsonArray();
			metas = new int[metasArray.size()];
			for(int meta = 0; meta < metasArray.size(); ++meta) {
				metas[meta] = metasArray.get(meta).getAsInt();
			}
		}
		return metas;
	}
}
