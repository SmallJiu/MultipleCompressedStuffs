package cat.jiu.mcs.util.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.mcs.interfaces.IMetaName;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.TileEntityChangeBlock;
import cat.jiu.mcs.util.init.MCSBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseBlockSub extends BaseBlock implements IHasModel, IMetaName, ITileEntityProvider {
	
	protected final boolean recipeIsRepeat;
	protected final boolean isBlock;
	protected final Block unCompressedBlock;
	protected final IBlockState unCompressedState;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);
	
	public BaseBlockSub isUnUse() {
		return null;
	}

	private static final PropertyEnum<ModSubtypes> VARIANT = PropertyEnum.create("level", ModSubtypes.class);
	protected final String langModID;
	
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem,  String langModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		if(Loader.isModLoaded(langModID)) {
			return new BaseBlockSub(nameIn, unCompressedItem, langModID, materialIn, soundIn, tabIn, hardnessIn);
		}else {
			return null;
		}
	}
	
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem,  String langModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, langModID, materialIn, soundIn, tabIn, 4.0F);
	}
	
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem,  String langModID, Material materialIn, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, langModID, materialIn, SoundType.METAL, tabIn);
	}
	
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem, String langModID, SoundType soundIn, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, langModID, Material.IRON, soundIn, tabIn);
	}
	
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem, String langModID, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, langModID, Material.IRON, SoundType.METAL, tabIn);
	}
	
	/**
	 * 
	 * @param langModID is the unCompressedItem owner modid, it use to the model
	 * 
	 * @author small_jiu
	 */
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem, String langModID) {
		return register(nameIn, unCompressedItem, langModID, MCS.COMPERESSED_BLOCKS);
	}
	
	/**
	 * This method is not recommended.
	 * because the mod id maybe was change.
	 * 
	 * @author small_jiu
	 */
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
		return register(nameIn, unCompressedItem, unCompressedItem.getItem().getRegistryName().getResourceDomain());
	}
	
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem,  String langModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		super(nameIn, unCompressedItem, materialIn, soundIn, tabIn, hardnessIn, true);
		this.recipeIsRepeat = this.checkRecipe(unCompressedItem);
		this.langModID = langModID;
		this.isBlock = JiuUtils.item.isBlock(unCompressedItem);
		this.unCompressedBlock = this.isBlock ? JiuUtils.item.getBlockFromItemStack(unCompressedItem) : Blocks.AIR;
		this.unCompressedState = this.isBlock ? JiuUtils.item.getStateFromItemStack(unCompressedItem) : Blocks.AIR.getDefaultState();
		MCSBlocks.SUB_BLOCKS.add(this);
		MCSBlocks.SUB_BLOCKS_NAME.add(nameIn);
		MCSBlocks.SUB_BLOCKS_MAP.put(nameIn, this);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, ModSubtypes.LEVEL_1));
	}
	
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem,  langModID, materialIn, soundIn, tabIn, 4.0F);
	}
	
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID, Material materialIn,  CreativeTabs tabIn) {
		this(nameIn, unCompressedItem,  langModID, materialIn, SoundType.METAL, tabIn);
	}
	
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, langModID, Material.IRON, soundIn, tabIn);
	}
	
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID, CreativeTabs tabIn, float hardnessIn) {
		this(nameIn, unCompressedItem, langModID, Material.IRON, SoundType.METAL, tabIn, hardnessIn);
	}
	
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID, float hardnessIn) {
		this(nameIn, unCompressedItem, langModID, MCS.COMPERESSED_BLOCKS, hardnessIn);
	}
	
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, langModID, Material.IRON, tabIn);
	}
	
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID) {
		this(nameIn, unCompressedItem, langModID, MCS.COMPERESSED_BLOCKS);
	}
	
	/**
	 * 
	 * default is minecraft owner unCompressedItem
	 */
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem) {
		this(nameIn, unCompressedItem, "minecraft");
	}
	
	boolean custem = false;
	
	public BaseBlockSub isOreDictCustem() {
		this.custem = !this.custem;
		return this;
	}
	
	public String getUnCompressedName() {
		String[] names = JiuUtils.other.custemSplitString(this.name, "_");
		
		if(this.isBlock) {
			if(names.length == 4) {
				return JiuUtils.other.upperCaseToFistLetter(names[1]) + JiuUtils.other.upperCaseToFistLetter(names[2]) + JiuUtils.other.upperCaseToFistLetter(names[3]);
			}else {
				return JiuUtils.other.upperCaseToFistLetter(names[1]) + JiuUtils.other.upperCaseToFistLetter(names[2]);
			}
		}else {
			List<String> name = new ArrayList<String>();
			for(int i = 1; i < names.length; ++i) {
				name.add(JiuUtils.other.upperCaseToFistLetter(names[i]));
			}
			if(names.length == 3) {
				if(this.custem) {
					return String.format("%s", name.toArray(new String[0]));
				}else {
					return String.format("%s%s", name.toArray(new String[0]));
				}
			}else {
				return String.format("%s", name.toArray(new String[0]));
			}
		}
	}
	
	public boolean isHasBlock() {
		return !this.isBlock;
	}
	
	public String getOwnerMod() {
		return this.langModID;
	}
	
	public boolean checkRecipe(ItemStack unCompressedItem) {
		return false;
	}
	
	private boolean makeRecipe = true;
	
	public BaseBlockSub setMakeDefaultStackRecipe(boolean makeRecipe) {
		this.makeRecipe = makeRecipe;
		return this;
	}
	
	public boolean canMakeDefaultStackRecipe() {
		return this.makeRecipe;
	}
	
	boolean isOpaqueCube = false;
	
	public BaseBlockSub setIsOpaqueCube() {
		this.isOpaqueCube = true;
		return this;
	}
	
	public final String getOredict(int meta) {
		return JiuUtils.item.getOreDict(new ItemStack(this, 1, meta)).get(0);
	}
	
	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		if(this.isOpaqueCube) {
			return this.isOpaqueCube;
		}else if(this.unCompressedItem != null){
			if(this.unCompressedItem.getItem() instanceof ItemBlock) {
				IBlockState block = JiuUtils.item.getStateFromItemStack(this.unCompressedItem);
				
				return block.isOpaqueCube();
			}else {
				return false;
			}
		}else {
			return super.isOpaqueCube(state);
		}
	}
	
	/*
	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		if(this.isOpaqueCube) {
			return BlockRenderLayer.TRANSLUCENT;
		}else if(this.unCompressedItem.getItem() instanceof ItemBlock) {
			Block block = JiuUtils.item.getBlockFromItemStack(this.unCompressedItem);
			return block.getBlockLayer();
		}else {
			return super.getBlockLayer();
		}
	}
	*/
	
	public PropertyEnum<ModSubtypes> getPropertyEnum(){
		return VARIANT;
	}
	
	boolean canChangeBlock = false;
	boolean canDropBlock = false;
	
	public BaseBlockSub canChangeBlock(boolean canChangeBlock, boolean canDropBlock) {
		this.canChangeBlock = canChangeBlock;
		this.canDropBlock = canDropBlock;
		return this;
	}
	
	int continueState;
	ItemStack changeState;
	int tick;
	int s;
	int m;
	
	public BaseBlockSub setChangeBlock(int continueState, ItemStack changeState, int tick, int s, int m) {
		this.continueState = continueState;
		this.changeState = changeState;
		this.tick = tick;
		this.s = s;
		this.m = m;
		return this;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(this.canChangeBlock) {
			return new TileEntityChangeBlock(this.getStateFromMeta(meta), this.getStateFromMeta(continueState), changeState, tick, s, m, this.canDropBlock);
		}else {
			TileEntityChangeBlock te = null;
			if(Configs.custom.custem_already_stuff.block.custem_change_block.length != 1) {
				try {
					for(int j = 1; j < Configs.custom.custem_already_stuff.block.custem_change_block.length; ++j) {
						String str = Configs.custom.custem_already_stuff.block.custem_change_block[j];
						
						String[] strri = JiuUtils.other.custemSplitString(str, "|");
						
						for(int i = 0; i < strri.length; ++i) {
							if(!(strri.length < 9 || strri.length > 9)) {
								String oname = strri[0];
								String cname = strri[2];
								
								try {
									// o是压缩方块的缩写
									// c是改变后的东西的缩写
									int ometa = new Integer(strri[1]);
									int camout = new Integer(strri[3]);
									int cmeta = new Integer(strri[4]);
									int tick = new Integer(strri[5]);
									int s = new Integer(strri[6]);
									int m = new Integer(strri[7]);
									boolean drop = new Boolean(strri[8]);
									
									//从已注册的方块名字里查找是否含有主方块名字
									if(MCSBlocks.BLOCKS_NAME.contains(oname)) {
										try{
											//判断主方块meta是否>=15
											if(ometa <= 16) {
												//如果是副物品是方块则转到方块区，如果不是，则转到物品区
												if(JiuUtils.item.isBlock(new ItemStack(Item.getByNameOrId(cname), camout, cmeta))) {
													//副stuff为方块
													//判断副方块meta是否>=15
													if(cmeta < 16) {
														//判断是不是true或者false
														if(strri[8].equals("true") || strri[8].equals("false")) {
															//判断主方块名字是不是等于现在这个方块的名字
															if(this.name.equals(oname)) {
																te = new TileEntityChangeBlock(
																	this.getStateFromMeta(meta),
																	this.getStateFromMeta(ometa),
																	new ItemStack(Item.getByNameOrId(cname), camout, cmeta),
																	tick,
																	s,
																	m,
																	drop
																);
															}
														}else {
															if(worldIn.isRemote) {
																MCS.instance.log.fatal("\"" + oname +  "\": "+ "\"" + drop + "\"" + " It's not boolean! It must be \"true\" or \"false\"");
															}
														}
													}else {
														if(worldIn.isRemote) {
															MCS.instance.log.fatal("\"" + oname +  "\": "+ "\"" + cmeta + "\"" + " It's too large! It must be >=15");
														}
													}
												}else {
													// 副stuff为物品
													if(oname.equals(this.name)) {
														
														te = new TileEntityChangeBlock(
															this.getStateFromMeta(meta),
															this.getStateFromMeta(ometa),
															new ItemStack(Item.getByNameOrId(cname), camout, cmeta),
															tick,
															s,
															m,
															drop
														);
													}
												}
											}else {
												if(worldIn.isRemote) {
													MCS.instance.log.fatal("\"" + oname +  "\": "+ "\"" + ometa + "\"" + " It's too large! It must be >=15");
												}
											}
										}catch(Exception e){
											if(worldIn.isRemote) {
												MCS.instance.log.fatal("\"" + oname +  "\": "+ e.getMessage() + " is not Number!");
											}
										}
									}else {
										if(worldIn.isRemote) {
											MCS.instance.log.fatal("\"" + oname +  "\": "+ "\"" + oname + "\"" + " is not belong to MCS's Block!");
										}
									}
								} catch (Exception e) {
									if(worldIn.isRemote) {
										MCS.instance.log.fatal(oname + ": \"" + e.getMessage() + "\" is not Number or Boolean!");
									}
								}
							}else {
								if(worldIn.isRemote) {
									MCS.instance.log.fatal("\"" + strri[0] +  "\": "+ " it have " + strri.length + " values, it must be 9 values!");
								}
							}
						}
					}
				}catch (ArrayIndexOutOfBoundsException e) {
					e.fillInStackTrace();
					if(worldIn.isRemote) {
						MCS.instance.log.fatal((new Integer(e.getMessage()) - 1) + " is not multiple of 9!");
					}
				}
			}
			return te;
		}
	}
	
	public int getUnCompressedBurnTime() {
		return ForgeEventFactory.getItemBurnTime(this.unCompressedItem);
	}
	
	public boolean canSetBurnTime() {
		return this.getUnCompressedBurnTime() > 0;
	}
	
	List<String[]> infos = new ArrayList<String[]>();
	
	public BaseBlockSub addCustemInformation(String[]... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced){
		int meta = stack.getMetadata();
		int level = meta + 1;
		
		if(Configs.use_3x3_recipes) {
			if(Configs.tooltip_information.show_specific_number) {
				if(!Configs.tooltip_information.can_custom_specific_number) {
					if(meta == 0) { tooltip.add("9 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 1) { tooltip.add("81 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 2) { tooltip.add("729 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 3) { tooltip.add("6,561 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 4) { tooltip.add("59,049 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 5) { tooltip.add("531,441 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 6) { tooltip.add("4,782,969 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 7) { tooltip.add("43,046,721 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 8) { tooltip.add("387,420,489 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 9) { tooltip.add("3,486,784,401 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 10) { tooltip.add("31,381,059,609 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 11) { tooltip.add("282,429,536,481 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 12) { tooltip.add("2,541,865,828,329 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 13) { tooltip.add("22,876,792,454,961 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 14) { tooltip.add("205,891,132,094,649 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 15) { tooltip.add("1,853,020,188,851,841 x " + this.getUnCompressedItemLocalizedName()); }
				}else {
					tooltip.add(I18n.format("info.compressed_3x3_" + level + ".name", 1) + " x " + this.getUnCompressedItemLocalizedName());
				}
			}else {
				tooltip.add(Math.pow(9, level) + " x " + this.getUnCompressedItemLocalizedName());
			}
		}else {
			if(Configs.tooltip_information.show_specific_number) {
				if(!Configs.tooltip_information.can_custom_specific_number) {
					if(meta == 0) { tooltip.add("4 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 1) { tooltip.add("16 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 2) { tooltip.add("64 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 3) { tooltip.add("256 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 4) { tooltip.add("1,024 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 5) { tooltip.add("4,096 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 6) { tooltip.add("16,384 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 7) { tooltip.add("65,536 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 8) { tooltip.add("262,144 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 9) { tooltip.add("1,048,876 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 10) { tooltip.add("4,194,304 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 11) { tooltip.add("16,777,216 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 12) { tooltip.add("67,108,864 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 13) { tooltip.add("268,435,456 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 14) { tooltip.add("1,073,741,824 x " + this.getUnCompressedItemLocalizedName()); }
					if(meta == 15) { tooltip.add("4,294,967,296 x " + this.getUnCompressedItemLocalizedName()); }
				}else {
					tooltip.add(I18n.format("info.compressed_2x2_" + level + ".name", 1) + " x " + this.getUnCompressedItemLocalizedName());
				}
			}else {
				tooltip.add(Math.pow(4, level) + " x " + this.getUnCompressedItemLocalizedName());
			}
		}
		
		if(MCS.instance.test_model) {
			tooltip.add("unCompressedItem: " + this.unCompressedItem.getItem().getRegistryName() + "." + this.unCompressedItem.getItemDamage());
			
			String[] names = JiuUtils.other.custemSplitString(this.name, "_");
			tooltip.add(names.length + "");
			tooltip.add(this.getUnCompressedName());
		}
		
		
		if(Configs.tooltip_information.show_owner_mod) {
			tooltip.add("Owner Mod: \'" + this.getOwnerMod() + "\'");
		}
		
		if(Configs.tooltip_information.show_burn_time) {
			tooltip.add("BurnTime: " + ForgeEventFactory.getItemBurnTime(stack));
		}
		
		if(Configs.tooltip_information.show_oredict) {
			tooltip.add("OreDictionary: ");
			for(String ore : JiuUtils.item.getOreDict(stack)){
				tooltip.add("> " + ore);
			}
		}
		
		if(!this.infos.isEmpty()) {
			for(String[] info : this.infos) {
				for(String str : info) {
					tooltip.add(str);
				}
			}
		}
		
		this.unCompressedItem.getItem().addInformation(this.unCompressedItem, world, tooltip, advanced);
		
		if(Configs.tooltip_information.custem_info.block.length != 1) {
			try {
				for(int i = 1; i < Configs.tooltip_information.custem_info.block.length; ++i) {
					String str0  = Configs.tooltip_information.custem_info.block[i].trim();
					
					String[] strri = JiuUtils.other.custemSplitString(str0, "#");
					
					if(MCSBlocks.SUB_BLOCKS_NAME.contains(strri[0])) {
						if(this.name.equals(strri[0])) {
							try {
								int strmeta = new Integer(strri[1]);
								
								if(strmeta == meta) {
									String[] str1 = JiuUtils.other.custemSplitString(strri[2], "|");
									
									for(int k = 0; k < str1.length; ++k) {
										tooltip.add(str1[k]);
										
										if(k == str1.length - 1) {
											break;
										}
									}
								}
							} catch (Exception e) {
								MCS.instance.log.fatal("\"" + strri[0] +  "\": "+ "\"" + strri[1] + "\"" + " is not Number!");
							}
						}
					}else {
						MCS.instance.log.fatal("\"" + strri[0] +  "\"" + " is not belong to MCS's Block!");
					}
				}
			} catch (Exception e) {
				MCS.instance.log.fatal("Block: " + (new Integer(e.getMessage()) - 1) + " is not multiple of 3!");
			}
		}
		
		if(Loader.isModLoaded("projecte")) {
//			tooltip.add("EMC: " + EMCHelper.getEmcValue(stack));
		}
	}

	@Override
	public void registerItemModel() {
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			
			if(this.unCompressedItem.getItem() instanceof ItemBlock) {
				model.registerItemModel(this, meta, this.langModID + "/block/normal/" + this.name, this.name + "." + meta);
			}else {
				model.registerItemModel(this, meta, this.langModID + "/block/has/" + this.name, this.name + "." + meta);
			}
		}
	}
	
	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon) {
		return this.isBlock ? this.unCompressedBlock.isBeaconBase(worldObj, pos, beacon) : super.isBeaconBase(worldObj, pos, beacon);
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return this.isBlock ? this.unCompressedBlock.isNormalCube(this.unCompressedState, world, pos) : super.isNormalCube(state, world, pos);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return this.isBlock ? this.unCompressedBlock.getLightValue(this.unCompressedState, world, pos) : super.getLightValue(state, world, pos);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return this.isBlock ? this.unCompressedBlock.getWeakPower(this.unCompressedState, world, pos, side) : super.getWeakPower(state, world, pos, side);
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return this.isBlock ? this.unCompressedBlock.getExplosionResistance(world, pos, exploder, explosion) : super.getExplosionResistance(world, pos, exploder, explosion);
	}
	
	@Override
	public String getName(ItemStack stack) {
		return ModSubtypes.values()[stack.getMetadata()].getName();
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(VARIANT).getMeta();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(VARIANT).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, ModSubtypes.byMetadata(meta));
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(this.getHasSubtypes()){
			if(this.isInCreativeTab(tab)){
				for(ModSubtypes type : ModSubtypes.values()) {
					items.add(new ItemStack(this, 1, type.getMeta()));
				}
			}
		}else{
			super.getSubBlocks(tab, items);
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}
}
