package net.eduard.api.minigame;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;

public class MinigameMap {
	
	private String name;
	private World world;
	private int minPlayersAmount=2;
	private int maxPlayersAmount=20;
	private int neededPlayersAmount=16;
	private Location lobby;
	private Location spawn;
	private Arena feast;
	private List<Arena> minifeasts= new ArrayList<>();
	private Location feastPosition;
	private Location highPosition;
	private Location lowPosition;
	private Arena map;
	private List<Location> spawns = new ArrayList<>();
	
	public MinigameMap(String name) {
		super();
		this.name = name;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
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

	public int getNeededPlayersAmount() {
		return neededPlayersAmount;
	}

	public void setNeededPlayersAmount(int neededPlayersAmount) {
		this.neededPlayersAmount = neededPlayersAmount;
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Arena getFeast() {
		return feast;
	}

	public void setFeast(Arena feast) {
		this.feast = feast;
	}

	public List<Arena> getMinifeasts() {
		return minifeasts;
	}

	public void setMinifeasts(List<Arena> minifeasts) {
		this.minifeasts = minifeasts;
	}

	public Location getFeastPosition() {
		return feastPosition;
	}

	public void setFeastPosition(Location feastPosition) {
		this.feastPosition = feastPosition;
	}

	public Location getHighPosition() {
		return highPosition;
	}

	public void setHighPosition(Location highPosition) {
		this.highPosition = highPosition;
	}

	public Location getLowPosition() {
		return lowPosition;
	}

	public void setLowPosition(Location lowPosition) {
		this.lowPosition = lowPosition;
	}

	public Arena getMap() {
		return map;
	}

	public void setMap(Arena map) {
		this.map = map;
	}

	public List<Location> getSpawns() {
		return spawns;
	}

	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
