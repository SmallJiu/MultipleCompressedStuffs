package cat.jiu.mcs.blocks.tileentity;

import cat.jiu.core.capability.CapabilityJiuEnergy;
import cat.jiu.core.capability.JiuEnergyStorage;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.blocks.compressed.CompressedCreativeEnergy;
import cat.jiu.mcs.blocks.compressed.CompressedCreativeRFSource;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;

public class TileEntityCreativeEnergy extends TileEntity implements ITickable {
	private int meta = -1;

	public TileEntityCreativeEnergy() {}
	public TileEntityCreativeEnergy(int meta) {
		this.meta = meta + 1;
	}

	@Override
	public void update() {
		this.markDirty();
		if(this.meta == -1) {
			if(this.blockType instanceof CompressedCreativeRFSource || this.blockType instanceof CompressedCreativeEnergy) {
				this.meta = ((TileEntityCreativeEnergy) this.blockType.createTileEntity(this.world, this.world.getBlockState(pos))).meta;
				return;
			}
		}
		int energy = Integer.MAX_VALUE;
		for(int i = 0; i < this.meta; i++) {
			JiuUtils.energy.sendFEEnergyToAll(this, energy, false);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("meta", this.meta);
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("meta")) this.meta = nbt.getInteger("meta");
	}
	
	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing side) {
		if(cap == CapabilityEnergy.ENERGY
		|| cap == CapabilityJiuEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(cap, side);
	}
	
	static final JiuEnergyStorage storage = new JiuEnergyStorage(0,0,0,0); 
	
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing side) {
		if(cap == CapabilityEnergy.ENERGY) return CapabilityEnergy.ENERGY.cast(storage.toFEStorage());
		if(cap == CapabilityJiuEnergy.ENERGY) return CapabilityJiuEnergy.ENERGY.cast(storage);
		return super.getCapability(cap, side);
	}
}
