
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class ClearInventoryCMD extends CMD {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player)sender;
			API.clearInventory(p);
			p.sendMessage("§6Seu inventario foi limpo!");
		}
	
	return true;}
}
