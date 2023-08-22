package cat.jiu.mcs.items;

import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseItem;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDeBug extends BaseItem.Normal {
	public ItemDeBug(String name) {
		super(MCS.MODID, name, CreativeTabs.TOOLS);
//		MCSResources.ITEMS.add(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format("info.mcs.debug.info"));
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			MCS.getLogger().info("=========================");
			TileEntity te = world.getTileEntity(pos);
			MCS.getLogger().info("PosTileEntityClass: {}", te == null ? null : te.getClass());
			MCS.getLogger().info("TileEntityNBT: {}", te == null ? null : te.writeToNBT(new NBTTagCompound()));
			
			IBlockState block = world.getBlockState(pos);
			MCS.getLogger().info("PosBlockClass: {}, Name: {}", block.getBlock().getClass(), block.getBlock().getRegistryName());
			
			MCS.getLogger().info("OffHandItem: {}", player.getHeldItemOffhand());
			
			if(player.isSneaking() && te instanceof TileEntityCompressor) {
				if(!JiuUtils.nbt.hasNBT(player.getHeldItem(hand), "nobreak")) {
					if(player.getHeldItem(hand).getCount() <= 1) {
						player.setItemStackToSlot(hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
					}else {
						player.setItemStackToSlot(hand == EnumHand.MAIN_HAND ? EntityEquipmentSlot.MAINHAND : EntityEquipmentSlot.OFFHAND, JiuUtils.item.copyStack(player.getHeldItem(hand), player.getHeldItem(hand).getCount()-1, false));
					}
				}
				world.playEvent(2005, pos, 500);
				MCS.getLogger().info(player.getName() + " Use Debug Item in: Save:" + world.getSaveHandler().loadWorldInfo().getWorldName() + ", Dimension:" + player.dimension + ", X:" + pos.getX() + ", Y:" + pos.getY() + ", Z:" + pos.getZ());
				((TileEntityCompressor) te).setDebug();
			}
		}
		return EnumActionResult.SUCCESS;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return "Debug";
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(this.isInCreativeTab(tab)) {
			items.add(JiuUtils.nbt.setItemNBT(new ItemStack(this), "nobreak", (byte) 1));
		}
	}
}
