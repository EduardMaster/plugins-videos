package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.API;
import net.eduard.api.game.Potions;
import net.eduard.api.gui.Kit;

public class Wither extends Kit {
	public double chance = 0.3;

	public Wither() {
		setIcon(Material.SKULL_ITEM,1, "§fEnvene seus inimigos com Wither");
		getPotions().add(new Potions(PotionEffectType.WITHER, 0, 20 * 5));
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (e.getEntity() instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) e.getEntity();
					if (API.getChance(chance)) {
						givePotions(livingEntity);
					}
				}
				
			}

		}
	}
}
