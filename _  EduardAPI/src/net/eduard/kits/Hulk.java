package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.util.Vector;

import net.eduard.api.API;
import net.eduard.api.dev.Jump;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Kit;

public class Hulk extends Kit {
	public Jump effect = new Jump(new Vector(0, 2, 0), Sounds.create(Sound.BURP));

	public Hulk() {
		setIcon(Material.DISPENSER, "Levante seus inimigos");
		setTime(15);
	}

	@EventHandler
	public void event(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();

		if (hasKit(p)) {
			if (API.isUsing(p, Material.AIR)) {
				if (p.getPassenger() != null) {

					if (e.getRightClicked().equals(p.getPassenger())) {
						Entity target = e.getRightClicked();
						p.eject();
						effect.create(target);
						p.sendMessage("§6Voce jogou alguem para Cima!");

					}
				} else {
					if (cooldown(p)) {
						p.setPassenger(e.getRightClicked());
						p.sendMessage("§6Voce colocou alguem em suas costas!");
					}
				}
			}

		}
	}

}
