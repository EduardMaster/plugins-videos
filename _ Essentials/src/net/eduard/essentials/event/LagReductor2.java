package net.eduard.essentials.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import net.eduard.api.lib.Mine;

public class LagReductor2 implements Listener {
	@SuppressWarnings("unchecked")
	@EventHandler
	public void event(PlayerPickupItemEvent e) {
		Item item = e.getItem();
		if (item.hasMetadata("LagReductor")) {
			Mine.broadcast("Pegando");
			List<ItemStack> list = (List<ItemStack>) item
					.getMetadata("LagReductor").get(0).value();
			Mine.broadcast("Tamanho da Lista"+list.size());
			for (ItemStack valor : list) {
				e.getPlayer().getInventory().addItem(valor);
			}
			e.setCancelled(true);
			e.getItem().remove();
		}

	}
	@SuppressWarnings("unchecked")
	@EventHandler
	public void event(EntitySpawnEvent e) {
		if (e.getEntity() instanceof Item) {
			Item item = (Item) e.getEntity();
			ItemStack stack = item.getItemStack();
			List<ItemStack> lista = new ArrayList<>();
			lista.add(stack);
			for (Entity en : item.getNearbyEntities(2, 2, 2)) {
				if (en instanceof Item) {
					Item entity = (Item) en;
					ItemStack other = entity.getItemStack();
					if (stack.isSimilar(other)) {

						if (entity.hasMetadata("LagReductor")) {
							List<ItemStack> newList = (List<ItemStack>) entity
									.getMetadata("LagReductor").get(0).value();
							Mine.broadcast("NewLista size "+newList.size());
							lista.addAll(newList);

						} else {
							
							Mine.broadcast("NewStack added");
							lista.add(stack);
						}
						entity.remove();
					}

				}
			}
			
			stack = stack.clone();
			stack.setAmount(1);
			item.setItemStack(stack);
			item.setMetadata("LagReductor",
					new FixedMetadataValue(Main.getInstance(), lista));

		}
	}

}
