package cat.jiu.mcs.util.event;

import cat.jiu.mcs.util.MCSUtil;

import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class FurnaceFuelBurnTime {
	@SubscribeEvent
	public static void setBurnTime(FurnaceFuelBurnTimeEvent event) {
		int baseTime = MCSUtil.item.getUnCompressedBurnTime(event.getItemStack());
		if(baseTime > 0) {
			event.setBurnTime((int) MCSUtil.item.getMetaValue(baseTime, event.getItemStack().getMetadata()));
			return;
		}
	}
}
