package net.eduard.essentials.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import net.eduard.api.lib.Mine;

public class LagReductor implements Listener{
	@EventHandler
	public void event(EntitySpawnEvent e) {
		
		if (e.getEntity() instanceof Item) {
			Item item = (Item) e.getEntity();
			ItemStack result = item.getItemStack();
			int amount = result.getAmount();
			for (Entity entity : item.getNearbyEntities(2, 1, 2)) {
				if (entity instanceof Item) {
					Item outroItem = (Item) entity;
					ItemStack stack = outroItem.getItemStack();
					if (result.isSimilar(stack)) {
						if (outroItem.hasMetadata("ReduzirLag")) {
							amount += outroItem.getMetadata("ReduzirLag").get(0)
									.asInt();
						} else {
							amount += stack.getAmount();
						}
						entity.remove();
					}

				}
			}
			item.setPickupDelay(20);
			item.setMetadata("ReduzirLag",
					new FixedMetadataValue(Main.getInstance(), amount));
			Mine.broadcast("§c"+amount);
			result.setAmount(1);
			item.setItemStack(result);
		}
	}
	@EventHandler
	public void event(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		
		if (e.getItem().hasMetadata("ReduzirLag")) {
			
			e.setCancelled(true);

			ItemStack item = e.getItem().getItemStack();
			Mine.broadcast(""+item.getAmount());
			p.getInventory().addItem(item);
			e.getItem().remove();
			
		}
		
	}
}
