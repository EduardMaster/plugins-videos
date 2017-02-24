package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Kit;

public class Vitality extends Kit {
	public ItemStack soup = API.newItem(Material.BROWN_MUSHROOM, "§6Sopa");
	public Sounds sound = Sounds.create(Sound.LEVEL_UP);
	
	public Vitality() {
		setIcon(Material.MUSHROOM_SOUP, "Ao eliminar um Inimigo vai ganhar sopas");
		
	}

	@EventHandler
	public void event(EntityDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player p = (Player) e.getEntity().getKiller();
			if (p == null) {
				return;
			}
			API.fill(p.getInventory(), soup);
			if (hasKit(p)) {
				sound.create(p);
			}
			p.sendMessage(getMessage());
		}
	}
}
