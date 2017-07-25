
package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class FlyCommand extends CMD {
	public String messageOn = "§6Voce pode voar!";
	public String messageOff = "§6Voce não pode mais voar!";
	public String messageTarget = "§6Voce troco o modo de voo do jogador $player";
	public FlyCommand() {
		super("fly");
	}
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			if (API.onlyPlayer(sender)) {
				Player p = (Player) sender;
				if (p.getAllowFlight()) {
					p.setFlying(false);
					p.setAllowFlight(false);
					ConfigSection.chat(p, messageOff);

				} else {
					p.setAllowFlight(true);
					ConfigSection.chat(p, messageOn);
				}
			}
		} else {
			String player = args[0];
			if (API.existsPlayer(sender, player)) {
				Player target = API.getPlayer(player);
				ConfigSection.chat(sender,
						messageTarget.replace("$player", target.getName()));

			}
		}

		return true;
	}

}
