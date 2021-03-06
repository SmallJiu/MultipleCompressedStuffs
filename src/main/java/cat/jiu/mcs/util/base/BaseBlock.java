package cat.jiu.mcs.util.base;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.api.ICompressedStuff;
import cat.jiu.mcs.util.ModSubtypes;

import cofh.api.item.IToolHammer;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@InterfaceList({
	@Interface(
		iface = "buildcraft.api.tools.IToolWrench",
		modid = "buildcraftcore"),
	@Interface(
		iface = "crazypants.enderio.api.tool.ITool",
		modid = "enderio"),
	@Interface(
		iface = "cofh.api.item.IToolHammer",
		modid = "cofhcore")})
@SuppressWarnings("deprecation")
public abstract class BaseBlock extends Block implements ICompressedStuff {
	protected final String name;
	protected final CreativeTabs tab;
	private final boolean hasSubtypes;
	protected ItemStack unCompressedItem;

	public BaseBlock(String name, @Nonnull ItemStack unCompressedItem, Material materialIn, SoundType soundType, CreativeTabs tab, float hardness, boolean hasSubType) {
		super(materialIn);
		this.name = name;
		this.tab = tab;
		this.hasSubtypes = hasSubType;
		this.unCompressedItem = unCompressedItem;

		this.setSoundType(soundType);
		this.setBlockHarvestLevel();
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName("mcs", this.name);
		if(hardness < 0) {
			this.setHardness(99999999);
		}else {
			this.setHardness(hardness);
		}

		if(unCompressedItem.getItem() instanceof ItemBlock) {
			Block unBlock = JiuUtils.item.getBlockFromItemStack(unCompressedItem);
			IBlockState unState = JiuUtils.item.getStateFromItemStack(unCompressedItem);

			if(this.getBlockHardness(unState, null, null) > 10F) {
				this.setHarvestLevel("pickaxe", 3);
			}
			this.setLightLevel(unState.getLightValue());
			this.setHardness(unBlock.getBlockHardness(unState, null, null));
			this.setSoundType(unBlock.getSoundType());
		}

		ForgeRegistries.ITEMS.register(new BaseBlockItem(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity) {
        if(JiuUtils.item.isBlock(this.unCompressedItem)) {
        	return JiuUtils.item.getBlockFromItemStack(this.unCompressedItem).getSoundType(JiuUtils.item.getStateFromItemStack(this.unCompressedItem), world, pos, entity);
        }
		return super.getSoundType(state, world, pos, entity);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	public BaseBlock setUnCompressed(ItemStack stack) {
		if(stack == null || stack.isEmpty()) {
			throw new RuntimeException(this.name + ": unCompressedItem can NOT be EMPTY!");
		}
		if(this.unCompressedItem == null || this.unCompressedItem.isEmpty()) {
			this.unCompressedItem = stack;
			if(this.unCompressedItem == null || this.unCompressedItem.isEmpty()) {
				throw new RuntimeException(this.name + ": unCompressedItem can NOT be EMPTY!");
			}
		}
		return this;
	}

	public final Item getUnCompressedItem() {
		return this.unCompressedItem.getItem();
	}

	public final ItemStack getUnCompressedStack() {
		return this.unCompressedItem;
	}

	public final String getUnCompressedItemFistOreDict() {
		return JiuUtils.item.getOreDict(this.unCompressedItem).get(0);
	}

	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemUnlocalizedName() {
		if("minecraft".equals(this.unCompressedItem.getItem().getCreatorModId(this.unCompressedItem))) {
			return I18n.format(this.unCompressedItem.getUnlocalizedName() + ".name", 1).trim();
		}else {
			return I18n.format(this.unCompressedItem.getUnlocalizedName(), 1).trim();
		}
	}

	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemLocalizedName() {
		if(!this.unCompressedItem.equals(new ItemStack(Items.AIR)) || this.unCompressedItem != null) {
			return this.unCompressedItem.getDisplayName();
		}else {
			return "\'Unknown Item\'";
		}
	}

	boolean canUseWrenchBreak = false;
	public BaseBlock canUseWrenchBreak(boolean canbe) {
		if(Loader.isModLoaded("thermalfoundation") || Loader.isModLoaded("buildcraftcore") || Loader.isModLoaded("enderio")) {
			this.canUseWrenchBreak = canbe;
		}
		return this;
	}

	Map<Integer, Boolean> canUseWrenchBreaks = null;
	public BaseBlock canUseWrenchBreak(Map<Integer, Boolean> canbe) {
		if(Loader.isModLoaded("thermalfoundation") || Loader.isModLoaded("buildcraftcore") || Loader.isModLoaded("enderio")) {
			this.canUseWrenchBreaks = canbe;
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

	public boolean getHasSubtypes() {
		return this.hasSubtypes;
	}

	private void setBlockHarvestLevel() {
		Block unBlock = JiuUtils.item.getBlockFromItemStack(this.unCompressedItem);
		IBlockState unState = JiuUtils.item.getStateFromItemStack(this.unCompressedItem);

		if(unBlock != null) {
			for(ModSubtypes type : ModSubtypes.values()) {
				int meta = type.getMeta();

				if(unBlock.getHarvestLevel(unState) == 0) {
					break;
				}
				if(unBlock.getHarvestLevel(unState) > 2) {
					this.setHarvestLevel("pickaxe", 3);
					break;
				}else if(unBlock.getHarvestLevel(unState) == 2) {
					this.setHarvestLevel("pickaxe", 2, this.getDefaultState());
					if(meta > 0) {
						this.setHarvestLevel("pickaxe", 3, this.getStateFromMeta(meta));
					}
				}else if(meta == 1) {
					this.setHarvestLevel("pickaxe", 2, this.getStateFromMeta(meta));
				}else if(meta > 1) {
					this.setHarvestLevel("pickaxe", 3, this.getStateFromMeta(meta));
				}
			}
		}
	}
}
