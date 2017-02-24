
package net.eduard.craft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
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
		ItemStack item = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6Armadura Chain Craftavel!");
		item.setItemMeta(meta);
		ShapedRecipe craft = new ShapedRecipe(item);
		craft.shape("A A", "AAA", "AAA");
		craft.setIngredient('A', Material.GOLD_BLOCK);
		Bukkit.addRecipe(craft);
	}

}
