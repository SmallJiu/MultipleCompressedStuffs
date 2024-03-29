package cat.jiu.mcs.util.client.waila;

import cat.jiu.core.util.WailaPluginRegister;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import mcp.mobius.waila.cbcore.Layout;

public class WailaPluginRegistry {
	public static void register() {
		WailaPluginRegister.addWailaPlugin(Layout.BODY, new ChangeBlockPlugin(), TileEntityChangeBlock.class, true);
		WailaPluginRegister.addWailaPlugin(Layout.BODY, new CompressorPlugin(), TileEntityCompressor.class, true);
	}
}
