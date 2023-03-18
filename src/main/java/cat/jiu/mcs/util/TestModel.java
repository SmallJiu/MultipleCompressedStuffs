package cat.jiu.mcs.util;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class TestModel {
	public static boolean test = false;

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
		isTest(event.getEntity());
		toVoid(event.getEntity());
	}
	
	@SubscribeEvent
	public static void onEntityTick(LivingUpdateEvent event) {
		isTest(event.getEntity());
		toVoid(event.getEntity());
	}

	private static void isTest(Entity entity) {
		if(!test) {
			return;
		}
		if(MCS.dev()) {
			test = MCS.dev();
			return;
		}
		if(entity instanceof EntityPlayer) {
			if(entity.getName().indexOf("Player") == 0) {
				test = MCS.dev();
				if(entity.world.isRemote) {
					JiuUtils.entity.sendMessage(entity, "TestMode Is: " + test);
				}
			}
		}
	}

	private static void toVoid(Entity entity) {
		if(test) {
			if(entity instanceof IMob) {
				entity.setPosition(entity.getPosition().getX(), -3, entity.getPosition().getZ());
				((EntityLivingBase) entity).setHealth(0);
				return;
			}
		}
	}
}
