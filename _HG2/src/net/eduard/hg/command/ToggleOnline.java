
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.hg.manager.HGCommand;

public class ToggleOnline extends HGCommand  {
	public void command(CommandSender sender, String cmd, String... args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (cmd.equalsIgnoreCase("invs") | cmd.equalsIgnoreCase("invisible")
					| cmd.equalsIgnoreCase("esconder") | cmd.equalsIgnoreCase("desaparecer")) {
				
				p.sendMessage("§6Voce esta invisivel!");
				API.hide(p);
			}else {
				API.show(p);
				p.sendMessage("§6Voce esta visivel!");
			}
		}
		
	}

}
