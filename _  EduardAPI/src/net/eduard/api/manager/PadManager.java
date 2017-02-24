package net.eduard.api.manager;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import net.eduard.api.API;
import net.eduard.api.dev.LaunchPad;

public class PadManager extends EventAPI{
	@SuppressWarnings("deprecation")
	// @EventHandler
	public void event(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		if (!API.NO_FALL.contains(p)) {
			for (LaunchPad pad : API.PADS) {
				Block bloco = p.getLocation().add(0, pad.getBlockHigh(), 0).getBlock();
				if (bloco.getTypeId() == pad.getBlockId() && bloco.getData() == pad.getBlockData()) {
					pad.getEffect().create(p);
					API.NO_FALL.add(p);
					break;
				}
			}
		} else {
			boolean air = false;
			if (e.getFrom().getBlock().getType() == Material.AIR) {
				if (e.getFrom().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
					air = true;
				}
			}
			if (air) {
				if (e.getFrom().getY() > e.getTo().getY()) {
					if (!API.NO_FALL.contains(p)) {
						API.NO_FALL.add(p);
					}
				}
			} else {
				if (e.getFrom().getY() == e.getTo().getY()) {
					API.NO_FALL.remove(p);
				}
				if (p.getVelocity().getY() > 0) {
					API.NO_FALL.add(p);
				}
			}

		}

	}
	@EventHandler(priority=EventPriority.HIGHEST)
	public void event(EntityDamageEvent e) {

		if (e.getEntity() instanceof Player && e.getCause() == DamageCause.FALL) {
			Player p = (Player) e.getEntity();
			if (e.isCancelled()) {
				API.NO_FALL.remove(p);
			} else if (API.NO_FALL.contains(p)) {
				e.setCancelled(true);
				API.NO_FALL.remove(p);
			}
		}

	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void event2(PlayerMoveEvent e) {

		Player p = e.getPlayer();
//		if (API.equals(e.getFrom(), e.getTo()))
//			return;
		if (!API.NO_FALL.contains(p)) {
			for (LaunchPad pad : API.PADS) {
				Block block = p.getLocation().add(0, pad.getBlockHigh(), 0).getBlock();

				boolean data;
				if (pad.getBlockData() == -1) {
					data = true;
				} else {
					data = block.getData() == pad.getBlockData();
				}
				if (block.getTypeId() == pad.getBlockId() && data) {
					pad.getEffect().create(p);
					API.NO_FALL.add(p);
					return;
				}
			}
		} else {
			boolean air = false;
			if (e.getFrom().getBlock().getType() == Material.AIR) {
				if (e.getFrom().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR) {
					air = true;
				}
			}
			if (air) {
				if (e.getFrom().getY() > e.getTo().getY()) {
					if (!API.NO_FALL.contains(p)) {
						API.NO_FALL.add(p);
					}
				}
			} else {
				if (e.getFrom().getY() == e.getTo().getY()) {
					API.NO_FALL.remove(p);
				}
				if (p.getVelocity().getY() > 0) {
					API.NO_FALL.add(p);
				}
			}

		}

	}
}
