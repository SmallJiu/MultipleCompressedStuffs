//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.blocks.tileentity;

import java.math.BigInteger;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import cat.jiu.core.energy.CapabilityJiuEnergy;
import cat.jiu.core.energy.JiuEnergyStorage;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
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

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCompressor extends TileEntity implements ITickable {
	/**
	 * Energy
	 */
	public final String ENERGY = "Energy";
	public final int maxEnergy = 5000000;
	public final JiuEnergyStorage storage = new JiuEnergyStorage(maxEnergy, 10000);
	public long energy = 0;
	public final ItemStackHandler energySlot = new ItemStackHandler();
	public final BigItemStackHandler compressedSlot = new BigItemStackHandler(17, this);
	
	int shrinkCount = 10;
	
	public int getShrinkCount() {
		return this.shrinkCount;
	}
	
	public void setShrinkCount(int shrinkCount) {
		if (shrinkCount >= 9 && shrinkCount <= 64) {
			this.shrinkCount = shrinkCount;
		}
	}
	
	@Override
	public void update() {
		this.markDirty();
		this.reloadEnergy();
		this.addEnergy();// 能量输入
		this.reloadEnergy();
		
		// 能量是否大于等于20，大于等�?20是为了避免同时有4个多重的物品同时合成导致没有能量
		if(this.energy >= 20) {
			if (this.compressedSlot.getStackInSlot(0) != null && !this.compressedSlot.getStackInSlot(0).isEmpty()) {
				this.onBlockCrafting();// 未压缩方块合成为�?重压缩方�?
				this.onItemCrafting();// 未压缩物品合成为�?重压缩物�?
			}
			this.craftCompressedItem();// 全部压缩物品合成为下�?重的压缩物品
			this.checkItemCount();// �?查slot物品，大于一定数禁止破坏，防止过多物品掉地上卡死
		}
		
		this.reloadEnergy();
	}
	
	private void checkItemCount() {
		if(this.checkCount()) {
			
			return;
		}
		
		return;
		
	}
	
	private boolean checkCount() {
		for (int i = 1; i < this.compressedSlot.getSlots(); i++) {
			ItemStack cStack = this.compressedSlot.getStackInSlot(i);
			if(cStack.getCount() >= 5000) {
				return true;
			}
		}
		return false;
	}
	
	private void craftCompressedItem() {
		for (int i = 1; i < this.compressedSlot.getSlots(); i++) {
			ItemStack cStack = this.compressedSlot.getStackInSlot(i);
			if(cStack.getCount() >= this.getShrinkCount()) {
				if(cStack.getMetadata() < 15) {
					cStack.shrink(9);
					JiuUtils.item.addItemToSlot(this.compressedSlot, new ItemStack(cStack.getItem(), 1, cStack.getMetadata() + 1));
					this.storage.extractEnergyWithInt(5, false);
					this.reloadEnergy();
				}
			}
		}
	}
	
	// 重载能量
	private void reloadEnergy() {
//		this.storage.setMaxEnergy(this.maxEnergy);
		this.energy = this.storage.getEnergyStored();
//		this.storage.setEnergy(this.energy);
	}

	private void addEnergy() {
		ItemStack stack = this.energySlot.getStackInSlot(0);
		if (!stack.isEmpty() && stack != null) {
			if (this.getEnergy(stack) > 0) {
				int i = this.storage.getEnergyStored() + this.getEnergy(stack);
				if (i < this.storage.getMaxEnergyStored() && !(i > this.maxEnergy)) {
					this.storage.receiveEnergy(this.getEnergy(stack), false);
					
					if(!MCS.instance.test_model) {
						stack.shrink(1);
					}
					this.reloadEnergy();
				}
			}else if (stack.hasCapability(CapabilityEnergy.ENERGY, (EnumFacing) null)) {
				long i = this.energy + this.getEnergy(stack);
				if(!(i > this.maxEnergy)) {
					if (i < this.storage.getMaxEnergyStored()) {
						IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, (EnumFacing) null);
						this.storage.receiveEnergy(energy.extractEnergy(this.storage.receiveEnergy(10001, true), false), false);
						this.reloadEnergy();
					}
				}
			}else if(JiuUtils.item.isBlock(stack)) {
				if(JiuUtils.item.getBlockFromItemStack(stack) == MCSBlocks.CREATIVE_ENERGY) {
					this.storage.receiveEnergy(Integer.MAX_VALUE, false);
				}else if(JiuUtils.item.getBlockFromItemStack(stack) == MCSBlocks.C_CREATIVE_ENERGY_B){
					this.storage.receiveEnergy(Integer.MAX_VALUE, false);
				}
			}
		}
	}
	
	// 方块的合�?
	private void onBlockCrafting() {
		for(BaseBlockSub c : MCSBlocks.SUB_BLOCKS) {
			ItemStack unBlock = this.compressedSlot.getStackInSlot(0);
			if (JiuUtils.item.equalsStack(unBlock, c.getUnCompressedItemStack())) {
				if (unBlock.getCount() >= this.getShrinkCount()) {
					if(!MCS.instance.test_model) {
						unBlock.shrink(9);
					}
					JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, 1));
					this.storage.extractEnergyWithInt(5, false);
					this.reloadEnergy();
				}
			}
		}
	}
	
	// 物品的合�?
	private void onItemCrafting() {
		for (BaseItemSub c : MCSItems.SUB_ITEMS) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if (JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if (unItem.getCount() >= this.getShrinkCount()) {
					JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, 1));
					if(!MCS.instance.test_model) {
						unItem.shrink(9);
					}
					this.storage.extractEnergyWithInt(5, false);
					this.reloadEnergy();
				}
			}
		}
		for (BaseItemFood c : MCSItems.FOODS) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if (JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if (unItem.getCount() >= this.getShrinkCount()) {
					JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, 1));
					if(!MCS.instance.test_model) {
						unItem.shrink(9);
					}
					this.storage.extractEnergyWithInt(5, false);
					this.reloadEnergy();
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
	
	public ItemStackHandler getCompressedSlotItems() {
		return this.compressedSlot;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setLong(this.ENERGY, this.energy);
		
		nbt.setTag("EnergySlot", this.energySlot.serializeNBT());
		nbt.setTag("CompressedSlot", this.compressedSlot.serializeNBT());
		
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.energy = nbt.getLong(this.ENERGY);
		this.storage.setEnergy(nbt.getLong(this.ENERGY));
		
		this.energySlot.deserializeNBT(nbt.getCompoundTag("EnergySlot"));
		this.compressedSlot.deserializeNBT(nbt.getCompoundTag("CompressedSlot"));
	}
	
	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing side) {
		if (cap == CapabilityEnergy.ENERGY) {
			return true;
		}
		if(cap == CapabilityJiuEnergy.ENERGY) {
			return true;
		}
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		
		return super.hasCapability(cap, side);
	}
	
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing side) {
		if (cap == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(this.storage);
		}
		if (cap == CapabilityJiuEnergy.ENERGY) {
			return CapabilityJiuEnergy.ENERGY.cast(this.storage);
		}
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if(side == EnumFacing.UP) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.energySlot);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.compressedSlot);
		}
		return super.getCapability(cap, side);
	}
	
	class BigItemStackHandler extends ItemStackHandler {
		private final Map<Integer, BigInteger> slots = Maps.newHashMapWithExpectedSize(this.stacks.size());
		private final TileEntity te;
		
		public BigItemStackHandler(int size, TileEntity te) {
			super(size);
			this.te = te;
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			
			return super.extractItem(slot, amount, simulate);
//			this.amount -= amount;
			/*
			if (amount == 0) {
				return ItemStack.EMPTY;
			}
			if (this.amount == 0L) {
				return ItemStack.EMPTY;
			}
			
			this.validateSlotIndex(slot);
			
			ItemStack existing = this.stacks.get(slot);
			
			if (existing.isEmpty()) {
				return ItemStack.EMPTY;
			}
			
			int toExtract = Math.min(amount, existing.getMaxStackSize());
			
			if (this.amount <= toExtract) {
				if (!simulate) {
					this.stacks.set(slot, ItemStack.EMPTY);
					this.onContentsChanged(slot);
				}
				int i =(int) this.amount;
				this.amount = 0L;
				return ItemHandlerHelper.copyStackWithSize(existing, i);
			} else {
				if (!simulate) {
					this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
					onContentsChanged(slot);
				}
				
				return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
			}
			*/
//			return ItemStack.EMPTY;
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if(!this.isItemValid(slot, stack)) {
				return stack;
			}
			
			this.addItem(slot, stack.getCount());
			
			return super.insertItem(slot, stack, simulate);
		}
		
		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			if (slot == 0) {
				return true;
			}
			if(JiuUtils.item.equalsStack(compressedSlot.getStackInSlot(slot), stack)) {
				return true;
			}
			
			ItemStack unStack = compressedSlot.getStackInSlot(0);
			
			if (unStack != null && !unStack.isEmpty()) {
				if(JiuUtils.item.isBlock(stack)) {
					if(JiuUtils.item.getBlockFromItemStack(stack) instanceof BaseBlockSub) {
						if(JiuUtils.item.equalsStack(((BaseBlockSub) JiuUtils.item.getBlockFromItemStack(stack)).getUnCompressedItemStack(), unStack)) {
							return true;
						}
					}
				}
				if(stack.getItem() instanceof BaseItemSub) {
					if(JiuUtils.item.equalsStack(((BaseItemSub)stack.getItem()).getUnCompressedStack(), unStack)) {
						return true;
					}
				}
				if(stack.getItem() instanceof BaseItemSub) {
					if(JiuUtils.item.equalsStack(((BaseItemSub)stack.getItem()).getUnCompressedStack(), unStack)) {
						return true;
					}
				}
			}
			return false;
		}
		
		protected int getStackLimit(int slot, ItemStack stack) {
			return Integer.MAX_VALUE;
		}
		
		@Override
		protected void onContentsChanged(int slot) {
			this.te.markDirty();
		}
		
		@Override
		public void setStackInSlot(int slot, ItemStack stack) {
			this.slots.replace(slot, BigInteger.valueOf(stack.getCount()));
			super.setStackInSlot(slot, stack);
		}
		
		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagCompound nbt = super.serializeNBT();
			for(Entry<Integer, BigInteger> slotList : this.slots.entrySet()) {
				nbt.setString("Slot_" + slotList.getKey(), slotList.getValue().toString());
			}
			
			return nbt;
		}
		
		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			super.deserializeNBT(nbt);
			for(int slot = 0; slot < this.slots.size(); slot++) {
				if(!nbt.getString("Slot_" + slot).isEmpty()) {
					this.addItem(slot, nbt.getString("Slot_" + slot));
				}
				
			}
		}
		
		public BigInteger getCount(int slot) {
			if(this.slots.containsKey(slot)) {
				return this.slots.get(slot);
			}
			return BigInteger.ZERO;
		}
		
		private void addItem(int slot, long amount) {
			this.addItem(slot, Long.toString(amount));
		}
		
		private void addItem(int slot, String amount) {
			if(this.slots.containsKey(slot)) {
				this.slots.replace(slot, new BigInteger(amount));
			}else {
				this.slots.put(slot, new BigInteger(amount));
			}
		}
	}
}
