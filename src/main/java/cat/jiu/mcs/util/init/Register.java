package cat.jiu.mcs.util.init;

import java.util.stream.Collectors;

import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.mcs.MCS;

import net.minecraft.item.Item;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = MCS.MODID)
public class Register {
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		long i = System.currentTimeMillis();
		event.getRegistry().registerAll(MCSResources.ITEMS.stream().filter(e->!(e instanceof BaseItemTool.MetaTool)).collect(Collectors.toList()).toArray(new Item[0]));
		MCS.proxy.startitem += System.currentTimeMillis() - i;
	}
}
