package net.eduard.tutoriais;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import net.eduard.api.dev.Game;
import net.eduard.api.util.SimpleEffect;

public class DropDiretoInventario implements Listener {
	@EventHandler
	public void event(BlockBreakEvent e) {
		new Game().delay(new SimpleEffect() {

			public void effect() {
				for (Entity block : e.getPlayer().getNearbyEntities(2, 2, 2)) {
					if (block instanceof Item) {
						Item item = (Item) block;
						e.getPlayer().getInventory().addItem(item.getItemStack());
						item.remove();
					}
				}
			}
		});

	}
}
