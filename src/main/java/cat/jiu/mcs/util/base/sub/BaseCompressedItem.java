package cat.jiu.mcs.util.base.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.api.recipe.ISmeltingRecipe;
import cat.jiu.mcs.config.Configs;
import cat.jiu.core.api.IHasModel;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.CompressedLevel;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseCompressedItem extends Item implements IHasModel, ICompressedStuff, ISmeltingRecipe {
	protected final String name;
	protected final CreativeTabs tab;
	protected final ItemStack unCompressedItem;
	protected final Item baseItem;
	protected final String ownerMod;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);
	private String model_material = null;

	public BaseCompressedItem setModelMaterial(String model_material) {
		this.model_material = model_material;
		return this;
	}

	public static BaseCompressedItem register(String name, ItemStack baseItem, String ownerMod, CreativeTabs tab, boolean hasSubtypes) {
		if(baseItem == null || baseItem.isEmpty()) return null;
		if(Loader.isModLoaded(ownerMod) || ownerMod.equals("custom")) {
			return new BaseCompressedItem(name, baseItem, ownerMod, tab, hasSubtypes);
		}else {
			return null;
		}
	}

	public static BaseCompressedItem register(String name, ItemStack baseItem, String ownerMod, CreativeTabs tab) {
		return register(name, baseItem, ownerMod, tab, true);
	}

	public static BaseCompressedItem register(String name, ItemStack baseItem, String ownerMod) {
		return register(name, baseItem, ownerMod, MCS.COMPERESSED_ITEMS);
	}

	public BaseCompressedItem(String name, ItemStack baseItem, String ownerMod, CreativeTabs tab, boolean hasSubtypes) {
		this.name = name;
		this.tab = tab;
		this.unCompressedItem = baseItem;
		this.baseItem = baseItem.getItem();
		this.ownerMod = ownerMod;
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName(this.name);
		this.setNoRepair();
		if(!this.ownerMod.equals("custom")) {
			MCSResources.ITEMS.add(this);
			MCSResources.STUFF_NAME.add(name);
			MCSResources.putCompressedStuff(this.unCompressedItem, this);
		}
		if(name.equalsIgnoreCase(ownerMod)) {
			throw new RuntimeException("name must not be owner mod. Name: " + name + ", OwnerMod: " + ownerMod);
		}else if(name.equalsIgnoreCase(unCompressedItem.getItem().getRegistryName().getResourceDomain())) {
			throw new RuntimeException("name must not be owner mod. Name: " + name + ", OwnerMod: " + unCompressedItem.getItem().getRegistryName().getResourceDomain());
		}
		RegisterModel.addNeedRegistryModel(MCS.MODID, this);
	}
	
	public BaseCompressedItem(String name, ItemStack baseItem, String ownerMod, CreativeTabs tab) {
		this(name, baseItem, ownerMod, tab, true);
	}

	public BaseCompressedItem(String name, ItemStack baseItem, CreativeTabs tab) {
		this(name, baseItem, baseItem.getItem().getRegistryName().getResourceDomain(), tab);
	}

	public BaseCompressedItem(String name, ItemStack baseItem) {
		this(name, baseItem, MCS.COMPERESSED_ITEMS);
	}

	public String getOwnerMod() {
		return this.ownerMod;
	}

	private final List<String> otherOredict = ICompressedStuff.super.getOtherOreDictionary();

	public BaseCompressedItem addOtherOreDict(String oredict) {
		this.otherOredict.add(oredict);
		return this;
	}

	@Override
	public List<String> getOtherOreDictionary() {
		return this.otherOredict;
	}

	private boolean makeRecipe = true;

	public BaseCompressedItem setMakeDefaultStackRecipe(boolean makeRecipe) {
		this.makeRecipe = makeRecipe;
		return this;
	}

	public boolean canMakeDefaultStackRecipe() {
		return this.makeRecipe;
	}

	boolean createOredict = true;

	public BaseCompressedItem createOreDictionary(boolean flag) {
		this.createOredict = flag;
		return this;
	}

	@Override
	public boolean createOreDictionary() {
		return this.createOredict;
	}

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

	private Map<Integer, Boolean> HasEffectMap = Maps.newHashMap();

	public BaseCompressedItem setHasEffectMap(Map<Integer, Boolean> map) {
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

	Map<Integer, IRarity> RarityMap = null;

	public BaseCompressedItem setRarityMap(Map<Integer, IRarity> map) {
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
		MCSUtil.item.getSubItems(this, tab, items);
	}

	private List<String> shiftInfos = new ArrayList<String>();

	public BaseCompressedItem addCustemShiftInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			shiftInfos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseCompressedItem setCustemShiftInformation(List<String> infos) {
		this.shiftInfos = infos;
		return this;
	}

	private Map<Integer, List<String>> metaShiftInfos = Maps.newHashMap();

	public BaseCompressedItem setCustemShiftInformation(Map<Integer, List<String>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}

	private List<String> infos = new ArrayList<String>();

	public BaseCompressedItem addCustemInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseCompressedItem setCustemInformation(List<String> infos) {
		this.infos = infos;
		return this;
	}

	private Map<Integer, List<String>> metaInfos = Maps.newHashMap();

	public BaseCompressedItem setCustemInformation(Map<Integer, List<String>> infos) {
		this.metaInfos = infos;
		return this;
	}

	ItemStack infoStack = null;

	public BaseCompressedItem setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}

	private Map<Integer, ItemStack> infoStacks = Maps.newHashMap();

	public BaseCompressedItem setInfoStack(Map<Integer, ItemStack> infoStacks) {
		this.infoStacks = infoStacks;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();
		MCSUtil.info.addInfoStackInfo(meta, this.infoStack, world, tooltip, advanced, this.unCompressedItem, infoStacks);
		MCSUtil.info.addCompressedInfo(meta, tooltip, this.getUnCompressedItemLocalizedName(), this);

		if(MCS.dev()) {
			tooltip.add(JiuUtils.other.custemSplitString(this.name, "_").length + " | " + this.getUnCompressedName());
		}

		if(Configs.Tooltip_Information.show_owner_mod) {
			tooltip.add(I18n.format("info.mcs.owner_mod") + " : " + TextFormatting.AQUA.toString() + this.getOwnerMod());
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

	@SideOnly(Side.CLIENT)
	@Override
	public void getItemModel() {
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			if(this.model_material != null) {
				model.registerItemModel(this, meta, this.ownerMod + "/item/normal/" + this.model_material + "/" + this.name, this.name + "." + meta);
			}else {
				model.registerItemModel(this, meta, this.ownerMod + "/item/normal/" + this.name, this.name + "." + meta);
			}
		}
		if(this.model_material != null) {
			model.registerItemModel(this, Short.MAX_VALUE - 1, this.ownerMod + "/item/normal/" + this.model_material + "/" + this.name, this.name + "." + (Short.MAX_VALUE - 1));
		}else {
			model.registerItemModel(this, Short.MAX_VALUE - 1, this.ownerMod + "/item/normal/" + this.name, this.name + "." + (Short.MAX_VALUE - 1));
		}
	}

	public final Item getUnCompressedItem() {
		return this.unCompressedItem.getItem();
	}

	public final ItemStack getUnCompressedStack() {
		return this.unCompressedItem;
	}

	public final String getOredict(int meta) {
		return JiuUtils.item.getOreDict(new ItemStack(this, 1, meta)).get(0);
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
	
	private ICompressedStuff smeltingOutput;
	public BaseCompressedItem setSmeltingOutput(ICompressedStuff stuff) {
		this.smeltingOutput = stuff;
		return this;
	}
	private int smeltingMetaDisparity = 0;
	public BaseCompressedItem setSmeltingMetaDisparity(int smeltingMetaDisparity) {
		this.smeltingMetaDisparity = smeltingMetaDisparity;
		return this;
	}
	@Override
	public boolean canCreateRecipe(int meta) {
		return this.smeltingOutput!=null;
	}
	@Override
	public ItemStack getSmeltingOutput(int meta) {
		int outmeta = meta-smeltingMetaDisparity;
		return outmeta < 0 ? null : smeltingOutput.getStack(outmeta);
	}
}
