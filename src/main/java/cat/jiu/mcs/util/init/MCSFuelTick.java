package cat.jiu.mcs.util.init;

import cat.jiu.core.util.JiuUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class MCSFuelTick {
	@SubscribeEvent
	public static void setFurnaceFuelTick(FurnaceFuelBurnTimeEvent event) {
		if(JiuUtils.item.equalsStack(event.getItemStack(), new ItemStack(MCSBlocks.minecraft.normal.C_COAL_B), false)) {
			event.setBurnTime((int) (ForgeEventFactory.getItemBurnTime(MCSBlocks.minecraft.normal.C_COAL_B.getUnCompressedItemStack()) * ((0 + 1) * 0.3)));
		}
//		event.setBurnTime(event.getItemStack().getItem().getItemBurnTime(event.getItemStack()));
//		
//		event.setCanceled(false);
		/*
		for(BaseBlockSub block : MCSBlocks.SUB_BLOCKS) {
			if(block.canSetBurnTime()) {
				for(int i = 0; i < 15; ++i) {
					ItemStack stack = new ItemStack(block, 1, i);
					
					if(JiuUtils.item.equalsStack(event.getItemStack(), stack, false)) {
						event.setBurnTime((int) (ForgeEventFactory.getItemBurnTime(block.getUnCompressedItemStack()) * ((i + 1) * 0.3)));
					}
				}
			}
		}
		*/
	}
}
