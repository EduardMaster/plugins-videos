package net.eduard.tag.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.dev.Tag;
import net.eduard.api.manager.CMD;
import net.eduard.tag.Main;

public class TagCMD extends CMD {

	public void command(CommandSender sender, String cmd, String... args) {
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				API.removeTag(p);
				sender.sendMessage("§c/"+cmd+" <tag>");
			} else {
				String name = args[0];
				if (Main.exists(name)) {
					Tag tag = Main.getTag(name);
					API.setTag(p,tag);
					p.sendMessage(
							Main.config.message("Tag").replace("$tag", tag.getName()));
				} else {
					sender.sendMessage(Main.config.message("NoTag").replace("$tag", name));
				}
			}
		}
	}

}
