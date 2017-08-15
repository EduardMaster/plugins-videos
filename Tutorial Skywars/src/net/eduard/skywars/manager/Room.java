package net.eduard.skywars.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import net.eduard.skywars.Main;

/**
 * É Sala
 * 
 * @author Eduard-PC
 *
 */
public class Room implements Runnable{

	private int id;
	private int time;
	private BukkitTask task;
	private RoomState state;
	private Arena map;
	private List<Player> alives = new ArrayList<>();
	private List<Player> deaths = new ArrayList<>();
	private Vote vote = new Vote();
	
	public Room(Arena map, int id) {
		this.map = map;
		this.id = id;
	}

	public void init(){
		if (task!=null){
			task.cancel();
		}
		Main.deleteWorld("sw-"+id);
		task = Bukkit.getScheduler().runTaskTimer(Main.plugin	,this, 20, 20);
	}

	@Override
	public void run() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public BukkitTask getTask() {
		return task;
	}

	public void setTask(BukkitTask task) {
		this.task = task;
	}

	public Arena getMap() {
		return map;
	}

	public void setMap(Arena map) {
		this.map = map;
	}

	public List<Player> getAlives() {
		return alives;
	}

	public void setAlives(List<Player> alives) {
		this.alives = alives;
	}

	public List<Player> getDeaths() {
		return deaths;
	}

	public void setDeaths(List<Player> deaths) {
		this.deaths = deaths;
	}

	public Vote getVote() {
		return vote;
	}

	public void setVote(Vote vote) {
		this.vote = vote;
	}

	public RoomState getState() {
		return state;
	}

	public void setState(RoomState state) {
		this.state = state;
	}

}
