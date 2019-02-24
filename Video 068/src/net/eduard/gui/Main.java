
package net.eduard.gui;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.click.PlayerEffect;
import net.eduard.api.lib.game.SoundEffect;
import net.eduard.api.lib.manager.EffectManager;
import net.eduard.api.lib.menu.Menu;
import net.eduard.api.lib.menu.MenuButton;

public class Main extends JavaPlugin implements Listener {
	public Menu gui;

	@Override
	public void onEnable() {
		SoundEffect sound = new SoundEffect(Sound.LEVEL_UP, 2, 0.5F);
		gui.setOpenWithItem(Mine.newItem("§4Abrir Gui Custom", Material.DIAMOND));
		gui = new Menu("§8Trocar velocidade", 3);
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
			final int id = i;
			MenuButton botao = new MenuButton(Mine.newItem(boot, "§6Nivel " + (i + 1), 11 + i));
			botao.setPosition(2, 1+id);
			botao.setEffects(new EffectManager());
			botao.getEffects().setEffect(new PlayerEffect() {

				@Override
				public void effect(Player p) {
					// TODO Auto-generated method stub

					p.sendMessage("§6Sua velocidade foi alterada para o nivel " + (id + 1));
					p.closeInventory();
					sound.create(p);
					float value = (id + 1) * 0.2F;
					p.setWalkSpeed(value);
				}
			});
			gui.addButton(botao);
		}
		gui.register(this);
		Mine.registerEvents(this, this);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		p.getInventory().addItem(gui.getOpenWithItem());
	}
}
