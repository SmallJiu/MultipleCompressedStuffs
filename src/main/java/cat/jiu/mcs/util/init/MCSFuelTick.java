package cat.jiu.mcs.util.init;

import cat.jiu.mcs.util.MCSUtil;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class MCSFuelTick {
	@SubscribeEvent
	public static void setFurnaceFuelTick(FurnaceFuelBurnTimeEvent event) {
		ItemStack stack = event.getItemStack();
		if(MCSUtil.item.isCompressedItem(stack)) {
			if(TileEntityFurnace.isItemFuel(MCSUtil.item.getUnCompressed(stack))) {
				int baseBurnTime = MCSUtil.item.getUnCompressedBurnTime(stack);
				if(baseBurnTime > 1) {
					double burnTime = baseBurnTime + (baseBurnTime * ((stack.getMetadata() + 1) * 0.794));
					if(burnTime <= 20) {
						return;
					}
					event.setBurnTime((int) burnTime);
				}
			}
		}
	}
}
