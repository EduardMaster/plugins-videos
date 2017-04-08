package net.eduard.witherspawn.command;

import org.bukkit.command.CommandSender;

import net.eduard.api.manager.SubCommands;

public class Help extends SubCommands {
	 
	public Help() {
		super("help","ajuda");
		// TODO Auto-generated constructor stub
	}

	public void command(CommandSender sender, String[] args) {
		sender.sendMessage("§2-=-=-=-= §a§lWitherSpawn §2=-=-=-=-=");
		sender.sendMessage("§b/wither setspawn §3Seta o Spawn do Wither");
		sender.sendMessage("§b/wither spawn §3Spawna o Wither");
	}

}
