package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.dev.Potions;
import net.eduard.api.gui.Kit;

public class Camel extends Kit {
	public Camel() {
		setIcon(Material.SAND, "Fique mais forte na areia");
	}

	public Potions power = new Potions(PotionEffectType.REGENERATION, 0, 20 * 5);
	public Potions speed = new Potions(PotionEffectType.SPEED, 1, 20 * 5);

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (hasKit(p)) {
			Material type = e.getTo().getBlock().getRelative(BlockFace.DOWN).getType();
			if (type == Material.SAND | type == Material.SANDSTONE) {
				power.create(p);
				speed.create(p);
			}	
		}
	}

}
