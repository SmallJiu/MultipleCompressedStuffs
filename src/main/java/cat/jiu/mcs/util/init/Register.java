package cat.jiu.mcs.util.init;

import cat.jiu.mcs.MCS;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class Register {
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		long i = System.currentTimeMillis();
		event.getRegistry().registerAll(MCSResources.BLOCKS.toArray(new Block[0]));
		MCS.proxy.startblock += System.currentTimeMillis() - i;
	}

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		long i = System.currentTimeMillis();
		event.getRegistry().registerAll(MCSResources.ITEMS.toArray(new Item[0]));
		MCS.proxy.startitem += System.currentTimeMillis() - i;
	}
}
