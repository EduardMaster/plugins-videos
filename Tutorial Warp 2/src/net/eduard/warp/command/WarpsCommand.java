
package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.eduard.warp.Main;

public class WarpsCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Main.warps.size() == 0) {
			sender.sendMessage("§cNão existem Warps!");

		} else {
			
			/// Não tenho certeza se funciona
			sender.sendMessage("§aWarps criados: " + Main.warps.keySet().toString().replace("[", "").replace("]", ""));
		}
		return true;
	}

}
