
package net.eduard.hg.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.hg.manager.HGCommand;

public class TeleportAll extends HGCommand  {


	public void command(CommandSender sender, String cmd, String... args) {
		if (sender instanceof Player)
		{
			Player p = (Player) sender;
			for (Player player:Bukkit.getOnlinePlayers())
			{
				if (p!=player) {
					player.teleport(p);
					player.sendMessage("§6Você foi teleportado para o jogador "+p.getName());
				}
				
			}
			p.sendMessage("§6Todos jogadores foram teleportados ate você!");
			
			
		}		
	}

}
