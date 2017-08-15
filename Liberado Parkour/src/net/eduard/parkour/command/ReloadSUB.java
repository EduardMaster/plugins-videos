package net.eduard.parkour.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.parkour.Arena;

public class ReloadSUB extends CMD {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Arena.reload();
		sender.sendMessage(Arena.message("Reload"));

		return true;
	}

}
