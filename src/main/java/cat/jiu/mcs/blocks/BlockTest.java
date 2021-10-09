package cat.jiu.mcs.blocks;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.base.BaseBlockNormal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTest extends BaseBlockNormal {

	public BlockTest() {
		super("test_block");
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
}
