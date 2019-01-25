package net.eduard.essentials.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.essentials.EssentialsPlugin;

public class CombatLog implements Listener {

	public static List<Player> players = new ArrayList<>();

	@EventHandler
	public void aoSair(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		if (!EssentialsPlugin.getInstance().getBoolean("combatlog-enabled")) {
			return;
		}
		if (players.contains(p)) {

			p.damage(1000);
			players.remove(p);
			Bukkit.broadcastMessage(
					EssentialsPlugin.getInstance().message("combat-quit").replace("$player", p.getName()));

		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void aoDigitarComandos(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		if (!EssentialsPlugin.getInstance().getBoolean("combatlog-enabled")) {
			return;
		}
		if (players.contains(p)) {
			for (String cmd : EssentialsPlugin.getInstance().getConfigs().getMessages("combatlog.commands-permitted")) {
				if (e.getMessage().toLowerCase().startsWith(cmd.toLowerCase())) {
					return;
				}
			}
			e.setCancelled(true);
			p.sendMessage(EssentialsPlugin.getInstance().message("combat-try-command"));

		}
	}

	@EventHandler
	public void aoMorrer(PlayerDeathEvent e) {

		if (e.getEntity() instanceof Player) {

			Player morreu = (Player) e.getEntity();

			if (e.getEntity().getKiller() instanceof Player) {
				players.remove(morreu);
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void aoBater(EntityDamageByEntityEvent e) {

		if (!EssentialsPlugin.getInstance().getBoolean("combatlog-enabled")) {
			return;
		}

		Integer time = EssentialsPlugin.getInstance().getConfigs().getInt("combatlog.combat-seconds");
		if (e.getEntity() instanceof Player) {

			if (e.getDamager() instanceof Player) {

				Player entity = (Player) e.getEntity();
				Player damager = (Player) e.getDamager();

				if (!players.contains(entity)) {

					players.add(entity);
					entity.sendMessage(EssentialsPlugin.getInstance().message("combat-started"));

					new BukkitRunnable() {

						public void run() {

							players.remove(entity);
							entity.sendMessage(EssentialsPlugin.getInstance().message("combat-out"));
							entity.playSound(entity.getLocation(), Sound.LEVEL_UP, 1, 1);

						}
					}.runTaskLaterAsynchronously(EssentialsPlugin.getInstance(), 20 * time);
				}

				if (!players.contains(damager)) {

					players.add(damager);
					damager.sendMessage(EssentialsPlugin.getInstance().message("combat-started"));

					new BukkitRunnable() {

						public void run() {

							players.remove(damager);
							damager.sendMessage(EssentialsPlugin.getInstance().message("combat-out"));
							damager.playSound(damager.getLocation(), Sound.LEVEL_UP, 1, 1);
							cancel();
						}
					}.runTaskLaterAsynchronously(EssentialsPlugin.getInstance(), 20 * time);

				}
			}
		}
	}
}
