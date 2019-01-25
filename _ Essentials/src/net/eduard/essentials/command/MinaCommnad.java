package net.eduard.essentials.command;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;

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
			sendUsage(sender);
		} else if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			World world = Mine.getWorld(this.world);
			Mine.teleport(p, world.getSpawnLocation());
			Mine.OPT_SOUND_TELEPORT.create(p);
			sender.sendMessage(message);
		}
		return true;
	}
}
