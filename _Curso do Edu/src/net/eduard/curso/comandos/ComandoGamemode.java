package net.eduard.curso.comandos;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ComandoGamemode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				if (p.getGameMode() == GameMode.SURVIVAL ) { 
					
				}else {
					
				}
			} else if (args.length == 1) {

			} else {

			}

		}

		return false;
	}

}
