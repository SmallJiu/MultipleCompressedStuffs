package cat.jiu.mcs.util.base.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import cat.jiu.core.api.ITime;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.Time;
import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.CompressedLevel;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.base.BaseBlockItem;
import cat.jiu.mcs.util.base.sub.BaseBlockSub.HarvestType;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSResources;
import cat.jiu.mcs.util.type.CustomStuffType;

import cofh.api.item.IToolHammer;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class BaseBlockSubT extends BaseBlock.Sub<ModSubtypes> implements ICompressedStuff, ITileEntityProvider {

	protected final boolean recipeIsRepeat;
	protected Block baseBlock;
	protected IBlockState baseState;
	protected final String ownerMod;
	protected ItemStack baseItem;
	
	public BaseBlockSubT(String nameIn, @Nonnull ItemStack baseItem, String ownerMod,  Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		super("mcs", nameIn, materialIn, soundIn, tabIn, hardnessIn);
		this.baseItem = baseItem;
		this.recipeIsRepeat = this.checkRecipe(baseItem);
		this.ownerMod = ownerMod;
		this.baseBlock = !this.isHas() ? JiuUtils.item.getBlockFromItemStack(baseItem) : Blocks.AIR;
		this.baseState = !this.isHas() ? JiuUtils.item.getStateFromItemStack(baseItem) : Blocks.AIR.getDefaultState();
		if(!ownerMod.equals("custom")) {
			MCSResources.BLOCKS.add(this);
			MCSResources.BLOCKS_NAME.add(this.name);
			MCSResources.STUFF_NAME.add(nameIn);
			MCSResources.putCompressedStuff(baseItem, this);
		}
		
		if(baseItem.getItem() instanceof ItemBlock) {
			Block unBlock = JiuUtils.item.getBlockFromItemStack(baseItem);
			IBlockState unState = JiuUtils.item.getStateFromItemStack(baseItem);

			if(this.getBlockHardness(unState, null, null) > 10F) {
				this.setHarvestLevel("pickaxe", 3);
			}
			this.setLightLevel(unState.getLightValue());
			this.setHardness(unBlock.getBlockHardness(unState, null, null));
			this.setSoundType(unBlock.getSoundType());
		}
		
		if(nameIn.equalsIgnoreCase(ownerMod)) {
			throw new RuntimeException("name must not be owner mod. Name: " + nameIn + ", OwnerMod: " + ownerMod);
		}else if(!baseItem.isEmpty()) {
			ResourceLocation regName = baseItem.getItem().getRegistryName();
			if(regName != null && nameIn.equalsIgnoreCase(regName.getResourceDomain())) {
				throw new RuntimeException("name must not be owner mod. Name: " + nameIn + ", OwnerMod: " + baseItem.getItem().getRegistryName().getResourceDomain());
			}
		}
	}
	
	public BaseBlockSubT(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, ownerMod, materialIn, soundIn, tabIn, 4.0F);
	}

	public BaseBlockSubT(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, Material materialIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, ownerMod, materialIn, SoundType.METAL, tabIn);
	}

	public BaseBlockSubT(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, ownerMod, Material.IRON, soundIn, tabIn);
	}

	public BaseBlockSubT(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, CreativeTabs tabIn, float hardnessIn) {
		this(nameIn, unCompressedItem, ownerMod, Material.IRON, SoundType.METAL, tabIn, hardnessIn);
	}

	public BaseBlockSubT(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, float hardnessIn) {
		this(nameIn, unCompressedItem, ownerMod, MCS.COMPERESSED_BLOCKS, hardnessIn);
	}

	public BaseBlockSubT(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, ownerMod, Material.IRON, tabIn);
	}

	public BaseBlockSubT(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod) {
		this(nameIn, unCompressedItem, ownerMod, MCS.COMPERESSED_BLOCKS);
	}

	public BaseBlockSubT(@Nonnull ItemStack unCompressedItem, String ownerMod) {
		this("compressed_" + unCompressedItem.getItem().getRegistryName().getResourcePath(), unCompressedItem, ownerMod);
	}

	public BaseBlockSubT(String nameIn, @Nonnull ItemStack unCompressedItem) {
		this(nameIn, unCompressedItem, unCompressedItem.getItem().getRegistryName().getResourceDomain());
	}

	public BaseBlockSubT(@Nonnull ItemStack unCompressedItem) {
		this(unCompressedItem, unCompressedItem.getItem().getRegistryName().getResourceDomain());
	}
	
	public boolean checkRecipe(ItemStack unCompressedItem) {
		return false;
	}
	
	public BaseBlockSubT setUnCompressed(ItemStack stack) {
		if(stack == null || stack.isEmpty()) {
			throw new RuntimeException(this.name + ": unCompressedItem can NOT be EMPTY!");
		}
		if(this.baseItem == null || this.baseItem.isEmpty()) {
			this.baseItem = stack;
			if(this.baseItem == null || this.baseItem.isEmpty()) {
				throw new RuntimeException(this.name + ": unCompressedItem can NOT be EMPTY!");
			}
		}
		if(this.baseBlock == null || this.baseBlock == Blocks.AIR) {
			this.baseBlock = this.getBaseBlock();
			this.baseState = this.getBaseState();
			if(this.baseBlock == null || this.baseBlock == Blocks.AIR) {
				throw new RuntimeException(this.name + ": unCompressedItem can NOT be EMPTY!");
			}
		}
		return this;
	}
	// Custom start
	
	private Map<Integer, Boolean> canUseWrenchBreaks = null;
	public BaseBlockSubT canUseWrenchBreak(Map<Integer, Boolean> canbe) {
		if(Loader.isModLoaded("thermalfoundation") || Loader.isModLoaded("buildcraftcore") || Loader.isModLoaded("enderio")) {
			this.canUseWrenchBreaks = canbe;
		}
		return this;
	}
	
	private Map<Integer, CustomStuffType.ChangeBlockType> entrys = Maps.newHashMap();
	public BaseBlockSubT setChangeBlock(int meta, int tick, int s, int m, boolean canDrops, ItemStack... drops) {
		return this.addChangeBlock(meta, new Time(m, s, tick), canDrops, drops);
	}
	
	private Map<Integer, List<String>> metaShiftInfos = Maps.newHashMap();
	public BaseBlockSubT setCustemShiftInformation(Map<Integer, List<String>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}

	private Map<Integer, List<String>> metaInfos = Maps.newHashMap();
	public BaseBlockSubT setCustemInformation(Map<Integer, List<String>> infos) {
		this.metaInfos = infos;
		return this;
	}
	
	private Map<Integer, Float> HardnessMap = Maps.newHashMap();
	public BaseBlockSubT setHardnessMap(Map<Integer, Float> map) {
		this.HardnessMap = map;
		return this;
	}
	public BaseBlockSubT addHardnessMap(int meta, float hardness) {
		this.HardnessMap.put(meta, hardness);
		return this;
	}
	
	private Map<Integer, HarvestType> HarvestMap = Maps.newHashMap();
	public BaseBlockSubT setHarvestMap(Map<Integer, HarvestType> map) {
		this.HarvestMap = map;
		return this;
	}
	public BaseBlockSubT addHarvestMap(int meta, String tool, int level) {
		this.HarvestMap.put(meta, new HarvestType(tool, level));
		return this;
	}

	private Map<Integer, Boolean> BeaconBaseMap = Maps.newHashMap();
	public BaseBlockSubT setBeaconBaseMap(Map<Integer, Boolean> map) {
		this.BeaconBaseMap = map;
		return this;
	}

	private Map<Integer, Integer> LightValueMap = Maps.newHashMap();
	public BaseBlockSubT setLightValueMap(Map<Integer, Integer> map) {
		this.LightValueMap = map;
		return this;
	}

	private Map<Integer, Float> ExplosionResistanceMap = Maps.newHashMap();
	public BaseBlockSubT setExplosionResistanceMap(Map<Integer, Float> map) {
		this.ExplosionResistanceMap = map;
		return this;
	}
	// Custom end
	
	private boolean canThroughBlock = false;
	public BaseBlockSubT setCanThroughBlock() {
		this.canThroughBlock = true;
		return this;
	}

	private boolean noToolBreak = false;
	public BaseBlockSubT setNotNeedToolBreak() {
		this.noToolBreak = true;
		return this;
	}
	
	private List<String> shiftInfos = new ArrayList<String>();
	public BaseBlockSubT addCustemShiftInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			shiftInfos.add(custemInfo[i]);
		}
		return this;
	}

	public BaseBlockSubT addCustemShiftInformation(List<String> infos) {
		this.shiftInfos = infos;
		return this;
	}

	private List<String> infos = new ArrayList<String>();
	public BaseBlockSubT setCustemInformation(List<String> infos) {
		this.infos = infos;
		return this;
	}
	public BaseBlockSubT addCustemInformation(String... custemInfo) {
		for(int i = 0; i < custemInfo.length; ++i) {
			infos.add(custemInfo[i]);
		}
		return this;
	}

	ItemStack infoStack = null;
	public BaseBlockSubT setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}

	private Map<Integer, ItemStack> infoStacks = null;
	public BaseBlockSubT setInfoStack(Map<Integer, ItemStack> infoStacks) {
		this.infoStacks = infoStacks;
		return this;
	}
	
	// Custom imp start

	@Override
	public String getHarvestTool(IBlockState state) {
		if(this.noToolBreak) return null;
		if(!HarvestMap.isEmpty()) {
			if(HarvestMap.containsKey(JiuUtils.item.getMetaFromBlockState(state))) {
				return HarvestMap.get(JiuUtils.item.getMetaFromBlockState(state)).tool;
			}else if(!this.isHas()) {
				return this.getBaseBlock().getHarvestTool(this.getBaseState());
			}
		}else if(!this.isHas()) {
			return this.getBaseBlock().getHarvestTool(this.getBaseState());
		}
		return super.getHarvestTool(state);
	}

	@Override
	public int getHarvestLevel(IBlockState state) {
		if(this.noToolBreak) return 0;
		
		if(!HarvestMap.isEmpty()) {
			if(HarvestMap.containsKey(JiuUtils.item.getMetaFromBlockState(state))) {
				return HarvestMap.get(JiuUtils.item.getMetaFromBlockState(state)).level;
			}else if(!this.isHas()) {
				return this.getBaseBlock().getHarvestLevel(this.getBaseState());
			}
		}else if(!this.isHas()) {
			return this.getBaseBlock().getHarvestLevel(this.getBaseState());
		}
		return super.getHarvestLevel(state);
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
		if(this.noToolBreak) return 0F;
		if(HardnessMap != null && !HardnessMap.isEmpty()) {
			if(HardnessMap.containsKey(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)))) {
				return HardnessMap.get(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)));
			}else if(!this.isHas()) {
				return this.getBaseBlock().getBlockHardness(this.getBaseState(), world, pos);
			}
		}else if(!this.isHas()) {
			return this.getBaseBlock().getBlockHardness(this.getBaseState(), world, pos);
		}
		return super.getBlockHardness(blockState, world, pos);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon) {
		if(!BeaconBaseMap.isEmpty()) {
			if(BeaconBaseMap.containsKey(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)))) {
				return BeaconBaseMap.get(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)));
			}else if(!this.isHas()) {
				return this.getBaseBlock().isBeaconBase(world, pos, beacon);
			}
		}else if(!this.isHas()) {
			return this.getBaseBlock().isBeaconBase(world, pos, beacon);
		}

		return super.isBeaconBase(world, pos, beacon);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if(!LightValueMap.isEmpty()) {
			if(LightValueMap.containsKey(JiuUtils.item.getMetaFromBlockState(state))) {
				return LightValueMap.get(JiuUtils.item.getMetaFromBlockState(state));
			}else if(!this.isHas()) {
				return this.getBaseBlock().getLightValue(this.getBaseState(), world, pos);
			}
		}else if(!this.isHas()) {
			return this.getBaseBlock().getLightValue(this.getBaseState(), world, pos);
		}

		return super.getLightValue(state, world, pos);
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		if(!ExplosionResistanceMap.isEmpty()) {
			if(ExplosionResistanceMap.containsKey(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)))) {
				return ExplosionResistanceMap.get(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)));
			}else if(!this.isHas()) {
				return this.getBaseBlock().getExplosionResistance(world, pos, exploder, explosion);
			}
		}else if(!this.isHas()) {
			return this.getBaseBlock().getExplosionResistance(world, pos, exploder, explosion);
		}
		return super.getExplosionResistance(world, pos, exploder, explosion);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();
		MCSUtil.info.addInfoStackInfo(meta, this.infoStack, world, tooltip, advanced, this.baseItem, infoStacks);
		MCSUtil.info.addCompressedInfo(meta, tooltip, this.getUnCompressedItemLocalizedName(), Item.getItemFromBlock(this));

		if(MCS.test()) {
			tooltip.add("Is Has Block: " + this.isHas());
			NBTTagCompound nbt = stack.getTagCompound();
			tooltip.add("unCompressedItem: " + this.baseItem.getItem().getRegistryName() + "." + this.baseItem.getItemDamage());

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
	// Custom imp end
	
	boolean canUseWrenchBreak = false;
	public BaseBlockSubT canUseWrenchBreak(boolean canbe) {
		if(Loader.isModLoaded("thermalfoundation") || Loader.isModLoaded("buildcraftcore") || Loader.isModLoaded("enderio")) {
			this.canUseWrenchBreak = canbe;
		}
		return this;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean lag = false;
		if(this.canUseWrenchBreak) {
			return this.useWrenchBreak(world, pos, state, player, hand, false);
		}else if(this.canUseWrenchBreaks != null && this.canUseWrenchBreaks.containsKey(JiuUtils.item.getMetaFromBlockState(state))) {
			return this.useWrenchBreak(world, pos, state, player, hand, true);
		}
		return lag;
	}

	private boolean useWrenchBreak(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, boolean useMap) {
		ItemStack handitem = player.getHeldItem(hand);

		if(player.isSneaking()) {
			if(Loader.isModLoaded("thermalfoundation") || Loader.isModLoaded("buildcraftcore") || Loader.isModLoaded("enderio")) {
				if(Loader.isModLoaded("thermalfoundation")) {
					if(handitem.getItem() instanceof IToolHammer) {
						if(!useMap) {
							JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.item.getStackFromBlockState(state));
							world.setBlockState(pos, Blocks.AIR.getDefaultState());
							return true;
						}else {
							int meta = JiuUtils.item.getMetaFromBlockState(state);
							if(this.canUseWrenchBreaks.containsKey(meta)) {
								if(this.canUseWrenchBreaks.get(meta)) {
									JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.item.getStackFromBlockState(state));
									world.setBlockState(pos, Blocks.AIR.getDefaultState());
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        if(JiuUtils.item.isBlock(this.baseItem)) {
        	return JiuUtils.item.getBlockFromItemStack(this.baseItem).getSoundType(JiuUtils.item.getStateFromItemStack(this.baseItem), world, pos, entity);
        }
		return super.getSoundType(state, world, pos, entity);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public BaseBlockSubT addChangeBlock(int meta, ITime time, boolean canDrops, ItemStack... drops) {
		this.entrys.put(meta, new CustomStuffType.ChangeBlockType(drops, time, canDrops));
		return this;
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(!this.entrys.isEmpty()) {
			if(this.entrys.containsKey(meta)) {
				return new TileEntityChangeBlock(entrys.get(meta));
			}
		}else {
			if(MCSBlocks.CHANGE_MCS_BLOCK_MAP.containsKey(this.name)) {
				if(MCSBlocks.CHANGE_MCS_BLOCK_MAP.get(this.name).containsKey(meta)) {
					return new TileEntityChangeBlock(MCSBlocks.CHANGE_MCS_BLOCK_MAP.get(this.name).get(meta));
				}
			}
		}

		return null;
	}

	public boolean canSetBurnTime() {
		if(this.baseItem != null) {
			return this.getUnCompressedBurnTime() > 0;
		}else {
			return false;
		}
	}

	// implements ICompressedStuff start
	
	private final List<String> otherOredict = ICompressedStuff.super.addOtherOreDictionary();
	public BaseBlockSubT addOtherOreDict(String oredict) {
		this.otherOredict.add(oredict);
		return this;
	}

	@Override
	public List<String> addOtherOreDictionary() {
		return this.otherOredict;
	}
	
	boolean createOredict = true;
	public BaseBlockSubT createOreDictionary(boolean flag) {
		this.createOredict = flag;
		return this;
	}

	@Override
	public boolean createOreDictionary() {
		return this.createOredict;
	}

	public boolean isHas() {
		return !JiuUtils.item.isBlock(this.baseItem);
	}
	
	@Override
	public ItemStack getUnCompressedStack() {
		return this.baseItem;
	}

	private boolean makeRecipe = true;
	public BaseBlockSubT setMakeDefaultStackRecipe(boolean makeRecipe) {
		this.makeRecipe = makeRecipe;
		return this;
	}

	public boolean canMakeDefaultStackRecipe() {
		return this.makeRecipe;
	}

	@Override
	public String getOwnerMod() {
		return this.ownerMod;
	}

	@Override
	public String getUnCompressedName() {
		String[] unNames = JiuUtils.other.custemSplitString(this.name, "_");
		StringBuffer i = new StringBuffer();
		for(String s : unNames) {
			if(!"compressed".equals(s)) {
				i.append(JiuUtils.other.upperFirst(s));
			}
		}
		return i.toString();
	}
	// implements ICompressedStuff end
	
	// implements BaseBlock.Sub start
	@Override
	protected PropertyEnum<ModSubtypes> getPropertyEnum() {
		return PropertyEnum.create("level", ModSubtypes.class);
	}
	@Override
	public ItemBlock getRegisterItemBlock() {
		return new BaseBlockItem(this);
	}
	String state = null;
	public BaseBlockSubT setModelState(String state) {
		this.state = state;
		return this;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void getItemModel() {
		String has = this.baseItem.getItem() instanceof ItemBlock ? "normal" : "has";
		if(this.state != null) has = this.state;

		model.setBlockStateMapper(this, false, this.ownerMod + "/" + has + "/" + this.name);
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			model.registerItemModel(this, meta, this.ownerMod + "/block/" + has + "/" + this.name, this.name + "." + meta);
		}
	}
	
	private final CompressedLevel type = new CompressedLevel(this);
	@Override
	public CompressedLevel getLevel() {
		return this.type;
	}
	// implements BaseBlock.Sub end
	
	@Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        if(this.canThroughBlock) {
        	return null;
        }
		return super.getCollisionBoundingBox(state, world, pos);
    }
	public static final AxisAlignedBB THROUGH_BOX_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.999999999999999999999999D, 0.999999999999999999999999D, 0.999999999999999999999999D);
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		if(this.canThroughBlock) {
        	return THROUGH_BOX_AABB;
        }
		return super.getBoundingBox(state, source, pos);
	}
	
	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemLocalizedName() {
		if(this.baseItem != null || !this.baseItem.equals(new ItemStack(Items.AIR))) {
			return this.baseItem.getDisplayName();
		}else {
			return "\'Unknown Item\'";
		}
	}
	
	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
		if(this.noToolBreak) return true;
		return super.canHarvestBlock(world, pos, player);
	}
	
	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return !this.isHas() ? this.baseBlock.isNormalCube(this.baseState, world, pos) : super.isNormalCube(state, world, pos);
	}
	
	public IBlockState getBaseState() {
		return JiuUtils.item.getStateFromItemStack(this.baseItem);
	}

	public Block getBaseBlock() {
		return JiuUtils.item.getBlockFromItemStack(this.baseItem);
	}
}
