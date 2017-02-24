
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class BroadcastCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		StringBuilder builder = new StringBuilder();
		for (String arg:args) {
			builder.append(API.toChatMessage(arg));
		}
		API.all(builder.toString());
		return true;
	}

}
