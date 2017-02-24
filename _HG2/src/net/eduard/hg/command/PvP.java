
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;

import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGCommand;

public class PvP extends HGCommand {

	public void command(CommandSender sender, String cmd, String... args) {
		if (HG.pvpEnabled) {
			HG.pvpEnabled = false;
			sender.sendMessage("§6O PvP foi desativado!");
		} else {
			HG.pvpEnabled = true;
			sender.sendMessage("§6O PvP foi ativado!");
			;
		}

	}

}
