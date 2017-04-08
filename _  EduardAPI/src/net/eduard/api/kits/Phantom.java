package net.eduard.api.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.API;
import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.gui.Kit;

public class Phantom extends Kit {
	public int effectSeconds = 5;
	public Phantom() {
		setIcon(Material.FEATHER, "§fVoe por 5 segundos");
		add(Material.FEATHER);
		setTime(40);
		setMessage("§6Acabou o tempo nao pode mais voar");
		setClick(new Click(Material.FEATHER, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				
			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {

					if (cooldown(p)) {
						API.saveArmours(p);
						API.setEquip(p, Color.WHITE, "§b" + getName());
						p.setAllowFlight(true);
						API.TIME.delay(effectSeconds*20,new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (hasKit(p)) {
									API.getArmours(p);
									sendMessage(p);
								}
								p.setAllowFlight(false);
							}
						});

					}
				}
			}
		}));
	}

}
