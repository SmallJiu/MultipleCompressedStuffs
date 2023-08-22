package cat.jiu.mcs.items.compressed.ic;

import cat.jiu.core.events.item.ItemInPlayerEvent;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;

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
		if(event.stack.getItem() instanceof IC2RadiationItem) {
			if(!ItemArmorHazmat.hasCompleteHazmat(event.getEntityPlayer())) {
				IC2Potion.radiation.applyTo(event.getEntityPlayer(), (int) MCSUtil.item.getMetaValue(200, event.stack.getMetadata()), 100);
			}
		}
	}

	@SubscribeEvent
	public void onItemInPlayerHandTick(ItemInPlayerEvent.InHand event) {
		if(event.stack.getItem() instanceof IC2RadiationItem && !ItemArmorHazmat.hasCompleteHazmat(event.getEntityPlayer())) {
			IC2Potion.radiation.applyTo(event.getEntityPlayer(), (int) MCSUtil.item.getMetaValue(200, event.stack.getMetadata()), 100);
		}
	}
}
