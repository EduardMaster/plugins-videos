package net.eduard.api.kits;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.API;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.ClickType;
import net.eduard.api.gui.Kit;

public class TimeLord extends Kit {

	public static ArrayList<Player> inEffect = new ArrayList<>();

	public int effectSeconds = 10;

	public TimeLord() {
		setIcon(Material.WATCH, "§fParalize seus inimigos");
		add(Material.WATCH);
		setClick(new Click(Material.WATCH,new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					e.setCancelled(true);
					if (e.getRightClicked() instanceof Player) {
						Player target = (Player) e.getRightClicked();
						if (cooldown(p)) {
							inEffect.add(target);
							API.TIME.delay(2,new Runnable() {

								@Override
								public void run() {
									inEffect.remove(target);
								}
							});
						}
					}

				}
			}

			@Override
			public void effect(PlayerInteractEvent e) {

			}
		}));
		getClick().setType(ClickType.ENTITY);
		setTime(30);
	

	}

	@EventHandler
	public void event(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			inEffect.remove(p);

		}
	}

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (inEffect.contains(p)) {
			p.teleport(e.getFrom());
		}
	}
}
