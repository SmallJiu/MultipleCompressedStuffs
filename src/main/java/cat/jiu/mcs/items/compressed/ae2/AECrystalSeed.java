package cat.jiu.mcs.items.compressed.ae2;

import java.util.List;

import appeng.api.AEApi;
import appeng.api.definitions.IItemDefinition;
import appeng.core.localization.ButtonToolTips;
import appeng.tile.misc.TileQuartzGrowthAccelerator;

import cat.jiu.core.api.events.entity.IEntityJoinWorldEvent;
import cat.jiu.core.api.events.item.IItemInFluidTickEvent;
import cat.jiu.core.util.JiuCoreEvents;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.util.base.sub.BaseItemSub;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AECrystalSeed extends BaseItemSub implements IItemInFluidTickEvent, IEntityJoinWorldEvent {
	protected final ICompressedStuff result;

	public AECrystalSeed(String name, IItemDefinition baseItem, int meta, ICompressedStuff result) {
		this(name, JiuUtils.item.copyStack(baseItem.maybeStack(1).get(), meta), result);
	}

	public AECrystalSeed(String name, ItemStack baseItem, ICompressedStuff result) {
		super(name, baseItem);
		this.result = result;
		super.setInfoStack(ItemStack.EMPTY);
		JiuCoreEvents.addEvent(this);
	}

	@Override
	public boolean createOreDictionary() {
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(AEApi.instance().definitions().blocks().quartzGlass().maybeStack(1).get().getDisplayName());
		tooltip.add(ButtonToolTips.DoesntDespawn.getLocal());
		int main = (int) (((float) (JiuUtils.nbt.getItemNBTInt(stack, "progress") * 100) / 600.0));
		String sub = Double.toString(((float) (JiuUtils.nbt.getItemNBTInt(stack, "progress") * 100) % 600.0)).substring(0, 1);
		tooltip.add(main + "." + sub + "%");

		super.addInformation(stack, world, tooltip, advanced);
	}

	@Override
	public void onItemInFluidTick(EntityItem item, World world, BlockPos pos, IBlockState state) {
		if(item.getItem().getItem() instanceof AECrystalSeed) {
			ItemStack stack = item.getItem();
			AECrystalSeed seed = (AECrystalSeed) stack.getItem();

			JiuUtils.nbt.addItemNBT(stack, "tick", 1);
			if(JiuUtils.nbt.getItemNBTInt(stack, "tick") >= 20) {
				int growthCount = 0;
				for(EnumFacing side : EnumFacing.values()) {
					TileEntity te = world.getTileEntity(pos.offset(side));
					if(te != null && te instanceof TileQuartzGrowthAccelerator && ((TileQuartzGrowthAccelerator) te).isActive()) {
						growthCount += 1;
					}
				}
				JiuUtils.nbt.addItemNBT(stack, "progress", 1 + growthCount + stack.getMetadata() + 1);
				JiuUtils.nbt.setItemNBT(stack, "tick", 0);
			}
			if(JiuUtils.nbt.getItemNBTInt(stack, "progress") >= 600) {
				JiuUtils.nbt.removeItemNBT(stack, "progress");
				JiuUtils.nbt.removeItemNBT(stack, "tick");
				world.playEvent(2005, pos, 50);
				world.playSound(null, pos, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.WEATHER, 1, 1);
				item.setItem(seed.result.getStack(stack.getCount(), stack.getMetadata()));
			}
		}
	}

	@Override
	public void onEntityJoinWorld(Entity entity, World world, BlockPos pos, int dim) {
		if(entity instanceof EntityItem && ((EntityItem) entity).getItem().getItem() instanceof AECrystalSeed) {
			((EntityItem) entity).setNoDespawn();
		}
	}
}
