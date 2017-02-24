
package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.warp.Main;
import net.eduard.warp.Warp;

public class WarpCommand extends CMD {
	public WarpCommand() {
		getCommand().setPermission("warp.use");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return false;
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				sender.sendMessage("§c/warp <name>");
			} else {
				String name = args[0];
				if (Warp.exists(name)) {
					Warp.getWarp(name).teleport(p);
				} else {
					sender.sendMessage(Main.config.message("NoWarp").replace("$warp", name));
				}
			}

		}
		return true;
	}
}
