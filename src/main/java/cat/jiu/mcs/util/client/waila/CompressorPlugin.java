package cat.jiu.mcs.util.client.waila;

import java.util.List;

import cat.jiu.core.capability.JiuEnergyStorage;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Optional.*;

@InterfaceList({
	@Interface(iface = "mcp.mobius.waila.api.IWailaDataProvider", modid = "waila", striprefs = true),
	@Interface(iface = "mcp.mobius.waila.api.IWailaDataAccessor", modid = "waila", striprefs = true),
	@Interface(iface = "mcp.mobius.waila.api.IWailaConfigHandler", modid = "waila", striprefs = true)
})
public class CompressorPlugin implements IWailaDataProvider {
	@Method(modid = "waila")
	public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileEntity tile = accessor.getTileEntity();
		if(tile != null && accessor.getNBTData() != null && tile instanceof TileEntityCompressor) {
			JiuEnergyStorage energy = JiuEnergyStorage.empty();
			energy.readFromNBT(accessor.getNBTData().getCompoundTag("energy"), true);
			
			if(accessor.getNBTData().hasKey("debug")) {
				tooltip.add("Debug: on");
			}
			
			StringBuilder s = new StringBuilder();
			s.append(I18n.format("info.mcs.energy"));
			s.append(": ");
			s.append(JiuUtils.big_integer.format(energy.getEnergyStoredWithBigInteger(), 3));
			s.append(" JE / ");
			s.append(JiuUtils.big_integer.format(energy.getMaxEnergyStoredWithBigInteger(), 3));
			s.append(" JE");
			
			tooltip.add(s.toString());
		}
		return tooltip;
	}
	
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, BlockPos pos) {
		if(tile != null && tile instanceof TileEntityCompressor) {
			if(((TileEntityCompressor) tile).debug) tag.setBoolean("debug", true);
			tag.setTag("energy", ((TileEntityCompressor) tile).storage.writeToNBT(null, true));
		}
		return tag;
	}
}
