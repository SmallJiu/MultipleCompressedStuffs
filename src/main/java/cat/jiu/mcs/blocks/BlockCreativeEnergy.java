package cat.jiu.mcs.blocks;

import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.tileentity.TileEntityCreativeEnergy;
import cat.jiu.mcs.util.init.MCSResources;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCreativeEnergy extends BaseBlock.Normal implements ITileEntityProvider {
	public BlockCreativeEnergy() {
		super(MCS.MODID, "creative_energy", Material.ROCK, SoundType.STONE, CreativeTabs.TRANSPORTATION, 10F);
		this.setBlockModelResourceLocation(MCS.MODID + "/block", this.name);
		MCSResources.BLOCKS_NAME.add(this.name);
		MCSResources.BLOCKS.add(this);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(I18n.format("tile.mcs.creative_energy.info.0"));
		tooltip.add(I18n.format("tile.mcs.creative_energy.info.1", JiuUtils.big_integer.format(Integer.MAX_VALUE * 10L, 3) + "/t (" + Integer.MAX_VALUE * 10L + "/t)"));
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCreativeEnergy(meta);
	}
	
	@Override
	public ItemBlock getRegisterItemBlock() {
		return (ItemBlock) new ItemBlock(this).setRegistryName(this.getRegistryName());
	}
}
