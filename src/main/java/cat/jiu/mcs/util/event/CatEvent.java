package cat.jiu.mcs.util.event;

import cat.jiu.core.api.events.entity.IEntityDeathEvent;
import cat.jiu.core.api.events.entity.IEntityTickEvent;
import cat.jiu.core.util.JiuRandom;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.init.MCSItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CatEvent implements IEntityDeathEvent, IEntityTickEvent {

	private static final JiuRandom rand = new JiuRandom(50);

	@Override
	public void onEntityDeath(Entity entity, World world, BlockPos pos) {
		if(Configs.Custom.custem_already_stuff.item.can_drop_cat_hair) {
			if(entity instanceof EntityOcelot) {
				EntityOcelot ocelot = (EntityOcelot) entity;
				onCatDropItem(ocelot, world, pos, rand.nextInt(5), true);
			}
		}
	}

	@Override
	public void onEntityTick(Entity entity, World world, BlockPos pos) {
		if(Configs.Custom.custem_already_stuff.item.can_drop_cat_hair) {
			if(entity instanceof EntityOcelot) {
				EntityOcelot ocelot = (EntityOcelot) entity;
				onCatDropItem(ocelot, world, pos, rand.nextInt(999), false);
			}
		}
	}

	int tick = 0;

	private void onCatDropItem(EntityOcelot cat, World world, BlockPos pos, int chance, boolean death) {
		if(death) {
			JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(MCSItems.normal.CAT_HAIR, rand.nextInt(3)));
		}else {
			tick++;
			if(tick == 40) {
				tick = 0;
				int chances = (int) (1000 - (Configs.Custom.custem_already_stuff.item.drop_cat_hair_chance * 1000));
				MCS.instance.log.info(chances + "");
				if(chance <= chances) {
					MCS.instance.log.info(chance + "");
					JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(MCSItems.normal.CAT_HAIR, rand.nextInt(3)));
				}
			}
		}
	}
}
