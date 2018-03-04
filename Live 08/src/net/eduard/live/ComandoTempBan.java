package net.eduard.live;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class ComandoTempBan implements CommandExecutor,TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length < 2) {
			sender.sendMessage("§c/tempban <nick> dias");
		} else {

			OfflinePlayer jogador = Bukkit.getOfflinePlayer(args[0]);
			jogador.setBanned(true);
			try {
				// /temban edu 3
				Integer dias = Integer.valueOf(args[1]);

				Main.config.set("Banimentos." + jogador.getName().toLowerCase() + ".dia", System.currentTimeMillis());
				Main.config.set("Banimentos." + jogador.getName().toLowerCase() + ".duracao", dias);
				Main.config.set("Banimentos." + jogador.getName().toLowerCase() + ".autor",
						sender.getName().toLowerCase());
				Main.config.saveConfig();
				sender.sendMessage("§aVoce baniu "+jogador.getName()+" por "+ dias +" dias.");
				if (jogador.isOnline()) {
					jogador.getPlayer().kickPlayer("§cVoce foi banido por "+sender.getName()+" por "+dias +" dias.");
				}
			} catch (Exception e) {

				sender.sendMessage("§cDigite numero corretamente");

			}

		}

		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> lista = new ArrayList<>();
		lista.add("5");
		lista.add("1");
		lista.add("7");
		lista.add("15");
		lista.add("30");
		if (args.length == 2)
		{
			return lista;
		}
		
		
		
		return null;
	}

}
