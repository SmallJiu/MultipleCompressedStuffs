package cat.jiu.mcs.command;

import cat.jiu.mcs.util.base.BaseCommand;
import net.minecraft.command.ICommand;

public class MCSCommand extends BaseCommand.CommandTree {
	public MCSCommand() {
		super(new ICommand[] {
				new CommandGetMCSBlocks("blocks", true, 0),
				new CommandGetModList("mods", true, 0),
				new CommandGetOreDict("ore", true, 2),
				new CommandHelp("help", true, 0),
				new CommandTestMode("test", true, 4),
				new CommandReinitChangeBlock("recb", true, 2)
		}, "mcs", false, true, 2);
	}
}
