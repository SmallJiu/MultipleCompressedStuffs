package cat.jiu.mcs.util.init;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.util.base.sub.*;
import cat.jiu.mcs.util.base.sub.tool.*;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public final class MCSResources {
	private static final HashMap<ItemStack, ICompressedStuff> compressed_stuff = Maps.newHashMap();
	public static ICompressedStuff putCompressedStuff(ItemStack unCompressed, ICompressedStuff stuff) {
		if(unCompressed == null || stuff == null) throw new NullPointerException();
		return compressed_stuff.put(unCompressed, stuff);
	}
	public static boolean hasCompressedStuff(ItemStack key) {
		for(ItemStack base : compressed_stuff.keySet()) {
			if(JiuUtils.item.equalsStack(key, base)) {
				return true;
			}
		}
		return false;
	}
	public static ICompressedStuff getCompressedStuff(ItemStack key) {
		for(ItemStack base : compressed_stuff.keySet()) {
			if(JiuUtils.item.equalsStack(key, base)) {
				return compressed_stuff.get(base);
			}
		}
		return null;
	}
	
	public static Collection<ICompressedStuff> getStuffs() {
		return Collections.unmodifiableCollection(compressed_stuff.values());
	}
	
	public static final List<Item> ITEMS = new ArrayList<>();
	public static final List<Block> BLOCKS = new ArrayList<>();
	
	public static final List<String> STUFF_NAME = new ArrayList<>();
	public static final List<String> ITEMS_NAME = new ArrayList<>();
	public static final List<String> BLOCKS_NAME = new ArrayList<>();
	
	public static List<BaseCompressedItem> getNormals() {
		List<BaseCompressedItem> items = Lists.newArrayList();
		compressed_stuff.entrySet().stream()
			.filter(e -> e.getValue() instanceof BaseCompressedItem)
			.forEach(e -> items.add(e.getValue().getAsCompressedItem()));
		return items;
	}
	public static List<BaseCompressedBlock> getNormalBlocks() {
		List<BaseCompressedBlock> items = Lists.newArrayList();
		compressed_stuff.entrySet().stream()
			.filter(e -> e.getValue().isBlock())
			.forEach(e -> items.add(e.getValue().getAsCompressedBlock()));
		return items;
	}
	public static List<Block> getBlocks() {
		List<Block> items = Lists.newArrayList();
		compressed_stuff.entrySet().stream()
			.filter(e -> e.getValue().isBlock())
			.forEach(e -> items.add(e.getValue().getAsBlock()));
		return items;
	}
	public static List<Item> getItems() {
		List<Item> items = Lists.newArrayList();
		compressed_stuff.entrySet().stream()
			.filter(e -> e.getValue().isItem())
			.forEach(e -> items.add(e.getValue().getAsItem()));
		return items;
	}
	public static List<BaseCompressedFood> getFoods() {
		List<BaseCompressedFood> items = Lists.newArrayList();
		ITEMS.stream()
			.filter(e -> e instanceof BaseCompressedFood)
			.forEach(e -> items.add((BaseCompressedFood) e));
		return items;
	}
	public static List<BaseCompressedSword> getSwords() {
		List<BaseCompressedSword> items = Lists.newArrayList();
		ITEMS.stream()
			.filter(e -> e instanceof BaseCompressedSword)
			.forEach(e -> items.add((BaseCompressedSword) e));
		return items;
	}
	public static List<BaseCompressedPickaxe> getPickaxes() {
		List<BaseCompressedPickaxe> items = Lists.newArrayList();
		ITEMS.stream()
			.filter(e -> e instanceof BaseCompressedPickaxe)
			.forEach(e -> items.add((BaseCompressedPickaxe) e));
		return items;
	}
	public static List<BaseCompressedAxe> getAxes() {
		List<BaseCompressedAxe> items = Lists.newArrayList();
		ITEMS.stream()
			.filter(e -> e instanceof BaseCompressedAxe)
			.forEach(e -> items.add((BaseCompressedAxe) e));
		return items;
	}
	public static List<BaseCompressedShovel> getShovels() {
		List<BaseCompressedShovel> items = Lists.newArrayList();
		ITEMS.stream()
			.filter(e -> e instanceof BaseCompressedShovel)
			.forEach(e -> items.add((BaseCompressedShovel) e));
		return items;
	}
	public static List<BaseCompressedHoe> getHoes() {
		List<BaseCompressedHoe> items = Lists.newArrayList();
		ITEMS.stream()
			.filter(e -> e instanceof BaseCompressedHoe)
			.forEach(e -> items.add((BaseCompressedHoe) e));
		return items;
	}
	public static List<BaseItemTool.MetaTool> getTools() {
		List<BaseItemTool.MetaTool> items = Lists.newArrayList();
		items.addAll(getSwords());
		items.addAll(getPickaxes());
		items.addAll(getShovels());
		items.addAll(getAxes());
		items.addAll(getHoes());
		return items;
	}
}
