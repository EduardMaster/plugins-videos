package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.CommandManager;

public class JailCommand extends CommandManager {

	public JailCommand() {
		super("jail", "prender");
	}

	private List<Player> players = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;

			if (args.length == 0) {
				sendUsage(p);

			} else {

				if (Mine.existsPlayer(sender, args[0])) {
					Player target = Bukkit.getPlayer(args[0]);

					if (players.contains(target)) {
						removePrison(target);
						sender.sendMessage("§aO jogador " + target.getName() + " foi absolvido!");
					} else {
						prison(target);
						sender.sendMessage("§aO jogador " + target.getName() + " foi aprisionado!");
					}

				}
			}
		}

		return false;
	}

	public void prison(Player player) {
		players.add(player);

		Location loc = player.getLocation();
		loc = loc.add(0, 10, 0);

		player.playSound(player.getLocation(), Sound.WITHER_SPAWN, 2, 1);
		loc.clone().add(0, 0, 0).getBlock().setType(Material.BEDROCK);
		loc.clone().add(0, 3, 0).getBlock().setType(Material.BEDROCK);
		loc.clone().add(0, 1, -1).getBlock().setType(Material.BEDROCK);
		loc.clone().add(-1, 1, 0).getBlock().setType(Material.BEDROCK);
		loc.clone().add(1, 1, 0).getBlock().setType(Material.BEDROCK);
		loc.clone().add(0, 1, 1).getBlock().setType(Material.BEDROCK);
		player.teleport(loc.clone().add(-0.4, 1, -0.4));
		player.sendMessage("§cVoce foi Aprisionado!");

	}

	public void removePrison(Player player) {
		players.remove(player);
		Location loc = player.getLocation();
		player.sendMessage("§aVoce foi solto da Prisão!");
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 2, 1);
		loc.clone().add(0, -1, 0).getBlock().setType(Material.AIR);
		loc.clone().add(0, 2, 0).getBlock().setType(Material.AIR);
		loc.clone().add(0, 0, 1).getBlock().setType(Material.AIR);
		loc.clone().add(1, 0, 0).getBlock().setType(Material.AIR);
		loc.clone().add(0, 0, -1).getBlock().setType(Material.AIR);
		loc.clone().add(-1, 0, 0).getBlock().setType(Material.AIR);

	}

}
