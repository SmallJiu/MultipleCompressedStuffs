package cat.jiu.mcs.util.event;

import cat.jiu.core.util.JiuRandom;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.init.MCSItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class CatEvent {
	private static final JiuRandom rand = new JiuRandom();
	
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		Entity entity = event.getEntity();
		if(Configs.Custom.custem_already_stuff.item.can_drop_cat_hair && entity instanceof EntityOcelot) {
			onCatDropItem((EntityOcelot) entity, entity.world, entity.getPosition(), true);
		}
	}
	
	@SubscribeEvent
	public static void onEntityTick(LivingUpdateEvent event) {
		Entity entity = event.getEntity();
		if(Configs.Custom.custem_already_stuff.item.can_drop_cat_hair && entity instanceof EntityOcelot) {
			onCatDropItem((EntityOcelot) entity, entity.world, entity.getPosition(), false);
		}
	}

	private static void onCatDropItem(EntityOcelot cat, World world, BlockPos pos, boolean death) {
		if(death) {
			JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(MCSItems.normal.CAT_HAIR, rand.nextInt(3)));
		}else {
			cat.getEntityData().setInteger("HairTick", cat.getEntityData().getInteger("HairTick")+1);
			if(cat.getEntityData().getInteger("HairTick") >= 20) {
				cat.getEntityData().setInteger("HairTick", 0);
				if(rand.nextInt(1001) <= Configs.Custom.custem_already_stuff.item.drop_cat_hair_chance * 1000) {
					JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(MCSItems.normal.CAT_HAIR, rand.nextInt(3)));
				}
			}
		}
	}
}
