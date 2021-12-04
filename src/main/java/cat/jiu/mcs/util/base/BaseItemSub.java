package cat.jiu.mcs.util.base;

import java.util.List;

import javax.annotation.Nullable;

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
import net.minecraft.client.resources.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class BaseItemSub extends Item implements IHasModel{
	
	protected final String name;
	protected final CreativeTabs tab;
	protected final ItemStack baseItemStack;
	protected final Item baseItem;
	protected final String langModID;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);
	private String model_material = null;
	
	public BaseItemSub setModelMaterial(String model_material) {
		this.model_material = model_material;
		return this;
	}
	
	public static BaseItemSub register(String name, ItemStack baseItem, String langModId, CreativeTabs tab, boolean hasSubtypes) {
		if(Loader.isModLoaded(langModId)) {
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
		this.baseItemStack = baseItem;
		this.baseItem = baseItem.getItem();
		this.langModID = langModId;
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName(this.name);
		this.setNoRepair();
//		ForgeRegistries.ITEMS.register(this.setRegistryName(this.name));
		MCSItems.ITEMS.add(this);
		MCSItems.ITEMS_NAME.add(this.name);
		MCSItems.SUB_ITEMS.add(this);
		MCSItems.SUB_ITEMS_NAME.add(this.name);
		MCSItems.SUB_ITEMS_MAP.put(this.name, this);
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
		String i = "";
		for(String s : unNames) {
			if(!"compressed".equals(s)) {
				i += JiuUtils.other.upperCaseToFistLetter(s);
			}
		}
		return i;
		/*
		String[] names = JiuUtils.other.custemSplitString(this.name, "_");
		
		if(names.length == 4) {
			return JiuUtils.other.upperCaseToFistLetter(names[1]) + JiuUtils.other.upperCaseToFistLetter(names[2]) + JiuUtils.other.upperCaseToFistLetter(names[3]);
		}else if(names.length == 3){
			return JiuUtils.other.upperCaseToFistLetter(names[1]) + JiuUtils.other.upperCaseToFistLetter(names[2]);
		}else {
			return JiuUtils.other.upperCaseToFistLetter(names[1]);
		}
		*/
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		if(this.baseItemStack != null) {
			return this.baseItem.hasEffect(this.baseItemStack);
		}else {
			return super.hasEffect(stack);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getItemEnchantability() {
		if(this.baseItemStack != null) {
			return this.baseItem.getItemEnchantability();
		}else {
			return super.getItemEnchantability();
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(this.baseItemStack != null) {
			return this.baseItemStack.getRarity();
		}else {
			return super.getRarity(stack);
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
	
	ItemStack infoStack = null;
	
	public BaseItemSub setCustemInformationItemStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced){
		int meta = stack.getMetadata();
		int level = meta + 1;
		if(Configs.use_3x3_recipes) {
			if(Configs.tooltip_information.show_specific_number) {
				if(!Configs.tooltip_information.can_custom_specific_number) {
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
				tooltip.add(Math.pow(9, level) + " x " + this.getUnCompressedItemLocalizedName());
			}
		}else {
			if(Configs.tooltip_information.show_specific_number) {
				if(!Configs.tooltip_information.can_custom_specific_number) {
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
				tooltip.add(Math.pow(4, level) + " x " + this.getUnCompressedItemLocalizedName());
			}
		}
		
		if(MCS.instance.test_model) {
			String[] names = JiuUtils.other.custemSplitString(this.name, "_");
			tooltip.add(names.length + "");
			tooltip.add(this.getUnCompressedName());
		}
		
		if(Configs.tooltip_information.show_owner_mod) {
			tooltip.add("Owner Mod: \'" + this.getOwnerMod() + "\'");
		}
		
		if(Configs.tooltip_information.show_burn_time) {
			tooltip.add("UnCompressedItemBurnTime: " + ForgeEventFactory.getItemBurnTime(this.baseItemStack));
			tooltip.add("BurnTime: " + ForgeEventFactory.getItemBurnTime(stack));
		}
		
		if(Configs.tooltip_information.show_oredict) {
			tooltip.add("OreDictionary: ");
			for(String ore : JiuUtils.item.getOreDict(stack)){
				tooltip.add("> " + ore);
			}
		}
		
		if(meta == 65535) { tooltip.add("感谢喵呜玖大人的恩惠！"); }
		
		if(this.infoStack != null) {
			this.baseItemStack.getItem().addInformation(this.infoStack, world, tooltip, advanced);
		}else {
			this.baseItemStack.getItem().addInformation(this.baseItemStack, world, tooltip, advanced);
		}
		
		if(Configs.tooltip_information.custem_info.item.length != 1) {
			try {
				for(int i = 1; i < Configs.tooltip_information.custem_info.item.length; ++i) {
					String str0  = Configs.tooltip_information.custem_info.item[i];
					
					String[] strri = JiuUtils.other.custemSplitString(str0, "#");
					
					if(MCSItems.SUB_ITEMS_NAME.contains(strri[0])) {
						if(this.name.equals(strri[0])) {
							try {
								int strmeta = new Integer(strri[1]);
								
								if(strmeta == meta) {
									String[] str1 = JiuUtils.other.custemSplitString(strri[2], "|");
									
									for(int k = 0; k < str1.length; ++k) {
										tooltip.add(str1[k]);
									}
								}
							} catch (Exception e) {
								MCS.instance.log.fatal("\"" + strri[0] +  "\": "+ "\"" + strri[1] + "\"" + " is not Number!");
							}
						}
					}else {
						MCS.instance.log.fatal("\"" + strri[0] +  "\"" + " is not belong to MCS's Item!");
					}
				}
			} catch (Exception e) {
				MCS.instance.log.fatal("Item: " + (new Integer(e.getMessage()) - 1) + " is not multiple of 3!");
			}
		}
	}

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
			model.registerItemModel(this, 65535, this.langModID + "/item/normal/" + this.model_material + "/" + this.name, this.name + "." + 65535);
		}else {
			model.registerItemModel(this, 65535, this.langModID + "/item/normal/" + this.name, this.name + "." + 65535);
		}
		
	}
	
	public final Item getUnCompressedItem(){
		return this.baseItemStack.getItem();
	}
	
	public final ItemStack getUnCompressedStack(){
		return this.baseItemStack;
	}
	
	public final String getOredict(int meta) {
		return JiuUtils.item.getOreDict(new ItemStack(this, 1, meta)).get(0);
	}
	
	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemLocalizedName() {
		String modid = this.baseItemStack.getItem().getCreatorModId(this.baseItemStack);
		if(JiuUtils.other.containKey(MCS.other_mod, modid) || "thermalfoundation".equals(modid)) {
			return I18n.format(this.baseItemStack.getUnlocalizedName() + ".name", 1).trim();
		}else {
			return I18n.format(this.baseItemStack.getUnlocalizedName(), 1).trim();
		}
	}
}
