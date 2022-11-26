package cat.jiu.mcs.blocks.tileentity;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cat.jiu.core.api.ITimer;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.timer.Timer;
import cat.jiu.mcs.util.type.CustomStuffType;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.Constants;

public class TileEntityChangeBlock extends TileEntity implements ITickable {
	private ITimer time = null;
	private long allTick = 0;
	private boolean dropBlock = true;
	private List<ItemStack> dropStacks = null;

	public TileEntityChangeBlock() {}
	public TileEntityChangeBlock(CustomStuffType.ChangeBlockType type) {
		this.dropBlock = type.dropBlock;
		this.time = type.times.copy();
		this.allTick = this.time.getTicks();
		this.dropStacks = type.drops;
	}
	
	public TileEntityChangeBlock(int meta, Map<Integer, CustomStuffType.ChangeBlockType> entrys) {
		if(entrys.containsKey(meta)) {
			CustomStuffType.ChangeBlockType type = entrys.get(meta);
			this.dropBlock = type.dropBlock;
			this.time = type.times;
			this.allTick = this.time.getTicks();
			this.dropStacks = type.drops;
		}
	}

	private boolean isAlive = true;

	@Override
	public void update() {
		if(this.world.isRemote) return;
		if(this.isAlive && this.time != null && this.world.getBlockState(pos).getBlock() != Blocks.AIR) {
			this.markDirty();

			if(this.time.getTicks() >= 0 && this.isAlive) {
				// MCS.instance.log.info("M:" + m + " S:" + s + " Tick:" + tick + " | AllTick: " + allTick);

				if(this.time.getTicks() == this.allTick / 5) {
					this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.DIAMOND_BLOCK.getDefaultState()));
				}
				if(this.time.getTicks() == ((this.allTick / 5) * 2)) {
					this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.GOLD_BLOCK.getDefaultState()));
				}
				if(this.time.getTicks() == ((this.allTick / 5) * 3)) {
					this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.IRON_BLOCK.getDefaultState()));
				}
				if(this.time.getTicks() == ((this.allTick / 5) * 4)) {
					this.world.playSound(null, this.pos, SoundEvents.BLOCK_METAL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					this.world.playEvent(2001, this.pos, Block.getStateId(Blocks.WOODEN_BUTTON.getDefaultState()));
				}

				if(this.time.getTicks() < 1) {
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
//				System.out.println(this.time.getTicks() + " == " + (this.time.getTicks() / 5) + ": " + (this.time.getTicks() == this.time.getTicks() / 5));
//				System.out.println(this.time.getTicks() + " == " + ((this.time.getTicks() / 5) * 2) + ": " + (this.time.getTicks() == ((this.time.getTicks() / 5) * 2)));
//				System.out.println(this.time.getTicks() + " == " + ((this.time.getTicks() / 5) * 3) + ": " + (this.time.getTicks() == ((this.time.getTicks() / 5) * 3)));
//				System.out.println(this.time.getTicks() + " == " + ((this.time.getTicks() / 5) * 4) + ": " + (this.time.getTicks() == ((this.time.getTicks() / 5) * 4)));
				
				this.time.update();
				this.markDirty();
			}else {
				this.isAlive = false;
				this.world.removeTileEntity(this.pos);
			}
		}else {
			this.isAlive = false;
			this.world.removeTileEntity(this.pos);
		}
	}

	public String toString() {
		return this.time.toString();
	}
	
	public ITimer getTime() {return this.time;}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(!this.isAlive) return nbt;
		super.writeToNBT(nbt);
		
		nbt.setBoolean("dropBlock", this.dropBlock);
		nbt.setTag("times", this.time.writeTo(NBTTagCompound.class));
		nbt.setLong("allTicks", this.allTick);
		
		NBTTagList items = new NBTTagList();
		for(ItemStack item : this.dropStacks) {
			items.appendTag(item.writeToNBT(new NBTTagCompound()));
		}
		nbt.setTag("Items", items);

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.dropBlock = nbt.getBoolean("dropBlock");
		if(this.time == null) this.time = new Timer();
		this.time.readFrom(nbt.getCompoundTag("times"));
		this.allTick = nbt.getLong("allTicks");
		
		if(this.dropStacks == null) this.dropStacks = Lists.newArrayList();
		NBTTagList items = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		for(int i = 0; i < items.tagCount(); i++) {
			this.dropStacks.add(new ItemStack(items.getCompoundTagAt(i)));
		}
	}
}
