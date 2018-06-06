
package net.eduard.essentials.command.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;

public class FeedCommand extends CommandManager {
	
	public FeedCommand() {
		super("feed");
		
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		
		return true;
	}

}
