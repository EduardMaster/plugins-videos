package net.eduard.template.bungee;

import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class Template extends Plugin implements Listener {
	@Override
	public void onEnable() {
		plugin = this;
//		BungeeCord.getInstance().getPluginManager().registerListener(this, new TemplateEvents());
//		BungeeCord.getInstance().getPluginManager().registerCommand(this, new TemplateCommand());
//		BungeeCord.getInstance().getConsole().sendMessage(new TextComponent("Plugin ativado"));
//		BungeeCord.getInstance().getPluginManager().registerListener(this, this);
//		BungeeCord.getInstance().registerChannel("Exemplo");
//		BungeeControl.register(plugin);
//		Servidores.getServidores().get("pvp").setState(1);
		// servidores.get("lobby").getServer().ping(new Callback<ServerPing>() {
		//
		// @Override
		// public void done(ServerPing pingo, Throwable erro) {
		//// pingo.getPlayers().getSample()[0].
		// }
		// });
	}

	private static Template plugin;

	public static Template getPlugin() {
		return plugin;
	}

//	@EventHandler
//	public void onMessageReceive(PluginMessageEvent e) {
//		BungeeCord.getInstance().getConsole().sendMessage("§cCanal: " + e.getTag());
//		if (e.getTag().equalsIgnoreCase("Exemplo")) {
//			DataInputStream in = new DataInputStream(new ByteArrayInputStream(e.getData()));
//			try {
//				String channel = in.readUTF();
//				if (channel.equals("console")) {
//					String comando = in.readUTF();
//					sendToBukkit("console", comando);
//					
//				}
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//		// }
//	}

//	public void sendToBukkit(String channel, String message) {
//		for (ServerInfo server : BungeeCord.getInstance().getServers().values())
//			sendToBukkit(channel, message, server);
//	}
//
//	public void sendToBukkit(String channel, String message, ServerInfo server) {
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		DataOutputStream out = new DataOutputStream(stream);
//		try {
//			out.writeUTF(channel);
//			out.writeUTF(message);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		server.sendData("Exemplo", stream.toByteArray());
//		// server.sendData("BungeeCord", stream.toByteArray());
//	}
//
//	public void sendUpdate(String player) {
//
//	}
}
