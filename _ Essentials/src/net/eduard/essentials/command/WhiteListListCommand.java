
package net.eduard.essentials.command;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.manager.CommandManager;

public class WhiteListListCommand extends CommandManager {

	public String message = " §6Jogadores na WhiteList: ";
	public String prefix = " §a";

	public WhiteListListCommand() {
		super("list", "jogadores", "lista");

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(message);
		for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
			sender.sendMessage(prefix + player.getName());
		}
		return true;
	}

	public static Map<Player, Long> delay = new HashMap<Player, Long>();

}
