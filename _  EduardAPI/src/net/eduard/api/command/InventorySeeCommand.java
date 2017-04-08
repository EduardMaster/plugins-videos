
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;

public class InventorySeeCommand extends Commands {
	public String messageTarget = "§6Voce abriu o Inventario do §e$target";
	public InventorySeeCommand() {
		super("inventorysee");
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			return false;
		}
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (API.existsPlayer(sender, args[0])) {
				Player target = API.getPlayer(args[0]);
				p.openInventory(target.getInventory());
				API.chat(p,messageTarget.replace("$target", target.getDisplayName()));
			}
		}

		return true;
	}
}
