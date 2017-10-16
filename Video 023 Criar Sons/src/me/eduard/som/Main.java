
package me.eduard.som;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener {

	public Main main = this;

	public Server server = Bukkit.getServer();

	public ScoreboardManager score = Bukkit.getScoreboardManager();

	public FileConfiguration cf = getConfig();

	public PluginManager pm = Bukkit.getPluginManager();

	public BukkitScheduler sh = Bukkit.getScheduler();

	public CommandSender send = Bukkit.getConsoleSender();

	@EventHandler
	public void FazerSom(EntityDamageEvent e) {

		if (!(e.getEntity() instanceof Player)) {
			e.getEntity().getWorld().playSound(e.getEntity().getLocation(),
				Sound.ANVIL_LAND, 1, 1);
		} else {
			e.getEntity().getWorld().playSound(e.getEntity().getLocation(),
				Sound.EXPLODE, 1, 1);
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		return true;
	}

	@Override
	public void onDisable() {

		HandlerList.unregisterAll();
	}

	@Override
	public void onEnable() {

		pm.registerEvents(this, this);
	}

	@Override
	public void onLoad() {

	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
		String alias, String[] args) {

		return null;
	}

}
