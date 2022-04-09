package cat.jiu.mcs.util.base.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.api.IHasModel;
import cat.jiu.mcs.api.IMetaName;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.config.Configs;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.base.BaseBlock;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSResources;
import cat.jiu.mcs.util.type.CustomStuffType;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseBlockSub extends BaseBlock implements IHasModel, IMetaName, ITileEntityProvider, ICompressedStuff {
	protected final boolean recipeIsRepeat;
	protected Block unCompressedBlock;
	protected IBlockState unCompressedState;
	protected final String langModID;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);

	public static final PropertyEnum<ModSubtypes> VARIANT = PropertyEnum.create("level", ModSubtypes.class);

	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem, String ownerModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		// if(unCompressedItem == null || unCompressedItem.isEmpty()) {
		// MCS.instance.log.error(ownerModID + " -> " + nameIn + ": " + unCompressedItem.toString());
		// return null;
		// }
		if(Loader.isModLoaded(ownerModID) || ownerModID.equals("custom")) {
			return new BaseBlockSub(nameIn, unCompressedItem, ownerModID, materialIn, soundIn, tabIn, hardnessIn);
		}else {
			return null;
		}
	}

	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem, String langModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, langModID, materialIn, soundIn, tabIn, 4.0F);
	}

	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem, String langModID, Material materialIn, CreativeTabs tabIn) {
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
	 * @param langModID
	 *            the unCompressedItem owner modid, it use to the model
	 * 
	 * @author small_jiu
	 */
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem, String langModID) {
		return register(nameIn, unCompressedItem, langModID, MCS.COMPERESSED_BLOCKS);
	}

	/**
	 * This method is not recommended. because the mod id maybe was change.
	 * 
	 * @author small_jiu
	 */
	public static BaseBlockSub register(String nameIn, ItemStack unCompressedItem) {
		return register(nameIn, unCompressedItem, unCompressedItem.getItem().getRegistryName().getResourceDomain());
	}

	public static BaseBlockSub register(ItemStack unCompressedItem, String langModID) {
		return register("compressed_" + unCompressedItem.getItem().getRegistryName().getResourcePath(), unCompressedItem, langModID);
	}

	public static BaseBlockSub register(ItemStack unCompressedItem) {
		return register("compressed_" + unCompressedItem.getItem().getRegistryName().getResourcePath(), unCompressedItem);
	}

	public BaseBlockSub(String nameIn, @Nonnull ItemStack unCompressedItem, String langModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		super(nameIn, unCompressedItem, materialIn, soundIn, tabIn, hardnessIn, true);
		this.recipeIsRepeat = this.checkRecipe(unCompressedItem);
		this.langModID = langModID;
		this.unCompressedBlock = this.isHasBlock() ? JiuUtils.item.getBlockFromItemStack(unCompressedItem) : Blocks.AIR;
		this.unCompressedState = this.isHasBlock() ? JiuUtils.item.getStateFromItemStack(unCompressedItem) : Blocks.AIR.getDefaultState();
		if(!langModID.equals("custom")) {
			MCSResources.SUB_BLOCKS_NAME.add(nameIn);
			MCSResources.SUB_BLOCKS.add(this);
			MCSResources.SUB_BLOCKS_MAP.put(nameIn, this);
		}
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, ModSubtypes.LEVEL_1));
	}

	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, langModID, materialIn, soundIn, tabIn, 4.0F);
	}

	public BaseBlockSub(String nameIn, ItemStack unCompressedItem, String langModID, Material materialIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, langModID, materialIn, SoundType.METAL, tabIn);
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

	public BaseBlockSub(ItemStack unCompressedItem, String langModID) {
		this("compressed_" + unCompressedItem.getItem().getRegistryName().getResourcePath(), unCompressedItem, langModID, MCS.COMPERESSED_BLOCKS);
	}

	/**
	 * default is minecraft owner unCompressedItem
	 */
	public BaseBlockSub(String nameIn, ItemStack unCompressedItem) {
		this(nameIn, unCompressedItem, "minecraft");
	}

	public BaseBlockSub setUnCompressed(ItemStack stack) {
		super.setUnCompressed(stack);
		if(this.unCompressedBlock == null || this.unCompressedBlock == Blocks.AIR) {
			this.unCompressedBlock = JiuUtils.item.getBlockFromItemStack(stack);
			this.unCompressedState = JiuUtils.item.getStateFromItemStack(stack);
			if(this.unCompressedBlock == null || this.unCompressedBlock == Blocks.AIR) {
				throw new RuntimeException(this.name + ": unCompressedItem can NOT be EMPTY!");
			}
		}
		return this;
	}

	private final List<String> otherOredict = ICompressedStuff.super.addOtherOreDictionary();

	public BaseBlockSub addOtherOreDict(String oredict) {
		this.otherOredict.add(oredict);
		return this;
	}

	@Override
	public List<String> addOtherOreDictionary() {
		return this.otherOredict;
	}

	public String getUnCompressedName() {
		String[] unNames = JiuUtils.other.custemSplitString(this.name, "_");
		StringBuffer i = new StringBuffer();
		for(String s : unNames) {
			if(!"compressed".equals(s)) {
				i.append(JiuUtils.other.upperCaseToFirstLetter(s));
			}
		}
		return i.toString();
	}

	boolean createOredict = true;

	public BaseBlockSub createOreDictionary(boolean flag) {
		this.createOredict = flag;
		return this;
	}

	@Override
	public boolean createOreDictionary() {
		return this.createOredict;
	}

	public boolean isHasBlock() {
		return !JiuUtils.item.isBlock(this.unCompressedItem);
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

	boolean isTransparentCube = false;

	public BaseBlockSub setIsTransparentCube() {
		this.isTransparentCube = true;
		return this;
	}

	public final String getOredict(int meta) {
		return JiuUtils.item.getOreDict(new ItemStack(this, 1, meta)).get(0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	public PropertyEnum<ModSubtypes> getPropertyEnum() {
		return VARIANT;
	}

	public Map<Integer, CustomStuffType.ChangeBlockType> entrys = Maps.newHashMap();

	public BaseBlockSub addChangeBlock(int meta, int tick, int s, int m, boolean canDrops, ItemStack... drops) {
		return this.addChangeBlock(meta, new int[]{tick, s, m}, canDrops, drops);
	}

	public BaseBlockSub addChangeBlock(int meta, int[] time, boolean canDrops, ItemStack... drops) {
		this.entrys.put(meta, new CustomStuffType.ChangeBlockType(drops, time, canDrops));
		return this;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(!this.entrys.isEmpty()) {
			return new TileEntityChangeBlock(meta, entrys);
		}else {
			if(MCSBlocks.CHANGE_MCS_BLOCK_MAP.containsKey(this.name)) {
				if(MCSBlocks.CHANGE_MCS_BLOCK_MAP.get(this.name).containsKey(meta)) {
					return new TileEntityChangeBlock(meta, MCSBlocks.CHANGE_MCS_BLOCK_MAP.get(this.name));
				}
			}

			return null;
		}
	}

	public boolean canSetBurnTime() {
		if(this.unCompressedItem != null) {
			return this.getUnCompressedBurnTime() > 0;
		}else {
			return false;
		}
	}

	private List<String> shiftInfos = new ArrayList<String>();

	public BaseBlockSub addCustemShiftInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			shiftInfos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseBlockSub addCustemShiftInformation(List<String> infos) {
		this.shiftInfos = infos;
		return this;
	}

	private Map<Integer, List<String>> metaShiftInfos = Maps.newHashMap();

	public BaseBlockSub addCustemShiftInformation(Map<Integer, List<String>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}

	private List<String> infos = new ArrayList<String>();

	public BaseBlockSub addCustemInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseBlockSub addCustemInformation(List<String> infos) {
		this.infos = infos;
		return this;
	}

	private Map<Integer, List<String>> metaInfos = Maps.newHashMap();

	public BaseBlockSub addCustemInformation(Map<Integer, List<String>> infos) {
		this.metaInfos = infos;
		return this;
	}

	ItemStack infoStack = null;

	public BaseBlockSub setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}

	private Map<Integer, ItemStack> infoStacks = null;

	public BaseBlockSub setInfoStack(Map<Integer, ItemStack> infoStacks) {
		this.infoStacks = infoStacks;
		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();
		MCSUtil.info.addInfoStackInfo(meta, this.infoStack, world, tooltip, advanced, this.unCompressedItem, infoStacks);
		MCSUtil.info.addCompressedInfo(meta, tooltip, this.getUnCompressedItemLocalizedName(), Item.getItemFromBlock(this));

		if(MCS.instance.test_model) {
			NBTTagCompound nbt = stack.getTagCompound();
			tooltip.add("unCompressedItem: " + this.unCompressedItem.getItem().getRegistryName() + "." + this.unCompressedItem.getItemDamage());

			if(nbt != null) {
				tooltip.add("ChangeTime: " + nbt.getInteger("ChangeM") + "/" + nbt.getInteger("ChangeS") + "/" + nbt.getInteger("ChangeTick"));
				tooltip.add(nbt.toString());
			}

			String[] names = JiuUtils.other.custemSplitString(this.name, "_");
			tooltip.add(names.length + "");
			tooltip.add(this.getUnCompressedName());
			tooltip.add("BaseBurnTime: " + this.getUnCompressedBurnTime());
		}

		if(Configs.Tooltip_Information.show_owner_mod) {
			tooltip.add(I18n.format("info.mcs.owner_mod") + " : " + TextFormatting.AQUA.toString() + this.getOwnerMod());
		}

		if(Configs.Tooltip_Information.show_burn_time) {
			if(this.getUnCompressedBurnTime() > 0) {
				tooltip.add(I18n.format("info.mcs.burn_time") + ": " + ForgeEventFactory.getItemBurnTime(stack));
			}
		}

		if(Configs.Tooltip_Information.show_oredict) {
			List<String> ores = JiuUtils.item.getOreDict(stack);
			if(!ores.isEmpty()) {
				tooltip.add(I18n.format("info.mcs.oredict") + ": ");
				for(String ore : ores) {
					tooltip.add("> " + ore);
				}
			}
		}

		MCSUtil.info.addMetaInfo(meta, tooltip, this.infos, this.metaInfos);
		MCSUtil.info.addShiftInfo(meta, tooltip, this.shiftInfos, this.metaShiftInfos);
	}

	String state = null;

	public BaseBlockSub setModelState(String state) {
		this.state = state;
		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerItemModel() {
		String has = this.unCompressedItem.getItem() instanceof ItemBlock ? "normal" : "has";
		if(this.state != null)
			has = this.state;

		model.setBlockStateMapper(this, false, this.langModID + "/" + has + "/" + this.name);
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			model.registerItemModel(this, meta, this.langModID + "/block/" + has + "/" + this.name, this.name + "." + meta);
		}
	}

	private Map<Integer, HarvestType> HarvestMap = Maps.newHashMap();

	public BaseBlockSub setHarvestMap(Map<Integer, HarvestType> map) {
		this.HarvestMap = map;
		return this;
	}

	public BaseBlockSub addHarvestMap(int meta, String tool, int level) {
		this.HarvestMap.put(meta, new HarvestType(tool, level));
		return this;
	}

	@Override
	public String getHarvestTool(IBlockState state) {
		if(!HarvestMap.isEmpty()) {
			if(HarvestMap.containsKey(JiuUtils.item.getMetaFormBlockState(state))) {
				return HarvestMap.get(JiuUtils.item.getMetaFormBlockState(state)).getHarvestTool();
			}else if(!this.isHasBlock()) {
				return this.getUnCompressedBlock().getHarvestTool(this.getUnCompressedState());
			}
		}else if(!this.isHasBlock()) {
			return this.getUnCompressedBlock().getHarvestTool(this.getUnCompressedState());
		}
		return super.getHarvestTool(state);
	}

	private boolean canThroughBlock = false;

	public BaseBlockSub setCanThroughBlock() {
		this.canThroughBlock = true;
		return this;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		if(this.canThroughBlock) {
			return true;
		}
		return false;
	}

	private boolean noToolBreak = false;

	public BaseBlockSub setNoToolBreak() {
		this.noToolBreak = true;
		return this;
	}

	@Override
	public int getHarvestLevel(IBlockState state) {
		if(this.noToolBreak) {
			return 0;
		}
		if(!HarvestMap.isEmpty()) {
			if(HarvestMap.containsKey(JiuUtils.item.getMetaFormBlockState(state))) {
				return HarvestMap.get(JiuUtils.item.getMetaFormBlockState(state)).getHarvestLevel();
			}else if(!this.isHasBlock()) {
				return this.getUnCompressedBlock().getHarvestLevel(this.getUnCompressedState());
			}
		}else if(!this.isHasBlock()) {
			return this.getUnCompressedBlock().getHarvestLevel(this.getUnCompressedState());
		}
		return super.getHarvestLevel(state);
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		if(this.noToolBreak)
			return true;
		return super.canHarvestBlock(world, pos, player);
	}

	public static class HarvestType {
		private final int level;
		private final String tool;

		public HarvestType(String tool, int level) {
			this.level = level;
			this.tool = tool;
		}

		public int getHarvestLevel() {
			return level;
		}

		public String getHarvestTool() {
			return tool;
		}

	}

	private Map<Integer, Float> HardnessMap = Maps.newHashMap();

	public BaseBlockSub setHardnessMap(Map<Integer, Float> map) {
		this.HardnessMap = map;
		return this;
	}

	public BaseBlockSub addHardnessMap(int meta, float hardness) {
		this.HardnessMap.put(meta, hardness);
		return this;
	}

	@SuppressWarnings("deprecation")
	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
		if(HardnessMap != null && !HardnessMap.isEmpty()) {
			if(HardnessMap.containsKey(JiuUtils.item.getMetaFormBlockState(world.getBlockState(pos)))) {
				return HardnessMap.get(JiuUtils.item.getMetaFormBlockState(world.getBlockState(pos)));
			}else if(!this.isHasBlock()) {
				return this.getUnCompressedBlock().getBlockHardness(this.getUnCompressedState(), world, pos);
			}
		}else if(!this.isHasBlock()) {
			return this.getUnCompressedBlock().getBlockHardness(this.getUnCompressedState(), world, pos);
		}
		return super.getBlockHardness(blockState, world, pos);
	}

	private Map<Integer, Boolean> BeaconBaseMap = Maps.newHashMap();

	public BaseBlockSub setBeaconBaseMap(Map<Integer, Boolean> map) {
		this.BeaconBaseMap = map;
		return this;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon) {
		if(!BeaconBaseMap.isEmpty()) {
			if(BeaconBaseMap.containsKey(JiuUtils.item.getMetaFormBlockState(world.getBlockState(pos)))) {
				return BeaconBaseMap.get(JiuUtils.item.getMetaFormBlockState(world.getBlockState(pos)));
			}else if(!this.isHasBlock()) {
				return this.getUnCompressedBlock().isBeaconBase(world, pos, beacon);
			}
		}else if(!this.isHasBlock()) {
			return this.getUnCompressedBlock().isBeaconBase(world, pos, beacon);
		}

		return super.isBeaconBase(world, pos, beacon);
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return this.isHasBlock() ? this.unCompressedBlock.isNormalCube(this.unCompressedState, world, pos) : super.isNormalCube(state, world, pos);
	}

	private Map<Integer, Integer> LightValueMap = Maps.newHashMap();

	public BaseBlockSub setLightValueMap(Map<Integer, Integer> map) {
		this.LightValueMap = map;
		return this;
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if(!LightValueMap.isEmpty()) {
			if(LightValueMap.containsKey(JiuUtils.item.getMetaFormBlockState(state))) {
				return LightValueMap.get(JiuUtils.item.getMetaFormBlockState(state));
			}else if(!this.isHasBlock()) {
				return this.getUnCompressedBlock().getLightValue(this.getUnCompressedState(), world, pos);
			}
		}else if(!this.isHasBlock()) {
			return this.getUnCompressedBlock().getLightValue(this.getUnCompressedState(), world, pos);
		}

		return super.getLightValue(state, world, pos);
	}

	// public HashMap<Integer, Integer> WeakPowerMap = Maps.newHashMap();
	//
	// @SuppressWarnings("deprecation")
	// @Override
	// public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
	//// return JiuUtils.item.getMetaFormBlockState(state);
	// if(!WeakPowerMap.isEmpty()) {
	// if(WeakPowerMap.containsKey(JiuUtils.item.getMetaFormBlockState(state))) {
	// return WeakPowerMap.get(JiuUtils.item.getMetaFormBlockState(state));
	// }else if(this.isHasBlock()) {
	// return this.unCompressedBlock.getWeakPower(this.unCompressedState, world, pos, side);
	// }
	// }else if(this.isHasBlock()) {
	// return this.unCompressedBlock.getWeakPower(this.unCompressedState, world, pos, side);
	// }
	// return super.getWeakPower(state, world, pos, side);
	//
	// }

	private Map<Integer, Float> ExplosionResistanceMap = Maps.newHashMap();

	public BaseBlockSub setExplosionResistanceMap(Map<Integer, Float> map) {
		this.ExplosionResistanceMap = map;
		return this;
	}

	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		if(!ExplosionResistanceMap.isEmpty()) {
			if(ExplosionResistanceMap.containsKey(JiuUtils.item.getMetaFormBlockState(world.getBlockState(pos)))) {
				return ExplosionResistanceMap.get(JiuUtils.item.getMetaFormBlockState(world.getBlockState(pos)));
			}else if(!this.isHasBlock()) {
				return this.getUnCompressedBlock().getExplosionResistance(world, pos, exploder, explosion);
			}
		}else if(!this.isHasBlock()) {
			return this.getUnCompressedBlock().getExplosionResistance(world, pos, exploder, explosion);
		}
		return super.getExplosionResistance(world, pos, exploder, explosion);
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
		if(this.getHasSubtypes()) {
			for(ModSubtypes type : ModSubtypes.values()) {
				items.add(new ItemStack(this, 1, type.getMeta()));
			}
		}else {
			super.getSubBlocks(tab, items);
		}
	}

	public IBlockState getUnCompressedState() {
		return JiuUtils.item.getStateFromItemStack(this.unCompressedItem);
	}

	public Block getUnCompressedBlock() {
		return JiuUtils.item.getBlockFromItemStack(this.unCompressedItem);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, VARIANT);
	}

	public final ItemStack Level_1 = new ItemStack(this, 1, 0);
	public final ItemStack Level_2 = new ItemStack(this, 1, 1);
	public final ItemStack Level_3 = new ItemStack(this, 1, 2);
	public final ItemStack Level_4 = new ItemStack(this, 1, 3);
	public final ItemStack Level_5 = new ItemStack(this, 1, 4);
	public final ItemStack Level_6 = new ItemStack(this, 1, 5);
	public final ItemStack Level_7 = new ItemStack(this, 1, 6);
	public final ItemStack Level_8 = new ItemStack(this, 1, 7);
	public final ItemStack Level_9 = new ItemStack(this, 1, 8);
	public final ItemStack Level_10 = new ItemStack(this, 1, 9);
	public final ItemStack Level_11 = new ItemStack(this, 1, 10);
	public final ItemStack Level_12 = new ItemStack(this, 1, 11);
	public final ItemStack Level_13 = new ItemStack(this, 1, 12);
	public final ItemStack Level_14 = new ItemStack(this, 1, 13);
	public final ItemStack Level_15 = new ItemStack(this, 1, 14);
	public final ItemStack Level_16 = new ItemStack(this, 1, 15);
}
