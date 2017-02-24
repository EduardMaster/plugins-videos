package net.eduard.kits;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.dev.Explosion;
import net.eduard.api.gui.Kit;

public class DemoMan extends Kit {
	
	public Explosion explosion = new Explosion(6, false, false);

	public DemoMan() {
		setIcon(Material.GRAVEL, "Exploda seus inimigos com armadilhas");
		add(new ItemStack(Material.GRAVEL, 8));
	}

	public static ArrayList<Location> locs = new ArrayList<>();

	@EventHandler
	public void event(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (hasKit(p)) {
			if (e.getBlock().getType() == Material.GRAVEL) {
				locs.add(e.getBlock().getLocation());
			}
		}
	}

	@EventHandler
	public void event(BlockBreakEvent e) {
		if (e.getBlock().getType() == Material.GRAVEL) {
			locs.remove(e.getBlock().getLocation());
		}
	}

	@EventHandler
	public void event(PlayerMoveEvent e) {
		if (!API.equals(e.getFrom(), e.getTo())) {
			for (Location loc : locs) {
				if (API.equals(loc, e.getTo().subtract(0, 1, 0))) {
					locs.remove(loc);
					explosion.create(loc);
					break;
				}
			}
		}
	}

}
