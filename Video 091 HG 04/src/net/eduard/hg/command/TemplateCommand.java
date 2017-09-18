
package net.eduard.hg.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TemplateCommand implements CommandExecutor {

	public TemplateCommand() {
	}

	public TemplateCommand(CommandSender sender, String[] args) {

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		return true;
	}

}
