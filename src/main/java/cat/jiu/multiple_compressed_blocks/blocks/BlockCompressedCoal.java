package cat.jiu.multiple_compressed_blocks.blocks;

import cat.jiu.multiple_compressed_blocks.interfaces.IHasModel;
import cat.jiu.multiple_compressed_blocks.interfaces.IMetaName;
import cat.jiu.multiple_compressed_blocks.server.util.base.BaseBlockSub;
import cat.jiu.multiple_compressed_blocks.util.InitRegisterModel;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;

public class BlockCompressedCoal extends BaseBlockSub implements IHasModel, IMetaName{
	
	private static String registerName;
	private static String blockModID;
	private final static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("type", BlockType.class);
	
	public BlockCompressedCoal(Block unCompressedBlock, String langModID) {
		super("compressed_coal_block", unCompressedBlock);
		registerName = this.name;
		blockModID = langModID;
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockType.BLOCK_0));
	}
	
	@Override
	public void regItemModel() {
		for(BlockType type : BlockType.values()) {
			int meta = type.getMeta();
			InitRegisterModel.registerItemModel(this, meta, blockModID + "/" + this.name, this.name + "." + meta);
		}
	}
	
	@Override
	public String getName(ItemStack stack) {
		return BlockType.values()[stack.getMetadata()].getName();
	}
	
	@Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMeta();
    }
	
	@Override
	public int getMetaFromState(IBlockState state) {
        return ((BlockType)state.getValue(VARIANT)).getMeta();
    }
	
	@Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, BlockType.byMetadata(meta));
    }
	
	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == this.getCreativeTabToDisplayOn()){
			for(BlockType blocktype : BlockType.values()) {
				items.add(new ItemStack(this, 1, blocktype.getMeta()));
			}
		}
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}
	
	public enum BlockType implements IStringSerializable {

		BLOCK_0(0),
		BLOCK_1(1),
		BLOCK_2(2),
		BLOCK_3(3),
		BLOCK_4(4),
		BLOCK_5(5),
		BLOCK_6(6),
		BLOCK_7(7),
		BLOCK_8(8),
		BLOCK_9(9),
		BLOCK_10(10),
		BLOCK_11(11),
		BLOCK_12(12),
		BLOCK_13(13),
		BLOCK_14(14),
		BLOCK_15(15);
		
		private final int meta;
		private static final BlockType[] METADATA_LOOKUP = new BlockType[values().length];
		
		BlockType(int meta) {
			this.meta = meta;
		}
		
		public int getMeta() {
			return meta;
		}
		
		@Override
		public String getName() {
			return registerName + "_" + meta;
		}
		
		public String getUnlocalName() {
			return "tile.mcb." + blockModID + "." + registerName + "." + meta + ".name";
		}
		
		public static BlockType byMetadata(int meta) {
            return METADATA_LOOKUP[meta];
        }static {
            for (BlockType type : values()) {
                METADATA_LOOKUP[type.getMeta()] = type;
            }
        }
	}
}
