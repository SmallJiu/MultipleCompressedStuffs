package cat.jiu.mcs.command;

import cat.jiu.core.util.base.BaseCommand;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.util.TestModel;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.text.TextComponentKeybind;

class CommandTestMode extends BaseCommand.CommandNormal {
	public CommandTestMode(String name, int level) {
		super(name, MCS.MODID, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender.canUseCommand(4, this.name)
		|| sender instanceof CommandBlockBaseLogic
		|| sender instanceof MinecraftServer
		|| sender instanceof RConConsoleSource) {
			TestModel.test = !TestModel.test;
			MCS.getLogOS().info("Test Model is " + (TestModel.test ? "Enable" : "Disable"));
			sender.sendMessage(new TextComponentKeybind("Test Model is " + (TestModel.test ? "Enable" : "Disable")));
		}
	}
}
