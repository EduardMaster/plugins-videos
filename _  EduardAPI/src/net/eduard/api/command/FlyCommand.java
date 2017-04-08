
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;

public class FlyCommand extends Commands {
	public String messageOn = "§6Voce pode voar!";
	public String messageOff = "§6Voce não pode mais voar!";
	public FlyCommand() {
		super("fly");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (p.getAllowFlight()) {
				p.setFlying(false);
				p.setAllowFlight(false);
				API.chat(p,messageOff);

			} else {
				p.setAllowFlight(true);
				API.chat(p,messageOn);
			}
		}
		return true;
	}

}
