package cat.jiu.mcs.api;

import cat.jiu.mcs.recipes.MCSRecipeUtils;

public interface ICompressedRecipe {
	void createRecipe(MCSRecipeUtils tool, ICompressedStuff stuff, int meta);
	boolean canCreateRecipe(int meta);
}
