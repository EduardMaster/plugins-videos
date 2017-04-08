package net.eduard.template;

import java.util.ArrayList;

import org.bukkit.entity.Player;

/**
 * É Sala
 * 
 * @author Eduard-PC
 *
 */
public class Room {

	private int id;
	private Arena arena;
	private ArrayList<Player> alives = new ArrayList<>();
	private ArrayList<Player> deaths = new ArrayList<>();
	private Vote vote = new Vote();

}
