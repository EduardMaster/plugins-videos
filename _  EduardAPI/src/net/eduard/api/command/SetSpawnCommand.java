package net.eduard.api.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.config.Config;
import net.eduard.api.manager.CMD;

public class SetSpawnCommand extends CMD {
	public String message = "§cVoce setou o Spawn!";
	public SetSpawnCommand() {
		super("setspawn");
	}
	public Config config = new Config("spawn.yml");
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			config.set("spawn", p.getLocation());
			ConfigSection.chat(p, message);
		}

		return true;
	}
	@EventHandler
	public void event(PlayerRespawnEvent e) {
		if (config.contains("spawn")) {
			e.setRespawnLocation(config.getLocation("spawn"));
		}
	}

}
