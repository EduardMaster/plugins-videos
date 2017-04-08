
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class WhiteListAddCommand extends SubCommands {
	public WhiteListAddCommand() {
		super("add", "adicionar");
	}
	public String message = "§6Voce adicionou o §e$player§6 na Lista §fBranca";

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
		target.setWhitelisted(true);
		API.chat(sender,message.replace("$player", target.getName()));
	}

}
