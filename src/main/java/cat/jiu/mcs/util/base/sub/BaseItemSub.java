package cat.jiu.mcs.util.base.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.interfaces.ICompressedStuff;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemSub extends Item implements IHasModel, ICompressedStuff{
	
	protected final String name;
	protected final CreativeTabs tab;
	protected final ItemStack unCompressedItem;
	protected final Item baseItem;
	protected final String langModID;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);
	private String model_material = null;
	
	public BaseItemSub setModelMaterial(String model_material) {
		this.model_material = model_material;
		return this;
	}
	
	public static BaseItemSub register(String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
		if(baseItem == null || baseItem.isEmpty()) {
			return null;
		}
		if(Loader.isModLoaded(langModId) || langModId.equals("custom")) {
			return new BaseItemSub(name, baseItem, tab, hasSubtypes);
		}else {
			return null;
		}
	}
	
	public static BaseItemSub register(String name, ItemStack baseItem, String langModId, CreativeTabs tab) {
		return register(name, baseItem, langModId, tab, true);
	}
	
	public static BaseItemSub register(String name, ItemStack baseItem, String langModId) {
		return register(name, baseItem, langModId, MCS.COMPERESSED_ITEMS);
	}
	
	public BaseItemSub(String name, ItemStack baseItem, CreativeTabs tab, boolean hasSubtypes) {
		this.name = name;
		this.tab = tab;
		this.unCompressedItem = baseItem;
		this.baseItem = baseItem.getItem();
		this.langModID = baseItem.getItem().getRegistryName().getResourceDomain();
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName(this.name);
		this.setNoRepair();
		if(!this.langModID.equals("custom")) {
			MCSResources.ITEMS.add(this);
			MCSResources.ITEMS_NAME.add(this.name);
			MCSResources.SUB_ITEMS.add(this);
			MCSResources.SUB_ITEMS_NAME.add(this.name);
			MCSResources.SUB_ITEMS_MAP.put(this.name, this);
		}
	}
	
	public BaseItemSub(String name, ItemStack baseItem, CreativeTabs tab) {
		this(name, baseItem, tab, true);
	}
	
	public BaseItemSub(String name, ItemStack baseItem) {
		this(name, baseItem, MCS.COMPERESSED_ITEMS);
	}
	
	public String getOwnerMod() {
		return this.langModID;
	}
	
	private boolean makeRecipe = true;
	
	public BaseItemSub setMakeDefaultStackRecipe(boolean makeRecipe) {
		this.makeRecipe = makeRecipe;
		return this;
	}
	
	public boolean canMakeDefaultStackRecipe() {
		return this.makeRecipe;
	}
	
	public String getUnCompressedName() {
		String[] unNames = JiuUtils.other.custemSplitString(this.name, "_");
		StringBuffer i = new StringBuffer();
		for(String s : unNames) {
			if(!"compressed".equals(s)) {
				i.append(JiuUtils.other.upperCaseToFirstLetter(s));
			}
		}
		return i.toString();
	}
	
	private Map<Integer, Boolean> HasEffectMap = Maps.newHashMap();
	public BaseItemSub setHasEffectMap(Map<Integer, Boolean> map) {
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
		if(this.unCompressedItem != null) {
			return this.baseItem.hasEffect(this.unCompressedItem);
		}
		return super.hasEffect(stack);
	}
	
	Map<Integer, EnumRarity> RarityMap = null;
	public BaseItemSub setRarityMap(Map<Integer, EnumRarity> map) {
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
	
	private List<String> shiftInfos = new ArrayList<String>();
	public BaseItemSub addCustemShiftInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			shiftInfos.add(custemInfo[i]);
		}
		return this;
	}
	
	public BaseItemSub addCustemShiftInformation(List<String> infos) {
		this.shiftInfos = infos;
		return this;
	}
	
	private Map<Integer, List<String>> metaShiftInfos = Maps.newHashMap();
	public BaseItemSub addCustemShiftInformation(Map<Integer, List<String>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}
	
	private List<String> infos = new ArrayList<String>();
	public BaseItemSub addCustemInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}
	
	public BaseItemSub addCustemInformation(List<String> infos) {
		this.infos = infos;
		return this;
	}
	
	private Map<Integer, List<String>> metaInfos = Maps.newHashMap();
	public BaseItemSub addCustemInformation(Map<Integer, List<String>> infos) {
		this.metaInfos = infos;
		return this;
	}
	
	ItemStack infoStack = null;
	
	public BaseItemSub setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}
	
	private Map<Integer, ItemStack> infoStacks = Maps.newHashMap();
	public BaseItemSub setInfoStack(Map<Integer, ItemStack> infoStacks) {
		this.infoStacks = infoStacks;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();
		MCSUtil.info.addInfoStackInfo(meta, this.infoStack, world, tooltip, advanced, this.unCompressedItem, infoStacks);
		MCSUtil.info.addCompressedInfo(meta, tooltip, this.getUnCompressedItemLocalizedName(), this);
		
		if(MCS.instance.test_model) {
			String[] names = JiuUtils.other.custemSplitString(this.name, "_");
			tooltip.add(names.length + "");
			tooltip.add(this.getUnCompressedName());
		}
		
		if(Configs.Tooltip_Information.show_owner_mod) {
			tooltip.add(I18n.format("info.mcs.owner_mod") + " : " + TextFormatting.AQUA.toString() + this.getOwnerMod());
		}
		
		if(Configs.Tooltip_Information.show_burn_time) {
			tooltip.add(I18n.format("info.mcs.burn_time") + ": " + ForgeEventFactory.getItemBurnTime(stack));
		}
		
		if(Configs.Tooltip_Information.show_oredict) {
			tooltip.add(I18n.format("info.mcs.oredict") + ": ");
			for(String ore : JiuUtils.item.getOreDict(stack)){
				tooltip.add("> " + ore);
			}
		}
		MCSUtil.info.addMetaInfo(meta, tooltip, this.infos, this.metaInfos);
		MCSUtil.info.addShiftInfo(meta, tooltip, this.shiftInfos, this.metaShiftInfos);
		
		if(meta == Short.MAX_VALUE) { tooltip.add("感谢喵呜玖大人的恩惠！"); }
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModel() {
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			if(this.model_material != null) {
				model.registerItemModel(this, meta, this.langModID + "/item/normal/" + this.model_material + "/" + this.name, this.name + "." + meta);
			}else {
				model.registerItemModel(this, meta, this.langModID + "/item/normal/" + this.name, this.name + "." + meta);
			}
		}
		if(this.model_material != null) {
			model.registerItemModel(this, Short.MAX_VALUE, this.langModID + "/item/normal/" + this.model_material + "/" + this.name, this.name + "." + Short.MAX_VALUE);
		}else {
			model.registerItemModel(this, Short.MAX_VALUE, this.langModID + "/item/normal/" + this.name, this.name + "." + Short.MAX_VALUE);
		}
	}
	
	public int getUnCompressedBurnTime() {
		return TileEntityFurnace.getItemBurnTime(this.unCompressedItem);
	}
	
	public final Item getUnCompressedItem(){
		return this.unCompressedItem.getItem();
	}
	
	public final ItemStack getUnCompressedStack(){
		return this.unCompressedItem;
	}
	
	public final String getOredict(int meta) {
		return JiuUtils.item.getOreDict(new ItemStack(this, 1, meta)).get(0);
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
