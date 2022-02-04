//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.blocks.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CompressorMessage implements IMessage {
	private int chunkX;
	private int chunkZ;
	private long energy;
	
	public CompressorMessage() {}
	public CompressorMessage(int chunkX, int chunkZ, long energy) {
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.energy = energy;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.chunkX = buf.readInt();
		this.chunkZ = buf.readInt();
		this.energy = buf.readLong();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.chunkX);
		buf.writeInt(this.chunkZ);
		buf.writeLong(this.energy);
		
	}
	
	@SuppressWarnings("unused")
	@SideOnly(Side.CLIENT)
	public IMessage handler(MessageContext ctx) {
		Minecraft mc = Minecraft.getMinecraft();
        mc.addScheduledTask(() -> {
            Chunk chunk = mc.world.getChunkFromChunkCoords(this.chunkX, this.chunkZ);
        });
        return null;
	}
}
