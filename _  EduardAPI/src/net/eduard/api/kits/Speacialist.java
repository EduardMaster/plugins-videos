package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.gui.Kit;

public class Speacialist extends Kit {

	public int xpAmount = 1;

	public Speacialist() {
		setIcon(Material.BOOK, "§fGanhe um Kit Aleatorio",
				"§fA cada morte ganhe Xp para encantar seus itens");
		add(Material.BOOK);
		setClick(new Click(Material.BOOK, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					p.openEnchanting(p.getLocation(), true);
				}
			}
		}));

	}

	@EventHandler
	public void event(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (e.getEntity().getKiller() != null) {
			Player killer = e.getEntity().getKiller();
			if (hasKit(p)) {
				API.drop(killer, new ItemStack(Material.EXP_BOTTLE, xpAmount));
			}
		}
	}
}
