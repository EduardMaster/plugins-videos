package net.eduard.essentials.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import net.eduard.api.lib.Mine;
import net.eduard.essentials.EssentialsPlugin;

public class LagReductor implements Listener {
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
							amount += outroItem.getMetadata("ReduzirLag").get(0).asInt();
						} else {
							amount += stack.getAmount();
						}
						entity.remove();
					}

				}
			}
			item.setPickupDelay(20);
			item.setMetadata("ReduzirLag", new FixedMetadataValue(EssentialsPlugin.getInstance(), amount));
			Mine.broadcast("§c" + amount);
			result.setAmount(1);
			item.setItemStack(result);
		}
	}

}
