
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.kitpvp.Main;

public class YoutuberCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for (String message : Main.kit.getYoutuber()) {
			sender.sendMessage(API.toChatMessage(message));
		}
		return true;
	}

}
