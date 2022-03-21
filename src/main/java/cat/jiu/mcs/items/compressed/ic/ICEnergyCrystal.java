package cat.jiu.mcs.items.compressed.ic;

import java.util.List;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.base.sub.BaseItemSub;

import ic2.api.item.ICustomDamageItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.item.BaseElectricItem;
import ic2.core.item.IPseudoDamageItem;
import ic2.core.util.LogCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ICEnergyCrystal extends BaseItemSub implements IElectricItem, IPseudoDamageItem, ICustomDamageItem {
	
	protected final double baseMaxEnergy;
	protected final int energyLevel;
	protected final double fransferLimit;
	
	public ICEnergyCrystal(String name, ItemStack baseItem, CreativeTabs tab) {
		super(name, baseItem, tab);
		this.setMaxStackSize(1);
		this.setInfoStack(ItemStack.EMPTY);
		if(baseItem.getItem() instanceof IElectricItem) {
			IElectricItem base = (IElectricItem) baseItem.getItem();
			this.baseMaxEnergy = base.getMaxCharge(baseItem);
			this.energyLevel = base.getTier(baseItem);
			this.fransferLimit = base.getTransferLimit(baseItem);
		}else {
			throw new RuntimeException("'" + baseItem.toString() + "' is NOT IC Energy Item");
		}
	}
	public ICEnergyCrystal(String name, ItemStack baseItem) {
		this(name, baseItem, MCS.COMPERESSED_ITEMS);
	}
	public ICEnergyCrystal(String name, String unCompressedItem, int meta, CreativeTabs tab) {
		this(name, new ItemStack(Item.getByNameOrId("ic2:" + unCompressedItem), 1, meta), tab);
	}
	public ICEnergyCrystal(String name, String unCompressedItem, CreativeTabs tab) {
		this(name, unCompressedItem, 0, tab);
	}
	public ICEnergyCrystal(String name, String unCompressedItem) {
		this(name, unCompressedItem, 0, MCS.COMPERESSED_ITEMS);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		if(Minecraft.getMinecraft().currentScreen instanceof GuiCrafting) {
			if(((GuiCrafting)Minecraft.getMinecraft().currentScreen).getSlotUnderMouse() instanceof SlotCrafting) {
				tooltip.add(TextFormatting.RED + I18n.format("info.mcs.warn.ic.craft_energy"));
			}
		}
		super.addInformation(stack, world, tooltip, advanced);
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
}
