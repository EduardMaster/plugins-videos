package zLuck.zComandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import zLuck.zScoreboard.Scoreboards;

public class Score implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("score")) {
			if (!Scoreboards.score.contains(p)) {
				Scoreboards.score.add(p);
				p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
				p.sendMessage("§a§lAVISO: §aSua scoreboard nao irá mais aparecer");
			} else {
				Scoreboards.score.remove(p);
				p.sendMessage("§a§lAVISO: §aSua scoreboard irá aparecer");
			}
		}
		return false;
	}
}
