
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;

public class UnBanIPCommand extends Commands {

	public String message = "§6O IP §e$ip §6foi desbanido!";

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		String ip = args[0];
		Bukkit.unbanIP(ip);
		API.chat(sender,message.replace("$ip", ip));

		return true;
	}
}
