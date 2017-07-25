package net.eduard.parkour;

import org.bukkit.entity.Player;

import net.eduard.api.config.CS;
import net.eduard.api.util.Save;

public class Stats implements Save{
	private int loses;
	private String name;
	private int falls;
	private int fall;
	private int wins;
	private int checkpoints;
	private double cash;
	public Stats(Player player) {
		name = player.getName();
	}
	public Stats() {
		
	}
	public int getFalls() {
		return falls;
	}
	public void setFalls(int falls) {
		this.falls = falls;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getCheckpoints() {
		return checkpoints;
	}
	public void setCheckpoints(int checkpoints) {
		this.checkpoints = checkpoints;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public void save(CS section, Object value) {
		Stats stat = (Stats)value;
		section.set("loses",stat.loses);
		section.set("name",stat.name);
		section.set("falls",stat.falls);
		section.set("wins",stat.wins);
		section.set("checkpoints",stat.checkpoints);
		section.set("cash",stat.cash);
	}
	public Object get(CS section) {
		Stats stat = new Stats();
		stat.setCash(section.getDouble("cash"));
		stat.setWins(section.getInt("wins"));
		stat.setFalls(section.getInt("falls"));
		stat.setCheckpoints(section.getInt("checkpoints"));
		stat.setLoses(section.getInt("loses"));
		stat.setName(section.getString("name"));
		return stat;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFall() {
		return fall;
	}
	public void setFall(int fall) {
		this.fall = fall;
	}
	public int getLoses() {
		return loses;
	}
	public void setLoses(int loses) {
		this.loses = loses;
	}
	
}
