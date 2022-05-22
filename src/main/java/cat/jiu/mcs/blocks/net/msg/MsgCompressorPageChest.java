package cat.jiu.mcs.blocks.net.msg;

import cat.jiu.mcs.blocks.net.container.ContainerCompressedPageChest;

import io.netty.buffer.ByteBuf;

import net.minecraft.inventory.Container;
import net.minecraft.world.WorldServer;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MsgCompressorPageChest implements IMessage {
	private int page = 0;

	public MsgCompressorPageChest() {}
	public MsgCompressorPageChest(int page) {
		this.page = page;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.page = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.page);
	}

	public IMessage handler(MessageContext ctx) {
		WorldServer world = ctx.getServerHandler().player.getServerWorld();
		world.addScheduledTask(() -> {
			Container con = ctx.getServerHandler().player.openContainer;
			if(con != null && con instanceof ContainerCompressedPageChest) {
				((ContainerCompressedPageChest)con).toPage(this.page);
			}
		});
		return this;
	}
}
