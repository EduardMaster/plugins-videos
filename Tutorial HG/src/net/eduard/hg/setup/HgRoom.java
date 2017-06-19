package net.eduard.hg.setup;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class HgRoom {
	
	private int id;
	private int time=60*2;
	private HgState state=HgState.PRE_GAME;
	private List<Player> players = new ArrayList<>();
	private List<Player> spectators = new ArrayList<>();
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public List<Player> getSpectators() {
		return spectators;
	}

	public void setSpectators(List<Player> spectators) {
		this.spectators = spectators;
	}

	public HgState getState() {
		return state;
	}

	public void setState(HgState state) {
		this.state = state;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
}
