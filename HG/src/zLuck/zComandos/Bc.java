package zLuck.zComandos;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.piracraft.api.Main;
import zLuck.zUteis.Uteis;

public class Bc implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("bc")) {
			if (Main.isStaff((Player) sender)) {
			    if (args.length >= 1) {
		  	        String bcast = "";
		  	        for (int x = 0; x < args.length; x++) {
		  	          bcast = bcast + args[x] + " ";
		  	        }
		  	        Bukkit.broadcastMessage(Uteis.prefix + " " + ChatColor.GREEN + bcast.replace("&", "§"));
				}
			} else {
				sender.sendMessage(Uteis.sempermissão);
			}
		}
		return false;
	}

}
