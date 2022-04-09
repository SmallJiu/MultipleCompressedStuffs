package cat.jiu.mcs.blocks.compressed;

import cat.jiu.mcs.util.base.sub.BaseBlockSub;

import net.minecraft.item.ItemStack;

public class CompressedTNT extends BaseBlockSub {
	public CompressedTNT(String nameIn, ItemStack unCompressedItem) {
		super(nameIn, unCompressedItem);
		// BlockTNT
	}

	// @Override
	// public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
	// Blocks.TNT.onBlockAdded(worldIn, pos, state);
	// }
	//
	// @Override
	// public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
	// Blocks.TNT.neighborChanged(state, worldIn, pos, blockIn, fromPos);
	// }
	//
	// @Override
	// public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	// return Blocks.TNT.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
	// }
	//
	// @Override
	// public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
	// Blocks.TNT.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
	// }
	//
	// @Override
	// public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
	// Blocks.TNT.onBlockDestroyedByPlayer(worldIn, pos, state);
	// }
	//
	// @Override
	// public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
	// Blocks.TNT.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	// }
}
