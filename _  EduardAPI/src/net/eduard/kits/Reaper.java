package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.dev.Potions;
import net.eduard.api.gui.Kit;

public class Reaper extends Kit {
	public Material material = Material.WOOD_HOE;
	public Potions effect = new Potions(PotionEffectType.WITHER, 3, 20 * 5);

	public Reaper() {
		setIcon(Material.WOOD_HOE, "Envene seus inimigos com Wither");
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			if (hasKit(p)) {
				if (p.getItemInHand() == null)
					return;
				if (p.getItemInHand().getType() == material)
					if (e.getEntity() instanceof LivingEntity) {
						LivingEntity livingEntity = (LivingEntity) e.getEntity();
						effect.create(livingEntity);
					}
			}

		}
	}
}
