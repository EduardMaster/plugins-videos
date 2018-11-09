package net.eduard.essentials.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.eduard.essentials.EssentialsPlugin;

public class RestartCommand implements CommandExecutor {
private static boolean reiniciando = false;
	public static HashMap<String, Boolean> evento = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		Player p = (Player) sender;

		if (cmd.equalsIgnoreCase("Reiniciar")) {
			if (args.length == 0) {
				p.sendMessage("");
				p.sendMessage("§e/reiniciar iniciar §8- §7Iniciar o restart");
				p.sendMessage("§e/reiniciar cancelar §8- §7Cancelar o restart");
				p.sendMessage("");
				return true;
			}

			if (p.hasPermission("nightmc.restart")) {
				if (args[0].equalsIgnoreCase("iniciar")) {
//					if (reiniciando) {
//						sender.sendMessage("§cO restart ja esta ocorrendo.");
//					} else {
//						reiniciando = true;
//						evento.put("Restart", Boolean.valueOf(true));
//						anuncio(5);
//					}

				}

				if ((args[0].equalsIgnoreCase("Cancelar")) && (reiniciando)) {
					reiniciando = false;
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("   \u00A7e* \u00A7e\u00A7lREINICIANDO");
					Bukkit.broadcastMessage("   \u00A7e* \u00A7eO restart foi cancelado!");
					Bukkit.broadcastMessage("");
				}
			}
		}

		return false;
	}

	public void anuncio(final int quantidade) {
		if (quantidade == 0) {
			reiniciando = false;
			return;
		}

		if (quantidade == 5) {
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("   \u00A7e* \u00A7e\u00A7lREINICIANDO");
			Bukkit.broadcastMessage("   \u00A7e* \u00A7eServidor reiniciando em 2 minutos!");
			Bukkit.broadcastMessage(" ");
		}

		if (quantidade == 3) {
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("   \u00A7e* \u00A7e\u00A7lREINICIANDO");
			Bukkit.broadcastMessage("   \u00A7e* \u00A7eServidor reiniciando em 1 minuto!");
			Bukkit.broadcastMessage(" ");
		}

		if (quantidade == 2) {
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("   \u00A7e* \u00A7e\u00A7lREINICIANDO");
			Bukkit.broadcastMessage("   \u00A7e* \u00A7eServidor reiniciando em 30 segundos!");
			Bukkit.broadcastMessage(" ");

			reiniciando = true;
		}

		if (quantidade == 1) {
			Bukkit.broadcastMessage(" ");
			Bukkit.broadcastMessage("   \u00A7e* \u00A7e\u00A7lREINICIANDO");
			Bukkit.broadcastMessage("   \u00A7e* \u00A7eServidor reiniciando!");
			Bukkit.broadcastMessage(" ");

			for (Player Todos : Bukkit.getOnlinePlayers()) {
				Todos.kickPlayer("§c§lNIGHT NETWORK\n\n\u00A7c   Servidor reiniciando !");
			}
		}

		if (quantidade == 0) {
			Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "stop");
		}

		Bukkit.getServer().getScheduler().runTaskLater(EssentialsPlugin.getInstance(), new Runnable() {
			public void run() {
				if (quantidade == 0) {
					evento.put("Restart", Boolean.valueOf(false));
					return;
				}
				if (reiniciando)
					anuncio(quantidade - 1);
			}
		}, 600L);
	}

}
