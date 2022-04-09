package cat.jiu.mcs.blocks.net;

import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.client.gui.GUICompressor;
import cat.jiu.mcs.blocks.net.container.ContainerCompressor;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;
import net.minecraft.util.math.BlockPos;

public class GuiHandler implements IGuiHandler {
	public static final int COMPRESSOR = 1;

	public static void register() {
		NetworkRegistry.INSTANCE.registerGuiHandler(MCS.MODID, new GuiHandler());
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		switch(ID) {
			case COMPRESSOR:
				if(te instanceof TileEntityCompressor) {
					return new ContainerCompressor(player, world, pos);
				}
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		switch(ID) {
			case COMPRESSOR:
				if(te instanceof TileEntityCompressor) {
					return new GUICompressor(player, world, pos);
				}
		}

		return null;
	}
}
