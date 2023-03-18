package cat.jiu.mcs.blocks.net;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.msg.*;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MCS.MODID);
	private static int ID = 0;
	private static int nextID() {
		return ID++;
	}

	public static void registerMessages() {
		INSTANCE.registerMessage(MsgCompressorCount::handler, MsgCompressorCount.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(MsgCompressorSlotActivate::handler, MsgCompressorSlotActivate.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(MsgCompressorChest::handler, MsgCompressorChest.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(MsgCompressorEnergy::handler, MsgCompressorEnergy.class, nextID(), Side.CLIENT);
		INSTANCE.registerMessage(MsgCompressorClientEnergy::handler, MsgCompressorClientEnergy.class, nextID(), Side.SERVER);
	}
}
