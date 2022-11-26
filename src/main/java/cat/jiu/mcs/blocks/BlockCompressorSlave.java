package cat.jiu.mcs.blocks;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressorSlave;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Loader;

public class BlockCompressorSlave extends BaseBlock.Normal implements ITileEntityProvider {
	public BlockCompressorSlave() {
		super(MCS.MODID, "compressor_slave", Material.ANVIL, SoundType.METAL, CreativeTabs.TRANSPORTATION, 10F);
		this.setBlockModelResourceLocation(MCS.MODID + "/block", this.name);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()) {
			Item item = player.getHeldItem(hand).getItem();
			if(Loader.isModLoaded("thermalfoundation")) {
				if(item instanceof cofh.api.item.IToolHammer) {
					world.setBlockToAir(pos);
					JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.item.getStackFromBlockState(state));
					return true;
				}
			}
		}
		return false;
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
