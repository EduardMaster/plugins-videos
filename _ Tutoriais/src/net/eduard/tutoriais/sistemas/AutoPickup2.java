package net.eduard.tutoriais.sistemas;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoPickup2 implements Listener {
	@EventHandler
	public void event(BlockBreakEvent e) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Entity block : e.getPlayer().getNearbyEntities(2, 2, 2)) {
					if (block instanceof Item) {
						Item item = (Item) block;
						e.getPlayer().getInventory().addItem(item.getItemStack());
						item.remove();
					}
				}
			}
		}.runTaskLater(JavaPlugin.getProvidingPlugin(getClass()), 1l);
	}
}
