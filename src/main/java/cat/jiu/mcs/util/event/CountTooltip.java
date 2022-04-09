package cat.jiu.mcs.util.event;

import cat.jiu.mcs.blocks.net.client.gui.GUICompressor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber
public class CountTooltip {
	@SubscribeEvent
	public static void addCountTooltip(ItemTooltipEvent event) {
		if(GuiScreen.isShiftKeyDown() && event.getItemStack().getCount() >= 1000 && Minecraft.getMinecraft().currentScreen instanceof GUICompressor) {
			event.getToolTip().add(I18n.format("info.mcs.count") + ": " + event.getItemStack().getCount());
		}
	}
}
