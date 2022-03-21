package cat.jiu.mcs.blocks.net.container;

import java.math.BigInteger;

import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCompressor extends Container {
	private BigInteger bigEnergy = BigInteger.ZERO;
	int energy = 0;
	int shrinkCount = 10;
	private final int energyID = 0;
	private final int shrinkCountID= 1;
	private final InventoryPlayer inventory;
	private final World world;
	private final BlockPos pos;
	private TileEntityCompressor te = null;
	
	public ContainerCompressor(EntityPlayer player, World world, BlockPos pos) {
		this.inventory = player.inventory;
		this.world = world;
		this.pos = pos;
		
		TileEntity posTe = world.getTileEntity(pos);
		if(posTe instanceof TileEntityCompressor) {
			this.te = (TileEntityCompressor) posTe;
		}
		
		if(this.te != null) {
			this.energy = this.te.storage.getEnergyStoredWithBigInteger().intValue();
			this.bigEnergy = this.te.storage.getEnergyStoredWithBigInteger();
			
			this.addSlotToContainer(new SlotItemHandler(this.te.energySlot, 0, 10, 70));
			
			this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, 0, 95, 7));
			
			for(int i = 1; i <= 8; i++) {
				this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, i, 	   14 + 18 * i, 36));
				this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, i + 8, 14 + 18 * i, 65));
			}
			
			int[] range = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
			for (int i : range) {
				this.addSlotToContainer(new Slot(this.inventory, i + 9,  14 + 18 * i, 96));
				this.addSlotToContainer(new Slot(this.inventory, i + 18, 14 + 18 * i, 114));
				this.addSlotToContainer(new Slot(this.inventory, i + 27, 14 + 18 * i, 132));
				this.addSlotToContainer(new Slot(this.inventory, i,      14 + 18 * i, 154));
			}
		}
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		return super.transferStackInSlot(player, index);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if(this.world.getTileEntity(this.pos) instanceof TileEntityCompressor) {
			this.te = (TileEntityCompressor) this.world.getTileEntity(this.pos);
			this.bigEnergy = this.te.storage.getEnergyStoredWithBigInteger();
		}
		
		if(!this.te.getWorld().isRemote) {
			if(this.energy != te.storage.getEnergyStoredWithLong()) {
				this.energy = te.storage.getEnergyStoredWithBigInteger().intValue();
				for(IContainerListener listener : this.listeners) {
					listener.sendWindowProperty(this, this.energyID, (int) this.energy);
				}
			}
			if(this.shrinkCount != this.te.getShrinkCount()) {
				this.shrinkCount = this.te.getShrinkCount();
				for(IContainerListener listener : this.listeners) {
					listener.sendWindowProperty(this, this.shrinkCountID, this.shrinkCount);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		switch(id) {
			case energyID: this.energy = data; break;
			case shrinkCountID : this.shrinkCount = data; break;
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if(listener instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) listener;
			player.sendWindowProperty(this, 0, (int) this.te.storage.getEnergyStoredWithLong());
		}
	}
	
	public TileEntityCompressor getTileEntity() {
		return this.te;
	}
	
	public int getEnergy() {
		return this.energy;
	}
	
	public BigInteger getBigEnergy() {
		return this.bigEnergy;
	}
	
	public int getShrinkCount() {
		return this.shrinkCount;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		boolean haveBlock = player.world.getBlockState(this.pos).getBlock() != Blocks.AIR;
		return player.world.equals(this.world) && player.getDistanceSq(this.pos) <= 32.0 && haveBlock;
	}
}
