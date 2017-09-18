
package net.eduard.gui;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.game.Gui;
import net.eduard.api.game.PlayerEffect;
import net.eduard.api.game.Slot;
import net.eduard.api.game.Sounds;

public class Main extends JavaPlugin implements Listener {
	public Gui gui;

	@Override
	public void onEnable() {
		Sounds sound = new Sounds(Sound.LEVEL_UP, 2, 0.5F);
		gui.setItem(API.newItem("§4Abrir Gui Custom", Material.DIAMOND));
		gui = new Gui("§8Trocar velocidade", 3);
		for (int i = 0; i < 5; i++) {
			Material boot = null;
			switch (i) {
				case 0 :
					boot = Material.LEATHER_BOOTS;
					break;
				case 1 :
					boot = Material.CHAINMAIL_BOOTS;
					break;
				case 2 :
					boot = Material.IRON_BOOTS;
					break;
				case 3 :
					boot = Material.GOLD_BOOTS;
					break;
				case 4 :
					boot = Material.DIAMOND_BOOTS;
					break;
				default :
					break;
			}
			final int id = i;
			Slot slot = new Slot(API.newItem(boot, "§6Nivel " + (i + 1)),
					11 + i);
			slot.newEffect(new PlayerEffect() {

				@Override
				public void effect(Player p) {
					p.sendMessage("§6Sua velocidade foi alterada para o nivel "
							+ (id + 1));
					p.closeInventory();
					sound.create(p);
					float value = (id + 1) * 0.2F;
					p.setWalkSpeed(value);
				}
			});
			gui.addSlot(1, slot);
		}
		gui.register(this);
		API.event(this);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		p.getInventory().addItem(gui.getItem());
	}
}
