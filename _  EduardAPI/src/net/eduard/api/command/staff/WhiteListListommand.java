
package net.eduard.api.command.staff;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;

public class WhiteListListommand extends CMD {

	public String message = " §6Jogadores na WhiteList: ";
	
	public WhiteListListommand() {
		super("list", "jogadores","lista");

	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		ConfigSection.chat(sender, message);
		for (OfflinePlayer player:Bukkit.getWhitelistedPlayers()){
			ConfigSection.chat(sender,player.getName());
		}
		return true;
	}


}
