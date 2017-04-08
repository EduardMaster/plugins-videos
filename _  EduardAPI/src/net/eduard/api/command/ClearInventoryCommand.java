
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;

public class ClearInventoryCommand extends Commands {
	public String message = "§6Seu inventario foi limpo!";
	public String messageTarget = "§6Voce limpou o inventario do §e$player";
	public ClearInventoryCommand() {
		super("clearinventory");
		
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				API.clearInventory(p);
				API.chat(p,message);
			}else 
			return false;
		} else {
			String name = args[0];
			if (API.existsPlayer(sender, name)) {
				Player target = API.getPlayer(name);
				API.chat(sender, messageTarget.replace("$player",
						target.getDisplayName()));
				API.chat(target,message);
				API.clearInventory(target);
			}
		}

		return true;
	}
}
