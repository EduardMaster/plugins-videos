
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.kitpvp.Main;

public class SetSpawnCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			Main.kit.setSpawn(p.getLocation());
			p.sendMessage("§aVoce setou o Spawn do KitPvP");

		}
		return true;
	}

}
