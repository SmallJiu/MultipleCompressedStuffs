//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.util.client;

import java.awt.Color;

import cat.jiu.mcs.MultipleCompressedStuffs;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;

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
	// 从IHasModel接口获取�?要注册的物品
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		MultipleCompressedStuffs.startmodel = System.currentTimeMillis();
		for(Item item : MCSItems.ITEMS) {
			if(item instanceof IHasModel) {
				((IHasModel) item).registerItemModel();
			}
		}
		
		for(Block block : MCSBlocks.SUB_BLOCKS) {
			if(block instanceof IHasModel) {
				((IHasModel) block).registerItemModel();
			}
		}
		
		for(Block block : MCSBlocks.BLOCKS0) {
			if(block instanceof cat.jiu.core.api.IHasModel) {
				((cat.jiu.core.api.IHasModel) block).getItemModel();
			}
		}
		
		MultipleCompressedStuffs.startmodel = (System.currentTimeMillis() - MultipleCompressedStuffs.startmodel);
	}
	
	@SubscribeEvent
	public static void itemColors(ColorHandlerEvent.Item event) {
	    event.getItemColors().registerItemColorHandler((ItemStack stack, int tint) -> {
	    	if(tint == 0) {
//	    		return new Color(255, 255, 255).getRGB() - (new Color(16, 16, 16).getRGB() * stack.getMetadata());
	    	}
	    	return new Color(255, 255, 255).getRGB();
	    }, MCSItems.SUB_ITEMS.toArray(new Item[0]));
	}
}
