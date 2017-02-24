
package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.warp.Main;
import net.eduard.warp.Warp;

public class DeleteWarpCommand extends CMD {

	public DeleteWarpCommand() {
		getCommand().setPermission("warp.delete");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return false;
		String name = args[0];
		if (Warp.exists(name)) {
			Warp warp = Warp.getWarp(name);
			Warp.delete(name);
			sender.sendMessage(warp.chat("DeleteWarp"));
		} else {
			sender.sendMessage(Main.config.message("NoWarp").replace("$warp", name));
		}
		return true;

	}
}
