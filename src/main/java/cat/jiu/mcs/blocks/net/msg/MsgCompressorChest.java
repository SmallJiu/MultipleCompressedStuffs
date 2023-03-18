package cat.jiu.mcs.blocks.net.msg;

import cat.jiu.mcs.blocks.net.container.ContainerCompressedChest;

import io.netty.buffer.ByteBuf;

import net.minecraft.inventory.Container;
import net.minecraft.world.WorldServer;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorChest implements IMessage {
	private float currentScroll = 0;

	public MsgCompressorChest() {}
	public MsgCompressorChest(float currentScroll) {
		this.currentScroll = currentScroll;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.currentScroll = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.currentScroll);
	}

	public IMessage handler(MessageContext ctx) {
		WorldServer world = ctx.getServerHandler().player.getServerWorld();
		world.addScheduledTask(() -> {
			Container con = ctx.getServerHandler().player.openContainer;
			if(con != null && con instanceof ContainerCompressedChest) {
				((ContainerCompressedChest)con).scrollTo(this.currentScroll);
			}
		});
		return null;
	}
}
