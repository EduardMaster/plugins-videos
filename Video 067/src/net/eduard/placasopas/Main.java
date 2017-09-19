
package net.eduard.placasopas;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void event(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!(e.getClickedBlock().getState() instanceof Sign)) return;
		Sign sign = (Sign) e.getClickedBlock().getState();
		if (!sign.getLine(0).equalsIgnoreCase("Sopas")) return;
		Inventory inv = Bukkit.createInventory(null, 6 * 9, "§7Sopas");
		for (int i = 0; i < 6 * 9; i++) {
			inv.addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}
		p.openInventory(inv);

	}
}
