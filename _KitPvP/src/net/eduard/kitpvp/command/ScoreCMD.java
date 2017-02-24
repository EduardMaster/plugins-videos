
package net.eduard.kitpvp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.kitpvp.KitPvP;

public class ScoreCMD extends CMD {
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (p.getScoreboard() == API.getMainScoreboard()) {
				API.resetScoreboard(p);
				p.sendMessage("§6Voce desativou o Scoreboard!");
			} else {
				KitPvP.scoreboards.get(p).setScoreboard(p);
				p.sendMessage("§6Voce ativou o Scoreboard!");
			}
		}
		return true;
	}

}
