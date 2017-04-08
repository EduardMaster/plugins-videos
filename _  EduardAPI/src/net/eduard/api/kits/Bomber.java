package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.gui.Kit;

public class Bomber extends Kit {

	public Bomber() {
		setIcon(Material.TNT, "§fAtire bombas nos inimigos");
		add(Material.TNT);
		setClick(new Click(Material.TNT, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						TNTPrimed tnt = (TNTPrimed) p.getWorld()
								.spawn(p.getLocation(), TNTPrimed.class);
						tnt.setVelocity(p.getEyeLocation().getDirection()
								.multiply(2.5D));
						tnt.setFuseTicks(40);
						p.getLocation().getWorld()
								.createExplosion(p.getLocation(), 4.0F);
					}
				}

			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		}));
	}

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (hasKit(p)) {
				if (e.getCause() == DamageCause.BLOCK_EXPLOSION) {
					e.setCancelled(true);
				}
			}

		}
	}

}
