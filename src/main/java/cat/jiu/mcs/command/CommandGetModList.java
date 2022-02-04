//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.command;

import cat.jiu.mcs.util.base.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentKeybind;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class CommandGetModList extends BaseCommand.CommandNormal {
	
	public CommandGetModList(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		sender.sendMessage(new TextComponentKeybind("Has " + Loader.instance().getModList().size() + " Mod load"));
		sender.sendMessage(new TextComponentKeybind("This is load mod name: "));
		for(ModContainer mod : Loader.instance().getModList()){
			sender.sendMessage(new TextComponentKeybind("> " + mod.getName()));
		}
		sender.sendMessage(new TextComponentKeybind("========Linkage mod========="));
		sender.sendMessage(new TextComponentKeybind("ThermalFoundation: " + Loader.isModLoaded("thermalfoundation")));
		sender.sendMessage(new TextComponentKeybind("EnderIO: " + Loader.isModLoaded("enderio")));
	}
}
