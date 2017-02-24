package net.eduard.kits;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import net.eduard.api.API;
import net.eduard.api.dev.Potions;
import net.eduard.api.dev.Sounds;
import net.eduard.api.gui.Kit;

public class Berserker extends Kit {
	public ItemStack soup = API.newItem(Material.BROWN_MUSHROOM, "§6Sopa");
	public Sounds sound = Sounds.create(Sound.AMBIENCE_THUNDER);
	public Potions power = new Potions(PotionEffectType.INCREASE_DAMAGE, 0, 20*30);
	public Potions speed  = new Potions(PotionEffectType.SPEED, 0, 20*30);
	public Berserker() {
		setIcon(Material.MUSHROOM_SOUP, "Ao eliminar um Inimigo vai ganhar sopas");
		setMessage("§6Modo berseker ativado");
	}
	@EventHandler
	public void event(EntityDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {
			Player p = (Player) e.getEntity().getKiller();
			if (p == null) {
				return;
			}
			if (hasKit(p)) {
				p.sendMessage(getMessage());
				sound.create(p);
				speed.create(p);
				power.create(p);
			}
		}
	}
}
