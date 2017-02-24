
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGCommand;

public class Admin extends HGCommand {
	

	public void command(CommandSender sender, String cmd, String... args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (HG.admins.contains(p)) {
				HG.removeFromAdminState(p);
			}else {
				HG.joinToAdminState(p);
			}

		}
	}

}
