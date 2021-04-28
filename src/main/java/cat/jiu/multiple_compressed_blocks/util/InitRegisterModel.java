package cat.jiu.multiple_compressed_blocks.util;

import java.util.List;

import cat.jiu.multiple_compressed_blocks.MultipleCompressedBlocks;
import cat.jiu.multiple_compressed_blocks.interfaces.IHasModel;
import cat.jiu.multiple_compressed_blocks.server.init.InitBlock;
import cat.jiu.multiple_compressed_blocks.server.init.InitItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT)
public class InitRegisterModel {
	
	// 创建/注册所有在ModItems(物品集合)里的物品对象
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(InitItem.ITEMS.toArray(new Item[0]));
	}
	
	// 创建/注册所有在ModBlocks(方块集合)里的方块对象
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(InitBlock.BLOCKS.toArray(new Block[0]));
	}
	
	// 从IHasModel接口获取需要注册的物品
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		modelRegisterHelper(InitItem.ITEMS);
		modelRegisterHelper(InitBlock.BLOCKS);
		for (Item item : InitItem.ITEMS) {
			if (item instanceof IHasModel) {
				((IHasModel) item).regItemModel();
			}
		}
		for (Block block : InitBlock.BLOCKS) {
			if (block instanceof IHasModel) {
				((IHasModel) block).regItemModel();
			}
		}
	}
	
	private static <T> void modelRegisterHelper(List<T> list) {
		for (T element : list) {
			if (element instanceof IHasModel) {
				((IHasModel) element).regItemModel();
			}
		}
	}
	
	// 物品模型快捷注册,模型文件默认丢到items文件夹下
	public static void itemModelRegister(Item item) {
		register(item);
	}
	
	// 不含有meta的物品模型注册，模型文件路径可自定义
	public static void registerItemModel(Item item, String name, String resname) {
		register(item, 0, MultipleCompressedBlocks.MODID + ":" + name + "/" + resname, "inventory");
	}
	
	// 含有meta的物品模型注册，模型文件路径可自定义
	public static void registerItemModel(Item item, int meta, String resname, String name) {
		register(item, meta, MultipleCompressedBlocks.MODID + ":" + resname + "/" + name, "inventory");
	}
	
	// 方块模型快捷注册,模型文件默认丢到blocks文件夹下
	public static void registerItemModel(Block block) {
		register(Item.getItemFromBlock(block), 0, "inventory");
	}
	
	// 不含有meta方块的模型注册，模型文件路径可自定义
	public static void registerItemModel(Block block, String name, String resname) {
		register(Item.getItemFromBlock(block), 0, MultipleCompressedBlocks.MODID + ":" + name + "/" + resname, "inventory");
	}
	
	// 含有meta方块的模型注册，模型文件路径可自定义
	public static void registerItemModel(Block block, int meta, String name, String resname) {
		register(Item.getItemFromBlock(block), meta, MultipleCompressedBlocks.MODID + ":" + name + "/" + resname, "inventory");
	}
	
	private static void register(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private static void register(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}
	
	private static void register(Item item, int meta, String pathName, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(pathName), id));
	}
}
