
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class EduardSaveCommand extends CMD {

	public EduardSaveCommand() {
		super("save");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 1){
			sender.sendMessage("§c/e save <name>");
		}else{

			
			if (API.onlyPlayer(sender)) {
				Player p = (Player) sender;
				if (!API.MAPS.containsKey(p)) {
					p.sendMessage(
							"§bEduardAPI §2Primeiro copie um Mapa:§a /e copy");
					return true;
				}
				API.SCHEMATICS.put(args[1].toLowerCase(), API.MAPS.get(p));
				p.sendMessage("§bEduardAPI §6Mapa salvado com sucesso!");
			}
		}
		return true;
	}

}
