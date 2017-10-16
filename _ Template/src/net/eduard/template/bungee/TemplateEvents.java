package net.eduard.template.bungee;

import org.bukkit.event.EventHandler;

import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;

public class TemplateEvents implements Listener{

	@EventHandler
	public void onMessage(PluginMessageEvent e) {
		if (Main.getPlugin().getDescription().getName().equals(e.getTag())) {

			
		}
	
	}
}
