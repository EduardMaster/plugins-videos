
package net.eduard.efeito;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin {

	public static Main instance;

	public static FileConfiguration config;

	public static PluginManager plugin;

	public static BukkitScheduler scheduler;

	public static ScoreboardManager scoreboard;

	public static ConsoleCommandSender console;

	public void onEnable() {

		Main.instance = this;
		Main.config = getConfig();

		if (Bukkit.getPluginManager() == null) {
			new BukkitRunnable() {

				public void run() {

					Main.plugin = Bukkit.getPluginManager();
					Main.scheduler = Bukkit.getScheduler();
					Main.scoreboard = Bukkit.getScoreboardManager();
					Main.console = Bukkit.getConsoleSender();
				}

			}.runTask(this);
		} else {
			Main.plugin = Bukkit.getPluginManager();
			Main.scheduler = Bukkit.getScheduler();
			Main.scoreboard = Bukkit.getScoreboardManager();
			Main.console = Bukkit.getConsoleSender();
		}

		new BukkitRunnable() {

			public void run() {

				for (Player jogador : Bukkit.getOnlinePlayers()) {
					PlayerInventory inv = jogador.getInventory();

					if (inv.getHelmet() == null) {
						continue;
					}
					if (inv.getHelmet().getType() == Material.DIAMOND_HELMET) {
						jogador.addPotionEffect(
							new PotionEffect(PotionEffectType.SPEED, 6, 30), true);
					}
				}
			}

		}.runTaskTimer(this, 5, 5);

	}

}
