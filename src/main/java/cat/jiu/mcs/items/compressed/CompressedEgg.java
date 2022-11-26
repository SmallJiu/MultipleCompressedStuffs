package cat.jiu.mcs.items.compressed;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class CompressedEgg extends BaseCompressedItem {
	public CompressedEgg(String modid, ItemStack unItem) {
		super(modid, unItem);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		ActionResult<ItemStack> lag = new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		
		if(MCS.dev()) {
			{
				world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

				if(!world.isRemote) {
					EntityEgg egg = new EntityEgg(world, player);
					egg.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
					world.spawnEntity(egg);
				}

				player.addStat(StatList.getObjectUseStats(this));
				lag = new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
			}
			if(JiuUtils.nbt.getItemNBTInt(stack, "uses") >= 8) {
				if(stack.getCount() == 1) {
					if(stack.getMetadata() == 0) {
						player.setHeldItem(hand, ItemStack.EMPTY);
					}else {
						player.setHeldItem(hand, new ItemStack(this, 8, stack.getMetadata() - 1));
						// JiuUtils.nbt.setItemNBT(stack, "uses", 0);
					}
				}else {
					if(stack.getMetadata() == 0) {
						stack.shrink(1);
						JiuUtils.nbt.setItemNBT(stack, "uses", 0);
					}else {
						stack.shrink(1);
						player.addItemStackToInventory(new ItemStack(this, 8, stack.getMetadata() - 1));
						JiuUtils.nbt.setItemNBT(stack, "uses", 0);
					}
				}
			}else {
				JiuUtils.nbt.addItemNBT(stack, "uses", 1);
			}
			JiuUtils.entity.sendMessage(player, JiuUtils.nbt.getItemNBTInt(stack, "uses") + "");
		}

		return lag;
	}

}
