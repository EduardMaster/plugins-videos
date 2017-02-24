package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.API;
import net.eduard.api.dev.Potions;
import net.eduard.api.gui.Kit;

public class Turtle extends Kit {
	public double chance = 0.3;
	public double damage = 1;
	public Potions effect = new Potions(PotionEffectType.POISON, 0, 20 * 5);

	public Turtle() {
		setIcon(Material.LEATHER_CHESTPLATE, "Ao estiver agaixado vai ficar quase invuneravel");
		setMessage("§6Voce não pode bater enquanto estiver agaixando");
	}

	@EventHandler
	public void event(EntityDamageEvent e) {;
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p)) {
				if (p.isSneaking()) {
					e.setDamage(damage);
				}
			}
		}
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (p.isSneaking()) {
					e.setCancelled(true);
					p.sendMessage(getMessage());;
				}
				if (e.getEntity() instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) e.getEntity();
					if (API.getChance(chance)) {
						effect.create(livingEntity);
					}
				}

			}

		}
	}
}
