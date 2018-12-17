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
		p.teleport(SpawnPlugin.getInstance().getConfigs().getLocation("Spawn"));
		p.sendMessage(SpawnPlugin.getInstance().message("Spawn teleport"));
		SpawnPlugin.getInstance().getConfigs().getSound("Sound on teleport").create(p);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.noConsole(sender)) {
			Player p = (Player) sender;
			if (SpawnPlugin.getInstance().getConfigs().contains("Spawn")) {
				if (SpawnPlugin.getInstance().getConfigs().getBoolean("Teleport delay")) {
					int delay = SpawnPlugin.getInstance().getConfigs().getInt("Teleport delay seconds");
					Mine.TIME.asyncDelay(new Runnable() {
						@Override
						public void run() {
							teleport(p);
						}
					}, delay * 20L);
					p.sendMessage(SpawnPlugin.getInstance().message("Spawn delay teleport").replace("$time", "" + delay));
				} else
					teleport(p);

			} else {
				p.sendMessage(SpawnPlugin.getInstance().message("Spawn not setted"));

			}

		}
		return true;
	}

}
