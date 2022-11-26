package cat.jiu.mcs.blocks.tileentity;

import java.util.HashSet;

import com.google.common.collect.Sets;

import cat.jiu.mcs.blocks.BlockCompressor;
import cat.jiu.mcs.blocks.BlockCompressorSlave;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.common.capabilities.Capability;

public class TileEntityCompressorSlave extends TileEntity {
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		blackList.clear();
		BlockPos pos = this.findOriginalPos(this.pos);
		if(pos != null) {
			return this.world.getTileEntity(pos).getCapability(capability, facing);
		}
		return null;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		blackList.clear();
		BlockPos pos = this.findOriginalPos(this.pos);
		if(pos != null) {
			return this.world.getTileEntity(pos).hasCapability(capability, facing);
		}
		return false;
	}
	
	private final HashSet<BlockPos> blackList = Sets.newHashSet();
	private BlockPos findOriginalPos(BlockPos pos) {
		blackList.add(pos);
		for(EnumFacing side : EnumFacing.values()) {
			BlockPos sidePos = pos.offset(side);
			if(blackList.contains(sidePos)) continue;
			Class<?> block = this.world.getBlockState(sidePos).getBlock().getClass();
			
			if(block == BlockCompressorSlave.class || block == BlockCompressor.class) {
				if(this.world.getTileEntity(sidePos) instanceof TileEntityCompressor) {
					return sidePos;
				}else {
					BlockPos posT =  this.findOriginalPos(sidePos);
					if(posT != null) return posT;
				}
			}
		}
		return null;
	}
}
