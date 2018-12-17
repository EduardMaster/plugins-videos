package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JailCommand implements CommandExecutor {

	private List<Player> players = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		Player p = (Player) sender;

		if (sender.hasPermission("nightmc.prender")) {

			if (args.length == 0) {
				sender.sendMessage("§cPor favor, use /" + label + " {Jogador}");

			} else {

				Player target = Bukkit.getPlayer(args[0]);
				if (target == null) {
					sender.sendMessage("§cEste jogador está offline.");

				} else {
					if (players.contains(target)) {
						removePrison(target);
						;
					} else {
						prison(target);
						sender.sendMessage("§aO jogador " + target.getName() + " foi aprisinado!");
					}
				}
			}
		} else {

//			MessageAPI.semPermissaoEssentials(p, "Moderador");

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
