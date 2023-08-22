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
import cat.jiu.core.util.timer.Timer;
import cat.jiu.mcs.util.type.CustomStuffType;
import cat.jiu.mcs.util.type.CustomStuffType.ToolModifiersType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.text.TextComponentTranslation;

public class InitCustomItem {
	private static <K, V> void put(Map<K, V> map, K k, V v) {
		if(!map.containsKey(k)) {
			map.put(k, v);
		}else {
			map.replace(k, v);
		}
	}

	public static Map<Integer, Boolean> initHasEffect(JsonObject obj) {
		if(obj.has("hasEffect")) {
			Map<Integer, Boolean> HasEffectMap = Maps.newHashMap();
			if(obj.get("hasEffect").isJsonObject()) {
				JsonObject effectsObject = obj.get("hasEffect").getAsJsonObject();
				for(Entry<String, JsonElement> effect : effectsObject.entrySet()) {
					int meta = Integer.parseInt(effect.getKey());
					boolean hasEffect = effect.getValue().getAsBoolean();

					if(meta == -1) {
						for(int meta0 = 0; meta0 < 16; meta0++) {
							put(HasEffectMap, meta0, hasEffect);
						}
					}else {
						put(HasEffectMap, meta, hasEffect);
					}
				}
			}else if(obj.get("hasEffect").isJsonPrimitive()) {
				boolean hasEffect = obj.get("hasEffect").getAsBoolean();
				for(int meta = 0; meta < 16; meta++) {
					put(HasEffectMap, meta, hasEffect);
				}
			}
			return HasEffectMap;
		}
		return null;
	}

	public static Map<Integer, List<CustomStuffType.PotionEffectType>> initPotionEffectMap(JsonObject obj) {
		if(obj.has("potionEffect")) {
			Map<Integer, List<CustomStuffType.PotionEffectType>> PotionEffectMap = Maps.newHashMap();
			if(obj.get("potionEffect").isJsonObject()) {
				JsonObject effectObject = obj.get("potionEffect").getAsJsonObject();

				for(Entry<String, JsonElement> effect : effectObject.entrySet()) {
					int meta = Integer.parseInt(effect.getKey());
					List<CustomStuffType.PotionEffectType> type = Lists.newArrayList();

					JsonArray effectsArray = effect.getValue().getAsJsonArray();
					for(int i = 0; i < effectsArray.size(); i++) {
						String[] effectElement = JiuUtils.other.custemSplitString(effectsArray.get(i).getAsString(), "@");
						Potion potion = Potion.getPotionFromResourceLocation(effectElement[0]);
						int level = Integer.parseInt(effectElement[1]);
						int m = Integer.parseInt(effectElement[2]);
						int s = Integer.parseInt(effectElement[3]);
						int tick = Integer.parseInt(effectElement[4]);
						type.add(new CustomStuffType.PotionEffectType(potion, new Timer(m, s, tick), level));
					}

					if(meta == -1) {
						for(int meta0 = 0; meta0 < 16; meta0++) {
							put(PotionEffectMap, meta0, type);
						}
					}else {
						put(PotionEffectMap, meta, type);
					}

				}

			}else if(obj.get("potionEffect").isJsonArray()) {

				JsonArray effectArray = obj.get("potionEffect").getAsJsonArray();
				List<CustomStuffType.PotionEffectType> type = Lists.newArrayList();
				for(int i = 0; i < effectArray.size(); i++) {
					String[] effectElement = JiuUtils.other.custemSplitString(effectArray.get(i).getAsString(), "@");
					Potion potion = Potion.getPotionFromResourceLocation(effectElement[0]);
					int level = Integer.parseInt(effectElement[1]);
					int m = Integer.parseInt(effectElement[2]);
					int s = Integer.parseInt(effectElement[3]);
					int tick = Integer.parseInt(effectElement[4]);
					type.add(new CustomStuffType.PotionEffectType(potion, new Timer(m, s, tick), level));
				}
				for(int meta = 0; meta < 16; meta++) {
					put(PotionEffectMap, meta, type);
				}
			}else if(obj.get("potionEffect").isJsonPrimitive()) {
				String[] effectElement = JiuUtils.other.custemSplitString(obj.get("potionEffect").getAsString(), "@");
				Potion potion = Potion.getPotionFromResourceLocation(effectElement[0]);
				int level = Integer.parseInt(effectElement[1]);
				int m = Integer.parseInt(effectElement[2]);
				int s = Integer.parseInt(effectElement[3]);
				int tick = Integer.parseInt(effectElement[4]);
				List<CustomStuffType.PotionEffectType> type = Lists.newArrayList(new CustomStuffType.PotionEffectType(potion, new Timer(m, s, tick), level));

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
				for(Entry<String, JsonElement> container : ContainerObject.entrySet()) {
					int meta = Integer.parseInt(container.getKey());
					ItemStack stack = JiuUtils.item.toStack(container.getValue());

					if(meta == -1) {
						for(int meta0 = 0; meta0 < 16; meta0++) {
							put(ContainerMap, meta0, stack);
						}
					}else {
						put(ContainerMap, meta, stack);
					}
				}
			}else if(obj.get("result").isJsonPrimitive()) {
				ItemStack container = JiuUtils.item.toStack(obj.get("result"));
				for(int meta = 0; meta < 16; meta++) {
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
						for(int meta0 = 0; meta0 < 16; meta0++) {
							put(HealAmountMap, meta0, healAmount);
						}
					}else {
						put(HealAmountMap, meta, healAmount);
					}
				}
			}else if(obj.get("subHealAmount").isJsonPrimitive()) {
				int healAmount = obj.get("subHealAmount").getAsInt();
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
				for(Entry<String, JsonElement> saturationElement : SaturationObject.entrySet()) {
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
			}else if(obj.get("subSaturation").isJsonPrimitive()) {
				float saturation = obj.get("subSaturation").getAsFloat();
				for(int meta = 0; meta < 16; meta++) {
					put(SaturationModifierMap, meta, saturation);
				}
			}
			return SaturationModifierMap;
		}
		return null;
	}

	public static Map<Integer, ItemStack> initRepairable(JsonObject json) {
		if(json.has("RepairStack")) {
			Map<Integer, ItemStack> RepairableStackMap = Maps.newHashMap();

			if(json.get("RepairStack").isJsonObject()) {
				JsonObject obj = json.get("RepairStack").getAsJsonObject();

				for(Entry<String, JsonElement> objEntry : obj.entrySet()) {
					int meta = Integer.parseInt(objEntry.getKey());
					ItemStack repair = JiuUtils.item.toStack(objEntry.getValue());

					if(meta == -1) {
						for(int i = 0; i < 16; i++) {
							put(RepairableStackMap, i, repair);
						}
					}else {
						put(RepairableStackMap, meta, repair);
					}
				}
			}else if(json.get("RepairStack").isJsonPrimitive()) {
				ItemStack repair = JiuUtils.item.toStack(json.get("RepairStack"));
				for(int meta = 0; meta < 16; meta++) {
					put(RepairableStackMap, meta, repair);
				}
			}
			return RepairableStackMap;
		}
		return null;
	}

	public static Map<Integer, ToolModifiersType> initAttributeModifier(JsonObject json) {
		if(json.has("Modifier")) {
			Map<Integer, ToolModifiersType> AttributeModifierMap = Maps.newHashMap();

			if(json.get("Modifier").isJsonObject()) {
				JsonObject obj = json.get("Modifier").getAsJsonObject();

				for(Entry<String, JsonElement> objEntry : obj.entrySet()) {
					int meta = Integer.parseInt(objEntry.getKey());
					JsonElement entry = objEntry.getValue();

					if(entry.isJsonObject()) {
						double damage = entry.getAsJsonObject().get("damage").getAsDouble();
						double speed = entry.getAsJsonObject().get("speed").getAsDouble();
						ToolModifiersType type = new ToolModifiersType(damage, speed);
						if(meta == -1) {
							for(int i = 0; i < 16; i++) {
								put(AttributeModifierMap, i, type);
							}
						}else {
							put(AttributeModifierMap, meta, type);
						}
					}
				}
			}else if(json.get("Modifier").isJsonPrimitive()) {
				String[] entry = JiuUtils.other.custemSplitString(json.get("Modifier").getAsString(), ":");
				double damage = Double.parseDouble(entry[0]);
				double speed = Double.parseDouble(entry[1]);
				ToolModifiersType type = new ToolModifiersType(damage, speed);

				for(int meta = 0; meta < 16; meta++) {
					put(AttributeModifierMap, meta, type);
				}
			}
			return AttributeModifierMap;
		}
		return null;
	}

	public static Map<Integer, Integer> initHarvestLevel(JsonObject json) {
		if(json.has("HarvestLevel")) {
			Map<Integer, Integer> HarvestLevelMap = Maps.newHashMap();

			if(json.get("HarvestLevel").isJsonObject()) {
				JsonObject obj = json.get("HarvestLevel").getAsJsonObject();

				for(Entry<String, JsonElement> objEntry : obj.entrySet()) {
					int meta = Integer.parseInt(objEntry.getKey());
					int level = objEntry.getValue().getAsInt();
					if(meta == -1) {
						for(int i = 0; i < 16; i++) {
							put(HarvestLevelMap, i, level);
						}
					}else {
						put(HarvestLevelMap, meta, level);
					}
				}
			}else if(json.get("HarvestLevel").isJsonPrimitive()) {
				int level = json.get("HarvestLevel").getAsInt();
				for(int meta = 0; meta < 16; meta++) {
					put(HarvestLevelMap, meta, level);
				}
			}
			return HarvestLevelMap;
		}
		return null;
	}

	public static Map<Integer, Float> initDestroySpeed(JsonObject json) {
		if(json.has("DestroySpeed")) {
			Map<Integer, Float> DestroySpeedMap = Maps.newHashMap();

			if(json.get("DestroySpeed").isJsonObject()) {
				JsonObject obj = json.get("DestroySpeed").getAsJsonObject();

				for(Entry<String, JsonElement> objEntry : obj.entrySet()) {
					int meta = Integer.parseInt(objEntry.getKey());
					float speed = objEntry.getValue().getAsFloat();
					if(meta == -1) {
						for(int i = 0; i < 16; i++) {
							put(DestroySpeedMap, i, speed);
						}
					}else {
						put(DestroySpeedMap, meta, speed);
					}
				}
			}else if(json.get("DestroySpeed").isJsonPrimitive()) {
				float speed = json.get("DestroySpeed").getAsFloat();
				for(int meta = 0; meta < 16; meta++) {
					put(DestroySpeedMap, meta, speed);
				}
			}
			return DestroySpeedMap;
		}
		return null;
	}

	public static Map<Integer, Integer> initEnchantabilityLevel(JsonObject json) {
		if(json.has("Enchantability")) {
			Map<Integer, Integer> EnchantabilityLevelMap = Maps.newHashMap();

			if(json.get("Enchantability").isJsonObject()) {
				JsonObject obj = json.get("Enchantability").getAsJsonObject();

				for(Entry<String, JsonElement> objEntry : obj.entrySet()) {
					int meta = Integer.parseInt(objEntry.getKey());
					int encLevel = objEntry.getValue().getAsInt();
					if(meta == -1) {
						for(int i = 0; i < 16; i++) {
							put(EnchantabilityLevelMap, i, encLevel);
						}
					}else {
						put(EnchantabilityLevelMap, meta, encLevel);
					}
				}
			}else if(json.get("Enchantability").isJsonPrimitive()) {
				int encLevel = json.get("Enchantability").getAsInt();
				for(int meta = 0; meta < 16; meta++) {
					put(EnchantabilityLevelMap, meta, encLevel);
				}
			}
			return EnchantabilityLevelMap;
		}
		return null;
	}

	public static Map<Integer, Integer> initMaxDamage(JsonObject json) {
		if(json.has("MaxDamage")) {
			Map<Integer, Integer> MaxDamageMap = Maps.newHashMap();
			JsonElement e = json.get("MaxDamage");
			if(e.isJsonObject()) {
				JsonObject obj = (JsonObject) e;
				for(Entry<String, JsonElement> entrys : obj.entrySet()) {
					int meta = Integer.parseInt(entrys.getKey());
					int max = entrys.getValue().getAsInt();
					if(meta == -1) {
						for(int i = 0; i < 16; i++) {
							put(MaxDamageMap, i, max);
						}
					}else {
						put(MaxDamageMap, meta, max);
					}
				}
			}else if(e.isJsonPrimitive()) {
				int max = e.getAsInt();
				for(int meta = 0; meta < 16; meta++) {
					put(MaxDamageMap, meta, max);
				}
			}
			return MaxDamageMap;
		}
		return null;
	}

	public static Map<Integer, List<IBlockState>> initCanHarvestBlock(JsonObject json) {
		if(json.has("canHarvestBlock")) {
			Map<Integer, List<IBlockState>> block = Maps.newHashMap();
			JsonElement e = json.get("canHarvestBlock");

			if(e.isJsonObject()) {
				JsonObject obj = (JsonObject) e;
				for(Entry<String, JsonElement> entrys : obj.entrySet()) {
					int meta = Integer.parseInt(entrys.getKey());
					JsonArray entryArray = entrys.getValue().getAsJsonArray();
					List<IBlockState> blocks = Lists.newArrayList();
					if(entryArray.size() > 0) {
						for(int i = 0; i < entryArray.size(); i++) {
							ItemStack stack = JiuUtils.item.toStack(entryArray.get(i));
							if(JiuUtils.item.isBlock(stack)) {
								blocks.add(JiuUtils.item.getStateFromItemStack(stack));
							}
						}
					}

					if(meta == -1) {
						for(int i = 0; i < 16; i++) {
							put(block, i, blocks);
						}
					}else {
						put(block, meta, blocks);
					}
				}
			}else if(e.isJsonArray()) {
				JsonArray array = (JsonArray) e;
				if(array.size() > 0) {
					for(int i = 0; i < array.size(); i++) {
						ItemStack stack = JiuUtils.item.toStack(array.get(i));
						if(JiuUtils.item.isBlock(stack)) {
							for(int meta = 0; meta < 16; meta++) {
								put(block, meta, Lists.newArrayList(JiuUtils.item.getStateFromItemStack(stack)));
							}
						}
					}
				}
			}else if(e.isJsonPrimitive()) {
				ItemStack stack = JiuUtils.item.toStack(e);
				if(JiuUtils.item.isBlock(stack)) {
					for(int meta = 0; meta < 16; meta++) {
						put(block, meta, Lists.newArrayList(JiuUtils.item.getStateFromItemStack(stack)));
					}
				}
			}
			return block;
		}
		return null;
	}

	public static Map<Integer, CustomStuffType.HarvestType> initHarvest(JsonObject obj) {
		if(obj.has("harvest")) {
			Map<Integer, CustomStuffType.HarvestType> HarvestMap = Maps.newHashMap();
			JsonElement e = obj.get("harvest");
			if(e.isJsonObject()) {
				JsonObject harvestObject = obj.get("harvest").getAsJsonObject();

				for(Entry<String, JsonElement> harvest : harvestObject.entrySet()) {
					String toolOrMeta = harvest.getKey();
					if(isTool(toolOrMeta)) {// if is tool
						JsonElement toolObject = harvest.getValue();

						if(isInt(toolObject)) {// if is int
							int level = toolObject.getAsInt();
							for(int meta = 0; meta < 16; meta++) {
								put(HarvestMap, meta, new CustomStuffType.HarvestType(toolOrMeta, level));
							}
						}else {
							// get meta list
							JsonObject toolObject0 = harvest.getValue().getAsJsonObject();
							for(Entry<String, JsonElement> toolElement : toolObject0.entrySet()) {
								int meta = Integer.parseInt(toolElement.getKey());
								int level = toolElement.getValue().getAsInt();
								if(meta == -1) {
									for(int i = 0; i < 16; i++) {
										put(HarvestMap, i, new CustomStuffType.HarvestType(toolOrMeta, level));
									}
								}else {
									put(HarvestMap, meta, new CustomStuffType.HarvestType(toolOrMeta, level));
								}
							}
						}
					}else {
						int meta = Integer.parseInt(toolOrMeta);
						int level = harvest.getValue().getAsInt();
						put(HarvestMap, meta, new CustomStuffType.HarvestType("pickaxe", level));
					}
				}
			}else if(e.isJsonPrimitive()) {
				int level = e.getAsJsonPrimitive().getAsInt();
				for(int i = 0; i < 16; i++) {
					put(HarvestMap, i, new CustomStuffType.HarvestType("pickaxe", level));
				}
			}
			return HarvestMap;
		}
		return null;
	}

	private static boolean isInt(JsonElement je) {
		if(je.isJsonPrimitive()) {
			return je.getAsJsonPrimitive().isNumber();
		}
		return false;
	}

	public static boolean isTool(String toolClass) {
		if(toolClass.toLowerCase().equals("pickaxe")) {
			return true;
		}else if(toolClass.toLowerCase().equals("axe")) {
			return true;
		}else if(toolClass.toLowerCase().equals("shovel")) {
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
				for(int meta = 0; meta < 16; meta++) {
					put(HardnessMap, meta, hardness);
				}
			}else {
				JsonObject hardnesss = obj.get("hardness").getAsJsonObject();
				for(Entry<String, JsonElement> entry : hardnesss.entrySet()) {
					int meta = Integer.parseInt(entry.getKey());
					float hardness = entry.getValue().getAsFloat();
					if(meta == -1) {
						for(int i = 0; i < 16; i++) {
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
				for(int meta = 0; meta < 16; meta++) {
					put(BeaconBaseMap, meta, isBeaconBase);
				}
			}else {
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
				for(int meta = 0; meta < 16; meta++) {
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

	public static Map<Integer, List<TextComponentTranslation>> initInfos(JsonObject obj) {
		if(obj.has("infos")) {
			Map<Integer, List<TextComponentTranslation>> InfoMap = Maps.newHashMap();
			JsonElement objInfos = obj.get("infos");
			if(objInfos.isJsonArray()) {
				List<TextComponentTranslation> infoList = Lists.newArrayList();
				for(int i = 0; i < objInfos.getAsJsonArray().size(); i++) {
					infoList.add(new TextComponentTranslation(objInfos.getAsJsonArray().get(i).getAsString()));
				}

				for(int meta = 0; meta < 16; meta++) {
					put(InfoMap, meta, infoList);
				}
			}else if(objInfos.isJsonObject()) {
				for(Entry<String, JsonElement> infos : objInfos.getAsJsonObject().entrySet()) {
					int meta = Integer.parseInt(infos.getKey());

					List<TextComponentTranslation> infoList = Lists.newArrayList();
					for(int i = 0; i < infos.getValue().getAsJsonArray().size(); i++) {
						infoList.add(new TextComponentTranslation(infos.getValue().getAsJsonArray().get(i).getAsString()));
					}

					if(meta == -1) {
						for(int meta0 = 0; meta0 < 16; meta0++) {
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

	public static Map<Integer, List<TextComponentTranslation>> initShiftInfos(JsonObject obj) {
		if(obj.has("shiftInfos")) {
			Map<Integer, List<TextComponentTranslation>> ShiftInfoMap = Maps.newHashMap();
			JsonElement objInfos = obj.get("shiftInfos");
			if(objInfos.isJsonArray()) {
				List<TextComponentTranslation> infoList = Lists.newArrayList();
				for(int i = 0; i < objInfos.getAsJsonArray().size(); i++) {
					infoList.add(new TextComponentTranslation(objInfos.getAsJsonArray().get(i).getAsString()));
				}

				for(int meta = 0; meta < 16; meta++) {
					put(ShiftInfoMap, meta, infoList);
				}
			}else if(objInfos.isJsonObject()) {
				for(Entry<String, JsonElement> infos : objInfos.getAsJsonObject().entrySet()) {
					int meta = Integer.parseInt(infos.getKey());

					List<TextComponentTranslation> infoList = Lists.newArrayList();
					for(int i = 0; i < infos.getValue().getAsJsonArray().size(); i++) {
						infoList.add(new TextComponentTranslation(infos.getValue().getAsJsonArray().get(i).getAsString()));
					}

					if(meta == -1) {
						for(int meta0 = 0; meta0 < 16; meta0++) {
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
				ItemStack infoStack = JiuUtils.item.toStack(obj.get("infoStack"));
				for(int meta = 0; meta < 16; meta++) {
					put(InfoStackMap, meta, infoStack);
				}
			}else {
				JsonObject infoObject = obj.get("infoStack").getAsJsonObject();
				for(Entry<String, JsonElement> infoElement : infoObject.entrySet()) {
					int meta = Integer.parseInt(infoElement.getKey());
					ItemStack infoStacks = JiuUtils.item.toStack(infoElement.getValue());
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
			if(tab.tabLabel.toLowerCase().equals(name.toLowerCase())) {
				return tab;
			}
		}
		return failback;
	}
}
