
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;
import net.eduard.api.util.Space;

public class EduardCopyCommand extends SubCommands {

	public EduardCopyCommand() {
		super("copy","copiar");
	}
	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (!API.POSITION1.containsKey(p)) {
				p.sendMessage("§bEduardAPI §2Posição 1 não foi setada!");
				return;
			}
			if (!API.POSITION2.containsKey(p)) {
				p.sendMessage("§bEduardAPI §2Posição 2 não foi setada!");
				return;
			}
			API.MAPS.put(p, Space.copy(API.POSITION1.get(p),
					API.POSITION2.get(p), p.getLocation(), true));
			API.POSITION1.put(p, p.getLocation());
			p.sendMessage("§bEduardAPI §6Mapa copiado!");
		}

	}

}
