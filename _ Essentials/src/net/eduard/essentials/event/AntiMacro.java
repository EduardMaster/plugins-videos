package net.eduard.essentials.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.essentials.EssentialsPlugin;

public class AntiMacro extends BukkitRunnable {
	public static Map<Player, Integer> cliques = new HashMap<>();

	@EventHandler
	public void bloquear(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (e.getEntity() instanceof Player) {
				double dis = p.getLocation().distance(e.getEntity().getLocation());
				if (dis > 5) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (player.hasPermission("antimacro.admin")) {
							player.sendMessage(EssentialsPlugin.getInstance().message("macro-warn").replace("$player",
									p.getName().replace("$distance", "" + dis)));
						}
					}
				}
			}

		}
	}

	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.LEFT_CLICK_AIR) {

			if (cliques.containsKey(p)) {
				Integer clique = cliques.get(p);
				cliques.put(p, ++clique);
			} else {
				cliques.put(p, 1);
			}
		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {

			Player p = (Player) e.getDamager();
			if (cliques.containsKey(p)) {
				Integer clique = cliques.get(p);
				cliques.put(p, ++clique);
			} else {
				cliques.put(p, 1);
			}
		}
	}

	@Override
	public void run() {

		for (Player player : Bukkit.getOnlinePlayers()) {
			Integer clique = cliques.get(player);
			// player.sendMessage("§aTestando seus cliques: "+clique);
			if (clique != null) {
				if (clique > 30) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission("antimacro.admin")) {
							p.sendMessage(EssentialsPlugin.getInstance().message("macro-found")
									.replace("<jogador>", player.getName()).replace("<cliques>", "" + clique));
						}
					}

				} else if (clique > 15) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (p.hasPermission("antimacro.admin")) {
							p.sendMessage(EssentialsPlugin.getInstance().message("macro-suspect")
									.replace("<jogador>", player.getName()).replace("<cliques>", "" + clique));
						}
					}
				}
				cliques.remove(player);
			}

		}

	}

}
