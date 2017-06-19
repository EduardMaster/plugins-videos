package net.eduard.api.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Vanish implements Runnable {

	private List<Player> players = new ArrayList<>();

	private boolean canSee;

	public boolean canSee() {
		return canSee;
	}

	public void canSee(boolean canSee) {
		this.canSee = canSee;
	}

	public BukkitTask getTask() {
		return task;
	}

	public void setTask(BukkitTask task) {
		this.task = task;
	}
	private BukkitTask task;

	public boolean enabled() {
		return task != null;
	}

	public void enable(Plugin plugin) {
		if (!enabled()) {
			task = Bukkit.getScheduler().runTaskTimer(plugin, this, 20, 20);
		}
	}
	public void disable() {
		if (enabled()) {
			task.cancel();
			task = null;
		}
	}

	public void update() {
		for (Player player : players) {
			hidePlayer(player);
		}
		if (canSee){
			for (Player player : players) {
				for (Player target:players){
					if (target.equals(player))continue;
					target.showPlayer(player);
				}
			}
		}
		
	}
	public static void hidePlayer(Player player) {
		for (Player p : getPlayers()) {
			if (player.equals(p))
				continue;
			p.hidePlayer(player);
		}
	}
	public static void showPlayer(Player player) {
		for (Player p : getPlayers()) {
			if (player.equals(p))
				continue;
			p.showPlayer(player);
		}
	}
	@SuppressWarnings("unchecked")
	public static List<Player> getPlayers() {

		try {

			Method getOnlinePlayers = Bukkit.class
					.getMethod("getOnlinePlayers");
			getOnlinePlayers.setAccessible(true);
			Object onlines = getOnlinePlayers.invoke(Bukkit.class);
			if (onlines instanceof List) {
				return (List<Player>) onlines;
			} else {
				return Arrays.asList((Player[]) onlines);
			}

		} catch (Exception e) {
		}
		return null;

	}
	public boolean isHidden(Player player) {
		return players.contains(player);
	}

	public void show(Player player) {
		players.remove(player);
	}
	public void hide(Player player) {
		players.add(player);
	}
	public void run() {
		update();
	}

}
