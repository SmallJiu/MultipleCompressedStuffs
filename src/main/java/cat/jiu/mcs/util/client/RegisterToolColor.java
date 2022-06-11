package cat.jiu.mcs.util.client;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT)
public class RegisterToolColor {
	static final int[] colors = {Color.RED.getRGB(), Color.ORANGE.getRGB(), Color.YELLOW.getRGB(), Color.GREEN.getRGB(), Color.BLUE.getRGB(), Color.MAGENTA.getRGB(), Color.GRAY.getRGB()};
	static int leg = 0;
	static int tick = 0;
	static final int s = new Color(16, 16, 16).getRGB();
	static final int white = Color.WHITE.getRGB();

	@SubscribeEvent
	public static void itemColors(ColorHandlerEvent.Item event) {
		List<Item> list = Lists.newArrayList();
		MCSResources.ITEMS.stream().filter(e-> e instanceof BaseItemTool.MetaTool).forEach(item -> list.add(item));
		
		event.getItemColors().registerItemColorHandler((ItemStack stack, int tint) -> {
			if(stack.getMetadata() > 15) {
				tick += 1;
				if(tick < 240) return colors[leg];
				
				leg += 1;
				if(leg > colors.length-1) leg = 0;
				
				tick = 0;
				return colors[leg];
			}
			return white - (s * (stack.getMetadata()));
		}, list.toArray(new Item[0]));
	}

//	@SubscribeEvent
	public static void blockColors(ColorHandlerEvent.Block event) {
		
	}
}