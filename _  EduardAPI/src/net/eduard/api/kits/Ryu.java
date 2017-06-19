package net.eduard.api.kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockIterator;

import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.Kit;

public class Ryu extends Kit {

	public static List<Snowball> inEffect = new ArrayList<>();

	public int damage = 8;

	public Ryu() {
		setIcon(Material.BEACON, "§fAtire seu Haduken");
		add(Material.DIAMOND_BLOCK);
		
		setClick(new Click(Material.DIAMOND_BLOCK, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {

						Location location = p.getEyeLocation();
						BlockIterator blocksToAdd = new BlockIterator(location,
								0.0D, 40);
						while (blocksToAdd.hasNext()) {
							Location blockToAdd = blocksToAdd.next()
									.getLocation();
							p.getWorld().playEffect(blockToAdd,
									Effect.STEP_SOUND, Material.DIAMOND_BLOCK,
									20);
							p.playSound(blockToAdd, Sound.IRONGOLEM_THROW, 3.0F,
									3.0F);
						}
						Snowball project = p.launchProjectile(Snowball.class);
						inEffect.add(project);
						project.setVelocity(p.getLocation().getDirection()
								.normalize().multiply(10));
					}
				}

			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {

			}
		}));
	}
	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Snowball) {
			Snowball snowball = (Snowball) e.getDamager();
			if (inEffect.contains(snowball)) {
				e.setDamage(e.getDamage() + damage);
				inEffect.remove(snowball);
			}

		}
	}

}
