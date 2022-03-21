package cat.jiu.mcs.items;

import cat.jiu.core.util.base.BaseItem;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDeBug extends BaseItem.Normal {
	public ItemDeBug() {
		super(MCS.MODID, "debug");
		MCSResources.ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!world.isRemote
		&& player.isSneaking()
		&& player.getHeldItem(hand).getItem() == MCSItems.normal.debug) {
			TileEntity te0 = world.getTileEntity(pos);
			if(te0 instanceof TileEntityCompressor) {
				((TileEntityCompressor) te0).setDebug();
			}
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return "Debug";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void getItemModel() {
		this.model.registerItemModel(this, "normal/items", this.name);
	}
}
