package cat.jiu.mcs.blocks.net;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorScroolChest;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorCount;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorEnergy;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorPageChest;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorSlotActivate;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static SimpleNetworkWrapper INSTANCE;
	private static int ID = 0;

	private static int nextID() {
		return ID++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MCS.MODID);
		INSTANCE.registerMessage(MsgCompressorCount::handler, MsgCompressorCount.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(MsgCompressorSlotActivate::handler, MsgCompressorSlotActivate.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(MsgCompressorScroolChest::handler, MsgCompressorScroolChest.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(MsgCompressorPageChest::handler, MsgCompressorPageChest.class, nextID(), Side.SERVER);
		INSTANCE.registerMessage(MsgCompressorEnergy::handler, MsgCompressorEnergy.class, nextID(), Side.CLIENT);
		
	}
}
