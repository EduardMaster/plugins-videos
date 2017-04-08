package net.eduard.api.click;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ClickEffect {

	public void effect(PlayerInteractEntityEvent e);
	public void effect(PlayerInteractEvent e);
}