
package net.eduard.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener {

	public static Main instance;

	public static FileConfiguration config;

	public static PluginManager plugin;

	public static BukkitScheduler scheduler;

	public static ScoreboardManager scoreboard;

	public static ConsoleCommandSender console;

	@EventHandler
	public void AbrirGUI(PlayerInteractEvent e) {

		Player p = e.getPlayer();
		if (e.getItem() == null) {
			return;
		}
		if (e.getItem().getType() == Material.DIAMOND) {
			Inventory inv = Bukkit.createInventory(null, 9, "Warps");
			ItemStack item = new ItemStack(Material.BED);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§6Spawn");
			item.setItemMeta(meta);
			inv.addItem(item);
			p.openInventory(inv);
		}
	}

	@EventHandler
	public void ClicarDentroDaGui(InventoryClickEvent e) {

		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory().getTitle().equals("Warps")) {
				if (e.getCurrentItem().getType() == Material.BED) {
					p.teleport(p.getWorld().getSpawnLocation());
					p.sendMessage("§6Voce foi teleporatdo para o Spawn!");
					p.closeInventory();
					p.addPotionEffect(
						new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 0));
					p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 2, 1);
					e.setCancelled(true);
				}
			}
		}
	}

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

		Bukkit.getPluginManager().registerEvents(this, this);

	}

}
