package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.warp.Main;
import net.eduard.warp.Warp;

public class WarpReloadCommand extends CMD{
	public WarpReloadCommand() {
		getCommand().setPermission("warp.reload");
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Warp.reloadWarps();
		Main.config.reloadConfig();
		sender.sendMessage(Main.config.message("ReloadWarps"));
		return true;
	}

}
