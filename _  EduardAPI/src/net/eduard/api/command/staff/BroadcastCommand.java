
package net.eduard.api.command.staff;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class BroadcastCommand extends CMD {
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

			builder.append(ConfigSection.toChatMessage(arg));
		}
		ConfigSection.all(this.message.replace("$message", builder.toString()));
		return true;
	}

}
