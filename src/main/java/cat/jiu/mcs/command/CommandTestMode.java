package cat.jiu.mcs.command;
import cat.jiu.mcs.util.JiuUtils;
import cat.jiu.mcs.util.TestModel;
import cat.jiu.mcs.util.base.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandTestMode extends BaseCommand.CommandNormal {

	public CommandTestMode(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		TestModel.test = !TestModel.test;
		JiuUtils.entity.sendMessage(sender, "" + TestModel.test);
    }
}
