package net.eduard.api.setup;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@SuppressWarnings("deprecation")
public class Scoreboards {
	private Scoreboard scoreboard;
	private Objective objective;

	public Scoreboards(String title) {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective("scoreboard", "dummy");
		objective.setDisplayName(title);
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		for (int id = 15; id > 0; id--) {
			Team team = scoreboard.registerNewTeam("team-" + id);
			teams.put(id, team);
		}
		for (int id = 15; id > 0; id--) {
			setLine("", "" + ChatColor.values()[id - 1], "", id);
		}
	}

	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	private Map<Integer, OfflinePlayer> fakes = new HashMap<>();
	private Map<Integer, Team> teams = new HashMap<>();
	public static String cutText(String text, int lenght) {
		return text.length() > lenght ? text.substring(0, lenght) : text;
	}
	public void setLine(String prefix, String center, String suffix, int line) {
		prefix = cutText(prefix, 16);
		center = cutText(center, 40);
		suffix = cutText(suffix, 16);
		Team team = teams.get(line);
		if (fakes.containsKey(line)) {
			OfflinePlayer fake = fakes.get(line);
			team.removePlayer(fake);
			objective.getScore(fake).setScore(-1);
//			Mine.delays(Mine.getEduard(), 1, () -> scoreboard.resetScores(fake));
		}
		FakeOfflinePlayer fake = new FakeOfflinePlayer(center);
		objective.getScore(fake).setScore(line);
		fakes.put(line, fake);
		team.addPlayer(fake);
		team.setSuffix(suffix);
		team.setPrefix(prefix);
		// clearScoreboard();
	
	}

	// public void clearScoreboard() {
	//// System.out.println(scoreboard.getPlayers().size());
	// for (OfflinePlayer fake : scoreboard.getPlayers()) {
	// Score score = objective.getScore(fake);
	// if (score.getScore() == -1000)
	// scoreboard.resetScores(fake);
	//
	// }
	//// System.out.println(scoreboard.getPlayers().size());
	// }
	public static class FakeOfflinePlayer implements OfflinePlayer {

		private String name;
		private UUID id;

		public void setName(String name) {
			this.name = name;
		}

		public FakeOfflinePlayer(String name) {
			this.name = name;
			// this.id = UUID.nameUUIDFromBytes(name.getBytes());
		}

		public FakeOfflinePlayer(String name, UUID id) {
			this(name);
			this.setId(id);
		}

		@Override
		public boolean isOp() {
			return false;
		}

		@Override
		public void setOp(boolean arg0) {

		}

		@Override
		public Map<String, Object> serialize() {
			return null;
		}

		@Override
		public Location getBedSpawnLocation() {
			return null;
		}

		@Override
		public long getFirstPlayed() {
			return 0;
		}

		@Override
		public long getLastPlayed() {
			return 0;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public Player getPlayer() {
			Player player = Bukkit.getPlayer(id);
			return player == null ? Bukkit.getPlayer(name) : player;
		}

		@Override
		public UUID getUniqueId() {
			return id;
		}

		@Override
		public boolean hasPlayedBefore() {
			return true;
		}

		@Override
		public boolean isBanned() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isOnline() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isWhitelisted() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setBanned(boolean arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setWhitelisted(boolean arg0) {
			// TODO Auto-generated method stub

		}

		public UUID getId() {
			return id;
		}

		public void setId(UUID id) {
			this.id = id;
		}

	}
}
