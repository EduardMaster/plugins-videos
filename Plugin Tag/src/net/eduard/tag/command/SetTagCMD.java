package net.eduard.tag.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.dev.Tag;
import net.eduard.api.manager.CMD;
import net.eduard.tag.Main;

public class SetTagCMD extends CMD {
	public SetTagCMD() {
		getCommand().setPermission("tag.set");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return false;
		String name = args[0];
		if (Main.exists(name)) {
			sender.sendMessage(Main.config.message("NoTag").replace("$tag", name));
		} else {
			String prefix = args[1];
			String suffix = "";
			if (args.length > 2) {
				suffix = args[2];
			}
			Tag tag = new Tag(prefix, suffix).setName(name);
			Main.create(name, tag);
			sender.sendMessage(API
					.toChatMessage(Main.config.message("SetTag").replace("$prefix", prefix).replace("$suffix", suffix))
					.replace("$tag", name));
		}
		return true;
	}

}
