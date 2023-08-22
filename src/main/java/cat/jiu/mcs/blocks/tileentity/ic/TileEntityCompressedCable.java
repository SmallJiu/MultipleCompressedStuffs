package cat.jiu.mcs.blocks.tileentity.ic;

import cat.jiu.core.util.base.BaseTileEntity;
import cat.jiu.mcs.items.compressed.ic.cable.ICCable;
import cat.jiu.mcs.util.MCSUtil;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyConductor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.core.block.wiring.CableType;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileEntityCompressedCable extends BaseTileEntity.Normal implements IEnergyConductor {
	protected ItemStack stack;
    protected CableType cableType;
    protected int insulation;
	
	public TileEntityCompressedCable() {
        this.setNeedUpdate(true)
        	.setUpdataIntervalTick(5 * 20);
	}
	public TileEntityCompressedCable(ItemStack stack, CableType cableType, int insulation) {
		this();
		this.stack = stack;
        this.cableType = cableType;
        this.insulation = insulation;
	}
	
	@Override
	public void validate() {
		super.validate();
		if(!this.world.isRemote) {
			EnergyNet.instance.addTile(this);
		}
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		if(!this.world.isRemote) {
			EnergyNet.instance.removeTile(this);
		}
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	public ItemStack getStack() {
		return stack;
	}
	
	@Override
	public void writeNBT(NBTTagCompound nbt) {
		nbt.setTag("stuff", this.stack.serializeNBT());
		nbt.setString("type", this.cableType.getName());
		nbt.setInteger("insulation", this.insulation);
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt) {
		this.stack = new ItemStack(nbt.getCompoundTag("stuff"));
		this.cableType = CableType.get(nbt.getString("type"));
		this.insulation = nbt.getInteger("insulation");
	}
	
	@Override
	public NBTTagCompound getTileEntityUpdatePacket(NBTTagCompound nbt) {
		if(nbt == null) {
			nbt = new NBTTagCompound();
		}
		this.writeNBT(nbt);
		return nbt;
	}
	@Override
	public void onTileEntityUpdatePacket(NBTTagCompound nbt) {
		this.readNBT(nbt);
	}
	
	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter from, EnumFacing side) {
		return !isEmpty();
	}
	
	@Override
	public boolean emitsEnergyTo(IEnergyAcceptor to, EnumFacing side) {
		return !isEmpty();
	}
	
	private boolean isEmpty() {
		if(this.stack==null || this.stack.isEmpty() || this.cableType == null || this.isInvalid()) {
			this.world.removeTileEntity(this.pos);
			return true;
		}
		return false;
	}
	
	@Override
	public double getConductionLoss() {
		return ICCable.getCompressedLoss(this.cableType, this.stack);
	}
	
	@Override
	public double getConductorBreakdownEnergy() {
		return MCSUtil.item.getMetaValue(this.cableType.capacity, this.stack);
	}
	
	@Override
	public double getInsulationEnergyAbsorption() {
		if (this.cableType.maxInsulation == 0) {
            return 2.147483647E9;
        }
        return EnergyNet.instance.getPowerFromTier(this.insulation + (this.cableType == CableType.tin?0:1));
	}
	
	@Override
	public double getInsulationBreakdownEnergy() {
		return 9001.0;
	}
	
	@Override
	public void removeInsulation() {
		
	}
	
	@Override
	public void removeConductor() {
		
	}
}
