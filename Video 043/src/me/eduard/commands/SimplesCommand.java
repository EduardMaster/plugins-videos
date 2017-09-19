
package me.eduard.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class SimplesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String texto,
		String[] args) {

		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("§cEsse comando e apenas para Players!");
		} else if (args.length == 0) {
			return false;
		} else if (args.length == 1) {
			sender.sendMessage(sender.getName());
		}

		return false;
	}

}
