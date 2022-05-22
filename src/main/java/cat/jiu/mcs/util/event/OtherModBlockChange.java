package cat.jiu.mcs.util.event;

import cat.jiu.core.api.events.player.IPlayerPlaceBlock;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.util.init.MCSBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OtherModBlockChange implements IPlayerPlaceBlock {
	@Override
	public void onPlayerPlaceBlock(EntityPlayer player, BlockPos pos, World world, IBlockState placedBlock, IBlockState placeedAgainst) {
		String name = placedBlock.getBlock().getRegistryName().toString();
		if(MCSBlocks.CHANGE_OTHER_BLOCK_MAP.containsKey(name)) {
			int meta = JiuUtils.item.getMetaFromBlockState(placedBlock);
			world.setTileEntity(pos, new TileEntityChangeBlock(meta, MCSBlocks.CHANGE_OTHER_BLOCK_MAP.get(name)));
		}
	}
}
