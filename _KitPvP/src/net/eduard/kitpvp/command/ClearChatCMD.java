
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class ClearChatCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for (int i = 0; i < 50; i++) {
			API.broadcast(" ");
		}
		API.all("§6O $sender limpou o Chat!".replace("$sender", sender.getName()));
		return true;
	}

}
