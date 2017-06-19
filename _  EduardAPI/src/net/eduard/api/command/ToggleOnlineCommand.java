
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.api.manager.GameAPI;
import net.eduard.api.util.Cs;

public class ToggleOnlineCommand extends CMD {
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

				Cs.chat(p,messageOn);
				GameAPI.hide(p);
			} else {
				GameAPI.show(p);
				Cs.chat(p,messageOff);
			}
		}
		return true;
	}

}
