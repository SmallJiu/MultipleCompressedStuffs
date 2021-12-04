package cat.jiu.mcs.util.init;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.helpers.ItemUtils;

import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class MCSOreDict {
	private static final ItemUtils item = JiuUtils.item;
	
	public static void register() {
		try {
			mcBlock();
			if(Configs.custom.Enable_Mod_Stuff) {
				itemThermalFoundation();
				blockThermalFoundation();
				blockBotania();
			}
			MCSItems.registerOreDict();
			MCSBlocks.registerOreDict();
			custom();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	private static void mcBlock() {
		item.registerOre("blockConcretePowder", Blocks.CONCRETE_POWDER, OreDictionary.WILDCARD_VALUE);
		item.registerOre("blockConcrete", Blocks.CONCRETE, OreDictionary.WILDCARD_VALUE);
		item.registerOre("hardenedClay", Blocks.STAINED_HARDENED_CLAY, OreDictionary.WILDCARD_VALUE);
	}
	
	private static void itemThermalFoundation() {
		if(Configs.custom.Enable_Mod_Stuff) {
			if(Loader.isModLoaded("thermalfoundation")) {
				item.registerOre("tfDyes", JiuUtils.item.getItemByNameOrId("thermalfoundation:dye"), OreDictionary.WILDCARD_VALUE);
			}
		}
	}
	
	private static void blockThermalFoundation() {
		if(Configs.custom.Enable_Mod_Stuff) {
			if(Loader.isModLoaded("thermalfoundation")) {
				
//				PropertyEnum propertyEnum = (PropertyEnum) JiuUtils.item.getBlockFromName("thermalfoundation:galss").getBlockState().getProperty("type");
//				for() {
					
//				}
				
//				for(BlockGlass.Type type : BlockGlass.Type.values()) {
//					item.registerOre("hardenGlass", TFBlocks.blockGlass, type.getMetadata());
//					item.registerOre(type.getName() + "HardenedGlass", TFBlocks.blockGlass, type.getMetadata());
//				}
//				
//				for(BlockGlassAlloy.Type type : BlockGlassAlloy.Type.values()) {
////					item.registerOre("hardenGlass", TFBlocks.blockGlassAlloy, type.getMetadata());
//					item.registerOre(type.getName() + "HardenedGlass", TFBlocks.blockGlassAlloy, type.getMetadata());
//				}
//				
//				for(BlockRockwool.Type type : BlockRockwool.Type.values()) {
//					item.registerOre("rockWool", TFBlocks.blockRockwool, type.getMetadata());
//					item.registerOre(type.getName() + "RockWool", TFBlocks.blockRockwool, type.getMetadata());
//				}
			}
		}
	}
	
	private static void blockBotania() {
		if(Configs.custom.Enable_Mod_Stuff) {
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
		String[] ore = Configs.custom.register_ore.ore_dict_register;
		for(int i = 1; i < Configs.custom.register_ore.ore_dict_register.length; ++i) {
			String[] strore = JiuUtils.other.custemSplitString(ore[i], "|");
			
			if(strore.length == 3) {
				String name = strore[0];
				int meta = -1;
				String oredict = strore[2];
				
				try {
					meta = new Integer(strore[1]);
				}catch (Exception e) {
					MCS.instance.log.fatal(strore[0] + ": " + (strore[1]) + " is not Number!");
				}
				
				if(meta != -1) {
					item.registerOre(oredict, Item.getByNameOrId(name), meta);
				}
			}else {
				MCS.instance.log.fatal(strore[0] + ": " + (strore.length - 1) + " is not multiple of 3!");
			}
		}
	}
}
