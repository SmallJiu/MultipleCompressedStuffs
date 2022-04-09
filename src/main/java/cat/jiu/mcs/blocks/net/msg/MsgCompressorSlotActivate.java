package cat.jiu.mcs.blocks.net.msg;

import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import io.netty.buffer.ByteBuf;

import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorSlotActivate implements IMessage {
	private BlockPos pos;
	private int slotID;
	private boolean activate;

	public MsgCompressorSlotActivate() {
	}

	public MsgCompressorSlotActivate(BlockPos pos, int slotID, boolean activate) {
		this.pos = pos;
		this.slotID = slotID;
		this.activate = activate;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		this.pos = pb.readBlockPos();
		this.slotID = buf.readInt();
		this.activate = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		pb.writeBlockPos(this.pos);
		buf.writeInt(this.slotID);
		buf.writeBoolean(this.activate);
	}

	public IMessage handler(MessageContext ctx) {
		WorldServer world = ctx.getServerHandler().player.getServerWorld();
		world.addScheduledTask(() -> {
			TileEntity te = world.getTileEntity(this.pos);
			if(te instanceof TileEntityCompressor) {
				((TileEntityCompressor) te).setActivateSlot(this.slotID, this.activate);
			}
		});
		return this;
	}
}
