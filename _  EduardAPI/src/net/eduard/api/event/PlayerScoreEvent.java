package net.eduard.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class PlayerScoreEvent extends PlayerEvent {

	public PlayerScoreEvent(Player who) {
		super(who);
	}

}
