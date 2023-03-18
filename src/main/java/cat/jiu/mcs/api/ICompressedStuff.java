package cat.jiu.mcs.api;

import java.util.Collections;
import java.util.List;

import cat.jiu.core.types.StackCaches;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.base.sub.BaseCompressedBlock;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;
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
	
	StackCaches getLevel();
	
	default void checkUnCompressed() {
		
	}
	
	default boolean isHas() {return false;}

	default int getUnCompressedBurnTime() {
		return TileEntityFurnace.getItemBurnTime(this.getUnCompressedStack());
	}
	
	default BaseCompressedBlock getAsCompressedBlock() {
		return (BaseCompressedBlock) this.getAsBlock();
	}
	
	default BaseCompressedItem getAsCompressedItem() {
		return (BaseCompressedItem) this.getAsItem();
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
		return this.getStack(0);
	}

	default ItemStack getStack(int meta) {
		return this.getStack(1, meta);
	}

	default ItemStack getStack(int count, int meta) {
		if(!this.isStuff()) throw new UnsupportedOperationException(String.format("%s not a Item or Block!", this.getClass()));
		ItemStack stack = null;
		do {
			if(count == 1) {
				if(meta > 15 && this.isBlock()) {
					stack = this.getLevel().get(15);
					break;
				}
				if(meta < 0) {
					stack = this.getLevel().get(0);
					break;
				}
				if(meta >= ModSubtypes.INFINITY) {
					this.getLevel().put(meta);
					stack = this.getLevel().get(ModSubtypes.INFINITY);
					break;
				}
				if(meta > ModSubtypes.MAX-1) {
					if(this.isBlock() && meta > 15) {
						stack = this.getLevel().get(15);
						break;
					}else if(meta < ModSubtypes.INFINITY) {
						stack = this.getLevel().get(ModSubtypes.MAX-1);
						break;
					}
				}
				stack = this.getLevel().get(meta);
				break;
			}
			stack = new ItemStack(this.getAsItem(), count, meta);
		}while(false);
		
		if(stack==null) {
			throw new IndexOutOfBoundsException(String.format("%s: %s", this.getAsItem().getRegistryName(), meta));
		}
		return stack;
	}

	default List<String> getOtherOreDictionary() {
		return Collections.emptyList();
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
