package cat.jiu.multiple_compressed_blocks.server.util.base;

import cat.jiu.multiple_compressed_blocks.server.init.InitBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseBlock extends Block {
	
	 protected final String name;
	 protected final CreativeTabs tab;
	 protected final Block unCompressedBlock;
	 
	public BaseBlock(String name, Block unCompressedBlock, Material materialIn, SoundType type, CreativeTabs tab, float hardness, boolean hasSubType) {
		super(materialIn);
		this.name = name;
		this.tab = tab;
		this.unCompressedBlock = unCompressedBlock;
		
		this.setSoundType(type);
		this.setUnlocalizedName("mcb." + this.name);
		this.setCreativeTab(this.tab);
		if(hardness < 0) {
			this.setHardness(99999999);
			this.setBlockUnbreakable();
		}else {
			this.setHardness(hardness);
		}
		
		InitBlock.BLOCKS.add(this);
		ForgeRegistries.BLOCKS.register(this.setRegistryName(this.name));
		if(hasSubType) {
			ForgeRegistries.ITEMS.register(new BaseBlockItem(this, this.unCompressedBlock).setRegistryName(this.name));
		}else {
			ForgeRegistries.ITEMS.register(new ItemBlock(this).setRegistryName(this.name));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
}
