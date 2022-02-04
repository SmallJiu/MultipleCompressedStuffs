//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.blocks.compressed;

import cat.jiu.mcs.blocks.tileentity.TileEntityCreativeEnergy;
import cat.jiu.mcs.util.base.BaseBlockSub;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class CompressedCreativeRFSource extends BaseBlockSub {
	public CompressedCreativeRFSource(String nameIn, ItemStack unCompressedItem) {
		super(nameIn, unCompressedItem, "draconicevolution");
		this.setInfoStack(new ItemStack(Items.AIR));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCreativeEnergy((meta + 1) * 3);
	}
}
