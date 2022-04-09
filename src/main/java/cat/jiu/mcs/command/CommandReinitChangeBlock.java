package cat.jiu.mcs.command;

import cat.jiu.mcs.util.base.BaseCommand;
import cat.jiu.mcs.util.init.MCSBlocks;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class CommandReinitChangeBlock extends BaseCommand.CommandNormal {
	public CommandReinitChangeBlock(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		long time = System.currentTimeMillis();

		boolean hasError = false;
		String errorMsg = "";

		try {
			MCSBlocks.reinitChangeBlock();
		}catch(Exception e) {
			hasError = true;
			errorMsg = e.getLocalizedMessage().substring(e.getLocalizedMessage().indexOf(":") + 2);
			e.printStackTrace();
		}

		if(hasError) {
			sender.sendMessage(new TextComponentTranslation("Has error, this is error message: "));
			TextComponentTranslation text1 = new TextComponentTranslation(errorMsg);
			sender.sendMessage(text1.setStyle(text1.getStyle().setColor(TextFormatting.RED)));
		}else {
			time = System.currentTimeMillis() - time;
			TextComponentTranslation text = new TextComponentTranslation("Reload successful! (took " + time + " ms)");
			sender.sendMessage(text.setStyle(text.getStyle().setColor(TextFormatting.GREEN)));
		}
	}
}
