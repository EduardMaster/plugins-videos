package net.eduard.parkour;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.server.minigame.GameMap;
import net.eduard.api.setup.Mine;

public class ComandoPrincipal implements CommandExecutor {

	private Parkour parkour;

	public ComandoPrincipal(Parkour parkour) {
		this.parkour = parkour;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (Mine.onlyPlayer(sender)) {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("§c/parkour help");
			} else {
				String sub = args[0];
				if (sub.equalsIgnoreCase("create")) {
					if (args.length == 1) {
						sender.sendMessage("§c/parkour create <parkour>");
					} else {
						String name = args[1];
						if (parkour.hasMap(name)) {
							parkour.chat("AlreadyExists", p);
						} else {
							parkour.createNewMap(p, name);
						}
					}
				} else if (sub.equalsIgnoreCase("delete")) {
					if (args.length == 1) {
						sender.sendMessage("§c/parkour delete <parkour>");
					} else {
						String name = args[1];

						if (parkour.existsMap(name)) {
							parkour.removeMap(name);
							parkour.chat("Delete", p);
						} else {
							parkour.chat("Invalid", p);
						}
					}
				} else if (sub.equalsIgnoreCase("setlobby")) {
					parkour.setLobby(p.getLocation());
					parkour.chat("SetLobby", p);
				} else if (sub.equalsIgnoreCase("lobby")) {
					if (parkour.hasLobby()) {
						p.teleport(parkour.getLobby());
						parkour.chat("Lobby", p);
					} else {
						parkour.chat("NoLobby", p);
					}
				} else if (sub.equalsIgnoreCase("setspawn")) {
					if (args.length == 1) {
						p.sendMessage("§c/parkour setspawn <parkour>");
					} else {
						String name = args[1];
						if (parkour.hasMap(name)) {
							GameMap map = parkour.getMap(name);
							map.setSpawn(p.getLocation());
							parkour.chat("SetSpawn", p);
						} else {
							parkour.chat("Invalid", p);
						}
					}
				} else if (sub.equalsIgnoreCase("setend")) {
					if (args.length == 1) {
						p.sendMessage("§c/parkour setend <parkour>");
					} else {
						String name = args[1];
						if (parkour.hasMap(name)) {
							GameMap map = parkour.getMap(name);
							map.getLocations().put("end", p.getLocation());
							parkour.chat("SetEnd", p);
						} else {
							parkour.chat("Invalid", p);
						}
					}
				}else if (sub.equalsIgnoreCase("help")) {
					sender.sendMessage(
							"§b§l-=-=-=-=-=-=-=- §6§lHELP §b§l-=-=-=-=-=-=-=-=");
					if (sender.hasPermission("parkour.admin")) {
						sender.sendMessage(
								"§a/parkour create|delete §b<arena>");
						sender.sendMessage("§a/parkour setspawn §b<arena>");
						sender.sendMessage("§a/parkour setend §b<arena>");
						sender.sendMessage("§a/parkour reload");
						sender.sendMessage("§a/parkour setlobby");
					}
					sender.sendMessage("§a/parkour play|jogar");
					sender.sendMessage("§a/parkour help");
					sender.sendMessage("§a/parkour lobby");

				}else {
					p.sendMessage("§c/parkour help");
				}
			}
		

		
		}
		return false;
	}

}
