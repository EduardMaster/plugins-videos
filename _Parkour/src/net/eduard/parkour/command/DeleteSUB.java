package net.eduard.parkour.command;

import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.parkour.Arena;

public class DeleteSUB extends CMD{

	public void command(CommandSender sender, String... args) {
		if (args.length == 1) {
			sender.sendMessage("§c/parkour delete <parkour>");
		}else {
			String name = args[1];
			if (Arena.exists(name)) {
				Arena arena = Arena.getArena(name);
				sender.sendMessage(arena.chat("Delete"));
				Arena.delete(name);
			}else {
				sender.sendMessage(Arena.message("Invalid"));
			}
		}
	}

}
