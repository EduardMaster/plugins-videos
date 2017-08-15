package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Kit;
import net.eduard.api.setup.GameAPI;

public class Vaccum extends Kit {
	public int range = 20;

	public Vaccum() {
		setIcon(Material.EYE_OF_ENDER, "§fSugue seus inimigos até você");
		add(Material.EYE_OF_ENDER);
		setClick(new Click(Material.EYE_OF_ENDER, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (e.getMaterial() == Material.EYE_OF_ENDER) {
						if (cooldown(p)) {
							for (Entity entity : p.getNearbyEntities(range,
									range - 5, range)) {
								if (entity instanceof LivingEntity) {
									LivingEntity livingEntity = (LivingEntity) entity;
									GameAPI.moveTo(livingEntity, p.getLocation(),
											-0.2);
								}
							}
						}
					}

				}
			}
		}));
	}

}
