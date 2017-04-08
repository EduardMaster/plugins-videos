
package net.eduard.skywars.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.skywars.Arena;
import net.eduard.skywars.Skywars;

public class SkywarsCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage("§aDigite: /skywars help");
		} else {
			// /skywars arg
			String cmd = args[0];
			if (cmd.equalsIgnoreCase("create")) {
				if (args.length >= 2) {
					String name = args[1];
					if (Skywars.hasMap(name)) {
						sender.sendMessage("§cJá existe este Skywars!");
					} else {
						Skywars.createMap(name);
						sender.sendMessage(
								"§aVoce criou o mapa de Skywars: §2" + name);
					}
				} else {
					sender.sendMessage("§aDigite: /skywars create <arena>");
				}
			} else if (cmd.equalsIgnoreCase("delete")) {
				if (args.length >= 2) {
					String name = args[1];
					if (Skywars.hasMap(name)) {
						Skywars.deleteMap(name);
						sender.sendMessage(
								"§aVoce deletou o mapa de Skywars: " + name);
					} else {
						sender.sendMessage("§cNão existe este Skywars!");
					}
				} else {
					sender.sendMessage("§aDigite: /skywars delete <arena>");
				}

			} else if (cmd.equalsIgnoreCase("clearspawns")) {
				if (args.length >= 2) {
					String name = args[1];
					if (Skywars.hasMap(name)) {
						Skywars.getMap(name).getSpawns().clear();
						sender.sendMessage("§aVoce deletou todos os Spawns!");
					} else {
						sender.sendMessage("§cNão existe este Skywars!");
					}
				} else {
					sender.sendMessage(
							"§aDigite: /skywars clearspawns <arena>");
				}

			} else if (cmd.equalsIgnoreCase("addspawn")) {
				if (args.length >= 2) {
					String name = args[1];
					if (Skywars.hasMap(name)) {
						Arena map = Skywars.getMap(name);
						if (sender instanceof Player) {
							Player p = (Player) sender;
							map.getSpawns().add(p.getLocation());
							sender.sendMessage("§aVoce adicionou um spawn!");
						} else {
							sender.sendMessage("§cComando para Jogadores!");
						}
						//
					} else {
						sender.sendMessage("§cNão existe este Skywars!");
					}
				} else {
					sender.sendMessage("§aDigite: /skywars addspawn <arena>");
				}

			}else if (cmd.equalsIgnoreCase("pos1")) {
				if (args.length >= 2) {
					String name = args[1];
					if (Skywars.hasMap(name)) {
						Arena map = Skywars.getMap(name);
						if (sender instanceof Player) {
							Player p = (Player) sender;
							map.getMap().setPos1(p.getLocation());
							sender.sendMessage("§aVoce setou a posição alta do Mapa!");
						} else {
							sender.sendMessage("§cComando para Jogadores!");
						}
						//
					} else {
						sender.sendMessage("§cNão existe este Skywars!");
					}
				} else {
					sender.sendMessage("§aDigite: /skywars pos1 <arena>");
				}

			} else if (cmd.equalsIgnoreCase("pos2")) {
				if (args.length >= 2) {
					String name = args[1];
					if (Skywars.hasMap(name)) {
						Arena map = Skywars.getMap(name);
						if (sender instanceof Player) {
							Player p = (Player) sender;
							map.getMap().setPos2(p.getLocation());
							sender.sendMessage("§aVoce setou a posição baixa do Mapa!");
						} else {
							sender.sendMessage("§cComando para Jogadores!");
						}
						//
					} else {
						sender.sendMessage("§cNão existe este Skywars!");
					}
				} else {
					sender.sendMessage("§aDigite: /skywars pos1 <arena>");
				}

			}else if (cmd.equalsIgnoreCase("lobby")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					p.teleport(Skywars.getLooby());

					sender.sendMessage("§aVoce foi teleportado para o Lobby!");
				} else {
					sender.sendMessage("§cComando para Jogadores!");
				}

			}else if (cmd.equalsIgnoreCase("setlobby")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					Skywars.setLooby(p.getLocation());

					sender.sendMessage("§aVoce setou o Lobby!");
				} else {
					sender.sendMessage("§cComando para Jogadores!");
				}

			} else if (cmd.equalsIgnoreCase("help")) {
				sender.sendMessage("§a============= SKYWARS =============");
				if (sender.hasPermission("skywars.admin")) {
					sender.sendMessage(
							"§a/skywars create <arena> §bCriar um Mapa");
					sender.sendMessage(
							"§a/skywars delete <arena> §bDeletar um Mapa");
				}
				sender.sendMessage(
						"§a/skywars help §bVer Informações dos Comandos");
			} else {
				sender.sendMessage("§aDigite: /skywars help");
			}
		}
		return true;
	}

}
