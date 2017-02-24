package net.eduard.template;

import java.util.ArrayList;

import org.bukkit.Location;

/**
 * É o Mapa
 * @author Eduard-PC
 *
 */
public class Arena {
	private String name;
	private int minPlayersAmount=2,maxPlayersAmount=12;
	private double reward;
	private ArrayList<Location> spawns = new ArrayList<>();
}
