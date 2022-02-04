package cat.jiu.mcs.util.init;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.mcs.util.base.BaseBlockSub.HarvestType;
import net.minecraft.item.ItemStack;

public class InitCustomBlock {
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
	
	private static <K, V> void put(Map<K,V> map, K k, V v){
		if(!map.containsKey(k)) {
			map.put(k, v);
		}else {
			map.replace(k, v);
		}
	}
}
