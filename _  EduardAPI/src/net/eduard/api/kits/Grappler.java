package net.eduard.api.kits;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.util.Vector;

import net.eduard.api.API;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.ClickType;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.GrapplerHook;

public class Grappler extends Kit {
	public static HashMap<Player, GrapplerHook> hooks = new HashMap<>();

	public Grappler() {
		setIcon(Material.LEASH, "§fSe mova mais rapido");
		add(Material.LEASH);
		setActiveCooldownOnPvP(true);
		setTime(5);
		setPrice(50 * 1000);
		setClick(new Click(Material.LEASH, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (!onCooldown(p)) {
						if (e.getAction() == Action.LEFT_CLICK_AIR) {
							if (hooks.containsKey(p)) {
								hooks.get(p).remove();
								hooks.remove(p);
							}
							hooks.put(p, new GrapplerHook(p, 1.5));
						} else if (e.getAction() == Action.RIGHT_CLICK_AIR) {
							if (hooks.containsKey(p)) {
								GrapplerHook hook = hooks.get(p);
								if (hook.isHooked) {
									p.setFallDistance(-10);
									Location target = hook.getBukkitEntity()
											.getLocation();
									Vector velocity = GrapplerHook.moveTo(
											p.getLocation(), target, 0.5, 1.5,
											0.5, 0.04, 0.06, 0.04);
									p.setVelocity(velocity);
								} else {
									p.sendMessage(
											"§6O gancho nao se prendeu em nada!");
								}
							}
						}
					} else {
						p.sendMessage(
								"§6Voce esta em PvP e não pode usar o Kit!");
					}
				}

			}
		}));
		getClick().setType(ClickType.ALL);
	}

	@EventHandler
	public void event(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		if (hooks.containsKey(p)) {
			hooks.get(p).remove();
			hooks.remove(p);
		}
	}

	@EventHandler
	public void event(PluginDisableEvent e) {
		if (getPlugin().equals(e.getPlugin()))
			for (Player p : API.getPlayers()) {
				if (hooks.containsKey(p)) {
					hooks.get(p).remove();
					hooks.remove(p);
				}
			}

	}

}
