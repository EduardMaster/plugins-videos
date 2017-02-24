package net.eduard.spawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.manager.CMD;
import net.eduard.api.util.SimpleEffect;
import net.eduard.spawn.Main;

public class SpawnCMD extends CMD {

	public void teleport(Player p) {
		p.teleport(Main.config.getLocation("SpawnLocation"));
		p.sendMessage(Main.config.message("Spawn"));
		Main.config.getSound("Sound.OnTeleport").create(p);

	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			if (Main.config.contains("SpawnLocation")) {
				if (Main.config.getBoolean("spawnWithDelay")) {
					new Game(Main.config.getInt("delayInSeconds")).delay(new SimpleEffect() {
						public void effect() {
							teleport(p);
						}
					});
					int delay = Main.config.getInt("delayInSeconds");
					p.sendMessage(Main.config.message("DelaySpawn").replace("$time", "" + delay));
				} else
					teleport(p);

			} else {
				p.sendMessage(Main.config.message("NoSpawn"));

			}

		}
		return true;
	}

}
