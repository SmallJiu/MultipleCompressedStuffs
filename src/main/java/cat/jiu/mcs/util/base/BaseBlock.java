package cat.jiu.mcs.util.base;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.mcs.util.JiuUtils;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.init.MCSBlocks;
import cofh.api.item.IToolHammer;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
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
import stanhebben.zenscript.annotations.NotNull;

@SuppressWarnings("deprecation")
@InterfaceList({
	@Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "buildcraftcore"),
	@Interface(iface = "crazypants.enderio.api.tool.ITool", modid = "enderio"),
	@Interface(iface = "cofh.api.item.IToolHammer", modid = "cofhcore") 
})
public class BaseBlock extends Block {

	protected final String name;
	protected final CreativeTabs tab;
	private final boolean hasSubtypes;
	protected ItemStack unCompressedItem;
	
	public BaseBlock(String name, Material materialIn, SoundType soundType, CreativeTabs tab, float hardness, boolean hasSubType) {
		super(materialIn);
		this.name = name;
		this.tab = tab;
		this.hasSubtypes = hasSubType;
		
		this.setSoundType(soundType);
		this.setBlockHarvestLevel();
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName(this.name);
		MCSBlocks.BLOCKS.add(this);
		MCSBlocks.BLOCKS_NAME.add(this.name);
		MCSBlocks.BLOCKS_MAP.put(this.name, this);
		if(hardness < 0) {
			this.setHardness(99999999);
			this.setBlockUnbreakable();
		}else {
			this.setHardness(hardness);
		}
		
		if(hasSubType) {
			ForgeRegistries.ITEMS.register(new BaseBlockItem(this, false).setRegistryName(this.name));
		}else {
			ForgeRegistries.ITEMS.register(new BaseBlockItem(this, hasSubType).setRegistryName(this.name));
		}
	}

	public BaseBlock(String name, @NotNull ItemStack unCompressedItem, Material materialIn, SoundType soundType, CreativeTabs tab, float hardness, boolean hasSubType) {
		super(materialIn);
		this.name = name;
		this.tab = tab;
		this.hasSubtypes = hasSubType;
		this.unCompressedItem =  JiuUtils.item.equalsStack(unCompressedItem, new ItemStack(Blocks.AIR), false) ? new ItemStack(Blocks.STRUCTURE_BLOCK) : unCompressedItem;
		
		this.setSoundType(soundType);
		this.setBlockHarvestLevel();
		this.setUnlocalizedName("mcs." + this.name);
		this.setCreativeTab(this.tab);
		this.setRegistryName(this.name);
		MCSBlocks.BLOCKS.add(this);
		MCSBlocks.BLOCKS_NAME.add(this.name);
		if(hardness < 0) {
			this.setHardness(99999999);
			this.setBlockUnbreakable();
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
		
//		ForgeRegistries.BLOCKS.register(this.setRegistryName(this.name));
		if(hasSubType) {
			ForgeRegistries.ITEMS.register(new BaseBlockItem(this, this.unCompressedItem).setRegistryName(this.name));
		}else {
			ForgeRegistries.ITEMS.register(new BaseBlockItem(this, hasSubType).setRegistryName(this.name));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	public final Item getUnCompressedItem(){
		return this.unCompressedItem.getItem();
	}
	
	public final ItemStack getUnCompressedItemStack(){
		return this.unCompressedItem;
	}
	
	public final String getUnCompressedItemFistOreDict() {
		return JiuUtils.item.getOreDict(this.unCompressedItem).get(0);
	}
	
	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemUnlocalizedName(){
		if("minecraft".equals(this.unCompressedItem.getItem().getCreatorModId(this.unCompressedItem))) {
			return I18n.format(this.unCompressedItem.getUnlocalizedName() + ".name", 1).trim();
		}else {
			return I18n.format(this.unCompressedItem.getUnlocalizedName(), 1).trim();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public final String getUnCompressedItemLocalizedName() {
		if(!this.unCompressedItem.equals(new ItemStack(Items.AIR)) || this.unCompressedItem != null) {
			String itemmodid = this.unCompressedItem.getItem().getCreatorModId(this.unCompressedItem);
			
			if(JiuUtils.other.containKey(MCS.other_mod, itemmodid)) {
				return I18n.format(this.unCompressedItem.getUnlocalizedName() + ".name", 1).trim();
			}else {
				return I18n.format(this.unCompressedItem.getUnlocalizedName(), 1).trim();
			}
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
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean lag = false;
		if(this.canUseWrenchBreak) {
			return this.useWrenchBreak(world, pos, state, player, hand);
		}else {
			if(Configs.custom.custem_already_stuff.block.custem_can_use_wrench_break.length != 0) {
				try {
					String[] spt0 = Configs.custom.custem_already_stuff.block.custem_can_use_wrench_break;
					for(int i = 1; i < spt0.length; ++i) {
						String[] str = JiuUtils.other.custemSplitString(spt0[i], "|");
						String name = str[0];
						
						try {
							int meta = new Integer(str[1]);
							
							if(MCSBlocks.BLOCKS_NAME.contains(name)) {
								if(!(meta > 15)) {
									if(JiuUtils.item.getMetaFormBlockState(state) == meta) {
										return this.useWrenchBreak(world, pos, state, player, hand);
									}
								}else {
									if(world.isRemote) {
										MCS.instance.log.fatal("\"" + name +  "\": "+ "\"" + meta + "\"" + " It's too large! It must be >=15");
									}
								}
							}else {
								if(world.isRemote) {
									MCS.instance.log.fatal("\"" + name +  "\": "+ "\"" + name + "\"" + " is not belong to MCS's Block!");
								}
							}
						} catch (Exception e) {
							if(world.isRemote) {
								MCS.instance.log.fatal("\"" + name +  "\": "+ e.getMessage() + " is not Number!");
							}
						}
					}
				} catch (Exception e) { }
			}
		}
		return lag;
	}
	
	private boolean useWrenchBreak(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand) {
		boolean lag = false;
		ItemStack handitem = player.getHeldItem(hand);
		
		if(player.isSneaking()) {
			if(Loader.isModLoaded("thermalfoundation") || Loader.isModLoaded("buildcraftcore") || Loader.isModLoaded("enderio")) {
				if(Loader.isModLoaded("thermalfoundation")) {
					if (handitem.getItem() instanceof IToolHammer) {
						JiuUtils.item.spawnAsEntity(world, pos, JiuUtils.item.getStackFormBlockState(state));
						world.setBlockState(pos, Blocks.AIR.getDefaultState());
						return true;
					}
				}
			}
		}
		return lag;
	}
	
	protected boolean isInCreativeTab(CreativeTabs targetTab) {
		CreativeTabs creativetabs = this.getCreativeTabToDisplayOn();
		return creativetabs != null && (targetTab == CreativeTabs.SEARCH || targetTab == creativetabs);
	}
	
	public boolean getHasSubtypes() {
		return this.hasSubtypes;
	}
	
	private void setBlockHarvestLevel() {
		Block unBlock = JiuUtils.item.getBlockFromItemStack(this.unCompressedItem);
		IBlockState unState = JiuUtils.item.getStateFromItemStack(this.unCompressedItem);
		
		for(ModSubtypes type : ModSubtypes.values()) {
			int meta = type.getMeta();
			
			if(unBlock.getHarvestLevel(unState) > 2) {
				this.setHarvestLevel("pickaxe", 3);
				break;
			}else if(unBlock.getHarvestLevel(unState) == 2) {
				this.setHarvestLevel("pickaxe", 2, this.getDefaultState());
				if(meta > 0) {
					this.setHarvestLevel("pickaxe", 3, this.getStateFromMeta(meta));
				}
			}else if(meta == 0) {
				this.setHarvestLevel("pickaxe", 1, this.getDefaultState());
			}else if(meta == 1) {
				this.setHarvestLevel("pickaxe", 2, this.getStateFromMeta(meta));
			}else if(meta > 1) {
				this.setHarvestLevel("pickaxe", 3, this.getStateFromMeta(meta));
			}
		}
	}
}
