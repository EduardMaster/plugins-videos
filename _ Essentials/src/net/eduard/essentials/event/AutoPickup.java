package net.eduard.essentials.event;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.lib.Mine;
import net.eduard.api.lib.manager.EventsManager;
import net.eduard.essentials.EssentialsPlugin;

public class AutoPickup extends EventsManager {

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void aoQuebrar(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (!EssentialsPlugin.getInstance().getBoolean("auto-pickup-enabled")) {
			return;
		}
		if (p.getItemInHand() == null)
			return;
		if (p.getItemInHand().getType() == Material.AIR)
			return;

		if (Mine.isFull(p.getInventory())) {

			p.sendMessage(EssentialsPlugin.getInstance().message("inventory-full"));

			Collection<ItemStack> lista = e.getBlock().getDrops(p.getItemInHand());

			for (ItemStack item : lista) {
				e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), item);
			}

		} else {

			Collection<ItemStack> lista = e.getBlock().getDrops(p.getItemInHand());
			for (ItemStack item : lista) {
				p.getInventory().addItem(item);
			}

			new BukkitRunnable() {

				@Override
				public void run() {
					Collection<Entity> drops = e.getBlock().getLocation().getWorld()
							.getNearbyEntities(e.getBlock().getLocation(), 1, 1, 1);
					for (Entity entidade : drops) {
						if (entidade instanceof Item) {
							Item drop = (Item) entidade;
							drop.setPickupDelay(300);
							drop.remove();

						}
					}

				}
			}.runTaskLaterAsynchronously(EssentialsPlugin.getInstance(), 1);
		}
	}
}
