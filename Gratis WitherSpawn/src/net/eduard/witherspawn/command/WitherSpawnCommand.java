package net.eduard.witherspawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;
import net.eduard.witherspawn.Main;

public class WitherSpawnCommand extends CommandManager {
	public WitherSpawnCommand() {
		super("spawn");
		// TODO Auto-generated constructor stub
	}
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		if (Main.wither == null) {
			if (Main.hasSpawn()) {
				Main.spawnWither();
				sender.sendMessage(Main.config.message("Spawn"));
			} else {
				sender.sendMessage(Main.config.message("NoSpawn"));
			}

		} else {
			sender.sendMessage(Main.config.message("Spawned"));
		}
	}

}
