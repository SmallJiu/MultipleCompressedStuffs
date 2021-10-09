package cat.jiu.mcs.util;

import cat.jiu.core.api.events.entity.IEntityDeathEvent;
import cat.jiu.core.api.events.entity.IEntityTickEvent;
import cat.jiu.core.util.JiuRandom;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.init.MCSItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CatEvent implements IEntityDeathEvent, IEntityTickEvent{
	
	private static final JiuRandom rand = new JiuRandom(50);
	
	@Override
	public void onEntityDeath(Entity entity, World world, BlockPos pos) {
		if(Configs.custom.custem_already_stuff.item.can_drop_cat_hair) {
			if(entity instanceof EntityOcelot) {
				EntityOcelot ocelot = (EntityOcelot) entity;
				onCatDropItem(ocelot, world, pos, rand.nextInt(5), true);
			}
		}
	}
	
	@Override
	public void onEntityTick(Entity entity, World world, BlockPos pos) {
		if(Configs.custom.custem_already_stuff.item.can_drop_cat_hair) {
			if(entity instanceof EntityOcelot) {
				EntityOcelot ocelot = (EntityOcelot)entity;
				onCatDropItem(ocelot, world, pos, rand.nextInt(999), false);
			}
		}
	}
	
	static int tick = 0;
	
	private static void onCatDropItem(EntityOcelot cat, World world, BlockPos pos, int chance, boolean death) {
		if(death) {
			JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(MCSItems.normal.CAT_HAIR, rand.nextInt(3)));
		}else {
			tick++;
			if(tick == 40) {
				tick = 0;
				int chances = (int) (Configs.custom.custem_already_stuff.item.drop_cat_hair_chance * 1000);
				if(chance >= chances) {
					JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(MCSItems.normal.CAT_HAIR, rand.nextInt(3)));
				}
			}
		}
	}
}
