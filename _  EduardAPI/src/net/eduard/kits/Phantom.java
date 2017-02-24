package net.eduard.kits;

import java.util.HashMap;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;
import net.eduard.api.util.SimpleEffect;

public class Phantom extends Kit {
	public Phantom() {
		setIcon(Material.FEATHER, "Voe por 5 segundos");
		add(Material.FEATHER);
		setTime(40);
		new Click(Material.FEATHER,new ClickEffect() {

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {

					if (cooldown(p)) {

						PlayerInventory inv = p.getInventory();
						armours.put(p, inv.getArmorContents());
						API.setEquip(p, Color.WHITE, "§b" + getName());
						p.setAllowFlight(true);
						new Game(effectTime).delay(new SimpleEffect() {
							
							public void effect() {
								if (hasKit(p)) {
									inv.setArmorContents(armours.get(p));
									p.sendMessage(flyOver);
								}
								p.setAllowFlight(false);
								armours.remove(p);
							}
						});

					}
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	public String flyOver = "§6Acabou o tempo nao pode mais voar";
	public int effectTime = 5;
	public static HashMap<Player, ItemStack[]> armours = new HashMap<>();

}
