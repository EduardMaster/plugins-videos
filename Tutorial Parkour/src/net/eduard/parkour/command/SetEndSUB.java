package net.eduard.parkour.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.parkour.Arena;

public class SetEndSUB extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 1) {
				p.sendMessage("§c/parkour setend <parkour>");
			} else {
				String name = args[1];
				if (Arena.exists(name)) {
					Arena arena = Arena.getArena(name);
					arena.setEnd(p.getLocation());
					p.sendMessage(arena.chat("SetEnd"));
				} else {
					p.sendMessage(Arena.message("Invalid"));
				}
			}
		}

		return true;
	}

}
