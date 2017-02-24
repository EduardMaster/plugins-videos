package net.eduard.kit.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.kit.Kit;
import net.eduard.kit.Main;

public class KitsCommand extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Kit.hasKits()) {
			sender.sendMessage(Main.config.message("Kits").replace("$kits", Kit.getKits()));
		} else {
			sender.sendMessage(Main.config.message("NoKits"));
		}
		return true;
	}

}
