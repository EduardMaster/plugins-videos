package net.eduard.api.manager;

import org.bukkit.event.Listener;

import net.eduard.api.API;

public abstract class EventAPI implements Listener {

	public EventAPI() {
		API.event(this);
	}
	
}
