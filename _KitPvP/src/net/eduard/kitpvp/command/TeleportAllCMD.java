
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class TeleportAllCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			for (Player d : API.getPlayers()) {
				if (d.equals(p))
					continue;
				d.teleport(p);
				d.sendMessage("§6O $player§6 te teleportou!".replace("$player", p.getDisplayName()));
			}
			p.sendMessage("§6Voce teleportou todos ate você!");
		}
		return true;
	}

}
