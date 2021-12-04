package cat.jiu.mcs.blocks.net;

import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cat.jiu.mcs.MCS;
import net.minecraft.util.math.BlockPos;

public class GuiHandler implements IGuiHandler {
	
	public static final int COMPRESSOR = 1;
	
	public static void register() {
		NetworkRegistry.INSTANCE.registerGuiHandler(MCS.MODID, new GuiHandler());
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x,y,z);
		switch (ID) {
			default:return null;
			case COMPRESSOR: return new ContainerCompressor(player, world, pos);
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x,y,z);
		switch (ID) {
			default:return null;
			case COMPRESSOR: return new GUICompressor(player, world, pos);
		}
	}
}
