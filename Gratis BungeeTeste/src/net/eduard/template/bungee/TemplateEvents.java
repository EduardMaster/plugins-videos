package net.eduard.template.bungee;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class TemplateEvents implements Listener {

	@EventHandler
	public void onMessage(PluginMessageEvent e) {
		if (Template.getPlugin().getDescription().getName().equals(e.getTag())) {

		}

	}

	@EventHandler
	public void event(PostLoginEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
	}
}
