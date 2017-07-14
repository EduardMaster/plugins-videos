package net.eduard.parkour.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.parkour.Arena;

public class LobbySUB extends CMD{
	
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player)sender;
			if (Arena.hasLobby()) {
				p.teleport(Arena.getLobby());
				p.sendMessage(Arena.message("Lobby"));
			}else {
				p.sendMessage(Arena.message("NoLobby"));
			}
			
			
		}
		return true;
	}

}
