
package me.eduard.kitpvp.habilidades;

import java.util.Random;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.eduard.kitpvp.KitPvP;
import me.eduard.kitpvp.KitType;

public class Viper extends KitPvP implements Listener {

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e) {

		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p, KitType.VIPER)) {
				double chance = new Random().nextDouble();
				if (chance <= 0.25) {
					if (e.getEntity() instanceof LivingEntity) {
						LivingEntity c = (LivingEntity) e.getEntity();
						c.addPotionEffect(
							new PotionEffect(PotionEffectType.POISON, 20 * 5, 0));

					}
				}
			}
		}
	}

}
