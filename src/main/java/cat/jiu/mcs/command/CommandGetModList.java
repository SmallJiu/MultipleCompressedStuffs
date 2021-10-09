package cat.jiu.mcs.command;
import cat.jiu.mcs.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class CommandGetModList extends BaseCommand.CommandNormal {
	
	public CommandGetModList(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		JiuUtils.entity.sendMessage(sender, "Has " + Loader.instance().getModList().size() + " Mod load");
		JiuUtils.entity.sendMessage(sender, "This is load mod name: ");
		for(ModContainer mod : Loader.instance().getModList()){
			JiuUtils.entity.sendMessage(sender, "> " + mod.getName());
		}
		JiuUtils.entity.sendMessage(sender, "========Linkage mod=========");
		JiuUtils.entity.sendMessage(sender, "ThermalFoundation: " + Loader.isModLoaded("thermalfoundation"));
		JiuUtils.entity.sendMessage(sender, "EnderIO: " + Loader.isModLoaded("enderio"));
	}
}
