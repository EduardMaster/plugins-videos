
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGCommand;

public class Skit extends HGCommand {

	public void command(CommandSender sender, String cmd, String... args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			for (Player player : HG.players) {
				if (player != p) {
					player.getInventory().setArmorContents(p.getInventory().getArmorContents());
					player.getInventory().setContents(p.getInventory().getContents());

				}
			}
			p.sendMessage("§6Seu inventario foi aplicado para todos jogadores!");
		}

	}

}
