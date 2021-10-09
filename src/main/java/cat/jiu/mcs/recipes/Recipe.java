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
	public void add1x1RecipesWithOreDictionary(ItemStack out, ItemStack in){
		if(JiuUtils.item.getOreDict(in).isEmpty()) {
			this.addShapedRecipe(out, "X", 'X', in);
		}else {
			for(String ore : JiuUtils.item.getOreDict(in)) {
				if(!JiuUtils.other.containKey(Configs.cancel_oredict_for_recipe, ore)) {
					this.addShapedRecipe(out, "X", 'X', ore);
				}else {
					this.addShapedRecipe(out, "X", 'X', in);
				}
			}
		}
	}
	
	@Override
	public void add2x2AllRecipesWithOreDictionary(ItemStack output, ItemStack input) {
		if(JiuUtils.item.getOreDict(input).isEmpty()) {
			this.addShapedRecipe(output, "XX", "XX", 'X', input);
		}else {
			for(String ore : JiuUtils.item.getOreDict(input)) {
				if(!JiuUtils.other.containKey(Configs.cancel_oredict_for_recipe, ore)) {
					this.addShapedRecipe(output, "XX", "XX", 'X', ore);
				}else {
					this.addShapedRecipe(output, "XX", "XX", 'X', input);
				}
			}
		}
    }

	@Override
	public void add3x3AllRecipesWithOreDictionary(ItemStack output, ItemStack input) {
		if(JiuUtils.item.getOreDict(input).isEmpty()) {
			this.addShapedRecipe(output, "XXX", "XXX", "XXX", 'X', input);
		}else {
			for(String ore : JiuUtils.item.getOreDict(input)) {
				if(!JiuUtils.other.containKey(Configs.cancel_oredict_for_recipe, ore)) {
					this.addShapedRecipe(output, "XXX", "XXX", "XXX", 'X', ore);
				}else {
					this.addShapedRecipe(output, "XXX", "XXX", "XXX", 'X', input);
				}
			}
		}
    }
}
