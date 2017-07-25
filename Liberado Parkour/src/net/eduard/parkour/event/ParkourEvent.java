package net.eduard.parkour.event;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import net.eduard.api.config.CS;
import net.eduard.api.manager.Manager;
import net.eduard.api.manager.WorldAPI;
import net.eduard.parkour.Arena;

public class ParkourEvent extends Manager {

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL) {
				if (Arena.isPlaying(p)) {
					Arena.updateFall(p);
					Arena.toCheckpoint(p);
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void event(PlayerRespawnEvent e) {

		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			Arena arena = Arena.getArena(p);
			Arena.updateFall(p);
			e.setRespawnLocation(arena.getSpawn());
		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {

		if (e.getEntity() instanceof Player
				& e.getDamager() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player d = (Player) e.getDamager();
			if (Arena.isPlaying(p)) {
				e.setCancelled(true);
			}
			if (Arena.isPlaying(d)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void event(PlayerCommandPreprocessEvent e) {

		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			if (CS.startWith(e.getMessage(), "/leave")
					| CS.startWith(e.getMessage(), "/sair")) {
				Arena.leave(p);
			} else {
				p.sendMessage(Arena.message("OnlyQuit"));
			}
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Arena.isPlaying(p)) {
			Arena arena = Arena.getArena(p);
			if (WorldAPI.equals(e.getTo(), arena.getEnd())) {
				Arena.win(p);
			}
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN)
					.getType() == arena.getChecker()) {
				Arena.updateCheckpoint(p);
			}
		}
	}
	@EventHandler
	public void event(PlayerQuitEvent e) {
		Arena.remove(e.getPlayer());
	}
	@EventHandler
	public void event(PlayerKickEvent e) {
		Arena.remove(e.getPlayer());
	}
	@EventHandler
	public void event(PlayerJoinEvent e) {
		Arena.join(e.getPlayer());
	}
	@EventHandler
	public void event(FoodLevelChangeEvent e) {

		HumanEntity who = e.getEntity();
		if (who instanceof Player) {
			Player p = (Player) who;
			if (Arena.isPlaying(p)) {
				e.setFoodLevel(20);
				p.setSaturation(20);
				p.setExhaustion(0);
			}

		}
	}
}
