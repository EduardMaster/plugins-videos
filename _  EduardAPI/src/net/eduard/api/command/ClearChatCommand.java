
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.api.util.Cs;

public class ClearChatCommand extends CMD {
	public String message = "§6O $sender limpou o Chat!";
	public int size = 50;
	public ClearChatCommand() {
		super("clearchat");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		for (int i = 0; i < size; i++) {
			Cs.broadcast(" ");
		}
		Cs.all(message.replace("$sender", sender.getName()));
		return true;
	}

}
