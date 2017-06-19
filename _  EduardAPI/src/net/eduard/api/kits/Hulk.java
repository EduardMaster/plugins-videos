package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import net.eduard.api.game.Jump;
import net.eduard.api.game.Sounds;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.ClickType;
import net.eduard.api.gui.Kit;

public class Hulk extends Kit {

	public Hulk() {
		setIcon(Material.DISPENSER, "§fLevante seus inimigos");
		setTime(15);
		setJump(new Jump(Sounds.create(Sound.BURP),
				new Vector(0, 2, 0)));
		setClick(new Click(Material.AIR, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (p.getPassenger() != null) {

						if (e.getRightClicked().equals(p.getPassenger())) {
							Entity target = e.getRightClicked();
							p.eject();
							jump(target);;
							p.sendMessage("§6Voce jogou alguem para Cima!");

						}
					} else {
						if (cooldown(p)) {
							p.setPassenger(e.getRightClicked());
							p.sendMessage(
									"§6Voce colocou alguem em suas costas!");
						}
					}

				}

			}
			public void effect(PlayerInteractEvent e) {

			}
		}));
		getClick().setType(ClickType.ENTITY);
	}

}
