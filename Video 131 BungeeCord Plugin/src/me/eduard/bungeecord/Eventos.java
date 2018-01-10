package me.eduard.bungeecord;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Eventos implements Listener{
	
	@EventHandler
	public void evento(PostLoginEvent e)
	{
		ProxiedPlayer p = e.getPlayer();
		p.sendMessage(new TextComponent("§aSeja bem vindo a network1"));
	}

}
