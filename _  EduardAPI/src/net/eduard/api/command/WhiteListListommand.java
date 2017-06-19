
package net.eduard.api.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.manager.CMD;
import net.eduard.api.util.Cs;

public class WhiteListListommand extends CMD {

	public String message = " §6Jogadores na WhiteList: ";
	
	public WhiteListListommand() {
		super("list", "jogadores","lista");

	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Cs.chat(sender, message);
		for (OfflinePlayer player:Bukkit.getWhitelistedPlayers()){
			Cs.chat(sender,player.getName());
		}
		return true;
	}


}
