package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.dev.Potions;
import net.eduard.api.gui.Kit;

public class Worm extends Kit {

	public Potions effect = new Potions(PotionEffectType.REGENERATION, 0, 20 * 2);

	public Worm() {
		setIcon(Material.DIRT, "Quebre terra mais facilmente");
	}

	@EventHandler
	public void event(BlockDamageEvent e) {
		Player p = e.getPlayer();
		if (hasKit(p)) {
			if (e.getBlock().getType() == Material.DIRT) {
				effect.create(p);
				e.setInstaBreak(true);
			}
		}

	}
}
