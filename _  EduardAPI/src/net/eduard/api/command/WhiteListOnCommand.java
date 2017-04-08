
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class WhiteListOnCommand extends SubCommands {

	public String message = "§6Voce ativou a Lista §fBranca";

	public WhiteListOnCommand() {
		super("on", "enable");

	}

	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		Bukkit.setWhitelist(true);
		API.chat(sender,message);
	}

}
