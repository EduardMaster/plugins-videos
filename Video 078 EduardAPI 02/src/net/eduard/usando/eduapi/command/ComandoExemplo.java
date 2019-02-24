
package net.eduard.usando.eduapi.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;

public class ComandoExemplo extends CommandManager {

	public ComandoExemplo() {
		super("comando");
	}
	@Override 
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {
	
		return true;
	}

}
