
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.kitpvp.KitPvP;

public class StaffChatCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (KitPvP.staffChat.contains(p)) {
				KitPvP.staffChat.remove(p);
				p.sendMessage("§6Voce saiu do Chat da Staff");
			} else {
				KitPvP.staffChat.add(p);
				p.sendMessage("§6Voce entrou no Chat da Staff");
			}
		}
		return true;
	}


}
