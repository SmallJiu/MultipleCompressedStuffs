package cat.jiu.mcs.util.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import cat.jiu.mcs.blocks.BlockCompressorSlave;
import cat.jiu.mcs.blocks.BlockCreativeEnergy;
import cat.jiu.mcs.blocks.BlockTest;
import cat.jiu.mcs.blocks.compressed.*;
import cat.jiu.mcs.config.Configs;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.timer.Timer;
import cat.jiu.mcs.util.base.sub.BaseCompressedBlock;
import cat.jiu.mcs.util.type.CustomStuffType;
import cat.jiu.mcs.util.type.CustomStuffType.ChangeBlockType;

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

@SuppressWarnings("static-access")
public class MCSBlocks {
	public static final BlockCompressor compressor = new BlockCompressor();
	public static final BlockCompressorSlave compressor_slave = new BlockCompressorSlave();
	public static final BlockCreativeEnergy creative_energy = new BlockCreativeEnergy();
	public static final BaseCompressedBlock C_creative_energy_B = new CompressedCreativeEnergy("compressed_creative_energy", new ItemStack(creative_energy));
	
	public static cat.jiu.core.util.base.BaseBlock.Normal TEST_BLOCK = null;
	static {
		if(MCS.dev()) {
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
	public static Torcherino torcherino = null;

	public MCSBlocks() {
		thermal_foundation = Configs.Custom.Mod_Stuff.ThermalFoundation ? new ThermalFoundationBlock() : null;
		draconic_evolution = Configs.Custom.Mod_Stuff.DraconicEvolution ? new DraconicEvolutionBlock() : null;
		avaritia = Configs.Custom.Mod_Stuff.Avaritia ? new AvaritiaBlock() : null;
		ic2 = Configs.Custom.Mod_Stuff.IndustrialCraft ? new IndustrialCraft() : null;
		ae2 = Configs.Custom.Mod_Stuff.AppliedEnergistics2 ? new AppliedEnergistics2() : null;
		
		torcherino = Configs.Custom.Mod_Stuff.Torcherino ? new Torcherino() : null;
		enderio = Configs.Custom.Mod_Stuff.EnderIO ? new EnderIOBlock() : null;
		projecte = Configs.Custom.Mod_Stuff.ProjectE ? new ProjectEBlock() : null;
		environmental_tech = Configs.Custom.Mod_Stuff.EnvironmentalTech ? new EnvironmentalTechBlock() : null;
		tconstruct = Configs.Custom.Mod_Stuff.Tconstruct ? new TconstructBlock() : null;
		botania = Configs.Custom.Mod_Stuff.Botania ? new BotaniaBlock() : null;
	}

	public static void registerOreDict() {
		for(Block sblock : MCSResources.BLOCKS) {
			if(sblock instanceof BaseCompressedBlock) {
				BaseCompressedBlock block = (BaseCompressedBlock) sblock;
				if(block.createOreDictionary()) {
					List<String> ores = block.getOtherOreDictionary();
					if(!ores.isEmpty()) {
						for(int i = 0; i < 16; i++) {
							String ore = ores.get(i);
							OreDictionary.registerOre((i + 1) + "x" + ore, new ItemStack(block, 1, i));
						}
					}
					JiuUtils.item.registerCompressedOre(block.getUnCompressedName(), block, block.isHas());
				}
			}
		}
	}

	public static class MinecraftBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public class Normal {
			public final BaseCompressedBlock C_BONE_B = new BaseCompressedBlock("compressed_bone_block", new ItemStack(Blocks.BONE_BLOCK)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_DIAMOND_B = new BaseCompressedBlock("compressed_diamond_block", new ItemStack(Blocks.DIAMOND_BLOCK)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_DIRT_B = new BaseCompressedBlock("compressed_dirt_block", new ItemStack(Blocks.DIRT));
			public final BaseCompressedBlock C_EMERALD_B = new BaseCompressedBlock("compressed_emerald_block", new ItemStack(Blocks.EMERALD_BLOCK)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_GLASS_B = new BaseCompressedBlock("compressed_glass_block", new ItemStack(Blocks.GLASS));
			public final BaseCompressedBlock C_GLOW_STONE_B = new BaseCompressedBlock("compressed_glowstone_block", new ItemStack(Blocks.GLOWSTONE)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_GOLD_B = new BaseCompressedBlock("compressed_gold_block", new ItemStack(Blocks.GOLD_BLOCK)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_GRAVEL_B = new BaseCompressedBlock("compressed_gravel_block", new ItemStack(Blocks.GRAVEL));
			public final BaseCompressedBlock C_ICE_B = new BaseCompressedBlock("compressed_ice_block", new ItemStack(Blocks.ICE)).setIsTransparentCube();
			public final BaseCompressedBlock C_IRON_B = new BaseCompressedBlock("compressed_iron_block", new ItemStack(Blocks.IRON_BLOCK)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_MELON_B = new BaseCompressedBlock("compressed_melon_block", new ItemStack(Blocks.MELON_BLOCK)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_MAGMA_B = new BaseCompressedBlock("compressed_magma_block", new ItemStack(Blocks.MAGMA));
			public final BaseCompressedBlock C_NETHERRACK_B = new BaseCompressedBlock("compressed_netherrack_block", new ItemStack(Blocks.NETHERRACK));
			public final BaseCompressedBlock C_PUMPKIN_B = new BaseCompressedBlock("compressed_pumpkin_block", new ItemStack(Blocks.PUMPKIN));
			public final BaseCompressedBlock C_RED_STONE_B = new BaseCompressedBlock("compressed_redstone_block", new ItemStack(Blocks.REDSTONE_BLOCK)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_SAND_B = new BaseCompressedBlock("compressed_sand_block", new ItemStack(Blocks.SAND));
			public final BaseCompressedBlock C_SNOW_B = new BaseCompressedBlock("compressed_snow_block", new ItemStack(Blocks.SNOW));
			public final BaseCompressedBlock C_STONE_B = new BaseCompressedBlock("compressed_stone_block", new ItemStack(Blocks.STONE));
			public final BaseCompressedBlock C_WOOL_B = new BaseCompressedBlock("compressed_wool_block", new ItemStack(Blocks.WOOL));
			public final BaseCompressedBlock C_QUARTZ_BLOCK_B = new BaseCompressedBlock("compressed_quartz_block", new ItemStack(Blocks.QUARTZ_BLOCK));
			public final BaseCompressedBlock C_RED_MUSHROOM_B = new BaseCompressedBlock("compressed_red_mushroom_block", new ItemStack(Blocks.RED_MUSHROOM)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_BROWN_MUSHROOM_B = new BaseCompressedBlock("compressed_brown_mushroom_block", new ItemStack(Blocks.BROWN_MUSHROOM)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_PRISMARINE_B = new BaseCompressedBlock("compressed_prismarine_block", new ItemStack(Blocks.PRISMARINE)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_BRICK_BLOCK_B = new BaseCompressedBlock("compressed_brick_block", new ItemStack(Blocks.BRICK_BLOCK));
			public final BaseCompressedBlock C_SOUL_SAND_B = new BaseCompressedBlock("compressed_soul_sand_block", new ItemStack(Blocks.SOUL_SAND));
			public final BaseCompressedBlock C_STONE_BRICK_B = new BaseCompressedBlock("compressed_stone_brick_block", new ItemStack(Blocks.STONEBRICK));
			public final BaseCompressedBlock C_CLAY_B = new BaseCompressedBlock("compressed_clay_block", new ItemStack(Blocks.CLAY));
			public final BaseCompressedBlock C_PURPUR_BLOCK_B = new BaseCompressedBlock("compressed_purpur_block", new ItemStack(Blocks.PURPUR_BLOCK));
			public final BaseCompressedBlock C_CONCRETE_POWDER_B = new BaseCompressedBlock("compressed_concrete_powder_block", new ItemStack(Blocks.CONCRETE_POWDER)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_CONCRETE_B = new BaseCompressedBlock("compressed_concrete_block", new ItemStack(Blocks.CONCRETE));
			public final BaseCompressedBlock C_HARDENED_CLAY_B = new BaseCompressedBlock("compressed_hardened_clay_block", new ItemStack(Blocks.HARDENED_CLAY));
			public final BaseCompressedBlock C_HAY_B = new BaseCompressedBlock("compressed_hay_block", new ItemStack(Blocks.HAY_BLOCK));
			public final BaseCompressedBlock C_END_STONE_B = new BaseCompressedBlock("compressed_end_stone_block", new ItemStack(Blocks.END_STONE));
			public final BaseCompressedBlock C_NETHER_BRICK_B = new BaseCompressedBlock("compressed_nether_brick_block", new ItemStack(Blocks.NETHER_BRICK));
			public final BaseCompressedBlock C_NETHER_WART_B = new BaseCompressedBlock("compressed_nether_wart", new ItemStack(Blocks.NETHER_WART_BLOCK));
			public final BaseCompressedBlock C_SPONGE_B = new BaseCompressedBlock("compressed_sponge_block", new ItemStack(Blocks.SPONGE));
			public final BaseCompressedBlock C_PLANKS_B = new BaseCompressedBlock("compressed_planks_block", new ItemStack(Blocks.PLANKS));
			public final BaseCompressedBlock C_LOG_B = new BaseCompressedBlock("compressed_log_block", new ItemStack(Blocks.LOG));
			public final BaseCompressedBlock C_OBSIDIAN_B = new BaseCompressedBlock("compressed_obsidian_block", new ItemStack(Blocks.OBSIDIAN));
			public final BaseCompressedBlock C_LAPIS_B = new BaseCompressedBlock("compressed_lapis_block", new ItemStack(Blocks.LAPIS_BLOCK));
			public final BaseCompressedBlock C_BEDROCK_B = new BaseCompressedBlock("compressed_bedrock_block", new ItemStack(Blocks.BEDROCK)).addChangeBlock(7, new Timer(0, 0, 30), true, new ItemStack(Blocks.COMMAND_BLOCK));
			public final BaseCompressedBlock C_COAL_B = new BaseCompressedBlock("compressed_coal_block", new ItemStack(Blocks.COAL_BLOCK)).addChangeBlock(7, new Timer(0, 0, 10), true, new ItemStack(C_DIAMOND_B, 1, 6));
			public final BaseCompressedBlock C_COBBLE_STONE_B = new BaseCompressedBlock("compressed_cobblestone_block", new ItemStack(Blocks.COBBLESTONE)).addChangeBlock(15, new Timer(0, 20, 0), true, new ItemStack(C_BEDROCK_B, 1, 6));

			public final BaseCompressedBlock C_SLIME_BLOCK_B = new CompressedSlimeBlock("compressed_slime_block", new ItemStack(Blocks.SLIME_BLOCK)).setNotNeedToolBreak().setBaseRecipeIsItem();
			public final BaseCompressedBlock C_TNT_B = new CompressedTNT("compressed_tnt_block", new ItemStack(Blocks.TNT)).setNotNeedToolBreak();

			// Update 3.0.0
			public final BaseCompressedBlock C_stone_granite_B = new BaseCompressedBlock("compressed_stone_granite", new ItemStack(Blocks.STONE, 1, 1));
			public final BaseCompressedBlock C_stone_granite_polished_B = new BaseCompressedBlock("compressed_stone_granite_polished", new ItemStack(Blocks.STONE, 1, 2));
			public final BaseCompressedBlock C_stone_diorite_B = new BaseCompressedBlock("compressed_stone_diorite", new ItemStack(Blocks.STONE, 1, 3));
			public final BaseCompressedBlock C_stone_diorite_polished_B = new BaseCompressedBlock("compressed_stone_diorite_polished", new ItemStack(Blocks.STONE, 1, 4));
			public final BaseCompressedBlock C_stone_andesite_B = new BaseCompressedBlock("compressed_stone_andesite", new ItemStack(Blocks.STONE, 1, 5));
			public final BaseCompressedBlock C_stone_andesite_polished_B = new BaseCompressedBlock("compressed_stone_andesite_polished", new ItemStack(Blocks.STONE, 1, 6));
			public final BaseCompressedBlock C_end_bricks_B = new BaseCompressedBlock("compressed_end_bricks", new ItemStack(Blocks.END_BRICKS));
			public final BaseCompressedBlock C_red_nether_brick_B = new BaseCompressedBlock("compressed_red_nether_brick", new ItemStack(Blocks.RED_NETHER_BRICK));
			public final BaseCompressedBlock C_sandstone_B = new BaseCompressedBlock("compressed_sandstone", new ItemStack(Blocks.SANDSTONE));
			public final BaseCompressedBlock C_sandstone_carved_B = new BaseCompressedBlock("compressed_sandstone_carved", new ItemStack(Blocks.SANDSTONE, 1, 1));
			public final BaseCompressedBlock C_sandstone_smooth_B = new BaseCompressedBlock("compressed_sandstone_smooth", new ItemStack(Blocks.SANDSTONE, 1, 2));
			public final BaseCompressedBlock C_sea_lantern_B = new BaseCompressedBlock("compressed_sea_lantern", new ItemStack(Blocks.SEA_LANTERN));
			public final BaseCompressedBlock C_prismarine_bricks_B = new BaseCompressedBlock("compressed_prismarine_bricks", new ItemStack(Blocks.PRISMARINE, 1, 1));
			public final BaseCompressedBlock C_prismarine_dark_B = new BaseCompressedBlock("compressed_prismarine_dark", new ItemStack(Blocks.PRISMARINE, 1, 2));
			public final BaseCompressedBlock C_diamond_ore_B = new BaseCompressedBlock("compressed_diamond_ore", new ItemStack(Blocks.DIAMOND_ORE));
			public final BaseCompressedBlock C_gold_ore_B = new BaseCompressedBlock("compressed_gold_ore", new ItemStack(Blocks.GOLD_ORE));
			public final BaseCompressedBlock C_iron_ore_B = new BaseCompressedBlock("compressed_iron_ore", new ItemStack(Blocks.IRON_ORE));
			public final BaseCompressedBlock C_coal_ore_B = new BaseCompressedBlock("compressed_coal_ore", new ItemStack(Blocks.COAL_ORE));
			public final BaseCompressedBlock C_lapis_ore_B = new BaseCompressedBlock("compressed_lapis_ore", new ItemStack(Blocks.LAPIS_ORE));
			public final BaseCompressedBlock C_redstone_ore_B = new BaseCompressedBlock("compressed_redstone_ore", new ItemStack(Blocks.REDSTONE_ORE));
			public final BaseCompressedBlock C_emerald_ore_B = new BaseCompressedBlock("compressed_emerald_ore", new ItemStack(Blocks.EMERALD_ORE));
			public final BaseCompressedBlock C_quartz_ore_B = new BaseCompressedBlock("compressed_quartz_ore", new ItemStack(Blocks.QUARTZ_ORE));

			// Update 3.0.1
			public final BaseCompressedBlock C_chest_B = new CompressedChest("compressed_chest", new ItemStack(Blocks.CHEST), 54);
			public final BaseCompressedBlock C_shulker_box_B = new CompressedChest("compressed_shulker_box", new ItemStack(Blocks.PURPLE_SHULKER_BOX), 32);
			
			// updata 3.0.3
			public final BaseCompressedBlock C_grass_block_B = new BaseCompressedBlock("compressed_grass_block", new ItemStack(Blocks.GRASS));
			public final BaseCompressedBlock C_coarse_dirt_B = new BaseCompressedBlock("compressed_coarse_dirt", new ItemStack(Blocks.DIRT, 1, 1));
			public final BaseCompressedBlock C_podzol_B = new BaseCompressedBlock("compressed_podzol", new ItemStack(Blocks.DIRT, 1, 2));
			public final BaseCompressedBlock C_wet_sponge_B = new BaseCompressedBlock("compressed_wet_sponge", new ItemStack(Blocks.SPONGE, 1, 1));
			public final BaseCompressedBlock C_red_sandstone_B = new BaseCompressedBlock("compressed_red_sandstone", new ItemStack(Blocks.RED_SANDSTONE));
			public final BaseCompressedBlock C_red_sandstone_carved_B = new BaseCompressedBlock("compressed_red_sandstone_carved", new ItemStack(Blocks.RED_SANDSTONE, 1, 1));
			public final BaseCompressedBlock C_red_sandstone_smooth_B = new BaseCompressedBlock("compressed_red_sandstone_smooth", new ItemStack(Blocks.RED_SANDSTONE, 1, 2));
			
		}

		public class Has {
			public final BaseCompressedBlock C_NETHER_STAR_B = new BaseCompressedBlock("compressed_nether_star", new ItemStack(Items.NETHER_STAR));
			public final BaseCompressedBlock C_CHAR_COAL_B = new BaseCompressedBlock("compressed_charcoal", new ItemStack(Items.COAL, 1, 1));
			public final BaseCompressedBlock C_BLAZE_B = new BaseCompressedBlock("compressed_blaze", new ItemStack(Items.BLAZE_ROD));
			public final BaseCompressedBlock C_CHORUS_FRUIT_B = new BaseCompressedBlock("compressed_chorus_fruit", new ItemStack(Items.CHORUS_FRUIT));
			public final BaseCompressedBlock C_ENDER_PEARL_B = new BaseCompressedBlock("compressed_ender_pearl", new ItemStack(Items.ENDER_PEARL));
			public final BaseCompressedBlock C_FLINT_B = new BaseCompressedBlock("compressed_flint", new ItemStack(Items.FLINT));
			public final BaseCompressedBlock C_GHAST_TEAR_B = new BaseCompressedBlock("compressed_ghast_tear", new ItemStack(Items.GHAST_TEAR));
		}
	}

	public static class OreStuff {
		public final boolean TF = Configs.Custom.Mod_Stuff.ThermalFoundation;
		public final boolean IC = Configs.Custom.Mod_Stuff.IndustrialCraft;
		public final boolean AE = Configs.Custom.Mod_Stuff.AppliedEnergistics2;

		public final OreBlock block = new OreBlock();
		public final Ore ore = new Ore();

		public class OreBlock {
			public final BaseCompressedBlock C_aluminum_B = TF ? register("aluminum", TFBlocks.blockStorage.blockAluminum, "thermalfoundation", false) : null;
			public final BaseCompressedBlock C_copper_B = TF ? register("copper", TFBlocks.blockStorage.blockCopper, "thermalfoundation", false) : IC ? register("copper", "resource", 6, "ic2", false) : null;
			public final BaseCompressedBlock C_iridium_B = TF ? register("iridium", TFBlocks.blockStorage.blockIridium, "thermalfoundation", false) : null;
			public final BaseCompressedBlock C_lead_B = TF ? register("lead", TFBlocks.blockStorage.blockLead, "thermalfoundation", false) : IC ? register("lead", "resource", 7, "ic2", false) : null;
			public final BaseCompressedBlock C_nickel_B = TF ? register("nickel", TFBlocks.blockStorage.blockNickel, "thermalfoundation", false) : null;
			public final BaseCompressedBlock C_platinum_B = TF ? register("platinum", TFBlocks.blockStorage.blockPlatinum, "thermalfoundation", false) : null;
			public final BaseCompressedBlock C_silver_B = TF ? register("silver", TFBlocks.blockStorage.blockSilver, "thermalfoundation", false) : IC ? register("silver", "resource", 15, "ic2", false) : null;
			public final BaseCompressedBlock C_tin_B = TF ? register("tin", TFBlocks.blockStorage.blockTin, "thermalfoundation", false) : IC ? register("tin", "resource", 9, "ic2", false) : null;
			public final BaseCompressedBlock C_uranium_B = IC ? register("uranium", "resource", 10, "ic2", false) : null;
			// compressed__block
			public final BaseCompressedBlock C_bronze_B = TF ? register("bronze", TFBlocks.blockStorageAlloy.blockBronze, "thermalfoundation", false) : IC ? register("bronze", "resource", 5, "ic2", false) : null;
			public final BaseCompressedBlock C_constantan_B = TF ? register("constantan", TFBlocks.blockStorageAlloy.blockConstantan, "thermalfoundation", false) : null;
			public final BaseCompressedBlock C_electrum_B = TF ? register("electrum", TFBlocks.blockStorageAlloy.blockElectrum, "thermalfoundation", false) : null;
			public final BaseCompressedBlock C_invar_B = TF ? register("invar", TFBlocks.blockStorageAlloy.blockInvar, "thermalfoundation", false) : null;
			public final BaseCompressedBlock C_steel_B = TF ? register("steel", TFBlocks.blockStorageAlloy.blockSteel, "thermalfoundation", false) : IC ? register("steel", "resource", 8, "ic2", false) : null;

		}
		public class Ore {
			public final BaseCompressedBlock C_aluminum_B = TF ? register("aluminum", TFBlocks.blockOre.oreAluminum, "thermalfoundation", true) : null;
			public final BaseCompressedBlock C_copper_B = TF ? register("copper", TFBlocks.blockOre.oreCopper, "thermalfoundation", true) : IC ? register("copper", "resource", 1, "ic2", true) : null;
			public final BaseCompressedBlock C_iridium_B = TF ? register("iridium", TFBlocks.blockOre.oreIridium, "thermalfoundation", true) : null;
			public final BaseCompressedBlock C_lead_B = TF ? register("lead", TFBlocks.blockOre.oreLead, "thermalfoundation", true) : IC ? register("lead", "resource", 2, "ic2", true) : null;
			public final BaseCompressedBlock C_nickel_B = TF ? register("nickel", TFBlocks.blockOre.oreNickel, "thermalfoundation", true) : null;
			public final BaseCompressedBlock C_platinum_B = TF ? register("platinum", TFBlocks.blockOre.orePlatinum, "thermalfoundation", true) : null;
			public final BaseCompressedBlock C_silver_B = TF ? register("silver", TFBlocks.blockOre.oreSilver, "thermalfoundation", true) : null;
			public final BaseCompressedBlock C_tin_B = TF ? register("tin", TFBlocks.blockOre.oreTin, "thermalfoundation", true) : IC ? register("tin", "resource", 3, "ic2", true) : null;
			public final BaseCompressedBlock C_uranium_B = IC ? register("uranium", "resource", 4, "ic2", true) : null;
			// compressed__ore_block
		}

		public BaseCompressedBlock register(String ore, String unCompressed, String ownerModid, boolean isOre) {
			return this.register(ore, unCompressed, 0, ownerModid, isOre);
		}

		public BaseCompressedBlock register(String ore, String unCompressed, int meta, String ownerModid, boolean isOre) {
			return this.register(ore, new ItemStack(Item.getByNameOrId(ownerModid + ":" + unCompressed), 1, meta), ownerModid, isOre);
		}

		public BaseCompressedBlock register(String ore, ItemStack unCompressedItem, String ownerModid, boolean isOre) {
			BaseCompressedBlock block = BaseCompressedBlock.register("compressed_" + ore + (isOre ? "_ore" : "") + "_block", unCompressedItem, ownerModid);
			if(!isOre) block.setBaseRecipeIsItem();
			return block;
		}
	}

	public static class ThermalFoundationBlock {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseCompressedBlock C_ROCKWOOL_B = register("compressed_rockwool_block", TFBlocks.blockRockwool.rockwoolWhite);
			public final BaseCompressedBlock C_HARDENED_GLASS_B = ((BaseCompressedBlock) register("compressed_hardened_glass_block", TFBlocks.blockGlass.glassLead).canUseWrenchBreak(true)).setIsTransparentCube();
			public final BaseCompressedBlock C_FUEL_COKE_B = register("compressed_fuel_coke_block", TFBlocks.blockStorageResource.blockCoke);

			public final BaseCompressedBlock C_LUMIUM_B = register("compressed_lumium_block", TFBlocks.blockStorageAlloy.blockLumium).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_SIGNALUM_B = register("compressed_signalum_block", TFBlocks.blockStorageAlloy.blockSignalum).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_ENDERIUM_B = register("compressed_enderium_block", TFBlocks.blockStorageAlloy.blockEnderium).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_MITHRIL_B = register("compressed_mithril_block", TFBlocks.blockStorage.blockMithril).setBaseRecipeIsItem();

		}

		public BaseCompressedBlock register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedBlock.register(nameIn, unCompressedItem, "thermalfoundation");
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

		public BaseCompressedBlock register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedBlock.register(nameIn, unCompressedItem, "enderio");
		}
	}

	// Fix 3.0.1
	public static class ProjectEBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public class Normal {
			public final BaseCompressedBlock C_DARK_MATTER_B = register("compressed_dark_matter_block", new ItemStack(Item.getByNameOrId("projecte:matter_block"), 1, 0)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_RED_MATTER_B = register("compressed_red_matter_block", new ItemStack(Item.getByNameOrId("projecte:matter_block"), 1, 1)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_ALCHEMICAL_COAL_B = register("compressed_alchemical_coal_block", new ItemStack(Item.getByNameOrId("projecte:pe_fuel_block"), 1, 0)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_MOBIUS_FUEL_B = register("compressed_mobius_fuel_block", new ItemStack(Item.getByNameOrId("projecte:pe_fuel_block"), 1, 1)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_AETERNALIS_FULE_B = register("compressed_aeternalis_fuel_block", new ItemStack(Item.getByNameOrId("projecte:pe_fuel_block"), 1, 2)).setModelState("normal").setBaseRecipeIsItem();

		}

		public class Has {
			public final BaseCompressedBlock C_LOW_COVALENCE_B = register("compressed_low_covalence_dust_block", new ItemStack(ObjHandler.covalence, 1, 0));
			public final BaseCompressedBlock C_MEDIUM_COVALENCE_B = register("compressed_medium_covalence_dust_block", new ItemStack(ObjHandler.covalence, 1, 1));
			public final BaseCompressedBlock C_HIGH_COVALENCE_B = register("compressed_high_covalence_dust_block", new ItemStack(ObjHandler.covalence, 1, 2));

		}

		public BaseCompressedBlock register(String nameIn, ItemStack unCompressedItem) {
			if(!JiuUtils.item.equalsStack(unCompressedItem, new ItemStack(Items.AIR), false)) {
				return BaseCompressedBlock.register(nameIn, unCompressedItem, "projecte");
			}else {
				return null;
			}
		}
	}

	public static class DraconicEvolutionBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public class Normal {
			public final BaseCompressedBlock C_DRACONIUM_BLOCK_B = register("compressed_draconium_block", new ItemStack(DEFeatures.draconiumBlock, 1, 0)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_DRACONIC_BLOCK_B = register("compressed_draconic_block", new ItemStack(DEFeatures.draconicBlock)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_INFUSED_OBSIDIAN_BLOCK_B = register("compressed_infused_obsidian_block", new ItemStack(DEFeatures.infusedObsidian));
			public final BaseCompressedBlock C_CREATIVE_RF_SOURCE_B = new CompressedCreativeRFSource("compressed_creative_rf_source", new ItemStack(DEFeatures.creativeRFSource));
		}

		public static class Has {
			public Has() {

			}
		}

		public BaseCompressedBlock register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedBlock.register(nameIn, unCompressedItem, "draconicevolution");
		}
	}

	// Fix 3.0.1
	public static class EnvironmentalTechBlock {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseCompressedBlock C_AETHIUM_BLOCK_B = register("compressed_aethium_block", "environmentaltech:aethium").setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_MICA_BLOCK_B = register("compressed_mica_block", "environmentaltech:mica").setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_LITHERITE_BLOCK_B = register("compressed_litherite_block", "environmentaltech:litherite").setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_ERODIUM_BLOCK_B = register("compressed_erodium_block", "environmentaltech:erodium").setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_KYRONITE_BLOCK_B = register("compressed_kyronite_block", "environmentaltech:kyronite").setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_PLADIUM_BLOCK_B = register("compressed_pladium_block", "environmentaltech:pladium").setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_IONITE_BLOCK_B = register("compressed_ionite_block", "environmentaltech:ionite").setModelState("normal").setBaseRecipeIsItem();
		}

		public BaseCompressedBlock register(String nameIn, String unCompressedItem) {
			return register(nameIn, unCompressedItem, 0);
		}

		public BaseCompressedBlock register(String nameIn, String unCompressedItem, int meta) {
			return register(nameIn, new ItemStack(Block.getBlockFromName(unCompressedItem), 1, meta));
		}

		private static BaseCompressedBlock register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedBlock.register(nameIn, unCompressedItem, "environmentaltech");
		}
	}

	public static class AvaritiaBlock {
		public final Normal normal = new Normal();
		public final Has has = new Has();
		protected static final morph.avaritia.init.ModBlocks blocks = new morph.avaritia.init.ModBlocks();
		protected static final morph.avaritia.init.ModItems items = new morph.avaritia.init.ModItems();

		public class Normal {
			public final BaseCompressedBlock C_NEUTRONIUM_BLOCK_B = register("compressed_neutronium_block", new ItemStack(blocks.resource)).setInfoStack(new ItemStack(items.resource, 1, 4)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_INFINITY_BLOCK_B = register("compressed_infinity_block", new ItemStack(blocks.resource, 1, 1)).setInfoStack(new ItemStack(items.resource, 1, 6)).setBaseRecipeIsItem();
			public final BaseCompressedBlock C_CRYSTAL_MATRIX_BLOCK_B = register("compressed_crystal_matrix_block", new ItemStack(blocks.resource, 1, 2)).setInfoStack(new ItemStack(items.resource, 1, 1)).setBaseRecipeIsItem();
		}

		public class Has {
			public final BaseCompressedBlock C_INFINITY_CATALYST_B = register("compressed_infinity_catalyst", items.infinity_catalyst).setIsTransparentCube().setInfoStack(items.infinity_catalyst);

		}

		public BaseCompressedBlock register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedBlock.register(nameIn, unCompressedItem, "avaritia");
		}
	}

	// Fix in 3.0.1
	public static class TconstructBlock {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseCompressedBlock C_SOIL_B = register("compressed_soil_block", new ItemStack(TinkerCommons.blockSoil)).setModelState("normal");
			public final BaseCompressedBlock C_SEARED_B = register("compressed_seared_block", new ItemStack(TinkerSmeltery.searedBlock, 1, 3)).setModelState("normal");

			public final BaseCompressedBlock C_Cobalt_B = register("compressed_cobalt_block", new ItemStack(TinkerCommons.blockMetal, 1, 0)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_Ardite_B = register("compressed_ardite_block", new ItemStack(TinkerCommons.blockMetal, 1, 1)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_Manyullyn_B = register("compressed_manyullyn_block", new ItemStack(TinkerCommons.blockMetal, 1, 2)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_Knightslime_B = register("compressed_knightslime_block", new ItemStack(TinkerCommons.blockMetal, 1, 3)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_Pigiron_B = register("compressed_pigiron_block", new ItemStack(TinkerCommons.blockMetal, 1, 4)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_Alubrass_B = register("compressed_alubrass_block", new ItemStack(TinkerCommons.blockMetal, 1, 5)).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_pearl_B = register("compressed_silky_jewel_block", new ItemStack(TinkerCommons.blockMetal, 1, 6)).setModelState("normal").setBaseRecipeIsItem();

		}

		public BaseCompressedBlock register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedBlock.register(nameIn, unCompressedItem, "tconstruct");
		}
	}

	// Fix in 3.0.1
	public static class BotaniaBlock {
		public final Normal normal = new Normal();

		public class Normal {
			public final BaseCompressedBlock C_MANA_STEEL_B = register("compressed_mana_stell_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_TERRASTELL_STEEL_B = register("compressed_terrasteel_stell_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_ELEMENTIUM_STELL_B = register("compressed_elementium_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_MANA_DIAMOND_B = register("compressed_mana_diamond_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_DRAGONSTONE_B = register("compressed_dragonstone_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();

			public final BaseCompressedBlock C_LIVING_WOOD_B = register("compressed_livingwood_block", ItemStack.EMPTY).setModelState("normal");
			public final BaseCompressedBlock C_LIVING_ROCK_B = register("compressed_livingrock_block", ItemStack.EMPTY).setModelState("normal");
			public final BaseCompressedBlock C_DREAMWOOD_B = register("compressed_dreamwood_block", ItemStack.EMPTY).setModelState("normal");
			
			public final BaseCompressedBlock C_ELFGLASS_B = register("compressed_elfglass_block", ItemStack.EMPTY).setModelState("normal");
			public final BaseCompressedBlock C_MANA_GLASS_B = register("compressed_managlass_block", ItemStack.EMPTY).setModelState("normal");
			public final BaseCompressedBlock C_SHIMMERROCK_B = register("compressed_shimmerrock_block", ItemStack.EMPTY).setModelState("normal");

			public final BaseCompressedBlock C_QUARTZ_TYPE_DARK_B = register("compressed_quartz_dark_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_QUARTZ_TYPE_MANA_B = register("compressed_quartz_mana_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_QUARTZ_TYPE_BLAZE_B = register("compressed_quartz_blaze_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_QUARTZ_TYPE_LAVENDER_B = register("compressed_quartz_lavender_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_QUARTZ_TYPE_RED_B = register("compressed_quartz_red_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_QUARTZ_TYPE_EELF_B = register("compressed_quartz_eelf_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_QUARTZ_TYPE_SUNNY_B = register("compressed_quartz_sunny_block", ItemStack.EMPTY).setModelState("normal").setBaseRecipeIsItem();
		}

		public BaseCompressedBlock register(String nameIn, ItemStack unCompressedItem) {
			return BaseCompressedBlock.register(nameIn, unCompressedItem, "botania");
		}
	}

	public static class IndustrialCraft {
		public final Normal normal = new Normal();
		public final Has has = new Has();

		public class Normal {
			// Update 3.0.0
			public final BaseCompressedBlock C_REINFORCED_GLASS_B = register("compressed_reinforced_glass", "glass");
			public final BaseCompressedBlock C_BASALT_B = register("compressed_basalt", "resource");
			public final BaseCompressedBlock C_REINFORCED_STONE_B = register("compressed_reinforced_stone", "resource", 11);
			public final BaseCompressedBlock C_BASIC_MACHINE_B = register("compressed_basic_machine", "resource", 12);
			public final BaseCompressedBlock C_Advanced_machine_B = register("compressed_advanced_machine", "resource", 13);
			public final BaseCompressedBlock C_reactor_vessel_B = register("compressed_reactor_vessel", "resource", 14);
			public final BaseCompressedBlock C_REFRACTORY_BRICKS_B = register("compressed_refractory_bricks", "refractory_bricks");
			public final BaseCompressedBlock C_CONSTRUCTION_FOAM_B = register("compressed_construction_foam", "foam").setNotNeedToolBreak().setCanThroughBlock();
			public final BaseCompressedBlock C_CONSTRUCTION_FOAM_REINFORCED_B = register("compressed_construction_foam_reinforced", "foam", 1).setNotNeedToolBreak().setCanThroughBlock();

			public final BaseCompressedBlock C_rubber_wood_B = register("compressed_rubber_wood", "rubber_wood");
		}

		public class Has {
		}

		public BaseCompressedBlock register(String nameIn, String unCompressedItem) {
			return register(nameIn, unCompressedItem, 0);
		}

		public BaseCompressedBlock register(String nameIn, String unCompressedItem, int meta) {
			return BaseCompressedBlock.register(nameIn, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, meta), "ic2");
		}
	}

	public static class AppliedEnergistics2 {
		private final IDefinitions ae = AEApi.instance().definitions();
		public final Normal normal = new Normal();

		public class Normal {
			// Updata 3.0.1
			public final BaseCompressedBlock C_certus_quartz_glass_B = register("compressed_certus_quartz_glass", ae.blocks().quartzGlass()).setModelState("normal");
			public final BaseCompressedBlock C_certus_quartz_vibrant_glass_B = register("compressed_certus_quartz_vibrant_glass", ae.blocks().quartzVibrantGlass()).setModelState("normal");
			public final BaseCompressedBlock C_fluix_block_B = register("compressed_fluix_block", ae.blocks().fluixBlock()).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_certus_quartz_block_B = register("compressed_certus_quartz_block", ae.blocks().quartzBlock()).setModelState("normal").setBaseRecipeIsItem();
			public final BaseCompressedBlock C_smooth_sky_stone_block_B = register("compressed_smooth_sky_stone_block", ae.blocks().smoothSkyStoneBlock()).setModelState("normal");
			public final BaseCompressedBlock C_sky_stone_block_B = register("compressed_sky_stone_block", ae.blocks().skyStoneBlock()).setModelState("normal").setSmeltingOutput(C_smooth_sky_stone_block_B);

		}

		public BaseCompressedBlock register(String name, IItemDefinition item) {
			return register(name, item.maybeStack(1).get());
		}

		public BaseCompressedBlock register(String nameIn, ItemStack stack) {
			return BaseCompressedBlock.register(nameIn, stack, "appliedenergistics2");
		}
	}
	
	public static class Torcherino {
		public final Normal normal = new Normal();

		public class Normal {
			// updata 3.0.3
			public final BaseCompressedBlock C_torcherino_B = new CompressedTorcherino("compressed_torcherino", new ItemStack(Block.getBlockFromName("torcherino:blocktorcherino")));
		}
	}
	
	@SuppressWarnings("unused")
	private static ItemStack getStack(String modid, String name, int meta) {
		return new ItemStack(Item.getByNameOrId(modid + ":" + name), 1, meta);
	}

	private static final Map<String, Map<Integer, CustomStuffType.ChangeBlockType>> CHANGE_BLOCK_MAP = Maps.newHashMap();

	public static final Map<String, Map<Integer, CustomStuffType.ChangeBlockType>> CHANGE_MCS_BLOCK_MAP = Maps.newHashMap();
	public static final Map<String, Map<Integer, CustomStuffType.ChangeBlockType>> CHANGE_OTHER_BLOCK_MAP = Maps.newHashMap();

	public static void reinitChangeBlock() throws JsonIOException, JsonSyntaxException, FileNotFoundException {
		CHANGE_BLOCK_MAP.clear();
		CHANGE_MCS_BLOCK_MAP.clear();
		CHANGE_OTHER_BLOCK_MAP.clear();
		initChangeBlock();
	}

	public static void initChangeBlock() throws JsonIOException, JsonSyntaxException, FileNotFoundException, NumberFormatException {
		File config = new File("./config/jiu/mcs/changed.json");
		if(config.exists()) {
			JsonObject file = new JsonParser().parse(new FileReader(config)).getAsJsonObject();
			for(Map.Entry<String, JsonElement> jobj : file.entrySet()) {

				JsonArray arr = jobj.getValue().getAsJsonArray();// 主清单
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

		for(Entry<String, Map<Integer, ChangeBlockType>> entry : CHANGE_BLOCK_MAP.entrySet()) {
			if(MCSResources.BLOCKS_NAME.contains(entry.getKey())) {
				CHANGE_MCS_BLOCK_MAP.put(entry.getKey(), entry.getValue());
			}else {
				CHANGE_OTHER_BLOCK_MAP.put(entry.getKey(), entry.getValue());
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
