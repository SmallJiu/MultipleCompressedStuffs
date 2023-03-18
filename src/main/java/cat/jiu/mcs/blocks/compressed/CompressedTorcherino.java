package cat.jiu.mcs.blocks.compressed;

import java.util.Random;

import javax.annotation.Nullable;

import com.sci.torcherino.TorcherinoRegistry;

import cat.jiu.mcs.blocks.tileentity.TileEntityCompressedTorcherino;
import cat.jiu.mcs.util.base.sub.BaseCompressedBlock;
import cat.jiu.mcs.util.init.MCSCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CompressedTorcherino extends BaseCompressedBlock {
	public CompressedTorcherino(String nameIn, ItemStack unCompressedItem) {
		super(nameIn, unCompressedItem, "torcherino", MCSCreativeTab.BLOCKS);
		this.setInfoStack(new ItemStack(Items.AIR));
		super.setTickRandomly(true);
		super.setNotNeedToolBreak();
		super.setModelState("normal");
		TorcherinoRegistry.blacklistBlock(this);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		Block b = super.getBaseBlock();
		if(b != null) {
			b.onBlockAdded(world, pos, super.getBaseState());
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		Block b = super.getBaseBlock();
		if(b != null) {
			b.neighborChanged(super.getBaseState(), worldIn, pos, blockIn, fromPos);
		}
	}
	
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		Block b = super.getBaseBlock();
		if(b != null) {
			return b.canPlaceBlockAt(worldIn, pos);
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		Block b = super.getBaseBlock();
		if(b != null) {
			b.randomDisplayTick(super.getBaseState(), worldIn, pos, rand);
		}
	}
	
	protected static final AxisAlignedBB STANDING_AABB = new AxisAlignedBB(0.4000000059604645D, 0.0D, 0.4000000059604645D, 0.6000000238418579D, 0.6000000238418579D, 0.6000000238418579D);
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return STANDING_AABB;
	}
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        return NULL_AABB;
    }

	@Override
	public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

	@Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCompressedTorcherino(meta);
	}
}
