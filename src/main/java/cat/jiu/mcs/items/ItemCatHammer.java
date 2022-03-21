package cat.jiu.mcs.items;

import java.util.List;

import cat.jiu.mcs.config.Configs;
import cat.jiu.core.util.JiuRandom;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseItemNormal;
import cat.jiu.mcs.util.init.MCSBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCatHammer extends BaseItemNormal {
	public ItemCatHammer(String name, CreativeTabs tab) {
		super(name, tab);
		super.setMaxStackSize(1);
	}
	
	JiuRandom rand = new JiuRandom(10104);
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(pos.getY() != 0 && (worldIn.getBlockState(pos).getBlock() == Blocks.BEDROCK || worldIn.getBlockState(pos).getBlock() == MCSBlocks.minecraft.normal.C_BEDROCK_B)) {
			ItemStack stack = player.getHeldItem(hand);
			if(stack.getItem() == this) {
				JiuUtils.nbt.addItemNBT(stack, "time", 1);
			}
			if(JiuUtils.nbt.getItemNBTInt(stack, "time") >= 10) {
				JiuUtils.nbt.setItemNBT(stack, "time", 0);
				
				if(rand.nextInt(9)+rand.nextFloat() <= (Configs.Custom.custem_already_stuff.item.break_bedrock_chance * 10)) {
					worldIn.playSound(null, pos, SoundEvents.BLOCK_METAL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
					worldIn.playEvent(2001, pos, Block.getStateId(Blocks.BEDROCK.getDefaultState()));
					Block.spawnAsEntity(worldIn, pos, JiuUtils.item.getStackFormBlockState(worldIn.getBlockState(pos)));
					JiuUtils.nbt.addItemNBT(stack, "bedrocks", 1);
					worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
				}else {
					worldIn.playSound(null, pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					worldIn.playEvent(2001, pos, Block.getStateId(Blocks.OBSIDIAN.getDefaultState()));
				}
			}
			return EnumActionResult.SUCCESS;
		}else {
			return EnumActionResult.PASS;
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("item.mcs.cat_hammer.info.0") + ": " + JiuUtils.nbt.getItemNBTLong(stack, "bedrocks"));
		tooltip.add(I18n.format("item.mcs.cat_hammer.info.1", Configs.Custom.custem_already_stuff.item.break_bedrock_chance * 10));
	}
}
