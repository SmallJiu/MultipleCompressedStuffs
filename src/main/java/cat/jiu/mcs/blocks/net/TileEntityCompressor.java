package cat.jiu.mcs.blocks.net;

import cat.jiu.core.energy.JiuEnergyStorage;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.BigItemStack;
import cat.jiu.mcs.util.base.BaseBlockSub;
import cat.jiu.mcs.util.base.BaseItemFood;
import cat.jiu.mcs.util.base.BaseItemSub;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCompressor extends TileEntity implements ITickable {
	public final String ENERGY = "Energy";
	public final int maxEnergy = 5000000;
	public final JiuEnergyStorage storage = new JiuEnergyStorage(this.maxEnergy, 10000);
	public final NonNullList<BigItemStack> compressedSlots = NonNullList.withSize(17, BigItemStack.EMPTY);
	public int energy = 0;
	public final ItemStackHandler energySlot = new ItemStackHandler() {
		@Override
		protected void onContentsChanged(int slot) {
			TileEntityCompressor.this.markDirty();
		}
	};
	
	public final ItemStackHandler unCompressedSlot = new ItemStackHandler() {
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return super.insertItem(slot, stack, simulate);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return super.extractItem(slot, amount, simulate);
		}
		
		protected int getStackLimit(int slot, ItemStack stack) {
			return Integer.MAX_VALUE;
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			TileEntityCompressor.this.markDirty();
		}
	};
	
	public final ItemStackHandler compressedSlot = new ItemStackHandler(16) {
		@Override
		protected void onContentsChanged(int slot) {
			TileEntityCompressor.this.markDirty();
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return super.insertItem(slot, stack, simulate);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return super.extractItem(slot, amount, simulate);
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			ItemStack unStack = unCompressedSlot.getStackInSlot(0);
			
			if (unStack != null && !unStack.isEmpty()) {
				for (BaseBlockSub c : MCSBlocks.SUB_BLOCKS) {
					if (JiuUtils.item.getBlockFromItemStack(stack) instanceof BaseBlockSub) {
						BaseBlockSub b = (BaseBlockSub) JiuUtils.item.getBlockFromItemStack(stack);
						
						for (int i = 0; i < 16; i++) {
							if (JiuUtils.item.equalsStack(new ItemStack(b, 1, i), stack, false)) {
								return true;
							}
						}
					}
					return JiuUtils.item.equalsStack(unStack, c.getUnCompressedItemStack(), false);
				}
				
				for (BaseItemSub c : MCSItems.SUB_ITEMS) {
					if (stack.getItem() instanceof BaseItemSub) {
						BaseItemSub b = (BaseItemSub) stack.getItem();
						
						for (int i = 0; i < 16; i++) {
							if (JiuUtils.item.equalsStack(new ItemStack(b, 1, i), stack, false)) {
								return true;
							}
						}
					}
					return JiuUtils.item.equalsStack(unStack, c.getUnCompressedStack(), false);
				}
			}
			return false;
		}
	};
	
	@Override
	public void update() {
		this.markDirty();
		this.reloadEnergy();
		{
			ItemStack stack = energySlot.getStackInSlot(0);
			
			if (!stack.isEmpty() && stack != null) {
				if (this.getEnergy(stack) > 0) {
					int i = this.energy;
					if ((i += this.getEnergy(stack)) <= this.storage.getMaxEnergyStored()) {
						this.storage.addEnergy(this.getEnergy(stack));
						this.energy = this.storage.getEnergyStored();
						stack.shrink(1);
					}
				}else if(stack.hasCapability(CapabilityEnergy.ENERGY, (EnumFacing)null)) {
					IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, (EnumFacing)null);
					this.storage.addEnergy(energy.extractEnergy(1000, false));
				}
			}
		}
		
		this.reloadEnergy();
		
		if (this.unCompressedSlot.getStackInSlot(0) != null && !this.unCompressedSlot.getStackInSlot(0).isEmpty() && this.energy >= 5) {
			this.onBlockCrafting();
			this.reloadEnergy();
			this.onItemCrafting();
			
			for (int i = 0; i < this.compressedSlot.getSlots(); i++) {
				ItemStack cStack = this.compressedSlot.getStackInSlot(i);
				if (cStack.getCount() >= 9) {
					if (cStack.getMetadata() != 15) {
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(cStack.getItem(), 1, cStack.getMetadata() + 1));
						cStack.shrink(9);
						this.storage.outputEnergy(5, false);
					}
				}
			}
		}
		
		this.reloadEnergy();
	}
	
	// 重载能量
	private void reloadEnergy() {
		this.storage.setMaxEnergyStored(this.maxEnergy);
		this.energy = this.storage.getEnergyStored();
		this.storage.setEnergyStored(this.energy);
	}
	
	// 方块的合成
	private void onBlockCrafting() {
		for(BaseBlockSub c : MCSBlocks.SUB_BLOCKS) {
			ItemStack unBlock = this.unCompressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unBlock, c.getUnCompressedItemStack())) {
				if(unBlock.getCount() >= 9) {
					JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, 10));
					 unBlock.shrink(9);
					 this.storage.outputEnergy(5, false);
				}
			}
		}
	}
	
	// 物品的合成
	private void onItemCrafting() {
		for(BaseItemSub c : MCSItems.SUB_ITEMS) {
			ItemStack unItem = this.unCompressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= 9) {
					JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, 10));
					unItem.shrink(9);
					this.storage.outputEnergy(5, false);
				}
			}
		}
		for(BaseItemFood c : MCSItems.FOODS) {
			ItemStack unItem = this.unCompressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= 9) {
					JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, 10));
					unItem.shrink(9);
					this.storage.outputEnergy(5, false);
				}
			}
		}
	}
	
	// BlockChest
	private int getEnergy(ItemStack stack) {
		Item item = stack.getItem();

		if (item == Items.REDSTONE) {
			return 1000;
		} else if (item == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK)) {
			return 9000;
		} else {
			return 0;
		}
	}
	
	public ItemStackHandler getEnergySlotItems() {
		return this.energySlot;
	}
	
	public ItemStackHandler getUnCompressedSlotSlotItems() {
		return this.unCompressedSlot;
	}
	
	public ItemStackHandler getCompressedSlotItems() {
		return this.compressedSlot;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger(this.ENERGY, this.energy);

		nbt.setTag("EnergySlot", this.energySlot.serializeNBT());
		nbt.setTag("UnCompressedSlot", this.unCompressedSlot.serializeNBT());
		nbt.setTag("CompressedSlot", this.compressedSlot.serializeNBT());

		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.energy = nbt.getInteger(this.ENERGY);
		this.storage.setEnergyStored(this.energy);

		this.energySlot.deserializeNBT(nbt.getCompoundTag("EnergySlot"));
		this.unCompressedSlot.deserializeNBT(nbt.getCompoundTag("UnCompressedSlot"));
		this.compressedSlot.deserializeNBT(nbt.getCompoundTag("CompressedSlot"));
	}
	/*
	public void addItemToSlot(IItemHandlerModifiable slots, ItemStack stack) {
		for (int slot = 0; slot < slots.getSlots(); ++slot) {
			ItemStack putStack = slots.getStackInSlot(slot);
			if (JiuUtils.item.equalsStack(putStack, stack)) {
				putStack.grow(stack.getCount());
				slots.setStackInSlot(slot, putStack);
				break;
			}else if(putStack == null || putStack.isEmpty()){
				slots.setStackInSlot(slot, stack);
				break;
			}
		}
	}
	*/
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityEnergy.ENERGY) {
			return (T) this.storage;
		}
		return super.getCapability(capability, facing);
	}
}
