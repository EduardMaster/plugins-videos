package net.eduard.witherspawn.command;

import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.witherspawn.WitherSpawn;

public class SpawnSUB extends CMD {
	public void command(CommandSender sender, String[] args) {
		if (WitherSpawn.wither == null) {
			if (WitherSpawn.hasSpawn()) {
				WitherSpawn.spawnWither();
				sender.sendMessage(WitherSpawn.config.message("Spawn"));
			} else {
				sender.sendMessage(WitherSpawn.config.message("NoSpawn"));
			}

		} else {
			sender.sendMessage(WitherSpawn.config.message("Spawned"));
		}
	}

}
