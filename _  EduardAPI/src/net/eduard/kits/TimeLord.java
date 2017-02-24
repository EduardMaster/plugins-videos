package net.eduard.kits;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.dev.Game;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;
import net.eduard.api.util.ClickType;
import net.eduard.api.util.SimpleEffect;

public class TimeLord extends Kit {

	public TimeLord() {
		setIcon(Material.WATCH, "Paralize seus inimigos");
		add(Material.WATCH);
		setTime(30);
		new Click(Material.WATCH,new ClickEffect() {
			
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					e.setCancelled(true);
					if (e.getRightClicked() instanceof Player) {
						Player target = (Player) e.getRightClicked();
						if (cooldown(p)) {
							players.add(target);
							new Game(2).delay(new SimpleEffect() {
								public void effect() {
									players.remove(target);
								}
							});
						}
					}

				}
			}
			
			public void effect(PlayerInteractEvent e) {
				
			}
		}).setType(ClickType.CLICK_ENTITY);
	}

	public int timeParalized = 10;

	public static ArrayList<Player> players = new ArrayList<>();

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (players.contains(p)) {
			p.teleport(e.getFrom());
		}
	}

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			players.remove(p);

		}
	}
}
