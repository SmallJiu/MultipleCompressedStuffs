package cat.jiu.mcs.blocks.net.msg;

import cat.jiu.mcs.blocks.net.container.ContainerCompressor;

import io.netty.buffer.ByteBuf;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorEnergy implements IMessage {
	private int energy;
	public MsgCompressorEnergy() {}
	public MsgCompressorEnergy(int energy) {
		this.energy = energy;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.energy = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.energy);
	}

	public IMessage handler(MessageContext ctx) {
		Container con = Minecraft.getMinecraft().player.openContainer;
		if(con instanceof ContainerCompressor) {
			((ContainerCompressor) con).setEnergy(this.energy);
		}
		return this;
	}
}
