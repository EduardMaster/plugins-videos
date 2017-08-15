package net.eduard.api.minigame;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import net.eduard.api.API;

public abstract class Minigame{

	private String name;

	private Map<String, GameMap> maps = new HashMap<>();
	private Map<Integer, Game> rooms = new HashMap<>();
	private Map<Player, Game> playing = new HashMap<>();
	private BukkitTask minigameRunnning;
	private boolean hasInvuneability = true;
	
	public Minigame(String name, JavaPlugin plugin) {
		setName(name);
		minigameRunnning = new BukkitRunnable() {

			@Override
			public void run() {
				for (Game game : rooms.values()) {
					MinigameEvent event = new MinigameEvent(Minigame.this, game);
					if (event.isCancelled()) {
						continue;
					}
					event(game);
				}

			}
		}.runTaskTimer(plugin, 20, 20);
	}
	public abstract void event(Game room);
	public void broadcast(Object... text) {
		API.send(playing.keySet(), text);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, GameMap> getMaps() {
		return maps;
	}

	public void setMaps(Map<String, GameMap> maps) {
		this.maps = maps;
	}

	public Map<Integer, Game> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Integer, Game> rooms) {
		this.rooms = rooms;
	}

	public Map<Player, Game> getPlaying() {
		return playing;
	}

	public void setPlaying(Map<Player, Game> playing) {
		this.playing = playing;
	}

	


	public BukkitTask getMinigameRunnning() {
		return minigameRunnning;
	}

	public void setMinigameRunnning(BukkitTask minigameRunnning) {
		this.minigameRunnning = minigameRunnning;
	}

	public boolean isHasInvuneability() {
		return hasInvuneability;
	}

	public void setHasInvuneability(boolean hasInvuneability) {
		this.hasInvuneability = hasInvuneability;
	}

	
}
