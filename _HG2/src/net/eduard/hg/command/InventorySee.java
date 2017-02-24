
package net.eduard.hg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.hg.manager.HGCommand;

public class InventorySee extends HGCommand  {


	public void command(CommandSender sender, String cmd, String... args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("§c/" + cmd + " <player>");
			} else {

				@SuppressWarnings("deprecation")
				Player player = Bukkit.getPlayer(args[0]);
				if (player == null) {
					p.sendMessage("§cJogador Invalido");
				} else {
					p.openInventory(player.getInventory());
				}
			}
		}		
	}

}
