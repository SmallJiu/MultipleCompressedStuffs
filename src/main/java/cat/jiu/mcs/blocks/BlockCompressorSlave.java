package cat.jiu.mcs.blocks;

import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressorSlave;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCompressorSlave extends BaseBlock.Normal implements ITileEntityProvider {
	public BlockCompressorSlave() {
		super(MCS.MODID, "compressor_slave", Material.ANVIL, SoundType.METAL, CreativeTabs.TRANSPORTATION, 10F);
		this.setBlockModelResourceLocation(MCS.MODID + "/block", this.name);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCompressorSlave();
	}

	@Override
	public ItemBlock getRegisterItemBlock() {
		return new ItemBlock(this);
	}
}
