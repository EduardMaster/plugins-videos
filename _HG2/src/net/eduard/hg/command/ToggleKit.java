
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;

import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGCommand;

public class ToggleKit extends HGCommand  {

	public void command(CommandSender sender, String cmd, String... args) {
		if (HG.kitsEnabled) {
			HG.kitsEnabled =false;
			sender.sendMessage("§aKits desativados!");
		}else {
			HG.kitsEnabled =true;
			sender.sendMessage("§aKits liberados!");
			
		}		
	}

}
