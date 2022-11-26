package cat.jiu.mcs.api;

import cat.jiu.core.api.handler.IBuilder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITest extends IBuilder<ItemStack> {
	@Override
	default ItemStack builder(Object... args) {
		return this.builder((World)args[0], (EntityPlayer)args[1], (IBlockState)args[2], (BlockPos)args[3]);
	}
	
	ItemStack builder(World world, EntityPlayer player, IBlockState state, BlockPos pos);
}
