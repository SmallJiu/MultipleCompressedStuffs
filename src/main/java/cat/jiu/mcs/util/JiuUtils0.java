package cat.jiu.mcs.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import cat.jiu.core.util.crafting.Recipes;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.base.BaseBlock;
import cat.jiu.mcs.util.base.BaseBlockItem;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

public final class JiuUtils0 {
	
	public static final ItemUtils item = new ItemUtils();
	public static final EntityUtils entity = new EntityUtils();
	public static final OtherUtils other = new OtherUtils();
	public static final Recipes recipe = new Recipes(MCS.MODID);

	public static class ItemUtils{
		
		public void itemInit(Item item, String name, CreativeTabs tab, boolean hasSubtypes) {
			item.setHasSubtypes(hasSubtypes);
			item.setUnlocalizedName("mcs." + name);
			item.setCreativeTab(tab);
			ForgeRegistries.ITEMS.register(item.setRegistryName(name));
			MCSItems.ITEMS.add(item);
		}
		
		public void blockInit(BaseBlock block, String name, CreativeTabs tab, float hardness, boolean hasSubType) {
			block.setUnlocalizedName("mcs." + name);
			block.setCreativeTab(tab);
			if(hardness < 0) {
				block.setHardness(Float.MAX_VALUE);
			}else {
				block.setHardness(hardness);
			}
			
			MCSBlocks.BLOCKS.add(block);
			ForgeRegistries.BLOCKS.register(block.setRegistryName(name));
			ForgeRegistries.ITEMS.register(new BaseBlockItem(block, hasSubType).setRegistryName(name));
		}
		
		/**
		 * {@link Block#spawnAsEntity(World, BlockPos, ItemStack)}
		 */
		public void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
			if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean("doTileDrops")&& !worldIn.restoringBlockSnapshots) {
				EntityItem ei = new EntityItem(worldIn, (double)pos.getX(), (double)pos.getY() + 1, (double)pos.getZ(), stack);
//	            ei.setDefaultPickupDelay();
	            worldIn.spawnEntity(ei);
	        }
            
//			Block.spawnAsEntity(worldIn, pos, stack);
		}
		
		public void spawnAsEntity(World worldIn, BlockPos pos, ItemStack[] stack, boolean clearArray) {
			for(int i = 0; i < stack.length; ++i) {
				this.spawnAsEntity(worldIn, pos, stack[i]);
				if(clearArray) {
					if(i == stack.length) {
						for(int j = 0; j < stack.length; ++j) {
							stack[j] = null;
						}
						break;
					}
				}
			}
		}
		
		public void spawnAsEntity(World worldIn, BlockPos pos, List<ItemStack> stack, boolean clearArray) {
			for(int i = 0; i < stack.size(); ++i) {
				this.spawnAsEntity(worldIn, pos, stack.get(i));
				if(clearArray) {
					if(i == stack.size()) {
						stack.clear();
						break;
					}
				}
			}
		}
		
		/**
		 * {@link Item#getByNameOrId(String)}
		 */
		public Item getItemByNameOrId(String name) {
			Item item = Item.getByNameOrId(name);
			if(item == null) {
				MCS.instance.log.fatal(name + ": item not found");
			}
			
			return item;
		}
		
		/**
		 * {@link Block#getBlockFromName(String)}
		 */
		public Block getBlockFromName(String name) {
			Block block = Block.getBlockFromName(name);
			if(item == null) {
				MCS.instance.log.fatal(name + ": block not found");
			}
			
			return block;
		}
		
		public boolean isBlock(ItemStack stack) {
			if(stack == null) {
				return false;
			}
			return stack.getItem() instanceof ItemBlock;
		}
		
		public Block getBlockFromItemStack(ItemStack stack) {
			return isBlock(stack) ? ((ItemBlock)stack.getItem()).getBlock() : Blocks.AIR;
		}
		
		@SuppressWarnings("deprecation")
		public IBlockState getStateFromItemStack(ItemStack stack) {
			return isBlock(stack) ? getBlockFromItemStack(stack).getStateFromMeta(stack.getMetadata()) : Blocks.AIR.getDefaultState();
		}
		
		public ItemStack getStackFormBlockState(IBlockState state) {
			return this.getStackFormBlockState(state, 1);
		}
		
		public ItemStack getStackFormBlockState(IBlockState state, int amout) {
			return new ItemStack(state.getBlock(), amout, state.getBlock().getMetaFromState(state));
		}
		
		public int getMetaFormBlockState(IBlockState state) {
			return state.getBlock().getMetaFromState(state);
		}
		
		// 修复物品
		public void fixedItem(ItemStack stack) {
			stack.setItemDamage(stack.getItemDamage() - 1);
		}
		
		public void fixedItem(ItemStack stack, int damage) {
			stack.setItemDamage(stack.getItemDamage() - damage);
		}
		
		public ItemStack copyStack(ItemStack stack, int i, boolean changeMeta) {
			if(changeMeta) {
				return new ItemStack(stack.getItem(), stack.getCount(), i);
			}else {
				return new ItemStack(stack.getItem(), i, stack.getMetadata());
			}
		}
		
		public ItemStack getStackFromString(String name, String amout, String meta) {
			try {
				Item jItem = this.getItemByNameOrId(name);
				int jAmout = new Integer(amout);
				int jMeta = new Integer(meta);
				boolean isBlock = jItem instanceof ItemBlock;
				
				if(isBlock) {
					if(!(jMeta > 15)) {
						return new ItemStack(jItem, jAmout, jMeta);
					}else {
						MCS.instance.log.fatal("\"" + name +  "\": "+ "\"" + jMeta + "\"" + " It's too large! It must be >=15");
						return new ItemStack(jItem, jAmout, 15);
					}
				}else {
					return new ItemStack(jItem, jAmout, jMeta);
				}
			} catch (Exception e) {
				MCS.instance.log.fatal(e.getMessage() + " is not Number!");
				return null;
			}
		}
		
		@SuppressWarnings("unused")
		public boolean checkConfigStack(String[] configs, String cname, boolean isBlock, int cmeta, int metaIndex) {
			boolean lag = false;
			for(int i = 1; i < configs.length; ++i) {
				String[] config = other.custemSplitString(configs[i], "|");
				
				String oname = config[0];
				try {
					int ometa = new Integer(config[metaIndex]);
					
					if(cname.equals(oname)) {
						if(isBlock) {
							if(!(ometa > 15)) {
								if(cmeta == ometa) {
									return true;
								}else {
									return false;
								}
							}else {
								MCS.instance.log.fatal("\"" + oname +  "\": "+ "\"" + ometa + "\"" + " It's too large! It must be >=15");
								return false;
							}
						}else {
							if(cmeta == ometa) {
								return true;
							}else {
								return false;
							}
						}
					}else {
						MCS.instance.log.fatal("\"" + oname +  "\": "+ "\"" + oname + "\"" + " is not belong to MCS's Block!");
						return false;
					}
				} catch (Exception e) {
					MCS.instance.log.fatal(oname + ": " + config[metaIndex] +  " is not Number!");
					return false;
				}
			}
			
			return lag;
		}
		
		public boolean equalsStack(ItemStack stackA, ItemStack stackB, boolean checkAmout) {
			if(stackA == null || stackB == null) {
				return false;
			}
			if(stackA == stackB) {
				return true;
			}else {
				if(stackA.getItem().equals(stackB.getItem())) {
					if(checkAmout) {
						if(stackA.getCount() == stackB.getCount()) {
							if(stackA.getMetadata() == stackB.getMetadata()) {
								return true;
							}else {
								return false;
							}
						}else {
							return false;
						}
					}else {
						if(stackA.getMetadata() == stackB.getMetadata()) {
							return true;
						}else {
							return false;
						}
					}
				}else {
					return false;
				}
			}
		}
		
		public boolean equalsState(IBlockState stateA, IBlockState stateB) {
			if(stateA == null || stateB == null) {
				return false;
			}
			if(stateA == stateB) {
				return true;
			}else {
				if(stateA.getBlock().equals(stateB.getBlock())) {
					if(stateA.getBlock().getMetaFromState(stateA) == stateB.getBlock().getMetaFromState(stateB)) {
						return true;
					}else {
						return false;
					}
				}else {
					return false;
				}
			}
		}
		
		// 从 ItemStack获取矿物词典
		public List<String> getOreDict(ItemStack stack) {
			List<String> names = new ArrayList<>();
			int[] ids = OreDictionary.getOreIDs(stack);

			for (int id : ids) {
				names.add(OreDictionary.getOreName(id));
			}
			
			return names;
		}
		
		//未完成
		//从ItemStack获取物品/方块材质
		@SuppressWarnings({ "deprecation", "unused" })
		public ResourceLocation getTexture(ItemStack stack) {
			if(isBlock(stack)) {
				Block block = this.getBlockFromItemStack(stack);
				IBlockState state = block.getStateFromMeta(stack.getMetadata());
			}else if(stack.getItem() instanceof Item) {
				Item item = stack.getItem();
			}
			return null;
		}
		
		public void registerCompressedOre(String oreDictName, Block itemIn) {
			this.registerCompressedOre(oreDictName, itemIn, false);
		}
		
		public void registerCompressedOre(String oreDictName, Block itemIn, boolean isHas) {
			this.registerCompressedOre(oreDictName, Item.getItemFromBlock(itemIn), isHas);
		}
		
		public void registerCompressedOre(String oreDictName, Item itemIn) {
			this.registerCompressedOre(oreDictName, itemIn, false);
		}
		
		public void registerCompressedOre(String oreDictName, Item itemIn, boolean isHas) {
			for(ModSubtypes type : ModSubtypes.values()){
				int meta = type.getMeta();
				
				if(isHas){
					if(meta == 0) {
						this.registerOre("block" + oreDictName, itemIn, 0);
					}else {
						this.registerOre("compressed" + meta + "x" + oreDictName, itemIn, meta);
					}
				}else {
					this.registerOre("compressed" + (meta + 1) + "x" + oreDictName, itemIn, meta);
				}
			}
		}
		
		public void registerCompressedOre(String oreDictName, Block blockIn, String materialName) {
			this.registerCompressedOre(oreDictName, Item.getItemFromBlock(blockIn), materialName);
		}
		
		public void registerCompressedOre(String oreDictName, Item itemIn, String materialName) {
			for(ModSubtypes type : ModSubtypes.values()){
				int meta = type.getMeta();
				
				this.registerOre("compressed" + (meta + 1) + "x" + materialName + oreDictName, itemIn, meta);
			}
		}
		
		public void registerOre(String oreDict, Block blockIn) {
			this.registerOre(oreDict, Item.getItemFromBlock(blockIn), 0);
		}
		
		public void registerOre(String oreDict, Item itemIn) {
			this.registerOre(oreDict, itemIn, 0);
		}
		
		public void registerOre(String oreDict, Block blockIn, int meta) {
			this.registerOre(oreDict, Item.getItemFromBlock(blockIn), meta);
		}
		
		public void registerOre(String oreDict, Item itemIn, int meta) {
			OreDictionary.registerOre(oreDict, new ItemStack(itemIn, 1, meta));
		}
	}
	
	public static class EntityUtils{
		
		/*
		// 发送信息
		public <T extends ICommandSender> void sendMessage(T sender, String key) {
			sender.sendMessage(new TextComponentTranslation(key, 4));
		}
		*/
		
		// 发送信息
		public void sendMessage(ICommandSender sender, String key) {
			sender.sendMessage(new TextComponentTranslation(key, 4)); 
		}
		
		/**
		 *  measure potionTime by the second
		 */
		public void addPotionEffect(EntityLivingBase entity, Potion potion, int potionTime, int potionLevel){
			entity.addPotionEffect(new PotionEffect(potion, potionTime * 20, potionLevel));
		}
	}
	
	public static class OtherUtils{
		 
		//一维数组转二维数组
		public <T> T[][] one2two(T[][] date, T[] da, int start){
			int k = start;
			int hang = date.length;
			int lie = date[0].length;
			
			T[][] date0 = date.clone();
			
			for(int i =0; i < hang; ++i) {
				for(int j =0; j < lie; ++j) {
					date0[i][j] = da[k];
					k++;
				}
			}
			
			return date0;
		}
		
		public String upperCaseToFistLetter(String name) {
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		
		public boolean containKey(String[] strs, String str) {
			for(String stri : strs) {
				if(stri.equals(str)) {
					return true;
				}
			}
			return false;
		}
		
		public boolean containKey(List<String> strs, String str) {
			for(String stri : strs) {
				if(stri.equals(str)) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Use the length of the 0th array to build the array.
		 * If some array length less than this length, will throw.
		 */
		public String[][] more2two(String[]... args) throws ArrayIndexOutOfBoundsException {
			String[][] arg = new String[args.length][args[0].length];
			int hang = arg.length;
			
			for(int i = 0; i < hang; ++i) {
				for(int j = 0; j < arg[i].length; ++j) {
					arg[i][j] = args[i][j];
				}
			}
			return arg;
		}
		
		public int[][] more2two(int[]... args) throws ArrayIndexOutOfBoundsException {
			int[][] arg = new int[args.length][args[0].length];
			int hang = arg.length;
			
			for(int i = 0; i < hang; ++i) {
				for(int j = 0; j < arg[i].length; ++j) {
					arg[i][j] = args[i][j];
				}
			}
			return arg;
		}
		
		public String[] custemSplitString(String arg, String separator){
			return arg.split("\\" + separator);
		}
		
		/**
		 * {@link Potion#getPotionFromResourceLocation(String)}
		 */
		@Nullable
	    public Potion getRegisteredMobEffect(String id) {
	        Potion potion = Potion.getPotionFromResourceLocation(id);
	        
	        if(potion == null) {
	        	MCS.instance.log.fatal("Effect not found: " + id);
	        	return null;
	        }else {
	        	return potion;
	        }
	    }
	}
}
