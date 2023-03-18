package cat.jiu.mcs.util.client;

import java.awt.Color;
import java.util.stream.Collectors;

import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.item.Item;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@EventBusSubscriber(value = Side.CLIENT)
public class ToolsColor {
	static final Color[] WEB_COLOR = {
			Color.RED, new Color(255, 0, 102), new Color(255, 0, 153), new Color(255, 0, 204), Color.MAGENTA, 
			new Color(204, 0, 255), new Color(153, 0, 255), new Color(102, 0, 255), new Color(51, 0, 255), Color.BLUE,
			new Color(0, 51, 255), new Color(0, 102, 255), new Color(0, 153, 255), new Color(0, 204, 255), Color.CYAN,
			new Color(0, 255, 204), new Color(0, 255, 153), new Color(0, 255, 102), new Color(0, 255, 51), Color.GREEN, 
			new Color(51, 255, 0), new Color(102, 255, 0), new Color(153, 255, 0), new Color(204, 255, 0), Color.YELLOW,
			new Color(255, 204, 0), new Color(255, 153, 0), new Color(255, 102, 0), new Color(255, 51, 0)
	};
	static int index = 0;
	
	static {
		new Thread(()->{
			boolean revese = false;
			while(true) {
				try {
					Thread.sleep(100);
					if(index >= WEB_COLOR.length-1) {
						revese = true;
					}else if(index <= 0) {
						revese = false;
					}
					
					if(revese) {
						index--;
					}else {
						index++;
					}
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	@SubscribeEvent
	public static void itemColors(ColorHandlerEvent.Item event) {
		int rgb = 255 / ModSubtypes.MAX;
		int step = new Color(rgb, rgb, rgb).getRGB();
		
		event.getItemColors().registerItemColorHandler((stack, tint) -> {
			if(stack.getMetadata() > 15 && stack.getMetadata() >= ModSubtypes.INFINITY) {
				return WEB_COLOR[index].getRGB();
			}
			return Color.WHITE.getRGB() - (step * (stack.getMetadata()));
		}, MCSResources.getStuffs().stream().filter(e -> e instanceof BaseItemTool.MetaTool).collect(Collectors.toList()).toArray(new Item[0]));
	}
}
