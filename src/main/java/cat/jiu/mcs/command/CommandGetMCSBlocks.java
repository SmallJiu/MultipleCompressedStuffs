package cat.jiu.mcs.command;

import cat.jiu.core.util.base.BaseCommand;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentKeybind;

public class CommandGetMCSBlocks extends BaseCommand.CommandNormal {
	public CommandGetMCSBlocks(String name, int level) {
		super(name, MCS.MODID, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		for(BaseBlockSub sub_block : MCSResources.SUB_BLOCKS) {
			sender.sendMessage(new TextComponentKeybind(sub_block.getLocalizedName()));
		}

		long regBlocks = 0L;

		for(int i = 0; i < MCSResources.SUB_BLOCKS.size(); ++i) {
			regBlocks += ((i + 1) * 16);
		}

		sender.sendMessage(new TextComponentKeybind("Has " + regBlocks + " Register Compressed"));
	}
}
