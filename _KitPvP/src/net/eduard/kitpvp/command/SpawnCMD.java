
package net.eduard.kitpvp.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.manager.CMD;
import net.eduard.api.util.SimpleEffect;
import net.eduard.kitpvp.Main;

public class SpawnCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			final Player p = (Player) sender;
			p.teleport(Main.kit.getSpawn());
			Location current = p.getLocation();
			p.sendMessage("§6Voce vai ser teleportado para o Spawn em alguns segundos!");
			p.sendMessage("§6Não se mova, se não irá cancelar!");
			new Game(3).delay(new SimpleEffect() {

				public void effect() {
					if (API.equals(current, p.getLocation())) {
						p.teleport(Main.kit.getSpawn());
						p.sendMessage("§6Voce vai ser teleportado para o Spawn em alguns segundos!");
					}
				}
			});

		}
		return true;
	}

}
