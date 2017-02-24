
package me.eduard.parkour;

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

import java.util.List;

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

	public void Arenas() {

		cf.createSection("Arenas.");
	}

	public void createArena(String name) {

		cf.createSection("Arenas." + name);
	}

	public void deleteArena(String name) {

		cf.set("Arenas." + name, null);
	}

	public void deleteArenas() {

		cf.set("Arenas.", null);
	}

	public Location getEnd(String name) {

		return getLocation("Arenas." + name + ".End");
	}

	public Location getLobby() {

		return getLocation("Lobby");
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

	public Location getSpawn(String name) {

		return getLocation("Arenas." + name + ".Spawn");
	}

	public boolean hasEnd(String name) {

		return cf.contains("Arenas." + name + ".End");
	}

	public boolean hasLobby() {

		return cf.contains("Lobby");
	}

	public boolean hasSpawn(String name) {

		return cf.contains("Arenas." + name + ".Spawn");
	}

	public void Msg() {

		addMsg("console", "&cVoce nao pode usar este comando pelo console!");
		cf.options().copyDefaults(true);
		saveConfig();
	}

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

	public void onDisable() {

		HandlerList.unregisterAll();
	}

	public void onEnable() {

		pm.registerEvents(this, this);

	}

	public void onLoad() {

		Msg();
	}

	public List<String> onTabComplete(CommandSender sender, Command command,
		String alias, String[] args) {

		return null;
	}

	public void setEnd(String name, Location loc) {

		setLocation("Arenas." + name + ".End", loc);
	}

	public void setLobby(Location loc) {

		setLocation("Lobby", loc);
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

	public void setSpawn(String name, Location loc) {

		setLocation("Arenas." + name + ".Spawn", loc);
	}
}
