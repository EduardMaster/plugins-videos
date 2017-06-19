package com.hcp.utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

public class Score {

	public String uuid;
	public String displayName;
	public List<String> lines;
	public Objective obj;
	
	public Score(String uuid, String displayName, List<String> lines){
		this.uuid = uuid;
		this.displayName = displayName;
		this.lines = lines;
	}
	
	public Score(String uuid, int line, String score){
		this.uuid = uuid;
	}
	
	public Score(String uuid){
		this.uuid = uuid;
	}
	
	public void create(){
		obj = Bukkit.getScoreboardManager().getNewScoreboard().registerNewObjective("dummy", "dummy");
		
		obj.setDisplayName(this.displayName);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		for(int x = 0; x < lines.size(); x++){
			obj.getScore(lines.get(x)).setScore(15-x);
		}
	}
	
	public void set(Player p){
		p.setScoreboard(obj.getScoreboard());
	}
	
	public void update(Player p){
		
	}

}
