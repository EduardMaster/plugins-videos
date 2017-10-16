package net.eduard.api;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import net.eduard.api.config.BungeeConfig;
import net.eduard.api.manager.DBManager;
import net.eduard.api.setup.StorageAPI;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ProxyReloadEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.event.TabCompleteResponseEvent;
import net.md_5.bungee.api.event.TargetedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class EduardBungee extends Plugin implements Listener {
	private DBManager bungeeManager;

	@EventHandler
	public void onJoin(PreLoginEvent e) {
		PendingConnection p = e.getConnection();
		info("§aPreLoginEvent", p);
	}
	@EventHandler
	public void onJoin(PostLoginEvent e) {
		ProxiedPlayer player = e.getPlayer();
		info("§aPostLoginEvent", player.getPendingConnection());
		if (!bungeeManager.playersContains(player.getName())) {
			bungeeManager.insert("players", player.getName(),
					player.getUniqueId(), "");
		}
	}

	@EventHandler
	public void event(PermissionCheckEvent e) {

	}
	@EventHandler
	public void event(PlayerHandshakeEvent e) {
	}
	@EventHandler
	public void event(PlayerDisconnectEvent e) {

	}
	@EventHandler
	public void event(PluginMessageEvent e) {
	
		
	}
	@EventHandler
	public void event(ProxyPingEvent e) {

	}
	@EventHandler
	public void event(ServerDisconnectEvent e) {

	}

	@EventHandler
	public void event(ServerConnectedEvent e) {
		ProxiedPlayer player = e.getPlayer();
		bungeeManager.change("players", "server = ?", "uuid = ?",
				e.getServer().getInfo().getName(), player.getUniqueId());
	}
	@EventHandler
	public void event(ServerKickEvent e) {

	}
	@EventHandler
	public void event(ServerConnectEvent e) {

	}
	@EventHandler
	public void event(ServerSwitchEvent e) {

	}
	@EventHandler
	public void event(TargetedEvent e) {

	}

	@EventHandler
	public void event(ProxyReloadEvent e) {

	}

	@EventHandler
	public void event(TabCompleteResponseEvent e) {

	}
	@EventHandler
	public void event(TabCompleteEvent e) {

	}
	@EventHandler
	public void onJoin(LoginEvent e) {
		PendingConnection p = e.getConnection();
		info("§aLoginEvent", p);
	}
	public void info(String prefix, PendingConnection p) {
		// BungeeCord.getInstance().getConsole()
		// .sendMessage(new TextComponent(prefix+" "+
		// "§fNAME: " + p.getName() + " UUID " + p.getUniqueId()
		// + " HOST " + p.getAddress().getHostString()));
	}
	private BungeeConfig config;
	public void onEnable() {
		BungeeCord.getInstance().getPluginManager().registerListener(this,
				this);
		StorageAPI.register(DBManager.class);
		config = new BungeeConfig("bungee-control.yml", this);
		if (config.contains("Database")) {
			setBungeeManager((DBManager) config.get("Database"));
		} else {
			setBungeeManager(new DBManager("root", "", "localhost"));
			config.set("Database", bungeeManager);
			config.saveConfig();

		}
		bungeeManager.openConnection();
		bungeeManager.createTable("servers",
				"name varchar(50), host varchar(100), port int, players int, status int");
		bungeeManager.createTable("players",
				"name varchar(16), uuid varchar(100), server varchar(50)");

		for (ServerInfo server : BungeeCord.getInstance().getServers()
				.values()) {
			if (!bungeeManager.serversContains(server.getName())) {
				bungeeManager.insert("servers", server.getName(),
						server.getAddress().getHostName(),
						server.getAddress().getPort(),
						server.getPlayers().size(), 0);
			}
		}
		BungeeCord.getInstance().getScheduler().schedule(this, new Runnable() {
			@Override
			public void run() {
				for (ServerInfo server : BungeeCord.getInstance().getServers()
						.values()) {
					bungeeManager.setPlayersAmount(server.getName(),
							server.getPlayers().size());
				}
			}
		}, 1, 1, TimeUnit.SECONDS);

	}
	
	 public void sendToBukkit(String channel, String message, ServerInfo server) { ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    DataOutputStream out = new DataOutputStream(stream);
	    try {
	      out.writeUTF(channel);
	      out.writeUTF(message);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    server.sendData("CashChannel", stream.toByteArray()); }

	public void onDisable() {
		bungeeManager.closeAll();
	}
	public DBManager getBungeeManager() {
		return bungeeManager;
	}
	public void setBungeeManager(DBManager bungeeManager) {
		this.bungeeManager = bungeeManager;
	}

}
