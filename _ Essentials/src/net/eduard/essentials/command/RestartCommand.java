package net.eduard.essentials.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RestartCommand implements CommandExecutor {

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
					if (Main.reinicio) {
						sender.sendMessage("§cO restart ja esta ocorrendo.");
					} else {
						Main.reinicio = true;
						evento.put("Restart", Boolean.valueOf(true));
						anuncio(5);
					}

				}

				if ((args[0].equalsIgnoreCase("Cancelar")) && (Main.reinicio)) {
					Main.reinicio = false;
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
			Main.reinicio = false;
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

			Main.reinicio = true;
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

		Bukkit.getServer().getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
			public void run() {
				if (quantidade == 0) {
					evento.put("Restart", Boolean.valueOf(false));
					return;
				}
				if (Main.reinicio)
					anuncio(quantidade - 1);
			}
		}, 600L);
	}

}
