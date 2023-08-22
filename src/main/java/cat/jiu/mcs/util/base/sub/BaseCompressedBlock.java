package cat.jiu.mcs.util.base.sub;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cat.jiu.core.api.ITimer;
import cat.jiu.core.types.StackCaches;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.RegisterModel;
import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.core.util.timer.Timer;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.api.ITooltipString;
import cat.jiu.mcs.api.recipe.ISmeltingRecipe;
import cat.jiu.mcs.blocks.tileentity.TileEntityChangeBlock;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.PropertySubtypes;
import cat.jiu.mcs.util.base.BaseCompressedBlockItem;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSCreativeTab;
import cat.jiu.mcs.util.init.MCSResources;
import cat.jiu.mcs.util.type.CustomStuffType;

import cofh.api.item.IToolHammer;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class BaseCompressedBlock extends BaseBlock.Sub<ModSubtypes> implements ICompressedStuff, ITileEntityProvider, ISmeltingRecipe {
	protected final boolean recipeIsRepeat;
	protected Block baseBlock;
	protected IBlockState baseState;
	protected final String ownerMod;
	protected ItemStack baseItem;
	public BaseCompressedBlock(String nameIn, @Nonnull UnFoundItem baseItem, String ownerMod,  Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		this(nameIn, ItemStack.EMPTY, ownerMod, materialIn, soundIn, tabIn, hardnessIn);
		this.unFoundItem = baseItem;
	}
	
	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack baseItem, String ownerMod,  Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		super("mcs", nameIn, materialIn, soundIn, tabIn, hardnessIn);
		this.baseItem = baseItem;
		this.recipeIsRepeat = this.checkRecipe(baseItem);
		this.ownerMod = ownerMod;
		this.baseBlock = !this.isHas() ? JiuUtils.item.getBlockFromItemStack(baseItem) : Blocks.AIR;
		this.baseState = !this.isHas() ? JiuUtils.item.getStateFromItemStack(baseItem) : Blocks.AIR.getDefaultState();
		if(!ownerMod.equals("custom")) {
//			MCSResources.BLOCKS.add(this);
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
	
	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, ownerMod, materialIn, soundIn, tabIn, 4.0F);
	}

	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, Material materialIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, ownerMod, materialIn, SoundType.METAL, tabIn);
	}

	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, ownerMod, Material.IRON, soundIn, tabIn);
	}

	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, CreativeTabs tabIn, float hardnessIn) {
		this(nameIn, unCompressedItem, ownerMod, Material.IRON, SoundType.METAL, tabIn, hardnessIn);
	}

	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, float hardnessIn) {
		this(nameIn, unCompressedItem, ownerMod, MCSCreativeTab.BLOCKS, hardnessIn);
	}

	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, CreativeTabs tabIn) {
		this(nameIn, unCompressedItem, ownerMod, Material.IRON, tabIn);
	}

	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod) {
		this(nameIn, unCompressedItem, ownerMod, MCSCreativeTab.BLOCKS);
	}

	public BaseCompressedBlock(@Nonnull ItemStack unCompressedItem, String ownerMod) {
		this("compressed_" + unCompressedItem.getItem().getRegistryName().getResourcePath(), unCompressedItem, ownerMod);
	}

	public BaseCompressedBlock(String nameIn, @Nonnull ItemStack unCompressedItem) {
		this(nameIn, unCompressedItem, unCompressedItem.getItem().getRegistryName().getResourceDomain());
	}

	public BaseCompressedBlock(@Nonnull ItemStack unCompressedItem) {
		this(unCompressedItem, unCompressedItem.getItem().getRegistryName().getResourceDomain());
	}
	
	public boolean checkRecipe(ItemStack unCompressedItem) {
		return false;
	}
	
	public BaseCompressedBlock setUnCompressed(ItemStack stack) {
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
	public BaseCompressedBlock canUseWrenchBreak(Map<Integer, Boolean> canbe) {
		if(Loader.isModLoaded("thermalfoundation") || Loader.isModLoaded("buildcraftcore") || Loader.isModLoaded("enderio")) {
			this.canUseWrenchBreaks = canbe;
		}
		return this;
	}
	
	private Map<Integer, CustomStuffType.ChangeBlockType> ChangeBlocks;
	public BaseCompressedBlock setChangeBlock(int meta, int tick, int s, int m, boolean canDrops, ItemStack... drops) {
		return this.addChangeBlock(meta, new Timer(m, s, tick), canDrops, drops);
	}
	
	private Map<Integer, List<TextComponentTranslation>> metaShiftInfos;
	public BaseCompressedBlock setCustemShiftInformation(Map<Integer, List<TextComponentTranslation>> infos) {
		this.metaShiftInfos = infos;
		return this;
	}

	private Map<Integer, List<TextComponentTranslation>> metaInfos;
	public BaseCompressedBlock setCustemInformation(Map<Integer, List<TextComponentTranslation>> infos) {
		this.metaInfos = infos;
		return this;
	}
	
	private Map<Integer, Float> HardnessMap;
	public BaseCompressedBlock setHardnessMap(Map<Integer, Float> map) {
		this.HardnessMap = map;
		return this;
	}
	
	private Map<Integer, CustomStuffType.HarvestType> HarvestMap;
	public BaseCompressedBlock setHarvestMap(Map<Integer, CustomStuffType.HarvestType> map) {
		this.HarvestMap = map;
		return this;
	}

	private Map<Integer, Boolean> BeaconBaseMap;
	public BaseCompressedBlock setBeaconBaseMap(Map<Integer, Boolean> map) {
		this.BeaconBaseMap = map;
		return this;
	}

	private Map<Integer, Integer> LightValueMap;
	public BaseCompressedBlock setLightValueMap(Map<Integer, Integer> map) {
		this.LightValueMap = map;
		return this;
	}

	private Map<Integer, Float> ExplosionResistanceMap;
	public BaseCompressedBlock setExplosionResistanceMap(Map<Integer, Float> map) {
		this.ExplosionResistanceMap = map;
		return this;
	}
	
	private Map<Integer, List<ItemStack>> DropsMap;
	public BaseCompressedBlock setDropsMapMap(Map<Integer, List<ItemStack>> map) {
		this.DropsMap = map;
		return this;
	}
	
	private Map<Integer, ItemStack> infoStacks = null;
	public BaseCompressedBlock setInfoStack(Map<Integer, ItemStack> infoStacks) {
		this.infoStacks = infoStacks;
		return this;
	}
	
	private List<ITooltipString> infoHandler;
	public BaseCompressedBlock addInfoHandler(ITooltipString handler) {
		if(this.infoHandler==null) this.infoHandler = Lists.newArrayList();
		this.infoHandler.add(handler);
		return this;
	}
	// Custom end

	public BaseCompressedBlock addHarvestMap(int meta, String tool, int level) {
		if(this.HarvestMap == null) this.HarvestMap = Maps.newHashMap();
		this.HarvestMap.put(meta, new CustomStuffType.HarvestType(tool, level));
		return this;
	}

	public BaseCompressedBlock addHardnessMap(int meta, float hardness) {
		if(this.HardnessMap == null) this.HardnessMap = Maps.newHashMap();
		this.HardnessMap.put(meta, hardness);
		return this;
	}
	
	private List<ItemStack> drops = null;
	public BaseCompressedBlock setDrops(List<ItemStack> drops) {
		this.drops = drops;
		return this;
	}
	public BaseCompressedBlock addDrops(ItemStack... stacks) {
		if(this.drops == null) this.drops = Lists.newArrayList();
		for(ItemStack stack : stacks) {
			this.drops.add(stack);
		}
		return this;
	}
	
	private boolean canThroughBlock = false;
	public BaseCompressedBlock setCanThroughBlock() {
		this.canThroughBlock = true;
		return this;
	}

	private boolean noToolBreak = false;
	public BaseCompressedBlock setNotNeedToolBreak() {
		this.noToolBreak = true;
		return this;
	}
	
	boolean isTransparentCube = false;
	public BaseCompressedBlock setIsTransparentCube() {
		this.isTransparentCube = true;
		return this;
	}
	
	private List<TextComponentTranslation> shiftInfos;
	public BaseCompressedBlock addCustemShiftInformation(String... custemInfo) {
		if(this.shiftInfos == null) this.shiftInfos = Lists.newArrayList();
		for(String str : custemInfo) {
			this.shiftInfos.add(new TextComponentTranslation(str));
		}
		return this;
	}

	public BaseCompressedBlock addCustemShiftInformation(List<TextComponentTranslation> infos) {
		if(this.shiftInfos == null) this.shiftInfos = Lists.newArrayList();
		
		this.shiftInfos.addAll(infos);
		
		return this;
	}

	private List<TextComponentTranslation> infos;
	public BaseCompressedBlock setCustemInformation(List<TextComponentTranslation> infos) {
		this.infos = infos;
		return this;
	}
	public BaseCompressedBlock addCustemInformation(String... custemInfo) {
		if(this.infos == null) this.infos = Lists.newArrayList();
		for(String str : custemInfo) {
			this.infos.add(new TextComponentTranslation(str));
		}
		return this;
	}
	public BaseCompressedBlock addCustemInformation(List<TextComponentTranslation> custemInfo) {
		if(this.infos == null) this.infos = Lists.newArrayList();
		this.infos.addAll(custemInfo);
		return this;
	}

	ItemStack infoStack = null;
	public BaseCompressedBlock setInfoStack(ItemStack stack) {
		this.infoStack = stack;
		return this;
	}
	
	// Custom implement start

	protected boolean getHarvestToolError;
	@Override
	public String getHarvestTool(IBlockState state) {
		if(this.noToolBreak) return null;
		if(HarvestMap != null && !HarvestMap.isEmpty()) {
			if(HarvestMap.containsKey(JiuUtils.item.getMetaFromBlockState(state))) {
				return HarvestMap.get(JiuUtils.item.getMetaFromBlockState(state)).tool;
			}else if(!this.isHas() && !this.getHarvestToolError) {
				try {
					return this.getBaseBlock().getHarvestTool(this.getBaseState());
				}catch(Throwable e) {
					this.getHarvestToolError = true;
				}
			}
		}else if(!this.isHas() && !this.getHarvestToolError) {
			try {
				return this.getBaseBlock().getHarvestTool(this.getBaseState());
			}catch(Throwable e) {
				this.getHarvestToolError = true;
			}
		}
		return super.getHarvestTool(state);
	}

	protected boolean getHarvestLevelError;
	@Override
	public int getHarvestLevel(IBlockState state) {
		if(this.noToolBreak) return 0;
		
		if(HarvestMap != null && !HarvestMap.isEmpty()) {
			if(HarvestMap.containsKey(JiuUtils.item.getMetaFromBlockState(state))) {
				return HarvestMap.get(JiuUtils.item.getMetaFromBlockState(state)).level;
			}else if(!this.isHas() && !this.getHarvestLevelError) {
				try {
					return this.getBaseBlock().getHarvestLevel(this.getBaseState());
				}catch(Throwable e) {
					this.getHarvestLevelError = true;
				}
			}
		}else if(!this.isHas() && !this.getHarvestLevelError) {
			return this.getBaseBlock().getHarvestLevel(this.getBaseState());
		}
		return super.getHarvestLevel(state);
	}

	protected boolean getBlockHardnessError;
	@Override
	public float getBlockHardness(IBlockState blockState, World world, BlockPos pos) {
		if(this.noToolBreak) return 0F;
		if(HardnessMap != null && !HardnessMap.isEmpty()) {
			if(HardnessMap.containsKey(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)))) {
				return HardnessMap.get(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)));
			}else if(!this.isHas() && !this.getBlockHardnessError) {
				try {
					return this.getBaseBlock().getBlockHardness(this.getBaseState(), world, pos);
				}catch(Throwable e) {
					this.getBlockHardnessError = true;
				}
			}
		}else if(!this.isHas() && !this.getBlockHardnessError) {
			try {
				return this.getBaseBlock().getBlockHardness(this.getBaseState(), world, pos);
			}catch(Throwable e) {
				this.getBlockHardnessError = true;
			}
		}
		return super.getBlockHardness(blockState, world, pos);
	}
	
	protected boolean isBeaconBaseError;
	@Override
	public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon) {
		if(BeaconBaseMap != null && !BeaconBaseMap.isEmpty()) {
			if(BeaconBaseMap.containsKey(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)))) {
				return BeaconBaseMap.get(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)));
			}else if(!this.isHas() && !this.isBeaconBaseError) {
				try {
					return this.getBaseBlock().isBeaconBase(world, pos, beacon);
				}catch(Throwable e) {
					this.isBeaconBaseError = true;
				}
			}
		}else if(!this.isHas() && !this.isBeaconBaseError) {
			try {
				return this.getBaseBlock().isBeaconBase(world, pos, beacon);
			}catch(Throwable e) {
				this.isBeaconBaseError = true;
			}
		}

		return super.isBeaconBase(world, pos, beacon);
	}
	
	private boolean getLightError;
	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if(LightValueMap != null && !LightValueMap.isEmpty()) {
			if(LightValueMap.containsKey(JiuUtils.item.getMetaFromBlockState(state))) {
				return LightValueMap.get(JiuUtils.item.getMetaFromBlockState(state));
			}else if(!this.isHas() && !this.getLightError) {
				try {
					return this.getBaseBlock().getLightValue(this.getBaseState(), world, pos);
				}catch(Throwable e) {
					this.getLightError = true;
				}
			}
		}else if(!this.isHas() && !this.getLightError) {
			try {
				return this.getBaseBlock().getLightValue(this.getBaseState(), world, pos);
			}catch(Throwable e) {
				this.getLightError = true;
			}
		}
		return super.getLightValue(state, world, pos);
	}
	
	protected boolean getExplosionResistanceError;
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		if(ExplosionResistanceMap != null && !ExplosionResistanceMap.isEmpty()) {
			if(ExplosionResistanceMap.containsKey(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)))) {
				return ExplosionResistanceMap.get(JiuUtils.item.getMetaFromBlockState(world.getBlockState(pos)));
			}else if(!this.isHas() && !this.getExplosionResistanceError) {
				try {
					return this.getBaseBlock().getExplosionResistance(world, pos, exploder, explosion);
				}catch(Throwable e) {
					this.getExplosionResistanceError = true;
				}
			}
		}else if(!this.isHas() && !this.getExplosionResistanceError) {
			try {
				return this.getBaseBlock().getExplosionResistance(world, pos, exploder, explosion);
			}catch(Throwable e) {
				this.getExplosionResistanceError = true;
			}
		}
		return super.getExplosionResistance(world, pos, exploder, explosion);
	}
	
	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		if(this.drops != null && !this.drops.isEmpty()) {
			JiuUtils.item.spawnAsEntity(world, pos, this.drops);
		}
		
		if(DropsMap != null && !DropsMap.isEmpty()) {
			int meta = JiuUtils.item.getMetaFromBlockState(state);
			if(DropsMap.containsKey(meta)) {
				JiuUtils.item.spawnAsEntity(world, pos, DropsMap.get(meta));
			}
		}
	}
	
	protected boolean canShowBaseStackInfo = true;
	public BaseCompressedBlock setCanShowBaseStackInfo(boolean canShow) {
		this.canShowBaseStackInfo = canShow;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced) {
		int meta = stack.getMetadata();
		if(this.canShowBaseStackInfo && !MCSUtil.info.addInfoStackInfo(meta, this.infoStack, world, tooltip, advanced, infoStacks)) {
			this.getUnCompressedStack().getItem().addInformation(getUnCompressedStack(), world, tooltip, advanced);
		}
		MCSUtil.info.addCompressedInfo(meta, tooltip, this.getUnCompressedItemLocalizedName(), Item.getItemFromBlock(this));
		
		if(MCS.dev()) {
			tooltip.add("Is Has Block: " + this.isHas());
			tooltip.add("unCompressedItem: " + this.baseItem.getItem().getRegistryName() + "." + this.baseItem.getItemDamage());

			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null) {
				tooltip.add("ChangeTime: " + nbt.getInteger("ChangeM") + "/" + nbt.getInteger("ChangeS") + "/" + nbt.getInteger("ChangeTick"));
				tooltip.add(nbt.toString());
			}

			tooltip.add(JiuUtils.other.custemSplitString(this.name, "_").length + " | " + this.getUnCompressedName());
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
		MCSUtil.info.addHandlerString(tooltip, this.infoHandler, stack, world, advanced);
	}
	// Custom implement end
	
	boolean canUseWrenchBreak = false;
	public BaseCompressedBlock canUseWrenchBreak(boolean canbe) {
		if(Loader.isModLoaded("thermalfoundation") || Loader.isModLoaded("buildcraftcore") || Loader.isModLoaded("enderio")) {
			this.canUseWrenchBreak = canbe;
		}
		return this;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean lag = false;
		if(player.isSneaking()) {
			if(this.canUseWrenchBreak) {
				lag = this.useWrenchBreak(world, pos, state, player, hand, false);
			}else if(this.canUseWrenchBreaks != null && this.canUseWrenchBreaks.containsKey(JiuUtils.item.getMetaFromBlockState(state))) {
				lag = this.useWrenchBreak(world, pos, state, player, hand, true);
			}
		}
		return lag;
	}

	protected boolean useWrenchBreak(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, boolean useMap) {
		ItemStack handitem = player.getHeldItem(hand);

		if(Loader.isModLoaded("thermalfoundation")) {
			if(handitem.getItem() instanceof IToolHammer) {
				return this.wrenchBreak(world, pos, state, useMap);
			}
		}
		
		return false;
	}
	
	protected boolean wrenchBreak(World world, BlockPos pos, IBlockState state, boolean useMap) {
		if(!useMap) {
			JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.item.getStackFromBlockState(state));
			world.setBlockToAir(pos);
			return true;
		}else {
			int meta = JiuUtils.item.getMetaFromBlockState(state);
			if(this.canUseWrenchBreaks.containsKey(meta)) {
				if(this.canUseWrenchBreaks.get(meta)) {
					JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.item.getStackFromBlockState(state));
					world.setBlockToAir(pos);
					return true;
				}
			}
		}
		return false;
	}

	protected boolean getSoundTypeError;
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        if(JiuUtils.item.isBlock(this.baseItem) && !this.getSoundTypeError) {
        	try {
				return this.getBaseBlock().getSoundType(this.getBaseState(), world, pos, entity);
			}catch(Throwable e) {
				this.getSoundTypeError = true;
			}
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
	
	public BaseCompressedBlock addChangeBlock(int meta, ITimer time, boolean canDrops, ItemStack... drops) {
		if(this.ChangeBlocks == null) this.ChangeBlocks = Maps.newHashMap();
		this.ChangeBlocks.put(meta, new CustomStuffType.ChangeBlockType(drops, time, canDrops));
		return this;
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(this.ChangeBlocks != null && !this.ChangeBlocks.isEmpty()) {
			if(this.ChangeBlocks.containsKey(meta)) {
				return new TileEntityChangeBlock(ChangeBlocks.get(meta));
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

	// implement ICompressedStuff start
	
	private final List<String> otherOredict = ICompressedStuff.super.getOtherOreDictionary();
	public BaseCompressedBlock addOtherOreDict(String oredict) {
		this.otherOredict.add(oredict);
		return this;
	}

	@Override
	public List<String> getOtherOreDictionary() {
		return this.otherOredict;
	}
	
	boolean createOredict = true;
	public BaseCompressedBlock createOreDictionary(boolean flag) {
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
	public BaseCompressedBlock setMakeDefaultStackRecipe(boolean makeRecipe) {
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

	protected String unCompressedName;
	@Override
	public String getUnCompressedName() {
		if(this.unCompressedName==null) {
			String[] unNames = JiuUtils.other.custemSplitString(this.name, "_");
			StringBuffer i = new StringBuffer();
			for(String s : unNames) {
				if(!"compressed".equals(s)) {
					i.append(JiuUtils.other.upperFirst(s));
				}
			}
			this.unCompressedName = i.toString();
		}
		
		return this.unCompressedName;
	}
	// implements ICompressedStuff end
	
	// implements BaseBlock.Sub start
	private PropertySubtypes property;
	@Override
	protected PropertySubtypes getBlockStateProperty() {
		if(this.property==null) this.property = new PropertySubtypes("level");
		return this.property;
	}
	@Override
	protected ModSubtypes[] getPropertyArray() {
		return ModSubtypes.values(ModSubtypes.MAX <= 16 ? ModSubtypes.MAX : 16);
	}
	
	@Override
	public ItemBlock getRegisterItemBlock() {
		return new BaseCompressedBlockItem(this);
	}
	String state = null;
	public BaseCompressedBlock setModelState(String state) {
		this.state = state;
		return this;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void getItemModel(RegisterModel util) {
		String has = this.baseItem.getItem() instanceof ItemBlock ? "normal" : "has";
		if(this.state != null) has = this.state;
		
		util.setBlockStateMapper(this, false, this.ownerMod + "/" + has + "/" + this.name);
		for(ModSubtypes type : ModSubtypes.values(ModSubtypes.MAX <= 16 ? ModSubtypes.MAX : 16)) {
			int meta = type.getMeta();
			String dir = this.ownerMod + "/block/" + has + "/" + this.name;
			String file = this.name + "." + meta;
			util.registerItemModel(this, meta, dir, file);
		}
	}
	
	private final StackCaches type = new StackCaches(this, ModSubtypes.MAX <= 16 ? ModSubtypes.MAX : 16);
	@Override
	public StackCaches getLevel() {
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
	
	private boolean baseRecipeIsItem = false;
	public BaseCompressedBlock setBaseRecipeIsItem() {
		this.baseRecipeIsItem = true;
		return this;
	}
	public boolean baseRecipeHasItem() {
		return baseRecipeIsItem;
	}
	
	private UnFoundItem unFoundItem;
	public BaseCompressedBlock setUnFoundItem(String name, int meta) {
		if(this.unFoundItem==null) {
			this.unFoundItem = new UnFoundItem(name, meta);
		}
		return this;
	}
	public ItemStack getUnFoundItem() {
		return unFoundItem.toStack();
	}
	public boolean hasUnFoundItem() {
		return (this.baseItem==null || this.baseItem.isEmpty()) && this.unFoundItem!=null;
	}
	
	public static class UnFoundItem {
		private final String name;
		private final int meta;
		public UnFoundItem(String name, int meta) {
			this.name = name;
			this.meta = meta;
		}
		private ItemStack toStack() {return new ItemStack(Item.getByNameOrId(name), 1, meta);}
	}
	
	private ICompressedStuff smeltingOutput;
	public BaseCompressedBlock setSmeltingOutput(ICompressedStuff stuff) {
		this.smeltingOutput = stuff;
		return this;
	}
	private int smeltingMetaDisparity = 1;
	public BaseCompressedBlock setSmeltingMetaDisparity(int smeltingMetaDisparity) {
		this.smeltingMetaDisparity = smeltingMetaDisparity;
		return this;
	}
	@Override
	public boolean canCreateRecipe(int meta) {
		return this.smeltingOutput!=null;
	}
	@Override
	public ItemStack getSmeltingOutput(int meta) {
		int outmeta = meta-smeltingMetaDisparity;
		if(this.smeltingOutput.isBlock() && outmeta > 15) {
			outmeta = 15;
		}
		return outmeta < 0 ? null : smeltingOutput.getStack(outmeta);
	}
	// static
	
	public static BaseCompressedBlock register(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		if(Loader.isModLoaded(ownerModID) || ownerModID.equals("custom")) {
			return new BaseCompressedBlock(nameIn, unCompressedItem, ownerModID, materialIn, soundIn, tabIn, hardnessIn);
		}else {
			return null;
		}
	}

	public static BaseCompressedBlock register(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, ownerMod, materialIn, soundIn, tabIn, 4.0F);
	}

	public static BaseCompressedBlock register(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, Material materialIn, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, ownerMod, materialIn, SoundType.METAL, tabIn);
	}

	public static BaseCompressedBlock register(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, SoundType soundIn, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, ownerMod, Material.IRON, soundIn, tabIn);
	}

	public static BaseCompressedBlock register(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod, CreativeTabs tabIn) {
		return register(nameIn, unCompressedItem, ownerMod, Material.IRON, SoundType.METAL, tabIn);
	}

	public static BaseCompressedBlock register(String nameIn, @Nonnull ItemStack unCompressedItem, String ownerMod) {
		return register(nameIn, unCompressedItem, ownerMod, MCSCreativeTab.BLOCKS);
	}

	public static BaseCompressedBlock register(String nameIn, @Nonnull ItemStack unCompressedItem) {
		return register(nameIn, unCompressedItem, unCompressedItem.getItem().getRegistryName().getResourceDomain());
	}

	public static BaseCompressedBlock register(@Nonnull ItemStack unCompressedItem, String ownerMod) {
		return register("compressed_" + unCompressedItem.getItem().getRegistryName().getResourcePath(), unCompressedItem, ownerMod);
	}

	public static BaseCompressedBlock register(@Nonnull ItemStack unCompressedItem) {
		return register("compressed_" + unCompressedItem.getItem().getRegistryName().getResourcePath(), unCompressedItem);
	}
	
}
