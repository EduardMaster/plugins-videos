package net.eduard.drops.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.drops.Main;

public class DropsReloadCMD extends CMD
{

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage("§aOs drops foram recarregados!");
		Main.reload();
		return true;
	}
}
