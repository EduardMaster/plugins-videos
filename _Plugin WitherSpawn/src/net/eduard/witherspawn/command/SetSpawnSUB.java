package net.eduard.witherspawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.witherspawn.WitherSpawn;

public class SetSpawnSUB extends CMD {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			WitherSpawn.setWitherSpawn(p.getLocation());
			p.sendMessage(WitherSpawn.config.message("SetSpawn"));
		}
		return true;
	}
}
