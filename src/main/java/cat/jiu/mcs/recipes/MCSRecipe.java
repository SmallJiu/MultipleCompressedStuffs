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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
			new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.GOLD_BLOCK));
	}
	
	private static void smelting() {
		addSmelting(MCSItems.minecraft.normal.food.C_BEEF_F, 		MCSItems.minecraft.normal.food.C_COOKED_BEEF_F);
		addSmelting(MCSItems.minecraft.normal.food.C_CHICKEN_F, 	MCSItems.minecraft.normal.food.C_COOKED_CHICKEN_F);
		addSmelting(MCSItems.minecraft.normal.food.C_FISH_F, 		MCSItems.minecraft.normal.food.C_COOKED_FISH_F);
		addSmelting(MCSItems.minecraft.normal.food.C_MUTTON_F, 		MCSItems.minecraft.normal.food.C_COOKED_MUTTON_F);
		addSmelting(MCSItems.minecraft.normal.food.C_PORKCHOP_F, 	MCSItems.minecraft.normal.food.C_COOKED_PORKCHOP_F);
		addSmelting(MCSItems.minecraft.normal.food.C_RABBIT_F, 		MCSItems.minecraft.normal.food.C_COOKED_RABBIT_F);
		addSmelting(MCSItems.minecraft.normal.food.C_SALMON_FISH_F, MCSItems.minecraft.normal.food.C_COOKED_SALMON_FISH_F);
		addSmelting(MCSItems.minecraft.normal.food.C_POTATO_F, 		MCSItems.minecraft.normal.food.C_BAKED_POTATO_F);
		
	}
	
	private static void addSmelting(Item in, Item out) {
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			recipe.addSmelting(new ItemStack(in, 1, meta), new ItemStack(out, 1, meta),  9);
		}
		recipe.addSmelting(new ItemStack(in, 1, 65535), new ItemStack(out, 1, 65535),  9);
	}
	
	private static void item() {
		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.CAT_INGOT), 
			new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR),
			new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.minecraft.normal.food.C_FISH_F, 1, 1), new ItemStack(MCSItems.normal.CAT_HAIR),
			new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR)
		);
		final ItemStack hammer = new ItemStack(MCSItems.normal.CAT_HAMMER);
		NBTTagCompound nbt = hammer.getTagCompound() == null ? new NBTTagCompound() : hammer.getTagCompound();
		nbt.setInteger("time", 0);
		nbt.setLong("bedrocks", 0L);
		hammer.setTagCompound(nbt);
		
		recipe.addShapedRecipes(hammer, 
			new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(MCSItems.normal.CAT_INGOT),
			new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(Items.MILK_BUCKET), new ItemStack(MCSItems.normal.CAT_INGOT),
			recipe.EMPTY, new ItemStack(Items.MILK_BUCKET), recipe.EMPTY
		);
		
		final ItemStack destroyer = new ItemStack(MCSItems.normal.DESTROYER);
		NBTTagCompound nbt0 = destroyer.getTagCompound() == null ? new NBTTagCompound() : destroyer.getTagCompound();
		nbt0.setDouble("breaks", 0D);
		destroyer.setTagCompound(nbt0);
		
		recipe.addShapedRecipes(destroyer, 
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
					
					//物品压缩
					{
						recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(item), unCompressedItem);
						
						for(int meta = 1; meta < 16; ++meta) {
							recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(item, 1, meta), new ItemStack(item, 1, (meta - 1)));
						}
					}
					
					//物品解压
					{
						recipe.add1x1RecipesWithOreDictionary(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(item));
						
						for(int meta = 0; meta < 15; ++meta) {
							ItemStack stack = new ItemStack(item, 1, (meta + 1));
							recipe.add1x1RecipesWithOreDictionary(new ItemStack(item, 9, meta), stack);
						}
					}
				}
			}else {
				if(Configs.use_3x3_recipes) {
					for (BaseItemFood food : MCSItems.FOODS) {
						ItemStack unCompressedItem = food.getUnCompressedStack();
						
						//食物压缩
						{
							recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(food), unCompressedItem);
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(food, 1, meta), new ItemStack(food, 1, (meta - 1)));
							}
						}
						
						//食物解压
						{
							recipe.add1x1RecipesWithOreDictionary(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(food));
							
							for(int meta = 0; meta < 15; ++meta) {
								recipe.add1x1RecipesWithOreDictionary(new ItemStack(food, 9, meta), new ItemStack(food, 1, (meta + 1)));
							}
						}
					}
					
					for (BaseItemSub item : MCSItems.SUB_ITEMS) {
						ItemStack unCompressedItem = item.getUnCompressedStack();
						
						//物品压缩
						{
							recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(item), unCompressedItem);
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(item, 1, meta), new ItemStack(item, 1, (meta - 1)));
							}
						}
						
						//物品解压
						{
							recipe.add1x1RecipesWithOreDictionary(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(item));
							
							for(int meta = 0; meta < 15; ++meta) {
								recipe.add1x1RecipesWithOreDictionary(new ItemStack(item, 9, meta), new ItemStack(item, 1, (meta + 1)));
							}
						}
					}
				}else{
					for (BaseItemFood food : MCSItems.FOODS) {
						ItemStack unCompressedItem = food.getUnCompressedStack();
						
						//食物压缩
						{
							recipe.add2x2AllRecipesWithOreDictionary(new ItemStack(food), unCompressedItem);
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add2x2AllRecipesWithOreDictionary(new ItemStack(food, 1, meta), new ItemStack(food, 1, (meta - 1)));
							}
						}
						
						//食物解压
						{
							recipe.add1x1RecipesWithOreDictionary(JiuUtils.item.copyStack(unCompressedItem, 4, false), new ItemStack(food));
							
							for(int meta = 0; meta < 15; ++meta) {
								recipe.add1x1RecipesWithOreDictionary(new ItemStack(food, 4, meta), new ItemStack(food, 1, (meta + 1)));
							}
						}
					}
					
					for (BaseItemSub item : MCSItems.SUB_ITEMS) {
						ItemStack unCompressedItem = item.getUnCompressedStack();
						
						//物品压缩
						{
							recipe.add2x2AllRecipesWithOreDictionary(new ItemStack(item), unCompressedItem);
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add2x2AllRecipesWithOreDictionary(new ItemStack(item, 1, meta), new ItemStack(item, 1, (meta - 1)));
							}
						}
						
						//物品解压
						{
							recipe.add1x1RecipesWithOreDictionary(JiuUtils.item.copyStack(unCompressedItem, 4, false), new ItemStack(item));
							
							for(int meta = 0; meta < 15; ++meta) {
								recipe.add1x1RecipesWithOreDictionary(new ItemStack(item, 4, meta), new ItemStack(item, 1, (meta + 1)));
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
				if(lag) {
					// 方块压缩
					{
						recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(block), unCompressedItem);
						
						for(int meta = 1; meta < 16; ++meta) {
							recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(block, 1, meta), new ItemStack(block, 1, (meta - 1)));
						}
					}
					
					// 方块解压
					{
						recipe.add1x1RecipesWithOreDictionary(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(block));
						
						for(int meta = 1; meta < 16; ++meta) {
								recipe.add1x1RecipesWithOreDictionary(new ItemStack(block, 9, meta - 1), new ItemStack(block, 1, meta));
							
						}
					}
				}else {
					if(Configs.use_3x3_recipes) {
						// 方块压缩
						{
							recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(block), unCompressedItem);
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add3x3AllRecipesWithOreDictionary(new ItemStack(block, 1, meta), new ItemStack(block, 1, (meta - 1)));
							}
						}
						
						// 方块解压
						{
							recipe.add1x1RecipesWithOreDictionary(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(block));
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add1x1RecipesWithOreDictionary(new ItemStack(block, 9, meta - 1), new ItemStack(block, 1, meta));
								
							}
						}
					}else {
						//方块压缩
						{
							recipe.add2x2AllRecipesWithOreDictionary(new ItemStack(block), unCompressedItem);
							
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add2x2AllRecipesWithOreDictionary(new ItemStack(block, 1, meta), new ItemStack(block, 1, (meta - 1)));
							}
						}
						
						//方块解压
						{
							recipe.add1x1RecipesWithOreDictionary(JiuUtils.item.copyStack(unCompressedItem, 4, false), new ItemStack(block));
							
							for(int meta = 1; meta < 15; ++meta) {
								recipe.add1x1RecipesWithOreDictionary(new ItemStack(block, 4, meta - 1), new ItemStack(block, 1, meta));
								
							}
						}
					}
				}
			}
		}
	}
}
