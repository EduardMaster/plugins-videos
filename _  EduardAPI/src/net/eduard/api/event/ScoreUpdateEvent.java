package net.eduard.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ScoreUpdateEvent extends PlayerEvent {
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
		return handlers;
	}
	private static final HandlerList handlers = new HandlerList();
	
	
	public ScoreUpdateEvent(Player who) {
		super(who);
	}


}
