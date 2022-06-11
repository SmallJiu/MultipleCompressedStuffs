package cat.jiu.mcs.blocks.tileentity;

import com.sci.torcherino.blocks.tiles.TileTorcherino;

import cat.jiu.mcs.util.MCSUtil;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityCompressedTorcherino extends TileTorcherino {
	protected int compressedSpeed = -1;
	
	public TileEntityCompressedTorcherino() {}
	public TileEntityCompressedTorcherino(int meta) {
		if(meta == 0) {
			this.compressedSpeed = 9;
		}else if(meta == 1) {
			this.compressedSpeed = 81;
		}else {
			this.compressedSpeed = (int) MCSUtil.item.getMetaValue(32, meta);
		}
	}
	
	public void update() {
		if(this.compressedSpeed == -1) {
			TileEntity te = this.world.getBlockState(this.pos).getBlock().createTileEntity(this.world, this.world.getBlockState(this.pos));
			if(te instanceof TileEntityCompressedTorcherino) {
				this.compressedSpeed = ((TileEntityCompressedTorcherino)te).compressedSpeed;
			}
		}
		super.update();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("CompressedSpeed", this.compressedSpeed);
		return tag;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		this.compressedSpeed = tag.getInteger("CompressedSpeed");
	}

	protected int speed(int base) {
		return base * this.compressedSpeed;
	}
}
