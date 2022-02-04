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

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;

import net.minecraftforge.common.IRarity;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemSub extends Item implements IHasModel{
	
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
		if(Loader.isModLoaded(langModId) || langModId.equals("custom")) {
			if(baseItem != null) {
				return new BaseItemSub(name, baseItem, langModId, tab, hasSubtypes);
			}else {
				return null;
			}
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
	
	public BaseItemSub(String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
		this.name = name;
		this.tab = tab;
		this.unCompressedItem = baseItem;
		this.baseItem = baseItem.getItem();
		this.langModID = langModId;
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName(this.name);
		this.setNoRepair();
		if(!langModId.equals("custom")) {
			MCSItems.ITEMS.add(this);
			MCSItems.ITEMS_NAME.add(this.name);
			MCSItems.SUB_ITEMS.add(this);
			MCSItems.SUB_ITEMS_NAME.add(this.name);
			MCSItems.SUB_ITEMS_MAP.put(this.name, this);
		}
	}
	
	public BaseItemSub(String name, ItemStack baseItem, String langModId, CreativeTabs tab) {
		this(name, baseItem, langModId, tab, true);
	}
	
	public BaseItemSub(String name, ItemStack baseItem, String langModId) {
		this(name, baseItem, langModId, MCS.COMPERESSED_ITEMS);
	}
	
	public BaseItemSub(String name, ItemStack baseItem) {
		this(name, baseItem, "minecraft");
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
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced){
		int meta = stack.getMetadata();
		int level = meta + 1;
		
		if(this.infoStack != null) {
			this.infoStack.getItem().addInformation(this.infoStack, world, tooltip, advanced);
		}else if(!this.infoStacks.isEmpty()){
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
				tooltip.add(new BigInteger("9").pow(level) + " x " + this.getUnCompressedItemLocalizedName());
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
				tooltip.add(new BigInteger("4").pow(level) + " x " + this.getUnCompressedItemLocalizedName());
			}
		}
		
		if(MCS.instance.test_model) {
			String[] names = JiuUtils.other.custemSplitString(this.name, "_");
			tooltip.add(names.length + "");
			tooltip.add(this.getUnCompressedName());
		}
		
		if(Configs.TooltipInformation.show_owner_mod) {
			tooltip.add("Owner Mod: \'" + this.getOwnerMod() + "\'");
		}
		
		if(Configs.TooltipInformation.show_burn_time) {
			tooltip.add("UnCompressedItemBurnTime: " + ForgeEventFactory.getItemBurnTime(this.unCompressedItem));
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
		}else if(!this.metaInfos.isEmpty()) {
			if(this.metaInfos.containsKey(meta)) {
				for(String str : this.metaInfos.get(meta)) {
					tooltip.add(str);
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
