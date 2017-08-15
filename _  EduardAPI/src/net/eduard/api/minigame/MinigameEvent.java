package net.eduard.api.minigame;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MinigameEvent extends Event implements Cancellable{

	private static final HandlerList events = new HandlerList();
	private Minigame minigame;
	private Game room;
	private GameMap map;
	private boolean cancelled;
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public MinigameEvent(Minigame minigame, Game room) {
	
		this.minigame = minigame;
		this.room = room;
		this.map = room.getMap();
	}

	public Minigame getMinigame() {
		return minigame;
	}

	public Game getRoom() {
		return room;
	}

	public GameMap getMap() {
		return map;
	}


	@Override
	public HandlerList getHandlers() {
		return events;
	}

	
}
