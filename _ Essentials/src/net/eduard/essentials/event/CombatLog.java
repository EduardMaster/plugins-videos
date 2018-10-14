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
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.lib.Mine;
import net.eduard.essentials.EssentialsPlugin;

public class CombatLog implements Listener {

	public static List<Player> players = new ArrayList<>();

	@EventHandler
	public void aoSair(PlayerQuitEvent e) {

		Player p = e.getPlayer();

		if (players.contains(p)) {

			p.damage(1000);
			players.remove(p);
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§cO jogador " + p.getName() + " deslogou em combate e perdeu todos os itens!");
			Bukkit.broadcastMessage("");

		}
	}

	@EventHandler
	public void aoSerKick(PlayerKickEvent e) {

		Player p = e.getPlayer();

		if (players.contains(p)) {

			p.damage(1000);
			players.remove(p);
			Bukkit.broadcastMessage("");
			Bukkit.broadcastMessage("§cO jogador " + p.getName() + " deslogou em combate e perdeu todos os itens!");
			Bukkit.broadcastMessage("");

		}
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled=true)
	public void aoDigitarComandos(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();

		if (players.contains(p)) {
			
			if (!e.getMessage().contains("/report")) {
				e.setCancelled(true);
				p.sendMessage("§cVocê não pode digitar comandos em combate.");
				
			}	
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

		if (e.isCancelled())
			return;

		if (e.getEntity() instanceof Player) {

			if (e.getDamager() instanceof Player) {

				Player entity = (Player) e.getEntity();
				Player damager = (Player) e.getDamager();

				if (!players.contains(entity)) {

					players.add(entity);
					entity.sendMessage(
							"§cVocê entrou em combate, não deslogue ou perderá todos os itens do inventário.");

					new BukkitRunnable() {

						int tempo = 10;

						public void run() {

							if (tempo % 1 == 0) {

								Mine.sendActionBar(entity,
										"§c(!) Você está em combate, por §f§l" + tempo + "§c segundos!");

							}

							if (tempo == 0) {
								players.remove(entity);
								entity.sendMessage("§aVocê não está mais em combate, pode deslogar tranquilamente.");
								entity.playSound(entity.getLocation(), Sound.LEVEL_UP, 1, 1);
								cancel();
							}
							tempo--;
						}
					}.runTaskTimerAsynchronously(EssentialsPlugin.getPlugin(), 20, 20 * 1);
				}

				if (!players.contains(damager)) {

					players.add(damager);
					damager.sendMessage(
							"§cVocê entrou em combate, não deslogue ou perderá todos os itens do inventário.");

					new BukkitRunnable() {

						int tempo = 10;

						public void run() {

							if (tempo % 1 == 0) {

								Mine.sendActionBar(damager,
										"§c(!) Você está em combate, por §f§l" + tempo + "§c segundos!");

							}

							if (tempo == 0) {
								players.remove(damager);
								damager.sendMessage("§aVocê não está mais em combate, pode deslogar tranquilamente.");
								damager.playSound(damager.getLocation(), Sound.LEVEL_UP, 1, 1);
								cancel();
							}
							tempo--;
						}
					}.runTaskTimerAsynchronously(EssentialsPlugin.getPlugin(), 20, 20 * 1);
				}
			}
		}
	}
}
