package cat.jiu.mcs.items;

import java.util.ArrayList;
import java.util.List;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.core.util.JiuRandom;
import cat.jiu.mcs.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseItemNormal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.SoundEvent;

public class ItemDestroyer extends BaseItemNormal {
	

	public ItemDestroyer(String name, CreativeTabs tab) {
		super(name, tab);
	}
	
	JiuRandom rand = new JiuRandom(10104);
	NBTTagCompound nbt0 = this.getNBTShareTag(new ItemStack(this));
	NBTTagCompound nbt = nbt0 == null ? new NBTTagCompound() : nbt0;
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		EnumActionResult lag = EnumActionResult.FAIL;
		String[] configs = Configs.custom.custem_already_stuff.block.custem_break_drop_item;
		List<ItemStack> drop_items = new ArrayList<ItemStack>(); // 掉落物品的列表
		IBlockState state = world.getBlockState(pos);
		String Oname = state.getBlock().getRegistryName()+""; //pos的name
		int Ometa = JiuUtils.item.getMetaFormBlockState(state); //pos的meta
		
		if(configs.length != 1) { // 如果配置文件还有除了注释外的内容
			for(int i = 1; i < configs.length; ++i) {
				
				String str0  = configs[i]; //总配置文件的单一配置项
				
				String[] strri = JiuUtils.other.custemSplitString(str0, "#"); //转为方块的配置
				//检测pos的方块的名字是不是等于配置文件的名字
				if(Oname.equals(strri[0])) {
					try {
						//配置文件的方块的meta
						int strmeta = new Integer(strri[1]);
						
						//如果pos的meta跟配置文件的meta一样
						if(Ometa == strmeta) {
							{
								//从String转为数组
								String[] stacklist = JiuUtils.other.custemSplitString(strri[2], "|");
								
								//从一维转二维
								String[][] stacklist2 = JiuUtils.other.one2two(new String[stacklist.length / 3][3], stacklist, 0);
								
								for(int k = 0; k < stacklist2.length; ++k) {
									//给可掉落物品的list添加
									ItemStack stack = JiuUtils.item.getStackFromString(stacklist2[k][0], stacklist2[k][1], stacklist2[k][2]);
									drop_items.add(stack);
								}
							}
							
//							SoundEvent sound = SoundEvent.REGISTRY.getObject(new ResourceLocation("botania:way")); // 这个是一个梗
							SoundEvent sound = SoundEvent.REGISTRY.getRandomObject(this.rand);//随机挑选一位小朋友出来叫
							world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1.0F, 1.0F);
							world.playEvent(rand.nextIntFromRange(2000, 2007), pos, rand.nextInt(500));
							
							nbt.setDouble("breaks", nbt.getDouble("breaks") + 0.5D);
							
							JiuUtils.item.spawnAsEntity(world, pos, drop_items, true); //生成掉落物品
							world.setBlockState(pos, Blocks.AIR.getDefaultState());// 设为空气
							lag = EnumActionResult.SUCCESS;
							break;
						}
					} catch (Exception e) {
						//如果meta的位置不是数字则输出错误log
						MCS.instance.log.fatal("\"" + strri[0] +  "\": "+ "\"" + strri[1] + "\"" + " is not Number!");
					}
				}
			}
		}
		return lag;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("BreakBlocks: " + nbt.getDouble("breaks"));
	}
}
