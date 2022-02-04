//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.util.base;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.type.PotionEffectType;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemFood extends ItemFood implements IHasModel {
	public static BaseItemFood register(String name, ItemStack baseItem, String langModId, CreativeTabs tab) {
		if(Loader.isModLoaded(langModId) || langModId.equals("custom")) {
			if(baseItem != null) {
				return new BaseItemFood(name, baseItem.getItem(), baseItem.getMetadata(), langModId, tab, true);
			}else {
				return null;
			}
		}else {
			return null;
		}
	}
	
	public static BaseItemFood register(String name, ItemStack baseItem, String langModId) {
		return register(name, baseItem, langModId, MCS.COMPERESSED_ITEMS);
	}
	
	protected final String name;
	protected final CreativeTabs tab;
	protected Item baseFoodItem = null;
	protected ItemFood baseFood = null;
	protected final ItemStack unCompressedItem;
	protected final String langModId;
	protected final boolean baseItemIsNotFood;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId, CreativeTabs tab, float saturation, boolean isWolfFood, boolean hasSubtypes) {
		super(8, saturation, isWolfFood);
		this.name = name;
		this.tab = tab;
		this.baseItemIsNotFood = !(baseFood instanceof ItemFood);
		
		if(this.baseItemIsNotFood) {
			this.baseFoodItem = baseFood;
			this.unCompressedItem = new ItemStack(this.baseFoodItem, 1, meta);
		}else {
			this.baseFood = (ItemFood)baseFood;
			this.unCompressedItem = new ItemStack(this.baseFood, 1, meta);
		}
		
		this.langModId = langModId;
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setNoRepair();
		this.setRegistryName(this.name);
		if(!langModId.equals("custom")) {
			MCSItems.ITEMS_NAME.add(name);
			MCSItems.FOODS_NAME.add(name);
			MCSItems.ITEMS.add((Item)this);
			MCSItems.FOODS.add(this);
			MCSItems.FOODS_MAP.put(this.name, this);
		}
	}
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId, CreativeTabs tab,  boolean isWolfFood, boolean hasSubtypes) {
		this(name, baseFood, meta, langModId, tab, 0.6F, isWolfFood, hasSubtypes);
	}
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId, CreativeTabs tab,  boolean hasSubtypes) {
		this(name, baseFood, meta, langModId, tab, false, hasSubtypes);
	}
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId, CreativeTabs tab) {
		this(name, baseFood, meta, langModId, tab, true);
	}
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId) {
		this(name, baseFood, meta, langModId, MCS.COMPERESSED_ITEMS);
	}
	
	public BaseItemFood(String name, Item baseFood, int meta) {
		this(name, baseFood, meta, "minecraft");
	}
	
	public BaseItemFood(String name, Item baseFood) {
		this(name, baseFood, 0);
	}
	
	public static boolean isFood(ItemStack stack) {
		return isFood(stack.getItem());
	}
	
	public static boolean isFood(Item item) {
		return item instanceof ItemFood;
	}
	
	public String getOwnerMod() {
		return this.langModId;
	}
	
	private boolean makeRecipe = true;
	
	public BaseItemFood setMakeDefaultStackRecipe(boolean makeRecipe) {
		this.makeRecipe = makeRecipe;
		return this;
	}
	
	public boolean canMakeDefaultStackRecipe() {
		return this.makeRecipe;
	}
	
	public String getUnCompressedName() {
		String[] unNames = JiuUtils.other.custemSplitString(this.name, "_");
		StringBuffer sb = new StringBuffer();
		for(String s : unNames) {
			if(!"compressed".equals(s)) {
				sb.append(JiuUtils.other.upperCaseToFirstLetter(s));
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
			return this.baseFoodItem.hasEffect(this.unCompressedItem);
		}
		return super.hasEffect(stack);
	}
	
//	Integer itemEnchantability = null;
//	public BaseItemFood setItemEnchantability(int itemEnchantability) {
//		this.itemEnchantability = itemEnchantability;
//		return this;
//	}
//	
//	private Map<Integer, Integer> EnchantabilityMap = null;
//	public BaseItemFood setEnchantabilityMap(Map<Integer, Integer> map) {
//		this.EnchantabilityMap = map;
//		return this;
//	}
//	
//	@Override
//	public int getItemEnchantability(ItemStack stack) {
//		if(this.EnchantabilityMap != null) {
//			if(this.EnchantabilityMap.containsKey(stack.getMetadata())) {
//				return this.EnchantabilityMap.get(stack.getMetadata());
//			}
//		}
//		
//		if(this.itemEnchantability != null) {
//			return this.itemEnchantability;
//		}else if(this.baseFood != null) {
//			return this.baseFood.getItemEnchantability(this.unCompressedItem);
//		}else if(this.baseFoodItem != null) {
//			return this.baseFoodItem.getItemEnchantability(this.unCompressedItem);
//		}
//		return super.getItemEnchantability(stack);
//	}
	
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
			if(Configs.TooltipInformation.get_actual_food_amout) {
				return (int) Math.pow(this.healAmount, stack.getMetadata() + 1);
			}else {
				return (int)(this.healAmount * ((stack.getMetadata() + 1) * 9) * 0.5);
			}
		}else {
			if(Configs.TooltipInformation.get_actual_food_amout) {
				return (int) Math.pow(this.baseFood.getHealAmount(this.unCompressedItem), stack.getMetadata() + 1);
			}else {
				return (int)(this.baseFood.getHealAmount(this.unCompressedItem) * ((stack.getMetadata() + 1) * 9) * 0.5);
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
			if(Configs.TooltipInformation.get_actual_food_amout) {
				return (float) Math.pow(this.saturationModifier, stack.getMetadata() + 1);
			}else {
				return this.saturationModifier * ((stack.getMetadata() + 1) * 9);
			}
		}else {
			if(Configs.TooltipInformation.get_actual_food_amout) {
				return (float) Math.pow(this.baseFood.getSaturationModifier(this.unCompressedItem), stack.getMetadata() + 1);
			}else {
				return this.baseFood.getSaturationModifier(this.unCompressedItem) * ((stack.getMetadata() + 1) * 9);
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
		if(this.getHasSubtypes()){
			if(this.isInCreativeTab(tab)){
				for(ModSubtypes type : ModSubtypes.values()) {
					items.add(new ItemStack(this, 1, type.getMeta()));
				}
			}
		}else{
			super.getSubItems(tab, items);
		}
	}
	
	private Map<Integer, List<PotionEffectType>> potionEffect = Maps.newHashMap();
	public BaseItemFood addPotionEffect(Map<Integer, List<PotionEffectType>> effect) {
		this.potionEffect = effect;
		return this;
	}
	
	public BaseItemFood addPotionEffect(int[] metas, PotionEffectType[]... effects) {
		if(metas.length == 1 && metas[0] == -1) {
			List<PotionEffectType> effect = JiuUtils.other.copyArrayToList(effects[0]);
			for(int i = 0; i < 16; i++) {
				this.potionEffect.put(i, effect);
			}
		}else {
			for(int i = 0; i < metas.length; i++) {
				int meta = metas[i];
				List<PotionEffectType> effect = JiuUtils.other.copyArrayToList(effects[i]);
				
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
	
	private Map<Integer, ItemStack> ContainerMap = null;
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
						for(PotionEffectType buff : this.potionEffect.get(stack.getMetadata())) {
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
        		if(this.container.getItem() instanceof BaseItemSub
        		|| this.container.getItem() instanceof BaseItemFood
        		|| this.isSubBlock(this.container)) {
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
		this.shiftInfos = infos;
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
		this.infos = infos;
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
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced){
		int meta = stack.getMetadata();
		int level = meta + 1;
		
		if(this.infoStack != null) {
			tooltip.add(this.infoStack.toString());
			this.infoStack.getItem().addInformation(this.infoStack, world, tooltip, advanced);
		}else if(this.infoStacks != null){
			if(this.infoStacks.containsKey(meta)) {
				if(this.infoStacks.get(meta) != null) {
					this.infoStacks.get(meta).getItem().addInformation(this.infoStacks.get(meta), world, tooltip, advanced);
				}
			}else {
				this.unCompressedItem.getItem().addInformation(this.unCompressedItem, world, tooltip, advanced);
			}
		}else {
			this.unCompressedItem.getItem().addInformation(this.unCompressedItem, world, tooltip, advanced);
		}
		
		if(Configs.use_3x3_recipes) {
			if(Configs.TooltipInformation.show_specific_number) {
				if(!Configs.TooltipInformation.can_custom_specific_number) {
					if(meta == 0) { tooltip.add("9 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 1) { tooltip.add("81 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 2) { tooltip.add("729 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 3) { tooltip.add("6,561 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 4) { tooltip.add("59,049 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 5) { tooltip.add("531,441 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 6) { tooltip.add("4,782,969 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 7) { tooltip.add("43,046,721 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 8) { tooltip.add("387,420,489 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 9) { tooltip.add("3,486,784,401 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 10) { tooltip.add("31,381,059,609 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 11) { tooltip.add("282,429,536,481 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 12) { tooltip.add("2,541,865,828,329 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 13) { tooltip.add("22,876,792,454,961 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 14) { tooltip.add("205,891,132,094,649 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 15) { tooltip.add("1,853,020,188,851,841 x " + this.getUnCompressedItemLocalizedName()); }
				}else {
					tooltip.add(I18n.format("info.compressed_3x3_" + level + ".name", 1) + " x " + this.getUnCompressedItemLocalizedName());
				}
			}else {
				if(level >= 100) {
					tooltip.add(Math.pow(9, level) + " x " + this.getUnCompressedItemLocalizedName());
				}else {
					tooltip.add(new BigInteger("9").pow(level) + " x " + this.getUnCompressedItemLocalizedName());
				}
				
			}
		}else {
			if(Configs.TooltipInformation.show_specific_number) {
				if(!Configs.TooltipInformation.can_custom_specific_number) {
					if(meta == 0) { tooltip.add("4 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 1) { tooltip.add("16 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 2) { tooltip.add("64 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 3) { tooltip.add("256 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 4) { tooltip.add("1,024 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 5) { tooltip.add("4,096 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 6) { tooltip.add("16,384 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 7) { tooltip.add("65,536 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 8) { tooltip.add("262,144 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 9) { tooltip.add("1,048,876 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 10) { tooltip.add("4,194,304 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 11) { tooltip.add("16,777,216 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 12) { tooltip.add("67,108,864 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 13) { tooltip.add("268,435,456 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 14) { tooltip.add("1,073,741,824 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 15) { tooltip.add("4,294,967,296 x " + this.getUnCompressedItemLocalizedName()); }
				}else {
					tooltip.add(I18n.format("info.compressed_2x2_" + level + ".name", 1) + " x " + this.getUnCompressedItemLocalizedName());
				}
			}else {
				if(level >= 100) {
					tooltip.add(Math.pow(4, level) + " x " + this.getUnCompressedItemLocalizedName());
				}else {
					tooltip.add(new BigInteger("4").pow(level) + " x " + this.getUnCompressedItemLocalizedName());
				}
			}
		}
		
		if(MCS.instance.test_model) {
			String[] names = JiuUtils.other.custemSplitString(this.name, "_");
			tooltip.add(names.length+" : "+this.getUnCompressedName());
		}
		
		if(Configs.TooltipInformation.show_owner_mod) {
			tooltip.add("Owner Mod: \'" + this.getOwnerMod() + "\'");
		}
		
		if(Configs.TooltipInformation.show_food_amount) {
			tooltip.add("FoodAmount: " + this.getHealAmount(stack));
			tooltip.add("SaturationModifier: " + this.getSaturationModifier(stack));
		}
		
		if(Configs.TooltipInformation.show_burn_time) {
			tooltip.add("BurnTime: " + ForgeEventFactory.getItemBurnTime(stack));
		}
		
		if(Configs.TooltipInformation.show_oredict) {
			tooltip.add("OreDictionary: ");
			for(String ore : JiuUtils.item.getOreDict(stack)){
				tooltip.add("> " + ore);
			}
		}
		
		if(!this.infos.isEmpty()) {
			for(String info : this.infos) {
				tooltip.add(info);
			}
		}else if(this.metaInfos != null) {
			if(!this.metaInfos.isEmpty()) {
				if(this.metaInfos.containsKey(meta)) {
					for(String str : this.metaInfos.get(meta)) {
						tooltip.add(str);
					}
				}
			}
		}
		
		if(!this.shiftInfos.isEmpty() || !this.metaShiftInfos.isEmpty()) {
			if(!this.shiftInfos.isEmpty()) {
				
				if(GuiScreen.isShiftKeyDown()) {
					for (String info : this.shiftInfos) {
						tooltip.add(info);
					}
				}else {
					tooltip.add(I18n.format("info.mcs.shift"));
				}
			}
			if(!this.metaShiftInfos.isEmpty()) {
				if(this.metaShiftInfos.containsKey(meta)) {
					if(GuiScreen.isShiftKeyDown()) {
						for(String info : this.metaShiftInfos.get(meta)) {
							tooltip.add(info);
						}
					}else {
						tooltip.add(I18n.format("info.mcs.shift"));
					}
				}
			}
		}
		
		if(meta == Short.MAX_VALUE) { tooltip.add("感谢喵呜玖大人的恩惠�?"); }
	}

	@Override
	public void registerItemModel() {
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			model.registerItemModel(this, meta, this.langModId + "/item/food/" + this.name, this.name + "." + meta);
		}
		model.registerItemModel(this, Short.MAX_VALUE, this.langModId + "/item/food/" + this.name, this.name + "." + Short.MAX_VALUE);
	}
	
	public final Item getUnCompressedItem(){
		return this.baseFoodItem;
	}
	
	public final ItemFood getUnCompressedItemFood(){
		return this.baseFood;
	}
	
	public final ItemStack getUnCompressedStack(){
		return this.unCompressedItem;
	}
	
	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemLocalizedName() {
		return this.unCompressedItem.getDisplayName();
	}
	
	public final ItemStack Level_1 = new ItemStack(this, 1, 0);
	public final ItemStack Level_2 = new ItemStack(this, 1, 1);
	public final ItemStack Level_3 = new ItemStack(this, 1, 2);
	public final ItemStack Level_4 = new ItemStack(this, 1, 3);
	public final ItemStack Level_5 = new ItemStack(this, 1, 4);
	public final ItemStack Level_6 = new ItemStack(this, 1, 5);
	public final ItemStack Level_7 = new ItemStack(this, 1, 6);
	public final ItemStack Level_8 = new ItemStack(this, 1, 7);
	public final ItemStack Level_9 = new ItemStack(this, 1, 8);
	public final ItemStack Level_10 = new ItemStack(this, 1, 9);
	public final ItemStack Level_11 = new ItemStack(this, 1, 10);
	public final ItemStack Level_12 = new ItemStack(this, 1, 11);
	public final ItemStack Level_13 = new ItemStack(this, 1, 12);
	public final ItemStack Level_14 = new ItemStack(this, 1, 13);
	public final ItemStack Level_15 = new ItemStack(this, 1, 14);
	public final ItemStack Level_16 = new ItemStack(this, 1, 15);
}
