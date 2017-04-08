
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.Commands;

public class ToggleOnlineCommand extends Commands {
	public String messageOn = "§6Voce esta visivel!";
	public String messageOff = "§6Voce esta invisivel!";
	public ToggleOnlineCommand() {
		super("toggleonline");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (label.equalsIgnoreCase("invs")
					| label.equalsIgnoreCase("invisible")
					| label.equalsIgnoreCase("esconder")
					| label.equalsIgnoreCase("desaparecer")) {

				API.chat(p,messageOn);
				API.hide(p);
			} else {
				API.show(p);
				API.chat(p,messageOff);
			}
		}
		return true;
	}

}
