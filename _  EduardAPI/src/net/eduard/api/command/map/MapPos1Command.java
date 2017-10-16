
package net.eduard.api.command.map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;

public class MapPos1Command extends CommandManager {

	public MapPos1Command() {
		super("pos1");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			API.POSITION1.put(p, p.getLocation());
			p.sendMessage("§bEduardAPI §6Posição 1 setada!");
		}
		return true;
	}

}
