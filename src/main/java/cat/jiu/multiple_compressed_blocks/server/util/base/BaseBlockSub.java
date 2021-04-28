package cat.jiu.multiple_compressed_blocks.server.util.base;

import java.util.List;

import javax.annotation.Nullable;

import cat.jiu.multiple_compressed_blocks.config.Configs;
import cat.jiu.multiple_compressed_blocks.server.CreativeTabCompressedBlocks;
import cat.jiu.multiple_compressed_blocks.util.InitHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class BaseBlockSub extends BaseBlock {
	
	private final Block unCompressedBlock;
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock, Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn, boolean hasSubType) {
		super(nameIn, unCompressedBlock, materialIn, soundIn, tabIn, hardnessIn, hasSubType);
		this.unCompressedBlock = unCompressedBlock;
	}
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock, Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		this(nameIn, unCompressedBlock, materialIn, soundIn, tabIn, hardnessIn, true);
	}
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock, Material materialIn, CreativeTabs tabIn, float hardnessIn) {
		this(nameIn, unCompressedBlock, materialIn, unCompressedBlock.getSoundType(), tabIn, hardnessIn);
	}
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		this(nameIn, unCompressedBlock, unCompressedBlock.getMaterial(unCompressedBlock.getDefaultState()), soundIn, tabIn, hardnessIn);
	}
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedBlock, materialIn, unCompressedBlock.getSoundType(), tabIn, 10F);
	}
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedBlock, unCompressedBlock.getMaterial(unCompressedBlock.getDefaultState()), soundIn, tabIn);
	}
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock, Material materialIn, CreativeTabs tabIn) {
		this(nameIn, unCompressedBlock, unCompressedBlock.getMaterial(unCompressedBlock.getDefaultState()), unCompressedBlock.getSoundType(), tabIn);
	}
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock, CreativeTabs tabIn) {
		this(nameIn, unCompressedBlock, unCompressedBlock.getMaterial(unCompressedBlock.getDefaultState()), unCompressedBlock.getSoundType(), tabIn);
	}
	
	public BaseBlockSub(String nameIn, Block unCompressedBlock) {
		this(nameIn, unCompressedBlock, CreativeTabCompressedBlocks.InitCreativeTabs.COMPERESSED_BLOCKS);
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced){
		int meta = stack.getMetadata();
		
		if(meta == 0) { tooltip.add(I18n.format("info.compressed_1.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 1) { tooltip.add(I18n.format("info.compressed_2.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 2) { tooltip.add(I18n.format("info.compressed_3.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 3) { tooltip.add(I18n.format("info.compressed_4.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 4) { tooltip.add(I18n.format("info.compressed_5.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 5) { tooltip.add(I18n.format("info.compressed_6.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 6) { tooltip.add(I18n.format("info.compressed_7.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 7) { tooltip.add(I18n.format("info.compressed_8.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 8) { tooltip.add(I18n.format("info.compressed_9.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 9) { tooltip.add(I18n.format("info.compressed_10.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 10) { tooltip.add(I18n.format("info.compressed_11.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 11) { tooltip.add(I18n.format("info.compressed_12.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 12) { tooltip.add(I18n.format("info.compressed_13.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 13) { tooltip.add(I18n.format("info.compressed_14.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 14) { tooltip.add(I18n.format("info.compressed_15.name", getUnCompressedBlockLocalizedName())); }
		if(meta == 15) { tooltip.add(I18n.format("info.compressed_16.name", getUnCompressedBlockLocalizedName())); }
		
		if(Configs.show_oredict) {
			tooltip.add("OreDictionary: "+ InitHelper.getOreDict(stack).toString());
		}
	}
	public final Block getUnCompressedBlock(){
		return this.unCompressedBlock;
	}
	
	public final String getUnCompressedBlockLocalizedName() {
		return this.unCompressedBlock.getLocalizedName();
	}
	
	
}
