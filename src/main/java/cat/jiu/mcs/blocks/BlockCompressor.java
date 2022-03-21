package cat.jiu.mcs.blocks;

import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.GuiHandler;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Loader;

public class BlockCompressor extends BaseBlock.Normal implements ITileEntityProvider {
	public BlockCompressor() {
		super(MCS.MODID, "compressor", Material.ANVIL, SoundType.METAL, CreativeTabs.TRANSPORTATION, 10F);
		this.setBlockModelResourceLocation(MCS.MODID + "/block", this.name);
		MCSResources.BLOCKS_NAME.add(this.name);
		MCSResources.BLOCKS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(GuiScreen.isShiftKeyDown()) {
			tooltip.add("NBTTags: ");
			tooltip.add(JiuUtils.nbt.getItemNBT(stack).toString());
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(playerIn.isSneaking()) {
			if(!world.isRemote) {
				if(MCS.instance.test_model) {
					TileEntity te0 = world.getTileEntity(pos);
					if(te0 instanceof TileEntityCompressor) {
						TileEntityCompressor te = (TileEntityCompressor) te0;
						NonNullList<ItemStack> inv = playerIn.inventoryContainer.getInventory();

						for(int i = 0; i < inv.size(); i++) {
							ItemStack stack = inv.get(i);
							Item item = stack.getItem();
							ItemStack baseItem = MCSUtil.item.getUnCompressed(item);

							if(!baseItem.isEmpty()) {
								if(JiuUtils.item.equalsStack(te.compressedSlot.getStackInSlot(0), stack)) {
									te.compressedSlot.getStackInSlot(0).grow(stack.getCount());
									stack.setCount(0);
								}else if(JiuUtils.item.equalsStack(te.compressedSlot.getStackInSlot(0), baseItem)) {
									if(JiuUtils.item.addItemToSlot(te.compressedSlot, stack, true)) {
										JiuUtils.item.addItemToSlot(te.compressedSlot, stack, false);
										stack.setCount(0);
									}
								}
							}else if(JiuUtils.item.equalsStack(stack, te.compressedSlot.getStackInSlot(0))) {
								te.compressedSlot.getStackInSlot(0).grow(stack.getCount());
								stack.setCount(0);
							}else if(te.compressedSlot.getStackInSlot(0).isEmpty()) {
								boolean lag = false;
								for(int j = 0; j < te.compressedSlot.getSlots(); j++) {
									ItemStack compressed = te.compressedSlot.getStackInSlot(j);
									if(!compressed.isEmpty()) {
										ItemStack compressedUn = MCSUtil.item.getUnCompressed(compressed.getItem());
										if(!compressedUn.isEmpty()) {
											if(JiuUtils.item.equalsStack(compressedUn, stack)) {
												if(!te.compressedSlot.getStackInSlot(0).isEmpty()) {
													te.compressedSlot.getStackInSlot(0).grow(stack.getCount());
												}else {
													te.compressedSlot.setStackInSlot(0, stack);
												}
												stack.setCount(0);
												lag = true;
												break;
											}else if(JiuUtils.item.equalsStack(stack, compressed)) {
												if(JiuUtils.item.addItemToSlot(te.compressedSlot, stack, true)) {
													JiuUtils.item.addItemToSlot(te.compressedSlot, stack, false);
													stack.setCount(0);
													lag = true;
													break;
												}
											}
										}else if(JiuUtils.item.equalsStack(stack, te.compressedSlot.getStackInSlot(0))) {
											te.compressedSlot.getStackInSlot(0).grow(stack.getCount());
											stack.setCount(0);
											lag = true;
											break;
										}
									}
								}
								if(!lag) {
									if(!stack.isEmpty()) {
										if(te.compressedSlot.getStackInSlot(0).isEmpty()) {
											te.compressedSlot.setStackInSlot(0, stack);
											stack.setCount(0);
										}else {
											te.compressedSlot.getStackInSlot(0).grow(stack.getCount());
											stack.setCount(0);
										}
									}
								}
							}
						}
					}
				}else {
					return true;
				}

			}
		}else {
			int x = pos.getX(), y = pos.getY(), z = pos.getZ();
			playerIn.openGui(MCS.MODID, GuiHandler.COMPRESSOR, world, x, y, z);
			return true;
		}
		return false;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity posTe = world.getTileEntity(pos);

		if(posTe instanceof TileEntityCompressor) {
			TileEntityCompressor te = (TileEntityCompressor) posTe;
			super.breakBlock(world, pos, state);
			// ItemStack dropBlock = JiuUtils.item.getStackFormBlockState(state);
			// NBTTagCompound nbt = new NBTTagCompound();
			// NBTTagCompound postNBT = te.writeToNBT(new NBTTagCompound());
			//
			// postNBT.removeTag("x");
			// postNBT.removeTag("y");
			// postNBT.removeTag("z");
			// postNBT.removeTag("id");
			// nbt.setTag("BlockEntityTag", postNBT);
			// dropBlock.setTagCompound(nbt);
			// JiuUtils.item.spawnAsEntity(world, pos, dropBlock);
			// world.setBlockState(pos, Blocks.AIR.getDefaultState());

			JiuUtils.item.spawnAsEntity(world, pos, te.getEnergySlotItems());
			JiuUtils.item.spawnAsEntity(world, pos, te.getCompressedSlotItems());

			if(!Loader.isModLoaded("redstoneflux")) {
				long i = te.storage.getEnergyStoredWithLong();

				if(i >= 9000) {
					while(i >= 9000) {
						i -= 9000;
						JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(Blocks.REDSTONE_BLOCK));
					}
				}

				if(i >= 1000) {
					while(i >= 1000) {
						i -= 1000;
						JiuUtils.item.spawnAsEntity(world, pos, new ItemStack(Items.REDSTONE));
					}
				}
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCompressor();
	}

	@Override
	public ItemBlock getRegisterItemBlock() {
		return (ItemBlock) new ItemBlock(this).setRegistryName(this.getRegistryName());
	}
}
