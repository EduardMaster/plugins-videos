
package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.warp.Main;
import net.eduard.warp.Warp;

public class SetWarpCommand extends CMD {
	public SetWarpCommand() {
		getCommand().setPermission("warp.set");
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return false;
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				sender.sendMessage("§c/setwarp <warp>");
			} else {
				String name = args[0];
				Warp warp = Warp.getWarp(name);
				warp.setSpawn(p.getLocation());
				sender.sendMessage(Main.config.message("SetWarp").replace("$warp", name));
			}

		}
		return true;
	}
}
