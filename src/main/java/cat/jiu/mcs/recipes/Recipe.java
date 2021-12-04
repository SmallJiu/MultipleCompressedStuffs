package cat.jiu.mcs.recipes;

import cat.jiu.core.util.JiuUtils;

import cat.jiu.mcs.config.Configs;
import net.minecraft.item.ItemStack;
import cat.jiu.core.util.crafting.Recipes;

public class Recipe extends Recipes{

	public Recipe(String modid) {
		super(modid);
	}
	
	@Override
	public void add1x1Recipes(ItemStack out, ItemStack in){
		if(JiuUtils.item.getOreDict(in).isEmpty()) {
			super.add1x1Recipes(out, in);
		}else {
			for(String ore : JiuUtils.item.getOreDict(in)) {
				if(!JiuUtils.other.containKey(Configs.cancel_oredict_for_recipe, ore)) {
					super.add1x1RecipesWithOreDictionary(out, ore);
				}else {
					super.add1x1Recipes(out, in);
				}
			}
		}
	}
	
	@Override
	public void add2x2AllRecipes(ItemStack output, ItemStack input) {
		if(JiuUtils.item.getOreDict(input).isEmpty()) {
			super.add2x2AllRecipes(output, input);
		}else {
			for(String ore : JiuUtils.item.getOreDict(input)) {
				if(!JiuUtils.other.containKey(Configs.cancel_oredict_for_recipe, ore)) {
					super.add2x2AllRecipesWithOreDictionary(output, ore);
				}else {
					super.add2x2AllRecipes(output, input);
				}
			}
		}
    }

	@Override
	public void add3x3AllRecipes(ItemStack output, ItemStack input) {
		if(JiuUtils.item.getOreDict(input).isEmpty()) {
			super.add3x3AllRecipes(output, input);
		}else {
			for(String ore : JiuUtils.item.getOreDict(input)) {
				if(!JiuUtils.other.containKey(Configs.cancel_oredict_for_recipe, ore)) {
					super.add3x3AllRecipesWithOreDictionary(output, ore);
				}else {
					super.add3x3AllRecipes(output, input);
				}
			}
		}
    }
}
