//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.command;

import cat.jiu.mcs.blocks.BlockTest;
import cat.jiu.mcs.util.base.BaseBlockSub;
import cat.jiu.mcs.util.base.BaseCommand;
import cat.jiu.mcs.util.init.MCSBlocks;

import net.minecraft.block.Block;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentKeybind;

public class CommandGetMCSBlocks extends BaseCommand.CommandNormal {
	public CommandGetMCSBlocks(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		for(BaseBlockSub sub_block : MCSBlocks.SUB_BLOCKS) {
			for(Block normal_block : MCSBlocks.NORMAL_BLOCKS) {
				if(!(normal_block instanceof BlockTest)) {
					sender.sendMessage(new TextComponentKeybind(normal_block.getLocalizedName()));
				}
			}
			sender.sendMessage(new TextComponentKeybind(sub_block.getLocalizedName()));
		}
		
		long regBlocks = 0L;
		
		for(int i = 0; i < MCSBlocks.SUB_BLOCKS.size(); ++i) {
			regBlocks += ((i + 1) * 16);
		}
		
		sender.sendMessage(new TextComponentKeybind("Has " + regBlocks + " Register Compressed"));
	}
}
