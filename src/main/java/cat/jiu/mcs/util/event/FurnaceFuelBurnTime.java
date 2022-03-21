package cat.jiu.mcs.util.event;

import cat.jiu.mcs.util.MCSUtil;

import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class FurnaceFuelBurnTime {
	@SubscribeEvent
	public static void setBurnTime(FurnaceFuelBurnTimeEvent event) {
		int baseTime = MCSUtil.item.getUnCompressedBurnTime(event.getItemStack().getItem());
		
		if(baseTime > 0) {
			int level = event.getItemStack().getMetadata() + 1;
			event.setBurnTime((int) (baseTime + (baseTime * (level * 0.794))));
			return;
		}
	}
}
