package cat.jiu.mcs.util;

import java.util.List;

import cat.jiu.mcs.MultipleCompressedStuffs;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT)
public class RegisterModel {
	
	// 从IHasModel接口获取需要注册的物品
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		MultipleCompressedStuffs.startmodel = System.currentTimeMillis();
		modelRegisterHelper(MCSItems.ITEMS);
		modelRegisterHelper(MCSBlocks.BLOCKS);
		modelRegisterHelper(MCSBlocks.BLOCKS0);
		for (Item item : MCSItems.ITEMS) {
			if (item instanceof IHasModel) {
				((IHasModel) item).registerItemModel();
			}
		}
		
		for (Block block : MCSBlocks.SUB_BLOCKS) {
			if (block instanceof IHasModel) {
				((IHasModel) block).registerItemModel();
			}
		}
		
		for (Block block : MCSBlocks.BLOCKS0) {
			if (block instanceof cat.jiu.core.api.IHasModel) {
				((cat.jiu.core.api.IHasModel) block).getItemModel();
			}
		}
		
		MultipleCompressedStuffs.startmodel = (System.currentTimeMillis() - MultipleCompressedStuffs.startmodel);
	}
	
	private static <T> void modelRegisterHelper(List<T> list) {
		for (T element : list) {
			if (element instanceof IHasModel) {
				((IHasModel) element).registerItemModel();
			}
		}
		
		for (T element : list) {
			if (element instanceof cat.jiu.core.api.IHasModel) {
				((cat.jiu.core.api.IHasModel) element).getItemModel();
			}
		}
	}
}
