package net.eduard.spawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.spawn.SpawnPlugin;

public class SetSpawnCommand extends CommandManager {
	public SetSpawnCommand() {
		super("setspawn");
		setPermission("spawn.set");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.noConsole(sender)) {
			Player p = (Player) sender;
			SpawnPlugin.getPlugin().getConfigs().set("Spawn", p.getLocation());
			SpawnPlugin.getPlugin().getConfigs().saveConfig();
			p.sendMessage(SpawnPlugin.getPlugin().message("Spawn setted"));

		}
		return true;
	}

}
