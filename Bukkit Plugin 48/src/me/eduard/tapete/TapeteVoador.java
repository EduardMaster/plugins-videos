
package me.eduard.tapete;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TapeteVoador implements Listener {

	private List<String> players = new ArrayList<>();

	private BlockFace[] blocks = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH,
		BlockFace.SELF, BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH_EAST,
		BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST };

	@EventHandler
	public void TapeteVoadorEvent(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		Location from = e.getFrom();
		Location to = e.getTo();
		Location locFrom = new Location(from.getWorld(), (int) from.getX(),
			(int) from.getY(), (int) from.getZ());
		Location locTo = new Location(to.getWorld(), (int) to.getX(),
			(int) to.getY(), (int) to.getZ());
		if (locFrom.equals(locTo)) {
			if (players.contains(p.getName())) {
				if (p.getGameMode() == GameMode.SURVIVAL) {

					e.getFrom().getBlock().getRelative(BlockFace.DOWN);
					e.getTo().getBlock().getRelative(BlockFace.DOWN);
					new ArrayList<>();
					new ArrayList<>();
				}
			}
		}
	}
}
