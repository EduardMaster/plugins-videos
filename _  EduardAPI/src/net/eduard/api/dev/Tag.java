package net.eduard.api.dev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class Tag implements Save{
	
	private static BukkitTask task;
	private static Map<UUID, Tag> tags = new HashMap<>();

	public static Map<UUID, Tag> getTags() {
		return tags;
	}

	public static void update() {
		ArrayList<Scoreboard> boards = new ArrayList<>();
		Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();
		for (Player p : Bukkit.getOnlinePlayers()) {
			Scoreboard score = p.getScoreboard();
			if (score != null & !score.equals(main) & !boards.contains(score)) {
				boards.add(p.getScoreboard());
				update(p.getScoreboard());
			}
		}
		update(main);
	}

	private static void update(Scoreboard board) {
		for (Entry<UUID, Tag> map : tags.entrySet()) {
			Tag tag = map.getValue();
			Player player = Bukkit.getPlayer(map.getKey());
			if (player == null)
				continue;
			String name = player.getName();
			Scoreboard score = player.getScoreboard();
			Team team = score.getTeam(name);
			if (team == null)
				team = score.registerNewTeam(name);
			team.setPrefix(API.toText(tag.getPrefix()));
			team.setSuffix(API.toText(tag.getSuffix()));
			if (!team.hasPlayer(player))
				team.addPlayer(player);

		}
	}


	public static void enable(JavaPlugin plugin) {
		if (task != null) {
			task.cancel();
		}
		task = new BukkitRunnable() {

			public void run() {
				update();
			}
		}.runTaskTimer(plugin, 20, 20);
	}

	public static void disable() {
		if (task != null) {
			task.cancel();
			task = null;
		}
	}
	private String prefix, suffix,name;


	public Tag(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public String getPrefix() {
		return prefix;
	}

	public Tag setPrefix(String prefix) {
		this.prefix = prefix;
		return this;
		
	}

	public String getSuffix() {
		return suffix;
	}

	public Tag setSuffix(String suffix) {
		this.suffix = suffix;
		return this;
	}

	public String getName() {
		return name;
	}

	public Tag setName(String name) {
		this.name = name;
		return this;
	}

	public Object get(Section section) {
		return null;
	}

	public void save(Section section, Object value) {
		
	}


}
