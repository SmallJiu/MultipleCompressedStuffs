package cat.jiu.mcs.util.base;

import cat.jiu.mcs.interfaces.ICompressedStuff;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseBlockItem extends ItemBlock implements ICompressedStuff {
	private ItemStack unCompressedItem = new ItemStack(Items.AIR);
	protected BaseBlockSub subBlock = null;
	
	public BaseBlockItem(Block block, ItemStack unCompressedItem) {
		super(block);
		if(block instanceof BaseBlockSub) {
			this.subBlock = (BaseBlockSub) block;
		}
		this.unCompressedItem = unCompressedItem;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	public BaseBlockItem(Block block, boolean hasSubtypes) {
		super(block);
		this.setHasSubtypes(hasSubtypes);
		this.setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	public int getMetadata() {
		return this.unCompressedItem.getMetadata();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(this.unCompressedItem.getItem() == Items.AIR) {
			return super.getItemStackDisplayName(stack);
		}else {
			if(this.unCompressedItem.getItem() instanceof ItemBlock) {
				return I18n.format(I18n.format("tile.mcs.compressed_" + stack.getMetadata() + ".name", 1) + this.getUnCompressedItemLocalizedName(), 1).trim();
			}else {
				if(stack.getMetadata() == 0) {
					return this.getUnCompressedItemLocalizedName() + I18n.format("tile.mcs.block.name", 1);
				}else {
					return I18n.format("tile.mcs.compressed_" + (stack.getMetadata() - 1) + ".name", 1) + this.getUnCompressedItemLocalizedName() + I18n.format("tile.mcs.block.name", 1);
				}
			}
		}
	}
	
	public ItemStack getUnCompressedStack() {
		if(this.subBlock != null) {
			return this.subBlock.getUnCompressedStack();
		}
		return ItemStack.EMPTY;
	}
	
	public int getUnCompressedBurnTime() {
		if(this.subBlock != null) {
			return this.subBlock.getUnCompressedBurnTime();
		}
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
	public String getUnCompressedItemLocalizedName() {
		if(!(this.unCompressedItem.getItem() == Items.AIR)) {
			return this.unCompressedItem.getDisplayName();
		}else {
			return "<Unknown Item>";
		}
	}

	public boolean canMakeDefaultStackRecipe() {
		if(this.subBlock != null) {
			return this.subBlock.canMakeDefaultStackRecipe();
		}
		return false;
	}

	@Override
	public String getOwnerMod() {
		if(this.subBlock != null) {
			return this.subBlock.getOwnerMod();
		}
		return "";
	}
}
