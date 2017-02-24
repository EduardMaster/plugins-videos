package net.eduard.api.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Events;
import net.eduard.api.gui.Gui;
import net.eduard.api.util.ClickType;

public class GuiManager extends EventAPI{
	
	@EventHandler
	private void event(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getCurrentItem() == null) {
				return;
			}

			for (Gui gui : API.GUIS) {
				if (gui.getInventory().equals(e.getInventory())) {
					e.setCancelled(true);
					if (e.getRawSlot() > e.getInventory().getSize()) {
						break;
					}
					if (e.getCurrentItem().getType() == Material.AIR) {
						continue;
					}
					Events slot = gui.getEvent(e.getRawSlot());
					if (slot != null) {
						slot.effect(p);
					}
					break;
				}
			}

		}

	}

	@EventHandler
	private void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack item = e.getItem();
		if (item == null)
			return;
		for (Click click : API.CLICKS) {
			if (click.getType().click(e.getAction())) {
				if (click.getCheck().click(click, item)) {
					e.setCancelled(true);
					if (click.getPlayer() != null) {
						if (!click.getPlayer().equals(p)) {
							continue;
						}
					}
					click.effect(p);
					click.getEffect().effect(e);

				}
			}

		}
	}

	@EventHandler
	private void event(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if (item == null)
			item = new ItemStack(Material.AIR);

		for (Click click : API.CLICKS) {
			if (click.getType() == ClickType.CLICK_ENTITY) {
				if (click.getCheck().click(click, item)) {
					e.setCancelled(true);
					if (click.getPlayer() != null) {
						if (!click.getPlayer().equals(p)) {
							break;
						}
					}
					click.effect(p);
					click.getEffect().effect(e);
				}
			}
		}
	}	
}
