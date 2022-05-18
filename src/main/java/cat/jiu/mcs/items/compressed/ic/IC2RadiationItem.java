package cat.jiu.mcs.items.compressed.ic;

import cat.jiu.core.api.events.item.IItemInPlayerHandTick;
import cat.jiu.core.api.events.item.IItemInPlayerInventoryTick;
import cat.jiu.core.util.JiuCoreEvents;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.base.sub.BaseItemSub;

import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

/**
 * Has ic2 Radiation effect item
 * 
 * @author small_jiu
 *
 */
public class IC2RadiationItem extends BaseItemSub implements IItemInPlayerInventoryTick, IItemInPlayerHandTick {
	public static IC2RadiationItem register(String name, ItemStack baseItem, String langModId, CreativeTabs tab) {
		if(baseItem == null || baseItem.isEmpty()) {
			return null;
		}
		if(Loader.isModLoaded(langModId) || langModId.equals("custom")) {
			return new IC2RadiationItem(name, baseItem, tab);
		}else {
			return null;
		}
	}

	public static IC2RadiationItem register(String name, ItemStack baseItem, String langModId) {
		return register(name, baseItem, langModId, MCS.COMPERESSED_ITEMS);
	}

	public IC2RadiationItem(String name, ItemStack baseItem, CreativeTabs tab) {
		super(name, baseItem, tab);
		JiuCoreEvents.addEvent(this);
	}

	public IC2RadiationItem(String name, ItemStack baseItem) {
		this(name, baseItem, MCS.COMPERESSED_ITEMS);
	}

	@Override
	public void onItemInPlayerInventoryTick(EntityPlayer player, ItemStack invStack, int slot) {
		if(invStack.getItem() instanceof IC2RadiationItem) {
			if(!ItemArmorHazmat.hasCompleteHazmat(player)) {
				IC2Potion.radiation.applyTo(player, (int) MCSUtil.item.getMetaValue(200, invStack.getMetadata()), 100);
			}
		}
	}

	@Override
	public void onItemInPlayerHandTick(EntityPlayer player, ItemStack mainHand, ItemStack offHand) {
		if(mainHand != null && !mainHand.isEmpty() && mainHand.getItem() instanceof IC2RadiationItem) {
			if(!ItemArmorHazmat.hasCompleteHazmat(player)) {
				IC2Potion.radiation.applyTo(player, (int) MCSUtil.item.getMetaValue(200, mainHand.getMetadata()), 100);
			}
		}else if(offHand != null && !offHand.isEmpty() && offHand.getItem() instanceof IC2RadiationItem) {
			if(!ItemArmorHazmat.hasCompleteHazmat(player)) {
				IC2Potion.radiation.applyTo(player, (int) MCSUtil.item.getMetaValue(200, offHand.getMetadata()), 100);
			}
		}
	}
}