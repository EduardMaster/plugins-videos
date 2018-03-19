package net.eduard.live;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoKits implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
		}
		
		
		
		return false;
	}

}
