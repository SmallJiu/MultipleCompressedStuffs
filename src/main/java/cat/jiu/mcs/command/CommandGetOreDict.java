package cat.jiu.mcs.command;
import java.util.List;

import cat.jiu.mcs.util.JiuUtils;
import cat.jiu.mcs.util.base.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

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
					JiuUtils.entity.sendMessage(sender, "OreDict Entries:");
					JiuUtils.entity.sendMessage(sender, " " + heldItem.getItem().getRegistryName() + "@" + heldItem.getItemDamage() + ":");
					for(String ore : ores) {
						JiuUtils.entity.sendMessage(sender, "  - <ore:" + ore + ">");
					}
				} else {
					JiuUtils.entity.sendMessage(sender, "No OreDict Entries");
				}
			}
		}
    }
}
