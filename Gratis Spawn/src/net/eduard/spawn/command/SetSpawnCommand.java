package net.eduard.spawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.core.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.spawn.Main;

public class SetSpawnCommand extends CommandManager {
	public SetSpawnCommand() {
		super("setspawn");
		getCommand().setPermission("spawn.set");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.noConsole(sender)) {
			Player p = (Player) sender;
			Main.getPlugin().getConfigs().set("SpawnLocation", p.getLocation());
			Main.getPlugin().getConfigs().saveConfig();
			p.sendMessage(Main.getPlugin().message("SetSpawn"));

		}
		return true;
	}

}
