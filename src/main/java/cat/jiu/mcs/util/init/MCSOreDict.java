package cat.jiu.mcs.util.init;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.helpers.ItemUtils;
import cat.jiu.mcs.config.Configs;

import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class MCSOreDict {
	private static final ItemUtils item = JiuUtils.item;
	
	public static void register() {
		if(Configs.use_default_oredict) {
			try {
				mcBlock();
				if(Configs.Custom.Enable_Mod_Stuff) {
					itemThermalFoundation();
					blockThermalFoundation();
					blockBotania();
				}
				MCSItems.registerOreDict();
				MCSBlocks.registerOreDict();
				custom();
			} catch (Throwable e) {e.printStackTrace();}
		}
	}
	
	private static void mcBlock() {
		item.registerOre("blockConcretePowder", Blocks.CONCRETE_POWDER, OreDictionary.WILDCARD_VALUE);
		item.registerOre("blockConcrete", Blocks.CONCRETE, OreDictionary.WILDCARD_VALUE);
		item.registerOre("hardenedClay", Blocks.STAINED_HARDENED_CLAY, OreDictionary.WILDCARD_VALUE);
	}
	
	private static void itemThermalFoundation() {
		if(Configs.Custom.Enable_Mod_Stuff) {
			if(Loader.isModLoaded("thermalfoundation")) {
				item.registerOre("tfDyes", JiuUtils.item.getItemByNameOrId("thermalfoundation:dye"), OreDictionary.WILDCARD_VALUE);
			}
		}
	}
	
	private static void blockThermalFoundation() {
		if(Configs.Custom.Enable_Mod_Stuff) {
			if(Loader.isModLoaded("thermalfoundation")) {
//				String[] types = new String[] {"copper", "tin", "silver", "lead", "aluminum", "nickel", "platinum", "iridium", "mithril"};
//				for(int i = 0; i < types.length; i++) {
//					item.registerOre("hardenGlass", JiuUtils.item.getItemByNameOrId("thermalfoundation:glass"), i);
//					item.registerOre(types[i] + "HardenedGlass", JiuUtils.item.getItemByNameOrId("thermalfoundation:glass"), i);
//				}
//				types = new String[] {"steel", "electrum", "invar", "bronze", "constantan", "signalum", "lumium", "enderium"};
//				for(int i = 0; i < types.length; i++) {
//					item.registerOre("hardenGlass", JiuUtils.item.getItemByNameOrId("thermalfoundation:glass_alloy"), i);
//					item.registerOre(types[i] + "HardenedGlass", JiuUtils.item.getItemByNameOrId("thermalfoundation:glass_alloy"), i);
//				}
//				types = new String[] {"black", "red", "green", "brown", "blue", "purple", "cyan", "silver", "gray", "pink", "lime", "yellow", "light_blue", "magenta", "orange", "white"};
//				for(int i = 0; i < types.length; i++) {
//					item.registerOre("rockWool", JiuUtils.item.getItemByNameOrId("thermalfoundation:rockwool"), i);
//					item.registerOre(types[i] + "RockWool", JiuUtils.item.getItemByNameOrId("thermalfoundation:rockwool"), i);
//				}
			}
		}
	}
	
	private static void blockBotania() {
		if(Configs.Custom.Enable_Mod_Stuff) {
			if(Loader.isModLoaded("botania")) {
				for(EnumDyeColor type : EnumDyeColor.values()) {
					String name = type.getUnlocalizedName();
					String colorName = name.substring(0, 1).toUpperCase() + name.substring(1);
					
					item.registerOre("block" + colorName + "Petal", JiuUtils.item.getItemByNameOrId("botania:petalBlock"), type.getMetadata());
				}
				item.registerOre("petal", JiuUtils.item.getItemByNameOrId("botania:petal"), OreDictionary.WILDCARD_VALUE);
				item.registerOre("botDye", JiuUtils.item.getItemByNameOrId("botania:dye"), OreDictionary.WILDCARD_VALUE);
				item.registerOre("blockPetal", JiuUtils.item.getItemByNameOrId("botania:petalBlock"), OreDictionary.WILDCARD_VALUE);
			}
		}
	}
	
	private static void custom() {
//		String[] ore = Configs.Custom.ore_dict_register;
//		for(int i = 1; i < Configs.Custom.ore_dict_register.length; ++i) {
//			String[] strore = JiuUtils.other.custemSplitString(ore[i], "|");
//			
//			if(strore.length == 3) {
//				String name = strore[0];
//				int meta = -1;
//				String oredict = strore[2];
//				
//				try {
//					meta = new Integer(strore[1]);
//				}catch (Exception e) {
//					MCS.instance.log.fatal(strore[0] + ": " + (strore[1]) + " is not Number!");
//				}
//				
//				if(meta != -1) {
//					item.registerOre(oredict, Item.getByNameOrId(name), meta);
//				}
//			}else {
//				MCS.instance.log.fatal(strore[0] + ": " + (strore.length - 1) + " is not multiple of 3!");
//			}
//		}
	}
}
