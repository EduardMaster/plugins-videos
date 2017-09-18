
package me.eduard.regenerarblocos;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	private List<BlockState> blocks = new ArrayList<>();

	@EventHandler
	public void BlocoExplode(EntityExplodeEvent e) {

		for (Block b : e.blockList()) {
			blocks.add(b.getState());
			final BlockState b2 = b.getState();
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {

					b2.getBlock().setType(b.getType());
				}

			}, 3 * 20);
		}

	}

	@EventHandler
	public void BlocoPlace(BlockPlaceEvent e) {

		blocks.add(e.getBlockReplacedState());
		final BlockState b = e.getBlockReplacedState();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {

				b.getBlock().setType(b.getType());
			}

		}, 3 * 20);
	}

	@EventHandler
	public void BlocoQuebra(BlockBreakEvent e) {

		blocks.add(e.getBlock().getState());
		final BlockState b = e.getBlock().getState();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

			@Override
			public void run() {

				b.getBlock().setType(b.getType());
			}

		}, 3 * 20);
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
}
