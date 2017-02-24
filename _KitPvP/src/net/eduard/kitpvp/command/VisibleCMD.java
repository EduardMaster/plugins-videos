
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class VisibleCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			API.show(p);
			p.sendMessage("§6Voce esta visivel!");

		}
		return true;
	}
		

}
