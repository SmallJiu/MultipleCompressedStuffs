package cat.jiu.mcs.blocks.net;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorCount;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static SimpleNetworkWrapper INSTANCE;
	private static int ID = 0;
	private static int nextID() {return ID++;}
	
	public static void registerMessages() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MCS.MODID);
        INSTANCE.registerMessage(MsgCompressorCount::handler, MsgCompressorCount.class, nextID(), Side.SERVER);
    }
}
