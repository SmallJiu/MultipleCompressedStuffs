package cat.jiu.mcs.blocks.net.msg;

import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import io.netty.buffer.ByteBuf;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorCount implements IMessage {
	private BlockPos pos;
	private int count;

	public MsgCompressorCount() {
	}

	public MsgCompressorCount(BlockPos pos, int energy) {
		this.pos = pos;
		this.count = energy;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		this.pos = pb.readBlockPos();
		this.count = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		pb.writeBlockPos(this.pos);
		buf.writeInt(this.count);
	}

	public IMessage handler(MessageContext ctx) {
		WorldServer world = ctx.getServerHandler().player.getServerWorld();
		world.addScheduledTask(() -> {
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof TileEntityCompressor) {
				((TileEntityCompressor) te).setShrinkCount(count);
			}
		});
		return this;
	}
}
