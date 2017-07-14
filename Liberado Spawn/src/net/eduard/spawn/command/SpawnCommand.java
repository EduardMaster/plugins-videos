package net.eduard.spawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.spawn.Main;

public class SpawnCommand extends CMD {
	
	public SpawnCommand() {
		super("spawn");
	}

	public void teleport(Player p) {
		p.teleport(Main.getPlugin().getConfigs().getLocation("SpawnLocation"));
		p.sendMessage(Main.getPlugin().message("Spawn"));
		Main.getPlugin().getConfigs().getSound("Sound.OnTeleport").create(p);

	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			if (Main.getPlugin().getConfigs().contains("SpawnLocation")) {
				if (Main.getPlugin().getConfigs().getBoolean("spawnWithDelay")) {
					API.TIME.delay(Main.getPlugin().getConfigs().getInt("delayInSeconds")*20L,new Runnable() {
						public void run() {
							teleport(p);
						}
					});
					int delay = Main.getPlugin().getConfigs().getInt("delayInSeconds");
					p.sendMessage(Main.getPlugin().message("DelaySpawn").replace("$time", "" + delay));
				} else
					teleport(p);

			} else {
				p.sendMessage(Main.getPlugin().message("NoSpawn"));

			}

		}
		return true;
	}

}
