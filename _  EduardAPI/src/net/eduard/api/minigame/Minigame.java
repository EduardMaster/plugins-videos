package net.eduard.api.minigame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.eduard.api.setup.Mine;
import net.eduard.api.setup.Mine.TimeManager;

public abstract class Minigame extends TimeManager {

	private String name;
	private Location lobby;
	private boolean enabled = true;
	private boolean unique = true;
	private boolean bungeecord = true;
	private String bungeeLobby = "Lobby";
	private Map<String, GameMap> maps = new HashMap<>();
	private transient Map<Integer, Game> rooms = new HashMap<>();
	private transient Map<Player, GamePlayer> players = new HashMap<>();
	public Minigame() {

	}
	public void remove(Player player) {
		players.remove(player);
	}public void removeMap(String name) {
		this.maps.remove(name.toLowerCase());
	}
	public void removeGame(int id) {
		this.rooms.remove(id);
	}
	public void removeGame(Game game) {
		this.rooms.remove(game);
	}
	public boolean existsMap(String name) {
		return this.maps.containsKey(name.toLowerCase());
	}
	public Game getGame(String name) {
		for (Game room : rooms.values()) {
			if (room.getMap().getName().equalsIgnoreCase(name)) {
				return room;
			}
		}
		return null;
	}
	public GameMap getMap(String name) {
		return maps.get(name.toLowerCase());
	}
	public void addMap(String name, GameMap map) {
		maps.put(name.toLowerCase(), map);
	}
	public void joinPlayer(GameTeam team,Player player) {
		GamePlayer p = getPlayer(player);
		p.join(team);
	}
	public void joinPlayer(Game game, Player player) {
		GamePlayer p = getPlayer(player);
		p.join(game);
	

	}
	public void leavePlayer(Player player) {
		GamePlayer p = getPlayer(player);
		if (p.isPlaying()) {
			p.getGame().getPlayers().remove(p);
			p.setGame(null);
		}
	
		if (p.hasTeam()) {
			p.getTeam().leave(p);
			p.setTeam(null);
		}

	}

	
	public void run() {
		if (!enabled)
			return;
		for (Game room : rooms.values()) {
			if (!room.isEnabled())
				continue;
			event(room);
		}
	}
	public Game getGame(Player player) {
		return getPlayer(player).getGame();
	}
	public List<Player> getPlaying() {
		List<Player> list = new ArrayList<>();
		for (Player player : players.keySet()) {
			list.add(player);
		}
		return list;
	}
	public Map<Player, GamePlayer> getPlayers() {
		return players;
	}
	public void setPlayers(Map<Player, GamePlayer> players) {
		this.players = players;
	}
	public Game getGame() {
		return getRooms().get(1);
	}
	public GameMap getMap() {
		return getMaps().get(getName().toLowerCase());
	}
	public Minigame(String name) {
		setName(name);
		new Game(this, new GameMap(this, getName()));
	}

	public Minigame(String name, Plugin plugin) {
		this(name);
		setPlugin(plugin);
	}
	public GamePlayer getPlayer(Player player) {
		GamePlayer gamePlayer = players.get(player);
		if (gamePlayer == null) {
			gamePlayer = new GamePlayer(player);
			players.put(player, gamePlayer);	
		}
		
		return gamePlayer;
	}

	public abstract void event(Game room);
	public void broadcast(String message) {
		for (Player player : players.keySet()) {
			player.sendMessage(Mine.getReplacers(message, player));
		}
	}
	public boolean isPlaying(Player player) {
		return players.containsKey(player);
	}
	public boolean hasMap(String name) {
		return maps.containsKey(name.toLowerCase());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, GameMap> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, GameMap> maps) {
		this.maps = maps;
	}

	public Map<Integer, Game> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Integer, Game> rooms) {
		this.rooms = rooms;
	}

	public Object restore(Map<String, Object> map) {
		new Game(this, getMap());
		return null;
	}

	public boolean hasLobby() {
		return lobby != null;
	}
	public Location getLobby() {
		return lobby;
	}
	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isBungeecord() {
		return bungeecord;
	}
	public void setBungeecord(boolean bungeecord) {
		this.bungeecord = bungeecord;
	}
	public boolean isUnique() {
		return unique;
	}
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
	public String getBungeeLobby() {
		return bungeeLobby;
	}
	public void setBungeeLobby(String bungeeLobby) {
		this.bungeeLobby = bungeeLobby;
	}

}