package net.eduard.api.manager;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.gui.Drop;
import net.eduard.api.gui.DropItem;

public class DropManager extends EventAPI{
	@EventHandler
	public void event(EntityDeathEvent e) {

		if (e.getEntity() instanceof Player) {
			return;
		}
		LivingEntity entity = e.getEntity();
		Drop drop = Drop.getDrop(entity.getWorld(), e.getEntityType());
		if (drop.isEnable()) {
			if (!drop.isNormalDrops()) {
				e.getDrops().clear();
			}
			e.setDroppedExp(drop.getRandomXp());
			for (DropItem itemDrop : drop.getDrops()) {
				ItemStack item = itemDrop.create();
				e.getDrops().add(item);
			}
		}

	}
	
}
