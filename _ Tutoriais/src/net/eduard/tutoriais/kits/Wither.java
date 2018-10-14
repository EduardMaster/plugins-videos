package net.eduard.tutoriais.kits;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.game.KitAbility;

public class Wither extends KitAbility {
	public double chance = 0.3;

	public Wither() {	
		setIcon(Material.SKULL_ITEM,1, "§fEnvene seus inimigos com Wither");
		getPotions().add(new PotionEffect(PotionEffectType.WITHER, 0, 20 * 5));
	}

	@Override
	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (e.getEntity() instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) e.getEntity();
					if (Mine.getChance(chance)) {
						givePotions(livingEntity);
					}
				}
				
			}

		}
	}
}
