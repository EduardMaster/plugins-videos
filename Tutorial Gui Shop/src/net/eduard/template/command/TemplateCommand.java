
package net.eduard.template.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;

public class TemplateCommand extends CMD {

	public TemplateCommand() {
		super("comando");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
	
		return true;
	}

}
