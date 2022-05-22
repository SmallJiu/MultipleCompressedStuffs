package cat.jiu.mcs.command;

import cat.jiu.core.util.base.BaseCommand;
import cat.jiu.mcs.MCS;

import net.minecraft.command.ICommand;

public class MCSCommand extends BaseCommand.CommandTree {
	public MCSCommand() {
		super(new ICommand[]{
				new CommandGetMCSBlocks("blocks", 0),
				new CommandGetModList("mods", 0),
				new CommandGetOreDict("ore", 2),
				new CommandHelp("help", 0),
				new CommandTestMode("test", 0),
				new CommandReinitChangeBlock("recb", 2),
				new CommandGetTexture()
			}, "mcs", MCS.MODID, false, 2);
	}
}
