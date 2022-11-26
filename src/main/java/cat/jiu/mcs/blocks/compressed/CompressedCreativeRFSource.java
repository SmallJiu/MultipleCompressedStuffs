package cat.jiu.mcs.blocks.compressed;

import cat.jiu.mcs.blocks.tileentity.TileEntityCreativeEnergy;
import cat.jiu.mcs.util.base.sub.BaseCompressedBlock;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CompressedCreativeRFSource extends BaseCompressedBlock {
	public CompressedCreativeRFSource(String name, ItemStack unCompressedItem) {
		super(name, unCompressedItem, "draconicevolution");
		this.setInfoStack(new ItemStack(Items.AIR));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCreativeEnergy((meta + 1) * 3);
	}
}
