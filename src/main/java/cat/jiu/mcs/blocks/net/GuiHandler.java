package cat.jiu.mcs.blocks.net;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.client.gui.*;
import cat.jiu.mcs.blocks.net.container.*;
import cat.jiu.mcs.blocks.tileentity.*;

import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class GuiHandler implements IGuiHandler {
	public static final int COMPRESSOR = 1;
	public static final int CHEST_SCROOL_GUI = 2;

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
			case CHEST_SCROOL_GUI:
				if(te instanceof TileEntityCompressedChest) {
					return new ContainerCompressedChest(player, world, pos);
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
			case CHEST_SCROOL_GUI:
				if(te instanceof TileEntityCompressedChest) {
					return new GUICompressedChest(player, world, pos);
				}
		}

		return null;
	}
}
