package cat.jiu.mcs.api;

import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.mcs.config.Configs;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntityFurnace;

public interface ICompressedStuff {
	ItemStack getUnCompressedStack();

	boolean canMakeDefaultStackRecipe();

	String getOwnerMod();

	String getUnCompressedName();

	default int getUnCompressedBurnTime() {
		return TileEntityFurnace.getItemBurnTime(this.getUnCompressedStack());
	}
	
	default ItemStack getStack() {
		return this.getStack(0);
	}

	default ItemStack getStack(int meta) {
		return this.getStack(1, meta);
	}

	default ItemStack getStack(int count, int meta) {
		return new ItemStack(this instanceof Item ? (Item) this : this instanceof Block ? Item.getItemFromBlock((Block) this) : (Item) null, count, meta);
	}

	default List<String> addOtherOreDictionary() {
		return Lists.newArrayList();
	}

	default String getDefaultOreDictionary(int meta) {
		if(!this.canMakeDefaultStackRecipe() || !Configs.use_default_oredict) {
			return "null_empty";
		}
		return "compressed" + (meta + 1) + "x" + this.getUnCompressedName();
	}

	default boolean createOreDictionary() {
		return !(this instanceof ItemTool);
	}
}
