package net.eduard.hg.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.gui.SimpleCraft;
import net.eduard.api.manager.EventAPI;
import net.eduard.hg.manager.HG;

public class Soup extends EventAPI {

	public Soup() {
		ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
		new SimpleCraft(soup).add(Material.BOWL).add(Material.INK_SACK, 3).addRecipe();
		new SimpleCraft(soup).add(Material.BOWL).add(Material.CACTUS).addRecipe();
		new SimpleCraft(soup).add(Material.BOWL).add(Material.PUMPKIN_SEEDS).addRecipe();
		new SimpleCraft(soup).add(Material.BOWL).add(Material.NETHER_STALK).addRecipe();
		new SimpleCraft(soup).add(Material.BOWL).add(Material.RED_ROSE).add(Material.YELLOW_FLOWER).addRecipe();

	}

	@EventHandler
	public void event(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getMaterial() == Material.MUSHROOM_SOUP) {
			double health = p.getHealth();
			double max = p.getMaxHealth();
			if (health < max) {
				double calc = health + HG.soupValue > max ? max : health + HG.soupValue;
				e.getItem().setType(Material.BOWL);
				p.setHealth(calc);
				e.setCancelled(true);
			}

		}
	}
}
