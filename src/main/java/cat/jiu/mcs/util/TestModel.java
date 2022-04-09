package cat.jiu.mcs.util;

import cat.jiu.core.api.events.entity.IEntityJoinWorldEvent;
import cat.jiu.core.api.events.entity.IEntityTickEvent;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TestModel implements IEntityJoinWorldEvent, IEntityTickEvent {
	public static boolean test = false;

	@Override
	public void onEntityJoinWorld(Entity entity, World world, BlockPos pos, int dim) {
		isTest(entity);
		toVoid(entity);
	}

	@Override
	public void onEntityTick(Entity entity, World world, BlockPos pos) {
		isTest(entity);
		toVoid(entity);
	}

	private static void isTest(Entity entity) {
		if(!test) {
			return;
		}
		if(MCS.instance.test_model) {
			test = MCS.instance.test_model;
			return;
		}
		if(entity instanceof EntityPlayer) {
			if(entity.getName().indexOf("Player") == 0) {
				test = MCS.instance.test_model;
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
