//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.blocks.net.container;

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
	int energy = 0;
	int maxEnergy = 0;
	private int energyID = 0;
	@SuppressWarnings("unused")
	private final EntityPlayer player;
	private final InventoryPlayer inventory;
	private final World world;
	private final BlockPos pos;
	private TileEntityCompressor te = null;
	
	public ContainerCompressor(EntityPlayer player, World world, BlockPos pos) {
		this.player = player;
		this.inventory = player.inventory;
		this.world = world;
		this.pos = pos;
		
		TileEntity posTe = world.getTileEntity(pos);
		if(posTe instanceof TileEntityCompressor) {
			this.te = (TileEntityCompressor) posTe;
		}
		
		if(this.te != null) {
			this.energy = this.te.storage.getEnergyStored();
			this.maxEnergy = this.te.storage.getMaxEnergyStored();
			
			this.addSlotToContainer(new SlotItemHandler(this.te.energySlot, 0, 10, 46));
			
			this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, 0, 95, 7));
			
			for(int i = 1; i <= 8; i++) {
				this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, i, 	   14 + 18 * i, 28));
				this.addSlotToContainer(new SlotItemHandler(this.te.compressedSlot, i + 8, 14 + 18 * i, 46));
			}
			
			int[] range = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
			for (int i : range) {
				this.addSlotToContainer(new Slot(this.inventory, i + 9,  14 + 18 * i, 71));
				this.addSlotToContainer(new Slot(this.inventory, i + 18, 14 + 18 * i, 89));
				this.addSlotToContainer(new Slot(this.inventory, i + 27, 14 + 18 * i, 107));
				this.addSlotToContainer(new Slot(this.inventory, i,      14 + 18 * i, 129));
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
		
		if(!this.te.getWorld().isRemote) {
			if(this.energy != te.storage.getEnergyStored()) {
				this.energy = te.storage.getEnergyStored();
				for(IContainerListener listener : this.listeners) {
					listener.sendWindowProperty(this, this.energyID, this.energy);
				}
			}
		}
		
//		TileEntity te = this.world.getTileEntity(this.pos);
//		if(te instanceof TileEntityCompressor) {
//			int energy = ((TileEntityCompressor) te).storage.getEnergyStored();
//			if(this.energy != energy) {
//				this.energy = energy;
//				for(IContainerListener listener : this.listeners) {
//					if(listener instanceof EntityPlayerMP) {
//						listener.sendWindowProperty(this, this.energyID, energy);
//					}
//				}
//			}
//		}
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		if(id == this.energyID) {
			this.energy = data;
		}
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		if(listener instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) listener;
			player.sendWindowProperty(this, 0, this.te.storage.getEnergyStored());
		}
	}
	
	public TileEntityCompressor getTileEntity() {
		return this.te;
	}
	
	public int getEnergy() {
		return this.energy;
	}
	
	public int getMaxEnergy() {
		return this.maxEnergy;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		boolean haveBlock = player.world.getBlockState(this.pos).getBlock() != Blocks.AIR;
		return player.world.equals(this.world) && player.getDistanceSq(this.pos) <= 32.0 && haveBlock;
	}
}
