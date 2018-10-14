package net.eduard.spawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.spawn.SpawnPlugin;

public class SpawnCommand extends CommandManager {

	public SpawnCommand() {
		super("spawn");
		
	}

	public void teleport(Player p) {
		p.teleport(SpawnPlugin.getPlugin().getConfigs().getLocation("Spawn"));
		p.sendMessage(SpawnPlugin.getPlugin().message("Spawn teleport"));
		SpawnPlugin.getPlugin().getConfigs().getSound("Sound on teleport").create(p);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.noConsole(sender)) {
			Player p = (Player) sender;
			if (SpawnPlugin.getPlugin().getConfigs().contains("Spawn")) {
				if (SpawnPlugin.getPlugin().getConfigs().getBoolean("Teleport delay")) {
					int delay = SpawnPlugin.getPlugin().getConfigs().getInt("Teleport delay seconds");
					Mine.TIME.delays(delay * 20L, new Runnable() {
						@Override
						public void run() {
							teleport(p);
						}
					});
					p.sendMessage(SpawnPlugin.getPlugin().message("Spawn delay teleport").replace("$time", "" + delay));
				} else
					teleport(p);

			} else {
				p.sendMessage(SpawnPlugin.getPlugin().message("Spawn not setted"));

			}

		}
		return true;
	}

}
