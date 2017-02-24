package net.eduard.tag.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.tag.Main;

public class TagsCMD extends CMD {
	public TagsCMD() {
		getCommand().setPermission("tag.list");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (Main.hasTags()) {
			Main.showTags(sender);
		} else {
			sender.sendMessage(Main.config.message("noTags"));
		}

		return true;
	}

}
