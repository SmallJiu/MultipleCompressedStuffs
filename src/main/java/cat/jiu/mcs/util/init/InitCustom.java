package cat.jiu.mcs.util.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.exception.JsonElementNotFoundException;
import cat.jiu.mcs.exception.JsonException;
import cat.jiu.mcs.exception.NonItemException;
import cat.jiu.mcs.exception.UnknownTypeException;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import cat.jiu.mcs.util.base.sub.BaseItemFood;
import cat.jiu.mcs.util.base.sub.BaseItemSub;
import cat.jiu.mcs.util.base.sub.BaseBlockSub.HarvestType;
import cat.jiu.mcs.util.type.CustomType;
import cat.jiu.mcs.util.type.PotionEffectType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class InitCustom {
	public static void registerCustom() {
		File config = new File("./config/jiu/mcs/custom.json");
		if(config.exists()) {
			try {
				JsonObject file = new JsonParser().parse(new FileReader(config)).getAsJsonObject();
				
				for(Map.Entry<String, JsonElement> fileObject : file.entrySet()) {
					JsonArray mainArray = fileObject.getValue().getAsJsonArray();// 主清单
					for(int i = 0; i < mainArray.size(); ++i) {
						JsonObject subObject = mainArray.get(i).getAsJsonObject();//子清单
						
						String type_tmp = subObject.get("type").getAsString();
						String[] main_type = type_tmp.indexOf(":") != -1 ? JiuUtils.other.custemSplitString(type_tmp, ":") : new String[]{type_tmp};
						
						CustomType type = CustomType.getType(main_type);
						if(type == CustomType.UNKNOWN) {
							String crashMsg = "\n\ncustom.json -> nunknown type: \n -> " + fileObject.getKey() + ": \n  -> (" + i + "):\n   -> \"type\": \"" + type_tmp + "\"\n";
							throw new UnknownTypeException(crashMsg);
						}else {
							JsonArray entries = subObject.get("entries").getAsJsonArray();// 方块清单
							for(int m = 0; m < entries.size(); ++m) {
								JsonObject itemObject = entries.get(m).getAsJsonObject();
								
								String name = itemObject.get("id").getAsString();
								ItemStack unItem = InitChangeBlock.getStack(itemObject.get("unItem").getAsString());
								if(unItem == null || unItem.getItem() == Items.AIR || unItem == ItemStack.EMPTY) {
									String crashMsg = "\n\ncustom.json -> unknown item:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"unItem\": \"" + itemObject.get("unItem").getAsString() + "\"\n";
									throw new NonItemException(crashMsg);
								}
								switch(type) {
									case BLOCK: 		initBlock(itemObject, name, unItem, getTab(itemObject, MCS.COMPERESSED_BLOCKS)); break;
									case ITEM_NORMA: 	initNormalItem(itemObject, name, unItem, getTab(itemObject, MCS.COMPERESSED_ITEMS)); break;
									case ITEM_FOOD: 	initFood(itemObject, name, unItem, getTab(itemObject, MCS.COMPERESSED_ITEMS), fileObject, i); break;
									case ITEM_SWORD: 	break;
									case ITEM_PICKAXE: 	break;
									case ITEM_SHOVEL: 	break;
									case ITEM_AXE: 		break;
									default:break;
								}
							}
						}
					}
				}
			}catch(JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				e.printStackTrace();
				throw new JsonException("custom.json -> " + e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf(":")+2));
			}
		}
	}
	
	public static Map<Integer, HarvestType> initHarvest(JsonObject obj) {
		if(obj.has("harvest")) {
			Map<Integer, HarvestType> HarvestMap = Maps.newHashMap();
			JsonObject harvestObject = obj.get("harvest").getAsJsonObject();
			
			for(Entry<String, JsonElement> harvest : harvestObject.entrySet()) {
				String toolOrMeta = harvest.getKey();
				if(isTool(toolOrMeta)) {// if is tool
					JsonElement toolObject = harvest.getValue();
					
					if(isInt(toolObject)) {// if is int
						int level = toolObject.getAsInt();
						for(int meta = 0; meta < 16; meta++) {
							put(HarvestMap, meta, new HarvestType(toolOrMeta, level));
						}
					}else {
						// get meta list
						JsonObject toolObject0 = harvest.getValue().getAsJsonObject();
						for(Entry<String, JsonElement> toolElement : toolObject0.entrySet()) {
							int meta = Integer.parseInt(toolElement.getKey());
							int level = toolElement.getValue().getAsInt();
							if(meta == -1) {
								for(int i = 0; i < 16; i++) {
									put(HarvestMap, i, new HarvestType(toolOrMeta, level));
								}
							}else {
								put(HarvestMap, meta, new HarvestType(toolOrMeta, level));
							}
						}
					}
				}else {
					int meta = Integer.parseInt(toolOrMeta);
					int level = harvest.getValue().getAsInt();
					put(HarvestMap, meta, new HarvestType("pickaxe", level));
				}
			}
			return HarvestMap; 
		}
		return null;
	}
	
	private static boolean isInt(JsonElement je) {
		Integer i = null;
		try {
			i = je.getAsInt();
		} catch (Exception e) {
			e.fillInStackTrace();
		}
		return i != null;
	}
	
	public static boolean isTool(String toolClass) {
		if(toolClass.equals("pickaxe")) {
			return true;
		}else if(toolClass.equals("axe")) {
			return true;
		}else if(toolClass.equals("shovel")) {
			return true;
		}else {
			return false;
		}
	}
	
	public static Map<Integer, Float> initHardness(JsonObject obj) {
		if(obj.has("hardness")) {
			Map<Integer, Float> HardnessMap = Maps.newHashMap();
			
			if(!obj.get("hardness").isJsonObject()) {
				float hardness = obj.get("hardness").getAsFloat();
				for (int meta = 0; meta < 16; meta++) {
					put(HardnessMap, meta, hardness);
				}
			}else {
				JsonObject hardnesss = obj.get("hardness").getAsJsonObject();
				for(Entry<String, JsonElement> entry : hardnesss.entrySet()) {
					int meta = Integer.parseInt(entry.getKey());
					float hardness = entry.getValue().getAsFloat();
					if(meta == -1) {
						for (int i = 0; i < 16; i++) {
							put(HardnessMap, i, hardness);
						}
					}else {
						put(HardnessMap, meta, hardness);
					}
				}
			}
			return HardnessMap;
		}
		return null;
	}
	
	public static Map<Integer, Boolean> initBeaconBase(JsonObject obj) {
		if(obj.has("isBeaconBase")) {
			Map<Integer, Boolean> BeaconBaseMap = Maps.newHashMap();
			
			if(!obj.get("isBeaconBase").isJsonObject()) {
				boolean isBeaconBase = obj.get("isBeaconBase").getAsBoolean();
				for (int meta = 0; meta < 16; meta++) {
					put(BeaconBaseMap, meta, isBeaconBase);
				}
			}else{
				JsonObject values = obj.get("isBeaconBase").getAsJsonObject();
				for(Entry<String, JsonElement> value : values.entrySet()) {
					int meta = Integer.parseInt(value.getKey());
					boolean isBeaconBase = value.getValue().getAsBoolean();
					put(BeaconBaseMap, meta, isBeaconBase);
				}
			}
			return BeaconBaseMap;
		}
		return null;
	}
	
	public static Map<Integer, Integer> initLightValue(JsonObject obj) {
		if(obj.has("lightValue")) {
			Map<Integer, Integer> LightValueMap = Maps.newHashMap();
			
			if(!obj.get("lightValue").isJsonObject()) {
				int light = obj.get("lightValue").getAsInt();
				for(int meta = 0; meta < 16; meta++) {
					put(LightValueMap, meta, light);
				}
			}else {
				JsonObject values = obj.get("lightValue").getAsJsonObject();
				for(Entry<String, JsonElement> value : values.entrySet()) {
					int meta = Integer.parseInt(value.getKey());
					int light = value.getValue().getAsInt();
					put(LightValueMap, meta, light);
				}
			}
			
			
			return LightValueMap;
		}
		return null;
	}
	
	public static Map<Integer, Float> initExplosionResistance(JsonObject obj) {
		if(obj.has("explosionResistance")) {
			Map<Integer, Float> ExplosionResistanceMap = Maps.newHashMap();
			
			if(!obj.get("explosionResistance").isJsonObject()) {
				float expR = obj.get("explosionResistance").getAsFloat();
				for (int meta = 0; meta < 16; meta++) {
					put(ExplosionResistanceMap, meta, expR);
				}
			}else {
				
				JsonObject ers = obj.get("explosionResistance").getAsJsonObject();
				for(Entry<String, JsonElement> er : ers.entrySet()) {
					int meta = Integer.parseInt(er.getKey());
					float expR = er.getValue().getAsFloat();
					put(ExplosionResistanceMap, meta, expR);
				}
			}
			
			return ExplosionResistanceMap;
		}
		return null;
	}
	
	public static Map<Integer, List<String>> initInfos(JsonObject obj) {
		if(obj.has("infos")) {
			Map<Integer, List<String>> InfoMap = Maps.newHashMap();
			JsonElement objInfos = obj.get("infos");
			if(objInfos.isJsonArray()) {
				List<String> infoList = Lists.newArrayList();
				for(int i = 0; i < objInfos.getAsJsonArray().size(); i++) {
					infoList.add(objInfos.getAsJsonArray().get(i).getAsString());
				}
				
				for(int meta = 0; meta < 16; meta++) {
					put(InfoMap, meta, infoList);
				}
			}else if(objInfos.isJsonObject()){
				for(Entry<String, JsonElement> infos : objInfos.getAsJsonObject().entrySet()) {
					int meta = Integer.parseInt(infos.getKey());
					
					List<String> infoList = Lists.newArrayList();
					for(int i = 0; i < infos.getValue().getAsJsonArray().size(); i++) {
						infoList.add(infos.getValue().getAsJsonArray().get(i).getAsString());
					}
					
					if(meta == -1) {
						for (int meta0 = 0; meta0 < 16; meta0++) {
							put(InfoMap, meta0, infoList);
						}
					}else {
						put(InfoMap, meta, infoList);
					}
				}
			}
			return InfoMap;
		}
		return null;
	}
	
	public static Map<Integer, List<String>> initShiftInfos(JsonObject obj) {
		if(obj.has("shiftInfos")) {
			Map<Integer, List<String>> ShiftInfoMap = Maps.newHashMap();
			JsonElement objInfos = obj.get("shiftInfos");
			if(objInfos.isJsonArray()) {
				List<String> infoList = Lists.newArrayList();
				for(int i = 0; i < objInfos.getAsJsonArray().size(); i++) {
					infoList.add(objInfos.getAsJsonArray().get(i).getAsString());
				}
				
				for(int meta = 0; meta < 16; meta++) {
					put(ShiftInfoMap, meta, infoList);
				}
			}else if(objInfos.isJsonObject()){
				for(Entry<String, JsonElement> infos : objInfos.getAsJsonObject().entrySet()) {
					int meta = Integer.parseInt(infos.getKey());
					
					List<String> infoList = Lists.newArrayList();
					for(int i = 0; i < infos.getValue().getAsJsonArray().size(); i++) {
						infoList.add(infos.getValue().getAsJsonArray().get(i).getAsString());
					}
					
					if(meta == -1) {
						for (int meta0 = 0; meta0 < 16; meta0++) {
							put(ShiftInfoMap, meta0, infoList);
						}
					}else {
						put(ShiftInfoMap, meta, infoList);
					}
				}
			}
			return ShiftInfoMap;
		}
		return null;
	}
	
	public static Map<Integer, ItemStack> initInfoStack(JsonObject obj) {
		if(obj.has("infoStack")) {
			Map<Integer, ItemStack> InfoStackMap = Maps.newHashMap();
			
			if(!obj.get("infoStack").isJsonObject()) {
				ItemStack infoStack = InitChangeBlock.getStack(obj.get("infoStack").getAsString());
				for(int meta = 0; meta < 16; meta++) {
					put(InfoStackMap, meta, infoStack);
				}
			}else {
				JsonObject infoObject = obj.get("infoStack").getAsJsonObject();
				for (Entry<String, JsonElement> infoElement : infoObject.entrySet()) {
					int meta = Integer.parseInt(infoElement.getKey());
					ItemStack infoStacks = InitChangeBlock.getStack(infoElement.getValue().getAsString());
					if(meta == -1) {
						for(int meta0 = 0; meta0 < 16; meta0++) {
							put(InfoStackMap, meta0, infoStacks);
						}
					}else {
						put(InfoStackMap, meta, infoStacks);
					}
				}
			}
			return InfoStackMap;
		}
		return null;
	}
	
	public static Map<Integer, Boolean> initUseWrenchBreak(JsonObject obj) {
		if(obj.has("canUseWrenchBreak")) {
			Map<Integer, Boolean> UseWrenchBreakMap = Maps.newHashMap();
			
			if(!obj.get("canUseWrenchBreak").isJsonObject()) {
				Boolean canUse = obj.get("canUseWrenchBreak").getAsBoolean();
				for(int meta = 0; meta < 16; meta++) {
					put(UseWrenchBreakMap, meta, canUse);
				}
			}else {
				JsonObject useObject = obj.get("canUseWrenchBreak").getAsJsonObject();
				for(Entry<String, JsonElement> useElements : useObject.entrySet()) {
					int meta = Integer.parseInt(useElements.getKey());
					boolean canUse0 = useElements.getValue().getAsBoolean();
					put(UseWrenchBreakMap, meta, canUse0);
				}
			}
			return UseWrenchBreakMap;
		}
		return null;
	}
	
	public static CreativeTabs getTab(JsonObject blockObject, CreativeTabs failback) {
		if(blockObject.has("tab")) {
			return getCreativeTabs(blockObject.get("tab").getAsString(), failback);
		}
		return failback;
	}
	
	public static CreativeTabs getCreativeTabs(String name, CreativeTabs failback) {
		for(CreativeTabs tab : CreativeTabs.CREATIVE_TAB_ARRAY) {
			if(tab.tabLabel.equals(name.toLowerCase())) {
				return tab;
			}
		}
		return failback;
	}
	
	private static <K, V> void put(Map<K,V> map, K k, V v){
		if(!map.containsKey(k)) {
			map.put(k, v);
		}else {
			map.replace(k, v);
		}
	}
	
	public static void initBlock(JsonObject blockObject, String name, ItemStack unItem, CreativeTabs tab) {
		BaseBlockSub block =  BaseBlockSub.register(name, unItem, "custom", tab);
		
		if(blockObject.has("enableDefaultRecipe")) {
			block.setMakeDefaultStackRecipe(blockObject.get("enableDefaultRecipe").getAsBoolean());
		}
		
		Map<Integer, ItemStack> InfoStackMap = InitCustom.initInfoStack(blockObject);
		if(InfoStackMap != null) {
			block.setInfoStack(InfoStackMap);
		}
		
		Map<Integer, List<String>> infos = InitCustom.initInfos(blockObject);
		if(infos != null) {
			block.addCustemInformation(infos);
		}
		
		Map<Integer, List<String>> shiftInfos = InitCustom.initShiftInfos(blockObject);
		if(shiftInfos != null) {
			block.addCustemShiftInformation(shiftInfos);
		}
		
		Map<Integer, HarvestType> HarvestMap = InitCustom.initHarvest(blockObject);
		if(HarvestMap != null) {
			block.setHarvestMap(HarvestMap);
		}
		
		Map<Integer, Float> HardnessMap = InitCustom.initHardness(blockObject);
		if(HardnessMap != null) {
			block.setHardnessMap(HardnessMap);
		}
		
		Map<Integer, Boolean> BeaconBaseMap = InitCustom.initBeaconBase(blockObject);
		if(BeaconBaseMap != null) {
			block.setBeaconBaseMap(BeaconBaseMap);
		}
		
		Map<Integer, Integer> LightValueMap = InitCustom.initLightValue(blockObject);
		if(LightValueMap != null) {
			block.setLightValueMap(LightValueMap);
		}
		
		Map<Integer, Float> ExplosionResistanceMap = InitCustom.initExplosionResistance(blockObject);
		if(ExplosionResistanceMap != null) {
			block.setExplosionResistanceMap(ExplosionResistanceMap);
		}
		
		Map<Integer, Boolean> UseWrenchBreakMap = InitCustom.initUseWrenchBreak(blockObject);
		if(UseWrenchBreakMap != null) {
			block.canUseWrenchBreak(UseWrenchBreakMap);
		}
		
		MCSResources.SUB_BLOCKS_NAME.add(name);
		MCSResources.SUB_BLOCKS.add(block);
		MCSResources.SUB_BLOCKS_MAP.put(name, block);
	}
	
	public static void initNormalItem(JsonObject itemObject, String name, ItemStack unItem, CreativeTabs tab) {
		BaseItemSub item = BaseItemSub.register(name, unItem, "custom", tab);
		
		if(itemObject.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(itemObject.get("enableDefaultRecipe").getAsBoolean());
		}
		
		Map<Integer, ItemStack> InfoStackMap = InitCustom.initInfoStack(itemObject);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}
		
		Map<Integer, List<String>> infos = InitCustom.initInfos(itemObject);
		if(infos != null) {
			item.addCustemInformation(infos);
		}
		
		Map<Integer, List<String>> shiftInfos = InitCustom.initShiftInfos(itemObject);
		if(shiftInfos != null) {
			item.addCustemShiftInformation(shiftInfos);
		}
		
		Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(itemObject);
		if(HasEffectMap != null) {
			item.setHasEffectMap(HasEffectMap);
		}
		
		MCSResources.ITEMS.add(item);
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.SUB_ITEMS.add(item);
		MCSResources.SUB_ITEMS_NAME.add(name);
		MCSResources.SUB_ITEMS_MAP.put(name, item);
	}
	public static void initFood(JsonObject itemObject, String name, ItemStack unItem, CreativeTabs tab, Entry<String, JsonElement> fileObject, int i) {
		BaseItemFood item = BaseItemFood.register(name, unItem, "custom", tab);
		
		if(itemObject.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(itemObject.get("enableDefaultRecipe").getAsBoolean());
			MCS.instance.log.info(itemObject.get("enableDefaultRecipe").getAsBoolean()+"");
		}
		
		if(!BaseItemFood.isFood(unItem)) {
			if(!itemObject.has("healAmount")) {
				String crashMsg = "\n\ncustom.json -> element not found:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"healAmount\": <Number>\n";
				throw new JsonElementNotFoundException(crashMsg);
			}
			if(!itemObject.has("saturation")) {
				String crashMsg = "\n\ncustom.json -> element not found:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"saturation\": <Number>\n";
				throw new JsonElementNotFoundException(crashMsg);
			}
			if(!itemObject.has("isWolfFood")) {
				String crashMsg = "\n\ncustom.json -> element not found:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"isWolfFood\": <Boolean>\n";
				throw new JsonElementNotFoundException(crashMsg);
			}
			item.setFoodEntry(itemObject.get("healAmount").getAsInt(), itemObject.get("saturation").getAsFloat(), itemObject.get("isWolfFood").getAsBoolean());
		}
		
		Map<Integer, ItemStack> InfoStackMap = InitCustom.initInfoStack(itemObject);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}
		
		Map<Integer, List<String>> infos = InitCustom.initInfos(itemObject);
		if(infos != null) {
			item.addCustemInformation(infos);
		}
		
		Map<Integer, List<String>> shiftInfos = InitCustom.initShiftInfos(itemObject);
		if(shiftInfos != null) {
			item.addCustemShiftInformation(shiftInfos);
		}
		
		Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(itemObject);
		if(HasEffectMap != null) {
			item.setHasEffectMap(HasEffectMap);
		}
		
		Map<Integer, ItemStack> ContainerMap = InitCustomItem.initContainerMap(itemObject);
		if(ContainerMap != null) {
			item.setContainerMap(ContainerMap);
		}
		
		Map<Integer, List<PotionEffectType>> PotionEffectMap = InitCustomItem.initPotionEffectMap(itemObject);
		if(PotionEffectMap != null) {
			item.addPotionEffect(PotionEffectMap);
		}
		
		Map<Integer, Integer> HealAmountMap = InitCustomItem.initHealAmountMap(itemObject);
		if(HealAmountMap != null) {
			item.setHealAmountMap(HealAmountMap);
		}
		
		Map<Integer, Float> SaturationModifierMap = InitCustomItem.initSaturationModifierMap(itemObject);
		if(SaturationModifierMap != null) {
			item.setSaturationModifierMap(SaturationModifierMap);
		}
		
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.FOODS_NAME.add(name);
		MCSResources.ITEMS.add(item);
		MCSResources.FOODS.add(item);
		MCSResources.FOODS_MAP.put(name, item);
	}
}
