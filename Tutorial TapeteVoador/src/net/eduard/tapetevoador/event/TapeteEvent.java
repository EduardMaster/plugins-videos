package net.eduard.tapetevoador.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;
import net.eduard.tapetevoador.TapeteVoador;

public class TapeteEvent extends EventAPI {

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {

		if (e.getEntity() instanceof Player && e.getCause() == DamageCause.FALL) {
			Player p = (Player) e.getEntity();
			if (TapeteVoador.players.contains(p)) {
				e.setCancelled(true);
			}
		}
	}
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {

		Player p = e.getEntity();
		if (TapeteVoador.players.contains(p)) {
			if (p.getGameMode() == TapeteVoador.getGamemode()) {
				TapeteVoador.reset(p);
			}
		}

	}

	@EventHandler
	public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent e) {

		Player p = e.getPlayer();
		if (TapeteVoador.players.contains(p)) {
			if (p.getGameMode() == TapeteVoador.getGamemode()) {
				TapeteVoador.reset(p);
			}
		}
	}
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		if (TapeteVoador.players.contains(p)) {
			TapeteVoador.reset(p);
			TapeteVoador.players.remove(p);
		}
	}

	@EventHandler
	public void onPlayerTeleportEvent(PlayerTeleportEvent e) {

		Player p = e.getPlayer();
		if (TapeteVoador.players.contains(p)) {
			if (p.getGameMode() == TapeteVoador.getGamemode()) {
				TapeteVoador.reset(p);
			}
		}
	}
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {

		Player p = e.getPlayer();

		if (!API.equals(e.getTo(), e.getFrom())) {
			if (TapeteVoador.players.contains(p)) {
				if (p.isDead()) {
					return;
				}
				if (p.getGameMode() == TapeteVoador.getGamemode()) {
					if (p.isSneaking()) {
						if (e.getFrom().getY() < e.getTo().getY()) {
							e.setCancelled(true);
							return;
						}
					}
					List<Block> listDeBlocosDeletados = new ArrayList<>();
					List<Block> listaDeBlocoFrom = TapeteVoador.getTapeteBlocks(e.getFrom());
					List<Block> listaDeBlocoTo = TapeteVoador.getTapeteBlocks(e.getTo());

					for (Block block : listaDeBlocoFrom) {
						if (!listaDeBlocoTo.contains(block)) {
							listDeBlocosDeletados.add(block);
						}
					}
					for (Block block : listDeBlocosDeletados) {
						if (block.getType() == TapeteVoador.getMaterial()) {
							block.setType(Material.AIR);
						}
					}
					for (Block block : listaDeBlocoTo) {
						if (block.getType() == Material.AIR) {
							block.setType(TapeteVoador.getMaterial());
						}
					}
				}
			}
		}
	}
}
