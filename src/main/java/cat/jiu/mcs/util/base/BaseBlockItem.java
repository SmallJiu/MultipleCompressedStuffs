package cat.jiu.mcs.util.base;

import cat.jiu.mcs.api.ICompressedStuff;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseBlockItem extends ItemBlock implements ICompressedStuff {
	protected final BaseBlock subBlock;

	public BaseBlockItem(BaseBlock block) {
		super(block);
		this.subBlock = block;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	public int getMetadata() {
		return this.subBlock.getUnCompressedStack().getMetadata();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(this.getUnCompressedStack().getItem() == Items.AIR) {
			return super.getItemStackDisplayName(stack);
		}else {
			if(this.getUnCompressedStack().getItem() instanceof ItemBlock) {
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
	
	@SideOnly(Side.CLIENT)
	public String getUnCompressedItemLocalizedName() {
		return this.subBlock.getUnCompressedItemLocalizedName();
	}

	public ItemStack getUnCompressedStack() {
		return this.subBlock.getUnCompressedStack();
	}
	
	public boolean canMakeDefaultStackRecipe() {
		return this.subBlock.canMakeDefaultStackRecipe();
	}

	@Override
	public String getOwnerMod() {
		return this.subBlock.getOwnerMod();
	}

	@Override
	public String getUnCompressedName() {
		return this.subBlock.getUnCompressedName();
	}
}
