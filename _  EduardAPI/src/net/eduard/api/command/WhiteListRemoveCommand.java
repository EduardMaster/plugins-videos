
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class WhiteListRemoveCommand extends SubCommands {
	public WhiteListRemoveCommand() {
		super("remove", "remover");
	}
	public String message = "§6Voce removeu o §e$player§6 da Lista §fBranca";

	@SuppressWarnings("deprecation")
	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		if (args.length <= 1) {
			sender.sendMessage("§c/" + label + " " + args[0]+" <player>");
			return;
		}

		String name = args[1];
		OfflinePlayer target = Bukkit.getOfflinePlayer(name);
		target.setWhitelisted(false);
		API.chat(sender,message.replace("$player", target.getName()));
	}

}
