package cat.jiu.mcs.blocks.net.msg;

import cat.jiu.core.util.base.BaseMessage;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorClientEnergy extends BaseMessage {
	private BlockPos pos;
	public MsgCompressorClientEnergy() {}
	public MsgCompressorClientEnergy(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	protected NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(nbt==null) nbt = new NBTTagCompound();
		nbt.setInteger("x", this.pos.getX());
		nbt.setInteger("y", this.pos.getY());
		nbt.setInteger("z", this.pos.getZ());
		return nbt;
	}

	@Override
	protected void readFromNBT(NBTTagCompound nbt) {
		this.pos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
	}
	
	public IMessage handler(MessageContext ctx) {
		if(ctx.side.isServer()) {
			TileEntity te = ctx.getServerHandler().player.world.getTileEntity(this.pos);
			if(te instanceof TileEntityCompressor) {
				return new MsgCompressorEnergy(((TileEntityCompressor) te).storage.getEnergyStoredWithLong());
			}
		}
		return null;
	}
}
