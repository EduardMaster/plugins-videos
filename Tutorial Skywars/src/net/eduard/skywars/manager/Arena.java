package net.eduard.skywars.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import net.eduard.skywars.util.ArenaSpace;

/**
 * É o Mapa
 * @author Eduard-PC
 *
 */
public class Arena {
	
	

	private String name;
	private int minPlayersAmount=2,maxPlayersAmount=12;
	private ArenaSpace map =  new ArenaSpace();
	private ArenaSpace feast =  new ArenaSpace();
	private double reward;
	private List<Location> spawns = new ArrayList<>();
	
	public Arena(String name){
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public List<Location> getSpawns() {
		return spawns;
	}
	public void setSpawns(List<Location> spawns) {
		this.spawns = spawns;
	}


	public ArenaSpace getMap() {
		return map;
	}


	public void setMap(ArenaSpace map) {
		this.map = map;
	}

	public void setName(String name) {
		this.name = name;
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


	public ArenaSpace getFeast() {
		return feast;
	}


	public void setFeast(ArenaSpace feast) {
		this.feast = feast;
	}


	public double getReward() {
		return reward;
	}


	public void setReward(double reward) {
		this.reward = reward;
	}
	
	
	
}
