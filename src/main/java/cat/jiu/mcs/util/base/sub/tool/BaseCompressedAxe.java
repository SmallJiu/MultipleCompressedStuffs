package cat.jiu.mcs.util.base.sub.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.RegisterModel;
import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.api.recipe.IToolRecipe;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.exception.NonToolException;
import cat.jiu.mcs.util.CompressedLevel;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.init.MCSResources;
import cat.jiu.mcs.util.type.CustomStuffType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseCompressedAxe extends BaseItemTool.MetaAxe implements ICompressedStuff, IToolRecipe {
	public static BaseCompressedAxe register(String name, ItemStack baseItem, String ownerMod, CreativeTabs tab, ICompressedStuff craftMaterialStack, ICompressedStuff craftRodStack) {
		if(baseItem == null || baseItem.isEmpty()) return null;
		if(Loader.isModLoaded(ownerMod) || ownerMod.equals("custom")) {
			return new BaseCompressedAxe(name, baseItem, ownerMod, tab, craftMaterialStack, craftRodStack);
		}else {
			return null;
		}
	}
	
	public static BaseCompressedAxe register(String name, ItemStack baseItem, String ownerMod, CreativeTabs tab) {
		return register(name, baseItem, ownerMod, tab, null, null);
	}

	public static BaseCompressedAxe register(String name, ItemStack baseItem, String ownerMod) {
		return register(name, baseItem, ownerMod, MCS.COMPERESSED_TOOLS);
	}

	protected final ItemStack baseToolStack;
	protected final ItemAxe baseTool;
	protected final float baseAttackDamage;
	protected final float baseAttackSpeed;
	protected final String ownerMod;
	protected final ICompressedStuff craftMaterialStack;
	protected final ICompressedStuff craftRodStack;
	public BaseCompressedAxe(String name, ItemStack baseTool, String ownerMod, CreativeTabs tab) {
		this(name, baseTool, ownerMod, tab, null, null);
	}
	public BaseCompressedAxe(String name, ItemStack baseTool, String ownerMod, CreativeTabs tab, ICompressedStuff craftMaterialStack) {
		this(name, baseTool, ownerMod, tab, craftMaterialStack, MCSItems.minecraft.normal.C_STICK_I);
	}
	public BaseCompressedAxe(String name, ItemStack baseTool, String ownerMod, CreativeTabs tab, ICompressedStuff craftMaterialStack, ICompressedStuff craftRodStack) {
		super(MCS.MODID, name, tab, true, getToolMaterial(baseTool), ModSubtypes.values());
		this.baseToolStack = baseTool;
		if(baseTool.getItem() instanceof ItemAxe) {
			this.baseTool = (ItemAxe) baseTool.getItem();
		}else {
			this.baseTool = null;
			throw new NonToolException(baseTool, "Axe");
		}
		this.ownerMod = ownerMod;
		this.baseAttackDamage = 3.0F + this.baseTool.attackDamage;
		this.baseAttackSpeed = this.baseTool.attackSpeed;
		this.setMaxMetadata(16);
		this.craftMaterialStack = craftMaterialStack;
		this.craftRodStack = craftRodStack;

		if(!ownerMod.equals("custom")) {
			MCSResources.ITEMS.add(this);
			MCSResources.STUFF_NAME.add(name);
			MCSResources.putCompressedStuff(this.baseToolStack, this);
		}
		if(name.equalsIgnoreCase(ownerMod)) {
			throw new RuntimeException("name must not be owner mod. Name: " + name + ", OwnerMod: " + ownerMod);
		}else if(name.equalsIgnoreCase(baseTool.getItem().getRegistryName().getResourceDomain())) {
			throw new RuntimeException("name must not be owner mod. Name: " + name + ", OwnerMod: " + baseTool.getItem().getRegistryName().getResourceDomain());
		}
	}
	public BaseCompressedAxe(String name, ItemStack baseTool, ICompressedStuff craftMaterialStack, ICompressedStuff craftRodStack) {
		this(name, baseTool, baseTool.getItem().getRegistryName().getResourceDomain(), MCS.COMPERESSED_TOOLS, craftMaterialStack, craftRodStack);
	}
	public BaseCompressedAxe(String name, ItemStack baseTool, ICompressedStuff craftMaterialStack) {
		this(name, baseTool, baseTool.getItem().getRegistryName().getResourceDomain(), MCS.COMPERESSED_TOOLS, craftMaterialStack);
	}
	public BaseCompressedAxe(String name, ItemStack baseTool) {
		this(name, baseTool, baseTool.getItem().getRegistryName().getResourceDomain(), MCS.COMPERESSED_TOOLS);
	}

	private static ToolMaterial getToolMaterial(ItemStack baseItem) {
		if(baseItem.getItem() instanceof ItemAxe) {
			return ((ItemAxe) baseItem.getItem()).toolMaterial;
		}
		return ToolMaterial.WOOD;
	}

	protected Map<Integer, Integer> EnchantabilityLevelMap = Maps.newHashMap();

	public BaseCompressedAxe setEnchantabilityLevel(Map<Integer, Integer> EnchantabilityLevelMap) {
		this.EnchantabilityLevelMap = EnchantabilityLevelMap;
		return this;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		if(!this.EnchantabilityLevelMap.isEmpty() && this.EnchantabilityLevelMap.containsKey(stack.getMetadata())) {
			return this.EnchantabilityLevelMap.get(stack.getMetadata());
		}
		int enchantability = this.baseTool.getItemEnchantability(this.baseToolStack);
		return (int) MCSUtil.item.getMetaValue(enchantability, stack);
	}

	protected Map<Integer, ItemStack> RepairableMap = Maps.newHashMap();

	public BaseCompressedAxe setRepairableMap(Map<Integer, ItemStack> RepairableMap) {
		this.RepairableMap = RepairableMap;
		return this;
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		boolean lag = false;
		if(!this.RepairableMap.isEmpty() && this.RepairableMap.containsKey(toRepair.getMetadata())) {
			lag = JiuUtils.item.equalsStack(this.RepairableMap.get(toRepair.getMetadata()), repair);
		}
		return this.baseTool.getIsRepairable(this.baseToolStack, repair) || lag;
	}

	protected Map<Integer, Float> DestroySpeedMap = Maps.newHashMap();

	public BaseCompressedAxe setDestroySpeed(Map<Integer, Float> DestroySpeedMap) {
		this.DestroySpeedMap = DestroySpeedMap;
		return this;
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		if(!this.DestroySpeedMap.isEmpty() && this.DestroySpeedMap.containsKey(stack.getMetadata())) {
			return this.DestroySpeedMap.get(stack.getMetadata());
		}
		float base = this.baseTool.getDestroySpeed(this.baseToolStack, state);
		return (float) MCSUtil.item.getMetaValue(base, stack);
	}

	protected Map<Integer, Integer> MaxDamageMap = Maps.newHashMap();
	public BaseCompressedAxe setMaxDamage(Map<Integer, Integer> MaxDamageMap) {
		this.MaxDamageMap = MaxDamageMap;
		return this;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		if(stack.getMetadata() >= 32766) return 10104;
		if(!this.MaxDamageMap.isEmpty() && this.MaxDamageMap.containsKey(stack.getMetadata())) {
			return this.MaxDamageMap.get(stack.getMetadata());
		}
		return (int) MCSUtil.item.getMetaValue(this.baseToolStack.getMaxDamage(), stack.getMetadata());
	}

	Map<Integer, CustomStuffType.ToolModifiersType> AttributeModifierMap = Maps.newHashMap();

	public BaseCompressedAxe setAttributeModifierMap(Map<Integer, CustomStuffType.ToolModifiersType> attributeModifierMap) {
		this.AttributeModifierMap = attributeModifierMap;
		return this;
	}

	public BaseCompressedAxe addAttributeModifierMap(int meta, double speed, double damage) {
		this.AttributeModifierMap.put(meta, new CustomStuffType.ToolModifiersType(speed, damage));
		return this;
	}

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();

		if(slot == EntityEquipmentSlot.MAINHAND) {
			boolean lag = false;
			if(!this.AttributeModifierMap.isEmpty() && this.AttributeModifierMap.containsKey(stack.getMetadata())) {
				CustomStuffType.ToolModifiersType type = this.AttributeModifierMap.get(stack.getMetadata());
				lag = true;
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", type.damage, 0));
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", type.speed, 0));
			}
			if(!lag) {
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", MCSUtil.item.getMetaValue(this.baseAttackDamage, stack.getMetadata()), 0));
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double) ((stack.getMetadata() + 1) * 0.0973) - 2.43D, 0));
			}
		}
		return multimap;
	}

	protected Map<Integer, Integer> HarvestLevelMap = Maps.newHashMap();
	public BaseCompressedAxe setHarvestLevelMap(Map<Integer, Integer> HarvestLevelMap) {
		this.HarvestLevelMap = HarvestLevelMap;
		return this;
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		if(!this.HarvestLevelMap.isEmpty() && this.HarvestLevelMap.containsKey(stack.getMetadata())) {
			return this.HarvestLevelMap.get(stack.getMetadata());
		}
		int level = this.baseTool.getHarvestLevel(stack, toolClass, player, blockState);
		level += (int) ((stack.getMetadata() + 1) * 0.339341);
		return level;
	}

	protected Map<Integer, List<IBlockState>> CanHarvestBlock = Maps.newHashMap();
	public BaseCompressedAxe setCanHarvestBlockMap(Map<Integer, List<IBlockState>> CanHarvestBlock) {
		this.CanHarvestBlock = CanHarvestBlock;
		return this;
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		boolean lag = false;
		if(this.CanHarvestBlock.containsKey(stack.getMetadata())) {
			lag = this.CanHarvestBlock.get(stack.getMetadata()).contains(state);
		}
		return this.baseTool.canHarvestBlock(state, this.baseToolStack) || lag;
	}

	Map<Integer, EnumRarity> RarityMap = null;

	public BaseCompressedAxe setRarityMap(Map<Integer, EnumRarity> map) {
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
		if(!this.baseToolStack.isEmpty()) {
			return this.baseToolStack.getItem().getForgeRarity(this.baseToolStack);
		}
		return super.getForgeRarity(stack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("tile.mcs.compressed_" + stack.getMetadata() + ".name", stack.getMetadata()) + this.getUnCompressedItemLocalizedName();
	}

	private Map<Integer, Boolean> HasEffectMap = Maps.newHashMap();

	public BaseCompressedAxe setHasEffectMap(Map<Integer, Boolean> map) {
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
		if(this.baseTool != null) {
			return this.baseTool.hasEffect(this.baseToolStack);
		}
		return super.hasEffect(stack);
	}

	private List<String> shiftInfos = new ArrayList<String>();

	public BaseCompressedAxe addCustemShiftInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			shiftInfos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseCompressedAxe addCustemShiftInformation(List<String> infos) {
		this.shiftInfos = infos;
		return this;
	}

	private Map<Integer, List<String>> metaShiftInfos = Maps.newHashMap();

	public BaseCompressedAxe addCustemShiftInformation(Map<Integer, List<String>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}

	private List<String> infos = new ArrayList<String>();

	public BaseCompressedAxe addCustemInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseCompressedAxe addCustemInformation(List<String> infos) {
		this.infos = infos;
		return this;
	}

	private Map<Integer, List<String>> metaInfos = Maps.newHashMap();

	public BaseCompressedAxe addCustemInformation(Map<Integer, List<String>> infos) {
		this.metaInfos = infos;
		return this;
	}

	ItemStack infoStack = null;
	public BaseCompressedAxe setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}

	private Map<Integer, ItemStack> infoStacks = Maps.newHashMap();
	public BaseCompressedAxe setInfoStack(Map<Integer, ItemStack> infoStacks) {
		this.infoStacks = infoStacks;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();
		MCSUtil.info.addInfoStackInfo(meta, this.infoStack, world, tooltip, advanced, this.baseToolStack, infoStacks);
		MCSUtil.info.addCompressedInfo(meta, tooltip, this.getUnCompressedItemLocalizedName(), this);

		if(Configs.Tooltip_Information.show_owner_mod) {
			tooltip.add(I18n.format("info.mcs.owner_mod") + " : " + TextFormatting.AQUA.toString() + this.getOwnerMod());
		}

		if(MCS.dev()) {
			tooltip.add("最大耐久: " + this.getMaxDamage(stack));
		}

		MCSUtil.info.addMetaInfo(meta, tooltip, this.infos, this.metaInfos);
		MCSUtil.info.addShiftInfo(meta, tooltip, this.shiftInfos, this.metaShiftInfos);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		MCSUtil.item.getSubItems(this, tab, items);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getItemModel(RegisterModel util) {
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			util.registerItemModel(this, meta, this.ownerMod + "/item/tools/axe/" + this.name, this.name + "." + meta);
		}
		util.registerItemModel(this, (Short.MAX_VALUE - 1), this.ownerMod + "/item/tools/axe/" + this.name, this.name + "." + (Short.MAX_VALUE - 1));
	}
	
	@Override
	public void setDamage(ItemStack stack, int damage) {
		if(stack.getMetadata() < 32766) {
			super.setDamage(stack, damage);
		}
	}
	
	@Override
	public void damageItem(ItemStack stack, int amount, EntityLivingBase entity) {
		if(stack.getMetadata() < 32766) {
			super.damageItem(stack, amount, entity);
		}
	}

	public String getOwnerMod() {
		return this.ownerMod;
	}

	public final Item getUnCompressedItem() {
		return this.baseToolStack.getItem();
	}

	public final ItemStack getUnCompressedStack() {
		return this.baseToolStack;
	}

	private boolean makeRecipe = true;

	public BaseCompressedAxe setMakeDefaultStackRecipe(boolean makeRecipe) {
		this.makeRecipe = makeRecipe;
		return this;
	}

	public boolean canMakeDefaultStackRecipe() {
		return this.makeRecipe;
	}

	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemLocalizedName() {
		return this.baseToolStack.getDisplayName();
	}

	@Override
	public String getUnCompressedName() {
		String[] unNames = JiuUtils.other.custemSplitString(this.name, "_");
		StringBuffer i = new StringBuffer();
		for(String s : unNames) {
			if(!"compressed".equals(s)) {
				i.append(JiuUtils.other.upperFirst(s));
			}
		}
		return i.toString();
	}
	private final CompressedLevel type = new CompressedLevel(this);
	@Override
	public CompressedLevel getLevel() {
		return this.type;
	}
	@Override
	public boolean canCreateRecipe(int meta) {
		return this.craftMaterialStack!=null && this.craftRodStack!=null;
	}
	@Override
	public ItemStack getMaterial(int meta) {
		if(this.craftMaterialStack.isBlock() && (this.craftMaterialStack.isHas() || this.craftMaterialStack.getAsCompressedBlock().baseRecipeHasItem())) {
			if(meta <= 0) {
				return this.craftMaterialStack.getUnCompressedStack();
			}else {
				return this.craftMaterialStack.getStack(meta-1);
			}
		}
		return this.craftMaterialStack.getStack(meta);
	}
	@Override
	public ItemStack getRod(int meta) {
		if(this.craftRodStack.isBlock() && (this.craftRodStack.isHas() || this.craftRodStack.getAsCompressedBlock().baseRecipeHasItem())) {
			if(meta <= 0) {
				return this.craftRodStack.getUnCompressedStack();
			}else {
				return this.craftRodStack.getStack(meta-1);
			}
		}
		return this.craftRodStack.getStack(meta);
	}
}
