package net.eduard.api.minigame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class GameTeam {

	private Game game;
	private List<GamePlayer> players = new ArrayList<>();
	public int getSize() {
		return players.size();
	}
	public boolean isEmpty() {
		return players.isEmpty();
	}
	public Game getGame() {
		return game;
	}
	public void join(GamePlayer player) {
		players.add(player);
		player.setTeam(this);
	}
	public void leave(GamePlayer player) {
		player.setTeam(null);
		players.remove(player);
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<GamePlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<GamePlayer> players) {
		this.players = players;
	}
	public void send(String message) {
		for (GamePlayer p : players) {
			p.send(message);
		}
	}

	public List<Player> getPlayers(GamePlayerState state) {
		List<Player> list = new ArrayList<>();
		for (GamePlayer player : players) {
			if (player.getState() == state)
				list.add(player.getPlayer());
		}
		return list;
	}

}
