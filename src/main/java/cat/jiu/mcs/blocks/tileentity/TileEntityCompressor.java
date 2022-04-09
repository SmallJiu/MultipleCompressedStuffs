package cat.jiu.mcs.blocks.tileentity;

import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.core.api.IJiuEnergyStorage;
import cat.jiu.core.energy.CapabilityJiuEnergy;
import cat.jiu.core.energy.JiuEnergyStorage;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import cat.jiu.mcs.util.base.sub.BaseItemFood;
import cat.jiu.mcs.util.base.sub.BaseItemSub;
import cat.jiu.mcs.util.base.sub.tool.BaseItemAxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemHoe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemPickaxe;
import cat.jiu.mcs.util.base.sub.tool.BaseItemShovel;
import cat.jiu.mcs.util.base.sub.tool.BaseItemSword;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSResources;
import ic2.api.item.IElectricItem;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
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
	/**
	 * Energy
	 */
	public final String ENERGY = "Energy";
	public final long maxEnergy = 5000000;
	// public final long maxEnergy = Long.MAX_VALUE;
	public final JiuEnergyStorage storage = new JiuEnergyStorage(maxEnergy, 10000);
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
					this.onBlockCrafting();// 未压缩方块合成为一重压缩方块
					this.onItemCrafting();// 未压缩物品合成为一重压缩物品
					// this.onToolCraft();// 未压缩工具合成为一重压缩工具
				}
				this.craftCompressedItem();// 全部压缩物品合成为下一重的压缩物品
				this.checkItemCount();// 检查slot物品，大于一定数禁止破坏，防止过多物品掉地上卡死
			}
		}
	}

	private void checkItemCount() {
		if(this.checkCount()) {
			return;
		}
		return;
	}

	private boolean checkCount() {
		for(int i = 1; i < this.compressedSlot.getSlots(); i++) {
			ItemStack cStack = this.compressedSlot.getStackInSlot(i);
			if(cStack.getCount() >= 5000) {
				return true;
			}
		}
		return false;
	}

	private void craftCompressedItem() {
		for(int i = 1; i < this.compressedSlot.getSlots(); i++) {
			ItemStack cStack = this.compressedSlot.getStackInSlot(i);
			if(cStack.getCount() >= this.getShrinkCount()) {
				if(cStack.getMetadata() < 15 && i + 1 < this.compressedSlot.getSlots()) {
					if(this.slotCanCraft(i) && cStack.getMetadata() + 1 == i) {
						int amount = cStack.getCount() / 9;
						if(this.compressedSlot.getStackInSlot(i + 1).getCount() < Integer.MAX_VALUE) {
							if(this.compressedSlot.insertItem(i + 1, new ItemStack(cStack.getItem(), amount, cStack.getMetadata() + 1), true).isEmpty()) {
								cStack.shrink(amount * 9);
								if(this.debug || MCS.instance.test_model) {
									
								}else {
									this.storage.extractEnergyWithLong(5 * amount, false);
								}
								this.compressedSlot.insertItem(i + 1, new ItemStack(cStack.getItem(), amount, cStack.getMetadata() + 1), false);
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

	private void addEnergy() {
		ItemStack stack = this.energySlot.getStackInSlot(0);
		if(!stack.isEmpty() && stack != null) {
			if(this.getEnergy(stack) > 0) {
				long i = this.storage.getEnergyStoredWithLong() + this.getEnergy(stack);

				if(i <= this.storage.getMaxEnergyStoredWithLong()) {
					this.storage.receiveEnergyWithLong(this.getEnergy(stack), false);
					if(stack.getItem() == Items.LAVA_BUCKET) {
						this.energySlot.setStackInSlot(0, new ItemStack(Items.BUCKET));
					}else {
						stack.shrink(1);
					}
					return;
				}
			}else if(stack.hasCapability(CapabilityEnergy.ENERGY, (EnumFacing) null)) {
				long i = this.storage.getEnergyStoredWithLong() + this.getEnergy(stack);
				if(!(i > this.maxEnergy)) {
					if(i < this.storage.getMaxEnergyStoredWithLong()) {
						IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, (EnumFacing) null);
						this.storage.receiveEnergyWithLong(energy.extractEnergy((int) this.storage.receiveEnergyWithLong(10001, true), false), false);
						return;
					}
				}
			}else if(stack.hasCapability(CapabilityJiuEnergy.ENERGY, (EnumFacing) null)) {
				long i = this.storage.getEnergyStoredWithLong() + this.getEnergy(stack);
				if(!(i > this.maxEnergy)) {
					if(i < this.storage.getMaxEnergyStoredWithLong()) {
						IJiuEnergyStorage energy = stack.getCapability(CapabilityJiuEnergy.ENERGY, (EnumFacing) null);
						this.storage.receiveEnergyWithLong(energy.extractEnergyWithLong(this.storage.receiveEnergyWithLong(10001, true), false), false);
						return;
					}
				}
			}else if(Loader.isModLoaded("ic2")) {
				if(stack.getItem() instanceof IElectricItem) {
					// JiuUtils.nbt.setItemNBT(stack, "charge", (JiuUtils.nbt.getItemNBTDouble(stack, "charge")-1));
					// this.storage.receiveEnergyWithLong(4, false);
				}
			}else if(JiuUtils.item.isBlock(stack)) {
				if(JiuUtils.item.getBlockFromItemStack(stack) == MCSBlocks.CREATIVE_ENERGY) {
					this.storage.receiveEnergyWithLong(Integer.MAX_VALUE, false);
					return;
				}else if(JiuUtils.item.getBlockFromItemStack(stack) == MCSBlocks.C_CREATIVE_ENERGY_B) {
					this.storage.receiveEnergyWithLong(Integer.MAX_VALUE, false);
					return;
				}
			}
		}
	}

	// 方块的合成
	private void onBlockCrafting() {
		for(BaseBlockSub c : MCSResources.SUB_BLOCKS) {
			ItemStack unBlock = this.compressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unBlock, c.getUnCompressedStack())) {
				if(unBlock.getCount() >= this.getShrinkCount()) {
					int amount = unBlock.getCount() / 9;
					if(JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), true)) {
						if(this.debug || MCS.instance.test_model) {

						}else {
							unBlock.shrink(amount * 9);
							this.storage.extractEnergyWithLong(5 * amount, false);
						}
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), false);
					}
				}
			}
		}
	}

	// 物品的合成
	private void onItemCrafting() {
		for(BaseItemSub c : MCSResources.SUB_ITEMS) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= this.getShrinkCount()) {
					int amount = unItem.getCount() / 9;
					if(JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), true)) {
						if(this.debug || MCS.instance.test_model) {

						}else {
							unItem.shrink(amount * 9);
							this.storage.extractEnergyWithLong(5 * amount, false);
						}
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), false);
					}
				}
			}
		}
		for(BaseItemFood c : MCSResources.FOODS) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= this.getShrinkCount()) {
					int amount = unItem.getCount() / 9;
					if(JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), true)) {
						if(this.debug || MCS.instance.test_model) {

						}else {
							unItem.shrink(amount * 9);
							this.storage.extractEnergyWithLong(5 * amount, false);
						}
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), false);
					}
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private void onToolCraft() {
		for(BaseItemSword c : MCSResources.SWORDS) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= this.getShrinkCount()) {
					int amount = unItem.getCount() / 9;
					if(!JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), true)) {
						if(this.debug || MCS.instance.test_model) {

						}else {
							unItem.shrink(amount * 9);
							this.storage.extractEnergyWithLong(5 * amount, false);
						}
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), false);
					}
				}
			}
		}
		for(BaseItemPickaxe c : MCSResources.PICKAXES) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= this.getShrinkCount()) {
					int amount = unItem.getCount() / 9;
					if(JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), true)) {
						if(this.debug || MCS.instance.test_model) {

						}else {
							unItem.shrink(amount * 9);
							this.storage.extractEnergyWithLong(5 * amount, false);
						}
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), false);
					}
				}
			}
		}
		for(BaseItemShovel c : MCSResources.SHOVELS) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= this.getShrinkCount()) {
					int amount = unItem.getCount() / 9;
					if(JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), true)) {
						if(this.debug || MCS.instance.test_model) {

						}else {
							unItem.shrink(amount * 9);
							this.storage.extractEnergyWithLong(5 * amount, false);
						}
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), false);
					}
				}
			}
		}
		for(BaseItemAxe c : MCSResources.AXES) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= this.getShrinkCount()) {
					int amount = unItem.getCount() / 9;
					if(JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), true)) {
						if(this.debug || MCS.instance.test_model) {

						}else {
							unItem.shrink(amount * 9);
							this.storage.extractEnergyWithLong(5 * amount, false);
						}
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), false);
					}
				}
			}
		}
		for(BaseItemHoe c : MCSResources.HOES) {
			ItemStack unItem = this.compressedSlot.getStackInSlot(0);
			if(JiuUtils.item.equalsStack(unItem, c.getUnCompressedStack())) {
				if(unItem.getCount() >= this.getShrinkCount()) {
					int amount = unItem.getCount() / 9;
					if(JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), true)) {
						if(this.debug || MCS.instance.test_model) {

						}else {
							unItem.shrink(amount * 9);
							this.storage.extractEnergyWithLong(5 * amount, false);
						}
						JiuUtils.item.addItemToSlot(compressedSlot, new ItemStack(c, amount), false);
					}
				}
			}
		}
	}

	// BlockChest
	private int getEnergy(ItemStack stack) {
		Item item = stack.getItem();
		if(item instanceof ItemBlock) {
			Block block = JiuUtils.item.getBlockFromItemStack(stack);
			if(block == Blocks.REDSTONE_BLOCK) {
				return 9000;
			}else if(block == Blocks.COAL_BLOCK) {
				return 7200;
			}
		}else {
			if(item == Items.REDSTONE) {
				return 1000;
			}else if(item == Items.COAL && stack.getMetadata() == 0) {
				return 800;
			}else if(item == Items.COAL && stack.getMetadata() == 1) {
				return 750;
			}else if(item == Items.LAVA_BUCKET) {
				return 9000;
			}else if(item == Items.BLAZE_ROD) {
				return 5000;
			}else if(item == Items.BLAZE_POWDER) {
				return 2500;
			}else if(item == Items.NETHER_STAR) {
				return 10000;
			}else if(item == Items.MAGMA_CREAM) {
				return 1500;
			}else if(item == Items.FIRE_CHARGE) {
				return 500;
			}
		}

		return -1;
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
		nbt.setTag("Energy", this.storage.writeToNBT(new NBTTagCompound()));

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
		this.storage.readFromNBT(nbt.getCompoundTag("Energy"));
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
		if(cap == CapabilityEnergy.ENERGY) {
			return true;
		}
		if(cap == CapabilityJiuEnergy.ENERGY) {
			return true;
		}
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}

		return super.hasCapability(cap, side);
	}

	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing side) {
		if(cap == CapabilityEnergy.ENERGY) {
			return CapabilityEnergy.ENERGY.cast(this.storage.toFEStorage());
		}
		if(cap == CapabilityJiuEnergy.ENERGY) {
			return CapabilityJiuEnergy.ENERGY.cast(this.storage);
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
			if(!this.isItemValid(slot, stack) && slot < 0 || slot >= stacks.size()) {
				return stack;
			}

			if(this.getStackInSlot(slot).isEmpty()) {
				return super.insertItem(slot, stack, simulate);
			}

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
