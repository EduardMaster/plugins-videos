package net.eduard.api.gui;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface ClickEffect {

	public void effect(PlayerInteractEntityEvent e);
	public void effect(PlayerInteractEvent e);
}