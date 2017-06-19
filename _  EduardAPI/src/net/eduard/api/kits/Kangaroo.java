package net.eduard.api.kits;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.game.Jump;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Kit;
import net.eduard.api.manager.GameAPI;

public class Kangaroo extends Kit {

	public static ArrayList<Player> inEffect = new ArrayList<>();
	public Jump front = new Jump( true, 2, 0.5,null);
	public Jump high = new Jump(false, 1, 1.2,null);
	public int maxDamage = 3;
	public Kangaroo() {
		setIcon(Material.FIREWORK, "§fSe mova mais rapido");
		add(Material.FIREWORK);
		setClick(new Click(Material.FIREWORK, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (!onCooldown(p)) {
						if (!inEffect.contains(p)) {
							if (GameAPI.isFlying(p)) {
								inEffect.add(p);
							}
							if (p.isSneaking()) {
								front.create(p);
							} else {
								high.create(p);
							}
						}

					}
				}
			}
		}));
		setActiveCooldownOnPvP(true);
		setTime(5);
	}

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (e.getCause() == DamageCause.FALL) {
				if (hasKit(p)) {
					if (e.getDamage() > maxDamage) {
						e.setDamage(maxDamage);
					}
				}
			}
		}

	}

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (GameAPI.isOnGround(p)) {
			inEffect.remove(p);
		}
	}

}
