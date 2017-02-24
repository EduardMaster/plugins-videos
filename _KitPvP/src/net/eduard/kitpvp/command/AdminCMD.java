
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.kitpvp.KitPvP;
import net.eduard.kitpvp.Main;

public class AdminCMD extends CMD {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (KitPvP.admins.contains(p)) {
				KitPvP.admins.remove(p);
				API.show(p);
				API.getItems(p);
				p.sendMessage("§6Voce saiu do Modo Admin!");
			} else {
				API.saveItems(p);
				API.refreshAll(p);
				KitPvP.admins.add(p);
				PlayerInventory inv = p.getInventory();
				API.hide(p);
				Main.kit.getSlotNockback().give(inv);
				p.sendMessage("§6Voce entrou no Modo Admin!");
			}
		}

		return true;
	}

}
