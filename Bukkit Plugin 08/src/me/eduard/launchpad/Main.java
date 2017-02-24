
package me.eduard.launchpad;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	public void onDisable() {

		HandlerList.unregisterAll();
	}

	public void onEnable() {

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	public void onLoad() {

		m = this;
		cf = getConfig();
		sh = Bukkit.getScheduler();
		pm = Bukkit.getPluginManager();
	}

	@EventHandler
	public void PlayerAndar(PlayerMoveEvent e) {

		Player p = e.getPlayer();
		Block block = e.getTo().getBlock().getRelative(BlockFace.DOWN);
		if (block.getType() == Material.DIAMOND_BLOCK) {
			p.setVelocity(p.getLocation().getDirection().multiply(5));
			p.setVelocity(
				new Vector(p.getLocation().getX(), 5, p.getLocation().getZ()));
		}else if (block.getType() == Material.SPONGE) {
			p.setVelocity(new Vector(0, 5, 0));
		}
	}
}
