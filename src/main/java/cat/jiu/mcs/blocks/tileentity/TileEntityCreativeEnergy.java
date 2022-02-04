//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.blocks.tileentity;

import cat.jiu.core.util.JiuUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityCreativeEnergy extends TileEntity implements ITickable {
	private int meta = 1;
	public TileEntityCreativeEnergy(int meta) {
		this.meta = meta + 1;
	}
	@Override
	public void update() {
		this.markDirty();
		int energy = Integer.MAX_VALUE;
		for (int i = 0; i < this.meta; i++) {
			JiuUtils.energy.sendFEEnergyToAll(this, energy, false);
		}
	}
}
