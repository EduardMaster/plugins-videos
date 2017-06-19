package net.eduard.api.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.eduard.api.API;
import net.eduard.api.config.Save;
import net.eduard.api.config.Section;
import net.eduard.api.util.Cs;

public class Tag implements Save {

	private static BukkitTask task;
	private static Map<Player, Tag> tags = new HashMap<>();

	public static Map<Player, Tag> getTags() {
		return tags;
	}

	public static void update() {
		Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();
		for (Player p : API.getPlayers()) {
			Scoreboard score = p.getScoreboard();
			if (score == null) {
				p.setScoreboard(main);
				score = main;
				continue;
			}
			for (Entry<Player, Tag> map : tags.entrySet()) {
				Tag tag = map.getValue();
				Player player = map.getKey();
				if (player == null)
					continue;
				String name = player.getName();
				Team team = score.getTeam(name);
				if (team == null)
					team = score.registerNewTeam(name);
				team.setPrefix(Cs.toText(Cs.toChatMessage(tag.getPrefix())));
				team.setSuffix(Cs.toText(Cs.toChatMessage(tag.getSuffix())));
				if (!team.hasPlayer(player))
					team.addPlayer(player);

			}
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
	private String prefix, suffix, name;

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
