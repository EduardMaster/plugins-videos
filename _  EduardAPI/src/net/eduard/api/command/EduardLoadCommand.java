
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class EduardLoadCommand extends SubCommands {

	public EduardLoadCommand() {
		super("load","carregar");
	}
	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		if (args.length == 1){
			sender.sendMessage("§c/e load <name>");
		}else{
			if (API.onlyPlayer(sender)) {
				Player p = (Player) sender;
				if (API.SCHEMATICS.containsKey(args[1].toLowerCase())){
					API.MAPS.put(p, API.SCHEMATICS.get(args[1].toLowerCase()));
					p.sendMessage("§bEduardAPI §6Mapa carregado com sucesso!");
				}else{
					p.sendMessage("§bEduardAPI §cMapa invalido: §f"+args[1]);
				}
				
			}
		}
	

	}

}
