package cat.jiu.mcs.items.compressed.ic;

import java.util.List;
import java.util.Locale;

import cat.jiu.core.api.events.item.IItemInPlayerInventoryTick;
import cat.jiu.core.util.JiuCoreEvents;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.base.sub.BaseItemSub;

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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ICBatteryCharge extends BaseItemSub implements IItemInPlayerInventoryTick, IElectricItem, IPseudoDamageItem, ICustomDamageItem {
	protected final double baseMaxEnergy;
	protected final int energyLevel;
	protected final double fransferLimit;
	protected final ItemBatteryChargeHotbar baseBar;
	
	public ICBatteryCharge(String name, ItemStack baseItem, CreativeTabs tab) {
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
		JiuCoreEvents.addEvent(this);
	}
	public ICBatteryCharge(String name, ItemStack baseItem) {
		this(name, baseItem, MCS.COMPERESSED_ITEMS);
	}
	public ICBatteryCharge(String name, String unCompressedItem, int meta, CreativeTabs tab) {
		this(name, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, Short.MAX_VALUE), tab);
	}
	public ICBatteryCharge(String name, String unCompressedItem, CreativeTabs tab) {
		this(name, unCompressedItem, Short.MAX_VALUE, tab);
	}
	public ICBatteryCharge(String name, String unCompressedItem) {
		this(name, unCompressedItem, Short.MAX_VALUE, MCS.COMPERESSED_ITEMS);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		this.baseBar.addInformation(stack, world, tooltip, advanced);
		if(Minecraft.getMinecraft().currentScreen instanceof GuiCrafting) {
			if(((GuiCrafting)Minecraft.getMinecraft().currentScreen).getSlotUnderMouse() instanceof SlotCrafting) {
				tooltip.add(TextFormatting.RED + I18n.format("info.mcs.warn.ic.craft_energy"));
			}
		}
		
		super.addInformation(stack, world, tooltip, advanced);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if(this.baseBar != null) {
			return this.baseBar.onItemRightClick(world, player, hand);
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void onItemInPlayerInventoryTick(EntityPlayer player, ItemStack invStack, int slot) {
		if(invStack.getItem() instanceof ICBatteryCharge) {
			Mode mode = this.getMode(invStack);
			if(!player.getEntityWorld().isRemote && mode != Mode.DISABLED) {
				List<ItemStack> inv = player.inventory.mainInventory;
				double limit = this.getTransferLimit(invStack);
	            int tier = this.getTier(invStack);
	            for(int invslot = 0; invslot < inv.size(); invslot++) {
	            	ItemStack toCharge = inv.get(invslot);
	                if(!toCharge.isEmpty()) {
	                	if(mode != Mode.NOT_IN_HAND || player.inventory.currentItem != invslot) {
	                		if (!(toCharge.getItem() instanceof ItemBatteryChargeHotbar) && !(toCharge.getItem() instanceof ICBatteryCharge)) {
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
                		if (!(offHand.getItem() instanceof ItemBatteryChargeHotbar) && !(offHand.getItem() instanceof ICBatteryCharge)) {
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
	                		if (!(armor.getItem() instanceof ItemBatteryChargeHotbar) && !(armor.getItem() instanceof ICBatteryCharge)) {
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
		return this.getCustomDamage(stack) > 0;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
        return (this.getMaxCharge(stack) - this.getCharge(stack)) / this.getMaxCharge(stack);
    }
	
	@Override
    public int getCustomDamage(ItemStack stack) {
        if (!stack.hasTagCompound()) {
            return 0;
        }
        return (int) stack.getTagCompound().getDouble("charge");
    }
	
	@Override
	public int getMaxCustomDamage(ItemStack stack) {
		return (int) this.getMaxCharge(stack);
	}

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
        if (damage != prev && BaseElectricItem.logIncorrectItemDamaging) {
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
		if (!stack.hasTagCompound()) {
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
		return this.fransferLimit * (this.fransferLimit * ((stack.getMetadata() + 1) * 0.327));
	}
	
	public String getNameOfMode(final Mode mode) {
        return Localization.translate("ic2.tooltip.mode." + mode.toString().toLowerCase(Locale.ENGLISH));
    }
    
    public void setMode(final ItemStack stack, final Mode mode) {
        final NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        nbt.setByte("mode", (byte)mode.ordinal());
    }
    
    public Mode getMode(final ItemStack stack) {
        final NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        if (!nbt.hasKey("mode")) {
            return Mode.ENABLED;
        }
        return this.getMode(nbt.getByte("mode"));
    }
    
    private Mode getMode(int mode) {
        if (mode < 0 || mode >= Mode.values.length) {
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
