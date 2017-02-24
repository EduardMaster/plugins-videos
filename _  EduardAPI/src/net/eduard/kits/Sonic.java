package net.eduard.kits;

import java.util.ArrayList;
import java.util.HashMap;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.API;
import net.eduard.api.dev.Effects;
import net.eduard.api.dev.Game;
import net.eduard.api.dev.Jump;
import net.eduard.api.dev.Potions;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;
import net.eduard.api.util.SimpleEffect;

public class Sonic extends Kit {
	public Sonic() {
		setIcon(Material.LAPIS_BLOCK, "Ganha um boost para frente");
		add(Material.LAPIS_BLOCK);
		setTime(40);
		new Click(Material.LAPIS_BLOCK, new ClickEffect() {

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						PlayerInventory inv = p.getInventory();
						armours.put(p, inv.getArmorContents());
						API.setEquip(p, Color.BLUE, "§b" + getName());
						effect.create(p);

						new Game(effectTime).delay(new SimpleEffect() {

							public void effect() {
								if (hasKit(p)) {
									inv.setArmorContents(armours.get(p));
								}
								armours.remove(p);
								players.remove(p);

							}
						});
					}
				}
			}
		});
	}

	public Jump effect = new Jump(true, 0.5, 2,Sounds.create(Sound.CLICK));
	public Potions potion = new Potions(PotionEffectType.POISON, 1, 20 * 5);
	public int effectTime = 5;
	public Effects display = new Effects(Effect.SMOKE, 10);
	public int range = 5;
	public static HashMap<Player, ItemStack[]> armours = new HashMap<>();
	public static ArrayList<Player> players = new ArrayList<>();

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (players.contains(p)) {
			for (Entity entity : p.getNearbyEntities(range, range, range)) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					potion.create(livingEntity);
					display.create(livingEntity.getLocation());

				}
			}
		}

	}
}
