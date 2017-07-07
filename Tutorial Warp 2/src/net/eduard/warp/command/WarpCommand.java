
package net.eduard.warp.command;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.warp.Main;
import net.eduard.warp.Warp;

public class WarpCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
		if (!(sender instanceof Player)) {
			
			sender.sendMessage("§cComandos para Jogadores");
			
			return true;
		}
		Player p = (Player)sender;
		if (args.length == 0) {
			return false;
		}
		String name = args[0];
		if (!Main.warps.containsKey(name.toLowerCase())) {
			sender.sendMessage("§cNão existe esse Warp §4" + name);
		} else {
			
			Warp warp = Main.warps.get(name.toLowerCase());
			p.teleport(warp.getWarpLocation());
			p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
			if (warp.getWarpMessage()==null) {
				Bukkit.broadcastMessage("Merda");
			}
			p.sendMessage(warp.getWarpMessage().replace("$warp", warp.getWarpName()));
		}
		
		return true;
	}

}
