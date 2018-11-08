package net.eduard.curso.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.curso.apis.RankAPI;
import net.eduard.curso.manager.Rank;

public class ComandoRankup implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			Rank rank = RankAPI.getManager().getRank(p);
			p.sendMessage("§cSeu rank é " + rank.getName());
//			RankAPI.save();

		}
		return true;
	}

}
