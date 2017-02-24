
package me.eduard.kitpvp.habilidades;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.eduard.kitpvp.KitPvP;
import me.eduard.kitpvp.KitType;

public class Camel extends KitPvP implements Listener {

	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		if (hasKit(p, KitType.CAMEL)) {
			if (e.getTo().getBlock().getRelative(BlockFace.DOWN)
				.getType() == Material.SAND) {
				p.addPotionEffect(
					new PotionEffect(PotionEffectType.SPEED, 20 * 5, 1), true);
			}
		}
	}
}
