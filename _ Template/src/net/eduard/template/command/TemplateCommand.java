
package net.eduard.template.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.Commands;

public class TemplateCommand extends Commands {

	public TemplateCommand() {
		super("comando");
	}
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
	
		return true;
	}

}
