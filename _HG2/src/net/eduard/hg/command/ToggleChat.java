
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;

import net.eduard.hg.manager.HG;
import net.eduard.hg.manager.HGCommand;

public class ToggleChat extends HGCommand {

	public void command(CommandSender sender, String cmd, String... args) {
		if (HG.chatEnabled) {
			HG.chatEnabled=false;
			sender.sendMessage("§aChat desativado!");
		}else {
			HG.chatEnabled=true;
			sender.sendMessage("§aChat ativado!");
		}
	}

}
