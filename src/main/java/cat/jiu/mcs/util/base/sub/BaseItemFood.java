package cat.jiu.mcs.util.base.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.config.Configs;
import cat.jiu.core.api.IHasModel;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.CompressedLevel;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSResources;
import cat.jiu.mcs.util.type.CustomStuffType;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemFood extends ItemFood implements IHasModel, ICompressedStuff {
	public static BaseItemFood register(String name, ItemStack baseItem, String ownerMod, CreativeTabs tab) {
		if(baseItem == null || baseItem.isEmpty()) return null;
		if(Loader.isModLoaded(ownerMod) || ownerMod.equals("custom")) {
			return new BaseItemFood(name, baseItem.getItem(), baseItem.getMetadata(), ownerMod, tab, true);
		}else {
			return null;
		}
	}

	public static BaseItemFood register(String name, ItemStack baseItem, String ownerMod) {
		return register(name, baseItem, ownerMod, MCS.COMPERESSED_ITEMS);
	}

	protected final String name;
	protected final CreativeTabs tab;
	protected Item baseFoodItem = null;
	protected ItemFood baseFood = null;
	protected final ItemStack unCompressedItem;
	protected final String ownerMod;
	protected final boolean baseItemIsNotFood;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);

	public BaseItemFood(String name, Item baseFood, int meta, String ownerMod, CreativeTabs tab, float saturation, boolean isWolfFood, boolean hasSubtypes) {
		super(8, saturation, isWolfFood);
		this.name = name;
		this.tab = tab;
		this.baseItemIsNotFood = !(baseFood instanceof ItemFood);

		if(this.baseItemIsNotFood) {
			this.baseFoodItem = baseFood;
			this.unCompressedItem = new ItemStack(this.baseFoodItem, 1, meta);
		}else {
			this.baseFood = (ItemFood) baseFood;
			this.unCompressedItem = new ItemStack(this.baseFood, 1, meta);
		}

		this.ownerMod = ownerMod;
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setNoRepair();
		this.setRegistryName(this.name);
		if(!ownerMod.equals("custom")) {
			MCSResources.ITEMS.add(this);
			MCSResources.STUFF_NAME.add(name);
			MCSResources.putCompressedStuff(this.unCompressedItem, this);
		}
		if(name.equalsIgnoreCase(ownerMod)) {
			throw new RuntimeException("name must not be owner mod. Name: " + name + ", OwnerMod: " + ownerMod);
		}else if(name.equalsIgnoreCase(unCompressedItem.getItem().getRegistryName().getResourceDomain())) {
			throw new RuntimeException("name must not be owner mod. Name: " + name + ", OwnerMod: " + unCompressedItem.getItem().getRegistryName().getResourceDomain());
		}
		RegisterModel.NeedToRegistryModel.add(this);
	}

	public BaseItemFood(String name, Item baseFood, int meta, String ownerMod, CreativeTabs tab, boolean isWolfFood, boolean hasSubtypes) {
		this(name, baseFood, meta, ownerMod, tab, 0.6F, isWolfFood, hasSubtypes);
	}

	public BaseItemFood(String name, Item baseFood, int meta, String ownerMod, CreativeTabs tab, boolean hasSubtypes) {
		this(name, baseFood, meta, ownerMod, tab, false, hasSubtypes);
	}

	public BaseItemFood(String name, Item baseFood, int meta, String ownerMod, CreativeTabs tab) {
		this(name, baseFood, meta, ownerMod, tab, true);
	}

	public BaseItemFood(String name, Item baseFood, int meta, String ownerMod) {
		this(name, baseFood, meta, ownerMod, MCS.COMPERESSED_ITEMS);
	}

	public BaseItemFood(String name, Item baseFood, int meta) {
		this(name, baseFood, meta, "minecraft");
	}

	public BaseItemFood(String name, Item baseFood) {
		this(name, baseFood, 0);
	}

	private final List<String> otherOredict = ICompressedStuff.super.addOtherOreDictionary();

	public BaseItemFood addOtherOreDict(String oredict) {
		this.otherOredict.add(oredict);
		return this;
	}

	@Override
	public List<String> addOtherOreDictionary() {
		return this.otherOredict;
	}

	public static boolean isFood(ItemStack stack) {
		return isFood(stack.getItem());
	}

	public static boolean isFood(Item item) {
		return item instanceof ItemFood;
	}

	public String getOwnerMod() {
		return this.ownerMod;
	}

	private boolean makeRecipe = true;

	public BaseItemFood setMakeDefaultStackRecipe(boolean makeRecipe) {
		this.makeRecipe = makeRecipe;
		return this;
	}

	boolean createOredict = true;

	public BaseItemFood createOreDictionary(boolean flag) {
		this.createOredict = flag;
		return this;
	}

	@Override
	public boolean createOreDictionary() {
		return this.createOredict;
	}

	public boolean canMakeDefaultStackRecipe() {
		return this.makeRecipe;
	}

	public String getUnCompressedName() {
		String[] unNames = JiuUtils.other.custemSplitString(this.name, "_");
		StringBuffer sb = new StringBuffer();
		for(String s : unNames) {
			if(!"compressed".equals(s)) {
				sb.append(JiuUtils.other.upperFirst(s));
			}
		}
		return sb.toString();
	}

	Boolean hasEffect = null;

	public BaseItemFood hasEffect(boolean hasEffect) {
		this.hasEffect = hasEffect;
		return this;
	}

	private Map<Integer, Boolean> HasEffectMap = Maps.newHashMap();

	public BaseItemFood setHasEffectMap(Map<Integer, Boolean> map) {
		this.HasEffectMap = map;
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		if(!this.HasEffectMap.isEmpty()) {
			if(this.HasEffectMap.containsKey(stack.getMetadata())) {
				return this.HasEffectMap.get(stack.getMetadata());
			}
		}
		if(this.hasEffect != null) {
			return this.hasEffect;
		}else if(this.baseFood != null) {
			return this.unCompressedItem.hasEffect();
		}else if(this.baseFoodItem != null) {
			return this.unCompressedItem.hasEffect();
		}
		return super.hasEffect(stack);
	}

	// Integer itemEnchantability = null;
	// public BaseItemFood setItemEnchantability(int itemEnchantability) {
	// this.itemEnchantability = itemEnchantability;
	// return this;
	// }
	//
	// private Map<Integer, Integer> EnchantabilityMap = null;
	// public BaseItemFood setEnchantabilityMap(Map<Integer, Integer> map) {
	// this.EnchantabilityMap = map;
	// return this;
	// }
	//
	// @Override
	// public int getItemEnchantability(ItemStack stack) {
	// if(this.EnchantabilityMap != null) {
	// if(this.EnchantabilityMap.containsKey(stack.getMetadata())) {
	// return this.EnchantabilityMap.get(stack.getMetadata());
	// }
	// }
	//
	// if(this.itemEnchantability != null) {
	// return this.itemEnchantability;
	// }else if(this.baseFood != null) {
	// return this.baseFood.getItemEnchantability(this.unCompressedItem);
	// }else if(this.baseFoodItem != null) {
	// return this.baseFoodItem.getItemEnchantability(this.unCompressedItem);
	// }
	// return super.getItemEnchantability(stack);
	// }

	Map<Integer, EnumRarity> RarityMap = null;

	public BaseItemFood setRarityMap(Map<Integer, EnumRarity> map) {
		this.RarityMap = map;
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IRarity getForgeRarity(ItemStack stack) {
		if(this.RarityMap != null) {
			if(!this.RarityMap.isEmpty()) {
				if(this.RarityMap.containsKey(stack.getMetadata())) {
					return this.RarityMap.get(stack.getMetadata());
				}
			}
		}
		if(!this.unCompressedItem.isEmpty()) {
			return this.unCompressedItem.getItem().getForgeRarity(this.unCompressedItem);
		}
		return super.getForgeRarity(stack);
	}

	private Map<Integer, Integer> HealAmountMap = null;

	public BaseItemFood setHealAmountMap(Map<Integer, Integer> map) {
		this.HealAmountMap = map;
		return this;
	}

	private int healAmount;
	private float saturationModifier;
	private boolean isWolfFood;

	public BaseItemFood setFoodEntry(int amount, float saturation, boolean isWolfFood) {
		this.healAmount = amount;
		this.saturationModifier = saturation;
		this.isWolfFood = isWolfFood;
		return this;
	}

	@Override
	public boolean isWolfsFavoriteMeat() {
		return this.isWolfFood;
	}

	@Override
	public int getHealAmount(ItemStack stack) {
		if(this.HealAmountMap != null) {
			if(this.HealAmountMap.containsKey(stack.getMetadata())) {
				return this.HealAmountMap.get(stack.getMetadata());
			}
		}
		if(this.baseItemIsNotFood) {
			if(Configs.Tooltip_Information.get_actual_food_amout) {
				return (int) Math.pow(this.healAmount, stack.getMetadata() + 1);
			}else {
				return (int) MCSUtil.item.getMetaValue(this.healAmount, stack.getMetadata());
			}
		}else {
			if(Configs.Tooltip_Information.get_actual_food_amout) {
				return (int) Math.pow(this.baseFood.getHealAmount(this.unCompressedItem), stack.getMetadata() + 1);
			}else {
				return (int) MCSUtil.item.getMetaValue(this.baseFood.getHealAmount(this.unCompressedItem), stack.getMetadata());
			}
		}
	}

	private Map<Integer, Float> SaturationModifierMap = null;

	public BaseItemFood setSaturationModifierMap(Map<Integer, Float> map) {
		this.SaturationModifierMap = map;
		return this;
	}

	@Override
	public float getSaturationModifier(ItemStack stack) {
		if(this.SaturationModifierMap != null) {
			if(this.SaturationModifierMap.containsKey(stack.getMetadata())) {
				return this.SaturationModifierMap.get(stack.getMetadata());
			}
		}
		if(this.baseItemIsNotFood) {
			if(Configs.Tooltip_Information.get_actual_food_amout) {
				return (float) ((double) Math.pow(this.saturationModifier, stack.getMetadata() + 1));
			}else {
				return (float) ((double) MCSUtil.item.getMetaValue(this.saturationModifier, stack.getMetadata()));
			}
		}else {
			if(Configs.Tooltip_Information.get_actual_food_amout) {
				return (float) ((double) Math.pow(this.baseFood.getSaturationModifier(this.unCompressedItem), stack.getMetadata() + 1));
			}else {
				return (float) ((double) MCSUtil.item.getMetaValue(this.baseFood.getSaturationModifier(this.unCompressedItem), stack.getMetadata()));
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("tile.mcs.compressed_" + stack.getMetadata() + ".name", stack.getMetadata()) + this.getUnCompressedItemLocalizedName();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		MCSUtil.item.getSubItems(this, tab, items);
	}

	private Map<Integer, List<CustomStuffType.PotionEffectType>> potionEffect = Maps.newHashMap();

	public BaseItemFood addPotionEffect(Map<Integer, List<CustomStuffType.PotionEffectType>> effect) {
		this.potionEffect = effect;
		return this;
	}

	public BaseItemFood addPotionEffect(int meta, CustomStuffType.PotionEffectType... effects) {
		potionEffect.put(meta, Lists.newArrayList(effects));
		return this;
	}

	public BaseItemFood addPotionEffect(int[] metas, CustomStuffType.PotionEffectType[]... effects) {
		if(metas.length == 1) {
			if(metas[0] == -1) {
				List<CustomStuffType.PotionEffectType> effect = Lists.newArrayList(effects[0]);
				for(int i = 0; i < 16; i++) {
					this.potionEffect.put(i, effect);
				}
			}else {
				this.potionEffect.put(metas[0], Lists.newArrayList(effects[0]));
			}
		}else {
			for(int i = 0; i < metas.length; i++) {
				int meta = metas[i];
				List<CustomStuffType.PotionEffectType> effect = Lists.newArrayList(effects[i]);

				this.potionEffect.put(meta, effect);
			}
		}
		return this;
	}

	ItemStack container = null;

	public BaseItemFood setContainer(ItemStack container) {
		this.container = container;
		return this;
	}

	private Map<Integer, ItemStack> ContainerMap = Maps.newHashMap();

	public BaseItemFood setContainerMap(Map<Integer, ItemStack> map) {
		this.ContainerMap = map;
		return this;
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		if(this.potionEffect != null && !world.isRemote) {
			if(this.potionEffect.containsKey(stack.getMetadata())) {
				if(this.potionEffect.get(stack.getMetadata()) != null) {
					if(!this.potionEffect.get(stack.getMetadata()).isEmpty()) {
						for(CustomStuffType.PotionEffectType buff : this.potionEffect.get(stack.getMetadata())) {
							if(buff.getEffect() != null) {
								player.addPotionEffect(buff.getEffect());
							}
						}
					}
				}
			}
		}

		if(this.ContainerMap != null) {
			if(this.ContainerMap.containsKey(stack.getMetadata())) {
				if(this.ContainerMap.get(stack.getMetadata()) != null) {
					player.addItemStackToInventory(this.ContainerMap.get(stack.getMetadata()).copy());
				}
			}
		}

		if(this.container != null) {
			if(Configs.Custom.custem_already_stuff.item.give_food_container) {
				if(this.container.getItem() instanceof BaseItemSub || this.container.getItem() instanceof BaseItemFood || this.isSubBlock(this.container)) {
					player.addItemStackToInventory(new ItemStack(this.container.getItem(), 1, stack.getMetadata()));
				}else {
					player.addItemStackToInventory(this.container.copy());
				}
			}
		}
	}

	private boolean isSubBlock(ItemStack stack) {
		if(JiuUtils.item.isBlock(stack)) {
			if(JiuUtils.item.getBlockFromItemStack(stack) instanceof BaseBlockSub) {
				return true;
			}
		}
		return false;
	}

	private List<String> shiftInfos = new ArrayList<String>();

	public BaseItemFood addCustemShiftInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			shiftInfos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseItemFood addCustemShiftInformation(List<String> infos) {
		this.shiftInfos.addAll(infos);
		return this;
	}

	private Map<Integer, List<String>> metaShiftInfos = Maps.newHashMap();

	public BaseItemFood addCustemShiftInformation(Map<Integer, List<String>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}

	private List<String> infos = new ArrayList<String>();

	public BaseItemFood addCustemInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseItemFood addCustemInformation(List<String> infos) {
		this.infos.addAll(infos);
		return this;
	}

	private Map<Integer, List<String>> metaInfos = null;

	public BaseItemFood addCustemInformation(Map<Integer, List<String>> infos) {
		this.metaInfos = infos;
		return this;
	}

	ItemStack infoStack = null;

	public BaseItemFood setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}

	private Map<Integer, ItemStack> infoStacks = null;

	public BaseItemFood setInfoStack(Map<Integer, ItemStack> infoStacks) {
		this.infoStacks = infoStacks;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();
		MCSUtil.info.addInfoStackInfo(meta, this.infoStack, world, tooltip, advanced, this.unCompressedItem, infoStacks);
		MCSUtil.info.addCompressedInfo(meta, tooltip, this.getUnCompressedItemLocalizedName(), this);

		if(MCS.test()) {
			String[] names = JiuUtils.other.custemSplitString(this.name, "_");
			tooltip.add(names.length + " : " + this.getUnCompressedName());
		}

		if(Configs.Tooltip_Information.show_owner_mod) {
			tooltip.add(I18n.format("info.mcs.owner_mod") + ": " + TextFormatting.AQUA.toString() + this.getOwnerMod());
		}

		if(Configs.Tooltip_Information.show_food_amount) {
			tooltip.add(I18n.format("info.mcs.food_amount") + ": " + this.getHealAmount(stack));
			tooltip.add(I18n.format("info.mcs.saturation_modifier") + ": " + this.getSaturationModifier(stack));
		}

		if(Configs.Tooltip_Information.show_burn_time) {
			if(this.getUnCompressedBurnTime() > 0) {
				tooltip.add(I18n.format("info.mcs.burn_time") + ": " + ForgeEventFactory.getItemBurnTime(stack));
			}
		}

		if(Configs.Tooltip_Information.show_oredict) {
			List<String> ores = JiuUtils.item.getOreDict(stack);
			if(!ores.isEmpty()) {
				tooltip.add(I18n.format("info.mcs.oredict") + ": ");
				for(String ore : ores) {
					tooltip.add("> " + ore);
				}
			}
		}

		MCSUtil.info.addMetaInfo(meta, tooltip, this.infos, this.metaInfos);
		MCSUtil.info.addShiftInfo(meta, tooltip, this.shiftInfos, this.metaShiftInfos);
	}

	@Override
	public void getItemModel() {
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			model.registerItemModel(this, meta, this.ownerMod +"/item/food/" + this.name, this.name + "." + meta);
		}
		model.registerItemModel(this, (Short.MAX_VALUE - 1), this.ownerMod +"/item/food/" + this.name, this.name + "." + (Short.MAX_VALUE - 1));
	}

	public final Item getUnCompressedItem() {
		return this.baseFoodItem;
	}

	public final ItemFood getUnCompressedItemFood() {
		return this.baseFood;
	}

	public final ItemStack getUnCompressedStack() {
		return this.unCompressedItem;
	}

	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemLocalizedName() {
		return this.unCompressedItem.getDisplayName();
	}
	
	private final CompressedLevel type = new CompressedLevel(this);
	@Override
	public CompressedLevel getLevel() {
		return this.type;
	}
}
