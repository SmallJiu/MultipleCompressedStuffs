package cat.jiu.mcs.api.recipe;

import java.util.List;

import cat.jiu.mcs.api.ICompressedRecipe;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.recipes.MCSRecipeUtils;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedAxe;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedHoe;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedPickaxe;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedShovel;
import cat.jiu.mcs.util.base.sub.tool.BaseCompressedSword;

import net.minecraft.item.ItemStack;

public interface IToolRecipe extends ICompressedRecipe {
	@Override
	default void createRecipe(MCSRecipeUtils tool, ICompressedStuff stuff, int meta) {
		if(canCreateRecipe(meta)) {
			ItemStack rod = this.getRod(meta);
			ItemStack material = this.getMaterial(meta); 
			ItemStack out = stuff.getStack(meta);
			
			List<String> materialOre = MCSUtil.item.getOreDict(material);
			List<String> rodOre = MCSUtil.item.getOreDict(rod);
			
			if(materialOre.isEmpty() && rodOre.isEmpty()) {
				tool.addSwordRecipes(out, material, rod);
			}else if(materialOre.isEmpty()) {
				for(String rodore : rodOre) {
					addToolRecipe(tool, this.getType(), out, material, rodore);
				}
			}else if(rodOre.isEmpty()) {
				for(String materialore : materialOre) {
					addToolRecipe(tool, this.getType(), out, materialore, rod);
				}
			}else {
				for(String rodore : rodOre) {
					for(String materialore : materialOre) {
						addToolRecipe(tool, this.getType(), out, materialore, rodore);
					}
				}
			}
		}
	}
	
	static void addToolRecipe(MCSRecipeUtils tool, ToolType type, ItemStack out, Object material, Object rod) {
		switch(type) {
			case SWORD:
				tool.addSwordRecipes(out, material, rod);
				break;
			case PICKAXE:
				tool.addPickaxeRecipes(out, material, rod);
				break;
			case AXE:
				tool.addAxeRecipes(out, material, rod);
				break;
			case SHOVEL:
				tool.addShovelRecipes(out, material, rod);
				break;
			case HOE:
				tool.addHoeRecipes(out, material, rod);
				break;
			default:break;
		}
	}
	default boolean canCreateRecipe(int meta) {
		return true;
	}
	ItemStack getMaterial(int meta);
	ItemStack getRod(int meta);
	default ToolType getType() {
		if(this instanceof BaseCompressedSword) return ToolType.SWORD;
		if(this instanceof BaseCompressedPickaxe) return ToolType.PICKAXE;
		if(this instanceof BaseCompressedShovel) return ToolType.SHOVEL;
		if(this instanceof BaseCompressedAxe) return ToolType.AXE;
		if(this instanceof BaseCompressedHoe) return ToolType.HOE;
		return null;
	}
	enum ToolType {
		SWORD, PICKAXE, AXE, SHOVEL, HOE;
	}
}
