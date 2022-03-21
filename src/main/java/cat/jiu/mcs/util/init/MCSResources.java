package cat.jiu.mcs.util.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import cat.jiu.mcs.util.base.sub.BaseItemFood;
import cat.jiu.mcs.util.base.sub.BaseItemSub;
import cat.jiu.mcs.util.base.sub.tool.BaseItemAxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemHoe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemPickaxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemShovel;
import cat.jiu.mcs.util.base.sub.tool.BaseItemSword;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class MCSResources {
	public static final List<Item> ITEMS = new ArrayList<>();
	public static final List<String> ITEMS_NAME = new ArrayList<>();
	public static final Map<String, Item> ITEMS_MAP = new HashMap<>();
	
	public static final List<BaseItemFood> FOODS = new ArrayList<>();
	public static final List<String> FOODS_NAME = new ArrayList<>();
	public static final Map<String, BaseItemFood> FOODS_MAP = new HashMap<>();
	
	public static final List<BaseItemSub> SUB_ITEMS = new ArrayList<>();
	public static final List<String> SUB_ITEMS_NAME = new ArrayList<>();
	public static final Map<String, BaseItemSub> SUB_ITEMS_MAP = new HashMap<>();
	
	public static final List<BaseItemTool.MetaTool> SUB_TOOLS = new ArrayList<>();
	public static final List<String> SUB_TOOLS_NAME = new ArrayList<>();
	public static final Map<String, BaseItemTool.MetaTool> SUB_TOOLS_MAP = new HashMap<>();
	
	public static final List<BaseItemSword> SWORDS = new ArrayList<>();
	public static final List<String> SWORDS_NAME = new ArrayList<>();
	
	public static final List<BaseItemPickaxe> PICKAXES = new ArrayList<>();
	public static final List<String> PICKAXES_NAME = new ArrayList<>();
	
	public static final List<BaseItemShovel> SHOVELS = new ArrayList<>();
	public static final List<String> SHOVEL_NAME = new ArrayList<>();
	
	public static final List<BaseItemAxe> AXES = new ArrayList<>();
	public static final List<String> AXES_NAME = new ArrayList<>();
	
	public static final List<BaseItemHoe> HOES = new ArrayList<>();
	public static final List<String> HOES_NAME = new ArrayList<>();
	
	public static final List<Block> BLOCKS = new ArrayList<>();
	public static final Map<String, Block> BLOCKS_MAP = new HashMap<>();
	public static final List<String> BLOCKS_NAME = new ArrayList<>();
	
	public static final List<String> SUB_BLOCKS_NAME = new ArrayList<>();
	public static final List<BaseBlockSub> SUB_BLOCKS = new ArrayList<>();
	public static final Map<String, BaseBlockSub> SUB_BLOCKS_MAP = new HashMap<>();
}
