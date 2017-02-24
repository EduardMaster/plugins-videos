
package me.eduard.soup;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.List;

public class Main extends JavaPlugin implements Listener {

	public Main main = this;

	public Server server = Bukkit.getServer();

	public ScoreboardManager score = Bukkit.getScoreboardManager();

	public FileConfiguration cf = getConfig();

	public PluginManager pm = Bukkit.getPluginManager();

	public BukkitScheduler sh = Bukkit.getScheduler();

	public CommandSender send = Bukkit.getConsoleSender();

	public int vida = 6, fome = 6;

	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para players!");
			return true;
		}
		Player p = (Player) sender;
		if (command.getName().equalsIgnoreCase("soup")) {
			for (ItemStack i : p.getInventory().getContents()) {
				if (i == null) {
					p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
				}
			}
		}
		return true;
	}

	public void onDisable() {

		HandlerList.unregisterAll();
	}

	public void onEnable() {

		pm.registerEvents(this, this);
	}

	public void onLoad() {

	}

	public List<String> onTabComplete(CommandSender sender, Command command,
		String alias, String[] args) {

		return null;
	}

	@EventHandler
	public void UsarSopa(PlayerInteractEvent e) {

		if (e.getItem() == null) {
			return;
		}
		if (e.getItem().getType() == Material.MUSHROOM_SOUP) {
			e.setCancelled(true);
			Player p = e.getPlayer();
			if (p.getHealth() < p.getMaxHealth()) {
				p.setHealth(p.getHealth() + vida > p.getMaxHealth()
					? p.getMaxHealth() : p.getHealth() + vida);
				e.getItem().setType(Material.BOWL);
			}
			if (p.getFoodLevel() < 20) {
				p.setFoodLevel(
					fome + p.getFoodLevel() > 20 ? 20 : fome + p.getFoodLevel());
				if (p.getFoodLevel() == 20) {
					p.setExhaustion(0);
				}
			}
		}

	}

}
