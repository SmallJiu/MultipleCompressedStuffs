package cat.jiu.mcs.util.client;

import java.awt.Color;

import cat.jiu.mcs.MultipleCompressedStuffs;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.mcs.util.init.MCSResources;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT)
public class RegisterModel {
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		MultipleCompressedStuffs.startmodel = System.currentTimeMillis();
		for(Item item : MCSResources.ITEMS) {
			if(item instanceof IHasModel) {
				((IHasModel) item).registerItemModel();
				continue;
			}
			if(item instanceof cat.jiu.core.api.IHasModel) {
				((cat.jiu.core.api.IHasModel) item).getItemModel();
				continue;
			}
		}
		
		for(Block block : MCSResources.BLOCKS) {
			if(block instanceof IHasModel) {
				((IHasModel) block).registerItemModel();
				continue;
			}
			if(block instanceof cat.jiu.core.api.IHasModel) {
				((cat.jiu.core.api.IHasModel) block).getItemModel();
				continue;
			}
		}
		MultipleCompressedStuffs.startmodel = (System.currentTimeMillis() - MultipleCompressedStuffs.startmodel);
	}
	
	@SubscribeEvent
	public static void itemColors(ColorHandlerEvent.Item event) {
	    event.getItemColors().registerItemColorHandler((ItemStack stack, int tint) -> {
	    	return Color.WHITE.getRGB() - (new Color(16, 16, 16).getRGB() * (stack.getMetadata()));
	    }, MCSResources.SUB_TOOLS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void blockColors(ColorHandlerEvent.Block event) {
		
	}
}
