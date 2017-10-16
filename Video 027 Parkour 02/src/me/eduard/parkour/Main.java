
package me.eduard.parkour;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
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

	public void addMsg(String name, String message) {

		cf.addDefault("Messages." + name, message);
	}

	public Location getLocation(String name) {

		World world = Bukkit.getWorld(cf.getString(name + ".world"));
		double x = cf.getDouble(name + ".x");
		double y = cf.getDouble(name + ".y");
		double z = cf.getDouble(name + ".z");
		double yaw = cf.getDouble(name + ".yaw");
		double pitch = cf.getDouble(name + ".pitch");
		return new Location(world, x, y, z, (float) yaw, (float) pitch);
	}

	public String getMsg(String name) {

		return ChatColor.translateAlternateColorCodes('&',
			cf.getString("Messages." + name));
	}

	public void Msg() {

		addMsg("console", "&cVoce nao pode usar este comando pelo console!");
		cf.options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage(getMsg("console"));
			return true;
		}
		Player p = (Player) sender;
		if (command.getName().equalsIgnoreCase("parkour")) {
			p.sendMessage("funciona");
		}
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

		Msg();
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
		String alias, String[] args) {

		return null;
	}

	public void setLocation(String name, Location loc) {

		cf.set(name + ".world", loc.getWorld().getName());
		cf.set(name + ".x", loc.getX());
		cf.set(name + ".y", loc.getY());
		cf.set(name + ".z", loc.getZ());
		cf.set(name + ".yaw", loc.getYaw());
		cf.set(name + ".pitch", loc.getPitch());
		saveConfig();
	}
}
