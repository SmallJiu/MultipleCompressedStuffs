package cat.jiu.mcs.command;

import cat.jiu.core.util.base.BaseCommand;
import cat.jiu.mcs.MCS;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentKeybind;

public class CommandHelp extends BaseCommand.CommandNormal {
	public CommandHelp(String name, int level) {
		super(name, MCS.MODID, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
			sender.sendMessage(new TextComponentKeybind("MCS has this command: "));
			sender.sendMessage(new TextComponentKeybind("> /mcs blocks   | get mcs registry all blocks name"));
			sender.sendMessage(new TextComponentKeybind("> /mcs getore   | get you held item OreDictionary"));
			sender.sendMessage(new TextComponentKeybind("> /mcs loadmod  | get minecraft load mod name"));
			sender.sendMessage(new TextComponentKeybind("> /mcs help     | get this helper"));
		}
	}
}
