
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGCommand;

public class Timer extends HGCommand  {

	public void command(CommandSender sender, String cmd, String... args) {
		if (args.length == 0) {
			sender.sendMessage("§c/" + cmd + " <seconds>");
		} else {
			int time = API.toInt(args[0]);
			HG.time = time;
			sender.sendMessage("§6O tempo foi trocado para "+time +" segundos");
		}
	}

}
