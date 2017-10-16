
package me.eduard.doublejump;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	private List<String> jumpers = new ArrayList<>();

	@EventHandler
	public void ativar(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		Block block = p.getLocation().getBlock();
		if (p.getGameMode() != GameMode.CREATIVE
			&& block.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
			p.setAllowFlight(true);
		}
	}

	@EventHandler
	public void dano(EntityDamageEvent e) {

		if (e.getEntity() instanceof Player && e.getCause() == DamageCause.FALL) {
			Player p = (Player) e.getEntity();
			if (jumpers.contains(p.getName())) {
				e.setCancelled(true);
				jumpers.remove(p.getName());
			}
		}
	}

	@Override
	public void onDisable() {

		HandlerList.unregisterAll();
	}

	@Override
	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onLoad() {

		m = this;
		cf = getConfig();
		sh = Bukkit.getScheduler();
		pm = Bukkit.getPluginManager();
		saveDefaultConfig();
	}

	@EventHandler
	public void usar(PlayerToggleFlightEvent e) {

		Player p = e.getPlayer();
		if (p.getGameMode() != GameMode.CREATIVE) {
			p.setAllowFlight(false);
			e.setCancelled(true);
			p.setVelocity(p.getLocation().getDirection().multiply(3).setX(2));
			if (!jumpers.contains(p.getName())) {
				jumpers.add(p.getName());
			}
		}
	}
}
