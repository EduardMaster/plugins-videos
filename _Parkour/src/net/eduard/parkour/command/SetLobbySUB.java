package net.eduard.parkour.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.parkour.Arena;

public class SetLobbySUB extends CMD {
	
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			Arena.setLobby(p.getLocation());
			p.sendMessage(Arena.message("SetLobby"));

		}
		return true;
	}

}
