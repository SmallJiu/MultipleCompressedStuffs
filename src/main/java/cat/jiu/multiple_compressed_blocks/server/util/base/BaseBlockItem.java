package cat.jiu.multiple_compressed_blocks.server.util.base;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BaseBlockItem extends ItemBlock {
	private final Block unCompressedBlock;
	
	public BaseBlockItem(Block block, Block unCompressedBlock) {
		super(block);
		this.unCompressedBlock = unCompressedBlock;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int damage) {
		return damage;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("tile.mcb.compressed_" + stack.getMetadata() + ".name", 1) + this.unCompressedBlock.getLocalizedName();
	}
}
