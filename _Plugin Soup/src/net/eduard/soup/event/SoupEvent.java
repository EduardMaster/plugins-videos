
package net.eduard.soup.event;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.manager.EventAPI;
import net.eduard.soup.Main;

public class SoupEvent extends EventAPI {

	@EventHandler
	public void event(FoodLevelChangeEvent e) {

		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (Main.getConfig(p.getWorld()).getBoolean("NoChangeFood")) {
				if (e.getFoodLevel() < 20) {
					e.setFoodLevel(20);
					p.setExhaustion(0);
					p.setSaturation(20);
				}
			}
		}

	}

	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		Action args = e.getAction();
		if (args == Action.RIGHT_CLICK_BLOCK) {
			if (e.getClickedBlock().getState() instanceof Sign) {
				Sign sign = (Sign) e.getClickedBlock().getState();
				if (sign.getLine(0).equalsIgnoreCase(
					API.toText(Main.config.getMessages("Sign").get(0)
							))) {
					Inventory inv = API
						.newInventory(Main.config.message("SignName"), 6);

					for (ItemStack item : inv) {
						if (item == null) {
							inv.addItem(Main.soup);
						}
					}
					p.openInventory(inv);
				}
			}
		}
	}

	@EventHandler
	public void onSignChangeEvent(SignChangeEvent e) {

		Player p = e.getPlayer();
		if (e.getLine(0).toLowerCase().contains("soup")) {
			int id = 0;
			for (String text:Main.config.getMessages("Sign")) {
				e.setLine(id, API.toText(text));
				id++;
			}
			p.sendMessage(Main.config.message("CreateSign"));
		}
	}
}
