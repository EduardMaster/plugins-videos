
package net.eduard.skywars.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.skywars.Main;
import net.eduard.skywars.manager.Skywars;

public class SkywarsCommand implements CommandExecutor {
	public static String noConsole = Main.message("no-console", "");
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§c/skywars help");
		} else {
			String cmd = args[0];
			if (cmd.equalsIgnoreCase("help") | cmd.equalsIgnoreCase("ajuda")) {
				if (sender.hasPermission("skywars.admin")) {
					sender.sendMessage("§a/skywars setlobby");
				}
				sender.sendMessage("§a/skywars help");
			} else if (cmd.equalsIgnoreCase("lobby")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					player.teleport(Skywars.getLobby());
				} else {
					sender.sendMessage(noConsole);
				}
			} else if (cmd.equalsIgnoreCase("create")
					| cmd.equalsIgnoreCase("criar"))

				if (args.length == 1) {
					sender.sendMessage("§c/skywars create <skywars>");
				} else {
					String name = args[1];
					if (Skywars.has(name)) {
						sender.sendMessage(Main.message("exists-arena", name));
					} else {
						Skywars.create(name);
						sender.sendMessage(Main.message("create-arena", name));

					}

				}
			else if (cmd.equalsIgnoreCase("delete")
					| cmd.equalsIgnoreCase("deletar")) {

				if (args.length == 1) {
					sender.sendMessage("§c/skywars delete <skywars>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(Main.message("create-arena", name));
					} else {
						Skywars.delete(name);
						sender.sendMessage(Main.message("delete-arena", name));
					}
				}

			} else if (cmd.equalsIgnoreCase("setlobby")) {

				if (sender instanceof Player) {
					Player player = (Player) sender;

					Skywars.setLobby(player.getLocation());
					sender.sendMessage(Main.message("set-lobby", ""));
				} else {
					sender.sendMessage(noConsole);
				}

			} else if (cmd.equalsIgnoreCase("addspawn")) {

				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (args.length == 1) {
						sender.sendMessage("§c/skywars addspawn <skywars>");
					} else {
						String name = args[1];
						if (!Skywars.has(name)) {
							sender.sendMessage(Main.message("no-arena", name));
						} else {
							Skywars.get(name).getSpawns()
									.add(player.getLocation());

							sender.sendMessage(
									Main.message("add-spawn", name));
						}
					}
				} else {
					sender.sendMessage(noConsole);
				}

			} else if (cmd.equalsIgnoreCase("resetspawns")) {
				if (args.length == 1) {
					sender.sendMessage("§c/skywars resetspawns <skywars>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(Main.message("no-arena", name));
					} else {
						Skywars.get(name).getSpawns().clear();
						sender.sendMessage(Main.message("reset-spawns", name));
					}
				}

			} else if (cmd.equalsIgnoreCase("min")) {

				if (args.length == 2) {
					sender.sendMessage("§c/skywars min <skywars> <amount>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(Main.message("no-arena", name));
					} else {
						int value = 2;
						try {
							value = Integer.valueOf(args[2]);
						} catch (Exception e) {
						}
						Skywars.get(name).setMinPlayersAmount(value);
						sender.sendMessage(
								Main.message("set-min-players-amount", name)
										.replace("$value", "" + value));
					}
				}

			} else if (cmd.equalsIgnoreCase("max")) {

				if (args.length == 2) {
					sender.sendMessage("§c/skywars max <skywars> <amount>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(
								Main.message("set-max-players-amount", name));
					} else {
						int value = 10;
						try {
							value = Integer.valueOf(args[2]);
						} catch (Exception e) {
						}
						Skywars.get(name).setMaxPlayersAmount(value);
						sender.sendMessage(
								Main.message("set-max-players-amount",
										name).replace("$value", "" + value));
					}
				}

			} else if (cmd.equalsIgnoreCase("pos1")) {

				if (args.length == 1) {
					sender.sendMessage("§c/skywars pos1 <skywars>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(Main.message("no-arena", name));
					} else {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							Skywars.get(name).getFeast()
									.setFirstPosition(p.getLocation());
							sender.sendMessage(
									Main.message("set-feast-low", name));

						} else {
							sender.sendMessage(noConsole);
						}
					}
				}

			} else if (cmd.equalsIgnoreCase("pos2")) {
				if (args.length == 1) {
					sender.sendMessage("§c/skywars pos2 <skywars>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(Main.message("no-arena", name));
					} else {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							Skywars.get(name).getFeast()
									.setSecondPosition(p.getLocation());
							sender.sendMessage(
									Main.message("set-feast-high", name));

						} else {
							sender.sendMessage(noConsole);
						}
					}
				}

			} else if (cmd.equalsIgnoreCase("update")) {
				if (args.length == 1) {
					sender.sendMessage("§c/skywars update <skywars>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(Main.message("no-arena", name));
					} else {
						Skywars.get(name).getMap().copy(false);
						sender.sendMessage(
								Main.message("update-blocks", name));

					}
				}
			} else if (cmd.equalsIgnoreCase("reload")) {
				if (args.length == 1) {
					Skywars.reload();
				}else{
					
				}
			} else if (cmd.equalsIgnoreCase("save")) {
				if (args.length == 1) {
					Skywars.save();
				}else{
					
				}
			} else if (cmd.equalsIgnoreCase("low")) {
				if (args.length == 1) {
					sender.sendMessage("§c/skywars low <skywars>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(Main.message("no-arena", name));
					} else {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							Skywars.get(name).getMap()
									.setFirstPosition(p.getLocation());
							sender.sendMessage(
									Main.message("set-low", name));

						} else {
							sender.sendMessage(noConsole);
						}
					}
				}

			} else if (cmd.equalsIgnoreCase("high")) {
				if (args.length == 1) {
					sender.sendMessage("§c/skywars high <skywars>");
				} else {
					String name = args[1];
					if (!Skywars.has(name)) {
						sender.sendMessage(Main.message("no-arena", name));
					} else {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							Skywars.get(name).getMap()
									.setSecondPosition(p.getLocation());
							sender.sendMessage(
									Main.message("set-high", name));

						} else {
							sender.sendMessage(noConsole);
						}
					}
				}
			}
		}
		return true;
	}
}
