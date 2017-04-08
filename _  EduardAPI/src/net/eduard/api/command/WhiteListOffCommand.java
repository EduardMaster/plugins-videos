
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class WhiteListOffCommand extends SubCommands {

	public String message = "§6Voce desativou a Lista §fBranca";

	public WhiteListOffCommand() {
		super("off", "disable");

	}

	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		Bukkit.setWhitelist(false);
		API.chat(sender,message);
	}

}
