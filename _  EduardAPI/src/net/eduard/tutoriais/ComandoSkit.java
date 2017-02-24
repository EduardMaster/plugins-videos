
package net.eduard.tutoriais;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.manager.CMD;

public class ComandoSkit extends CMD  {

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			for (Player player:Bukkit.getOnlinePlayers()) {
				if (player!=p) {
						player.getInventory().setArmorContents(p.getInventory().getArmorContents());
						player.getInventory().setContents(p.getInventory().getContents());
					
				}
			}
			p.sendMessage("§6Seu inventario foi aplicado para todos jogadores!");
		}
		return true;
	}

	public void command(CommandSender sender, String cmd, String... args) {
		
	}

}
