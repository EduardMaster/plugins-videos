
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;
import net.eduard.api.util.Extras;

public class BroadcastCommand extends Commands {
	public String message = "§f$> $message";
	public BroadcastCommand() {
		super("broadcast");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		StringBuilder builder = new StringBuilder();
		int id = 0;
		for (String arg : args) {
			if (id == 0)
				id++;

			else
				builder.append(" ");

			builder.append(API.toChatMessage(arg));
		}
		API.all(this.message.replace("$>", Extras.getArrowRight())
				.replace("$message", builder.toString()));
		return true;
	}

}
