
package me.eduard.tapete;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class TapeteVoador implements Listener {

	private static List<String> players = new ArrayList<>();

	private static Material material = Material.DIAMOND_BLOCK;

	private static BlockFace[] blocks =
		new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.SELF,
			BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH_EAST,
			BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST, BlockFace.SOUTH_WEST };

	public static void TapeteVoadorTimer() {

		new BukkitRunnable() {

			@Override
			public void run() {

				List<String> list = players;
				for (String pn : list) {
					Player p = Bukkit.getPlayerExact(pn);
					if (p.isSneaking()) {
						List<Block> blocos = new ArrayList<>();
						Block blocoRelativo =
							p.getLocation().getBlock().getRelative(BlockFace.DOWN);
						for (BlockFace blockFace : blocks) {
							blocos.add(blocoRelativo.getRelative(blockFace));
						}
						for (Block b : blocos) {
							if (b.getType() == material) {
								b.setType(Material.AIR);
							}
						}
					}
				}

			}
		}.runTaskTimer(Main.m, 2, 2);
	}

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
					if (p.isSneaking()) {
						if (e.getFrom().getY() < e.getTo().getY()) {
							e.setCancelled(true);
							return;
						}
					}

					Block blockFrom =
						e.getFrom().getBlock().getRelative(BlockFace.DOWN);
					Block blockTo = e.getTo().getBlock().getRelative(BlockFace.DOWN);
					List<Block> listFrom = new ArrayList<>();
					List<Block> listTo = new ArrayList<>();
					List<Block> listDeletados = new ArrayList<>();
					for (BlockFace blockFace : blocks) {
						listFrom.add(blockFrom.getRelative(blockFace));
						listTo.add(blockTo.getRelative(blockFace));
					}
					for (Block b : listFrom) {
						if (!listTo.contains(b)) {
							listDeletados.add(b);
						}
					}
					for (Block b : listDeletados) {
						if (b.getType() == material) {
							b.setType(Material.AIR);
						}
					}
					for (Block b : listTo) {
						if (b.getType() == Material.AIR) {
							b.setType(material);
						}
					}

				}
			}
		}
	}
}
