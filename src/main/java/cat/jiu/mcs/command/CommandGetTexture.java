package cat.jiu.mcs.command;

import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseCommand;
import cat.jiu.mcs.MCS;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandGetTexture extends BaseCommand.CommandNormal {
	public CommandGetTexture() {
		super("texture", MCS.MODID);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 1) {
			JiuUtils.entity.sendMessage(sender, "Modids: ");
			for(Entry<String, JsonElement> modids : MCS.getTextures().entrySet()) {
				JiuUtils.entity.sendMessage(sender, " > " + modids.getKey());
			}
		}else if(args.length >= 1) {
			String modid = args[0];
			JsonObject modids = null;

			if(MCS.getTextures().has(modid)) {
				modids = MCS.getTextures().get(modid).getAsJsonObject();
			}

			if(modids == null) {
				throw new CommandException("Mod Not Found!");
			}
			JiuUtils.entity.sendMessage(sender, modid);
			if(args.length >= 2) {
				String blockoritem_boi = args[1];
				if(args.length >= 3) {
					String name = args[2];
					if(!MCS.getTextures().get(modid).getAsJsonObject().get(blockoritem_boi).getAsJsonObject().has(name)) {
						throw new CommandException("Item Not Found!");
					}
					JsonElement texture = MCS.getTextures().get(modid).getAsJsonObject().get(blockoritem_boi).getAsJsonObject().get(name);
					if(texture.isJsonPrimitive()) {
						JiuUtils.entity.sendMessage(sender, "    > name: " + name + ", texture: " + texture.getAsString());
					}
				}else {
					if(!modids.has(blockoritem_boi)) {
						throw new CommandException("Entry Not Found!");
					}
					JsonElement bois = modids.get(blockoritem_boi);
					if(bois.isJsonObject()) {
						JiuUtils.entity.sendMessage(sender, "  > " + blockoritem_boi);
						for(Entry<String, JsonElement> items : bois.getAsJsonObject().entrySet()) {
							String name = items.getKey();
							JsonElement texture = items.getValue();
							if(texture.isJsonPrimitive()) {
								JiuUtils.entity.sendMessage(sender, "    > name: " + name + ", texture: " + texture.getAsString());
							}
						}
					}
				}
			}else {
				for(Entry<String, JsonElement> boi : modids.entrySet()) {
					String key = boi.getKey();
					JsonElement e = boi.getValue();

					JiuUtils.entity.sendMessage(sender, " > " + key);
					if(e.isJsonObject()) {
						for(Entry<String, JsonElement> items : e.getAsJsonObject().entrySet()) {
							String name = items.getKey();
							JsonElement texture = items.getValue();
							if(texture.isJsonPrimitive()) {
								JiuUtils.entity.sendMessage(sender, "  > name: " + name + ", texture: " + texture.getAsString());
							}
						}
					}
				}
			}
		}
	}
}
