package cat.jiu.mcs.util.base;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.server.command.CommandTreeBase;

public class BaseCommand {
	public static abstract class CommandNormal extends CommandBase {
		protected final String name;
		protected final boolean checkPermission;
		protected final int level;
		
		public CommandNormal(String name){
			this(name, true, 0);
		}

		public CommandNormal(String name, boolean checkPermission, int level){
			this.name = name;
			this.checkPermission = checkPermission;
			this.level = level;
		}

		@Override
		public String getName(){
			return this.name;
		}

		@Override
		public String getUsage(ICommandSender sender) {
			return "command.mcs." + this.getName() +".info";
		}
		
		@Override
		public int getRequiredPermissionLevel() {
			return this.level;
		}

		@Override
		public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
			return this.checkPermission;
		}

		@Override
		public abstract void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;
	}
	
	public static class CommandTree extends CommandTreeBase {
		protected final String name;
		protected final boolean canAddCommand;
		protected final boolean checkPermission;
		protected final int level;
		protected final ICommand[] commands;
		
		public CommandTree(ICommand[] commands, String name, boolean canAddComman, boolean checkPermission, int level) {
			this.commands = commands;
			this.name = name;
			this.canAddCommand = canAddComman;
			this.checkPermission = checkPermission;
			this.level = level;
			
			for(ICommand cmd : commands){
				if(cmd != null){
					super.addSubcommand(cmd);
				}
			}
		}
		
		public ICommand[] getCommands() {
			return this.commands;
		}

		@Override
		public String getName() {
			return this.name;
		}

		@Override
		public String getUsage(ICommandSender sender) {
			return "command.mcs." + this.getName() +".info";
		}

		@Override
		public void addSubcommand(ICommand command) {
			if(!this.canAddCommand){
				if(command.getClass().getPackage() != this.getClass().getPackage()) {
					throw new UnsupportedOperationException("Don't add sub-commands to /" + this.name + ", create your own command !");
				}
			}else {
				super.addSubcommand(command);
			}
		}

		@Override
		public int getRequiredPermissionLevel() {
			return this.level;
		}

		@Override
		public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
			return this.checkPermission;
		}
	}
}
