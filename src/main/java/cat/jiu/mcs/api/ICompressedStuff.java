package cat.jiu.mcs.api;

import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.CompressedLevel;
import cat.jiu.mcs.util.base.sub.BaseCompressedBlock;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
	
	default BaseCompressedBlock getAsCompressedBlock() {
		return (BaseCompressedBlock) this.getAsBlock();
	}
	
	default boolean isItem() {
		return this instanceof Item;
	}
	
	default boolean isBlock() {
		return this instanceof Block || this instanceof ItemBlock;
	}
	
	default Item getAsItem() {
		return this.isItem() ? (Item)this : this.isBlock() ? Item.getItemFromBlock(this.getAsBlock()) : null;
	}
	
	default Block getAsBlock() {
		return this instanceof Block ? (Block)this : this instanceof ItemBlock ? ((ItemBlock)this).getBlock() : null;
	}
	
	default boolean isStuff() {
		return this.isItem() || this.isBlock();
	}
	
	default ItemStack getStack() {
		return this.getLevel().getStack(0);
	}

	default ItemStack getStack(int meta) {
		return this.getLevel().getStack(meta);
	}

	default ItemStack getStack(int count, int meta) {
		if(!this.isStuff()) return null;
		if(count == 1) {
			return this.getLevel().getStack(meta);
		}else {
			return new ItemStack(this.getAsItem(), count, meta);
		}
	}

	default List<String> getOtherOreDictionary() {
		return Lists.newArrayList();
	}

	default String getDefaultOreDictionary(int meta) {
		if(!this.canMakeDefaultStackRecipe() || !Configs.use_default_oredict) {
			return null;
		}
		return "compressed" + (meta + 1) + "x" + this.getUnCompressedName();
	}

	default boolean createOreDictionary() {
		return !(this instanceof ItemTool);
	}
}
