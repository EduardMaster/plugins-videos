
package net.eduard.api.command.staff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class WhiteListHelpCommand extends CMD {

	private List<String> messages = new ArrayList<>();

	public WhiteListHelpCommand() {
		super("help", "ajuda");
		messages.add("§a      Tutorial      ");
		messages.add("§a/whitelist help|list");
		messages.add("§a/whitelist on|off");
		messages.add("§a/whitelist add|remove <player>");

	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		for (String line : messages) {
			ConfigSection.chat(sender,line);
		}
		
		return true;
	}

}
