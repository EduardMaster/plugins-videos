package net.eduard.live.almas;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class Eventos implements Listener {

	@EventHandler
	public void mover(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Block bloquinho = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Material tipoDoBloquinho = bloquinho.getType();
	}
	@EventHandler
	public void evento(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Main.almas.put(p, Main.configExtra.getDouble(""+p.getName().toLowerCase()));
	}
	@EventHandler
	public void cmdFicitio(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/teste")) {
			e.setCancelled(true);
			LojaAlmas.spawnarMercadao(e.getPlayer().getLocation());
		}
	}
	@EventHandler
	public void abriMercadao(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/mercadao")) {
			e.setCancelled(true);
			LojaAlmas.abrirLoja(e.getPlayer());
		}
	}
	@EventHandler
	public void morrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		Player killer = p.getKiller();
		if (killer != null) {
			AlmasAPI.addAlmas(killer, 1);
		}
		AlmasAPI.removeAlmas(p, 1);
	}

}
