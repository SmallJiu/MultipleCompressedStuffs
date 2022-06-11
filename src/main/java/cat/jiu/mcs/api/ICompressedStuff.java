package cat.jiu.mcs.api;

import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.CompressedLevel;
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
	
	CompressedLevel getLevel();
	
	default boolean isHas() {return false;}

	default int getUnCompressedBurnTime() {
		return TileEntityFurnace.getItemBurnTime(this.getUnCompressedStack());
	}
	
	default boolean isItem() {
		return this instanceof Item;
	}
	
	default boolean isBlock() {
		return this instanceof Block;
	}
	
	default Item getItem() {
		return this.isItem() ? (Item)this : this.isBlock() ? Item.getItemFromBlock(this.getBlock()) : null;
	}
	
	default Block getBlock() {
		return this.isBlock() ? (Block)this : null;
	}
	
	default boolean isStuff() {
		return this.isItem() || this.isBlock();
	}
	
	default ItemStack getStack() {
		return this.getStack(0);
	}

	default ItemStack getStack(int meta) {
		return this.getStack(1, meta);
	}

	default ItemStack getStack(int count, int meta) {
		if(!this.isStuff()) return null;
		if(count == 1) {
			return this.getLevel().getStack(meta);
		}else {
			return new ItemStack(this.getItem(), count, meta);
		}
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
