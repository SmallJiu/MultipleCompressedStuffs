package cat.jiu.multiple_compressed_blocks.util;

import java.util.ArrayList;
import java.util.List;

import cat.jiu.multiple_compressed_blocks.server.init.InitBlock;
import cat.jiu.multiple_compressed_blocks.server.init.InitItem;

import net.minecraft.block.Block;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.oredict.OreDictionary;

public class InitHelper {

	// 快捷写入物品的属性
	public static void itemInit(Item item, int maxdamage) {
		if (maxdamage != 0) {
			item.setMaxDamage(maxdamage);// 如果maxdamage大于0,则设置耐久值为maxdamage的值
		}
		InitItem.ITEMS.add(item);// 添加到物品集合
	}

	// 快捷写入方块的属性
	public static void blockInit(Block block, float resistance) {
		block.setResistance(resistance);// 方块抗爆能力
		InitBlock.BLOCKS.add(block);
	}

	// 发送信息
	public static <T extends ICommandSender> void sendMessage(T sender, String key) {
		sender.sendMessage(new TextComponentTranslation(key, 4));
	}

	/*
	// 发送信息
	public static void sendMessage(ICommandSender sender, String key) {
	  sender.sendMessage(new TextComponentTranslation(key, 4)); 
	}
	*/
	public static void addPotionEffect(EntityPlayer player, Potion potion, int potionTime, int potionLevel){
		player.addPotionEffect(new PotionEffect(potion, potionTime, potionLevel));
	}
	// 修复物品
	public static void fixedItem(ItemStack stack) {
		stack.setItemDamage(stack.getItemDamage() - 1);
	}
	
	public static void fixedItem(ItemStack stack, int damage) {
		stack.setItemDamage(stack.getItemDamage() - damage);
	}

	// 从 ItemStack获取矿物词典
	public static List<String> getOreDict(ItemStack stack) {
		int[] ids = OreDictionary.getOreIDs(stack);
		List<String> names = new ArrayList<>();

		for (int id : ids) {
			names.add(OreDictionary.getOreName(id));
		}

		return names;
	}
}
