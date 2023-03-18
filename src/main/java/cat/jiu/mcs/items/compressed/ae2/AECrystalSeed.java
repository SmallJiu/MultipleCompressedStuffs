package cat.jiu.mcs.items.compressed.ae2;

import java.util.List;

import appeng.api.AEApi;
import appeng.api.definitions.IItemDefinition;
import appeng.core.localization.ButtonToolTips;
import appeng.tile.misc.TileQuartzGrowthAccelerator;

import cat.jiu.core.events.item.ItemInWorldEvent;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AECrystalSeed extends BaseCompressedItem {
	protected final ICompressedStuff result;

	public AECrystalSeed(String name, IItemDefinition baseItem, int meta, ICompressedStuff result) {
		this(name, JiuUtils.item.copyStack(baseItem.maybeStack(1).get(), meta), result);
	}

	public AECrystalSeed(String name, ItemStack baseItem, ICompressedStuff result) {
		super(name, baseItem);
		this.result = result;
		super.setInfoStack(ItemStack.EMPTY);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean createOreDictionary() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(AEApi.instance().definitions().blocks().quartzGlass().maybeStack(1).get().getDisplayName());
		tooltip.add(ButtonToolTips.DoesntDespawn.getLocal());
		int main = (int) (((float) (JiuUtils.nbt.getItemNBTInt(stack, "progress") * 100) / 600.0));
		String sub = Double.toString(((float) (JiuUtils.nbt.getItemNBTInt(stack, "progress") * 100) % 600.0)).substring(0, 1);
		tooltip.add(main + "." + sub + "%");

		super.addInformation(stack, world, tooltip, advanced);
	}

	@SubscribeEvent
	public void onItemInFluidTick(ItemInWorldEvent.InFluid event) {
		if(event.item.getItem().getItem() instanceof AECrystalSeed) {
			World world = event.item.world;
			BlockPos pos = event.item.getPosition();
			ItemStack stack = event.item.getItem();
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
				event.item.setItem(seed.result.getStack(stack.getCount(), stack.getMetadata()));
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityItem && ((EntityItem) event.getEntity()).getItem().getItem() instanceof AECrystalSeed) {
			((EntityItem) event.getEntity()).setNoDespawn();
		}
	}
}
