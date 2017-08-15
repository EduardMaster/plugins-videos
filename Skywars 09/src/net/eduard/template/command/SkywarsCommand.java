
package net.eduard.template.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.eduard.template.Skywars;

public class SkywarsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§aDigite: /skywars help");
		} else {
			// /skywars arg 
			String cmd = args[0];
			if (cmd.equalsIgnoreCase("create")) {
				if (args.length >= 2){
					String name = args[1];
					if (Skywars.hasMap(name)){
						sender.sendMessage("§cJá existe este Skywars!");
					}else{
						Skywars.createMap(name);
						sender.sendMessage("§aVoce criou o mapa de Skywars: §2"+name);
					}
				}else{
					sender.sendMessage("§aDigite: /skywars create <arena>");
				}
			} else if (cmd.equalsIgnoreCase("delete")) {
				if (args.length >= 2){
					String name = args[1];
						if (Skywars.hasMap(name)){
							Skywars.deleteMap(name);
							sender.sendMessage("§aVoce deletou o mapa de Skywars: "+name);
						}else{
							sender.sendMessage("§cNão existe este Skywars!");
						}
				}else{
					sender.sendMessage("§aDigite: /skywars delete <arena>");
				}
				

			} else if (cmd.equalsIgnoreCase("help")) {
				sender.sendMessage("§a============= SKYWARS =============");
				if (sender.hasPermission("skywars.admin")){
					sender.sendMessage("§a/skywars create <arena> §bCriar um Mapa");
					sender.sendMessage("§a/skywars delete <arena> §bDeletar um Mapa");
				}
				sender.sendMessage("§a/skywars help §bVer Informações dos Comandos");
			}else{
				sender.sendMessage("§aDigite: /skywars help");
			}
		}
		return true;
	}

}
