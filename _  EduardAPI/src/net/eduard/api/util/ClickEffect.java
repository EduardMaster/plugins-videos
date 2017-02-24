package net.eduard.api.util;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ClickEffect {

	public void effect(PlayerInteractEvent e);
	public void effect(PlayerInteractEntityEvent e);
}
