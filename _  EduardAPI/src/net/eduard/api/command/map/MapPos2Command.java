
package net.eduard.api.command.map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;

public class MapPos2Command extends CommandManager {

	public MapPos2Command() {
		super("pos2");

	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			API.POSITION2.put(p, p.getLocation());
			p.sendMessage("§bEduardAPI §6Posição 2 setada!");
		}
		return true;
	}

}
