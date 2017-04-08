package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.API;
import net.eduard.api.gui.Kit;
import net.eduard.api.player.Potions;

public class DarkMage extends Kit {
	public double chance = 0.3;

	public DarkMage() {
		setIcon(Material.COAL, "§fSegue seus inimigos");
		getPotions().add(new Potions(PotionEffectType.BLINDNESS, 0, 20 * 5));
	}

	@Override
	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (p.getItemInHand() == null)
					return;
				if (API.getChance(chance)) 
					if (e.getEntity() instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity) e.getEntity();
						givePotions(livingEntity);
					}
				}

		}
	}
}
