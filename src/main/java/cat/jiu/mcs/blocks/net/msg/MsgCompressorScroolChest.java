package cat.jiu.mcs.blocks.net.msg;

import cat.jiu.mcs.blocks.net.container.ContainerCompressedScroolChest;

import io.netty.buffer.ByteBuf;

import net.minecraft.inventory.Container;
import net.minecraft.world.WorldServer;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorScroolChest implements IMessage {
	private float currentScroll = 0;

	public MsgCompressorScroolChest() {}
	public MsgCompressorScroolChest(float currentScroll) {
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
			if(con != null && con instanceof ContainerCompressedScroolChest) {
				((ContainerCompressedScroolChest)con).scrollTo(this.currentScroll);
			}
		});
		return this;
	}
}
