//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.blocks.tileentity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.type.ChangeBlockType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

public class TileEntityChangeBlock extends TileEntity implements ITickable {
	private int continueTick = -1;
	private int continueSecond = -1;
	private int continueMinute = -1;
	private long continueAllTick = -1;
	private boolean dropBlock = true;
	private List<ItemStack> dropStacks = null;
	private int meta = 0;
	
	public TileEntityChangeBlock(int meta, Map<Integer, ChangeBlockType> entrys) {
		this.meta = meta;
		if(entrys.containsKey(meta)) {
			ChangeBlockType type = entrys.get(meta);
			this.dropBlock = type.canDropBlock();
			
			int[] timer = type.getTime();
			this.continueTick = timer[0] <= 0 ? 1 : timer[0];
			this.continueSecond = timer[1] < 0 ? 0 : timer[1];
			this.continueMinute = timer[2] < 0 ? 0 : timer[2];
			this.continueAllTick = JiuUtils.other.parseTick(continueMinute, continueSecond, continueTick);
			
			this.dropStacks = type.getDrops();
		}
	}
	
	boolean writeLog = true;
	boolean makeLog = true;
	String log = "null";
	
	private int tick = 0;
	private int s = 0;
	private int m = 0;
	private long allTick = 0;
	private boolean isAlive = true;
	
	@Override
	public void update() {
		if(this.isAlive) {
			this.markDirty();
			if(this.makeLog) {
				this.log = JiuUtils.day.getDate() + " [ " + this.world.getBlockState(getPos()).getBlock().getRegistryName() + "@" + this.meta + "] Place at " + "Dim:" + this.world.provider.getDimension() + ",DimName:" + this.world.provider.getDimensionType().getName() + ",X:" + this.pos.getX() + ",Y:" + this.pos.getY() + ",Z:" + this.pos.getZ();
				this.makeLog = false;
			}
			
			if(this.continueAllTick > 0 && this.isAlive) {
				if(this.writeLog) {
					if(!this.world.isRemote) {
						MCS.instance.log.info(this.log);
						String path = "logs/jiu/" + MCS.MODID + "/";
						String file = path + "mcs_server.log";
						File filepath = new File(path);
						
						if (!filepath.exists()) {
							filepath.mkdirs();
						}
						
						this.writeLog(file, log);
						this.writeLog = false;
					}
				}
				
//				MCS.instance.log.info("M:" + m + " S:" + s + " Tick:" + tick + " | AllTick: " + allTick);
				
				if(this.allTick == ((this.continueAllTick / 5) * 4)) {
					this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.DIAMOND_BLOCK.getDefaultState()));
				}
				
				if(this.allTick == ((this.continueAllTick / 5) * 3)) {
					this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.GOLD_BLOCK.getDefaultState()));
				}
				
				if(this.allTick == ((this.continueAllTick / 5) * 2)) {
					this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.IRON_BLOCK.getDefaultState()));
				}
				
				if(this.allTick == this.continueAllTick / 5) {
					this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.WOODEN_BUTTON.getDefaultState()));
				}
				
				if(this.tick >= this.continueTick) {
					if(this.s >= this.continueSecond) {
						if(this.m >= this.continueMinute) {
							this.world.removeTileEntity(this.pos);
							if(this.dropStacks.size() == 1) {
								this.world.playSound(null, this.pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
								this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.OBSIDIAN.getDefaultState()));
								
								if(JiuUtils.item.isBlock(this.dropStacks.get(0))) {
									IBlockState newState = JiuUtils.item.getStateFromItemStack(this.dropStacks.get(0));
									
									if(this.dropBlock) {
										this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
										JiuUtils.item.spawnAsEntity(this.world, this.pos, this.dropStacks);
									}else {
										this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
										this.world.setBlockState(this.pos, newState);
										JiuUtils.item.spawnAsEntity(this.world, this.pos.add(0, 1, 0), JiuUtils.item.copyStack(this.dropStacks.get(0), this.dropStacks.get(0).getCount() - 1, false));
									}
								}else {
									this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
									JiuUtils.item.spawnAsEntity(this.world, this.pos.add(0, 1, 0), this.dropStacks);
								}
							}else {
								this.world.playSound(null, this.pos, SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.BLOCKS, 1.0F, 1.0F);
								this.world.playEvent(2005, this.pos, 9);
								this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
								JiuUtils.item.spawnAsEntity(this.world, this.pos, this.dropStacks);
							}
							
							this.isAlive = false;
							this.world.removeTileEntity(this.pos);
						}
					}
				}
				
				this.tick++;
				this.allTick++;
				
				if(this.tick >= 20) {
					this.tick = 0;
					this.s += 1;
				}
				
				if(this.s >= 60) {
					this.s = 0;
					this.m += 1;
				}
				this.markDirty();
			}else {
				this.isAlive = false;
				this.world.removeTileEntity(this.pos);
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
		return "Continue all tick:" + this.continueAllTick + ", tick: " + tick + ", s: " + s + ", m: " + m;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("WriteLog", this.writeLog);
		nbt.setBoolean("MakeLog", this.makeLog);
		
		nbt.setString("Log", this.log);
		
		nbt.setInteger("ChangeTick", this.tick);
		nbt.setInteger("ChangeS", this.s);
		nbt.setInteger("ChangeM", this.m);
		nbt.setLong("AllTick", this.allTick);
		
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.writeLog = nbt.getBoolean("WriteLog");
		this.makeLog = nbt.getBoolean("MakeLog");
		
		this.log = nbt.getString("Log");
		
		this.tick = nbt.getInteger("ChangeTick");
		this.s = nbt.getInteger("ChangeS");
		this.m = nbt.getInteger("ChangeM");
		this.allTick = nbt.getLong("AllTick");
	}
}
