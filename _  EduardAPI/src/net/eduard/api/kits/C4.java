package net.eduard.api.kits;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.gui.Kit;
import net.eduard.api.player.Explosion;
import net.eduard.api.player.Jump;
import net.eduard.api.player.SoundEffect;

public class C4 extends Kit {

	
	public Material type = Material.TNT;
	public static HashMap<Player, Item> bombs = new HashMap<>();

	public C4() {
		setIcon(Material.TNT, "§fPlante e Ative a C4");
		add(Material.STONE_BUTTON);
		setMessage("§6A bomba foi plantada!");
		setJump(new Jump(false, 0.6, 0.5,
				SoundEffect.create(Sound.CLICK)));
		setExplosion(new Explosion(4, false, false));
		setTime(2);
		setTimes(2);
		setClick(new Click(Material.STONE_BUTTON, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (cooldown(p)) {
						if (bombs.containsKey(p)) {
							Item c4 = bombs.get(p);
							makeExplosion(c4);
							c4.remove();
							bombs.remove(p);
						} else {
							Item c4 = p.getWorld().dropItemNaturally(
									p.getEyeLocation(), new ItemStack(type));
							c4.setPickupDelay(99999);
							API.setDirection(c4, p);
							jump(c4);
							bombs.put(p, c4);
							sendMessage(p);
						}
					}
				}
			}

			@Override
			public void effect(PlayerInteractEntityEvent e) {

			}
		}));
	}

}
