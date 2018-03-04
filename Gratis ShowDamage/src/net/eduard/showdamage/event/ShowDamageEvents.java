
package net.eduard.showdamage.event;

import java.text.DecimalFormat;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.eduard.api.setup.ChatAPI.Chat;
import net.eduard.api.setup.Mine;
import net.eduard.api.setup.Mine.EventsManager;
import net.eduard.showdamage.Main;

public class ShowDamageEvents extends EventsManager {

	public static DecimalFormat formato = new DecimalFormat("#.#");

	@EventHandler
	public void event(PlayerMoveEvent e) {

	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
	}

	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		// if (e.getDamager() instanceof Player) {
		// Player p = (Player) e.getDamager();
		double dano = e.getFinalDamage();
		if (e.getEntity() instanceof ArmorStand)
			return;
		if (dano == 0)
			return;
		createTempArmourStand(e.getEntity().getLocation(), dano);
		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			
			Mine.sendActionBar(p, "§e" + (formato.format(dano / 2)) + "§c§l" + Chat.getHeart());
			
		}else {
			if (e.getDamager() instanceof Projectile) {
				Projectile projectile = (Projectile) e.getDamager();
				
				if (projectile.getShooter() instanceof Player) {
					Player p = (Player) projectile.getShooter();
					Mine.sendActionBar(p, "§e" + (formato.format(dano / 2)) + "§c§l" + Chat.getRedHeart());
				}
				
			} 
	
		}
		// if (e.getDamager() instanceof Projectile) {
		// Projectile projectile = (Projectile) e.getDamager();
		// if (projectile.getShooter() instanceof LivingEntity) {
		//
		// LivingEntity livingEntity = (LivingEntity) projectile.getShooter();
		// double distancia =
		// livingEntity.getLocation().distance(e.getEntity().getLocation());
		// System.out.println(distancia);
		// if (distancia>5) {
		// createTempArmourStand(livingEntity.getLocation(), dano);
		// }
		// }
		//
		// }
		//

		// }
	}

	public static void createTempArmourStand(Location location, double dano) {
		ArmorStand armor = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		armor.setVisible(false);
		armor.setCustomName("§e" + (formato.format(dano / 2)) + "§c§l" + Chat.getHeart());
		armor.setCustomNameVisible(true);
		// armor.setGravity(false);
		armor.setSmall(true);
		armor.setVelocity(new Vector(0, 0.6, 0));
		new BukkitRunnable() {

			@Override
			public void run() {
				armor.remove();
			}
		}.runTaskLater(Main.getPlugin(), 20);
	}
}
