package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;

public class Speacialist extends Kit {

	public Speacialist() {
		setIcon(Material.BOOK, "Ganhe um Kit Aleatorio", "A cada morte ganhe Xp para encantar seus itens");
		add(Material.BOOK);
		new Click(Material.BOOK, new ClickEffect() {

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					p.openEnchanting(p.getLocation(), true);
				}
			}
		});
		
	}

	public int amount = 1;

	@EventHandler
	public void event(EntityDeathEvent e) {
		if (e.getEntity().getKiller() != null) {
			Player p = e.getEntity().getKiller();
			if (hasKit(p)) {
				API.drop(p, new ItemStack(Material.EXP_BOTTLE, amount));
			}
		}
	}
}
