package net.eduard.spawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.spawn.Main;

public class SetSpawnCMD extends CMD {
	public SetSpawnCMD() {
		getCommand().setPermission("spawn.set");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			Main.config.set("SpawnLocation", p.getLocation());
			Main.config.saveConfig();
			p.sendMessage(Main.config.message("SetSpawn"));

		}
		return false;
	}

}
