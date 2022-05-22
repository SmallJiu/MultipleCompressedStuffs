package cat.jiu.mcs.util.event;

import cat.jiu.core.api.events.entity.IEntityDeathEvent;
import cat.jiu.core.api.events.entity.IEntityTickEvent;
import cat.jiu.core.util.JiuRandom;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.init.MCSItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CatEvent implements IEntityDeathEvent, IEntityTickEvent {
	private final JiuRandom rand = new JiuRandom();
	@Override
	public void onEntityDeath(Entity entity, World world, BlockPos pos) {
		if(Configs.Custom.custem_already_stuff.item.can_drop_cat_hair && entity instanceof EntityOcelot) {
			onCatDropItem((EntityOcelot) entity, world, pos, true);
		}
	}

	@Override
	public void onEntityTick(Entity entity, World world, BlockPos pos) {
		if(Configs.Custom.custem_already_stuff.item.can_drop_cat_hair && entity instanceof EntityOcelot) {
			onCatDropItem((EntityOcelot) entity, world, pos, false);
		}
	}

	private void onCatDropItem(EntityOcelot cat, World world, BlockPos pos, boolean death) {
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
