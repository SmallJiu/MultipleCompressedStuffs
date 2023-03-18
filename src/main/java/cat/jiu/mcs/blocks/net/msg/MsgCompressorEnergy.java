package cat.jiu.mcs.blocks.net.msg;

import cat.jiu.mcs.blocks.net.container.ContainerCompressor;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorEnergy implements IMessage {
	private long energy;
	public MsgCompressorEnergy() {}
	public MsgCompressorEnergy(long energy) {
		this.energy = energy;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.energy = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(this.energy);
	}

	public IMessage handler(MessageContext ctx) {
		Container con = ctx.side.isClient() ? Minecraft.getMinecraft().player.openContainer : ctx.getServerHandler().player.openContainer;
		if(con instanceof ContainerCompressor) {
			((ContainerCompressor) con).setEnergy(this.energy);
		}
		return null;
	}
}
