package net.eduard.hg.event;

import net.eduard.hg.HG;
import net.eduard.hg.HGState;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class HGWorldRegeneration implements Listener{
	@EventHandler
	public void event(BlockBreakEvent e) {

		if (HG.state == HGState.STARTING | HG.state == HGState.RESTARTING) {
			e.setCancelled(true);
		} else {
			HG.blocks.add(e.getBlock().getRelative(BlockFace.UP).getState());
			HG.blocks.add(e.getBlock().getState());
		}
	}

	@EventHandler
	public void event(EntityExplodeEvent e) {

		if (HG.state == HGState.STARTING | HG.state == HGState.RESTARTING) {
			e.setCancelled(true);
		} else {
			for (Block block : e.blockList()) {
				HG.blocks.add(block.getRelative(BlockFace.UP).getState());
				HG.blocks.add(block.getState());

			}
		}

	}

	@EventHandler
	public void event(BlockPlaceEvent e) {

		if (HG.state == HGState.STARTING | HG.state == HGState.RESTARTING) {
			e.setCancelled(true);
		} else {
			HG.blocks.add(e.getBlockAgainst().getRelative(BlockFace.UP).getState());
			HG.blocks.add(e.getBlockReplacedState());
		}
	}
}
