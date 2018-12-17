package net.eduard.essentials.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class MaintenanceCommand implements CommandExecutor {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogin(PlayerLoginEvent e) {
		
//		if (Main.manutencao) {
			
			Player p = e.getPlayer();
			
			if (!p.hasPermission("smash.ajudante")) {
				
				e.disallow(Result.KICK_OTHER, "§c§lSMASHMC NETWORK\n\n§c   O servidor se encontra em modo manutenção,\n§c  Para obter mais informações acesse: §rwww.smash-mc.com");
				
			}
//		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("manutencao")) {
				if (p.hasPermission("smash.manutencao")) {

					if (args.length == 0) {

						sender.sendMessage("§cPor favor, use /manutencao <on/off>");

					} else {

						if (args[0].equalsIgnoreCase("on")) {

//							Main.manutencao = true;

							Bukkit.broadcastMessage("");
							Bukkit.broadcastMessage("§4 • §cO servidor entrou em modo §4§lMANUTENÇAO§c!");
							Bukkit.broadcastMessage("");
							
							for (Player player : Bukkit.getOnlinePlayers()) {

								if (!player.hasPermission("smash.manutencao")) {

									player.kickPlayer(
											"§c§lSMASHMC NETWORK\n\n§cO servidor se encontra em modo manutenção,\n§cPara obter mais informações acesse: §rwww.smash-mc.com");

								}
							}
						} else if (args[0].equalsIgnoreCase("off")) {
							
//							if (!Main.manutencao) {
//								
//								Bukkit.broadcastMessage("");
//								Bukkit.broadcastMessage("§4 • §cO servidor saiu do modo §4§lMANUTENÇAO§c!");
//								Bukkit.broadcastMessage("");
//								
//								Main.manutencao = false;
//								
//							} else {
//								
//								sender.sendMessage("§cO servidor não está em manutenção.");
//								
//							}
						}
					}

				} else {

					sender.sendMessage("§cVocê precisa do cargo Administrador ou superior para executar este comando.");

				}
			}
		}

		return false;
	}
}
