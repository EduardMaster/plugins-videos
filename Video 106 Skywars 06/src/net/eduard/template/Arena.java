package net.eduard.template;

import java.util.ArrayList;

import org.bukkit.Location;

/**
 * É o Mapa
 * @author Eduard-PC
 *
 */
public class Arena {
	
	public Arena(String name){
		this.name = name;
	}
	
	
	private String name;
	private int minPlayersAmount=2,maxPlayersAmount=12;
	private ArenaSpace map =  new ArenaSpace();
	private ArenaSpace feast =  new ArenaSpace();
	private ArrayList<ArenaSpace> miniFeasts = new ArrayList<>();
	private double reward;
	private ArrayList<Location> spawns = new ArrayList<>();
}
