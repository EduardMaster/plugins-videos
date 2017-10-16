package net.eduard.api.minigame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

/**
 * Sala do Minigame
 * 
 * @author Eduard-PC
 *
 */
public class Game {

	private int id;
	private int time;
	private Minigame minigame;
	private boolean enabled;
	private GameMap map;
	private MinigameState state = MinigameState.STARTING;
	private List<GamePlayer> players = new ArrayList<>();
	private List<GameTeam> teams = new ArrayList<>();
	public void broadcast(String message) {
		for (GamePlayer player : players) {
			player.send(message);
		}
	} 
	public boolean isPlaying(Player player) {
		for (GamePlayer p : players) {
			if (p.getPlayer().equals(player)) {
				return true;
			}

		}
		return false;
	}

	public List<GamePlayer> getPlayers() {
		return players;
	}
	public List<Player> getPlaying() {
		List<Player> list = new ArrayList<>();
		for (GamePlayer player : players) {
			list.add(player.getPlayer());
		}
		return list;
	}

	public void setPlayers(List<GamePlayer> players) {
		this.players = players;
	}

	public List<Player> getPlayers(GamePlayerState state) {
		List<Player> list = new ArrayList<>();
		for (GamePlayer player : players) {
			if (player.getState() == state)
				list.add(player.getPlayer());
		}
		return list;
	}

	public Game(Minigame minigame, GameMap map) {
		this.minigame = minigame;
		this.id = minigame.getRooms().size() + 1;
		this.minigame.getRooms().put(id, this);
		this.map = map;
		this.enabled = true;
		this.time = map.getTimeIntoStart();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MinigameState getState() {
		return state;
	}

	public void setState(MinigameState state) {
		this.state = state;
	}

	public GameMap getMap() {
		return map;
	}

	public void setMap(GameMap map) {
		this.map = map;
	}

	public Minigame getMinigame() {
		return minigame;
	}

	public void setMinigame(Minigame minigame) {
		this.minigame = minigame;
	}

	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<GameTeam> getTeams() {
		return teams;
	}
	public void setTeams(List<GameTeam> teams) {
		this.teams = teams;
	}

}