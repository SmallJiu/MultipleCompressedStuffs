package cat.jiu.mcs.util.init;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import cat.jiu.mcs.exception.UnknownTypeException;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import cat.jiu.mcs.util.base.sub.BaseItemFood;
import cat.jiu.mcs.util.base.sub.BaseItemSub;
import cat.jiu.mcs.util.base.sub.tool.BaseItemAxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemHoe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemPickaxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemShovel;
import cat.jiu.mcs.util.base.sub.tool.BaseItemSword;
import cat.jiu.mcs.util.base.sub.BaseBlockSub.HarvestType;
import cat.jiu.mcs.util.type.CustomStuffType;
import cat.jiu.mcs.util.type.CustomType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class InitCustom {
	private static final Logger log = LogManager.getLogger();
	public static final HashMap<String, JsonElement> unRegisterCustom = Maps.newHashMap();
	public static final HashMap<String, BaseBlockSub> unSetUnItem = Maps.newHashMap();

	public static void registerCustom() {
		File config = new File("./config/jiu/mcs/custom.json");
		if(config.exists()) {
			try {
				JsonObject file = new JsonParser().parse(new FileReader(config)).getAsJsonObject();

				for(Map.Entry<String, JsonElement> fileObject : file.entrySet()) {
					JsonArray mainArray = fileObject.getValue().getAsJsonArray();// 主清单
					for(int i = 0; i < mainArray.size(); ++i) {
						JsonObject subObject = mainArray.get(i).getAsJsonObject();// 子清单

						String type_tmp = subObject.get("type").getAsString();
						String[] main_type = type_tmp.indexOf(":") != -1 ? JiuUtils.other.custemSplitString(type_tmp, ":") : new String[]{type_tmp};

						CustomType type = CustomType.getType(main_type);
						if(type == CustomType.UNKNOWN) {
							String crashMsg = "\n\ncustom.json -> \nunknown type: \n -> " + fileObject.getKey() + ": \n  -> (" + i + "):\n   -> \"type\": \"" + type_tmp + "\"\n";
							throw new UnknownTypeException(crashMsg);
						}else {
							JsonArray entries = subObject.get("entries").getAsJsonArray();// 方块清单
							for(int m = 0; m < entries.size(); ++m) {
								JsonObject itemObject = entries.get(m).getAsJsonObject();
								
								String name = itemObject.get("id").getAsString();
								MCS.instance.log.info(itemObject.toString());
								ItemStack unItem = JiuUtils.item.toStack(itemObject.get("unItem"));
								if(unItem == null || unItem.isEmpty()) {
									String crashMsg = "\n\ncustom.json -> unknown item:\n -> " + fileObject.getKey() + ":\n  -> (" + i + "): \n   -> \"unItem\": \"" + (itemObject.get("unItem").isJsonObject() ? itemObject.get("unItem").getAsJsonObject().get("name").getAsString() : itemObject.get("unItem").getAsString()) + "\"\n";
									log.error(crashMsg);
								}
								switch(type) {
									case BLOCK:
										initBlock(itemObject, name, itemObject.get("unItem"), InitCustomItem.getTab(itemObject, MCS.COMPERESSED_BLOCKS));
										break;
									case ITEM_NORMA:
										initNormalItem(itemObject, name, unItem, InitCustomItem.getTab(itemObject, MCS.COMPERESSED_ITEMS));
										break;
									case ITEM_FOOD:
										initFood(itemObject, name, unItem, InitCustomItem.getTab(itemObject, MCS.COMPERESSED_ITEMS), fileObject, i);
										break;
									case ITEM_SWORD:
										initSword(itemObject, name, unItem, InitCustomItem.getTab(itemObject, MCS.COMPERESSED_TOOLS));
										break;
									case ITEM_PICKAXE:
										initPickaxe(itemObject, name, unItem, InitCustomItem.getTab(itemObject, MCS.COMPERESSED_TOOLS));
										break;
									case ITEM_SHOVEL:
										initShovel(itemObject, name, unItem, InitCustomItem.getTab(itemObject, MCS.COMPERESSED_TOOLS));
										break;
									case ITEM_AXE:
										initAxe(itemObject, name, unItem, InitCustomItem.getTab(itemObject, MCS.COMPERESSED_TOOLS));
										break;
									case ITEM_HOE:
										initHoe(itemObject, name, unItem, InitCustomItem.getTab(itemObject, MCS.COMPERESSED_TOOLS));
										break;
									default:
										break;
								}
							}
						}
					}
				}
			}catch(JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				e.printStackTrace();
				throw new JsonException("custom.json -> " + e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf(":") + 2));
			}
		}
	}

	public static void initBlock(JsonObject blockObject, String name, JsonElement unItemE, CreativeTabs tab) {
		ItemStack unItem = JiuUtils.item.toStack(unItemE);
		boolean lag = false;
		if(unItem == null || unItem.isEmpty()) {
			lag = true;
			InitCustom.unRegisterCustom.put(name, unItemE);
			unItem = ItemStack.EMPTY;
		}
		BaseBlockSub block = BaseBlockSub.register(name, unItem, "custom", tab);
		if(lag) {
			unSetUnItem.put(name, block);
		}
		block.setCreativeTab(tab);

		if(blockObject.has("enableDefaultRecipe")) {
			block.setMakeDefaultStackRecipe(blockObject.get("enableDefaultRecipe").getAsBoolean());
		}

		Map<Integer, ItemStack> InfoStackMap = InitCustomItem.initInfoStack(blockObject);
		if(InfoStackMap != null) {
			block.setInfoStack(InfoStackMap);
		}

		Map<Integer, List<String>> infos = InitCustomItem.initInfos(blockObject);
		if(infos != null) {
			block.addCustemInformation(infos);
		}

		Map<Integer, List<String>> shiftInfos = InitCustomItem.initShiftInfos(blockObject);
		if(shiftInfos != null) {
			block.addCustemShiftInformation(shiftInfos);
		}

		Map<Integer, HarvestType> HarvestMap = InitCustomItem.initHarvest(blockObject);
		if(HarvestMap != null) {
			block.setHarvestMap(HarvestMap);
		}

		Map<Integer, Float> HardnessMap = InitCustomItem.initHardness(blockObject);
		if(HardnessMap != null) {
			block.setHardnessMap(HardnessMap);
		}

		Map<Integer, Boolean> BeaconBaseMap = InitCustomItem.initBeaconBase(blockObject);
		if(BeaconBaseMap != null) {
			block.setBeaconBaseMap(BeaconBaseMap);
		}

		Map<Integer, Integer> LightValueMap = InitCustomItem.initLightValue(blockObject);
		if(LightValueMap != null) {
			block.setLightValueMap(LightValueMap);
		}

		Map<Integer, Float> ExplosionResistanceMap = InitCustomItem.initExplosionResistance(blockObject);
		if(ExplosionResistanceMap != null) {
			block.setExplosionResistanceMap(ExplosionResistanceMap);
		}

		Map<Integer, Boolean> UseWrenchBreakMap = InitCustomItem.initUseWrenchBreak(blockObject);
		if(UseWrenchBreakMap != null) {
			block.canUseWrenchBreak(UseWrenchBreakMap);
		}

		MCSResources.SUB_BLOCKS_NAME.add(name);
		MCSResources.SUB_BLOCKS.add(block);
		MCSResources.SUB_BLOCKS_MAP.put(name, block);
	}

	public static void initNormalItem(JsonObject itemObject, String name, ItemStack unItem, CreativeTabs tab) {
		BaseItemSub item = BaseItemSub.register(name, unItem, "custom", tab);
		if(item == null)
			return;
		item.setCreativeTab(tab);

		if(itemObject.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(itemObject.get("enableDefaultRecipe").getAsBoolean());
		}

		Map<Integer, ItemStack> InfoStackMap = InitCustomItem.initInfoStack(itemObject);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}

		Map<Integer, List<String>> infos = InitCustomItem.initInfos(itemObject);
		if(infos != null) {
			item.setCustemInformation(infos);
		}

		Map<Integer, List<String>> shiftInfos = InitCustomItem.initShiftInfos(itemObject);
		if(shiftInfos != null) {
			item.setCustemShiftInformation(shiftInfos);
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
		if(item == null)
			return;
		item.setCreativeTab(tab);

		if(itemObject.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(itemObject.get("enableDefaultRecipe").getAsBoolean());
			MCS.instance.log.info(itemObject.get("enableDefaultRecipe").getAsBoolean() + "");
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

		Map<Integer, ItemStack> InfoStackMap = InitCustomItem.initInfoStack(itemObject);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}

		Map<Integer, List<String>> infos = InitCustomItem.initInfos(itemObject);
		if(infos != null) {
			item.addCustemInformation(infos);
		}

		Map<Integer, List<String>> shiftInfos = InitCustomItem.initShiftInfos(itemObject);
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

		Map<Integer, List<CustomStuffType.PotionEffectType>> PotionEffectMap = InitCustomItem.initPotionEffectMap(itemObject);
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

	public static void initSword(JsonObject json, String name, ItemStack unItem, CreativeTabs tab) {
		BaseItemSword item = BaseItemSword.register(name, unItem, "custom", tab);
		if(item == null)
			return;
		item.setCreativeTab(tab);

		if(json.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(json.get("enableDefaultRecipe").getAsBoolean());
		}

		Map<Integer, ItemStack> InfoStackMap = InitCustomItem.initInfoStack(json);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}

		Map<Integer, List<String>> infos = InitCustomItem.initInfos(json);
		if(infos != null) {
			item.addCustemInformation(infos);
		}

		Map<Integer, List<String>> shiftInfos = InitCustomItem.initShiftInfos(json);
		if(shiftInfos != null) {
			item.addCustemShiftInformation(shiftInfos);
		}

		Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(json);
		if(HasEffectMap != null) {
			item.setHasEffectMap(HasEffectMap);
		}

		Map<Integer, ItemStack> RepairableMap = InitCustomItem.initRepairable(json);
		if(RepairableMap != null) {
			item.setRepairableMap(RepairableMap);
		}

		Map<Integer, CustomStuffType.ToolModifiersType> AttributeModifierMap = InitCustomItem.initAttributeModifier(json);
		if(AttributeModifierMap != null) {
			item.setAttributeModifierMap(AttributeModifierMap);
		}

		Map<Integer, Integer> MaxDamageMap = InitCustomItem.initMaxDamage(json);
		if(MaxDamageMap != null) {
			item.setMaxDamage(MaxDamageMap);
		}

		MCSResources.ITEMS.add(item);
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.SWORDS.add(item);
		MCSResources.SWORDS_NAME.add(name);
	}

	public static void initPickaxe(JsonObject json, String name, ItemStack unItem, CreativeTabs tab) {
		BaseItemPickaxe item = BaseItemPickaxe.register(name, unItem, "custom", tab);
		if(item == null)
			return;
		item.setCreativeTab(tab);

		if(json.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(json.get("enableDefaultRecipe").getAsBoolean());
		}

		Map<Integer, ItemStack> InfoStackMap = InitCustomItem.initInfoStack(json);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}

		Map<Integer, List<String>> infos = InitCustomItem.initInfos(json);
		if(infos != null) {
			item.addCustemInformation(infos);
		}

		Map<Integer, List<String>> shiftInfos = InitCustomItem.initShiftInfos(json);
		if(shiftInfos != null) {
			item.addCustemShiftInformation(shiftInfos);
		}

		Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(json);
		if(HasEffectMap != null) {
			item.setHasEffectMap(HasEffectMap);
		}

		Map<Integer, ItemStack> RepairableMap = InitCustomItem.initRepairable(json);
		if(RepairableMap != null) {
			item.setRepairableMap(RepairableMap);
		}

		Map<Integer, CustomStuffType.ToolModifiersType> AttributeModifierMap = InitCustomItem.initAttributeModifier(json);
		if(AttributeModifierMap != null) {
			item.setAttributeModifierMap(AttributeModifierMap);
		}

		Map<Integer, Integer> HarvestLevelMap = InitCustomItem.initHarvestLevel(json);
		if(HarvestLevelMap != null) {
			item.setHarvestLevelMap(HarvestLevelMap);
		}

		Map<Integer, Float> DestroySpeedMap = InitCustomItem.initDestroySpeed(json);
		if(DestroySpeedMap != null) {
			item.setDestroySpeedMap(DestroySpeedMap);
		}

		Map<Integer, Integer> EnchantabilityLevelMap = InitCustomItem.initEnchantabilityLevel(json);
		if(EnchantabilityLevelMap != null) {
			item.setEnchantabilityLevelMap(EnchantabilityLevelMap);
		}

		Map<Integer, Integer> MaxDamageMap = InitCustomItem.initMaxDamage(json);
		if(MaxDamageMap != null) {
			item.setMaxDamage(MaxDamageMap);
		}

		Map<Integer, List<IBlockState>> CanHarvestBlock = InitCustomItem.initCanHarvestBlock(json);;
		if(CanHarvestBlock != null) {
			item.setCanHarvestBlockMap(CanHarvestBlock);
		}

		MCSResources.ITEMS.add(item);
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.PICKAXES.add(item);
		MCSResources.PICKAXES_NAME.add(name);
	}

	public static void initShovel(JsonObject json, String name, ItemStack unItem, CreativeTabs tab) {
		BaseItemShovel item = BaseItemShovel.register(name, unItem, "custom", tab);
		if(item == null)
			return;
		item.setCreativeTab(tab);

		if(json.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(json.get("enableDefaultRecipe").getAsBoolean());
		}

		Map<Integer, ItemStack> InfoStackMap = InitCustomItem.initInfoStack(json);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}

		Map<Integer, List<String>> infos = InitCustomItem.initInfos(json);
		if(infos != null) {
			item.addCustemInformation(infos);
		}

		Map<Integer, List<String>> shiftInfos = InitCustomItem.initShiftInfos(json);
		if(shiftInfos != null) {
			item.addCustemShiftInformation(shiftInfos);
		}

		Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(json);
		if(HasEffectMap != null) {
			item.setHasEffectMap(HasEffectMap);
		}

		Map<Integer, ItemStack> RepairableMap = InitCustomItem.initRepairable(json);
		if(RepairableMap != null) {
			item.setRepairableMap(RepairableMap);
		}

		Map<Integer, CustomStuffType.ToolModifiersType> AttributeModifierMap = InitCustomItem.initAttributeModifier(json);
		if(AttributeModifierMap != null) {
			item.setAttributeModifierMap(AttributeModifierMap);
		}

		Map<Integer, Integer> EnchantabilityLevelMap = InitCustomItem.initEnchantabilityLevel(json);
		if(EnchantabilityLevelMap != null) {
			item.setEnchantabilityLevel(EnchantabilityLevelMap);
		}

		Map<Integer, Integer> MaxDamageMap = InitCustomItem.initMaxDamage(json);
		if(MaxDamageMap != null) {
			item.setMaxDamage(MaxDamageMap);
		}

		Map<Integer, List<IBlockState>> CanHarvestBlock = InitCustomItem.initCanHarvestBlock(json);;
		if(CanHarvestBlock != null) {
			item.setCanHarvestBlockMap(CanHarvestBlock);
		}

		MCSResources.ITEMS.add(item);
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.SHOVELS.add(item);
		MCSResources.SHOVEL_NAME.add(name);
	}

	public static void initAxe(JsonObject json, String name, ItemStack unItem, CreativeTabs tab) {
		BaseItemAxe item = BaseItemAxe.register(name, unItem, "custom", tab);
		if(item == null)
			return;
		item.setCreativeTab(tab);

		if(json.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(json.get("enableDefaultRecipe").getAsBoolean());
		}

		Map<Integer, ItemStack> InfoStackMap = InitCustomItem.initInfoStack(json);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}

		Map<Integer, List<String>> infos = InitCustomItem.initInfos(json);
		if(infos != null) {
			item.addCustemInformation(infos);
		}

		Map<Integer, List<String>> shiftInfos = InitCustomItem.initShiftInfos(json);
		if(shiftInfos != null) {
			item.addCustemShiftInformation(shiftInfos);
		}

		Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(json);
		if(HasEffectMap != null) {
			item.setHasEffectMap(HasEffectMap);
		}

		Map<Integer, ItemStack> RepairableMap = InitCustomItem.initRepairable(json);
		if(RepairableMap != null) {
			item.setRepairableMap(RepairableMap);
		}

		Map<Integer, CustomStuffType.ToolModifiersType> AttributeModifierMap = InitCustomItem.initAttributeModifier(json);
		if(AttributeModifierMap != null) {
			item.setAttributeModifierMap(AttributeModifierMap);
		}

		Map<Integer, Integer> EnchantabilityLevelMap = InitCustomItem.initEnchantabilityLevel(json);
		if(EnchantabilityLevelMap != null) {
			item.setEnchantabilityLevel(EnchantabilityLevelMap);
		}

		Map<Integer, Integer> MaxDamageMap = InitCustomItem.initMaxDamage(json);
		if(MaxDamageMap != null) {
			item.setMaxDamage(MaxDamageMap);
		}

		Map<Integer, List<IBlockState>> CanHarvestBlock = InitCustomItem.initCanHarvestBlock(json);;
		if(CanHarvestBlock != null) {
			item.setCanHarvestBlockMap(CanHarvestBlock);
		}

		MCSResources.ITEMS.add(item);
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.AXES.add(item);
		MCSResources.AXES_NAME.add(name);
	}

	public static void initHoe(JsonObject json, String name, ItemStack unItem, CreativeTabs tab) {
		BaseItemHoe item = BaseItemHoe.register(name, unItem, "custom", tab);
		if(item == null)
			return;
		item.setCreativeTab(tab);

		if(json.has("enableDefaultRecipe")) {
			item.setMakeDefaultStackRecipe(json.get("enableDefaultRecipe").getAsBoolean());
		}

		Map<Integer, ItemStack> InfoStackMap = InitCustomItem.initInfoStack(json);
		if(InfoStackMap != null) {
			item.setInfoStack(InfoStackMap);
		}

		Map<Integer, List<String>> infos = InitCustomItem.initInfos(json);
		if(infos != null) {
			item.addCustemInformation(infos);
		}

		Map<Integer, List<String>> shiftInfos = InitCustomItem.initShiftInfos(json);
		if(shiftInfos != null) {
			item.addCustemShiftInformation(shiftInfos);
		}

		Map<Integer, Boolean> HasEffectMap = InitCustomItem.initHasEffect(json);
		if(HasEffectMap != null) {
			item.setHasEffectMap(HasEffectMap);
		}

		Map<Integer, ItemStack> RepairableMap = InitCustomItem.initRepairable(json);
		if(RepairableMap != null) {
			item.setRepairableMap(RepairableMap);
		}

		Map<Integer, CustomStuffType.ToolModifiersType> AttributeModifierMap = InitCustomItem.initAttributeModifier(json);
		if(AttributeModifierMap != null) {
			item.setAttributeModifierMap(AttributeModifierMap);
		}

		Map<Integer, Integer> EnchantabilityLevelMap = InitCustomItem.initEnchantabilityLevel(json);
		if(EnchantabilityLevelMap != null) {
			item.setEnchantabilityLevel(EnchantabilityLevelMap);
		}

		Map<Integer, Integer> MaxDamageMap = InitCustomItem.initMaxDamage(json);
		if(MaxDamageMap != null) {
			item.setMaxDamage(MaxDamageMap);
		}

		Map<Integer, List<IBlockState>> CanHarvestBlock = InitCustomItem.initCanHarvestBlock(json);;
		if(CanHarvestBlock != null) {
			item.setCanHarvestBlockMap(CanHarvestBlock);
		}

		MCSResources.ITEMS.add(item);
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.HOES.add(item);
		MCSResources.HOES_NAME.add(name);
	}
}
