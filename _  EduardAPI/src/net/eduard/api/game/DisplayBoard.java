package net.eduard.api.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.eduard.api.setup.ExtraAPI;
import net.eduard.api.setup.StorageAPI.Copyable;
import net.eduard.api.setup.StorageAPI.FakeOfflinePlayer;
import net.eduard.api.setup.StorageAPI.Storable;

public class DisplayBoard implements Storable, Copyable {

	public static final int PLAYER_ABOVE_1_7_NAME_LIMIT = 40;
	public static final int PLAYER_BELOW_1_8_NAME_LIMIT = 16;
	public static final int TITLE_LIMIT = 32;
	public static final int REGISTER_LIMIT = 16;
	public static final int PREFIX_LIMIT = 16;
	public static final int SUFFIX_LIMIT = 16;
	public int PLAYER_NAME_LIMIT = PLAYER_BELOW_1_8_NAME_LIMIT;
	protected transient Map<Integer, OfflinePlayer> players = new HashMap<>();
	protected transient Map<Integer, Team> teams = new HashMap<>();
	protected List<String> lines = new ArrayList<>();
	protected String title;
	protected transient Scoreboard score;
	protected transient Objective board;
	protected transient Objective health;

	public DisplayBoard hide() {

		board.setDisplaySlot(null);
		return this;
	}
	public boolean isShowing() {
		return board.getDisplaySlot() == DisplaySlot.SIDEBAR;
	}
	public DisplayBoard show() {
		board.setDisplaySlot(DisplaySlot.SIDEBAR);
		return this;
	}
	public DisplayBoard copy() {
		return copy(this);
	}

	public DisplayBoard() {
		title = "§6§lScoreboard";
		init();
		build();
	}
	public DisplayBoard(String title, String... lines) {
		setTitle(title);
		getLines().addAll(Arrays.asList(lines));
		init();
		build();
		update();
	}
	public List<String> getDisplayLines() {
		List<String>list = new ArrayList<>();
		for (Entry<Integer, OfflinePlayer> entry : players.entrySet()) {
			Integer id = entry.getKey();
			Team team = teams.get(id);
			list.add(team.getPrefix()+entry.getValue().getName()+team.getSuffix());
		}
		return list;
	}
	public DisplayBoard update(Player player) {
		int id = 15;
		for (String line : this.lines) {
			set(id,ExtraAPI.getReplacers(line, player));
			id--;
		}
		setDisplay(ExtraAPI.getReplacers(title, player));
		return this;
	}
	public DisplayBoard update() {
		setDisplay(title);
		int id = 15;
		for (String line : lines) {
			set(id, line);
			id--;
		}
		return this;
	}
	public DisplayBoard init() {
		score = Bukkit.getScoreboardManager().getNewScoreboard();
		for (int id = 1; id <= 15; id++) {
			teams.put(id, score.registerNewTeam("TeamTag-" + id));
		}
		return this;
	}



	public DisplayBoard apply(Player player) {
		player.setScoreboard(score);
		return this;
	}
	public DisplayBoard updateHealthBar(Player player) {
		player.setHealth(player.getMaxHealth() - 1);
		return this;
	}
	public void empty(int slot) {
		set(slot, "");
	}
	public void clear(int slot) {
		int id = id(slot);
		remove(id);
	}
	public DisplayBoard setDisplay(String name) {
		board.setDisplayName(cut(name, TITLE_LIMIT));
		return this;
	}
	@SuppressWarnings("deprecation")
	public boolean remove(int id) {
		if (players.containsKey(id)) {
			OfflinePlayer fake = players.get(id);
			score.resetScores(fake);
			teams.get(id).removePlayer(fake);
			players.remove(id);
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
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
			return new FakeOfflinePlayer(ChatColor.values()[id].toString());
		}
		return new FakeOfflinePlayer(ChatColor.translateAlternateColorCodes('&',
				cut(text, PLAYER_NAME_LIMIT)));
	}
	protected int id(int slot) {
		return slot <= 0 ? 1 : slot > 15 ? 15 : slot;
	}
	public String getDisplay() {
		return board.getDisplayName();
	}

	public DisplayBoard build() {
		board = score.registerNewObjective(cut(title, REGISTER_LIMIT),
				"Displayboard");
		board.setDisplaySlot(DisplaySlot.SIDEBAR);
		setDisplay(title);
		health = score.registerNewObjective("HealthBar", Criterias.HEALTH);
		health.setDisplaySlot(DisplaySlot.BELOW_NAME);
		health.setDisplayName(" §aYOUR LITLE HEAD");
		return this;
	}
	public String cut(String text, int size) {
		return text.length() > size ? text.substring(0, size) : text;
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
	@Override
	public Object restore(Map<String, Object> map) {
		update();
		return null;
	}
	@Override
	public void onCopy() {
		init().build();
		
	}
	@Override
	public void store(Map<String, Object> map, Object object) {
		// TODO Auto-generated method stub

	}

}
