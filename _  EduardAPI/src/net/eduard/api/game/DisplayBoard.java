package net.eduard.api.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class DisplayBoard {

	public static final int PLAYER_ABOVE_1_7_NAME_LIMIT = 40;
	public static final int PLAYER_BELOW_1_8_NAME_LIMIT = 16;
	public static final int TITLE_LIMIT = 32;
	public static final int REGISTER_LIMIT = 16;
	public static final int PREFIX_LIMIT = 16;
	public static final int SUFFIX_LIMIT = 16;
	public int PLAYER_NAME_LIMIT = PLAYER_BELOW_1_8_NAME_LIMIT;
	protected Map<Integer, OfflinePlayer> players = new HashMap<>();
	protected Map<Integer, Team> teams = new HashMap<>();
	protected List<String> lines = new ArrayList<>();
	protected String title;
	protected Scoreboard score;
	protected Objective board;
	protected Objective health;

	public DisplayBoard() {
		title = "§6§lScoreboard";
		init();
		build();
	}

	public DisplayBoard copy() {
		DisplayBoard copy = new DisplayBoard();
		copy.lines.addAll(lines);
		copy.title = title;
		return copy;

	}
	protected void init() {
		score = Bukkit.getScoreboardManager().getNewScoreboard();
		for (int id = 1; id <= 15; id++) {
			teams.put(id, score.registerNewTeam("TeamTag-" + id));
		}
	}

	public DisplayBoard(String title, String... lines) {
		setTitle(title);
		getLines().addAll(Arrays.asList(lines));
		init();
		build();
		update();
	}

	public void apply(Player player) {
		player.setScoreboard(score);
		player.setHealth(player.getMaxHealth()-1);

	}
	public void empty(int slot) {
		set(slot, "");
	}
	public void clear(int slot) {
		int id = id(slot);
		remove(id);
	}
	public void setDisplay(String name) {
		board.setDisplayName(cut(name, TITLE_LIMIT));
	}
	protected void remove(int id) {
		if (players.containsKey(id)) {
			OfflinePlayer fake = players.get(id);
			score.resetScores(fake);
			teams.get(id).removePlayer(fake);
			players.remove(id);
		}
	}

	public boolean set(int slot, String text) {
		int id = id(slot);
		text = cut(text, PREFIX_LIMIT + SUFFIX_LIMIT + PLAYER_NAME_LIMIT);
		String center = "";
		String prefix = "";
		String suffix = "";
		if (text.length() > PLAYER_NAME_LIMIT + PREFIX_LIMIT + SUFFIX_LIMIT) {
			text = cut(text, PLAYER_NAME_LIMIT + PREFIX_LIMIT + SUFFIX_LIMIT);
		}
		if (text.length() <= PLAYER_NAME_LIMIT) {
			center = text;
		} else if (text.length() <= PLAYER_NAME_LIMIT + PREFIX_LIMIT) {
			prefix = text.substring(0, PREFIX_LIMIT);
			center = text.substring(PREFIX_LIMIT);
		} else if (text.length() <= PLAYER_NAME_LIMIT + PREFIX_LIMIT
				+ SUFFIX_LIMIT) {
			prefix = text.substring(0, PREFIX_LIMIT);
			center = text.substring(PREFIX_LIMIT - 1,
					PREFIX_LIMIT + PLAYER_NAME_LIMIT - 1);
			suffix = text.substring(PREFIX_LIMIT + PLAYER_NAME_LIMIT);
		}
		Team team = teams.get(id);
		if (players.containsKey(id)) {
			@SuppressWarnings("deprecation")
			String fake = players.get(id).getName();
			if (center.equals(fake) && prefix.equals(team.getPrefix())
					&& suffix.equals(team.getSuffix())) {
				return false;
			}
			remove(id);
		}
		OfflinePlayer fake = text(center, id);
		board.getScore(fake).setScore(id);
		team.setPrefix(prefix);
		team.setSuffix(suffix);
		team.addPlayer(fake);
		players.put(id, fake);
		return true;

	}
	protected OfflinePlayer text(String text, int id) {
		if (text.isEmpty()) {
			return new FakePlayer(ChatColor.values()[id].toString());
		}
		return new FakePlayer(cut(text, PLAYER_NAME_LIMIT));
	}
	protected int id(int slot) {
		return slot <= 0 ? 1 : slot > 15 ? 15 : slot;
	}
	public String getDisplay() {
		return board.getDisplayName();
	}

	public void build() {
		board = score.registerNewObjective(cut(title, REGISTER_LIMIT),
				"Displayboard");
		board.setDisplaySlot(DisplaySlot.SIDEBAR);
		setDisplay(title);
		health = score.registerNewObjective("HealthBar", Criterias.HEALTH);
		health.setDisplaySlot(DisplaySlot.BELOW_NAME);
		health.setDisplayName(" §aYOUR LITLE HEAD");
	}
	public String cut(String text, int size) {
		return text.length() > size ? text.substring(0, size) : text;
	}

	public void update() {
		int id = 15;
		for (String line : lines) {
			set(id, line);
			id--;
			if (id == 0)
				return;
		}
	}

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Scoreboard getScore() {
		return score;
	}

	public Objective getBoard() {
		return board;
	}

	public Objective getHealth() {
		return health;
	}

	protected static class FakePlayer implements OfflinePlayer {

		protected String text;

		public FakePlayer(String text) {
			this.text = text;
		}

		@Override
		public boolean isOp() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setOp(boolean arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public Map<String, Object> serialize() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Location getBedSpawnLocation() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getFirstPlayed() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getLastPlayed() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getName() {
			return text;
		}

		@Override
		public Player getPlayer() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public UUID getUniqueId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasPlayedBefore() {
			// TODO Auto-generated method stub
			return false;
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
		public void setBanned(boolean banned) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setWhitelisted(boolean value) {
			// TODO Auto-generated method stub

		}

	}

	

	
}
