package net.eduard.api.minigame;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.eduard.api.API;
import net.eduard.api.util.Cs;

public class Minigame implements MinigameEvents {

	private String name;

	private Map<String, MinigameMap> maps = new HashMap<>();
	private Map<Integer, MinigameRoom> rooms = new HashMap<>();
	private Map<Player, MinigameRoom> playing = new HashMap<>();
	private BukkitTask minigameRunnning;
	private boolean hasAttributes = true;
	private boolean hasInvuneability = true;
	private MinigameAttributes attributes = new MinigameAttributes();

	public Minigame(String name, JavaPlugin plugin) {
		setName(name);
		minigameRunnning = new BukkitRunnable() {

			public void run() {
				for (MinigameRoom room : rooms.values()) {
					event(room);
				}

			}
		}.runTaskTimer(plugin, 20, 20);
	}
	public void broadcast(Object... text) {
		Cs.sendMessage(playing.keySet(), text);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, MinigameMap> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, MinigameMap> maps) {
		this.maps = maps;
	}

	public Map<Integer, MinigameRoom> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Integer, MinigameRoom> rooms) {
		this.rooms = rooms;
	}

	public Map<Player, MinigameRoom> getPlaying() {
		return playing;
	}

	public void setPlaying(Map<Player, MinigameRoom> playing) {
		this.playing = playing;
	}

	public void event(MinigameRoom room) {
		 MinigameState state = room.getState();
		 MinigameEventType type = MinigameEventType.TIMER_CHANGE;
		 int time = room.getTime();
		 if (state== MinigameState.STARTING){
			 room.setTime(time-1);
			 time = room.getTime();
			 
			 if (time==0){
				 type = MinigameEventType.GAME_START;
				 MinigameEvent event = new MinigameEvent(this, room,type );
				 API.callEvent(event);
				 if (event.isCancelled())
					 return;
				 event(room, type);
				 room.setState(MinigameState.INVULNERABILITY);
				 room.setTime(time);
			 }

		 }else if (state==MinigameState.RESTARTING){
			 
		 }
	}
	public void event(MinigameRoom room, MinigameEventType event) {
		
	}


	public BukkitTask getMinigameRunnning() {
		return minigameRunnning;
	}

	public void setMinigameRunnning(BukkitTask minigameRunnning) {
		this.minigameRunnning = minigameRunnning;
	}

	public MinigameAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(MinigameAttributes attributes) {
		this.attributes = attributes;
	}

	public boolean isHasInvuneability() {
		return hasInvuneability;
	}

	public void setHasInvuneability(boolean hasInvuneability) {
		this.hasInvuneability = hasInvuneability;
	}

	public boolean isHasAttributes() {
		return hasAttributes;
	}

	public void setHasAttributes(boolean hasAttributes) {
		this.hasAttributes = hasAttributes;
	}
	
	
}
