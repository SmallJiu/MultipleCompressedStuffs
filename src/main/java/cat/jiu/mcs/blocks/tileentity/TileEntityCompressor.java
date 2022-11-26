package cat.jiu.mcs.blocks.tileentity;

import java.math.BigInteger;
import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.core.api.IJiuEnergyStorage;
import cat.jiu.core.capability.CapabilityJiuEnergy;
import cat.jiu.core.capability.JiuEnergyStorage;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSResources;

import ic2.api.item.IElectricItem;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCompressor extends TileEntity implements ITickable {
	/** Energy */ public final String ENERGY = "Energy";
	public final long maxEnergy = 5000000;
	// public final long maxEnergy = Long.MAX_VALUE;
	public final JiuEnergyStorage storage = new JiuEnergyStorage(maxEnergy, 1000000, 1000000);
	public final ItemStackHandler energySlot = new ItemStackHandler();
	public final BigItemStackHandler compressedSlot = new BigItemStackHandler(17, this);
	private final List<Boolean> activate = Lists.newArrayList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);

	public void setActivateSlot(int slotID, boolean activate) {
		if(slotID > -1 && slotID < 16) {
			this.activate.set(slotID, activate);
		}
	}

	public boolean slotCanCraft(int slotID) {
		if(slotID > -1 && slotID < 16) {
			return this.activate.get(slotID);
		}
		return false;
	}

	public List<Boolean> getActivateList() {
		return this.activate;
	}

	int shrinkCount = 10;

	public int getShrinkCount() {
		return this.shrinkCount;
	}

	public void setShrinkCount(int shrinkCount) {
		if(shrinkCount >= 9 && shrinkCount <= Integer.MAX_VALUE) {
			this.shrinkCount = shrinkCount;
		}
	}
	
	private boolean canBreak = true;
	public boolean canBreak() {
		return this.canBreak;
	}

	@Override
	public void update() {
		if(!this.world.isRemote) {
			this.markDirty();
			if(this.compressedSlot.getSlots() == 0) {
				this.compressedSlot.setSize(17);
			}
			this.addEnergy();// 能量输入
			// 能量是否大于等于20，大于等于20是为了避免同时有4个多重的物品同时合成导致没有能量
			if(this.storage.getEnergyStoredWithLong() >= 20) {
				if(this.compressedSlot.getStackInSlot(0) != null && !this.compressedSlot.getStackInSlot(0).isEmpty()) {
					this.craftUnCompressedItem();// 未压缩方块合成为一重压缩方块
				}
				this.craftCompressedItem();// 全部压缩物品合成为下一重的压缩物品
				this.checkBreakState();
			}
		}
	}
	
	private void checkBreakState() {
		for(int i = 0; i < this.compressedSlot.getSlots(); i++) {
			ItemStack slotStack = this.compressedSlot.getStackInSlot(i);
			if(slotStack.getCount() >= 2304) {
				this.canBreak = false;
				return;
			}
		}
		this.canBreak = true;
	}
	
	// 方块的合成
	private void craftUnCompressedItem() {
		ItemStack unBlock = this.compressedSlot.getStackInSlot(0);
		ICompressedStuff block = MCSResources.getCompressedStuff(unBlock);
		int shirkC = Configs.use_3x3_recipes ? 9 : 4;
		if(block != null) {
			int amount = unBlock.getCount() / shirkC;
			if(!block.isStuff()) return;
			ItemStack stack = block.getStack(amount, 0);
			
			if(JiuUtils.item.addItemToSlot(compressedSlot, stack, true)) {
				if(this.debug || MCS.dev()) {this.storage.extractEnergyWithLong(1, false);}else {
					unBlock.shrink(amount * shirkC);
					this.storage.extractEnergyWithLong(5 * amount, false);
				}
				JiuUtils.item.addItemToSlot(compressedSlot, stack, false);
			}
		}
	}

	private void craftCompressedItem() {
		int shirkC = Configs.use_3x3_recipes ? 9 : 4;
		for(int i = 1; i < this.compressedSlot.getSlots(); i++) {
			ItemStack slotStack = this.compressedSlot.getStackInSlot(i);
			if(slotStack.getCount() >= this.getShrinkCount()) {
				if(slotStack.getMetadata() < 15 && i + 1 < this.compressedSlot.getSlots()) {
					if(this.slotCanCraft(i) && slotStack.getMetadata() + 1 == i) {
						int amount = slotStack.getCount() / shirkC;
						if(this.compressedSlot.getStackInSlot(i + 1).getCount() < Integer.MAX_VALUE-2) {
							ItemStack stack = new ItemStack(slotStack.getItem(), amount, slotStack.getMetadata() + 1);
							if(this.compressedSlot.insertItem(i + 1, stack, true).isEmpty()) {
								slotStack.shrink(amount * shirkC);
								if(this.debug || MCS.dev()) {this.storage.extractEnergyWithLong(1, false);}else {
									this.storage.extractEnergyWithLong(5 * amount, false);
								}
								this.compressedSlot.insertItem(i + 1, stack, false);
							}
						}
					}else {
						break;
					}
				}else {
					break;
				}
			}
		}
	}

	public boolean debug = false;
	public void setDebug() {
		this.debug = !this.debug;
	}
	
	protected int getEnergy(ItemStack stack) {
		int energy = TileEntityFurnace.getItemBurnTime(stack);
		if(energy <= 0) {
			Item item = stack.getItem();
			if(item == Items.REDSTONE) {
				energy = 1000;
			}
		}
		
		return energy;
	}

	private void addEnergy() {
		ItemStack stack = this.energySlot.getStackInSlot(0);
		if(!stack.isEmpty() && stack != null) {
			int itemEnergy = this.getEnergy(stack) / 10;
			if(itemEnergy > 0) {
				long i = this.storage.getEnergyStoredWithLong() + itemEnergy;

				if(i <= this.storage.getMaxEnergyStoredWithLong()) {
					this.storage.receiveEnergyWithLong(itemEnergy, false);
					if(stack.getItem() == Items.LAVA_BUCKET) {
						this.energySlot.setStackInSlot(0, new ItemStack(Items.BUCKET));
					}else {
						stack.shrink(1);
					}
					return;
				}
			}else if(stack.hasCapability(CapabilityEnergy.ENERGY, (EnumFacing) null)) {
				long i = this.storage.getEnergyStoredWithLong() + itemEnergy;
				if(!(i > this.maxEnergy)) {
					if(i < this.storage.getMaxEnergyStoredWithLong()) {
						IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, (EnumFacing) null);
						this.storage.receiveEnergyWithLong(energy.extractEnergy((int) this.storage.receiveEnergyWithLong(10001, true), false), false);
						return;
					}
				}
			}else if(stack.hasCapability(CapabilityJiuEnergy.ENERGY, (EnumFacing) null)) {
				long i = this.storage.getEnergyStoredWithLong() + itemEnergy;
				if(!(i > this.maxEnergy)) {
					if(i < this.storage.getMaxEnergyStoredWithLong()) {
						IJiuEnergyStorage energy = stack.getCapability(CapabilityJiuEnergy.ENERGY, (EnumFacing) null);
						this.storage.receiveEnergyWithLong(energy.extractEnergyWithLong(this.storage.receiveEnergyWithLong(10001, true), false), false);
						return;
					}
				}
			}else if(Loader.isModLoaded("ic2") && stack.getItem() instanceof IElectricItem) {
				// JiuUtils.nbt.setItemNBT(stack, "charge", (JiuUtils.nbt.getItemNBTDouble(stack, "charge")-1));
				// this.storage.receiveEnergy(4, false);
			}else if(JiuUtils.item.isBlock(stack)) {
				Block block = JiuUtils.item.getBlockFromItemStack(stack);
				if(block == MCSBlocks.creative_energy) {
					this.storage.receiveEnergyWithLong(Integer.MAX_VALUE, false);
					return;
				}else if(block == MCSBlocks.C_creative_energy_B) {
					this.storage.receiveEnergyWithLong(Integer.MAX_VALUE, false);
					return;
				}
			}
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
		super.writeToNBT(nbt);
		nbt.setInteger("ShrinkCount", this.shrinkCount);
		nbt.setBoolean("Debug", this.debug);

		nbt.setTag("EnergySlot", this.energySlot.serializeNBT());
		nbt.setTag("CompressedSlot", this.compressedSlot.serializeNBT());
		nbt.setTag("Energy", this.storage.writeToNBT(null, false));

		NBTTagList activates = new NBTTagList();
		for(int i = 0; i < this.activate.size(); i++) {
			NBTTagCompound activateNbt = new NBTTagCompound();
			activateNbt.setInteger("Slot", i);
			activateNbt.setBoolean("State", this.activate.get(i));
			activates.appendTag(activateNbt);
		}
		nbt.setTag("SlotActivate", activates);

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.storage.readFromNBT(nbt.getCompoundTag("Energy"), false);
		this.shrinkCount = nbt.getInteger("ShrinkCount") < 8 ? 10 : nbt.getInteger("ShrinkCount");
		this.debug = nbt.getBoolean("Debug");

		this.energySlot.deserializeNBT(nbt.getCompoundTag("EnergySlot"));
		this.compressedSlot.deserializeNBT(nbt.getCompoundTag("CompressedSlot"));

		NBTTagList activates = nbt.getTagList("SlotActivate", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < activates.tagCount(); i++) {
			NBTTagCompound activateNbt = activates.getCompoundTagAt(i);
			this.activate.set(activateNbt.getInteger("Slot"), activateNbt.getBoolean("State"));
		}
	}

	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing side) {
		if(cap == CapabilityEnergy.ENERGY
		|| cap == CapabilityJiuEnergy.ENERGY
		|| cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}

		return super.hasCapability(cap, side);
	}
	final JiuEnergyStorage capStorage = new JiuEnergyStorage(0,0,0,0) {
		public BigInteger receiveEnergyWithBigInteger(BigInteger maxReceive, boolean simulate) {return storage.receiveEnergyWithBigInteger(maxReceive, simulate);}
		public java.math.BigInteger extractEnergyWithBigInteger(java.math.BigInteger maxExtract, boolean simulate) {return BigInteger.ZERO;}
		public boolean canReceive() {return storage.canReceive();}
		public boolean canExtract() {return false;}
		public BigInteger getEnergyStoredWithBigInteger() {return storage.getEnergyStoredWithBigInteger();}
		public BigInteger getMaxEnergyStoredWithBigInteger() {return storage.getMaxEnergyStoredWithBigInteger();}
	};

	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing side) {
		if(cap == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(this.capStorage.toFEStorage());
		}
		if(cap == CapabilityJiuEnergy.ENERGY) {
			return CapabilityJiuEnergy.ENERGY.cast(this.capStorage);
		}
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if(side == EnumFacing.UP) {
				return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.energySlot);
			}
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.compressedSlot);
		}
		return super.getCapability(cap, side);
	}

	public class BigItemStackHandler extends ItemStackHandler {
		private final TileEntity te;

		public BigItemStackHandler(int size, TileEntity te) {
			super(size);
			this.te = te;
			this.setSize(size);
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if(!this.isItemValid(slot, stack) && slot < 0 || slot >= stacks.size()) return stack;
			if(this.getStackInSlot(slot).getCount() >= Integer.MAX_VALUE-2) return stack;
			if(this.getStackInSlot(slot).isEmpty()) return super.insertItem(slot, stack, simulate);
			
			int emptySlot = -1;
			for(int i = 0; i < this.stacks.size(); i++) {
				ItemStack slotStack = this.stacks.get(i);
				if(emptySlot == -1 && slotStack.isEmpty()) {
					emptySlot = i;
				}
				if(!slotStack.isEmpty() && JiuUtils.item.equalsStack(slotStack, stack)) {
					return super.insertItem(i, stack, simulate);
				}
			}
			if(this.te instanceof TileEntityCompressor && !((TileEntityCompressor) this.te).slotCanCraft(emptySlot)) {
				return stack;
			}

			return emptySlot == -1 ? stack : super.insertItem(emptySlot, stack, simulate);
		}

		@Override
		public boolean isItemValid(int slot, ItemStack stack) {
			if(slot == 0) {
				return true;
			}
			if(JiuUtils.item.equalsStack(compressedSlot.getStackInSlot(slot), stack)) {
				return true;
			}

			ItemStack unStack = compressedSlot.getStackInSlot(0);

			if(unStack != null && !unStack.isEmpty()) {
				if(JiuUtils.item.equalsStack(MCSUtil.item.getUnCompressed(stack), unStack)) {
					return true;
				}
			}
			return false;
		}

		@Override
		protected void onLoad() {
			this.te.markDirty();
		}

		@Override
		protected void onContentsChanged(int slot) {
			this.te.markDirty();
		}

		@Override
		public NBTTagCompound serializeNBT() {
			NBTTagList nbtTagList = new NBTTagList();
			for(int i = 0; i < stacks.size(); i++) {
				if(!stacks.get(i).isEmpty()) {
					ItemStack stack = stacks.get(i);
					NBTTagCompound itemTag = new NBTTagCompound();
					itemTag.setInteger("Slot", i);
					stack.writeToNBT(itemTag);
					itemTag.setInteger("ItemCount", stack.getCount());
					nbtTagList.appendTag(itemTag);
				}
			}
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setTag("Items", nbtTagList);
			nbt.setInteger("Size", stacks.size());
			return nbt;
		}

		@Override
		public void deserializeNBT(NBTTagCompound nbt) {
			setSize(nbt.hasKey("Size", Constants.NBT.TAG_INT) ? nbt.getInteger("Size") : stacks.size());
			NBTTagList tagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
				int slot = itemTags.getInteger("Slot");

				if(slot >= 0 && slot < stacks.size()) {
					ItemStack stack = new ItemStack(itemTags);
					stack.setCount(itemTags.getInteger("ItemCount"));
					stacks.set(slot, stack);
				}
			}
			onLoad();
		}

		@Override
		protected int getStackLimit(int slot, ItemStack stack) {
			return Integer.MAX_VALUE;
		}
	}
}
