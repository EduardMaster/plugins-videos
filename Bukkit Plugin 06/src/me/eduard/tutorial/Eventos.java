
package me.eduard.tutorial;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Eventos implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void DiamanteExplosivo(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		Action action = event.getAction();
		if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
			&& player.getItemInHand().getType() == Material.DIAMOND) {
			Location blocoClicado = player.getTargetBlock(null, 100).getLocation();
			player.getWorld().createExplosion(blocoClicado, 10, true);
		}
	}
}
