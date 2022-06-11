package cat.jiu.mcs.blocks.net.msg;

import java.math.BigInteger;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.blocks.net.container.ContainerCompressor;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorEnergy implements IMessage {
	private BigInteger energy;
	public MsgCompressorEnergy() {}
	public MsgCompressorEnergy(BigInteger energy) {
		this.energy = energy;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.energy = JiuUtils.big_integer.create(new String(buf.array()));
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBytes(this.energy.toString().getBytes());
	}

	public IMessage handler(MessageContext ctx) {
		Container con = Minecraft.getMinecraft().player.openContainer;
		if(con instanceof ContainerCompressor) {
			((ContainerCompressor) con).setBigEnergy(this.energy);
		}
		
		return this;
	}
}
