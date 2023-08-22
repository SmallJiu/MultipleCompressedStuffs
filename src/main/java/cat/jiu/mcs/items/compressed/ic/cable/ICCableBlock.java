package cat.jiu.mcs.items.compressed.ic.cable;

import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.tileentity.ic.TileEntityCompressedCable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ICCableBlock extends BaseBlock.Normal {
	public ICCableBlock() {
		super(MCS.MODID, "compressed_cable", Material.CLOTH, SoundType.CLOTH, null, 0.25f);
		this.setBlockModelResourceLocation(MCS.MODID + "/block", this.name);
	}
	
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
		player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.005F);
        
        harvesters.set(player);
		if(te instanceof TileEntityCompressedCable) {
			spawnAsEntity(world, pos, ((TileEntityCompressedCable) te).getStack());
		}
        harvesters.set(null);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		TileEntity tileEntity = world.getTileEntity(pos);
		if(tileEntity instanceof TileEntityCompressedCable) {
			return ((TileEntityCompressedCable) tileEntity).getStack();
		}
		return ItemStack.EMPTY;
	}
	
	@Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
	public static final AxisAlignedBB AABB = new AxisAlignedBB(0.35, 0.35, 0.35, 0.65, 0.65, 0.65);
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCompressedCable();
	}
	
	@Override
	public ItemBlock getRegisterItemBlock() {
		return new ItemBlock(this) {
			@Override
			public String getItemStackDisplayName(ItemStack stack) {
				return "Cable block";
			}
		};
	}
}
