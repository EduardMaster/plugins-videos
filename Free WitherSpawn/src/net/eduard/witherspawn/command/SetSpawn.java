package net.eduard.witherspawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;
import net.eduard.witherspawn.Main;

public class SetSpawn extends CMD {
	public SetSpawn() {
		super("setspawn","setarspawn");
		// TODO Auto-generated constructor stub
	}
	public void command(CommandSender sender, Command command, String label,
			String[] args) {
		if (API.noConsole(sender)) {
			Player p = (Player) sender;
			Main.setWitherSpawn(p.getLocation());
			p.sendMessage(Main.config.message("SetSpawn"));
		}

	}
}
