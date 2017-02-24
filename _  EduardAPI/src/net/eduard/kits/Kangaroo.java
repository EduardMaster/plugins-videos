package net.eduard.kits;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import net.eduard.api.API;
import net.eduard.api.dev.Jump;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;

public class Kangaroo extends Kit {

	public Kangaroo() {
		setIcon(Material.FIREWORK, "Se mova mais rapido");
		add(Material.FIREWORK);
		setActiveCooldownOnPvP(true);
		setTime(5);
		new Click(Material.FIREWORK,new ClickEffect() {

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (!onCooldown(p)) {
						if (!players.contains(p)) {
							if (API.isFlying(p)) {
								players.add(p);
							}
							if (p.isSneaking()) {
								front.create(p);
							} else {
								hight.create(p);
							}
						}

					}
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public Jump front = new Jump(true, 0.5, 2, Sounds.create(Sound.BAT_LOOP));
	public Jump hight = new Jump(new Vector(0, 2.5, 0), Sounds.create(Sound.BAT_LOOP));
	public int maxDamage = 3;
	public static ArrayList<Player> players = new ArrayList<>();

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (API.isOnGround(p)) {
			players.remove(p);
		}
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

}
