package net.eduard.api.gui;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
/**
 * Representa um Efeito de clicar na Tela<br>
 * Efeito de PlayerInteractEntityEvent ou PlayerInteractEvent
 * @author Eduard-PC
 *
 */
public interface ClickEffect {

	public void effect(PlayerInteractEntityEvent e);
	public void effect(PlayerInteractEvent e);
}