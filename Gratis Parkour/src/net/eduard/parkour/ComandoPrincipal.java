package net.eduard.parkour;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.api.API;

public class ComandoPrincipal implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (command.getName().equalsIgnoreCase("parkour")) {
			if (args.length == 0) {

			} else {
				String sub = args[0];
				if (sub.equalsIgnoreCase("help")) {
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
				} else if (sub.equalsIgnoreCase("create")) {
					if (args.length == 1) {
						sender.sendMessage("§c/parkour create <parkour>");
					} else {
						String name = args[1];
						// if (Arena.exists(name)) {
						// p.sendMessage(Arena.message("AlreadyExists"));
						// } else {
						// Arena.newItem(p, name);
						// }
					}
				} else if (sub.equalsIgnoreCase("delete")) {
					if (args.length == 1) {
						sender.sendMessage("§c/parkour delete <parkour>");
					} else {
						String name = args[1];

						if (Arena.exists(name)) {
							Arena arena = Arena.getArena(name);
							sender.sendMessage(arena.chat("Delete"));
							Arena.delete(name);
						} else {
							sender.sendMessage(Arena.message("Invalid"));
						}
					}
				} else if (sub.equalsIgnoreCase("setend")) {
					if (API.onlyPlayer(sender)) {
						Player p = (Player) sender;
						if (args.length == 1) {
							p.sendMessage("§c/parkour setend <parkour>");
						} else {
							String name = args[1];
							if (Arena.exists(name)) {
								Arena arena = Arena.getArena(name);
								arena.setEnd(p.getLocation());
								p.sendMessage(arena.chat("SetEnd"));
							} else {
								p.sendMessage(Arena.message("Invalid"));
							}
						}
					}
				} else if (sub.equalsIgnoreCase("setend")) {
					if (API.onlyPlayer(sender)) {
						Player p = (Player) sender;
						Arena.setLobby(p.getLocation());
						p.sendMessage(Arena.message("SetLobby"));

					}
				} else if (sub.equalsIgnoreCase("lobby")) {
					if (API.onlyPlayer(sender)) {
						Player p = (Player) sender;
						if (Arena.hasLobby()) {
							p.teleport(Arena.getLobby());
							p.sendMessage(Arena.message("Lobby"));
						} else {
							p.sendMessage(Arena.message("NoLobby"));
						}

					}
				} else if (sub.equalsIgnoreCase("lobby")) {
					if (API.onlyPlayer(sender)) {
						Player p = (Player) sender;

						if (args.length == 1) {
							p.sendMessage("§c/parkour setspawn <parkour>");
						} else {
							String name = args[1];
							if (Arena.exists(name)) {
								Arena arena = Arena.getArena(name);
								arena.setEnd(p.getLocation());
								p.sendMessage(arena.chat("SetSpawn"));
							} else {
								p.sendMessage(Arena.message("Invalid"));
							}
						}
					}
				} else {

				}
			}
		}
		return false;
	}

}
