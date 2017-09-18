package net.eduard.template;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {

	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	public Configs config;
	@Override
	public void onEnable() {
		plugin = this;
		config = new Configs("config.yml");;
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (config.contains("spawn")) {
			p.teleport(config.getLocation("spawn"));
			p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
		}
	}
	@EventHandler
	public void event(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		if (config.contains("spawn")) {
			e.setRespawnLocation(config.getLocation("spawn"));
			new BukkitRunnable() {

				@Override
				public void run() {
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
				}
			}.runTask(this);
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (command.getName().equalsIgnoreCase("spawn")) {
				// if (config.contains("spawn")) {
				// p.teleport(config.getLocation("spawn"));
				// p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
				// p.sendMessage("tp");
				// }
				p.teleport(p.getWorld().getSpawnLocation()
						.setDirection(p.getLocation().getDirection()));
			} else if (command.getName().equalsIgnoreCase("setspawn")) {
				config.setLocation("spawn", p.getLocation());
				config.saveConfig();
				p.getWorld().setSpawnLocation(p.getLocation().getBlockX(),
						p.getLocation().getBlockY(),
						p.getLocation().getBlockZ());
			}

		} else {

		}

		return true;
	}

}
