
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.api.util.Cs;

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

			builder.append(Cs.toChatMessage(arg));
		}
		Cs.all(this.message.replace("$>", Cs.getArrowRight())
				.replace("$message", builder.toString()));
		return true;
	}

}
