package cat.jiu.mcs.recipes;

import java.util.List;

import com.google.common.collect.Lists;

import appeng.api.AEApi;
import appeng.api.features.IGrinderRecipeBuilder;
import appeng.api.features.IGrinderRegistry;
import appeng.api.features.IInscriberRecipe;
import appeng.api.features.IInscriberRecipeBuilder;
import appeng.api.features.IInscriberRegistry;
import appeng.api.features.InscriberProcessType;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.init.MCSResources;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.config.Configs;

import ic2.api.recipe.IRecipeInput;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class MCSRecipe {
	private static final MCSRecipeTool recipe = new MCSRecipeTool(MCS.MODID);

	public static void register() {
		mc();
		item();
		compressed();
		smelting();
	}

	private static void compressed() {
		if(!Configs.use_default_recipes) return;
		long t = System.currentTimeMillis();
		
		for(ICompressedStuff stuff : MCSResources.getStuffs()) {
			if(stuff == null) continue;
			
			ItemStack baseItem = stuff.getUnCompressedStack();
			if(!baseItem.isEmpty() && stuff.canMakeDefaultStackRecipe()) {
				if(Configs.use_3x3_recipes) {
					// 压缩
					recipe.add3x3AllRecipes(stuff.getStack(), baseItem);
					// 解压
					recipe.add1x1Recipes(JiuUtils.item.copyStack(baseItem, 9, false), stuff.getStack());
					for(int meta = 1; meta < 16; meta++) {
						// 压缩
						recipe.add3x3AllRecipes(stuff.getStack(meta), stuff.getStack(meta - 1));
						// 解压
						recipe.add1x1Recipes(stuff.getStack(9, meta - 1), stuff.getStack(meta));
					}
					if(stuff.isItem() && Configs.enable_infinite_recipe) {
						recipe.add3x3AllRecipes(stuff.getStack(23766), stuff.getStack(15));
						recipe.add1x1Recipes(stuff.getStack(9, 15), stuff.getStack(32766));
					}
				}else {
					// 压缩
					recipe.add2x2AllRecipes(stuff.getStack(), baseItem);
					// 解压
					recipe.add1x1Recipes(JiuUtils.item.copyStack(baseItem, 4, false), stuff.getStack());
					for(int meta = 1; meta < 16; meta++) {
						// 压缩
						recipe.add2x2AllRecipes(stuff.getStack(meta), stuff.getStack((meta - 1)));
						// 解压
						recipe.add1x1Recipes(stuff.getStack(4, meta - 1), stuff.getStack(meta));
					}
					if(stuff.isItem() && Configs.enable_infinite_recipe) {
						recipe.add2x2AllRecipes(stuff.getStack(23766), stuff.getStack(15));
						recipe.add1x1Recipes(stuff.getStack(4, 15), stuff.getStack(1, 32766));
					}
				}
			}
		}
		MCS.getLogOS().info("register Compressed recipe success (took {}ms)", System.currentTimeMillis()-t);
	}

	private static void mc() {
		recipe.addShapedRecipes(new ItemStack(Items.NETHER_WART, 9), new ItemStack(Blocks.NETHER_WART_BLOCK));
		recipe.addShapedRecipes(new ItemStack(Items.GOLDEN_APPLE, 1, 1), 
				new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.GOLD_BLOCK),
				new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Blocks.GOLD_BLOCK),
				new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(MCSBlocks.minecraft.normal.C_GOLD_B), new ItemStack(Blocks.GOLD_BLOCK));

		for(int meta = 0; meta < 16; meta++) {
			ItemStack gold_apple = new ItemStack(MCSBlocks.minecraft.normal.C_GOLD_B);
			recipe.addShapedRecipes(new ItemStack(MCSItems.minecraft.food.C_ENCHANTED_GOLD_APPLE_F, 1, meta),
					gold_apple, gold_apple, gold_apple,
					gold_apple, new ItemStack(MCSItems.minecraft.food.C_GOLD_APPLE_F), gold_apple,
					gold_apple, gold_apple, gold_apple);
		}
	}

	private static void smelting() {
		addSmelting(MCSItems.minecraft.food.C_BEEF_F, MCSItems.minecraft.food.C_COOKED_BEEF_F);
		addSmelting(MCSItems.minecraft.food.C_CHICKEN_F, MCSItems.minecraft.food.C_COOKED_CHICKEN_F);
		addSmelting(MCSItems.minecraft.food.C_FISH_F, MCSItems.minecraft.food.C_COOKED_FISH_F);
		addSmelting(MCSItems.minecraft.food.C_MUTTON_F, MCSItems.minecraft.food.C_COOKED_MUTTON_F);
		addSmelting(MCSItems.minecraft.food.C_PORKCHOP_F, MCSItems.minecraft.food.C_COOKED_PORKCHOP_F);
		addSmelting(MCSItems.minecraft.food.C_RABBIT_F, MCSItems.minecraft.food.C_COOKED_RABBIT_F);
		addSmelting(MCSItems.minecraft.food.C_SALMON_FISH_F, MCSItems.minecraft.food.C_COOKED_SALMON_FISH_F);
		addSmelting(MCSItems.minecraft.food.C_POTATO_F, MCSItems.minecraft.food.C_BAKED_POTATO_F);

		if(Configs.Custom.Mod_Stuff.DraconicEvolution) addSmelting(MCSItems.draconic_evolution.normal.C_DRACONIUM_DUST_I, MCSBlocks.draconic_evolution.normal.C_DRACONIUM_BLOCK_B, 1);

		// dust to block
		if(Configs.Custom.Mod_Stuff.ThermalFoundation) {
			addSmelting(MCSItems.thermal_foundation.normal.C_DUST_SIGNALUM_I, MCSBlocks.thermal_foundation.normal.C_SIGNALUM_B, 1);
			addSmelting(MCSItems.thermal_foundation.normal.C_DUST_MITHRIL_I, MCSBlocks.thermal_foundation.normal.C_MITHRIL_B, 1);
			addSmelting(MCSItems.thermal_foundation.normal.C_DUST_LUMIUM_I, MCSBlocks.thermal_foundation.normal.C_LUMIUM_B, 1);
			addSmelting(MCSItems.thermal_foundation.normal.C_DUST_ENDERIUM_I, MCSBlocks.thermal_foundation.normal.C_ENDERIUM_B, 1);
		}
		addSmelting(MCSItems.ore_stuff.dust.C_dust_aluminum_I, MCSBlocks.ore_stuff.block.C_aluminum_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_bronze_I, MCSBlocks.ore_stuff.block.C_bronze_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_constantan_I, MCSBlocks.ore_stuff.block.C_constantan_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_copper_I, MCSBlocks.ore_stuff.block.C_copper_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_electrum_I, MCSBlocks.ore_stuff.block.C_electrum_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_gold_I, MCSBlocks.minecraft.normal.C_GOLD_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_invar_I, MCSBlocks.ore_stuff.block.C_invar_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_iridium_I, MCSBlocks.ore_stuff.block.C_iridium_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_iron_I, MCSBlocks.minecraft.normal.C_IRON_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_lead_I, MCSBlocks.ore_stuff.block.C_lead_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_nickel_I, MCSBlocks.ore_stuff.block.C_nickel_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_platinum_I, MCSBlocks.ore_stuff.block.C_platinum_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_silver_I, MCSBlocks.ore_stuff.block.C_silver_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_steel_I, MCSBlocks.ore_stuff.block.C_steel_B, 1);
		addSmelting(MCSItems.ore_stuff.dust.C_dust_tin_I, MCSBlocks.ore_stuff.block.C_tin_B, 1);
		
		if(Configs.Custom.Mod_Stuff.AppliedEnergistics2) {
			addSmelting(MCSItems.ae.normal.C_certus_dust_I, MCSItems.ae.normal.C_silicon_I);
			addSmelting(MCSItems.ae.normal.C_nether_quartz_dust_I, MCSItems.ae.normal.C_silicon_I);
			addSmelting(MCSBlocks.ae2.normal.C_sky_stone_block_B, MCSBlocks.ae2.normal.C_smooth_sky_stone_block_B);
		}
	}

	/**
	 * 物品a 合成 物品a的meta-x的物品b
	 */
	public static void addSmelting(Item in, Block out, int metaDisparity) {
		if(in != null && out != null) {
			for(ModSubtypes type : ModSubtypes.values()) {
				int imeta = type.getMeta();
				int ometa = imeta - metaDisparity;
				if(!(ometa < 0)) {
					recipe.addSmelting(new ItemStack(in, 1, imeta), new ItemStack(out, 1, ometa), 0);
				}
			}
		}
	}

	private static void addSmelting(Block in, Block out) {
		addSmelting(Item.getItemFromBlock(in), Item.getItemFromBlock(out));
	}

	private static void addSmelting(Item in, Item out) {
		if(in != null && out != null) {
			for(ModSubtypes type : ModSubtypes.values()) {
				int meta = type.getMeta();
				recipe.addSmelting(new ItemStack(in, 1, meta), new ItemStack(out, 1, meta), 0);
			}
			if(!(in instanceof ItemBlock && out instanceof ItemBlock)) {
				recipe.addSmelting(new ItemStack(in, 1, Short.MAX_VALUE - 1), new ItemStack(out, 1, Short.MAX_VALUE - 1), 0);
			}
		}
	}

	private static void item() {
		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.CAT_INGOT),
				new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR),
				new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.minecraft.food.C_FISH_F, 1, 1), new ItemStack(MCSItems.normal.CAT_HAIR),
				new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR));

		if(Configs.Custom.Mod_Stuff.ThermalFoundation) {
			recipe.addShapedRecipes(new ItemStack(MCSBlocks.compressor),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1), new ItemStack(MCSItems.ore_stuff.gear.C_gear_platinum_I), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B));
		}else {
			recipe.addShapedRecipes(new ItemStack(MCSBlocks.compressor),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1), recipe.EMPTY, new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B),
					new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B));
		}

		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.CAT_HAMMER),
				new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(MCSItems.normal.CAT_INGOT),
				new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(Items.MILK_BUCKET), new ItemStack(MCSItems.normal.CAT_INGOT),
				recipe.EMPTY, new ItemStack(Items.MILK_BUCKET), recipe.EMPTY);

		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.DESTROYER),
				new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSBlocks.minecraft.has.C_NETHER_STAR_B, 1, 3), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1),
				new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSItems.normal.CAT_HAMMER), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B),
				new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSBlocks.minecraft.normal.C_BEDROCK_B, 1, 2), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1));
		
		recipe.addShapedRecipes(new ItemStack(MCSBlocks.compressor_slave),
				new ItemStack(Blocks.OBSIDIAN), new ItemStack(Items.ENDER_EYE), new ItemStack(Blocks.OBSIDIAN),
				new ItemStack(Blocks.OBSIDIAN), new ItemStack(Items.ENDER_PEARL), new ItemStack(Blocks.OBSIDIAN),
				new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.OBSIDIAN), new ItemStack(Blocks.OBSIDIAN));

		if(Configs.Custom.Mod_Stuff.IndustrialCraft) {
			MCS.getLogOS().info("Start register ic2 item recipe");
			icCompressedItemCrafting();
		}
		if(Configs.Custom.Mod_Stuff.AppliedEnergistics2) {
			MCS.getLogOS().info("Start register ae2 item recipe");
			aeCompressedItemCrafting();
		}
		if(Configs.Custom.Mod_Stuff.Torcherino) {
			torcherinoCompressedItemCrafting();
		}
	}
	
//	@Optional.Method(modid = "torcherino")
	private static void torcherinoCompressedItemCrafting() {
		MCSBlocks.Torcherino.Normal tor = MCSBlocks.torcherino.normal;
		
		recipe.add1x1Recipes(new ItemStack(Block.getBlockFromName("torcherino:blockcompressedtorcherino")), new ItemStack(tor.C_torcherino_B, 1, 0));
		recipe.add1x1Recipes(new ItemStack(Block.getBlockFromName("torcherino:blockdoublecompressedtorcherino")), new ItemStack(tor.C_torcherino_B, 1, 1));
		
		recipe.add1x1Recipes(new ItemStack(tor.C_torcherino_B, 1, 0), new ItemStack(Block.getBlockFromName("torcherino:blockcompressedtorcherino")));
		recipe.add1x1Recipes(new ItemStack(tor.C_torcherino_B, 1, 1), new ItemStack(Block.getBlockFromName("torcherino:blockdoublecompressedtorcherino")));
	}

	private static void aeCompressedItemCrafting() {
		MCSItems.AppliedEnergistics2.Normal items = MCSItems.ae.normal;
		MCSBlocks.AppliedEnergistics2.Normal blocks = MCSBlocks.ae2.normal;

		IInscriberRegistry inscriberRecipe = AEApi.instance().registries().inscriber();
		IGrinderRegistry grinderRecipe = AEApi.instance().registries().grinder();
		IGrinderRecipeBuilder grinderRecipeBuilder = AEApi.instance().registries().grinder().builder();
		long t = System.currentTimeMillis();
		
		for(int meta = 0; meta < 16; meta++) {
			ItemStack pure_certus_crystal = items.C_pure_certus_crystal_I.getStack(meta);
			ItemStack certus_quartz_crystal = items.C_certus_quartz_crystal_I.getStack(meta);
			ItemStack certus_quartz_crystal_charged = items.C_certus_quartz_crystal_charged_I.getStack(meta);
			ItemStack fluix_crystal = items.C_fluix_crystal_I.getStack(meta);

			ItemStack eng = items.C_eng_processor_I.getStack(meta);
			ItemStack logic = items.C_logic_processor_I.getStack(meta);
			ItemStack calc = items.C_calc_processor_I.getStack(meta);
			
			grinderRecipe.addRecipe(grinderRecipeBuilder.withInput(fluix_crystal).withOutput(items.C_fluix_dust_I.getStack(meta)).build());
			grinderRecipe.addRecipe(grinderRecipeBuilder.withInput(blocks.C_sky_stone_block_B.getStack(meta)).withOutput(items.C_sky_dust_I.getStack(meta)).build());
			grinderRecipe.addRecipe(grinderRecipeBuilder.withInput(MCSBlocks.minecraft.has.C_ENDER_PEARL_B.getStack(meta)).withOutput(items.C_ender_dust_I.getStack(meta)).build());
			grinderRecipe.addRecipe(grinderRecipeBuilder.withInput(certus_quartz_crystal).withOutput(items.C_certus_dust_I.getStack(meta)).build());
			grinderRecipe.addRecipe(grinderRecipeBuilder.withInput(MCSBlocks.minecraft.normal.C_QUARTZ_BLOCK_B.getStack(meta)).withOutput(items.C_nether_quartz_dust_I.getStack(meta)).build());

			// 电路板
			inscriberRecipe.addRecipe(addAEInscriberRecipe(pure_certus_crystal, items.C_calc_processor_print_I.getStack(meta), items.C_calc_processor_press_I.getStack(meta), ItemStack.EMPTY, InscriberProcessType.INSCRIBE));
			inscriberRecipe.addRecipe(addAEInscriberRecipe(meta == 0 ? new ItemStack(Blocks.GOLD_BLOCK) : MCSBlocks.minecraft.normal.C_GOLD_B.getStack(meta - 1), items.C_logic_processor_print_I.getStack(meta), items.C_logic_processor_press_I.getStack(meta), ItemStack.EMPTY, InscriberProcessType.INSCRIBE));
			inscriberRecipe.addRecipe(addAEInscriberRecipe(meta == 0 ? new ItemStack(Blocks.DIAMOND_BLOCK) : MCSBlocks.minecraft.normal.C_DIAMOND_B.getStack(meta - 1), items.C_eng_processor_print_I.getStack(meta), items.C_eng_processor_press_I.getStack(meta), ItemStack.EMPTY, InscriberProcessType.INSCRIBE));
			inscriberRecipe.addRecipe(addAEInscriberRecipe(items.C_silicon_I.getStack(meta), items.C_silicon_print_I.getStack(meta), items.C_silicon_press_I.getStack(meta), ItemStack.EMPTY, InscriberProcessType.INSCRIBE));

			// 模板
			inscriberRecipe.addRecipe(addAEInscriberRecipe(MCSBlocks.minecraft.normal.C_IRON_B.getStack(meta), items.C_calc_processor_press_I.getStack(meta), items.C_calc_processor_press_I.getStack(meta), ItemStack.EMPTY, InscriberProcessType.INSCRIBE));
			inscriberRecipe.addRecipe(addAEInscriberRecipe(MCSBlocks.minecraft.normal.C_IRON_B.getStack(meta), items.C_logic_processor_press_I.getStack(meta), items.C_logic_processor_press_I.getStack(meta), ItemStack.EMPTY, InscriberProcessType.INSCRIBE));
			inscriberRecipe.addRecipe(addAEInscriberRecipe(MCSBlocks.minecraft.normal.C_IRON_B.getStack(meta), items.C_eng_processor_press_I.getStack(meta), items.C_eng_processor_press_I.getStack(meta), ItemStack.EMPTY, InscriberProcessType.INSCRIBE));

			// 处理器
			inscriberRecipe.addRecipe(addAEInscriberRecipe(meta == 0 ? new ItemStack(Blocks.REDSTONE_BLOCK) : MCSBlocks.minecraft.normal.C_RED_STONE_B.getStack(meta), eng, items.C_eng_processor_print_I.getStack(meta), items.C_silicon_print_I.getStack(meta), InscriberProcessType.PRESS));
			inscriberRecipe.addRecipe(addAEInscriberRecipe(meta == 0 ? new ItemStack(Blocks.REDSTONE_BLOCK) : MCSBlocks.minecraft.normal.C_RED_STONE_B.getStack(meta), logic, items.C_logic_processor_print_I.getStack(meta), items.C_silicon_print_I.getStack(meta), InscriberProcessType.PRESS));
			inscriberRecipe.addRecipe(addAEInscriberRecipe(meta == 0 ? new ItemStack(Blocks.REDSTONE_BLOCK) : MCSBlocks.minecraft.normal.C_RED_STONE_B.getStack(meta), calc, items.C_calc_processor_print_I.getStack(meta), items.C_silicon_print_I.getStack(meta), InscriberProcessType.PRESS));

			add1x3ShapedRecipes(items.C_annihilation_core_I.getStack(2, meta), items.C_pure_nether_crystal_I.getStack(meta), items.C_fluix_dust_I.getStack(meta), items.C_logic_processor_I.getStack(meta));
			add1x3ShapedRecipes(items.C_annihilation_core_I.getStack(2, meta), MCSBlocks.minecraft.normal.C_QUARTZ_BLOCK_B.getStack(meta), items.C_fluix_dust_I.getStack(meta), items.C_logic_processor_I.getStack(meta));

			add1x3ShapedRecipes(items.C_formation_core_I.getStack(2, meta), pure_certus_crystal, items.C_fluix_dust_I.getStack(meta), items.C_logic_processor_I.getStack(meta));
			add1x3ShapedRecipes(items.C_formation_core_I.getStack(2, meta), certus_quartz_crystal, items.C_fluix_dust_I.getStack(meta), items.C_logic_processor_I.getStack(meta));
			add1x3ShapedRecipes(items.C_formation_core_I.getStack(2, meta), certus_quartz_crystal_charged, items.C_fluix_dust_I.getStack(meta), items.C_logic_processor_I.getStack(meta));

			recipe.addShapedlessRecipe(items.C_item_cell_1k_I.getStack(meta), items.C_empty_storage_cell_I.getStack(meta), items.C_item_cell_part_1k_I.getStack(meta));
			recipe.addShapedlessRecipe(items.C_item_cell_4k_I.getStack(meta), items.C_empty_storage_cell_I.getStack(meta), items.C_item_cell_part_4k_I.getStack(meta));
			recipe.addShapedlessRecipe(items.C_item_cell_16k_I.getStack(meta), items.C_empty_storage_cell_I.getStack(meta), items.C_item_cell_part_16k_I.getStack(meta));
			recipe.addShapedlessRecipe(items.C_item_cell_64k_I.getStack(meta), items.C_empty_storage_cell_I.getStack(meta), items.C_item_cell_part_64k_I.getStack(meta));

			recipe.addShapedlessRecipe(items.C_fluid_cell_1k_I.getStack(meta), items.C_empty_storage_cell_I.getStack(meta), items.C_fluid_cell_part_1k_I.getStack(meta));
			recipe.addShapedlessRecipe(items.C_fluid_cell_4k_I.getStack(meta), items.C_empty_storage_cell_I.getStack(meta), items.C_fluid_cell_part_4k_I.getStack(meta));
			recipe.addShapedlessRecipe(items.C_fluid_cell_16k_I.getStack(meta), items.C_empty_storage_cell_I.getStack(meta), items.C_fluid_cell_part_16k_I.getStack(meta));
			recipe.addShapedlessRecipe(items.C_fluid_cell_64k_I.getStack(meta), items.C_empty_storage_cell_I.getStack(meta), items.C_fluid_cell_part_64k_I.getStack(meta));

			ItemStack redstone = meta == 0 ? MCSBlocks.minecraft.normal.C_RED_STONE_B.getUnCompressedStack() : MCSBlocks.minecraft.normal.C_RED_STONE_B.getStack(meta - 1);
			ItemStack item_1k = items.C_item_cell_part_1k_I.getStack(meta);

			recipe.addShapedRecipes(item_1k, 
					redstone, pure_certus_crystal, redstone, 
					pure_certus_crystal, logic, pure_certus_crystal, 
					redstone, pure_certus_crystal, redstone);
			recipe.addShapedRecipes(item_1k, 
					redstone, certus_quartz_crystal, redstone, 
					certus_quartz_crystal, logic, certus_quartz_crystal, 
					redstone, certus_quartz_crystal, redstone);
			recipe.addShapedRecipes(item_1k, 
					redstone, certus_quartz_crystal_charged, redstone, 
					certus_quartz_crystal_charged, logic, certus_quartz_crystal_charged, 
					redstone, certus_quartz_crystal_charged, redstone);

			ItemStack lapis = meta == 0 ? MCSBlocks.minecraft.normal.C_LAPIS_B.getUnCompressedStack() : MCSBlocks.minecraft.normal.C_LAPIS_B.getStack(meta - 1);
			ItemStack fluid_1k = items.C_fluid_cell_part_1k_I.getStack(meta);
			recipe.addShapedRecipes(fluid_1k, 
					lapis, pure_certus_crystal, lapis, 
					pure_certus_crystal, logic, pure_certus_crystal, 
					lapis, pure_certus_crystal, lapis);
			recipe.addShapedRecipes(fluid_1k, 
					lapis, certus_quartz_crystal, lapis, 
					certus_quartz_crystal, logic, certus_quartz_crystal, lapis, 
					certus_quartz_crystal, lapis);
			recipe.addShapedRecipes(fluid_1k, 
					lapis, certus_quartz_crystal_charged, lapis, 
					certus_quartz_crystal_charged, logic, certus_quartz_crystal_charged, 
					lapis, certus_quartz_crystal_charged, lapis);

			ItemStack item_4k = items.C_item_cell_part_4k_I.getStack(meta);
			ItemStack fluid_4k = items.C_fluid_cell_part_4k_I.getStack(meta);
			ItemStack quartz_glass = blocks.C_certus_quartz_glass_B.getStack(meta);
			recipe.addShapedRecipes(item_4k, 
					redstone, calc, redstone, 
					item_1k, quartz_glass, item_1k,
					redstone, item_1k, redstone);
			recipe.addShapedRecipes(fluid_4k, 
					lapis, calc, lapis, 
					fluid_1k, quartz_glass, fluid_1k, 
					lapis, fluid_1k, lapis);

			ItemStack glowstone = meta == 0 ? MCSBlocks.minecraft.normal.C_GLOW_STONE_B.getUnCompressedStack() : MCSBlocks.minecraft.normal.C_GLOW_STONE_B.getStack(meta - 1);
			ItemStack item_16k = items.C_item_cell_part_16k_I.getStack(meta);
			ItemStack fluid_16k = items.C_fluid_cell_part_16k_I.getStack(meta);
			recipe.addShapedRecipes(item_16k, 
					glowstone, calc, glowstone, 
					item_4k, quartz_glass, item_4k, 
					glowstone, item_4k, glowstone);
			recipe.addShapedRecipes(fluid_16k, 
					lapis, calc, lapis, 
					fluid_4k, quartz_glass, fluid_4k, 
					lapis, fluid_4k, lapis);

			ItemStack item_64k = items.C_item_cell_part_64k_I.getStack(meta);
			ItemStack fluid_64k = items.C_fluid_cell_part_64k_I.getStack(meta);
			recipe.addShapedRecipes(item_64k, glowstone, calc,
					glowstone, item_16k, quartz_glass,
					item_16k, glowstone, item_16k, glowstone);
			recipe.addShapedRecipes(fluid_64k, lapis, calc,
					lapis, fluid_16k, quartz_glass,
					fluid_16k, lapis, fluid_16k, lapis);

			ItemStack fluix_dust = items.C_fluix_dust_I.getStack(meta);
			ItemStack pure_fluix_crystal = items.C_pure_fluix_crystal_I.getStack(meta);
			ItemStack ener_pearl = MCSBlocks.minecraft.has.C_ENDER_PEARL_B.getStack(meta);
			recipe.addShapedRecipes(items.C_fluix_pearl_I.getStack(meta),
					fluix_dust, pure_fluix_crystal, fluix_dust,
					pure_fluix_crystal, ener_pearl, pure_fluix_crystal,
					fluix_dust, pure_fluix_crystal, fluix_dust);
			recipe.addShapedRecipes(items.C_fluix_pearl_I.getStack(meta),
					fluix_dust, fluix_crystal, fluix_dust,
					fluix_crystal, ener_pearl, fluix_crystal,
					fluix_dust, fluix_crystal, fluix_dust);
		}
		MCS.getLogOS().info("register ae2 recipe success (took {}ms)", System.currentTimeMillis()-t);
	}
	
	private static IInscriberRecipe addAEInscriberRecipe(ItemStack input, ItemStack output, ItemStack top, ItemStack down, InscriberProcessType type) {
		IInscriberRecipeBuilder re = AEApi.instance().registries().inscriber().builder()
				.withInputs(Lists.newArrayList(input))
				.withOutput(output)
				.withProcessType(type);
		
		if(top != null && !top.isEmpty()) {
			re = re.withTopOptional(top);
		}
		if(down != null && !down.isEmpty()) {
			re = re.withBottomOptional(down);
		}
		return re.build();
	}

	private static void add1x3ShapedRecipes(ItemStack output, ItemStack input1, ItemStack input2, ItemStack input3) {
		recipe.addShapedRecipe(output, "ABC", 'A', input1, 'B', input2, 'C', input3);
	}

	@SuppressWarnings("static-access")
	private static void icCompressedItemCrafting() {
		MCSItems.IndustrialCraft.Normal ic2 = MCSItems.ic2.normal;
		MCSItems.OreStuff oreItem = MCSItems.ore_stuff;
		MCSBlocks.OreStuff oreBlock = MCSBlocks.ore_stuff;
		MCSBlocks.MinecraftBlock mc = MCSBlocks.minecraft;
		ic2.api.recipe.Recipes icRecipe = new ic2.api.recipe.Recipes();
		long t = System.currentTimeMillis();
		ItemStack forge_hammer = new ItemStack(Item.getByNameOrId("ic2:forge_hammer"), 1, Short.MAX_VALUE);
		
		for(int meta = 0; meta < 16; meta++) {
			// 装罐机
			icRecipe.cannerBottle.addRecipe(getICInput(ic2.C_MOX_I.getStack(meta)), getICInput(ic2.C_fuel_rod_I.getStack(meta)), ic2.C_MOX_FUEL_ROD_I.getStack(meta), false);
			icRecipe.cannerBottle.addRecipe(getICInput(ic2.C_URANIUM_I.getStack(meta)), getICInput(ic2.C_fuel_rod_I.getStack(meta)), ic2.C_URANIUM_FUEL_ROD_I.getStack(meta), false);

			// 提取机
			icRecipe.extractor.addRecipe(getICInput(ic2.C_resin_I.getStack(meta)), null, false, ic2.C_rubber_I.getStack(3, meta));
			icRecipe.extractor.addRecipe(getICInput(MCSBlocks.ic2.normal.C_rubber_wood_B.getStack(meta)), null, false, ic2.C_rubber_I.getStack(meta));
			icRecipe.extractor.addRecipe(getICInput(mc.normal.C_BRICK_BLOCK_B.getStack(meta)), null, false, MCSItems.minecraft.normal.C_brick_I.getStack(4, meta-1));
			icRecipe.extractor.addRecipe(getICInput(MCSItems.minecraft.normal.C_GUNPOWDER_I.getStack(meta)), null, false, oreItem.dust.C_dust_sulfur_I.getStack(meta));
			icRecipe.extractor.addRecipe(getICInput(ic2.C_dust_tin_hydrated_I.getStack(meta)), null, false, ic2.C_iodine_I.getStack(meta));

			// 金属成型机
			icRecipe.metalformerRolling.addRecipe(getICInput(oreItem.plate.C_plate_bronze_I.getStack(meta)), null, false, ic2.C_casing_bronze_I.getStack(2, meta));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreItem.plate.C_plate_copper_I.getStack(meta)), null, false, ic2.C_casing_copper_I.getStack(2, meta));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreItem.plate.C_plate_iron_I.getStack(meta)), null, false, ic2.C_casing_iron_I.getStack(2, meta));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreItem.plate.C_plate_tin_I.getStack(meta)), null, false, ic2.C_casing_tin_I.getStack(2, meta));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreItem.plate.C_plate_gold_I.getStack(meta)), null, false, ic2.C_casing_gold_I.getStack(2, meta));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreItem.plate.C_plate_lead_I.getStack(meta)), null, false, ic2.C_casing_lead_I.getStack(2, meta));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreItem.plate.C_plate_steel_I.getStack(meta)), null, false, ic2.C_casing_steel_I.getStack(2, meta));

			icRecipe.metalformerRolling.addRecipe(getICInput(mc.normal.C_GOLD_B.getStack(meta)), null, false, oreItem.plate.C_plate_gold_I.getStack(9, meta - 1));
			icRecipe.metalformerRolling.addRecipe(getICInput(mc.normal.C_IRON_B.getStack(meta)), null, false, oreItem.plate.C_plate_iron_I.getStack(9, meta - 1));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreBlock.block.C_bronze_B.getStack(meta)), null, false, oreItem.plate.C_plate_bronze_I.getStack(9, meta - 1));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreBlock.block.C_copper_B.getStack(meta)), null, false, oreItem.plate.C_plate_copper_I.getStack(9, meta - 1));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreBlock.block.C_tin_B.getStack(meta)), null, false, oreItem.plate.C_plate_tin_I.getStack(9, meta - 1));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreBlock.block.C_lead_B.getStack(meta)), null, false, oreItem.plate.C_plate_lead_I.getStack(9, meta - 1));
			icRecipe.metalformerRolling.addRecipe(getICInput(oreBlock.block.C_steel_B.getStack(meta)), null, false, oreItem.plate.C_plate_steel_I.getStack(9, meta - 1));

			icRecipe.metalformerExtruding.addRecipe(getICInput(oreItem.plate.C_plate_iron_I.getStack(meta)), null, false, ic2.C_fuel_rod_I.getStack(meta));

			// 打粉机
			icRecipe.macerator.addRecipe(getICInput(mc.normal.C_COBBLE_STONE_B.getStack(meta)), null, false, mc.normal.C_SAND_B.getStack(meta));
			icRecipe.macerator.addRecipe(getICInput(MCSItems.minecraft.food.C_SPIDER_EYE_F.getStack(meta)), null, false, ic2.C_dust_grin_powder_I.getStack(2, meta));
			icRecipe.macerator.addRecipe(getICInput(MCSItems.minecraft.food.C_SPIDER_EYE_F.getStack(meta)), null, false, ic2.C_dust_grin_powder_I.getStack(2, meta));
			icRecipe.macerator.addRecipe(getICInput(mc.normal.C_sandstone_B.getStack(meta)), null, false, mc.normal.C_SAND_B.getStack(meta));
			icRecipe.macerator.addRecipe(getICInput(ic2.C_fuel_rod_I.getStack(meta)), null, false, oreItem.dust.C_dust_iron_I.getStack(meta));
			icRecipe.macerator.addRecipe(getICInput(mc.normal.C_WOOL_B.getStack(meta)), null, false, MCSItems.minecraft.normal.C_STRING_I.getStack(2, meta));
			icRecipe.macerator.addRecipe(getICInput(mc.normal.C_STONE_B.getStack(meta)), null, false, mc.normal.C_COBBLE_STONE_B.getStack(meta));
			icRecipe.macerator.addRecipe(getICInput(mc.normal.C_OBSIDIAN_B.getStack(meta)), null, false, oreItem.dust.C_dust_obsidian_I.getStack(meta));
			icRecipe.macerator.addRecipe(getICInput(mc.normal.C_CLAY_B.getStack(meta)), null, false, ic2.C_dust_clay_I.getStack(2, meta));

			icRecipe.macerator.addRecipe(getICInput(ic2.C_dense_plate_bronze_I.getStack(meta)), null, false, oreItem.dust.C_dust_bronze_I.getStack(8, meta));
			icRecipe.macerator.addRecipe(getICInput(ic2.C_dense_plate_copper_I.getStack(meta)), null, false, oreItem.dust.C_dust_copper_I.getStack(8, meta));
			icRecipe.macerator.addRecipe(getICInput(ic2.C_dense_plate_gold_I.getStack(meta)), null, false, oreItem.dust.C_dust_gold_I.getStack(8, meta));
			icRecipe.macerator.addRecipe(getICInput(ic2.C_dense_plate_iron_I.getStack(meta)), null, false, oreItem.dust.C_dust_iron_I.getStack(8, meta));
			icRecipe.macerator.addRecipe(getICInput(ic2.C_dense_plate_lapis_I.getStack(meta)), null, false, ic2.C_dust_lapis_I.getStack(8, meta));
			icRecipe.macerator.addRecipe(getICInput(ic2.C_dense_plate_lead_I.getStack(meta)), null, false, oreItem.dust.C_dust_lead_I.getStack(8, meta));
			icRecipe.macerator.addRecipe(getICInput(ic2.C_dense_plate_obsidian_I.getStack(meta)), null, false, oreItem.dust.C_dust_obsidian_I.getStack(8, meta));
			icRecipe.macerator.addRecipe(getICInput(ic2.C_dense_plate_tin_I.getStack(meta)), null, false, oreItem.dust.C_dust_tin_I.getStack(8, meta));

			if(meta < 15) {
				icRecipe.macerator.addRecipe(getICInput(mc.normal.C_DIAMOND_B.getStack(meta)), null, false, ic2.C_dust_diamond_I.getStack(meta + 1));
				icRecipe.macerator.addRecipe(getICInput(ic2.C_energy_crystal_I.getStack(meta)), null, false, ic2.C_dust_energium_I.getStack(meta + 1));
			}
			// 压缩机
			icRecipe.compressor.addRecipe(getICInput(ic2.C_coal_chunk_I.getStack(meta)), null, false, meta == 0 ? mc.normal.C_DIAMOND_B.getUnCompressedStack() : mc.normal.C_DIAMOND_B.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(ic2.C_ic_alloy_ingot_I.getStack(meta)), null, false, ic2.C_plate_alloy_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(ic2.C_dust_lapis_I.getStack(meta)), null, false, ic2.C_plate_lapis_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(mc.normal.C_SAND_B.getStack(4, meta)), null, false, mc.normal.C_sandstone_B.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_obsidian_I.getStack(4, meta)), null, false, ic2.C_plate_obsidian_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(MCSItems.minecraft.normal.C_brick_I.getStack(4, meta)), null, false, mc.normal.C_BRICK_BLOCK_B.getStack(meta));

			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_gold_I.getStack(meta)), null, false, oreItem.plate.C_plate_gold_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_bronze_I.getStack(meta)), null, false, oreItem.plate.C_plate_bronze_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_copper_I.getStack(meta)), null, false, oreItem.plate.C_plate_copper_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_iron_I.getStack(meta)), null, false, oreItem.plate.C_plate_iron_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(ic2.C_dust_lapis_I.getStack(meta)), null, false, ic2.C_plate_lapis_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_lead_I.getStack(meta)), null, false, oreItem.plate.C_plate_lead_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_obsidian_I.getStack(meta)), null, false, ic2.C_plate_obsidian_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_steel_I.getStack(meta)), null, false, oreItem.plate.C_plate_steel_I.getStack(meta));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_tin_I.getStack(meta)), null, false, oreItem.plate.C_plate_tin_I.getStack(meta));

			icRecipe.compressor.addRecipe(getICInput(ic2.C_plate_lapis_I.getStack(meta)), null, false, ic2.C_dense_plate_lapis_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.plate.C_plate_tin_I.getStack(meta)), null, false, ic2.C_dense_plate_tin_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.plate.C_plate_copper_I.getStack(meta)), null, false, ic2.C_dense_plate_copper_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(ic2.C_dust_energium_I.getStack(meta)), null, false, ic2.C_energy_crystal_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.plate.C_plate_iron_I.getStack(meta)), null, false, ic2.C_dense_plate_iron_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.plate.C_plate_bronze_I.getStack(meta)), null, false, ic2.C_dense_plate_bronze_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(ic2.C_plate_obsidian_I.getStack(meta)), null, false, ic2.C_dense_plate_obsidian_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(mc.normal.C_SNOW_B.getStack(meta)), null, false, mc.normal.C_ICE_B.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(ic2.C_MOX_PELLET_I.getStack(meta)), null, false, ic2.C_URANIUM_235_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.plate.C_plate_lead_I.getStack(meta)), null, false, ic2.C_dense_plate_lead_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.plate.C_plate_steel_I.getStack(meta)), null, false, ic2.C_dense_plate_steel_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.plate.C_plate_gold_I.getStack(meta)), null, false, ic2.C_dense_plate_gold_I.getStack(meta - 1));

			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_gold_I.getStack(meta)), null, false, ic2.C_dense_plate_gold_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_bronze_I.getStack(meta)), null, false, ic2.C_dense_plate_bronze_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_copper_I.getStack(meta)), null, false, ic2.C_dense_plate_copper_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_iron_I.getStack(meta)), null, false, ic2.C_dense_plate_iron_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(ic2.C_dust_lapis_I.getStack(meta)), null, false, ic2.C_dense_plate_lapis_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_lead_I.getStack(meta)), null, false, ic2.C_dense_plate_lead_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_obsidian_I.getStack(meta)), null, false, ic2.C_dense_plate_obsidian_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_steel_I.getStack(meta)), null, false, ic2.C_dense_plate_steel_I.getStack(meta - 1));
			icRecipe.compressor.addRecipe(getICInput(oreItem.dust.C_dust_tin_I.getStack(meta)), null, false, ic2.C_dense_plate_tin_I.getStack(meta - 1));

			ItemStack lead_plate = MCSItems.ore_stuff.plate.C_plate_lead_I.getStack(meta);
			ItemStack obs_stone = MCSBlocks.minecraft.normal.C_COBBLE_STONE_B.getStack(meta);
			ItemStack andesite_stone = MCSBlocks.minecraft.normal.C_stone_andesite_B.getStack(meta);
			ItemStack granite_stone = MCSBlocks.minecraft.normal.C_stone_granite_B.getStack(meta);
			ItemStack diorite_stone = MCSBlocks.minecraft.normal.C_stone_diorite_B.getStack(meta);

			recipe.addShapedRecipes(MCSBlocks.ic2.normal.C_reactor_vessel_B.getStack(4, meta),
					lead_plate, obs_stone, lead_plate,
					obs_stone, lead_plate, obs_stone,
					lead_plate, obs_stone, lead_plate);
			recipe.addShapedRecipes(MCSBlocks.ic2.normal.C_reactor_vessel_B.getStack(4, meta),
					lead_plate, andesite_stone, lead_plate,
					andesite_stone, lead_plate, andesite_stone,
					lead_plate, andesite_stone, lead_plate);
			recipe.addShapedRecipes(MCSBlocks.ic2.normal.C_reactor_vessel_B.getStack(4, meta),
					lead_plate, granite_stone, lead_plate,
					granite_stone, lead_plate, granite_stone,
					lead_plate, granite_stone, lead_plate);
			recipe.addShapedRecipes(MCSBlocks.ic2.normal.C_reactor_vessel_B.getStack(4, meta),
					lead_plate, diorite_stone, lead_plate,
					diorite_stone, lead_plate, diorite_stone,
					lead_plate, diorite_stone, lead_plate);

			ItemStack circuit = ic2.C_circuit_I.getStack(meta);
			ItemStack re_battery = ic2.C_re_battery_I.getStack(meta);
			recipe.addShapedRecipes(ic2.C_charging_re_battery_I.getStack(meta),
					circuit, re_battery, circuit,
					re_battery, recipe.EMPTY, re_battery,
					circuit, re_battery, circuit);

			ItemStack heat_exchanger = ic2.C_heat_exchanger_I.getStack(meta);
			ItemStack advanced_re_battery = ic2.C_charging_advanced_re_battery_I.getStack(meta);
			ItemStack C_re_battery_I = ic2.C_charging_re_battery_I.getStack(meta);
			recipe.addShapedRecipes(ic2.C_charging_advanced_re_battery_I.getStack(meta),
					heat_exchanger, advanced_re_battery, heat_exchanger,
					advanced_re_battery, C_re_battery_I, advanced_re_battery,
					heat_exchanger, advanced_re_battery, heat_exchanger);

			ItemStack component_heat_exchanger = ic2.C_component_heat_exchanger_I.getStack(meta);
			ItemStack energy_crystal = ic2.C_energy_crystal_I.getStack(meta);
			ItemStack C_advanced_re_battery_I = ic2.C_advanced_re_battery_I.getStack(meta);
			recipe.addShapedRecipes(ic2.C_charging_energy_crystal_I.getStack(meta),
					component_heat_exchanger, energy_crystal, component_heat_exchanger,
					energy_crystal, C_advanced_re_battery_I, energy_crystal, 
					component_heat_exchanger, energy_crystal, component_heat_exchanger);

			ItemStack advanced_heat_exchanger = ic2.C_advanced_heat_exchanger_I.getStack(meta);
			ItemStack lapotron_crystal = ic2.C_lapotron_crystal_I.getStack(meta);
			ItemStack C_charging_energy_crystal_I = ic2.C_charging_energy_crystal_I.getStack(meta);
			recipe.addShapedRecipes(ic2.C_charging_lapotron_crystal_I.getStack(meta),
					advanced_heat_exchanger, lapotron_crystal, advanced_heat_exchanger,
					lapotron_crystal, C_charging_energy_crystal_I, lapotron_crystal,
					advanced_heat_exchanger, lapotron_crystal, advanced_heat_exchanger);

			ItemStack thick_neutron_reflector = ic2.C_thick_neutron_reflector_I.getStack(meta);
			ItemStack dense_plate_copper = ic2.C_dense_plate_copper_I.getStack(meta);
			ItemStack plate_iridium = ic2.C_plate_iridium_reinforcing_I.getStack(meta);
			recipe.addShapedRecipes(ic2.C_iridium_reflector_I.getStack(meta),
					thick_neutron_reflector, thick_neutron_reflector, thick_neutron_reflector,
					dense_plate_copper, plate_iridium, dense_plate_copper,
					thick_neutron_reflector, thick_neutron_reflector, thick_neutron_reflector);
			recipe.addShapedRecipes(ic2.C_iridium_reflector_I.getStack(meta),
					thick_neutron_reflector, dense_plate_copper, thick_neutron_reflector,
					thick_neutron_reflector, plate_iridium, thick_neutron_reflector,
					thick_neutron_reflector, dense_plate_copper, thick_neutron_reflector);

			ItemStack casing_copper = ic2.C_casing_copper_I.getStack(meta);
			recipe.addShapedRecipes(ic2.C_copper_boiler_I.getStack(meta),
					casing_copper, casing_copper, casing_copper,
					casing_copper, recipe.EMPTY, casing_copper,
					casing_copper, casing_copper, casing_copper);

			recipe.addSmelting(ic2.C_resin_I.getStack(meta), ic2.C_rubber_I.getStack(meta), 9);

			ItemStack dense_plate_iron = ic2.C_dense_plate_iron_I.getStack(meta);
			ItemStack plutonium = ic2.C_plutonium_I.getStack(meta);
			recipe.addShapedRecipes(ic2.C_RTG_PELLET_I.getStack(meta),
					dense_plate_iron, plutonium, dense_plate_iron,
					dense_plate_iron, plutonium, dense_plate_iron,
					dense_plate_iron, plutonium, dense_plate_iron);
			recipe.addShapedRecipes(ic2.C_RTG_PELLET_I.getStack(meta),
					dense_plate_iron, dense_plate_iron, dense_plate_iron,
					plutonium, plutonium, plutonium,
					dense_plate_iron, dense_plate_iron, dense_plate_iron);

			ItemStack U_238 = ic2.C_URANIUM_238_I.getStack(meta);
			recipe.addShapedRecipes(ic2.C_MOX_I.getStack(meta),
					U_238, plutonium, U_238,
					U_238, plutonium, U_238,
					U_238, plutonium, U_238);
			recipe.addShapedRecipes(ic2.C_MOX_I.getStack(meta),
					U_238, U_238, U_238,
					plutonium, plutonium, plutonium,
					U_238, U_238, U_238);

			ItemStack uranium_block = MCSBlocks.ore_stuff.block.C_uranium_B.getStack(meta);
			recipe.add1x1Recipes(JiuUtils.item.copyStack(U_238, 9, false), uranium_block);
			recipe.add3x3AllRecipes(uranium_block, U_238);

			ItemStack glass = MCSBlocks.minecraft.normal.C_GLASS_B.getStack(meta);
			ItemStack plate_alloy = ic2.C_plate_alloy_I.getStack(meta);
			recipe.addShapedRecipes(MCSBlocks.ic2.normal.C_REINFORCED_GLASS_B.getStack(meta),
					glass, plate_alloy, glass,
					glass, glass, glass,
					glass, plate_alloy, glass);
			recipe.addShapedRecipes(MCSBlocks.ic2.normal.C_REINFORCED_GLASS_B.getStack(meta),
					glass, glass, glass,
					plate_alloy, glass, plate_alloy,
					glass, glass, glass);

			ItemStack plate_iron = oreItem.plate.C_plate_iron_I.getStack(meta);
			recipe.addShapedRecipes(MCSBlocks.ic2.normal.C_BASIC_MACHINE_B.getStack(meta),
					plate_iron, plate_iron, plate_iron,
					plate_iron, recipe.EMPTY, plate_iron,
					plate_iron, plate_iron, plate_iron);

			List<String> ores = JiuUtils.item.getOreDict(oreItem.plate.C_plate_iron_I.getStack(meta));
			if(ores.isEmpty()) {
				recipe.addShapedlessRecipe(ic2.C_casing_iron_I.getStack(2, meta), forge_hammer, oreItem.plate.C_plate_iron_I.getStack(meta));
			}else {
				for(String oredict : ores) {
					recipe.addShapedlessRecipe(ic2.C_casing_iron_I.getStack(2, meta), forge_hammer, oredict);
				}
			}

			ores = JiuUtils.item.getOreDict(oreItem.plate.C_plate_bronze_I.getStack(meta));
			if(ores.isEmpty()) {
				recipe.addShapedlessRecipe(ic2.C_casing_copper_I.getStack(2, meta), forge_hammer, oreItem.plate.C_plate_bronze_I.getStack(meta));
			}else {
				for(String oredict : ores) {
					recipe.addShapedlessRecipe(ic2.C_casing_copper_I.getStack(2, meta), forge_hammer, oredict);
				}
			}

			ores = JiuUtils.item.getOreDict(oreItem.plate.C_plate_copper_I.getStack(meta));
			if(ores.isEmpty()) {
				recipe.addShapedlessRecipe(ic2.C_casing_copper_I.getStack(2, meta), forge_hammer, oreItem.plate.C_plate_copper_I.getStack(meta));
			}else {
				for(String oredict : ores) {
					recipe.addShapedlessRecipe(ic2.C_casing_copper_I.getStack(2, meta), forge_hammer, oredict);
				}
			}

			ores = JiuUtils.item.getOreDict(oreItem.plate.C_plate_gold_I.getStack(meta));
			if(ores.isEmpty()) {
				recipe.addShapedlessRecipe(ic2.C_casing_gold_I.getStack(2, meta), forge_hammer, oreItem.plate.C_plate_gold_I.getStack(meta));
			}else {
				for(String oredict : ores) {
					recipe.addShapedlessRecipe(ic2.C_casing_gold_I.getStack(2, meta), forge_hammer, oredict);
				}
			}

			ores = JiuUtils.item.getOreDict(oreItem.plate.C_plate_lead_I.getStack(meta));
			if(ores.isEmpty()) {
				recipe.addShapedlessRecipe(ic2.C_casing_lead_I.getStack(2, meta), forge_hammer, oreItem.plate.C_plate_lead_I.getStack(meta));
			}else {
				for(String oredict : ores) {
					recipe.addShapedlessRecipe(ic2.C_casing_lead_I.getStack(2, meta), forge_hammer, oredict);
				}
			}

			ores = JiuUtils.item.getOreDict(oreItem.plate.C_plate_steel_I.getStack(meta));
			if(ores.isEmpty()) {
				recipe.addShapedlessRecipe(ic2.C_casing_steel_I.getStack(2, meta), forge_hammer, oreItem.plate.C_plate_steel_I.getStack(meta));
			}else {
				for(String oredict : JiuUtils.item.getOreDict(oreItem.plate.C_plate_steel_I.getStack(meta))) {
					recipe.addShapedlessRecipe(ic2.C_casing_steel_I.getStack(2, meta), forge_hammer, oredict);
				}
			}

			ores = JiuUtils.item.getOreDict(oreItem.plate.C_plate_tin_I.getStack( 1, meta));
			if(ores.isEmpty()) {
				recipe.addShapedlessRecipe(ic2.C_casing_tin_I.getStack(2, meta), forge_hammer, oreItem.plate.C_plate_tin_I.getStack(meta));
			}else {
				for(String oredict : ores) {
					recipe.addShapedlessRecipe(ic2.C_casing_tin_I.getStack(2, meta), forge_hammer, oredict);
				}
			}
		}
		MCS.getLogOS().info("register ic2 recipe success (took {}ms)", System.currentTimeMillis()-t);
	}

	private static IRecipeInput getICInput(ItemStack stack) {
		return ic2.api.recipe.Recipes.inputFactory.forStack(stack);
	}
}
