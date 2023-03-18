package cat.jiu.mcs.util.client.waila;

import java.util.List;

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
			NBTTagCompound nbt = accessor.getNBTData();
			if(nbt.getBoolean("debug")) {
				tooltip.add("Debug: on");
			}
			
			StringBuilder s = new StringBuilder();
			s.append(I18n.format("info.mcs.energy"))
			 .append(": ")
			 .append(JiuUtils.big_integer.format(JiuUtils.big_integer.create(nbt.getString("energy")), 3))
			 .append(" JE / ")
			 .append(JiuUtils.big_integer.format(JiuUtils.big_integer.create(nbt.getString("maxEnergy")), 1))
			 .append(" JE");
			
			tooltip.add(s.toString());
		}
		return tooltip;
	}
	
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, BlockPos pos) {
		if(tile != null && tile instanceof TileEntityCompressor) {
			tag.setBoolean("debug", ((TileEntityCompressor) tile).debug);
			tag.setString("energy", ((TileEntityCompressor) tile).storage.getEnergyStoredWithBigInteger().toString());
			tag.setString("maxEnergy", ((TileEntityCompressor) tile).storage.getMaxEnergyStoredWithBigInteger().toString());
		}
		return tag;
	}
}
