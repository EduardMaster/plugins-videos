package net.eduard.eduardapi.command;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CMD;

public class GotoCommand extends CMD {

	public GotoCommand() {
	}
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0)
			return false;

		if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (API.existsWorld(sender, args[0])) {
				World world = API.getWorld(args[0]);
				API.teleport(p, world.getSpawnLocation());
				API.SOUND_TELEPORT.create(p);
				p.sendMessage("§6Voce foi teleportado para o Mundo §e" + world.getName());
			}
		}

		return true;
	}
}
