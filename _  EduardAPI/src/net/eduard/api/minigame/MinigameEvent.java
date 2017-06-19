package net.eduard.api.minigame;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MinigameEvent extends Event implements Cancellable{

	private static final HandlerList events = new HandlerList();
	private Minigame minigame;
	private MinigameRoom room;
	private MinigameMap map;
	private boolean cancelled;
	
	
	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public MinigameEvent(Minigame minigame, MinigameRoom room,
			MinigameEventType event) {
		super();
		this.minigame = minigame;
		this.room = room;
		this.event = event;
		this.map = room.getMap();
	}

	public Minigame getMinigame() {
		return minigame;
	}

	public MinigameRoom getRoom() {
		return room;
	}

	public MinigameMap getMap() {
		return map;
	}

	public MinigameEventType getEvent() {
		return event;
	}

	private MinigameEventType event;
	
	public HandlerList getHandlers() {
		return events;
	}

	
}
