package cat.jiu.mcs.util.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class BaseItemFood extends ItemFood implements IHasModel{

	protected final String name;
	protected final CreativeTabs tab;
	protected Item baseFoodItem = null;
	protected ItemFood baseFood = null;
	protected final ItemStack baseFoodStack;
	protected final String langModId;
	protected boolean baseItemIsNotFood;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);
	
	public BaseItemFood(String name, Item baseFood, int amount, float saturation, boolean isWolfFood, boolean baseItemIsNotFood, int meta, String langModId, CreativeTabs tab, boolean hasSubtypes) {
		super(amount, saturation, isWolfFood);
		this.baseItemIsNotFood = baseItemIsNotFood;
		this.name = name;
		this.tab = tab;
		this.baseFoodItem = baseFood;
		this.langModId = langModId;
		this.baseFoodStack = new ItemStack(this.baseFoodItem, 1, meta);
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName(this.name);
		this.setNoRepair();
		MCSItems.ITEMS.add((Item)this);
		MCSItems.ITEMS_NAME.add(this.name);
		MCSItems.FOODS_NAME.add(this.name);
		MCSItems.FOODS.add(this);
		MCSItems.FOODS_MAP.put(this.name, this);
	}
	
	public BaseItemFood(String name, Item baseFood, int amount, float saturation, boolean isWolfFood, boolean baseItemIsNotFood, int meta, String langModId, CreativeTabs tab) {
		this(name, baseFood, amount, saturation, isWolfFood, baseItemIsNotFood, meta, langModId, tab, true);
	}
	
	public BaseItemFood(String name, Item baseFood, int amount, float saturation, boolean isWolfFood, boolean baseItemIsNotFood, int meta, String langModId) {
		this(name, baseFood, amount, saturation, isWolfFood, baseItemIsNotFood, meta, langModId, MCS.COMPERESSED_ITEMS);
	}
	
	public BaseItemFood(String name, Item baseFood, int amount, float saturation, boolean isWolfFood, boolean baseItemIsNotFood, int meta) {
		this(name, baseFood, amount, saturation, isWolfFood, baseItemIsNotFood, meta, "minecraft");
	}
	
	public BaseItemFood(String name, Item baseFood, int amount, float saturation, boolean isWolfFood, boolean baseItemIsNotFood) {
		this(name, baseFood, amount, saturation, isWolfFood, baseItemIsNotFood, 0);
	}
	
	public BaseItemFood(String name, Item baseFood, int amount, float saturation, boolean isWolfFood) {
		this(name, baseFood, amount, saturation, isWolfFood, true);
	}
	
	public BaseItemFood(String name, Item baseFood, int amount, float saturation) {
		this(name, baseFood, amount, saturation, false);
	}
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId, CreativeTabs tab, float saturation, boolean isWolfFood, boolean hasSubtypes) {
		super(8, saturation, isWolfFood);
		this.name = name;
		this.tab = tab;
		this.baseFood = baseFood instanceof ItemFood ? (ItemFood)baseFood : (ItemFood)Items.COOKED_BEEF;
		this.langModId = langModId;
		this.baseFoodStack = new ItemStack(this.baseFood, 1, meta);
		this.setHasSubtypes(hasSubtypes);
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setNoRepair();
		this.setRegistryName(this.name);
		MCSItems.ITEMS_NAME.add(name);
		MCSItems.FOODS_NAME.add(name);
		MCSItems.ITEMS.add((Item)this);
		MCSItems.FOODS.add(this);
		MCSItems.FOODS_MAP.put(this.name, this);
	}
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId, CreativeTabs tab,  boolean isWolfFood, boolean hasSubtypes) {
		this(name, baseFood, meta, langModId, tab, 0.6F, isWolfFood, hasSubtypes);
	}
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId, CreativeTabs tab,  boolean hasSubtypes) {
		this(name, baseFood, meta, langModId, tab, false, hasSubtypes);
	}
	
	public BaseItemFood(String name, Item baseFood, int meta, String langModId,  CreativeTabs tab) {
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
	
	public String getOwnerMod() {
		return this.langModId;
	}
	
	private boolean makeRecipe = true;
	
	public BaseItemFood setMakeRecipe(boolean makeRecipe) {
		this.makeRecipe = makeRecipe;
		return this;
	}
	
	public boolean getMakeRecipe() {
		return this.makeRecipe;
	}
	
	boolean custem = false;
	int index = 1;
	
	public BaseItemFood isOreDictCustem(int index) {
		this.custem = !this.custem;
		this.index = index;
		return this;
	}
	
	public String getUnCompressedName() {
		String[] names = JiuUtils.other.custemSplitString(this.name, "_");
		
		if(names.length == 4) {
			return JiuUtils.other.upperCaseToFistLetter(names[1]) + JiuUtils.other.upperCaseToFistLetter(names[2]) + JiuUtils.other.upperCaseToFistLetter(names[3]);
		}else if(names.length == 3){
			return JiuUtils.other.upperCaseToFistLetter(names[1]) + JiuUtils.other.upperCaseToFistLetter(names[2]);
		}else {
			return JiuUtils.other.upperCaseToFistLetter(names[1]);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasEffect(ItemStack stack) {
		if(this.baseFood != null) {
			return this.baseFoodStack.hasEffect();
		}else if(this.baseFoodItem != null) {
			return this.baseFoodItem.hasEffect(this.baseFoodStack);
		}else {
			return super.hasEffect(stack);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getItemEnchantability(ItemStack stack) {
		if(this.baseFood != null) {
			return this.baseFood.getItemEnchantability(this.baseFoodStack);
		}else if(this.baseFoodItem != null) {
			return this.baseFoodItem.getItemEnchantability(this.baseFoodStack);
		}else {
			return super.getItemEnchantability(stack);
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public EnumRarity getRarity(ItemStack stack) {
		if(this.baseFood != null) {
			return this.baseFoodStack.getRarity();
		}else if(this.baseFoodItem != null){
			return this.baseFoodItem.getRarity(baseFoodStack);
		}else {
			return super.getRarity(stack);
		}
	}
	
	@Override
	public int getHealAmount(ItemStack stack) {
		if(this.baseItemIsNotFood) {
			if(Configs.tooltip_information.get_actual_food_amout) {
				return (int) Math.pow(super.getHealAmount(new ItemStack(this)), stack.getMetadata() + 1);
			}else {
				return (int)(super.getHealAmount(new ItemStack(this)) * ((stack.getMetadata() + 1) * 9) * 0.5);
			}
		}else {
			if(Configs.tooltip_information.get_actual_food_amout) {
				return (int) Math.pow(this.baseFood.getHealAmount(this.baseFoodStack), stack.getMetadata() + 1);
			}else {
				return (int)(this.baseFood.getHealAmount(this.baseFoodStack) * ((stack.getMetadata() + 1) * 9) * 0.5);
			}
		}
	}
	
	@Override
	public float getSaturationModifier(ItemStack stack) {
		if(this.baseItemIsNotFood) {
			if(Configs.tooltip_information.get_actual_food_amout) {
				return (float) Math.pow(super.getSaturationModifier(new ItemStack(this)), stack.getMetadata() + 1);
			}else {
				return super.getSaturationModifier(new ItemStack(this)) * ((stack.getMetadata() + 1) * 9);
			}
		}else {
			if(Configs.tooltip_information.get_actual_food_amout) {
				return (float) Math.pow(this.baseFood.getSaturationModifier(this.baseFoodStack), stack.getMetadata() + 1);
			}else {
				return this.baseFood.getSaturationModifier(this.baseFoodStack) * ((stack.getMetadata() + 1) * 9);
			}
		}
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("tile.mcs.compressed_" + stack.getMetadata() + ".name", stack.getMetadata()) + I18n.format(this.baseFoodStack.getUnlocalizedName() + ".name", 1);
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
	
	boolean canAddPotion;
	List<PotionEffect> effects = new ArrayList<PotionEffect>();
	
	public BaseItemFood canAddPotionEffect(boolean canAdd) {
		this.canAddPotion = canAdd;
		return this;
	}
	
	public BaseItemFood setPotionEffect(PotionEffect[] potion){
		for(int i = 0; i < potion.length; ++i) {
			this.effects.add(potion[i]);
		}
		return this;
	}
	
	public void addPotionEffect(EntityPlayer player, boolean isCustem) {
		if(this.effects != null) {
			for(PotionEffect effect : this.effects) {
				player.addPotionEffect(effect);
			}
			if(isCustem) {
				// 添加完后清掉所有buff，以防后续重新给予buff
				// 只有在是自定义的情况下才清
				this.effects.clear();
			}
			
		}
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
//		super.onFoodEaten(stack, worldIn, player);
		if(this.canAddPotion) {
			this.addPotionEffect(player, false);
		}else {
			if(Configs.custom.custem_already_stuff.item.custem_eat_effect.length != 1) {
				try {
					for(int j = 1; j < Configs.custom.custem_already_stuff.item.custem_eat_effect.length; ++j) {
						String configs = Configs.custom.custem_already_stuff.item.custem_eat_effect[j];
						
						String[] config = JiuUtils.other.custemSplitString(configs, "#");// 包含各种东西的数组
						
						String[] effectConfigs = JiuUtils.other.custemSplitString(config[2], "|");// 仅包含buff的总数组
						String[][] effectConfig = JiuUtils.other.one2two(new String[effectConfigs.length / 3][3], effectConfigs, 0);// 将buff总数组里面的buffs拆分成单个buff
						
						String name = config[0];// 物品名字
						int meta = new Integer(config[1]);// 物品meta
						
						if(MCSItems.ITEMS_NAME.contains(name)) {
							if(this.name.equals(name)) {
								if(stack.getMetadata() == meta) {
									this.setEffect(effectConfig, worldIn);
									this.addPotionEffect(player, true);
								}
							}
						}else {
							if(worldIn.isRemote) {
								MCS.instance.log.fatal("\"" + name +  "\": "+ "\"" + name + "\"" + " is not belong to MCS's Item!");
							}
						}
					}
				} catch (Exception e) {
					MCS.instance.log.fatal(e.getMessage() + " is not Number!");
				}
			}
		}
	}
	
	private void setEffect(String[][] effectConfig, World world) {
		for(int i = 0; i < effectConfig.length; ++i) {
			if(effectConfig[i].length == 3) {
				String effectName = effectConfig[i][0];// buff 名字
				try {
					int time = new Integer(effectConfig[i][1]) * 20;// buff时间，这里乘以20是为了以秒计算
					int level = new Integer(effectConfig[i][2]);// buff的等级
					if(JiuUtils.other.getRegisteredMobEffect(effectName) != null) {// 如果buff名字获取后不是null
						if(time <= 2147483647) {// 如果时间没有超出int上限
							if(level < 256) {// 如果等级没有超出255
								// 往list里面添加buff
								this.effects.add(new PotionEffect(JiuUtils.other.getRegisteredMobEffect(effectName), time, level));
							}else {
								if(world.isRemote) {
									MCS.instance.log.fatal(effectName + ": \"" + level + "\" It's too large! It must be >= 255");// 提示等级太大
								}
							}
						}else {
							if(world.isRemote) {
								MCS.instance.log.fatal(effectName + ": \"" + time + "\" It's too large! It must be >=107374182");// 提示时间太大
							}
						}
					}
				} catch (Exception e) {
					if(world.isRemote) {
						MCS.instance.log.fatal(effectName + ": \"" + e.getMessage() + "\" is not Number!");// 提示 xxxx 不是数字
					}
				}
			}else {
				if(world.isRemote) {
					MCS.instance.log.fatal(effectConfig[i][0] + ": " + " length(" + effectConfig[i].length + ") is not multiple of 3!");// 提示单个buff单元不含有，或超出指定的数量
				}
			}
		}
	}
	
	/*
	boolean hasContainer;
	ItemStack container;
	
	public BaseItemFood hasContainer(boolean hasContainer, ItemStack container) {
		this.hasContainer = hasContainer;
		this.container = container;
		return this;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		super.onItemUseFinish(stack, worldIn, entityLiving);
		if(this.hasContainer) {
			if (entityLiving instanceof EntityPlayer) {
	            EntityPlayer player = (EntityPlayer)entityLiving;
	            System.out.println(stack);
	            player.addItemStackToInventory(this.container);
	        }
		}
		return super.onItemUseFinish(stack, worldIn, entityLiving);
    }
	*/
	
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
			tooltip.add(names.length+"");
			tooltip.add(this.getUnCompressedName());
		}
		
		if(Configs.tooltip_information.show_owner_mod) {
			tooltip.add("Owner Mod: \'" + this.getOwnerMod() + "\'");
		}
		
		if(Configs.tooltip_information.show_food_amount) {
			tooltip.add("FoodAmount: " + this.getHealAmount(stack));
			tooltip.add("SaturationModifier: " + this.getSaturationModifier(stack));
		}
		
		if(Configs.tooltip_information.show_burn_time) {
			tooltip.add("BurnTime: " + ForgeEventFactory.getItemBurnTime(stack));
		}
		
		if(Configs.tooltip_information.show_oredict) {
			tooltip.add("OreDictionary: ");
			for(String ore : JiuUtils.item.getOreDict(stack)){
				tooltip.add("> " + ore);
			}
		}
		
		if(meta == 65535) { tooltip.add("感谢喵呜玖大人的恩惠！"); }
		
		try {
			for(int i = 1; i < Configs.tooltip_information.custem_info.item_food.length; ++i) {
				String str0  = Configs.tooltip_information.custem_info.item_food[i];
				
				String[] strri = JiuUtils.other.custemSplitString(str0, "#");
				
				if(MCSItems.FOODS_NAME.contains(strri[0])) {
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

	@Override
	public void registerItemModel() {
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			model.registerItemModel(this, meta, this.langModId + "/item/food/" + this.name, this.name + "." + meta);
		}
		model.registerItemModel(this, 65535, this.langModId + "/item/food/" + this.name, this.name + "." + 65535);
	}
	
	public final Item getUnCompressedItem(){
		return this.baseFoodItem;
	}
	
	public final ItemFood getUnCompressedItemFood(){
		return this.baseFood;
	}
	
	public final ItemStack getUnCompressedStack(){
		return this.baseFoodStack;
	}
	
	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemLocalizedName() {
		if("minecraft".equals(this.baseFoodStack.getItem().getCreatorModId(this.baseFoodStack))) {
			return I18n.format(this.baseFoodStack.getUnlocalizedName() + ".name", 1).trim();
		}else {
			return I18n.format(this.baseFoodStack.getUnlocalizedName(), 1).trim();
		}
	}
}
