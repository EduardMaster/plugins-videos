package net.eduard.api.setup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criterias;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.eduard.api.API;
import net.eduard.api.setup.StorageAPI.Copyable;
import net.eduard.api.setup.StorageAPI.Storable;
public final class ScoreAPI {
	
	public static class ScoreListener implements Listener {
		@EventHandler
		public void onQuit(PlayerQuitEvent e) {
			Player p = e.getPlayer();

			removeScore(p);
			removeTag(p);
		}
		@EventHandler
		public void onKick(PlayerKickEvent e) {
			Player p = e.getPlayer();
			removeScore(p);
			removeTag(p);
		}

		@EventHandler
		public void onJoin(PlayerJoinEvent e) {
			Player p = e.getPlayer();
			if (scoresEnabled) {
				setScore(e.getPlayer(), scoreDefault.copy());
			}
			if (tagsEnabled) {
				updateTagByRank(p);
			}

		}

	}

	public static void enable(Plugin plugin) {
		disable();
		updater = new BukkitRunnable() {

			@Override
			public void run() {
				updateTagsScores();
			}
		};
		updater.runTaskTimer(plugin, 20, 20);
		Bukkit.getPluginManager().registerEvents(listener, plugin);
	}
	public static void disable() {
		if (updater != null) {
			updater.cancel();
			updater = null;
		}
		HandlerList.unregisterAll(listener);
	}
	private static boolean tagsEnabled;
	private static boolean scoresEnabled;
	private static Tag tagDefault;
	private static DisplayBoard scoreDefault;
	private static List<String> tagsGroups = new ArrayList<>();
	private static Map<Player, Tag> playersTags = new HashMap<>();
	private static Map<Player, DisplayBoard> playersScores = new HashMap<>();
	private static BukkitRunnable updater;
	private static ScoreListener listener = new ScoreListener();
	
	@SuppressWarnings("deprecation")
	public static void updateTags(Scoreboard score) {
		for (Entry<Player, Tag> map : playersTags.entrySet()) {
			Tag tag = map.getValue();
			Player player = map.getKey();
			if (player == null)
				continue;
			String name = ExtraAPI.getText(tag.getRank() + player.getName());
			Team team = score.getTeam(name);
			if (team == null)
				team = score.registerNewTeam(name);
			TagUpdateEvent event = new TagUpdateEvent(tag, player);
			API.callEvent(event);
			if (!event.isCancelled())
				continue;
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
		int id = 0;
		for (String rank : tagsGroups) {
			if (rank.equalsIgnoreCase(group)) {
				Tag tag = new Tag(prefix, suffix);
				tag.setName(player.getName());
				tag.setRank(id);
				setTag(player, tag);
				break;
			}
			id++;
		}

	}
	public static void setScore(Player player, DisplayBoard score) {
		playersScores.put(player, score);
		score.apply(player);
	}
	public static DisplayBoard getScore(Player player) {
		return playersScores.get(player);

	}

	public static Tag getTag(Player player) {
		return playersTags.get(player);
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
		playersTags.put(player, tag);

	}

	public static void updateTagsScores() {
		if (scoresEnabled) {

			try {
				for (Entry<Player, DisplayBoard> map : playersScores.entrySet()) {
					DisplayBoard score = map.getValue();
					Player player = map.getKey();
					ScoreUpdateEvent event = new ScoreUpdateEvent(player,
							score);
					if (!event.isCancelled()) {
						score.update(player);
					}
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
	public static void removeScore(Player player) {
		player.setScoreboard(ExtraAPI.getMainScoreboard());
		playersTags.remove(player);
	}
	public static void removeTag(Player player) {
		playersTags.remove(player);
	}

	public static class TagUpdateEvent extends PlayerEvent
			implements
				Cancellable {

		private Tag tag;
		private boolean cancelled;

		public boolean isCancelled() {
			return cancelled;
		}
		public void setCancelled(boolean cancelled) {
			this.cancelled = cancelled;
		}
		@Override
		public HandlerList getHandlers() {
			return handlers;
		}
		public static HandlerList getHandlerList() {
			return handlers;
		}
		private static final HandlerList handlers = new HandlerList();

		public TagUpdateEvent(Tag tag, Player who) {
			super(who);
			setTag(tag);
		}
		public Tag getTag() {
			return tag;
		}
		public void setTag(Tag tag) {
			this.tag = tag;
		}
	}
	public static class Tag implements Storable {

		private String prefix, suffix, name;

		private int rank;

		public Tag(String prefix, String suffix) {
			this.prefix = prefix;
			this.suffix = suffix;
		}
public Tag() {
	// TODO Auto-generated constructor stub
}
		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;

		}

		public String getSuffix() {
			return suffix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		@Override
		public Object restore(Map<String, Object> map) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void store(Map<String, Object> map, Object object) {
			// TODO Auto-generated method stub

		}

	}
	public static class ScoreUpdateEvent extends PlayerEvent
			implements
				Cancellable {
		private DisplayBoard score;
		private boolean cancelled;

		@Override
		public HandlerList getHandlers() {
			return handlers;
		}
		public static HandlerList getHandlerList() {
			return handlers;
		}
		private static final HandlerList handlers = new HandlerList();

		public ScoreUpdateEvent(Player who, DisplayBoard score) {
			super(who);
			setScore(score);
		}
		public DisplayBoard getScore() {
			return score;
		}
		public void setScore(DisplayBoard score) {
			this.score = score;
		}
		public boolean isCancelled() {
			return cancelled;
		}
		public void setCancelled(boolean cancelled) {
			this.cancelled = cancelled;
		}

	}

	@SuppressWarnings("deprecation")
	public static Scoreboard applyScoreboard(Player player, String title,
			String... lines) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("score", "dummy");
		obj.setDisplayName(title);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		int id = 15;
		for (String line : lines) {
			String empty = ChatColor.values()[id - 1].toString();
			obj.getScore(new FakeOfflinePlayer(line.isEmpty() ? empty : line))
					.setScore(id);;
			id--;
			if (id == 0) {
				break;
			}
		}

		player.setScoreboard(board);
		return board;
	}
	public static Scoreboard newScoreboard(Player player, String title,
			String... lines) {
		return applyScoreboard(player, title, lines);
	}
	/**
	 * Jogador Off Ficticio
	 * 
	 * @author Eduard-PC
	 *
	 */
	public static class FakeOfflinePlayer implements OfflinePlayer {

		private String name;
		private UUID id;

		public FakeOfflinePlayer(String name) {
			this.name = name;
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

	public static class DisplayBoard implements Storable, Copyable {

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
			List<String> list = new ArrayList<>();
			for (Entry<Integer, OfflinePlayer> entry : players.entrySet()) {
				Integer id = entry.getKey();
				Team team = teams.get(id);
				list.add(team.getPrefix() + entry.getValue().getName()
						+ team.getSuffix());
			}
			return list;
		}
		public DisplayBoard update(Player player) {
			int id = 15;
			for (String line : this.lines) {
				set(id, ExtraAPI.getReplacers(line, player));
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
			if (text.length() > PLAYER_NAME_LIMIT + PREFIX_LIMIT
					+ SUFFIX_LIMIT) {
				text = cut(text,
						PLAYER_NAME_LIMIT + PREFIX_LIMIT + SUFFIX_LIMIT);
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
			return new FakeOfflinePlayer(ChatColor.translateAlternateColorCodes(
					'&', cut(text, PLAYER_NAME_LIMIT)));
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

	public static boolean isTagsEnabled() {
		return tagsEnabled;
	}
	public static void setTagsEnabled(boolean tagsEnabled) {
		ScoreAPI.tagsEnabled = tagsEnabled;
	}
	public static boolean isScoresEnabled() {
		return scoresEnabled;
	}
	public static void setScoresEnabled(boolean scoresEnabled) {
		ScoreAPI.scoresEnabled = scoresEnabled;
	}
	public static Tag getTagDefault() {
		return tagDefault;
	}
	public static void setTagDefault(Tag tagDefault) {
		ScoreAPI.tagDefault = tagDefault;
	}
	public static DisplayBoard getScoreDefault() {
		return scoreDefault;
	}
	public static void setScoreDefault(DisplayBoard scoreDefault) {
		ScoreAPI.scoreDefault = scoreDefault;
	}
	public static List<String> getTagsGroups() {
		return tagsGroups;
	}
	public static void setTagsGroups(List<String> tagsGroups) {
		ScoreAPI.tagsGroups = tagsGroups;
	}
	public static Map<Player, Tag> getPlayersTags() {
		return playersTags;
	}
	public static void setPlayersTags(Map<Player, Tag> playersTags) {
		ScoreAPI.playersTags = playersTags;
	}
	public static Map<Player, DisplayBoard> getPlayersScores() {
		return playersScores;
	}
	public static void setPlayersScores(Map<Player, DisplayBoard> playersScores) {
		ScoreAPI.playersScores = playersScores;
	}
}
