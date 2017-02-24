
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.kitpvp.Main;

public class ReportCMD extends CMD {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length <= 1) {
			return false;
		}
		if (API.existsPlayer(sender, args[0])) {
			Player target = API.getPlayer(args[0]);
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < args.length; i++) {
				builder.append(args[i] + " ");
			}
			API.broadcast(Main.config.message("Report").replace("$player", target.getDisplayName()).replace("$reason",
					builder.toString()), "kitpvp.report");
		}

		return true;
	}
}
