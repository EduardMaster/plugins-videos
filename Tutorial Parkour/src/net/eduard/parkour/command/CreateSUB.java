package net.eduard.parkour.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;
import net.eduard.parkour.Arena;

public class CreateSUB extends SubCommands {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 1) {
				p.sendMessage("§c/parkour create <parkour>");
			} else {
				String name = args[1];
				if (Arena.exists(name)) {
					p.sendMessage(Arena.message("AlreadyExists"));
				} else {
					Arena.newItem(p, name);
				}
			}

		}

		return true;
	}

}
