
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class EduardSaveCommand extends SubCommands {

	public EduardSaveCommand() {
		super("save");
	}
	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		if (args.length == 1){
			sender.sendMessage("§c/e save <name>");
		}else{

			
			if (API.onlyPlayer(sender)) {
				Player p = (Player) sender;
				if (!API.MAPS.containsKey(p)) {
					p.sendMessage(
							"§bEduardAPI §2Primeiro copie um Mapa:§a /e copy");
					return;
				}
				API.SCHEMATICS.put(args[1].toLowerCase(), API.MAPS.get(p));
				p.sendMessage("§bEduardAPI §6Mapa salvado com sucesso!");
			}
		}
	

	}

}
