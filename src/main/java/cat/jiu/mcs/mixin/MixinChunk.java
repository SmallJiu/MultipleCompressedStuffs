package cat.jiu.mcs.mixin;

import java.lang.reflect.Field;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSItems;

import ic2.core.block.invslot.InvSlotConsumableItemStack;
import ic2.core.block.machine.tileentity.TileEntityLiquidHeatExchanger;
import ic2.core.util.ItemComparableItemStack;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import net.minecraftforge.fml.common.Loader;

@Mixin(Chunk.class)
public class MixinChunk {
	private MixinChunk() {
		throw new RuntimeException();
	}
	
	@Inject(
			cancellable = true,
			at = {@At("RETURN")},
			method = {"createNewTileEntity(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/tileentity/TileEntity;"})
	private void mixin_createNewTileEntity(BlockPos pos, CallbackInfoReturnable<TileEntity> cir) {
		TileEntity tile = cir.getReturnValue();
		if(Loader.isModLoaded("ic2")) {
			addHeatExchanger(tile);
		}
		cir.cancel();
	}
	
	@Redirect(
			at = @At(
				value = "INVOKE",
				target = "Lnet/minecraft/block/Block;createTileEntity(Lnet/minecraft/world/World;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/tileentity/TileEntity;"
			),
			method = {"setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/block/state/IBlockState;"}
	)
	private TileEntity mixin_setBlockState(Block block, World world, IBlockState state) {
		TileEntity tile = block.createTileEntity(world, state);
		if(Loader.isModLoaded("ic2")) {
			addHeatExchanger(tile);
		}
		return tile;
	}
	
	@SuppressWarnings("unchecked")
	private void addHeatExchanger(TileEntity tile) {
		if(tile instanceof TileEntityLiquidHeatExchanger) {
			try {
				Field stacksField = InvSlotConsumableItemStack.class.getDeclaredField("stacks");
				stacksField.setAccessible(true);
				Set<ItemComparableItemStack> stacks = (Set<ItemComparableItemStack>) stacksField.get(((TileEntityLiquidHeatExchanger) tile).heatexchangerslots);
				for(int i = 0; i < MCSItems.ic2.normal.C_heat_conductor_I.getLevel().maxMeta; i++) {
					stacks.add(new ItemComparableItemStack(MCSItems.ic2.normal.C_heat_conductor_I.getStack(i), true));
				}
				stacks.add(new ItemComparableItemStack(MCSItems.ic2.normal.C_heat_conductor_I.getStack(ModSubtypes.INFINITY), true));
				System.out.println(stacks);
				new Exception().printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
