
package net.eduard.essentials.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;

public class WhiteListOnCommand extends CommandManager {

	public String message = "§aVoce ativou a WhiteList";

	public WhiteListOnCommand() {
		super("on", "enable");

	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Bukkit.setWhitelist(true);
		sender.sendMessage(message);
		return true;
	}

}
