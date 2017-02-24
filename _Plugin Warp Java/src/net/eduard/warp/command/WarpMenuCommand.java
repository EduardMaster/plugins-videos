package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.warp.Warp;

public class WarpMenuCommand extends CMD {
	public WarpMenuCommand() {
		getCommand().setPermission("warp.menu");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			Warp.open(p);
		}
		return true;
	}


}
