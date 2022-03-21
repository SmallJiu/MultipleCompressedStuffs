package cat.jiu.mcs.blocks;

import cat.jiu.core.util.base.BaseBlock;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTest extends BaseBlock.Normal {
	public BlockTest() {
		super(MCS.MODID, "test_block", CreativeTabs.TOOLS);
		MCSResources.BLOCKS.add(this);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
		String[] res = this.getBlockModelResourceLocation();
		if(world.isRemote) {
			MCS.instance.log.info("ResLeg: " + res.length + " FileDirName: " + res[0] + " FileName: " + res[1]);
		}
		return true;
	}

	@Override
	public ItemBlock getRegisterItemBlock() {
		return new BaseBlock.BaseBlockItem(this);
	}
}
