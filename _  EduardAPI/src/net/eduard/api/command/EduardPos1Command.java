
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class EduardPos1Command extends SubCommands {

	public EduardPos1Command() {
		super("pos1");
	}
	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		if (API.onlyPlayer(sender)){
			Player p = (Player)sender;
			API.POSITION1.put(p, p.getLocation());
			p.sendMessage("§bEduardAPI §6Posição 1 setada!");
		}
	
	}

}
