package cat.jiu.mcs.items.compressed.ic;

import cat.jiu.core.events.item.ItemInPlayerEvent;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Has ic2 Radiation effect item
 * 
 * @author small_jiu
 *
 */
public class IC2RadiationItem extends BaseCompressedItem {
	public static IC2RadiationItem register(String name, ItemStack baseItem, String langModId) {
		if(baseItem == null || baseItem.isEmpty()) {
			return null;
		}
		if(Loader.isModLoaded(langModId) || langModId.equals("custom")) {
			return new IC2RadiationItem(name, baseItem);
		}else {
			return null;
		}
	}

	public IC2RadiationItem(String name, ItemStack baseItem) {
		super(name, baseItem);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onItemInPlayerInventoryTick(ItemInPlayerEvent.InInventory event) {
		onItemInPlayerInventoryTick(event.getEntityPlayer(), event.stack, event.slot);
	}

	private void onItemInPlayerInventoryTick(EntityPlayer player, ItemStack invStack, int slot) {
		if(invStack.getItem() instanceof IC2RadiationItem) {
			if(!ItemArmorHazmat.hasCompleteHazmat(player)) {
				IC2Potion.radiation.applyTo(player, (int) MCSUtil.item.getMetaValue(200, invStack.getMetadata()), 100);
			}
		}
	}

	@SubscribeEvent
	public void onItemInPlayerHandTick(ItemInPlayerEvent.InHand event) {
		if(event.isMainHand) {
			onItemInPlayerHandTick(event.getEntityPlayer(), event.stack, null);
		}else {
			onItemInPlayerHandTick(event.getEntityPlayer(), null, event.stack);
		}
	}

	private void onItemInPlayerHandTick(EntityPlayer player, ItemStack mainHand, ItemStack offHand) {
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
