package cat.jiu.mcs.util.client.waila;

import java.util.List;

import cat.jiu.core.api.ITimer;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.timer.Timer;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;

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
public class ChangeBlockPlugin implements IWailaDataProvider {
	@Method(modid = "waila")
	public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		TileEntity tile = accessor.getTileEntity();
		if(tile != null && accessor.getNBTData() != null && tile instanceof TileEntityChangeBlock) {
			ITimer times = new Timer();
			times.readFrom(accessor.getNBTData());
			
			Long failback = 0L;
//			if(!Configs.Tooltip_Information.Static_Show_Change_Block_Remaining_Time) failback = null;
			Long day = times.getDay() != 0 ? times.getDay() : failback;
			Long hour = times.getHour() != 0 ? times.getHour() : failback;
			Long min = times.getMinute() != 0 ? times.getMinute() : failback;
			Long sec = times.getSecond();
			
			tooltip.add(JiuUtils.other.addJoins(": ", new Object[] {I18n.format("info.mcs.waila.less_time"), JiuUtils.other.addJoins(":", day, hour, min, sec)}));
		}
		return tooltip;
	}
	
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tile, NBTTagCompound tag, World world, BlockPos pos) {
		if(tile != null && tile instanceof TileEntityChangeBlock) {
			((TileEntityChangeBlock) tile).getTime().write(tag);
		}
		return tag;
	}
}
