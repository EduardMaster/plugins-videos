
package net.eduard.api.command.essentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;

public class ListCommand extends CommandManager {

	public String message = "§aTem $players jogadores online!";
	public ListCommand() {
		super("list");
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		API.chat(sender, message.replace("$players", ""+API.getPlayers().size()));
		return true;
	}
}
