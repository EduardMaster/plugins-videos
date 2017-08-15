package net.eduard.api.kits;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.API;
import net.eduard.api.game.Effects;
import net.eduard.api.game.Jump;
import net.eduard.api.game.Sounds;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Kit;
import net.eduard.api.setup.ItemAPI;

public class DeshFire extends Kit {
	public static ArrayList<Player> inEffect = new ArrayList<>();
	public int range = 5;
	public int effectSeconds = 5;

	public DeshFire() {
		setIcon(Material.REDSTONE_BLOCK, "§fGanha um boost para frente");
		setTime(40);
		add(Material.REDSTONE_BLOCK);
		setDisplay(new Effects(Effect.MOBSPAWNER_FLAMES, 10));
		setJump(new Jump(true, 0.5, 2, Sounds.create(Sound.CLICK)));
		setClick(new Click(Material.REDSTONE_BLOCK, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						ItemAPI.saveArmours(p);
						ItemAPI.setEquip(p, Color.RED, "§c" + getName());
						inEffect.add(p);
						p.setAllowFlight(true);
						API.TIME.delay(effectSeconds, new Runnable() {

							@Override
							public void run() {
								if (hasKit(p)) {
									ItemAPI.getArmours(p);
									inEffect.remove(p);
								}
							}
						});
						jump(p);
					}

				}
			}
		}));
	}
	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (inEffect.contains(p)) {
			for (Entity entity : p.getNearbyEntities(range, range, range)) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					livingEntity.setFireTicks(20*effectSeconds);
					getDisplay().create(livingEntity.getLocation());

				}
			}
		}

	}

}
