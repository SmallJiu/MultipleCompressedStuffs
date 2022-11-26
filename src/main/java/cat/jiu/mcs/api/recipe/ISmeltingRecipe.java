package cat.jiu.mcs.api.recipe;

import cat.jiu.mcs.api.ICompressedRecipe;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.recipes.MCSRecipeUtils;
import net.minecraft.item.ItemStack;

public interface ISmeltingRecipe extends ICompressedRecipe {
	@Override
	default void createRecipe(MCSRecipeUtils tool, ICompressedStuff stuff, int meta) {
		ItemStack out = this.getSmeltingOutput(meta);
		if(out !=null && !out.isEmpty()) {
			tool.addSmelting(stuff.getStack(meta), out, this.getSmeltingXP(meta));
		}
	}
	default boolean canCreateRecipe(int meta) {
		return this.getSmeltingOutput(meta)!=null;
	};
	ItemStack getSmeltingOutput(int meta);
	default int getSmeltingXP(int meta) {
		return 9;
	}
}
