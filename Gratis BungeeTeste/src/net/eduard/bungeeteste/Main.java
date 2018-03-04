
package net.eduard.bungeeteste;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import net.eduard.api.EduardPlugin;
import net.eduard.bungeeteste.command.TemplateCommand;
import net.eduard.bungeeteste.event.TemplateEvents;

public class Main extends EduardPlugin implements PluginMessageListener {
	private static Main plugin;

	public static Main getInstance() {
		return plugin;
	}

	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	@Override
	public void onEnable() {
		plugin = this;
		new TemplateEvents().register(this);
		new TemplateCommand().register();
		
		// getServer().getPluginManager().registerEvents(this, this);
		// if (!getServer().getMessenger().isOutgoingChannelRegistered(this,
		// "BungeeCord")) {
//		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
//		getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
//		getServer().getMessenger().registerOutgoingPluginChannel(this, "Exemplo");
//		getServer().getMessenger().registerIncomingPluginChannel(this, "Exemplo", this);
//		BukkitControl.register(plugin);
//		BukkitControl.sendMessage("set-state", "3");
	}

//	private void sendMessage( String channel, String message) {
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		DataOutputStream out = new DataOutputStream(stream);
//		try {
//			out.writeUTF(channel);
//			out.writeUTF(message);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		getServer().sendPluginMessage(this, "Exemplo", stream.toByteArray());
//	}

	@Override
	public void onDisable() {
		
	}
//
//	public void sendMessage(Player p, String channel, String message) {
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		DataOutputStream out = new DataOutputStream(stream);
//		try {
//			out.writeUTF(channel);
//			out.writeUTF(message);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		p.sendPluginMessage(this, "Exemplo", stream.toByteArray());
//	}
//
	@Override
	public void onPluginMessageReceived(String tag, Player player, byte[] data) {
//		Bukkit.getConsoleSender().sendMessage("§cCanal: "+tag);
//		if (tag.equals("Exemplo")) {
//			ByteArrayDataInput in = ByteStreams.newDataInput(data);
//			String channel = in.readUTF();
//			if (channel.equals("console")) {
//				String comando = in.readUTF();
//				Mine.makeCommand(comando);
//			}
//		}
//
	}

}
