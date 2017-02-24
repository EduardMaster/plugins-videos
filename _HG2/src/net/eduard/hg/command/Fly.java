
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.hg.manager.HGCommand;

public class Fly extends HGCommand  {


	public void command(CommandSender sender, String cmd, String... args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.getAllowFlight()) {
				p.setFlying(false);
				p.setAllowFlight(false);
				p.sendMessage("§6Fly desativado!");
			}else {
				p.setAllowFlight(true);
				p.sendMessage("§6Fly ativado!");
			}
			
		}		
	}

}
