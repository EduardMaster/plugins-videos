
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class TagCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (p.getAllowFlight()) {
				p.setFlying(false);
				p.setAllowFlight(false);
				p.sendMessage("§6Voce não pode mais voar!");

			} else {
				p.setAllowFlight(true);
				p.sendMessage("§6Voce pode voar!");
			}

		}
		return true;
	}
		

}
