package me.eduard.bungeecord;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

	public void onEnable() {
		BungeeCord.getInstance().getConsole().sendMessage("");
		BungeeCord.getInstance().getPluginManager().registerListener(this, new Eventos());
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new Comando());
		BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§aPlugin ativado"));
		
	}
	public void onDisable() {
		
		
		BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("§aPlugin desativado"));
	}
}
