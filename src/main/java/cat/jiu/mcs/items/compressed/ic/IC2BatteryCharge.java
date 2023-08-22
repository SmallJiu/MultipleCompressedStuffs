package cat.jiu.mcs.items.compressed.ic;

import java.util.List;
import java.util.Locale;

import com.google.common.collect.Lists;

import cat.jiu.core.events.item.ItemInPlayerEvent;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;
import cat.jiu.mcs.util.init.CraftCompressedStuffTrigger;
import cat.jiu.mcs.util.init.MCSCreativeTab;
import ic2.api.item.ElectricItem;
import ic2.api.item.ICustomDamageItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.init.Localization;
import ic2.core.item.BaseElectricItem;
import ic2.core.item.IPseudoDamageItem;
import ic2.core.item.ItemBatteryChargeHotbar;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class IC2BatteryCharge extends BaseCompressedItem implements IElectricItem, IPseudoDamageItem, ICustomDamageItem {
	protected final double baseMaxEnergy;
	protected final int energyLevel;
	protected final double fransferLimit;
	protected final ItemBatteryChargeHotbar baseBar;

	public IC2BatteryCharge(String name, ItemStack baseItem, CreativeTabs tab) {
		super(name, baseItem, tab);
		if(baseItem.getItem() instanceof IElectricItem) {
			IElectricItem base = (IElectricItem) baseItem.getItem();
			if(base instanceof ItemBatteryChargeHotbar) {
				this.baseBar = (ItemBatteryChargeHotbar) base;
			}else {
				this.baseBar = null;
			}
			this.baseMaxEnergy = base.getMaxCharge(baseItem);
			this.energyLevel = base.getTier(baseItem);
			this.fransferLimit = base.getTransferLimit(baseItem);
		}else {
			throw new RuntimeException("'" + baseItem.toString() + "' is NOT IC Energy Item");
		}
		this.setMaxStackSize(1);
		this.setInfoStack(ItemStack.EMPTY);
		this.setCanShowBaseStackInfo(false);
		MinecraftForge.EVENT_BUS.register(this);
	}

	public IC2BatteryCharge(String name, ItemStack baseItem) {
		this(name, baseItem, MCSCreativeTab.ITEMS);
	}

	public IC2BatteryCharge(String name, String unCompressedItem, int meta, CreativeTabs tab) {
		this(name, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, (ModSubtypes.INFINITY)), tab);
	}

	public IC2BatteryCharge(String name, String unCompressedItem, CreativeTabs tab) {
		this(name, unCompressedItem, (ModSubtypes.INFINITY), tab);
	}

	public IC2BatteryCharge(String name, String unCompressedItem) {
		this(name, unCompressedItem, (ModSubtypes.INFINITY), MCSCreativeTab.ITEMS);
	}

	@Override
	public boolean createOreDictionary() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		this.baseBar.addInformation(stack, world, tooltip, advanced);
		super.addInformation(stack, world, tooltip, advanced);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(this.baseBar != null) {
			return this.baseBar.onItemRightClick(world, player, hand);
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@SubscribeEvent
	public void onPlayerCraftedItemInGui(PlayerEvent.ItemCraftedEvent event) {
		this.onPlayerCraftedItemInGui(event.player, event.craftMatrix, event.crafting);
	}
	
	private void onPlayerCraftedItemInGui(EntityPlayer player, IInventory gui, ItemStack stack) {
		if(!player.world.isRemote) {
			if(stack.getItem() == this) {
				List<ItemStack> craftIn = Lists.newArrayList();

				for(int slot = 0; slot < gui.getSizeInventory(); slot++) {
					ItemStack in0 = gui.getStackInSlot(slot);
					if(!in0.isEmpty()) {
						craftIn.add(in0);
					}
				}

				if(!craftIn.isEmpty()) {
					if(craftIn.size() == 9 || craftIn.size() == 4 || craftIn.size() == 5) {
						double energy = 0;
						for(ItemStack stack0 : craftIn) {
							energy += JiuUtils.nbt.getItemNBTDouble(stack0, "charge");
						}
						if(energy > this.getMaxCharge(stack)) {
							energy = this.getMaxCharge(stack);
						}
						JiuUtils.nbt.setItemNBT(stack, "charge", energy);
					}else if(craftIn.size() == 1) {
						JiuUtils.nbt.setItemNBT(stack, "charge", this.getCharge(craftIn.get(0)) / 9);
					}
				}

			}
		}
	}
	
	@SubscribeEvent
	public void onItemInPlayerInventoryTick(ItemInPlayerEvent.InInventory event) {
		this.onItemInPlayerInventoryTick(event.getEntityPlayer(), event.stack, event.slot);
	}

	private void onItemInPlayerInventoryTick(EntityPlayer player, ItemStack invStack, int slot) {
		ICriterionTrigger<?> trigger =  CriteriaTriggers.get(CraftCompressedStuffTrigger.instance.getId());
		if(trigger != null && trigger instanceof CraftCompressedStuffTrigger) {
			((CraftCompressedStuffTrigger)trigger).trigger(player, invStack);
		}
		
		if(invStack.getItem() instanceof IC2BatteryCharge) {
			Mode mode = this.getMode(invStack);
			if(!player.getEntityWorld().isRemote && mode != Mode.DISABLED) {
				List<ItemStack> inv = player.inventory.mainInventory;
				double limit = this.getTransferLimit(invStack);
				int tier = this.getTier(invStack);
				for(int invslot = 0; invslot < inv.size(); invslot++) {
					ItemStack toCharge = inv.get(invslot);
					if(!toCharge.isEmpty()) {
						if(mode != Mode.NOT_IN_HAND || player.inventory.currentItem != invslot) {
							if(!(toCharge.getItem() instanceof ItemBatteryChargeHotbar) && !(toCharge.getItem() instanceof IC2BatteryCharge)) {
								double charge = ElectricItem.manager.charge(toCharge, limit, tier, false, true);
								charge = ElectricItem.manager.discharge(invStack, charge, tier, true, false, false);
								ElectricItem.manager.charge(toCharge, charge, tier, true, false);
								limit -= charge;
							}
						}
					}
				}

				ItemStack offHand = player.getHeldItemOffhand();
				if(!offHand.isEmpty()) {
					if(mode != Mode.NOT_IN_HAND) {
						if(!(offHand.getItem() instanceof ItemBatteryChargeHotbar) && !(offHand.getItem() instanceof IC2BatteryCharge)) {
							double charge = ElectricItem.manager.charge(offHand, limit, tier, false, true);
							charge = ElectricItem.manager.discharge(invStack, charge, tier, true, false, false);
							ElectricItem.manager.charge(offHand, charge, tier, true, false);
							limit -= charge;
						}
					}
				}

				for(ItemStack armor : player.inventory.armorInventory) {
					if(!armor.isEmpty()) {
						if(mode != Mode.NOT_IN_HAND) {
							if(!(armor.getItem() instanceof ItemBatteryChargeHotbar) && !(armor.getItem() instanceof IC2BatteryCharge)) {
								double charge = ElectricItem.manager.charge(armor, limit, tier, false, true);
								charge = ElectricItem.manager.discharge(invStack, charge, tier, true, false, false);
								ElectricItem.manager.charge(armor, charge, tier, true, false);
								limit -= charge;
							}
						}
					}
				}
			}
		}
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return this.getCharge(stack) != this.getMaxCharge(stack);
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return (this.getMaxCharge(stack) - this.getCharge(stack)) / this.getMaxCharge(stack);
	}

	@Override
	public int getCustomDamage(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			return 0;
		}
		return (int) stack.getTagCompound().getDouble("charge");
	}

	@Override
	public int getMaxCustomDamage(ItemStack stack) {
		return (int) this.getMaxCharge(stack);
	}

	// public void setCharge(ItemStack stack, double energy) {
	//
	// }

	@Override
	public void setCustomDamage(ItemStack stack, int damage) {
		this.setDamage(stack, damage);
	}

	@Override
	public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase p2) {
		this.setCustomDamage(stack, this.getCustomDamage(stack) + damage);
		return true;
	}

	@Override
	public void setDamage(final ItemStack stack, final int damage) {
		final int prev = this.getDamage(stack);
		if(damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
			IC2.log.warn(LogCategory.Armor, new Throwable(), "Detected invalid armor damage application (%d):", damage - prev);
		}
	}

	@Override
	public void setStackDamage(final ItemStack stack, final int damage) {
		this.setDamage(stack, damage);
	}

	@Override
	public boolean canProvideEnergy(ItemStack stack) {
		return true;
	}

	public double getCharge(ItemStack stack) {
		if(!stack.hasTagCompound()) {
			return 0;
		}
		return stack.getTagCompound().getDouble("charge");
	}

	@Override
	public double getMaxCharge(ItemStack stack) {
		return this.baseMaxEnergy + (this.baseMaxEnergy * ((stack.getMetadata() + 1) * 6.579));
	}

	@Override
	public int getTier(ItemStack stack) {
		return this.energyLevel;
	}

	@Override
	public double getTransferLimit(ItemStack stack) {
		return MCSUtil.item.getMetaValue(this.fransferLimit, stack.getMetadata());
	}

	public String getNameOfMode(final Mode mode) {
		return Localization.translate("ic2.tooltip.mode." + mode.toString().toLowerCase(Locale.ENGLISH));
	}

	public void setMode(final ItemStack stack, final Mode mode) {
		final NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		nbt.setByte("mode", (byte) mode.ordinal());
	}

	public Mode getMode(final ItemStack stack) {
		final NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
		if(!nbt.hasKey("mode")) {
			return Mode.ENABLED;
		}
		return this.getMode(nbt.getByte("mode"));
	}

	private Mode getMode(int mode) {
		if(mode < 0 || mode >= Mode.values.length) {
			mode = 0;
		}
		return Mode.values[mode];
	}

	public enum Mode {
		ENABLED, DISABLED, NOT_IN_HAND;
		public static final Mode[] values;
		static {
			values = values();
		}
	}
}
