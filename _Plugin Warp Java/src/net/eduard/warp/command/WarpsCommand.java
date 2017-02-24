
package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.warp.Main;
import net.eduard.warp.Warp;

public class WarpsCommand extends CMD {
	public WarpsCommand() {
		getCommand().setPermission("warp.list");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (Warp.hasWarps()) {
			sender.sendMessage(Main.config.message("Warps").replace("$warps", Warp.getWarps()));
		} else {
			sender.sendMessage(Main.config.message("NoWarps"));
		}

		return true;

	}
}
