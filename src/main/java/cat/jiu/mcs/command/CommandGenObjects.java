package cat.jiu.mcs.command;

import java.util.Collections;
import java.util.List;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseCommand.CommandNormal;
import cat.jiu.mcs.MCS;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

class CommandGenObjects extends CommandNormal {
	public CommandGenObjects(String name, int level) {
		super(name, MCS.MODID, level);
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length < 4) throw new CommandException("cmd.mcs.dev.gen");
		
		BlockPos pos = parseBlockPos(sender, args, 1, true);
		final long max = parseLong(args[0], 1, Long.MAX_VALUE);
		NonNullList<ItemStack> stacks = NonNullList.create();
		World world = sender.getEntityWorld();
		while(true) {
			if(stacks.size() >= max) break;
			Item.REGISTRY.getRandomObject(world.rand).getSubItems(CreativeTabs.SEARCH, stacks);
		}
		JiuUtils.item.spawnAsEntity(world, pos, stacks);
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return args.length > 1 && args.length <= 4 ? getTabCompletionCoordinate(args, 1, targetPos) : Collections.emptyList();
	}
}
