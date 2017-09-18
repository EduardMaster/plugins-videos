
package me.eduard.kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Apenas para players!");
			return true;
		}
		Player p = (Player) sender;
		PlayerInventory inv = p.getInventory();
		if (cmd.getName().equalsIgnoreCase("kit")) {
			if (args.length == 0) {
			} else if (args.length == 1) {
				if (getConfig().getConfigurationSection("Kits") != null) {
					for (String kits : cf.getConfigurationSection("Kits")
						.getKeys(false)) {
						if (kits.equalsIgnoreCase(args[0])) {
							if (cf.getString("Kits." + kits).contains(", ")) {
								for (String items : cf.getString("Kits." + kits)
									.split(", ")) {
									String[] info = items.split("-");
									int id = Integer.valueOf(info[0]);
									int qnt = Integer.valueOf(info[1]);
									ItemStack item = new ItemStack(id, qnt);
									inv.addItem(item);
								}
							} else {
								String[] info =
									cf.getString("Kits." + kits).split(", ");
								int id = Integer.valueOf(info[0]);
								int qnt = Integer.valueOf(info[1]);
								ItemStack item = new ItemStack(id, qnt);
								inv.addItem(item);
							}
							p.sendMessage(ChatColor.GREEN + "Voce ganhou este kit: "
								+ args[0]);
							return true;
						}
					}
					p.sendMessage(ChatColor.RED + "Nao existe esse Kit: " + args[0]);
				} else {
					p.sendMessage(ChatColor.RED + "Nao existem kits!");
				}
			} else if (args.length == 2) {
				p.sendMessage(ChatColor.GREEN + "Digite: /kit <nome>");
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
		saveDefaultConfig();
	}
}
