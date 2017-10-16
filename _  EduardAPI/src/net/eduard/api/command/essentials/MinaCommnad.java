package net.eduard.api.command.essentials;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.manager.CommandManager;
import net.eduard.api.setup.Mine;

public class MinaCommnad extends CommandManager {
	public MinaCommnad() {
		super("mina");
	}
	public String message = "§6Voce foi teleportado para o Mundo Mina mundo de mineração";
	public String world = "mina";

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		if (args.length == 0) {
			API.chat(sender, getUsage());
		} else if (API.onlyPlayer(sender)) {
			Player p = (Player) sender;
			World world = Mine.getWorld(this.world);
			Mine.teleport(p, world.getSpawnLocation());
			API.SOUND_TELEPORT.create(p);
			API.chat(p, message);
		}
		return true;
	}
}
