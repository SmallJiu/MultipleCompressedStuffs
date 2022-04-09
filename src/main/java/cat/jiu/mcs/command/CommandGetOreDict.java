package cat.jiu.mcs.command;
import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentKeybind;

public class CommandGetOreDict extends BaseCommand.CommandNormal {

	public CommandGetOreDict(String name, boolean checkPermission, int level) {
		super(name, checkPermission, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(sender.getCommandSenderEntity() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			ItemStack heldItem = player.getHeldItemMainhand();

			if(!heldItem.isEmpty()) {
				List<String> ores = JiuUtils.item.getOreDict(heldItem);

				if(!ores.isEmpty()) {
					player.sendMessage(new TextComponentKeybind("OreDict Entries:"));
					player.sendMessage(new TextComponentKeybind(" " + heldItem.getItem().getRegistryName() + "@" + heldItem.getItemDamage() + ":"));
					for(String ore : ores) {
						player.sendMessage(new TextComponentKeybind("  - <ore:" + ore + ">"));
					}
				}else {
					player.sendMessage(new TextComponentKeybind("No OreDict Entries"));
				}
			}
		}
	}
}
