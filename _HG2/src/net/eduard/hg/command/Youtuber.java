
package net.eduard.hg.command;

import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.hg.Main;
import net.eduard.hg.manager.HGCommand;

public class Youtuber extends HGCommand {

	public void command(CommandSender sender, String cmd, String... args) {
		if (Main.config.contains("Requisitos para Tag Youtuber")) {
			for (String message:Main.config.getConfig().getStringList("Requisitos para Tag Youtuber")) {
				sender.sendMessage(API.toChatMessage(message));
			}
		}
	}

}
