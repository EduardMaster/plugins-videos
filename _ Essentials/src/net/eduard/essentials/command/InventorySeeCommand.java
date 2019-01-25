
package net.eduard.essentials.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class InventorySeeCommand extends CommandManager {
	public String messageTarget = "§6Voce abriu o Inventario do §e$target";
	public InventorySeeCommand() {
		super("inventorysee");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (Mine.existsPlayer(sender, args[0])) {
				Player target = Mine.getPlayer(args[0]);
				p.openInventory(target.getInventory());
				sender.sendMessage(messageTarget.replace("$target", target.getDisplayName()));
			}
		}

		return true;
	}
}
