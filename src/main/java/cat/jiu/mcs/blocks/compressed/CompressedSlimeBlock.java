//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.blocks.compressed;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseBlockSub;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CompressedSlimeBlock extends BaseBlockSub {
	public CompressedSlimeBlock(String nameIn, ItemStack unCompressedItem) {
		super(nameIn, unCompressedItem);
	}
	
	public void onLanded(World world, Entity entity) {
		IBlockState state = world.getBlockState(entity.getPosition().add(0,-0.5,0));
		int meta = JiuUtils.item.getMetaFormBlockState(state)+1;
		
        if (entity.isSneaking()) {
            super.onLanded(world, entity);
        }else if (entity.motionY < 0.0D) {
            entity.motionY = -(entity.motionY-(0.0124D * meta));
            if (!(entity instanceof EntityLivingBase)) {
                entity.motionY *= 2.31D;
            }
        }
    }
	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		Blocks.SLIME_BLOCK.onFallenUpon(worldIn, pos, entityIn, fallDistance);
    }
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		Blocks.SLIME_BLOCK.onEntityWalk(worldIn, pos, entityIn);
	}
}
