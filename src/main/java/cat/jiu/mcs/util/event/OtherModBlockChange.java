//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.util.event;

import java.util.Map;

import cat.jiu.core.api.events.player.IPlayerPlaceBlock;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.type.ChangeBlockType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OtherModBlockChange implements IPlayerPlaceBlock{
	@Override
	public void onPlayerPlaceBlock(EntityPlayer player, BlockPos pos, World world, IBlockState placedBlock, IBlockState placeedAgainst) {
		String name = placedBlock.getBlock().getRegistryName().toString();
		if(MCSBlocks.CHANGE_OTHER_BLOCK_MAP.containsKey(name)) {
			int meta = JiuUtils.item.getMetaFormBlockState(placedBlock);
			Map<Integer, ChangeBlockType> entrys = MCSBlocks.CHANGE_OTHER_BLOCK_MAP.get(name);
			
//			int[] time = entrys.get(meta).getTime();
//			MCS.instance.log.info("M:" + time[2] + " S:" + time[1] + " Tick:" + time[0]);
			
			TileEntityChangeBlock te = new TileEntityChangeBlock(meta, entrys);
			world.setTileEntity(pos, te);
		}
	}
}
