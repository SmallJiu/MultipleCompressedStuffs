package cat.jiu.mcs.command;

import cat.jiu.core.util.base.BaseCommand;
import cat.jiu.mcs.MCS;

import net.minecraft.command.ICommand;

public class MCSCommand extends BaseCommand.CommandTree {
	public MCSCommand() {
		super(new ICommand[]{
				new CommandGetOreDict("ore", 2),
				new CommandTestMode("test", 0),
				new CommandReinitChangeBlock("recb", 2),
				new CommandGetTexture()
			}, "mcs", MCS.MODID, false, 2);
	}
}
