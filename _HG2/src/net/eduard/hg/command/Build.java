
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;

import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGCommand;

public class Build extends HGCommand {

	public void command(CommandSender sender, String cmd, String... args) {
		if (HG.buildEnabled) {
			HG.buildEnabled = false;
			sender.sendMessage("§6O Build foi desativado!");
		} else {
			HG.buildEnabled = true;
			sender.sendMessage("§6O Build foi ativado!");
		}		
	}

}
