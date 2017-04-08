package net.eduard.api.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.manager.TimeAPI;
import net.eduard.api.util.Extras;

public class Score extends TimeAPI implements Cloneable {

	public void run() {
		String newTitle = ChatColor.stripColor(name + getEmpty(100));
		// vamo supor que size = 30
		int size = 32 - scrollPrefix.length() - scrollSuffix.length();
		if (newTitle.length() <= size) {
			currentChar = 0;
			setTitle(name);
		} else {
			if (currentChar >= newTitle.length()) {
				currentChar = 0;
			}

			String scrolled = "";
			String moved = newTitle.substring(currentChar);
			if (moved.length() > size) {
				scrolled = moved.substring(0, size);
			} else {
				int rest = size - moved.length();
				scrolled = moved.substring(0, moved.length());
				scrolled += newTitle.substring(0, rest);
			}
			setTitle(scrollPrefix + scrolled + scrollSuffix);
			currentChar++;
		}
	}

	private String scrollPrefix = " §6§l";
	private String scrollSuffix = " ";
	private boolean scroll;
	private boolean withHealthBar = true;
	private String name;
	private List<String> lines = new ArrayList<>();
	private transient int currentChar;
	private transient Scoreboard scoreboard;
	private transient Objective objective;
	private transient Objective health;
	private transient Map<Integer, OfflinePlayer> slots = new HashMap<>();
	private transient Map<Integer, Team> teams = new HashMap<>();

	public Score(String name) {
		this.name = name;
		setTime(2L);
	}
	public Score build() {
		scoreboard = API.newScoreboard();
		objective = getObjective("display");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		if (withHealthBar) {
			health = scoreboard.registerNewObjective("healthDisplay", "health");
			health.setDisplaySlot(DisplaySlot.BELOW_NAME);
			health.setDisplayName("" + Extras.getRedHeart());
		}
		setName(name);
		for (int i = 1; i < 16; i++) {
			slots.put(i, getDisplay("SLOT-" + i));
		}
		for (int i = 1; i < 16; i++) {
			teams.put(i, getTeam("TEAM-" + i));
		}
		setEmpty(1);

		return this;
	}
	public Score scroll(Plugin plugin) {
		if (scroll) {
			timer(plugin);
		}
		return this;
	}

	public Score() {
		this("Scoreboard");
	}
	public void setScoreboard(Player p) {
		p.setScoreboard(scoreboard);
	}
	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public void setName(String name) {
		this.name = name;
		currentChar = 0;
		setTitle(name);
	}

	public void setTitle(String title) {
		objective.setDisplayName(API.toText(32, title));
	}

	public Objective getObjective(String name) {
		name = API.toText(16, name);
		if (scoreboard.getObjective(name) == null) {
			scoreboard.registerNewObjective(name, name);
		}
		return scoreboard.getObjective(name);
	}

	public Team getTeam(String name) {

		name = API.toText(16, name);
		if (scoreboard.getTeam(name) == null) {
			scoreboard.registerNewTeam(name);
		}
		return scoreboard.getTeam(name);
	}

	private int getId(int id) {

		if (id < 0) {
			id = 1;
		}
		if (id > 15) {
			id = 15;
		}
		return id;
	}

	public void set(int slot, String display) {
		slot = getId(slot);
		teams.get(slot).removePlayer(slots.get(slot));
		scoreboard.resetScores(slots.get(slot));
		display = API.toText(16 * 3, display);
		OfflinePlayer text = null;
		if (display.length() > 16 * 2) {
			text = getDisplay(display.substring(16, 16 * 2));
			Team team = teams.get(slot);
			team.addPlayer(text);
			team.setPrefix(display.substring(0, 16));
			team.setSuffix(display.substring(16 * 2));

		} else if (display.length() > 16) {
			String pre = display.substring(0, 16);
			String str = display.substring(16);
			if (str.isEmpty()) {
				str = "" + ChatColor.getLastColors(pre);
			}
			text = getDisplay(str);
			Team team = teams.get(slot);
			team.addPlayer(text);
			team.setPrefix(pre);
		} else {
			text = getDisplay(display);
		}
		slots.put(slot, text);
		objective.getScore(text).setScore(slot);
	}

	private String getEmpty(int size) {

		StringBuilder empty = new StringBuilder();
		for (int i = 0; i < size; i++) {
			empty.append(" ");
		}
		return empty.toString();
	}

	private OfflinePlayer getDisplay(String display) {
		@SuppressWarnings("deprecation")
		OfflinePlayer text = Bukkit.getOfflinePlayer(display);
		return text;
	}

	public void setEmpty(int slot) {
		slot = getId(slot);
		set(slot, "" + ChatColor.values()[slot - 1] + getEmpty(14));
	}

	public void remove(int slot) {
		slot = getId(slot);
		scoreboard.resetScores(slots.get(slot));
	}

	public void update(Player player) {
		int id = 15;
		for (String line : lines) {
			if (player != null) {
				line = API.getText(line, player);
			}
			if (line.isEmpty()) {
				setEmpty(id);
			} else {
				set(id, line);
			}

			id--;
			if (id == 0)
				break;
		}
	}

	public void use(Player p) {
		p.setScoreboard(scoreboard);;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	public String getScrollPrefix() {
		return scrollPrefix;
	}

	public void setScrollPrefix(String scrollPrefix) {
		this.scrollPrefix = scrollPrefix;
	}

	public String getScrollSuffix() {
		return scrollSuffix;
	}

	public void setScrollSuffix(String scrollSuffix) {
		this.scrollSuffix = scrollSuffix;
	}

	public int getCurrentChar() {
		return currentChar;
	}

	public void setCurrentChar(int currentChar) {
		this.currentChar = currentChar;
	}

	public Objective getObjective() {
		return objective;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	public String getName() {
		return name;
	}

	public List<String> getLines() {
		return lines;
	}

	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}

	public boolean isScroll() {
		return scroll;
	}

	public void setScroll(boolean scroll) {
		this.scroll = scroll;
	}

	public boolean isWithHealthBar() {
		return withHealthBar;
	}

	public void setWithHealthBar(boolean withHealthBar) {
		this.withHealthBar = withHealthBar;
	}
	public Score clone() {

		try {
			return (Score) super.clone();
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public void save(Section section, Object value) {

	}
	public Object get(Section section) {
		lines = section.getStringList("lines");
		return null;
	}

}
