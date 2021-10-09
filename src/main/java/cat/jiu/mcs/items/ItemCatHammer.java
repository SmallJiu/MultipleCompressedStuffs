package cat.jiu.mcs.items;

import java.util.List;

import cat.jiu.mcs.config.Configs;
import cat.jiu.core.util.JiuRandom;
import cat.jiu.mcs.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseItemNormal;
import cat.jiu.mcs.util.init.MCSBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCatHammer extends BaseItemNormal{

	public ItemCatHammer(String name, CreativeTabs tab) {
		super(name, tab);
	}
	
	NBTTagCompound nbt0 = this.getNBTShareTag(new ItemStack(this));
	NBTTagCompound nbt = nbt0 == null ? new NBTTagCompound() : nbt0;
	JiuRandom rand = new JiuRandom();
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(worldIn.getBlockState(pos).equals(Blocks.BEDROCK.getDefaultState()) || worldIn.getBlockState(pos).getBlock().equals(MCSBlocks.minecraft.normal.C_BEDROCK_B)) {
			if(pos.getY() != 0) {
				if(player.getHeldItem(hand).getItem() == this) {
					ItemStack stack = player.getHeldItem(hand);
					nbt.setInteger("time", nbt.getInteger("time") + 1);
					stack.setTagCompound(nbt);
				}
				if(nbt.getInteger("time") >= 10) {
					nbt.setInteger("time", 0);
					
					if(rand.nextInt(9) <= (Configs.custom.custem_already_stuff.item.break_bedrock_chance * 10)) {
						worldIn.playSound(null, pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
						worldIn.playEvent(2001, pos, Block.getStateId(Blocks.BEDROCK.getDefaultState()));
						Block.spawnAsEntity(worldIn, pos, JiuUtils.item.getStackFormBlockState(worldIn.getBlockState(pos)));
						nbt.setLong("bedrocks", nbt.getLong("bedrocks") + 1);
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
		}else {
			return EnumActionResult.PASS;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("BerakBedrocks: " + nbt.getLong("bedrocks"));
	}
}
