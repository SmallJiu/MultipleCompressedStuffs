package cat.jiu.mcs.util.event;

import java.util.List;

import cat.jiu.core.util.JiuRandom;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.base.sub.BaseCompressedFood;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@EventBusSubscriber
public class OnPlayerLogging {
	@SubscribeEvent
	public static void onPlayerLogging(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;

		if(player.getName().equals(MCS.OWNER)) {
			if(JiuUtils.day.isTheDay(9, 7)) {
				JiuUtils.entity.sendMessage(player, "Happy birthday! My Master!");

				player.addItemStackToInventory(new ItemStack(MCSItems.minecraft.food.C_ENCHANTED_GOLD_APPLE_F, 1, 9));
			}else {
				JiuUtils.entity.sendMessage(player, "\u4e3b\u4eba\u60a8\u6765\u5566\uff01\u8fd9\u662f\u7ed9\u60a8\u7684\u4ff8\u7984");

				player.addItemStackToInventory(new ItemStack(MCSItems.minecraft.food.C_COOKED_BEEF_F, 4, 5));
			}
		}else {
			if(Configs.Custom.custem_already_stuff.logging_give_food) {
				ItemStack stack = randStack(player);

				if(!(stack.getItem() instanceof ItemAir)) {
					JiuUtils.entity.sendMessage(player, "info.mcs.logging.name");

					JiuUtils.entity.sendMessage(player, stack.getDisplayName() + " x " + stack.getCount());
					player.addItemStackToInventory(stack);
				}
			}
		}
	}

	static JiuRandom rand = new JiuRandom();
	private static ItemStack randStack(EntityPlayer player) {
		List<BaseCompressedFood> foods = MCSResources.getFoods();
		int seed = rand.nextIntNoZero(foods.size());
		
		int item = rand.nextInt(seed);
		int amount = rand.nextInt(10) > 5 ? 2 : 1;
		int meta = rand.nextInt(10) > 6 ? 1 : 0;

		ItemStack stack = new ItemStack(foods.get(item), amount, meta);

		if(stack.isEmpty()) {
			if(rand.nextInt(20) > 10) {
				JiuUtils.entity.sendMessage(player, "info.mcs.logging.air.name");
				return stack;
			}else {
				return randStack(player);
			}
		}else {
			return stack;
		}
	}
}