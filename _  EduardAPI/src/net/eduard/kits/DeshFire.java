package net.eduard.kits;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.dev.Effects;
import net.eduard.api.dev.Game;
import net.eduard.api.dev.Jump;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;
import net.eduard.api.util.SimpleEffect;
import net.eduard.api.util.TimeEffect;

public class DeshFire extends Kit {

	public static Effects effect = new Effects(Effect.MOBSPAWNER_FLAMES, 10);
	public Jump jump = new Jump(true, 0.5, 2, Sounds.create(Sound.CLICK));
	public int range = 5;
	public int fireSeconds = 5;
	public int effectTime = 5;
	private HashMap<Player, ItemStack[]> armours = new HashMap<>();

	public DeshFire() {
		setIcon(Material.REDSTONE_BLOCK, "Ganha um boost para frente");
		setTime(40);
		add(Material.REDSTONE_BLOCK);
		new Click(Material.REDSTONE_BLOCK, new ClickEffect() {

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {

					if (cooldown(p)) {
						PlayerInventory inv = p.getInventory();
						armours.put(p, inv.getArmorContents());
						API.setEquip(p, Color.RED, "§c" + getName());
						p.setAllowFlight(true);
						new Game(effectTime).delay(new SimpleEffect() {

							public void effect() {
								if (hasKit(p)) {
									inv.setArmorContents(armours.get(p));
								}
								armours.remove(p);
							}
						});
						new Game(1).countdown(new TimeEffect() {

							public void effect(int times) {
								for (Entity entity : p.getNearbyEntities(range, range, range)) {
									if (entity instanceof LivingEntity) {
										LivingEntity livingEntity = (LivingEntity) entity;
										livingEntity.setFireTicks(20 * fireSeconds);
										effect.create(p);

									}
								}
							}
						}, effectTime);

					}
					;
					jump.create(p);
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

}
