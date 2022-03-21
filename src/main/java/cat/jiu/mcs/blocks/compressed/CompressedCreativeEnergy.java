package cat.jiu.mcs.blocks.compressed;

import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.blocks.tileentity.TileEntityCreativeEnergy;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CompressedCreativeEnergy extends BaseBlockSub {
	public CompressedCreativeEnergy(String nameIn, ItemStack unCompressedItem) {
		super(nameIn, unCompressedItem, "mcs", CreativeTabs.TRANSPORTATION);
		this.setInfoStack(new ItemStack(Items.AIR));
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, world, tooltip, advanced);
		tooltip.add(I18n.format("tile.mcs.creative_energy.info.0"));
		tooltip.add(I18n.format("tile.mcs.creative_energy.info.1", JiuUtils.big_integer.format(Integer.MAX_VALUE * (10L * (stack.getMetadata() + 5)), 3) + " (" + Integer.MAX_VALUE * (10L * (stack.getMetadata() + 5)) + ")"));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCreativeEnergy((10 * (meta + 4))+9);
	}
}
