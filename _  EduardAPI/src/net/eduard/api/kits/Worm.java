package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.gui.Kit;
import net.eduard.api.player.Potions;

public class Worm extends Kit {


	public Worm() {
		setIcon(Material.DIRT, "§fQuebre terra mais facilmente");
		getPotions().add(new Potions(PotionEffectType.REGENERATION, 0, 20 * 2));
	}

	@EventHandler
	public void event(BlockDamageEvent e) {
		Player p = e.getPlayer();
		if (hasKit(p)) {
			if (e.getBlock().getType() == Material.DIRT) {
				givePotions(p);
				e.setInstaBreak(true);
			}
		}

	}
}
