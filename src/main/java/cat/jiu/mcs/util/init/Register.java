package cat.jiu.mcs.util.init;

import cat.jiu.mcs.MCS;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = MCS.MODID)
public class Register {
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(MCSBlocks.BLOCKS.toArray(new Block[0]));
		event.getRegistry().registerAll(MCSBlocks.BLOCKS0.toArray(new Block[0]));
	}
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(MCSItems.ITEMS.toArray(new Item[0]));
	}
}
