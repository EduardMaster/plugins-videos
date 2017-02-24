package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;
import net.eduard.api.util.ClickType;

public class JackHammer extends Kit{
	
	public JackHammer() {
		setIcon(Material.STONE_AXE, "Crie grandes buracos na terra");
		add(Material.STONE_AXE);
		setTime(20);
		setTimes(8);
		new Click(Material.STONE_AXE,new ClickEffect() {
			
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					e.setCancelled(false);
					if (cooldown(p)) {
						Block block = e.getClickedBlock();
						block.setType(Material.AIR);
						double y = block.getY();
						while(y>2) {
							block = block.getRelative(BlockFace.DOWN);
							block.setType(Material.AIR);
							y--;
						}
						
					}
					
				}
			}

			public void effect(PlayerInteractEntityEvent e) {
				
			}

		}).setType(ClickType.BLOCK_CLICK);
	}
}
