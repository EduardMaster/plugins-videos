
package net.eduard.warp.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.warp.Main;
import net.eduard.warp.Warp;

public class SetWarpCommand implements CommandExecutor {

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
		if (Main.warps.containsKey(name.toLowerCase())) {
			sender.sendMessage("§cJá existe esse Warp §4" + name);
		} else {
			Warp warp = new Warp();
			warp.setWarpLocation(p.getLocation());
			warp.setWarpName(name);
			warp.setWarpMessage("§6Voce foi teleportado para o " + name);
			Main.warps.put(name.toLowerCase(), warp);
			sender.sendMessage("§aWarp criada §2"+name);
		}
		
		return true;
	}

}
