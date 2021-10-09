package cat.jiu.mcs.util.base;

import cat.jiu.mcs.MCS;
import cat.jiu.core.util.JiuUtils;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseBlockItem extends ItemBlock {
	private ItemStack unCompressedItem = new ItemStack(Items.AIR);
	
	public BaseBlockItem(Block block, ItemStack unCompressedItem) {
		super(block);
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
		if(this.unCompressedItem.equals(new ItemStack(Items.AIR))) {
			return super.getItemStackDisplayName(stack);
		}else {
			if(this.getBlock() instanceof BaseBlockNormal) {
				return I18n.format(super.getUnlocalizedName());
			}else if(this.unCompressedItem.getItem() instanceof ItemBlock) {
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
		if(!this.unCompressedItem.equals(new ItemStack(Items.AIR))) {
			String itemmodid = this.unCompressedItem.getItem().getCreatorModId(this.unCompressedItem);
			
			if(JiuUtils.other.containKey(MCS.other_mod, itemmodid)) {
				return I18n.format(this.unCompressedItem.getUnlocalizedName() + ".name", 1).trim();
			}else {
				return I18n.format(this.unCompressedItem.getUnlocalizedName(), 1).trim();
			}
		}else {
			return "\'Unknown Item\'";
		}
	}
}
