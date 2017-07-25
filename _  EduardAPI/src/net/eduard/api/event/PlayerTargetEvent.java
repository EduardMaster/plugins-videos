package net.eduard.api.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PlayerTargetEvent extends PlayerEvent
		 {

	private LivingEntity entity;
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}
	private static final HandlerList handlers = new HandlerList();

	public PlayerTargetEvent(Player player, LivingEntity livingEntity) {
		super(player);
		setEntity(livingEntity);

	}

	public LivingEntity getEntity() {
		return entity;
	}

	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}

}
