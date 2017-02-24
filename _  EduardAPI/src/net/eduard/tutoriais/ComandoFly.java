
package net.eduard.tutoriais;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.manager.CMD;

public class ComandoFly extends CMD {

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
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
		return true;
	}

	public void command(CommandSender sender, String cmd, String... args) {
		
	}

}
