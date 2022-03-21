package cat.jiu.mcs.blocks.tileentity;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.blocks.compressed.CompressedCreativeEnergy;
import cat.jiu.mcs.blocks.compressed.CompressedCreativeRFSource;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCreativeEnergy extends TileEntity implements ITickable {
	private int meta = -1;
	public TileEntityCreativeEnergy() {}
	public TileEntityCreativeEnergy(int meta) {
		this.meta = meta + 1;
	}
	@Override
	public void update() {
		this.markDirty();
		if(this.meta == -1) {
			if(this.blockType instanceof CompressedCreativeRFSource || this.blockType instanceof CompressedCreativeEnergy) {
				this.meta = ((TileEntityCreativeEnergy)this.blockType.createTileEntity(this.world, this.world.getBlockState(pos))).meta;
			}
		}else {
			int energy = Integer.MAX_VALUE;
			for(int i = 0; i < this.meta; i++) {
				JiuUtils.energy.sendFEEnergyToAll(this, energy, false);
			}
		}
	}
}
