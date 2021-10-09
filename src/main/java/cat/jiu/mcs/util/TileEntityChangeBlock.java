package cat.jiu.mcs.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import cat.jiu.mcs.MCS;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

@SuppressWarnings("unused")
public class TileEntityChangeBlock extends TileEntity implements ITickable {
	private final IBlockState oldState;
	private final IBlockState continueState;
	private final IBlockState newState;
	private final int newStateMeta;
	private final int oldStateMeta;
	private final int continueTick;
	private final int continueSecond;
	private final int continueMinute;
	private final int continueAllTick;
	private final boolean dropBlock;
	private final ItemStack oldStack;
	private final ItemStack newStack;
	
	public TileEntityChangeBlock(IBlockState oldState, IBlockState continueState, ItemStack changeStack, int tick, int s, int m, boolean dropBlock){
		this.oldState = oldState;
		this.continueState = continueState;
		this.newState = JiuUtils.item.getStateFromItemStack(changeStack);
		this.newStateMeta = JiuUtils.item.getMetaFormBlockState(continueState);
		this.oldStateMeta = this.oldState.getBlock().getMetaFromState(this.oldState);
		this.dropBlock = dropBlock;
		this.continueTick = tick == 0 ? 1 : tick;
		this.continueSecond = s;
		this.continueMinute = m; 
		this.continueAllTick = tick + (((m * 60) + s) * 20);
		this.oldStack = new ItemStack(this.oldState.getBlock(), 1, this.oldStateMeta);
		this.newStack = changeStack;
	}
	
	boolean i = true;
	boolean makeLog = true;
	boolean t = true;
	String log;
	Date date = new Date();
	
	private int tick = 0;
	private int s = 0;
	private int m = 0;
	private int allTick = 0;
	
	@SuppressWarnings("deprecation")
	@Override
	public void update() {
		if(this.t) {
			log = "[ " + (date.getYear() + 1900) + "/" + (date.getMonth() + 1) + "/" + date.getDate()  + " | " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + " ]" + " [ " + this.oldStack.getDisplayName() + "  ] " + " Place at " + "X:" + this.pos.getX() + " Y:" + this.pos.getY() + " Z:" + this.pos.getZ();
			
			this.t = false;
		}
		
		if(JiuUtils.item.equalsState(this.oldState, this.continueState)) {
			
			this.tick++;
			this.allTick++;
			if(this.i) {
				if(!this.world.isRemote) {
					MCS.instance.log.info(log);
				}
				i = false;
			}
			
			if(this.makeLog) {
				if(!this.world.isRemote) {
					String path = "logs/jiu/" + MCS.MODID + "/";
					String file = path + "mcs_server.log";
					File filepath = new File(path);
					
					if (!filepath.exists()) {
						filepath.mkdirs();
					}
					
					this.writeLog(file, log);
					this.makeLog = false;
				}
			}
			
			if(this.tick == 20) {
				this.markDirty();
//				System.out.println("continue all tick:" + this.continueAllTick + " tick: " + tick + " s: " + s + " m: " + m + " now tick: " + this.allTick);
				
				this.tick = 0;
				this.s = this.s + 1;
			}
			
			if(this.s == 60) {
				this.s = 0;
				this.m = this.m + 1;
			}
			
			if(this.allTick == this.continueAllTick / 5) {
				this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.WOODEN_BUTTON.getDefaultState()));
			}
			
			if(this.allTick == ((this.continueAllTick / 5) * 2)) {
				this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.IRON_BLOCK.getDefaultState()));
			}
			
			if(this.allTick == ((this.continueAllTick / 5) * 3)) {
				this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.GOLD_BLOCK.getDefaultState()));
			}
			
			if(this.allTick == ((this.continueAllTick / 5) * 4)) {
				this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
				this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.DIAMOND_BLOCK.getDefaultState()));
			}
			
			if(this.m == this.continueMinute) {
				if(this.s == this.continueSecond) {
					if(this.tick == this.continueTick) {
						this.world.removeTileEntity(this.pos);
						
						if(JiuUtils.item.isBlock(this.newStack)) {
							this.world.playSound(null, this.pos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
							this.world.playEvent(2001, this.pos, Block.getStateId(this.newState));
							
							if(this.dropBlock) {
								Block.spawnAsEntity(this.world, this.pos, this.newStack);
								this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
							}else {
								Block.spawnAsEntity(this.world, this.pos.add(0, 1, 0), new ItemStack(this.newStack.getItem(), (this.newStack.getCount() - 1), this.newStack.getMetadata()));
								this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
								this.world.setBlockState(this.pos, this.newState);
							}
						}else {
							this.world.playSound(null, this.pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
							Block.spawnAsEntity(this.world, this.pos, this.newStack);
							this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
						}
						
						this.world.removeTileEntity(this.pos);
					}
				}
			}
		}else {
			this.world.removeTileEntity(this.pos);
		}
	}
	
	private void writeLog(String path, String name) {
		try {
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(path, true));
			out.write(name + "\n");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		return "OldState: " + this.oldState + ",NewState: " + this.newState + "," + ", continue all tick:" + this.continueAllTick + ", tick: " + tick + ", s: " + s + ", m: " + m;
	}
	
}
