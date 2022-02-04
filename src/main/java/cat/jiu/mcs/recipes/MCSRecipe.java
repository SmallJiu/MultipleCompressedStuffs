//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.recipes;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.base.BaseBlockSub;
import cat.jiu.mcs.util.base.BaseItemFood;
import cat.jiu.mcs.util.base.BaseItemSub;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SuppressWarnings("static-access")
public class MCSRecipe {
	private static final Recipe recipe = new Recipe(MCS.MODID);
	
	public static void register() {
		try {
			mc();
			item();
			compressed();
			smelting();
		}catch (Exception e) {}
	}
	
	private static void compressed() {
		itemCompressed();
		blockCompressed();
	}
	
	private static void mc() {
		recipe.addShapedRecipes(new ItemStack(Items.NETHER_WART, 9), new ItemStack(Blocks.NETHER_WART_BLOCK));
		recipe.addShapedRecipes(new ItemStack(Items.GOLDEN_APPLE, 1, 1),
			new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.GOLD_BLOCK), 
			new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Items.APPLE), 		new ItemStack(Blocks.GOLD_BLOCK), 
			new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(MCSBlocks.minecraft.normal.C_GOLD_B), new ItemStack(Blocks.GOLD_BLOCK));
	}
	
	private static void smelting() {
		addSmelting(MCSItems.minecraft.food.C_BEEF_F, 		MCSItems.minecraft.food.C_COOKED_BEEF_F);
		addSmelting(MCSItems.minecraft.food.C_CHICKEN_F, 	MCSItems.minecraft.food.C_COOKED_CHICKEN_F);
		addSmelting(MCSItems.minecraft.food.C_FISH_F, 		MCSItems.minecraft.food.C_COOKED_FISH_F);
		addSmelting(MCSItems.minecraft.food.C_MUTTON_F, 	MCSItems.minecraft.food.C_COOKED_MUTTON_F);
		addSmelting(MCSItems.minecraft.food.C_PORKCHOP_F, 	MCSItems.minecraft.food.C_COOKED_PORKCHOP_F);
		addSmelting(MCSItems.minecraft.food.C_RABBIT_F, 	MCSItems.minecraft.food.C_COOKED_RABBIT_F);
		addSmelting(MCSItems.minecraft.food.C_SALMON_FISH_F,MCSItems.minecraft.food.C_COOKED_SALMON_FISH_F);
		addSmelting(MCSItems.minecraft.food.C_POTATO_F, 	MCSItems.minecraft.food.C_BAKED_POTATO_F);
		
		addSmelting(MCSItems.draconic_evolution.normal.C_DRACONIUM_DUST_I, MCSBlocks.draconic_evolution.normal.C_DRACONIUM_BLOCK_B, 1);
		
		// ThermalFoundation: dust to block
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_ALUMINUM_I, MCSBlocks.thermal_foundation.normal.C_ALUMINUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_BRONZE_I, MCSBlocks.thermal_foundation.normal.C_BRONZE_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_CONSTANTAN_I, MCSBlocks.thermal_foundation.normal.C_CONSTANTAN_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_COPPER_I, MCSBlocks.thermal_foundation.normal.C_COPPER_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_ELECTRUM_I, MCSBlocks.thermal_foundation.normal.C_ELECTRUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_ENDERIUM_I, MCSBlocks.thermal_foundation.normal.C_ENDERIUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_GOLD_I, MCSBlocks.minecraft.normal.C_GOLD_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_INVAR_I, MCSBlocks.thermal_foundation.normal.C_INVAR_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_IRIDIUM_I, MCSBlocks.thermal_foundation.normal.C_IRIDIUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_IRON_I, MCSBlocks.minecraft.normal.C_IRON_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_LEAD_I, MCSBlocks.thermal_foundation.normal.C_LEAD_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_LUMIUM_I, MCSBlocks.thermal_foundation.normal.C_LUMIUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_MITHRIL_I, MCSBlocks.thermal_foundation.normal.C_MITHRIL_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_NICKL_I, MCSBlocks.thermal_foundation.normal.C_NICKEL_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_PLATINUM_I, MCSBlocks.thermal_foundation.normal.C_PLATINUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_SIGNALUM_I, MCSBlocks.thermal_foundation.normal.C_SIGNALUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_SILVER_I, MCSBlocks.thermal_foundation.normal.C_SILVER_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_STEEL_I, MCSBlocks.thermal_foundation.normal.C_STEEL_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_TIN_I, MCSBlocks.thermal_foundation.normal.C_TIN_B, 1);
		
	}
	
	private static void addSmelting(Item in, Block out, int disparity) {
		addSmelting(in,	Item.getItemFromBlock(out), disparity);
	}
	
	/**
	 * 物品a 合成 物品a的meta-x的物品b
	 */
	private static void addSmelting(Item in, Item out, int disparity) {
		if(in != null && out != null) {
			for(ModSubtypes type : ModSubtypes.values()) {
				int imeta = type.getMeta();
				int ometa = imeta-disparity;
				if(!(ometa < 0)) {
					recipe.addSmelting(new ItemStack(in, 1, imeta), new ItemStack(out, 1, ometa),  9);
				}
			}
		}
	}
	
	private static void addSmelting(Item in, Item out) {
		if(in != null && out != null) {
			for(ModSubtypes type : ModSubtypes.values()) {
				int meta = type.getMeta();
				recipe.addSmelting(new ItemStack(in, 1, meta), new ItemStack(out, 1, meta),  9);
			}
			recipe.addSmelting(new ItemStack(in, 1, 65535), new ItemStack(out, 1, 65535),  9);
		}
	}
	
	private static void item() {
		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.CAT_INGOT), 
			new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR),
			new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.minecraft.food.C_FISH_F, 1, 1), new ItemStack(MCSItems.normal.CAT_HAIR),
			new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR)
		);
		
		if(Configs.Custom.ModStuff.ThermalFoundation) {
			recipe.addShapedRecipes(new ItemStack(MCSBlocks.COMPRESSOR), 
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1), new ItemStack(MCSItems.thermal_foundation.normal.C_GEAR_PLATINUM_I), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B)
			);
		}else {
			recipe.addShapedRecipes(new ItemStack(MCSBlocks.COMPRESSOR), 
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1), recipe.EMPTY, new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B)
			);
		}
		
		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.CAT_HAMMER), 
			new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(MCSItems.normal.CAT_INGOT),
			new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(Items.MILK_BUCKET), new ItemStack(MCSItems.normal.CAT_INGOT),
			recipe.EMPTY, new ItemStack(Items.MILK_BUCKET), recipe.EMPTY
		);
		
		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.DESTROYER), 
			new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSBlocks.minecraft.has.C_NETHER_STAR_B, 1, 3), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1),
			new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSItems.normal.CAT_HAMMER), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B),
			new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSBlocks.minecraft.normal.C_BEDROCK_B, 1, 2), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1)
		);
	}
	
	static final boolean lag = false;
	
	private static void itemCompressed() {
		if(Configs.use_default_recipes) {
			if(lag) {
				for (BaseItemSub item : MCSItems.SUB_ITEMS) {
					ItemStack unCompressedItem = item.getUnCompressedStack();
					if(item.canMakeDefaultStackRecipe()){
						//物品压缩
						{
							recipe.add3x3AllRecipes(new ItemStack(item), unCompressedItem);
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add3x3AllRecipes(new ItemStack(item, 1, meta), new ItemStack(item, 1, (meta - 1)));
							}
						}
						
						//物品解压
						{
							recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(item));
							
							for(int meta = 0; meta < 15; ++meta) {
								ItemStack stack = new ItemStack(item, 1, (meta + 1));
								recipe.add1x1Recipes(new ItemStack(item, 9, meta), stack);
							}
						}
					}
				}
			}else {
				if(Configs.use_3x3_recipes) {
					for (BaseItemFood food : MCSItems.FOODS) {
						ItemStack unCompressedItem = food.getUnCompressedStack();
						if(food.canMakeDefaultStackRecipe()) {
							//食物压缩
							{
								recipe.add3x3AllRecipes(new ItemStack(food), unCompressedItem);
								
								for(int meta = 1; meta < 16; ++meta) {
									recipe.add3x3AllRecipes(new ItemStack(food, 1, meta), new ItemStack(food, 1, (meta - 1)));
								}
							}
							
							//食物解压
							{
								recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(food));
								
								for(int meta = 0; meta < 15; ++meta) {
									recipe.add1x1Recipes(new ItemStack(food, 9, meta), new ItemStack(food, 1, (meta + 1)));
								}
							}
						}
					}
					
					for (BaseItemSub item : MCSItems.SUB_ITEMS) {
						ItemStack unCompressedItem = item.getUnCompressedStack();
						if(item.canMakeDefaultStackRecipe()) {
							//物品压缩
							{
								recipe.add3x3AllRecipes(new ItemStack(item), unCompressedItem);
								
								for(int meta = 1; meta < 16; ++meta) {
									recipe.add3x3AllRecipes(new ItemStack(item, 1, meta), new ItemStack(item, 1, (meta - 1)));
								}
							}
							
							//物品解压
							{
								recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(item));
								
								for(int meta = 0; meta < 15; ++meta) {
									recipe.add1x1Recipes(new ItemStack(item, 9, meta), new ItemStack(item, 1, (meta + 1)));
								}
							}
						}
					}
				}else{
					for (BaseItemFood food : MCSItems.FOODS) {
						ItemStack unCompressedItem = food.getUnCompressedStack();
						if(food.canMakeDefaultStackRecipe()) {
							//食物压缩
							{
								recipe.add2x2AllRecipes(new ItemStack(food), unCompressedItem);
								
								for(int meta = 1; meta < 16; ++meta) {
									recipe.add2x2AllRecipes(new ItemStack(food, 1, meta), new ItemStack(food, 1, (meta - 1)));
								}
							}
							
							//食物解压
							{
								recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 4, false), new ItemStack(food));
								
								for(int meta = 0; meta < 15; ++meta) {
									recipe.add1x1Recipes(new ItemStack(food, 4, meta), new ItemStack(food, 1, (meta + 1)));
								}
							}
						}
					}
					
					for (BaseItemSub item : MCSItems.SUB_ITEMS) {
						ItemStack unCompressedItem = item.getUnCompressedStack();
						if(item.canMakeDefaultStackRecipe()) {
							//物品压缩
							{
								recipe.add2x2AllRecipes(new ItemStack(item), unCompressedItem);
								
								for(int meta = 1; meta < 16; ++meta) {
									recipe.add2x2AllRecipes(new ItemStack(item, 1, meta), new ItemStack(item, 1, (meta - 1)));
								}
							}
							
							//物品解压
							{
								recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 4, false), new ItemStack(item));
								
								for(int meta = 0; meta < 15; ++meta) {
									recipe.add1x1Recipes(new ItemStack(item, 4, meta), new ItemStack(item, 1, (meta + 1)));
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static void blockCompressed() {
		if (Configs.use_default_recipes) {
			for (BaseBlockSub block : MCSBlocks.SUB_BLOCKS) {
				ItemStack unCompressedItem = block.getUnCompressedItemStack();
				
				if(block.canMakeDefaultStackRecipe()) {
					if(lag) {
						// 方块压缩
						{
							recipe.add3x3AllRecipes(new ItemStack(block), unCompressedItem);
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add3x3AllRecipes(new ItemStack(block, 1, meta), new ItemStack(block, 1, (meta - 1)));
							}
						}
						
						// 方块解压
						{
							recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(block));
							
							for(int meta = 1; meta < 16; ++meta) {
									recipe.add1x1Recipes(new ItemStack(block, 9, meta - 1), new ItemStack(block, 1, meta));
								
							}
						}
					}else {
						if(Configs.use_3x3_recipes) {
							// 方块压缩
							{
								recipe.add3x3AllRecipes(new ItemStack(block), unCompressedItem);
								
								for(int meta = 1; meta < 16; ++meta) {
									recipe.add3x3AllRecipes(new ItemStack(block, 1, meta), new ItemStack(block, 1, (meta - 1)));
								}
							}
							
							// 方块解压
							{
								recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(block));
								
								for(int meta = 1; meta < 16; ++meta) {
									recipe.add1x1Recipes(new ItemStack(block, 9, meta - 1), new ItemStack(block, 1, meta));
									
								}
							}
						}else {
							//方块压缩
							{
								recipe.add2x2AllRecipes(new ItemStack(block), unCompressedItem);
								
								for(int meta = 1; meta < 16; ++meta) {
									recipe.add2x2AllRecipes(new ItemStack(block, 1, meta), new ItemStack(block, 1, (meta - 1)));
								}
							}
							
							//方块解压
							{
								recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 4, false), new ItemStack(block));
								
								for(int meta = 1; meta < 15; ++meta) {
									recipe.add1x1Recipes(new ItemStack(block, 4, meta - 1), new ItemStack(block, 1, meta));
									
								}
							}
						}
					}
				}
			}
		}
	}
}
