package cat.jiu.mcs.command;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class CommandHelp extends BaseCommand.CommandNormal {

	public CommandHelp(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
			JiuUtils.entity.sendMessage(sender, "MCS has this command: ");
			JiuUtils.entity.sendMessage(sender, "> /mcs blocks   | get mcs registry all blocks name");
			JiuUtils.entity.sendMessage(sender, "> /mcs getore   | get you held item OreDictionary");
			JiuUtils.entity.sendMessage(sender, "> /mcs loadmod  | get minecraft load mod name");
			JiuUtils.entity.sendMessage(sender, "> /mcs help     | get this helper");
			
		}
    }
}
