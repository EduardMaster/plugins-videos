package net.eduard.api.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.eduard.api.API;
import net.eduard.api.game.Tag;
import net.eduard.api.player.Score;
import net.eduard.api.setup.ExtraAPI;
import net.eduard.api.setup.VaultAPI;

public final class Scores extends Manager {

	public static Scores getAPI() {
		return instance;
	}
	private static Scores instance;
	static {
		instance = new Scores();
	}
	private static boolean tagsEnabled;
	private static boolean scoresEnabled;
	private static Score scoreboard = new Score("§aServidor", "§a$player");
	private static Map<Player, Tag> tags = new HashMap<>();
	private static Map<Player, Score> scores = new HashMap<>();
	@EventHandler
	public void event(PlayerQuitEvent e) {
		removeScore(e.getPlayer());
		removeTag(e.getPlayer());
	}
	@EventHandler
	public void event(PlayerKickEvent e) {
		removeScore(e.getPlayer());
		removeTag(e.getPlayer());
	}

	@EventHandler
	public void event(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (scoresEnabled) {
			setScore(e.getPlayer(), scoreboard.copy());
		}
		if (tagsEnabled) {
			updateTagByRank(p);
		}

	}
	private Scores() {
		register(API.PLUGIN);
		API.TIME.timer(20, new Runnable() {

			@Override
			public void run() {
				update();

			}
		});
	}

	public static void update() {
		if (scoresEnabled) {

			try {
				for (Entry<Player, Score> map : scores.entrySet()) {
					Score score = map.getValue();
					Player player = map.getKey();
					score.update(player);
				}
			} catch (Exception e) {
				Bukkit.getLogger().info(
						"Falha ao dar update ocorreu uma Troca de Scoreboard no meio do FOR");
			}
		}
		if (tagsEnabled) {
			Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();

			for (Player p : API.getPlayers()) {
				Scoreboard score = p.getScoreboard();
				if (score == null) {
					p.setScoreboard(main);
					score = main;
					continue;
				}
				updateTags(score);

			}
			updateTags(main);
		}
	}
	@SuppressWarnings("deprecation")
	public static void updateTags(Scoreboard score) {
		for (Entry<Player, Tag> map : tags.entrySet()) {
			Tag tag = map.getValue();
			Player player = map.getKey();
			if (player == null)
				continue;
			String name = ExtraAPI.getText(tag.getRank() + player.getName());
			Team team = score.getTeam(name);
			if (team == null)
				team = score.registerNewTeam(name);
			team.setPrefix(
					ExtraAPI.toText(ExtraAPI.toChatMessage(tag.getPrefix())));
			team.setSuffix(
					ExtraAPI.toText(ExtraAPI.toChatMessage(tag.getSuffix())));
			if (!team.hasPlayer(player))
				team.addPlayer(player);

		}
	}
	public static void updateScoreboard(Player player) {
		getScore(player).update(player);
	}
	public static void updateTagByRank(Player player) {
		String group = VaultAPI.getPermission().getPrimaryGroup(player);
		String prefix = VaultAPI.getChat().getGroupPrefix("null", group);
		String suffix = VaultAPI.getChat().getGroupSuffix("null", group);
		String rank = VaultAPI.getChat().getGroupInfoString("0", group,
				"rank", "");
		VaultAPI.getChat().setGroupInfoString(rank, group,
				"rank", "");
		
		Tag tag = new Tag(prefix, suffix);
		tag.setName(player.getName());
		tag.setRank(ExtraAPI.toInt(rank));
		setTag(player, tag);
	}

	public static void removeScore(Player player) {
		player.setScoreboard(API.getMainScoreboard());
		tags.remove(player);
	}
	private static void removeTag(Player player) {
		tags.remove(player);
	}

	public static Score getScoreboard() {
		return scoreboard;
	}
	public static void setScoreboard(Score score) {
		scoreboard = score;
	}

	public static void setScore(Player player, Score score) {
		scores.put(player, score);
		score.apply(player);
	}
	public static Score getScore(Player player) {
		return scores.get(player);

	}

	public static Tag getTag(Player player) {
		return tags.get(player);
	}

	public static void resetTag(Player player) {
		setTag(player, "");
	}

	public static void setTag(Player player, String prefix) {
		setTag(player, prefix, "");
	}

	public static void setTag(Player player, String prefix, String suffix) {
		setTag(player, new Tag(prefix, suffix));
	}

	public static void setTag(Player player, Tag tag) {
		tags.put(player, tag);

	}
	public static boolean isTagsEnabled() {
		return tagsEnabled;
	}
	public static void setTagsEnabled(boolean tagsEnabled) {
		Scores.tagsEnabled = tagsEnabled;
	}
	public static boolean isScoresEnabled() {
		return scoresEnabled;
	}
	public static void setScoresEnabled(boolean scoresEnabled) {
		Scores.scoresEnabled = scoresEnabled;
	}
	public static Map<Player, Tag> getTags() {
		return tags;
	}
	public static void setTags(Map<Player, Tag> tags) {
		Scores.tags = tags;
	}
	public static Map<Player, Score> getScores() {
		return scores;
	}
	public static void setScores(Map<Player, Score> scores) {
		Scores.scores = scores;
	}

}
