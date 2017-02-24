
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.kitpvp.KitPvP;

public class PvPCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (KitPvP.pvp) {
			KitPvP.pvp = false;
			API.all("§6O PvP foi desabilidado!");
		}else {
			KitPvP.pvp = true;
			API.all("§6O PvP foi habilidado!");
		}
		return true;
	}

}
