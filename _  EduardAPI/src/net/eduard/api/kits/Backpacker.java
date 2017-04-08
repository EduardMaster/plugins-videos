package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.gui.Kit;


public class Backpacker extends Kit{
	
	public ItemStack soup = API.newItem(Material.BROWN_MUSHROOM, "§6Sopa");
	
	public Backpacker() {
		setIcon(Material.LAPIS_BLOCK, "§fGanhe uma espada Melhor");
		add(Material.WOOD_SWORD);
		setClick(new Click(Material.NETHER_STAR, new ClickEffect() {
			
			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)){
					if (cooldown(p)){
						Inventory inv = API.newInventory("§7Backpacker", 6*9);
						API.fill(inv, soup);
						p.openInventory(inv);
					}
				}
			}
			
			@Override
			public void effect(PlayerInteractEntityEvent e) {
				
			}
		}));
	}

}
