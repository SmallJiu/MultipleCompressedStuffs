package cat.jiu.mcs.command;

import cat.jiu.mcs.blocks.BlockTest;
import cat.jiu.mcs.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseBlockSub;
import cat.jiu.mcs.util.base.BaseCommand;
import cat.jiu.mcs.util.init.MCSBlocks;
import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandGetMCSBlocks extends BaseCommand.CommandNormal {
	public CommandGetMCSBlocks(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		for(BaseBlockSub sub_block : MCSBlocks.SUB_BLOCKS) {
			for(Block normal_block : MCSBlocks.NORMAL_BLOCKS) {
				if(!(normal_block instanceof BlockTest)) {
					JiuUtils.entity.sendMessage(sender, normal_block.getLocalizedName());
				}
			}
			JiuUtils.entity.sendMessage(sender, sub_block.getLocalizedName());
		}
		
		long regBlocks = 0L;
		
		for(int i = 0; i < MCSBlocks.SUB_BLOCKS.size(); ++i) {
			regBlocks = ((i + 1) * 16) + regBlocks;
		}
		
		JiuUtils.entity.sendMessage(sender, "Has " + regBlocks + " Register Compressed");
	}
}
