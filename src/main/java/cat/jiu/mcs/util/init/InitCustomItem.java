//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.util.init;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.type.PotionEffectType;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

public class InitCustomItem {
	public static Map<Integer, Boolean> initHasEffect(JsonObject obj) {
		if(obj.has("hasEffect")) {
			Map<Integer, Boolean> HasEffectMap = Maps.newHashMap();
			if(obj.get("hasEffect").isJsonObject()) {
				JsonObject effectsObject = obj.get("hasEffect").getAsJsonObject();
				for(Entry<String, JsonElement> effect : effectsObject.entrySet()) {
					int meta = Integer.parseInt(effect.getKey());
					boolean hasEffect = effect.getValue().getAsBoolean();
					
					if(meta == -1) {
						for (int meta0 = 0; meta0 < 16; meta0++) {
							put(HasEffectMap, meta0, hasEffect);
						}
					}else {
						put(HasEffectMap, meta, hasEffect);
					}
				}
			}else {
				boolean hasEffect = obj.get("hasEffect").getAsBoolean();
				for (int meta = 0; meta < 16; meta++) {
					put(HasEffectMap, meta, hasEffect);
				}
			}
			return HasEffectMap;
		}
		return null;
	}
	
	public static Map<Integer, List<PotionEffectType>> initPotionEffectMap(JsonObject obj) {
		if(obj.has("potionEffect")) {
			Map<Integer, List<PotionEffectType>> PotionEffectMap = Maps.newHashMap();
			if(obj.get("potionEffect").isJsonObject()) {
				JsonObject effectObject = obj.get("potionEffect").getAsJsonObject();
				
				for(Entry<String, JsonElement> effect : effectObject.entrySet()) {
					int meta = Integer.parseInt(effect.getKey());
					List<PotionEffectType> type = Lists.newArrayList();
					
					JsonArray effectsArray = effect.getValue().getAsJsonArray();
					for (int i = 0; i < effectsArray.size(); i++) {
						String[] effectElement = JiuUtils.other.custemSplitString(effectsArray.get(i).getAsString(), "@");
						Potion potion = Potion.getPotionFromResourceLocation(effectElement[0]);
						int level = Integer.parseInt(effectElement[1]);
						int m = Integer.parseInt(effectElement[2]);
						int s = Integer.parseInt(effectElement[3]);
						int tick = Integer.parseInt(effectElement[4]);
						type.add(new PotionEffectType(potion, level, m, s, tick));
					}
					
					if(meta == -1) {
						for (int meta0 = 0; meta0 < 16; meta0++) {
							put(PotionEffectMap, meta0, type);
						}
					}else {
						put(PotionEffectMap, meta, type);
					}
					
				}
				
			}else if(obj.get("potionEffect").isJsonArray()){
				
				JsonArray effectArray = obj.get("potionEffect").getAsJsonArray();
				List<PotionEffectType> type = Lists.newArrayList();
				for(int i = 0; i < effectArray.size(); i++) {
					String[] effectElement = JiuUtils.other.custemSplitString(effectArray.get(i).getAsString(), "@");
					Potion potion = Potion.getPotionFromResourceLocation(effectElement[0]);
					int level = Integer.parseInt(effectElement[1]);
					int m = Integer.parseInt(effectElement[2]);
					int s = Integer.parseInt(effectElement[3]);
					int tick = Integer.parseInt(effectElement[4]);
					type.add(new PotionEffectType(potion, level, m, s, tick));
				}
				for(int meta = 0; meta < 16; meta++) {
					put(PotionEffectMap, meta, type);
				}
				
			}else {
				
				String[] effectElement = JiuUtils.other.custemSplitString(obj.get("potionEffect").getAsString(), "@");
				Potion potion = Potion.getPotionFromResourceLocation(effectElement[0]);
				int level = Integer.parseInt(effectElement[1]);
				int m = Integer.parseInt(effectElement[2]);
				int s = Integer.parseInt(effectElement[3]);
				int tick = Integer.parseInt(effectElement[4]);
				List<PotionEffectType> type = Lists.newArrayList(new PotionEffectType(potion, level, m, s, tick));
				
				for(int meta = 0; meta < 16; meta++) {
					put(PotionEffectMap, meta, type);
				}
				
			}
			return PotionEffectMap;
		}
		return null;
	}
	
	public static Map<Integer, ItemStack> initContainerMap(JsonObject obj) {
		if(obj.has("result")) {
			Map<Integer, ItemStack> ContainerMap = Maps.newHashMap();
			if(obj.get("result").isJsonObject()) {
				JsonObject ContainerObject = obj.get("result").getAsJsonObject();
				for (Entry<String, JsonElement> container : ContainerObject.entrySet()) {
					int meta = Integer.parseInt(container.getKey());
					ItemStack stack = InitChangeBlock.getStack(container.getValue().getAsString());
					
					if(meta == -1) {
						for (int meta0 = 0; meta0 < 16; meta0++) {
							put(ContainerMap, meta0, stack);
						}
					}else {
						put(ContainerMap, meta, stack);
					}
				}
			}else {
				ItemStack container = InitChangeBlock.getStack(obj.get("result").getAsString());
				for (int meta = 0; meta < 16; meta++) {
					put(ContainerMap, meta, container);
				}
			}
			return ContainerMap;
		}
		return null;
	}
	
	public static Map<Integer, Integer> initHealAmountMap(JsonObject obj) {
		if(obj.has("subHealAmount")) {
			Map<Integer, Integer> HealAmountMap = Maps.newHashMap();
			if(obj.get("subHealAmount").isJsonObject()) {
				JsonObject HealAmountObject = obj.get("subHealAmount").getAsJsonObject();
				for(Entry<String, JsonElement> healAmountElement : HealAmountObject.entrySet()) {
					int meta = Integer.parseInt(healAmountElement.getKey());
					int healAmount = healAmountElement.getValue().getAsInt();
					
					if(meta == -1) {
						for (int meta0 = 0; meta0 < 16; meta0++) {
							put(HealAmountMap, meta0, healAmount);
						}
					}else {
						put(HealAmountMap, meta, healAmount);
					}
				}
			}else {
				int healAmount = Integer.parseInt(obj.get("subHealAmount").getAsString());
				for(int meta = 0; meta < 16; meta++) {
					put(HealAmountMap, meta, healAmount);
				}
			}
			return HealAmountMap;
		}
		return null;
	}
	
	public static Map<Integer, Float> initSaturationModifierMap(JsonObject obj) {
		if(obj.has("subSaturation")) {
			Map<Integer, Float> SaturationModifierMap = Maps.newHashMap();
			
			if(obj.get("subSaturation").isJsonObject()) {
				JsonObject SaturationObject = obj.get("subSaturation").getAsJsonObject();
				for (Entry<String, JsonElement> saturationElement : SaturationObject.entrySet()) {
					int meta = Integer.parseInt(saturationElement.getKey());
					float saturation = saturationElement.getValue().getAsFloat();
					
					if(meta == -1) {
						for(int meta0 = 0; meta0 < 16; meta0++) {
							put(SaturationModifierMap, meta0, saturation);
						}
					}else {
						put(SaturationModifierMap, meta, saturation);
					}
				}
			}else {
				float saturation = obj.get("subSaturation").getAsFloat();
				for(int meta = 0; meta < 16; meta++) {
					put(SaturationModifierMap, meta, saturation);
				}
			}
			return SaturationModifierMap;
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
