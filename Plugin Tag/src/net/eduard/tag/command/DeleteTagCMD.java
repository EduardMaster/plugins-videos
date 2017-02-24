package net.eduard.tag.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.tag.Main;

public class DeleteTagCMD extends CMD {
	public DeleteTagCMD() {
		getCommand().setPermission("tag.delete");
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0)
			return false;
		String name = args[0];
		if (Main.exists(name)) {
			Main.delete(name);
			sender.sendMessage(Main.config.message("DeleteTag").replace("$tag", name));
		} else {
			sender.sendMessage(Main.config.message("NoTag").replace("$tag", name));
		}
		return true;
	}

}
