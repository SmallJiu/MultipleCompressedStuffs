package cat.jiu.multiple_compressed_blocks.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Recipes {
	/* ===============================Compressed Start============================================================================= */

	public void addCompressedRecipes(Block output, int outAmount, int outMeta, Item input) {
		addCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input));
	}

	public void addCompressedRecipes(Block output, int outAmount, int outMeta, Item input, int inMeta) {
		addCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input, 1, inMeta));
	}

	public void addCompressedRecipes(Item output, int outAmount, int outMeta, Block input) {
		addCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input));
	}

	public void addCompressedRecipes(Item output, int outAmount, int outMeta, Block input, int inMeta) {
		addCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input, 1, inMeta));
	}

	public void addCompressedRecipes(Item output, int outAmount, int outMeta, Item input) {
		addCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input));
	}

	public void addCompressedRecipes(Item output, int outAmount, int outMeta, Item input, int inMeta) {
		addCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input, 1, inMeta));
	}

	public void addCompressedRecipes(Block output, int outAmount, int outMeta, Block input) {
		addCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input));
	}

	public void addCompressedRecipes(Block output, int outAmount, int outMeta, Block input, int inMeta) {
		addCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input, 1, inMeta));
	}
	
/* =============================Compressed End=============================================================================== */

/* =============================UnCompressed Start=============================================================================== */
	
	public void addUnCompressedRecipes(Block output, int outAmount, int outMeta, Item input) {
		addUnCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input));
	}

	public void addUnCompressedRecipes(Block output, int outAmount, int outMeta, Item input, int inMeta) {
		addUnCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input, 1, inMeta));
	}

	public void addUnCompressedRecipes(Item output, int outAmount, int outMeta, Block input) {
		addUnCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input));
	}

	public void addUnCompressedRecipes(Item output, int outAmount, int outMeta, Block input, int inMeta) {
		addUnCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input, 1, inMeta));
	}

	public void addUnCompressedRecipes(Item output, int outAmount, int outMeta, Item input) {
		addUnCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input));
	}

	public void addUnCompressedRecipes(Item output, int outAmount, int outMeta, Item input, int inMeta) {
		addUnCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input, 1, inMeta));
	}

	public void addUnCompressedRecipes(Block output, int outAmount, int outMeta, Block input) {
		addUnCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input));
	}

	public void addUnCompressedRecipes(Block output, int outAmount, int outMeta, Block input, int inMeta) {
		addUnCompressedRecipes(new ItemStack(output, outAmount, outMeta), new ItemStack(input, 1, inMeta));
	}
	
/* =============================UnUnCompressed End=============================================================================== */

/* =============================Recipes Start=============================================================================== */
	
	public void addRecipes(ItemStack output, ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5, ItemStack input6, ItemStack input7, ItemStack input8, ItemStack input9) {
		ResourceLocation recipeName = new ResourceLocation(output.getItem().getRegistryName().toString() + "." + output.getMetadata());
		ResourceLocation recipeGroup = new ResourceLocation(output.getItem().getRegistryName().toString());

		GameRegistry.addShapedRecipe(
			recipeName,
			recipeGroup,
			output,
			"ABC",
			"DEF",
			"GHI",
			Character.valueOf('A'), input1,
			Character.valueOf('B'), input2,
			Character.valueOf('C'), input3,
			
			Character.valueOf('D'), input4,
			Character.valueOf('E'), input5,
			Character.valueOf('F'), input6,
			
			Character.valueOf('G'), input7,
			Character.valueOf('H'), input8,
			Character.valueOf('I'), input9
		);
	}

	public void addCompressedRecipes(ItemStack output, ItemStack input) {
		ResourceLocation recipeName = new ResourceLocation("compressed" + output.getItem().getRegistryName().toString() + "." + output.getMetadata());
		ResourceLocation recipeGroup = new ResourceLocation("compressed" + output.getItem().getRegistryName().toString());

		GameRegistry.addShapedRecipe(
				recipeName,
				recipeGroup,
				output,
				"BBB",
				"BBB",
				"BBB",
				Character.valueOf('B'), input
		);
	}
	
	public void addUnCompressedRecipes(ItemStack output, ItemStack input) {
		ResourceLocation recipeName = new ResourceLocation("unCompressed" + output.getItem().getRegistryName().toString() + "." + output.getMetadata());
		ResourceLocation recipeGroup = new ResourceLocation("unCompressed" + output.getItem().getRegistryName().toString());

		GameRegistry.addShapedRecipe(
				recipeName,
				recipeGroup,
				output,
				"B",
				Character.valueOf('B'), input
		);
	}
/* =============================Recipes End=============================================================================== */
}
