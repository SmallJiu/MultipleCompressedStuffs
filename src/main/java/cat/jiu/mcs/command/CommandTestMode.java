package cat.jiu.mcs.command;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.TestModel;
import cat.jiu.mcs.util.base.BaseCommand;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.text.TextComponentKeybind;

public class CommandTestMode extends BaseCommand.CommandNormal {
	public CommandTestMode(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender.canUseCommand(4, this.name)
		|| sender instanceof CommandBlockBaseLogic
		|| sender instanceof MinecraftServer
		|| sender instanceof RConConsoleSource) {
			TestModel.test = !TestModel.test;
			MCS.instance.log.info("Test Model is " + (TestModel.test ? "Enable" : "Disable"));
			sender.sendMessage(new TextComponentKeybind("Test Model is " + (TestModel.test ? "Enable" : "Disable")));
		}
	}
}
