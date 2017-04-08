
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;

public class ClearChatCommand extends Commands {
	public String message = "§6O $sender limpou o Chat!";
	public int size = 50;
	public ClearChatCommand() {
		super("clearchat");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		for (int i = 0; i < size; i++) {
			API.broadcast(" ");
		}
		API.all(message.replace("$sender", sender.getName()));
		return true;
	}

}
