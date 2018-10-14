package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.eduard.api.lib.manager.CommandManager;

public class SiteCommand extends CommandManager {
	public List<String> messages = new ArrayList<>();
	public SiteCommand() {
		super("site");
		messages.add("");
		messages.add("§aAcesse o site www.meusite.com");
		messages.add("");
		// TODO Auto-generated constructor stub
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		for (String msg : messages) {
			sender.sendMessage(msg);
		}
		return true;
	}
	

}
