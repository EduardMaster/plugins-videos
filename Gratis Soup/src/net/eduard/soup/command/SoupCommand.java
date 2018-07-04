package net.eduard.soup.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.lib.core.Mine;
import net.eduard.api.lib.manager.CommandManager;
import net.eduard.soup.Main;

public class SoupCommand extends CommandManager {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (Mine.noConsole(sender)) {
			Player p = (Player) sender;
			for (ItemStack item : p.getInventory().getContents()) {
				if (item == null) {
					p.getInventory().addItem(Main.soup);
				}
			}
			p.sendMessage(Main.config.message("soup"));
		}
		return true;
	}

}
