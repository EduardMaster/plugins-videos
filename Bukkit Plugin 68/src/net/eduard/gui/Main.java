
package net.eduard.gui;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Events;
import net.eduard.api.gui.Gui;
import net.eduard.api.util.PlayerEffect;

public class Main extends JavaPlugin implements Listener {
	public Gui gui;

	public void onEnable() {
		Sounds sound = new Sounds(Sound.LEVEL_UP, 2, 0.5F);
		gui.setItem(API.newItem("§4Abrir Gui Custom", Material.DIAMOND));
		gui = new Gui(3, "§8Trocar velocidade");
		for (int i = 0; i < 5; i++) {
			Material boot = null;
			switch (i) {
			case 0:
				boot = Material.LEATHER_BOOTS;
				break;
			case 1:
				boot = Material.CHAINMAIL_BOOTS;
				break;
			case 2:
				boot = Material.IRON_BOOTS;
				break;
			case 3:
				boot = Material.GOLD_BOOTS;
				break;
			case 4:
				boot = Material.DIAMOND_BOOTS;
				break;
			default:
				break;
			}
			int id = i;
			gui.set(11 + i, API.newItem(boot, "§6Nivel " + (i + 1)),
					new Events().setSound(sound).setCloseInventory(true)
							.setMessage("§6Sua velocidade foi alterada para o nivel " + (i + 1))
							.effect(new PlayerEffect() {

								public void effect(Player p) {
									float value = (id + 1) * 0.2F;
									p.setWalkSpeed(value);
								}
							}));
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		p.getInventory().addItem(gui.getItem());
	}
}
