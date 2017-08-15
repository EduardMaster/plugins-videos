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
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.API;
import net.eduard.api.game.Effects;
import net.eduard.api.game.Jump;
import net.eduard.api.game.Potions;
import net.eduard.api.game.Sounds;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Kit;
import net.eduard.api.setup.ItemAPI;

public class Sonic extends Kit {
	public static ArrayList<Player> inEffect = new ArrayList<>();
	public int effectSeconds = 5;
	public int range = 5;
	public Sonic() {
		setIcon(Material.LAPIS_BLOCK, "§fGanha um boost para frente");
		add(Material.LAPIS_BLOCK);
		setDisplay( new Effects(Effect.SMOKE, 10));
		setJump(new Jump(true, 0.5, 2,
				Sounds.create(Sound.CLICK)));
		getPotions().add(new Potions(PotionEffectType.POISON, 1, 20 * 5));
		setClick(new Click(Material.LAPIS_BLOCK, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						ItemAPI.saveItems(p);
						ItemAPI.setEquip(p, Color.BLUE, "§b" + getName());
						inEffect.add(p);
						API.TIME.delay(effectSeconds,new Runnable() {

							@Override
							public void run() {
								if (hasKit(p)) {
									ItemAPI.getArmours(p);
								}
								inEffect.remove(p);
							}
						});
						jump(p);
					}
				}
			}
		}));
		setTime(40);
	}

	@EventHandler
	public void event(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (inEffect.contains(p)) {
			for (Entity entity : p.getNearbyEntities(range, range, range)) {
				if (entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					givePotions(livingEntity);
					getDisplay().create(livingEntity.getLocation());

				}
			}
		}

	}
}
