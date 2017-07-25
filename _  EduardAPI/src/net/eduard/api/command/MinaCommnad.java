package net.eduard.api.command;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.eduard.api.manager.CMD;
import net.eduard.api.manager.GameAPI;
import net.eduard.api.manager.WorldAPI;

public class MinaCommnad extends CMD {
	public MinaCommnad() {
		super("mina");
	}
	public String message = "§6Voce foi teleportado para o Mundo Mina mundo de mineração";
	public String world = "mina";

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length == 0) {
			ConfigSection.chat(sender, getUsage());
		} else if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			World world = WorldAPI.getWorld(this.world);
			GameAPI.teleport(p, world.getSpawnLocation());
			API.SOUND_TELEPORT.create(p);
			ConfigSection.chat(p, message);
		}
		return true;
	}
}
