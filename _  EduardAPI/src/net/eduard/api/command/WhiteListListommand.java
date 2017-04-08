
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.API;
import net.eduard.api.manager.SubCommands;

public class WhiteListListommand extends SubCommands {

	public String message = " §6Jogadores na WhiteList: ";
	
	public WhiteListListommand() {
		super("list", "jogadores","lista");

	}

	@SuppressWarnings("deprecation")
	@Override
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		API.chat(sender, message);
		for (OfflinePlayer player:Bukkit.getWhitelistedPlayers()){
			API.chat(sender,player.getName());
		}
	}

}
