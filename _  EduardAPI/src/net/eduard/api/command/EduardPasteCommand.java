
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class EduardPasteCommand extends SubCommands {

	public EduardPasteCommand() {
		super("paste","colar");
		
	}
	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (!API.MAPS.containsKey(p)) {
				p.sendMessage(
						"§bEduardAPI §2Primeiro copie um Mapa:§a /e copy");
				return;
			}
			API.MAPS.get(p).paste(p.getLocation());
			p.sendMessage("§bEduardAPI §6Mapa colado com sucesso!");
		}

	}

}
