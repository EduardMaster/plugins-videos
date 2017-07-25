package net.eduard.api.minigame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import net.eduard.api.config.ConfigSection;

public class MinigameRoom {

	private int id;
	private int time;
	private Minigame minigame;
	private MinigameState state = MinigameState.STARTING;
	private World world = Bukkit.getWorlds().get(0);
	private MinigameAttributes attributes = new MinigameAttributes();
	private MinigameOptions options = new MinigameOptions();
	private List<Player> players = new ArrayList<>();
	private List<Player> spectators = new ArrayList<>();
	private List<Player> admins = new ArrayList<>();
	private List<Player> winners = new ArrayList<>();
	private MinigameMap map;
	
	public MinigameRoom(Minigame minigame,MinigameMap map) {
		this.minigame = minigame;
		this.id = minigame.getRooms().size()+1;
		this.minigame.getRooms().put(id, this);
		this.map = map;
	}
	public void sendToPlayers(Object...text){
		ConfigSection.sendMessage(players, text);
	}
	public void sendToSpecs(Object...text){
		ConfigSection.sendMessage(spectators, text);
	}
	public void sendToAdmins(Object...text){
		ConfigSection.sendMessage(admins, text);
	}
	public void broadcast(Object...text){
		sendToAdmins(text);
		sendToPlayers(text);
		sendToSpecs(text);
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

	public MinigameMap getMap() {
		return map;
	}

	public void setMap(MinigameMap map) {
		this.map = map;
	}

	public Minigame getMinigame() {
		return minigame;
	}

	public void setMinigame(Minigame minigame) {
		this.minigame = minigame;
	}

	public MinigameAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(MinigameAttributes attributes) {
		this.attributes = attributes;
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

	public List<Player> getAdmins() {
		return admins;
	}

	public void setAdmins(List<Player> admins) {
		this.admins = admins;
	}

	public MinigameOptions getOptions() {
		return options;
	}

	public void setOptions(MinigameOptions options) {
		this.options = options;
	}


	public World getWorld() {
		return world;
	}


	public void setWorld(World world) {
		this.world = world;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public List<Player> getWinners() {
		return winners;
	}
	public void setWinners(List<Player> winners) {
		this.winners = winners;
	}

}
