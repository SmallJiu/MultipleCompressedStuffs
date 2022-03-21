package cat.jiu.mcs.util.base.sub.tool;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import cat.jiu.core.util.base.BaseItemTool;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.exception.NonToolException;
import cat.jiu.mcs.interfaces.ICompressedStuff;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemSword extends BaseItemTool.MetaSword implements ICompressedStuff {
	protected final ItemStack baseToolStack;
	protected final ItemSword baseTool;
	protected final float baseAttackDamage;
	protected final String ownerMod;
	
	public BaseItemSword(ItemStack baseTool, String name) {
		super(MCS.MODID, name, MCS.COMPERESSED_TOOLS, true, getToolMaterial(baseTool), ModSubtypes.values());
		this.baseToolStack = baseTool;
		if(baseTool.getItem() instanceof ItemSword) {
			this.baseTool = (ItemSword)baseTool.getItem();
		}else {
			this.baseTool = null;
			throw new NonToolException(baseTool, "Sword");
		}
		this.ownerMod = this.baseTool.getRegistryName().getResourceDomain();
		this.baseAttackDamage = 3.0F + (baseTool.getItem() instanceof ItemSword ? ((ItemSword)baseTool.getItem()).getAttackDamage() : 0);
		this.setMaxMetadata(16);
		
		MCSResources.SUB_TOOLS.add(this);
		MCSResources.SUB_TOOLS_NAME.add(name);
		MCSResources.ITEMS.add(this);
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.SWORDS.add(this);
		MCSResources.SWORDS_NAME.add(name);
		MCSResources.SUB_TOOLS_MAP.put(name, this);
	}
	
	private static ToolMaterial getToolMaterial(ItemStack baseItem) {
		if(baseItem.getItem() instanceof ItemSword) {
			return ((ItemSword)baseItem.getItem()).material;
		}
		return ToolMaterial.WOOD;
	}
	
	public int getUnCompressedBurnTime() {
		return TileEntityFurnace.getItemBurnTime(this.baseToolStack);
	}
	
	@Override
	public int getItemEnchantability(ItemStack stack) {
		int enchantability = this.baseTool.getItemEnchantability(this.baseToolStack);
		return (int) (enchantability + (enchantability * ((stack.getMetadata()+1) * 0.29394)));
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.baseTool.getIsRepairable(this.baseToolStack, repair);
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return this.baseTool.canHarvestBlock(state);
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return this.baseTool.getDestroySpeed(this.baseToolStack, state);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {
		int baseMaxDamage = this.baseToolStack.getMaxDamage();
		return (int) (baseMaxDamage + (baseMaxDamage * ((stack.getMetadata()+1) * 0.4399)));
	}
	
	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();
		if(slot == EntityEquipmentSlot.MAINHAND) {
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.baseAttackDamage + ((stack.getMetadata()+1) * this.baseAttackDamage) * 0.789, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)((stack.getMetadata()+1) * 0.0973) - 2.43D, 0));
		}
		return multimap;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("tile.mcs.compressed_" + stack.getMetadata() + ".name", stack.getMetadata()) + this.getUnCompressedItemLocalizedName();
	}
	
	private List<String> shiftInfos = new ArrayList<String>();
	public BaseItemSword addCustemShiftInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			shiftInfos.add(custemInfo[i]);
		}
		return this;
	}
	
	public BaseItemSword addCustemShiftInformation(List<String> infos) {
		this.shiftInfos = infos;
		return this;
	}
	
	private Map<Integer, List<String>> metaShiftInfos = Maps.newHashMap();
	public BaseItemSword addCustemShiftInformation(Map<Integer, List<String>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}
	
	private List<String> infos = new ArrayList<String>();
	public BaseItemSword addCustemInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}
	
	public BaseItemSword addCustemInformation(List<String> infos) {
		this.infos = infos;
		return this;
	}
	
	private Map<Integer, List<String>> metaInfos = Maps.newHashMap();
	public BaseItemSword addCustemInformation(Map<Integer, List<String>> infos) {
		this.metaInfos = infos;
		return this;
	}
	
	ItemStack infoStack = null;
	public BaseItemSword setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}
	
	private Map<Integer, ItemStack> infoStacks = Maps.newHashMap();
	public BaseItemSword setInfoStack(Map<Integer, ItemStack> infoStacks) {
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
		
		if(MCS.instance.test_model) {
			Color rgb = new Color(Color.WHITE.getRGB() - (new Color(16, 16, 16).getRGB() * (stack.getMetadata())));
			tooltip.add("R: " + rgb.getRed());
			tooltip.add("G: " + rgb.getGreen());
			tooltip.add("B: " + rgb.getBlue());
			tooltip.add("最大耐久: " + this.getMaxDamage(stack));
			tooltip.add("附魔能力: " + this.baseTool.getItemEnchantability(this.baseToolStack) + " -> " + this.getItemEnchantability(stack));
		}
		
		MCSUtil.info.addMetaInfo(meta, tooltip, this.infos, this.metaInfos);
		MCSUtil.info.addShiftInfo(meta, tooltip, this.shiftInfos, this.metaShiftInfos);
		
		if(meta == Short.MAX_VALUE) { tooltip.add("感谢喵呜玖大人的恩惠！"); }
	}

	@Override
	public void getItemModel() {
		super.getItemModel();
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			this.model.registerItemModel(this, meta, this.ownerMod + "/item/tools/sword/" + this.name, this.name + "." + meta);
		}
		this.model.registerItemModel(this, Short.MAX_VALUE, this.ownerMod + "/item/tools/sword/" + this.name, this.name + "." + Short.MAX_VALUE);
	}

	public String getOwnerMod() {
		return this.ownerMod;
	}
	
	public final Item getUnCompressedItem(){
		return this.baseToolStack.getItem();
	}
	
	public final ItemStack getUnCompressedStack(){
		return this.baseToolStack;
	}
	
	private boolean makeRecipe = true;
	public BaseItemSword setMakeDefaultStackRecipe(boolean makeRecipe) {
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
}
