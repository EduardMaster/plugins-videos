
package me.eduard.kit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
		String[] args) {

		if (cmd.getName().equalsIgnoreCase("kit")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				PlayerInventory inv = p.getInventory();
				ItemStack item1 = new ItemStack(Material.DIAMOND_SWORD, 1);
				ItemStack item2 =
					new ItemStack(Material.DIAMOND_SWORD, 1, (short) 1);
				item1.addEnchantment(Enchantment.DAMAGE_ALL, 10);
				inv.addItem(item1);
				inv.addItem(item2);
			}
		}
		return false;
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
	}
}
