package net.eduard.hg.setup;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class HgManager {

	private HashMap<String, HgRoom> mapas = new HashMap<>();
	private HashMap<Integer, HgRoom> rooms = new HashMap<>();
	private HashMap<Player, HgRoom> players = new HashMap<>();
	
	private int timeToStart=60*2;
	private int timeToGameOver=30*60;
	private int timeInvunerable=60*2;
	private int timeToRestart=20;
	private int minPlayersAmount=2;
	private int maxPlayersAmount=30;
	

	public void reloadMapas(){
	
	}
	public void reloadMensagens(){
		
	}
	public void salvarMapas(){
	}
	
	public int getMinPlayersAmount() {
		return minPlayersAmount;
	}

	public void setMinPlayersAmount(int minPlayersAmount) {
		this.minPlayersAmount = minPlayersAmount;
	}

	public int getMaxPlayersAmount() {
		return maxPlayersAmount;
	}

	public void setMaxPlayersAmount(int maxPlayersAmount) {
		this.maxPlayersAmount = maxPlayersAmount;
	}

	public int getTimeToStart() {
		return timeToStart;
	}

	public void setTimeToStart(int timeToStart) {
		this.timeToStart = timeToStart;
	}

	public int getTimeToGameOver() {
		return timeToGameOver;
	}

	public void setTimeToGameOver(int timeToGameOver) {
		this.timeToGameOver = timeToGameOver;
	}

	public int getTimeToRestart() {
		return timeToRestart;
	}

	public void setTimeToRestart(int timeToRestart) {
		this.timeToRestart = timeToRestart;
	}

	public HashMap<Integer, HgRoom> getRooms() {
		return rooms;
	}

	public void setRooms(HashMap<Integer, HgRoom> rooms) {
		this.rooms = rooms;
	}

	public int getTimeInvunerable() {
		return timeInvunerable;
	}

	public void setTimeInvunerable(int timeInvunerable) {
		this.timeInvunerable = timeInvunerable;
	}

	public HashMap<Player, HgRoom> getPlayers() {
		return players;
	}

	public void setPlayers(HashMap<Player, HgRoom> players) {
		this.players = players;
	}
}
