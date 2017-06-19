
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.api.util.Cs;

public class TellCommand extends CMD {

	public String message = "§aPara: §f$target$> §7$message";
	public String messageTarget = "§De: §f$player$> §7$message";
public TellCommand() {
	super("tell");
}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length <= 1) {
			return false;
		}
		if (API.existsPlayer(sender, args[0])) {
			Player target = API.getPlayer(args[0]);
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				builder.append(args[i] + " ");
			}
			String message = Cs.toChatMessage(builder.toString());
			sender.sendMessage(this.message.replace("$target", target.getName())
					.replace("$>", Cs.getArrowRight())
					.replace("$message", message));
			target.sendMessage(
					messageTarget.replace("$player", sender.getName())
							.replace("$>", Cs.getArrowRight())
							.replace("$message", message));
		}

		return true;
	}
}
