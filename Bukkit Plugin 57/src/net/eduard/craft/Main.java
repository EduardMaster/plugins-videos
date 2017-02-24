
package net.eduard.craft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Arrays;

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
		ItemStack item = new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6Mega maça dourada!");
		meta.setLore(Arrays.asList("§cMaça capirotão!"));
		item.setItemMeta(meta);
		ShapelessRecipe craft = new ShapelessRecipe(item);

		craft.addIngredient(Material.APPLE);
		craft.addIngredient(Material.GOLD_BLOCK);

		Bukkit.addRecipe(craft);

	}

}
