package cat.jiu.mcs.util;

import java.util.Date;

import cat.jiu.core.util.JiuRandom;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.init.MCSItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAir;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@SuppressWarnings("deprecation")
@EventBusSubscriber
public class OnPlayerLogging {
	
	@SubscribeEvent
	public static void onPlayerLogging(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		
		if(player.getName().indexOf(MCS.OWNER) == 0) {
			Date date = new Date();
			
			if((date.getMonth() + 1) == 9) {
				if(date.getDay() == 7) {
					JiuUtils.entity.sendMessage(player, "Happy birthday! My Master!");
					
					player.addItemStackToInventory(new ItemStack(MCSItems.minecraft.normal.food.C_ENCHANTED_GOLD_APPLE_F, 1, 9));
				}else {
					JiuUtils.entity.sendMessage(player, "\u4e3b\u4eba\u60a8\u6765\u5566\uff01\u8fd9\u662f\u7ed9\u60a8\u7684\u4ff8\u7984");
					
					player.addItemStackToInventory(new ItemStack(MCSItems.minecraft.normal.food.C_COOKED_BEEF_F, 4, 5));
				}
			}else {
				JiuUtils.entity.sendMessage(player, "\u4e3b\u4eba\u60a8\u6765\u5566\uff01\u8fd9\u662f\u7ed9\u60a8\u7684\u4ff8\u7984");
				
				player.addItemStackToInventory(new ItemStack(MCSItems.minecraft.normal.food.C_COOKED_BEEF_F, 4, 5));
			}
		}else {
			if(Configs.custom.custem_already_stuff.logging_give_food) {
				ItemStack stack = randStack(player);
				
				if(!(stack.getItem() instanceof ItemAir)) {
					if(player.getEntityWorld().isRemote) {
						JiuUtils.entity.sendMessage(player, I18n.format("info.mcs.logging.name", 0));
					}
					JiuUtils.entity.sendMessage(player, stack.getDisplayName() + " x " + stack.getCount());
					player.addItemStackToInventory(stack);
				}
			}
		}
	}
	
	private static ItemStack randStack(EntityPlayer player) {
		JiuRandom rand = new JiuRandom();
		int seed = rand.nextIntNoZero(MCSItems.FOODS.size());
		int item = rand.nextInt(seed);
		
		ItemStack stack = new ItemStack(MCSItems.FOODS.get(item), rand.nextInt(10) > 5 ? 2 : 1, rand.nextInt(10) > 6 ? 1 : 0);
		
		if(stack.getItem() instanceof ItemAir) {
			if(rand.nextInt(20) > 10) {
				if(player.getEntityWorld().isRemote) {
					JiuUtils.entity.sendMessage(player, I18n.format("info.mcs.logging.air.name", 0));
				}
				return stack;
			}else {
				return randStack(player);
			}
		}else {
			return stack;
		}
	}
}