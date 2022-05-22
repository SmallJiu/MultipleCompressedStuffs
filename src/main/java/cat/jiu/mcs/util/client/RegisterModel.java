package cat.jiu.mcs.util.client;

import java.awt.Color;

import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(
	value = Side.CLIENT)
public class RegisterModel {
	static final Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.MAGENTA, Color.GRAY};
	static int leg = 0;
	static int tick = 0;

	@SubscribeEvent
	public static void itemColors(ColorHandlerEvent.Item event) {
		event.getItemColors().registerItemColorHandler((ItemStack stack, int tint) -> {
			if(stack.getMetadata() > 15) {
				tick += 1;
				if(tick < 240) return colors[leg].getRGB();
				
				leg += 1;
				if(leg > colors.length-1) leg = 0;
				
				tick = 0;
				return colors[leg].getRGB();
			}
			return Color.WHITE.getRGB() - (new Color(16, 16, 16).getRGB() * (stack.getMetadata()));
		}, MCSResources.SUB_TOOLS.toArray(new Item[0]));
		
	}

//	@SubscribeEvent
	public static void blockColors(ColorHandlerEvent.Block event) {
		
	}
}
