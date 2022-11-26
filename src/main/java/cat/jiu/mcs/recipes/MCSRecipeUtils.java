package cat.jiu.mcs.recipes;

import java.util.List;

import cat.jiu.core.util.JiuUtils;

import cat.jiu.mcs.util.MCSUtil;
import net.minecraft.item.ItemStack;
import cat.jiu.core.util.crafting.Recipes;

public class MCSRecipeUtils extends Recipes {
	public MCSRecipeUtils(String modid) {
		super(modid);
	}
	
	public void addSwordRecipes(ItemStack output, Object material, Object rod) {
		if((material instanceof ItemStack || material instanceof String)
		&& (rod instanceof ItemStack || rod instanceof String)) {
			super.addShapedRecipe(output,
					"A",
					"A",
					"B",
					'A', material,
					'B', rod
			);
		}
	}
	public void addPickaxeRecipes(ItemStack output, Object material, Object rod) {
		if((material instanceof ItemStack || material instanceof String)
		&& (rod instanceof ItemStack || rod instanceof String)) {
			super.addShapedRecipe(output,
					"AAA",
					" B ",
					" B ",
					'A', material,
					'B', rod
			);
		}
	}
	public void addShovelRecipes(ItemStack output, Object material, Object rod) {
		if((material instanceof ItemStack || material instanceof String)
		&& (rod instanceof ItemStack || rod instanceof String)) {
			super.addShapedRecipe(output,
					"A",
					"B",
					"B",
					'A', material,
					'B', rod
			);
		}
	}
	public void addAxeRecipes(ItemStack output, Object material, Object rod) {
		if((material instanceof ItemStack || material instanceof String)
		&& (rod instanceof ItemStack || rod instanceof String)) {
			super.addShapedRecipe(output,
					"AA",
					"AB",
					" B",
					'A', material,
					'B', rod
			);
			super.addShapedRecipe(output,
					"AA",
					"BA",
					"B ",
					'A', material,
					'B', rod
			);
		}
	}
	public void addHoeRecipes(ItemStack output, Object material, Object rod) {
		if((material instanceof ItemStack || material instanceof String)
		&& (rod instanceof ItemStack || rod instanceof String)) {
			super.addShapedRecipe(output,
					"AA",
					" B",
					" B",
					'A', material,
					'B', rod
			);
			super.addShapedRecipe(output,
					"AA",
					"B ",
					"B ",
					'A', material,
					'B', rod
			);
		}
	}

	public void add1x1ShapedRecipes(ItemStack output, ItemStack input) {
		super.add1x1Recipes(output, input);
	}

	public void add2x2ShapedRecipes(ItemStack output, ItemStack input) {
		super.add2x2AllRecipes(output, input);
	}

	public void add3X3ShapedRecipes(ItemStack output, ItemStack input) {
		super.add3x3AllRecipes(output, input);
	}

	@Override
	public void add1x1Recipes(ItemStack out, ItemStack in) {
		List<String> ores = MCSUtil.item.getOreDict(in);
		if(ores.isEmpty()) {
			super.add1x1Recipes(out, in);
		}else {
			for(String ore : JiuUtils.item.getOreDict(in)) {
				super.add1x1RecipesWithOreDictionary(out, ore);
			}
		}
	}

	@Override
	public void add2x2AllRecipes(ItemStack output, ItemStack input) {
		List<String> ores = MCSUtil.item.getOreDict(input);
		if(ores.isEmpty()) {
			super.add2x2AllRecipes(output, input);
		}else {
			for(String ore : JiuUtils.item.getOreDict(input)) {
				super.add2x2AllRecipesWithOreDictionary(output, ore);
			}
		}
	}

	@Override
	public void add3x3AllRecipes(ItemStack output, ItemStack input) {
		List<String> ores = MCSUtil.item.getOreDict(input);
		if(ores.isEmpty()) {
			super.add3x3AllRecipes(output, input);
		}else {
			for(String ore : JiuUtils.item.getOreDict(input)) {
				super.add3x3AllRecipesWithOreDictionary(output, ore);
			}
		}
	}
}
