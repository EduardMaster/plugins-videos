
package net.eduard.template.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.eduard.template.Main;

public class TeleportBowReloadCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
		Main.reload();
		sender.sendMessage("§aAs configurações do TeleportBow foram recarregadas!");
		return true;
	}

}
