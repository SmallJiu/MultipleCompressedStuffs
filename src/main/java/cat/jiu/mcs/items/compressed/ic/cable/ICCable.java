package cat.jiu.mcs.items.compressed.ic.cable;

import java.text.DecimalFormat;
import java.util.List;

import cat.jiu.mcs.blocks.tileentity.ic.TileEntityCompressedCable;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.base.sub.BaseCompressedItem;

import ic2.api.item.IBoxable;
import ic2.core.block.wiring.CableType;
import ic2.core.item.block.ItemCable;
import ic2.core.util.StackUtil;

import net.minecraft.block.SoundType;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ICCable extends BaseCompressedItem implements IBoxable {
	public static final DecimalFormat lossFormat = new DecimalFormat("0.00#");
	public static final ICCableBlock CABLE_BLOCK = new ICCableBlock();
	
	protected final ItemCable base;
	public ICCable(String name, ItemStack baseItem) {
		super(name, baseItem);
		if(baseItem.getItem() instanceof ItemCable) {
			this.base = (ItemCable) baseItem.getItem();
		}else {
			throw new RuntimeException(String.format("%s not a IC cable!", baseItem));
		}
		super.setCanShowBaseStackInfo(false);
	}
	
	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
		CableType type = getCableType(super.getUnCompressedStack());
		
        tooltip.add(((int) MCSUtil.item.getMetaValue(type.capacity, stack)) + " " + I18n.format("ic2.generic.text.EUt"));
        tooltip.add(I18n.format("ic2.cable.tooltip.loss", lossFormat.format(getCompressedLoss(type, stack))));
		tooltip.add(TextFormatting.RED + I18n.format("info.mcs.unfinished"));
        
        super.addInformation(stack, world, tooltip, advanced);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItemMainhand();
        if (StackUtil.isEmpty(stack)
        || !player.canPlayerEdit(pos, side, stack)
        || world.getBlockState(pos).getBlock().isReplaceable((IBlockAccess)world, pos)) {
            return EnumActionResult.PASS;
        }
        pos = pos.offset(side);
        if (world.setBlockState(pos, CABLE_BLOCK.getDefaultState())) {
        	ItemStack stack2 = stack.copy(); stack2.setCount(1);
        	world.setTileEntity(pos, new TileEntityCompressedCable(stack2, getCableType(this.getUnCompressedStack()), getInsulation(this.getUnCompressedStack())));
        	SoundType soundtype = CABLE_BLOCK.getSoundType(world.getBlockState(pos), world, pos, (Entity)player);
            world.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
            StackUtil.consumeOrError(player, hand, 1);
        }
        return EnumActionResult.SUCCESS;
	}
	
	public static CableType getCableType(final ItemStack stack) {
        final NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        final int type = nbt.getByte("type") & 0xFF;
        if (type < CableType.values.length) {
            return CableType.values[type];
        }
        return CableType.copper;
    }
    
	public static int getInsulation(final ItemStack stack) {
        final CableType type = getCableType(stack);
        final NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        final int insulation = nbt.getByte("insulation") & 0xFF;
        return Math.min(insulation, type.maxInsulation);
    }
	
	public static double getCompressedLoss(CableType type, ItemStack stack) {
        double loss = type.loss - MCSUtil.item.getMetaValue(type.loss, stack, 0.06);
        return Math.max(0, loss);
	}

	@Override
	public boolean canBeStoredInToolbox(ItemStack p0) {
		return true;
	}
	@Override
	public boolean canMakeDefaultStackRecipe() {
		return false;
	}
}
