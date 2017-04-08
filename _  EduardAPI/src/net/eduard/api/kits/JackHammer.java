package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.click.ClickType;
import net.eduard.api.gui.Kit;

public class JackHammer extends Kit{
	
	public JackHammer() {
		setIcon(Material.STONE_AXE, "§fCrie grandes buracos na terra");
		add(Material.STONE_AXE);
		setTime(20);
		setTimes(8);
		setClick(new Click(Material.STONE_AXE, new ClickEffect() {
			
			@Override
			public void effect(PlayerInteractEntityEvent e) {
				
			}

			@Override
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

		}));
		getClick().setType(ClickType.BLOCK);
	}
}
