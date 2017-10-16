package net.eduard.api.setup;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

/**
 * API do BungeeCord
 * 
 * @author Eduard-PC
 *
 */
public final class BungeeAPI {

	public static void log(String message) {
		Mine.console("§bBungeeAPI §f" + message);
	}

	private static Map<String, ServerInfo> servers = new HashMap<>();
	private static Map<String, PlayerInfo> players = new HashMap<>();
	public static boolean enabled;
	private static Plugin plugin;
	private static BungeeMessageListener listener;
	private static String currentServer;
	private static BukkitTask task;
	
	private static boolean registred;


	public static PlayerInfo getPlayer(Player player) {
		return players.getOrDefault(player.getName().toLowerCase(),
				new PlayerInfo(player));
	}
	public static PlayerInfo getPlayer(String player) {
		return players.getOrDefault(player.toLowerCase(),
				new PlayerInfo(player));
	}

	public static ServerInfo getServer(String server) {
		return servers.getOrDefault(server, new ServerInfo(server));
	}
	public static class BungeeMessageListener
			implements
				PluginMessageListener,
				Listener {
		@Override
		public void onPluginMessageReceived(String channel, Player player,
				byte[] message) {
			if (!channel.equals("BungeeCord")) {
				return;
			}
			ByteArrayDataInput in = ByteStreams.newDataInput(message);
			Mine.callEvent(new BungeeMessageEvent(player, in));
		}
		public static void updateServer(String serverName) {
			getServer(serverName);
			requestServerIp(serverName);
			log("? SERVER PLAYER COUNT §E" + serverName);
			requestPlayerCount(serverName);
			updatePlayers(serverName);
		}
		public static void updatePlayers(String serverName) {
			requestPlayerList(serverName);
		}

		@EventHandler
		public void event(BungeeMessageEvent e) {
			Player p = e.getPlayer();
			ByteArrayDataInput data = e.getData();
			String request = e.getRequest();
			if (request.equals("GetServers")) {
				String[] serversNames = data.readUTF().split(", ");
				log("§aSERVERS: §F" + Arrays.asList(serversNames));
				for (String serverName : serversNames) {
					getServer(serverName);
				}
			}
			if (request.equals("GetServer")) {
				ServerInfo server = getServer(data.readUTF());
				log("§APLAYER §F" + p.getName() + "§A CONNECTED §F"
						+ server.getName());
				if (currentServer == null)
					currentServer = server.getName();
				if (server.getIp() == null)
					requestServerIp(currentServer);
				if (servers.isEmpty())
					requestServersNames();
			}
			if (request.equals("PlayerList")) {
				ServerInfo server = getServer(data.readUTF());
				String[] array = data.readUTF().split(", ");
				List<String> list = Arrays.asList(array);
				log("§ASERVER PLAYERS: §F" + server.getName() + " | " + list);
				server.setPlayers(list);
				for (String playerName : list) {
					if (playerName.isEmpty())
						continue;
					PlayerInfo player = getPlayer(playerName);
					if (player.getId() == null)
						requestPlayerId(playerName);
				}
			}
			if (request.equals("PlayerCount")) {
				ServerInfo server = getServer(data.readUTF());
				server.setPlayersAmount(data.readInt());
				// log("Player count from Server " + server.getName() + " is "
				// + server.getPlayersAmount());
			}
			if (request.equals("UUID")) {
				PlayerInfo playerInfo = getPlayer(p);
				playerInfo.setId(
						UUID.nameUUIDFromBytes(data.readUTF().getBytes()));
				log("§aPLAYER UUID §F" + p.getName() + " | "
						+ playerInfo.getId());
			}
			if (request.equals("UUIDOther")) {
				String playerName = data.readUTF();
				UUID id = UUID.nameUUIDFromBytes(data.readUTF().getBytes());
				log("§aPLAYER NAME ID §F" + playerName + " | " + id);
				getPlayer(playerName).setId(id);
			}
			if (request.equals("IP")) {
				PlayerInfo player = getPlayer(p);
				player.setIp(data.readUTF());
				player.setPort(data.readInt());
				log("§aPLAYER IP §F" + p.getName() + " | " + player.getIp()
						+ ":" + player.getPort());
			}
			if (request.equals("ServerIP")) {
				ServerInfo server = getServer(data.readUTF());
				server.setIp(data.readUTF());
				server.setPort(data.readUnsignedShort());
				log("§aSERVER IP §F" + server.getName() + " | " + server.getIp()
						+ ":" + server.getPort());

			}

		}
	}
	static {
		listener = new BungeeMessageListener();
	}
	
	public static void register(Plugin plugin) {
		unregister();
		Bukkit.getMessenger().registerOutgoingPluginChannel(plugin,
				"BungeeCord");
		Bukkit.getMessenger().registerIncomingPluginChannel(plugin,
				"BungeeCord", listener);
		Bukkit.getPluginManager().registerEvents(listener, plugin);
		setRegistred(true);
		setPlugin(plugin);
//
//		task = new BukkitRunnable() {
//			int max = 60 * 3;
//			int id = max;
//			@Override
//			public void run() {
//				for (ServerInfo server : servers.values()) {
//					requestPlayerCount(server.getName());
//				}
//
//				if (id >= max) {
//					Player p = getFirstPlayer();
//					if (p != null) {
//						requestPlayerServer(p);
//						requestPlayerId(p);
//						requestPlayerIp(p);
//						id = 0;
//					}
//
//				}
//				id++;
//			}
//		}.runTaskTimer(plugin, 20, 20);

	}
	public static void unregister() {
		if (isRegistred()) {

			Bukkit.getMessenger().unregisterIncomingPluginChannel(getPlugin(),
					"BungeeCord", listener);
			Bukkit.getMessenger().unregisterOutgoingPluginChannel(plugin,
					"BungeeCord");
			HandlerList.unregisterAll(listener);
			setRegistred(false);
			if (task != null) {
				task.cancel();
				task = null;
			}
			setPlugin(null);
		}
	}
	public boolean isEnabled() {
		return enabled;
	}

	// public static Plugin getInstance() {
	// return JavaPlugin.getProvidingPlugin(BungeeAPI.class);
	// }
	public static void connectServer(Player player, String server) {
		log("CONNECT TO §E" + server + "§F PLAYER §E" + player.getName());
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);

		sendBungeeMessage(player, out);
	}
	public static void connectServer(String playerName, String server) {
		log("CONNECT TO §E" + server + "§F PLAYER §E" + playerName);
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ConnectOther");
		out.writeUTF(playerName);
		out.writeUTF(server);
		sendBungeeMessage(out);
	}
	/**
	 * String server to send to, ALL to send to every server (except the one
	 * sending the plugin message), or ONLINE to send to every server that's
	 * online (except the one sending the plugin message)
	 * 
	 * @param subChannel
	 * @param server
	 * @param data
	 */
	public static void sendToServer(String subChannel, String server,
			Object... data) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Forward"); // So BungeeCord knows to forward it
		out.writeUTF(server);
		out.writeUTF(subChannel); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeInt(data.length);
			for (int i = 0; i < data.length; i++) {
				msgout.writeUTF(data[i].toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // You can do anything you want with msgout

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		sendBungeeMessage(out);
	}
	public static void sendToPlayer(String playerName, String subChannel,
			Object... data) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ForwardToPlayer"); // So BungeeCord knows to forward it
		out.writeUTF(playerName);
		out.writeUTF(subChannel); // The channel name to check if this your data

		ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
		DataOutputStream msgout = new DataOutputStream(msgbytes);
		try {
			msgout.writeInt(data.length);
			for (int i = 0; i < data.length; i++) {
				msgout.writeUTF(data[i].toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} // You can do anything you want with msgout

		out.writeShort(msgbytes.toByteArray().length);
		out.write(msgbytes.toByteArray());

		sendBungeeMessage(out);
	}
	public static String[] readBungeeMessage(ByteArrayDataInput data) {
		short len = data.readShort();
		byte[] msgbytes = new byte[len];

		data.readFully(msgbytes);
		String[] result = null;
		DataInputStream msgin = new DataInputStream(
				new ByteArrayInputStream(msgbytes));
		try {
			result = new String[msgin.readInt()];
			for (int i = 0; i < result.length; i++) {
				String somedata = msgin.readUTF();
				result[i] = somedata;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = new String[0];
		}
		return result;
	}

	public static void sendMessage(String playerName, String message) {
		log("CHAT §E" + playerName + "§F MESSAGE §E" + message);
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Message");
		out.writeUTF(playerName);
		out.writeUTF(message);
		sendBungeeMessage(out);
	}
	public static void requestPlayerCount(String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("PlayerCount");
		out.writeUTF(server);
		sendBungeeMessage(out);
	}
	public static void requestServersNames() {
		log("? SERVERS");
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("GetServers");
		sendBungeeMessage(out);
	}
	public static void requestPlayerList(String server) {
		log("? SERVER PLAYERS §E" + server);
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("PlayerList");
		out.writeUTF(server);
		sendBungeeMessage(out);
	}
	public static void requestPlayerServer(Player player) {
		log("? PLAYER SERVER §E" + player.getName());
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("GetServer");

		sendBungeeMessage(player, out);
	}

	public static void kickPlayer(String playerName, String reason) {
		log("KICK §e" + playerName + "'§E REASON §F" + reason);
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("KickPlayer");
		out.writeUTF(playerName);
		out.writeUTF(reason);

		sendBungeeMessage(out);
	}
	public static void requestServerIp(String server) {
		log("? SERVER IP §e" + server);
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ServerIP");
		out.writeUTF(server);
		sendBungeeMessage(out);
	}
	public static void requestPlayerId(Player player) {
		log("? PLAYER ID §e" + player.getName());
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("UUID");

		sendBungeeMessage(player, out);
	}
	public static void requestPlayerId(String playerName) {
		log("? PLAYER NAME ID §e" + playerName);
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("UUIDOther");
		out.writeUTF(playerName);
		sendBungeeMessage(out);
	}
	public static void requestPlayerIp(Player player) {
		log("? PLAYER IP §e" + player.getName());
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("IP");
		player.sendPluginMessage(getPlugin(), "BungeeCord", out.toByteArray());
	}
	public static void sendBungeeMessage(ByteArrayDataOutput message) {
		Player first = getFirstPlayer();
		if (first == null)
			return;
		sendBungeeMessage(first, message);
	}
	public static void sendBungeeMessage(Player player,
			ByteArrayDataOutput message) {
		// Bukkit.getMessenger().dispatchIncomingMessage(player, "BungeeCord",
		// message.toByteArray());
		player.sendPluginMessage(getPlugin(), "BungeeCord",
				message.toByteArray());
	}
	public static byte[] writeMessage(Object... objects) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		StringBuilder st = new StringBuilder();
		for (int i = 0; i < objects.length; i++) {
			if (i != 0) {
				st.append(";");
			}
			st.append(objects[i]);
		}
		out.writeUTF(st.toString());
		return out.toByteArray();
	}
	public static String[] readMessage(byte[] message) {
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String text = in.readUTF();
		if (text.contains(";")) {
			return text.split(";");
		}
		return new String[]{text};
	}

	public static Player getFirstPlayer() {
		return Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
	}
	public static Plugin getPlugin() {
		return plugin;
	}
	public static void setPlugin(Plugin plugin) {
		BungeeAPI.plugin = plugin;
	}
	public static boolean isRegistred() {
		return registred;
	}
	public static void setRegistred(boolean registred) {
		BungeeAPI.registred = registred;
	}
	public static String getCurrentServer() {
		return currentServer;
	}
	public static void setCurrentServer(String currentServer) {
		BungeeAPI.currentServer = currentServer;
	}
	public static class BungeeMessageEvent extends Event {
		private static final HandlerList handlers = new HandlerList();
		@Override
		public HandlerList getHandlers() {
			return handlers;
		}
		public static HandlerList getHandlerList() {
			return handlers;
		}
		public BungeeMessageEvent(Player player, ByteArrayDataInput data) {
			this.player = player;
			this.data = data;
			this.request = data.readUTF();
		}
		public ByteArrayDataInput getData() {
			return data;
		}
		public Player getPlayer() {
			return player;
		}
		public String getRequest() {
			return request;
		}
		private ByteArrayDataInput data;
		private Player player;
		private String request;

	}
	public static class ServerInfo {
		private String name;
		private String ip;
		private int port;
		private int playersAmount;
		private List<String> players = new ArrayList<>();
		public ServerInfo(String serverName) {
			this.name = serverName;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public int getPlayersAmount() {
			return playersAmount;
		}
		public void setPlayersAmount(int playersAmount) {
			this.playersAmount = playersAmount;
		}
		public List<String> getPlayers() {
			return players;
		}
		public void setPlayers(List<String> players) {
			this.players = players;
		}
		@Override
		public String toString() {
			return "ServerInfo [name=" + name + ", ip=" + ip + ", port=" + port
					+ ", playersAmount=" + playersAmount + ", players="
					+ players + "]";
		}

	}
	public static class PlayerInfo {
		public PlayerInfo(Player player) {
			this(player.getName());
			this.id = player.getUniqueId();
		}
		public PlayerInfo(String playerName) {
			this.name = playerName;
		}
		private String name;
		private UUID id;
		private String ip;
		private int port;
		private String server;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public UUID getId() {
			return id;
		}
		public void setId(UUID id) {
			this.id = id;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public String getServer() {
			return server;
		}
		public void setServer(String server) {
			this.server = server;
		}
		@Override
		public String toString() {
			return "PlayerInfo [name=" + name + ", id=" + id + ", ip=" + ip
					+ ", port=" + port + ", server=" + server + "]";
		}

	}
}
