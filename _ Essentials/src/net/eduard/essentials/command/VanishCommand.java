package net.eduard.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class VanishCommand implements CommandExecutor, Listener {

	public static List<String> vanish = new ArrayList<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player p = (Player) sender;

			if (cmd.getName().equalsIgnoreCase("vanish")) {
				if (p.hasPermission("slime.moderador")) {

					if (vanish.contains(p.getName())) {

						p.sendMessage("§aModo vanish alterado para §a§lDESLIGADO");
						vanish.remove(p.getName());

						for (Player player : Bukkit.getOnlinePlayers()) {

							player.showPlayer(p);

						}
						return true;
					}
					
					vanish.add(p.getName());
					p.sendMessage("§aModo vanish alterado para §a§lLIGADO");
					
					for (Player player : Bukkit.getOnlinePlayers()) {

						if (!player.hasPermission("slime.moderador")) {
							player.hidePlayer(p);
						} else {
							player.showPlayer(p);
						}
					}
				} else {

//					MensagemAPI.semPermissao(p, "Moderador");

				}
			}
		}
		return false;
	}

	@EventHandler
	public void aoSair(PlayerQuitEvent e) {

		Player p = e.getPlayer();

		if (vanish.contains(p.getName())) {
			vanish.remove(p.getName());
		}
	}

	@EventHandler
	public void aoKick(PlayerKickEvent e) {

		Player p = e.getPlayer();

		if (vanish.contains(p.getName())) {
			vanish.remove(p.getName());
		}
	}
}
