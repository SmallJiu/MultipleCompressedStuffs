package cat.jiu.mcs.util.event;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import cat.jiu.core.api.events.player.IPlayerPlaceBlock;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.util.init.MCSBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OtherModBlockChange implements IPlayerPlaceBlock {
	@Override
	public void onPlayerPlaceBlock(EntityPlayer player, BlockPos pos, World world, IBlockState placedBlock, IBlockState placeedAgainst) {
		if(world.isRemote) return;
		String name = placedBlock.getBlock().getRegistryName().toString();
		int meta = JiuUtils.item.getMetaFromBlockState(placedBlock);
		if(MCSBlocks.CHANGE_OTHER_BLOCK_MAP.containsKey(name)) {
			if(MCSBlocks.CHANGE_OTHER_BLOCK_MAP.get(name).containsKey(meta)) {
				world.setTileEntity(pos, new TileEntityChangeBlock(MCSBlocks.CHANGE_OTHER_BLOCK_MAP.get(name).get(meta)));
			}
		}
		TileEntity tileentity = world.getTileEntity(pos);
		if(tileentity != null && tileentity instanceof TileEntityChangeBlock) {
			String log = JiuUtils.day.getDate() + " Player: " + player.getName() + " Place [" + name + "@" + meta + "] at" + " Dim: " + player.dimension + ", DimName: " + world.provider.getDimensionType().getName().toLowerCase() + ", " + pos;
			MCS.getLogOS().info(log);
			
			this.writeLog("logs/jiu/" + MCS.MODID + "/" + "mcs_server.log", log);
		}
	}
	
	private void writeLog(String path, String name) {
		File filepath = new File(path);

		if(!filepath.exists()) {
			filepath.mkdirs();
		}
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filepath, true));
			out.write(name + "\n");
			out.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
