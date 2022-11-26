package cat.jiu.mcs.api.recipe;

import appeng.api.AEApi;

import cat.jiu.mcs.api.ICompressedRecipe;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.recipes.MCSRecipeUtils;

import ic2.api.recipe.Recipes;

import net.minecraft.item.ItemStack;

public interface IPulverizerRecipe extends ICompressedRecipe {
	@Override
	default void createRecipe(MCSRecipeUtils tool, ICompressedStuff stuff, int meta) {
		if(Configs.Custom.Mod_Stuff.IndustrialCraft) {
			Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(this.getPulverizerOutput(meta)), null, false, stuff.getStack(meta));
		}
		if(Configs.Custom.Mod_Stuff.AppliedEnergistics2) {
			AEApi.instance().registries().grinder().addRecipe(AEApi.instance().registries().grinder().builder().withInput(stuff.getStack(meta)).withOutput(this.getPulverizerOutput(meta)).build());
		}
	}
	@Override
	default boolean canCreateRecipe(int meta) {
		ItemStack out = this.getPulverizerOutput(meta);
		return out!=null && !out.isEmpty();
	}
	ItemStack getPulverizerOutput(int meta);
}
