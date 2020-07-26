
package net.eduard.build;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildEvent implements Listener {

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e) {

		Player p = e.getPlayer();
		if (!Main.build.contains(p)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent e) {

		Player p = e.getPlayer();
		if (!Main.build.contains(p)) {
			e.setCancelled(true);
		}
	}
}
