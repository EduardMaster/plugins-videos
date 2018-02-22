
package net.eduard.template.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.setup.Mine.EventsManager;

public class TemplateEvents extends EventsManager {

	@EventHandler
	public void event(PlayerMoveEvent e) {

	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();

	}

	@EventHandler
	public void juntarPots(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().equals(e.getWhoClicked().getInventory())) {
				if (e.getCurrentItem() != null) {
					if (e.getClick() == ClickType.DOUBLE_CLICK) {
						ItemStack item = e.getCurrentItem();
						if (item.getType() == Material.POTION) {
							e.setCancelled(true);
							int amount = item.getAmount();
							for (ItemStack itemStack : p.getInventory().getContents()) {
								if (itemStack == null)
									continue;
								if (item.isSimilar(itemStack)) {
									amount += itemStack.getAmount();
									item.setType(Material.AIR);
								}
								if (amount >= 64) {
									break;
								}
							}
							item.setAmount(amount);
						}
					}
				}
			}
		}
	}

}
