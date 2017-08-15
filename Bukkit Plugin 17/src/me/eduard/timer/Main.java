
package me.eduard.timer;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {

	public static Main m;

	public static FileConfiguration cf;

	public static BukkitScheduler sh;

	public static PluginManager pm;

	public HashMap<Player, Integer> cd = new HashMap<>();

	public HashMap<Player, Integer> task = new HashMap<>();

	public HashMap<Player, Integer> taskdl = new HashMap<>();

	public HashMap<Player, Integer> tasktm = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
		String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas para Players!");
			return true;
		}
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("cd")) {
			if (task.containsKey(p)) {
				p.sendMessage(ChatColor.RED + "Voce esta no cooldown! " + cd.get(p));
				return true;
			}
			final Player pf = p;
			int TaskID = sh.scheduleSyncRepeatingTask(this, new Runnable() {

				int numero = 10;

				@Override
				public void run() {

					if (numero > 0) {
						numero--;
						cd.put(pf, numero);
					}
					if (numero == 0) {
						sh.cancelTask(task.get(pf));
					}

				}

			}, 0, 20);
			task.put(p, TaskID);
			p.sendMessage(ChatColor.GREEN + "Voce usou o cooldown!");
		}
		if (cmd.getName().equalsIgnoreCase("dl")) {
			final Player pf = p;
			if (taskdl.containsKey(p)) {
				p.sendMessage("§cVoce ja usou este comando!");
				return true;
			}
			int taskID = sh.scheduleSyncDelayedTask(this, new Runnable() {

				@Override
				public void run() {

					pf.sendMessage("§cO delay acabou!");
					sh.cancelTask(taskdl.get(pf));
					taskdl.remove(pf);
				}

			}, 5 * 20);
			taskdl.put(p, taskID);
		}
		if (cmd.getName().equalsIgnoreCase("tm")) {
			final Player pf = p;
			int TaskID = sh.scheduleSyncRepeatingTask(this, new Runnable() {

				int numero = 10;

				@Override
				public void run() {

					if (numero > 0) {
						numero--;
						pf.sendMessage("§cTimer correndo! " + numero);
					}
					if (numero == 0) {
						pf.sendMessage("§cTimer acabou!");
						sh.cancelTask(task.get(pf));
						tasktm.remove(pf);
					}

				}

			}, 0, 20);
			tasktm.put(p, TaskID);
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
