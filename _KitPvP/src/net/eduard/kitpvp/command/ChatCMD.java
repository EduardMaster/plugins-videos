
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.kitpvp.KitPvP;

public class ChatCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (KitPvP.chat) {
			KitPvP.chat = false;
			API.all("§6O Chat foi desabilidado!");
		}else {
			KitPvP.chat = true;
			API.all("§6O Chat foi habilidado!");
		}
		return true;
	}

}
