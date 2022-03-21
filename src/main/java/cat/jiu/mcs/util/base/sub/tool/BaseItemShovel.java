package cat.jiu.mcs.util.base.sub.tool;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseItemShovel extends BaseItemTool.MetaShovel implements ICompressedStuff {
	protected final ItemStack baseToolStack;
	protected final ItemSpade baseTool;
	protected final float baseAttackDamage;
	protected final String ownerMod;
	
	public BaseItemShovel(ItemStack baseTool, String name) {
		super(MCS.MODID, name, MCS.COMPERESSED_TOOLS, true, getToolMaterial(baseTool), ModSubtypes.values());
		this.baseToolStack = baseTool;
		if(baseTool.getItem() instanceof ItemSpade) {
			this.baseTool = (ItemSpade)baseTool.getItem();
		}else {
			this.baseTool = null;
			throw new NonToolException(baseTool, "Shovel");
		}
		this.ownerMod = this.baseTool.getRegistryName().getResourceDomain();
		this.baseAttackDamage = 3.0F + this.baseTool.attackDamage;
		this.setMaxMetadata(16);
		
		MCSResources.SUB_TOOLS.add(this);
		MCSResources.SUB_TOOLS_NAME.add(name);
		MCSResources.ITEMS.add(this);
		MCSResources.ITEMS_NAME.add(name);
		MCSResources.SHOVELS.add(this);
		MCSResources.SHOVEL_NAME.add(name);
		MCSResources.SUB_TOOLS_MAP.put(name, this);
	}
	
	private static ToolMaterial getToolMaterial(ItemStack baseItem) {
		if(baseItem.getItem() instanceof ItemPickaxe) {
			return ((ItemPickaxe)baseItem.getItem()).toolMaterial;
		}
		return ToolMaterial.WOOD;
	}
	
	public int getUnCompressedBurnTime() {
		return TileEntityFurnace.getItemBurnTime(this.baseToolStack);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		return this.baseTool.getIsRepairable(this.baseToolStack, repair);
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
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double)((stack.getMetadata()+1) * 0.0973) - 2.83D, 0));
		}
		return multimap;
	}
	
	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		float speed = this.baseToolStack.getDestroySpeed(state);
		speed += ((stack.getMetadata()+1)*speed) * 0.175;//15 is max speed
		return speed;
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
		return this.baseTool.canHarvestBlock(state, this.baseToolStack);
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState) {
		int level = this.baseTool.getHarvestLevel(stack, toolClass, player, blockState);
		level += (int)((stack.getMetadata()+1) * 0.339341);
		return level;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return I18n.format("tile.mcs.compressed_" + stack.getMetadata() + ".name", stack.getMetadata()) + this.getUnCompressedItemLocalizedName();
	}
	
	private List<String> shiftInfos = new ArrayList<String>();
	public BaseItemShovel addCustemShiftInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			shiftInfos.add(custemInfo[i]);
		}
		return this;
	}
	
	public BaseItemShovel addCustemShiftInformation(List<String> infos) {
		this.shiftInfos = infos;
		return this;
	}
	
	private Map<Integer, List<String>> metaShiftInfos = Maps.newHashMap();
	public BaseItemShovel addCustemShiftInformation(Map<Integer, List<String>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}
	
	private List<String> infos = new ArrayList<String>();
	public BaseItemShovel addCustemInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}
	
	public BaseItemShovel addCustemInformation(List<String> infos) {
		this.infos = infos;
		return this;
	}
	
	private Map<Integer, List<String>> metaInfos = Maps.newHashMap();
	public BaseItemShovel addCustemInformation(Map<Integer, List<String>> infos) {
		this.metaInfos = infos;
		return this;
	}
	
	ItemStack infoStack = null;
	
	public BaseItemShovel setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}
	
	private Map<Integer, ItemStack> infoStacks = Maps.newHashMap();
	public BaseItemShovel setInfoStack(Map<Integer, ItemStack> infoStacks) {
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
			tooltip.add("最大耐久: " + this.getMaxDamage(stack));
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
			this.model.registerItemModel(this, meta, this.ownerMod + "/item/tools/shovel/" + this.name, this.name + "." + meta);
		}
		this.model.registerItemModel(this, Short.MAX_VALUE, this.ownerMod + "/item/tools/shovel/" + this.name, this.name + "." + Short.MAX_VALUE);
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
	
	public BaseItemShovel setMakeDefaultStackRecipe(boolean makeRecipe) {
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
