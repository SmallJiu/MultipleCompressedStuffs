package cat.jiu.mcs.api;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ITooltipString {
	@SideOnly(Side.CLIENT)
	String get(ItemStack stack, @Nullable World world, ITooltipFlag advanced);
}
