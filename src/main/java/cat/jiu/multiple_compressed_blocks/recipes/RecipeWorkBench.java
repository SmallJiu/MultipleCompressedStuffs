package cat.jiu.multiple_compressed_blocks.recipes;

import cat.jiu.multiple_compressed_blocks.api.Recipes;
import cat.jiu.multiple_compressed_blocks.server.init.InitBlock;
import net.minecraft.init.Blocks;
import cat.jiu.multiple_compressed_blocks.config.Configs;

public class RecipeWorkBench {

	private static final Recipes recipe = new Recipes();

	public static void register() {
		unPackBlocks();
		compressedBlocks();
	}

	private static void unPackBlocks() {
		if(Configs.use_default_recipes.bone_block) {
			recipe.addUnCompressedRecipes(Blocks.BONE_BLOCK, 9, 0, InitBlock.C_BONE_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_BONE_B, 9, i, InitBlock.C_BONE_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.coal_block) {
			recipe.addUnCompressedRecipes(Blocks.COAL_BLOCK, 9, 0, InitBlock.C_COAL_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_COAL_B, 9, i, InitBlock.C_COAL_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.cobble_stone) {
			recipe.addUnCompressedRecipes(Blocks.COBBLESTONE, 9, 0, InitBlock.C_COBBLE_STONE_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_COBBLE_STONE_B, 9, i, InitBlock.C_COBBLE_STONE_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.diamon_block) {
			recipe.addUnCompressedRecipes(Blocks.DIAMOND_BLOCK, 9, 0, InitBlock.C_DIAMOND_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_DIAMOND_B, 9, i, InitBlock.C_DIAMOND_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.dirt) {
			recipe.addUnCompressedRecipes(Blocks.DIRT, 9, 0, InitBlock.C_DIRT_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_DIRT_B, 9, i, InitBlock.C_DIRT_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.emerald_block) {
			recipe.addUnCompressedRecipes(Blocks.EMERALD_BLOCK, 9, 0, InitBlock.C_EMERALD_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_EMERALD_B, 9, i, InitBlock.C_EMERALD_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.glass) {
			recipe.addUnCompressedRecipes(Blocks.GLASS, 9, 0, InitBlock.C_GLASS_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_GLASS_B, 9, i, InitBlock.C_GLASS_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.glow_stone_block) {
			recipe.addUnCompressedRecipes(Blocks.GLOWSTONE, 9, 0, InitBlock.C_GLOW_STONE_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_GLOW_STONE_B, 9, i, InitBlock.C_GLOW_STONE_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.gold_block) {
			recipe.addUnCompressedRecipes(Blocks.GOLD_BLOCK, 9, 0, InitBlock.C_GOLD_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_GOLD_B, 9, i, InitBlock.C_GOLD_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.gravel) {
			recipe.addUnCompressedRecipes(Blocks.GRAVEL, 9, 0, InitBlock.C_GRAVEL_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_GRAVEL_B, 9, i, InitBlock.C_GRAVEL_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.ice) {
			recipe.addUnCompressedRecipes(Blocks.ICE, 9, 0, InitBlock.C_ICE_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_ICE_B, 9, i, InitBlock.C_ICE_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.iron_block) {
			recipe.addUnCompressedRecipes(Blocks.IRON_BLOCK, 9, 0, InitBlock.C_IRON_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_IRON_B, 9, i, InitBlock.C_IRON_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.melon_block) {
			recipe.addUnCompressedRecipes(Blocks.MELON_BLOCK, 9, 0, InitBlock.C_MELON_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_MELON_B, 9, i, InitBlock.C_MELON_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.netherrack) {
			recipe.addUnCompressedRecipes(Blocks.NETHERRACK, 9, 0, InitBlock.C_NETHERRACK_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_NETHERRACK_B, 9, i, InitBlock.C_NETHERRACK_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.pumpkin_block) {
			recipe.addUnCompressedRecipes(Blocks.PUMPKIN, 9, 0, InitBlock.C_PUMPKIN_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_PUMPKIN_B, 9, i, InitBlock.C_PUMPKIN_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.redstone_block) {
			recipe.addUnCompressedRecipes(Blocks.REDSTONE_BLOCK, 9, 0, InitBlock.C_RED_STONE_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_RED_STONE_B, 9, i, InitBlock.C_RED_STONE_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.sand) {
			recipe.addUnCompressedRecipes(Blocks.SAND, 9, 0, InitBlock.C_SAND_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_SAND_B, 9, i, InitBlock.C_SAND_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.snow) {
			recipe.addUnCompressedRecipes(Blocks.SNOW, 9, 0, InitBlock.C_SNOW_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_SNOW_B, 9, i, InitBlock.C_SNOW_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.stone) {
			recipe.addUnCompressedRecipes(Blocks.STONE, 9, 0, InitBlock.C_STONE_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_STONE_B, 9, i, InitBlock.C_STONE_B, i + 1);
			}
		}

		if(Configs.use_default_recipes.wool) {
			recipe.addUnCompressedRecipes(Blocks.WOOL, 9, 0, InitBlock.C_WOOL_B, 0);
			for (int i = 0; i < 15; i++) {
				recipe.addUnCompressedRecipes(InitBlock.C_WOOL_B, 9, i, InitBlock.C_WOOL_B, i + 1);
			}
		}
	}

	private static void compressedBlocks() {
		if(Configs.use_default_recipes.bone_block) {
			recipe.addCompressedRecipes(InitBlock.C_BONE_B, 1, 0, Blocks.BONE_BLOCK);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_BONE_B, 1, i, InitBlock.C_BONE_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.coal_block) {
			recipe.addCompressedRecipes(InitBlock.C_COAL_B, 1, 0, Blocks.COAL_BLOCK, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_COAL_B, 1, i, InitBlock.C_COAL_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.coal_block) {
			recipe.addCompressedRecipes(InitBlock.C_COBBLE_STONE_B, 1, 0, Blocks.COBBLESTONE, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_COBBLE_STONE_B, 1, i, InitBlock.C_COBBLE_STONE_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.diamon_block) {
			recipe.addCompressedRecipes(InitBlock.C_DIAMOND_B, 1, 0, Blocks.DIAMOND_BLOCK, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_DIAMOND_B, 1, i, InitBlock.C_DIAMOND_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.dirt) {
			recipe.addCompressedRecipes(InitBlock.C_DIRT_B, 1, 0, Blocks.DIRT, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_DIRT_B, 1, i, InitBlock.C_DIRT_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.emerald_block) {
			recipe.addCompressedRecipes(InitBlock.C_EMERALD_B, 1, 0, Blocks.EMERALD_BLOCK, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_EMERALD_B, 1, i, InitBlock.C_EMERALD_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.glass) {
			recipe.addCompressedRecipes(InitBlock.C_GLASS_B, 1, 0, Blocks.GLASS, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_GLASS_B, 1, i, InitBlock.C_GLASS_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.glow_stone_block) {
			recipe.addCompressedRecipes(InitBlock.C_GLOW_STONE_B, 1, 0, Blocks.GLOWSTONE, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_GLOW_STONE_B, 1, i, InitBlock.C_GLOW_STONE_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.gold_block) {
			recipe.addCompressedRecipes(InitBlock.C_GOLD_B, 1, 0, Blocks.GOLD_BLOCK, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_GOLD_B, 1, i, InitBlock.C_GOLD_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.gravel) {
			recipe.addCompressedRecipes(InitBlock.C_GRAVEL_B, 1, 0, Blocks.GRAVEL, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_GRAVEL_B, 1, i, InitBlock.C_GRAVEL_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.ice) {
			recipe.addCompressedRecipes(InitBlock.C_ICE_B, 1, 0, Blocks.ICE, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_ICE_B, 1, i, InitBlock.C_ICE_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.iron_block) {
			recipe.addCompressedRecipes(InitBlock.C_IRON_B, 1, 0, Blocks.IRON_BLOCK, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_IRON_B, 1, i, InitBlock.C_IRON_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.melon_block) {
			recipe.addCompressedRecipes(InitBlock.C_MELON_B, 1, 0, Blocks.MELON_BLOCK, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_MELON_B, 1, i, InitBlock.C_MELON_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.netherrack) {
			recipe.addCompressedRecipes(InitBlock.C_NETHERRACK_B, 1, 0, Blocks.NETHERRACK, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_NETHERRACK_B, 1, i, InitBlock.C_NETHERRACK_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.pumpkin_block) {
			recipe.addCompressedRecipes(InitBlock.C_PUMPKIN_B, 1, 0, Blocks.PUMPKIN, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_PUMPKIN_B, 1, i, InitBlock.C_PUMPKIN_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.redstone_block) {
			recipe.addCompressedRecipes(InitBlock.C_RED_STONE_B, 1, 0, Blocks.REDSTONE_BLOCK, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_RED_STONE_B, 1, i, InitBlock.C_RED_STONE_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.sand) {
			recipe.addCompressedRecipes(InitBlock.C_SAND_B, 1, 0, Blocks.SAND, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_SAND_B, 1, i, InitBlock.C_SAND_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.snow) {
			recipe.addCompressedRecipes(InitBlock.C_SNOW_B, 1, 0, Blocks.SNOW, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_SNOW_B, 1, i, InitBlock.C_SNOW_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.stone) {
			recipe.addCompressedRecipes(InitBlock.C_STONE_B, 1, 0, Blocks.STONE, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_STONE_B, 1, i, InitBlock.C_STONE_B, i - 1);
			}
		}

		if(Configs.use_default_recipes.wool) {
			recipe.addCompressedRecipes(InitBlock.C_WOOL_B, 1, 0, Blocks.WOOL, 0);
			for (int i = 1; i < 16; i++) {
				recipe.addCompressedRecipes(InitBlock.C_WOOL_B, 1, i, InitBlock.C_WOOL_B, i - 1);
			}
		}
	}
}
