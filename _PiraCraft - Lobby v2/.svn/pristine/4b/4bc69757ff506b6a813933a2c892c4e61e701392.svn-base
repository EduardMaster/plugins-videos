package br.com.piracraft.lobby2.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import br.com.piracraft.lobby2.Main;

public final class LobbyAPI {

	public static void playercount(Player p, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("PlayerCount");
		out.writeUTF(server);
		p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
	}

	public static void motd(Player p, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("PlayerCount");
		out.writeUTF(server);
		p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
	}


	public static void enviar(Player p, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
	}

	private static Location lobby;

	public static Location getLobby() {
		return lobby;
	}

	public static void enviarAlertaPlayer(Player player,  String prefix,
			String message,Cor cor) {
		if (cor == Cor.Vermelho) {
			player.sendMessage("§4" + prefix + " §c> §f" + message);
		}
		if (cor == Cor.Amarelo) {
			player.sendMessage("§6" + prefix + " §e> §f" + message);
		}
		if (cor == Cor.Cinza) {
			player.sendMessage("§7" + prefix + " §8> §f" + message);
		}
		if (cor == Cor.Verde) {
			player.sendMessage("§2" + prefix + " §a> §f" + message);
		}
		if (cor == Cor.Azul) {
			player.sendMessage("§9" + prefix + " §b> §f" + message);
		}
		if (cor == Cor.Roxo) {
			player.sendMessage("§5" + prefix + " §d> §f" + message);
		}
	}

	public static void enviarAlertaConsole(String prefix, String message,
			Cor cor) {
		if (cor == Cor.Vermelho) {
			Main.getPlugin().getServer().getConsoleSender()
					.sendMessage("§4" + prefix + " §c> §f" + message);
		}
		if (cor == Cor.Amarelo) {
			Main.getPlugin().getServer().getConsoleSender()
					.sendMessage("§6" + prefix + " §e> §f" + message);
		}
		if (cor == Cor.Cinza) {
			Main.getPlugin().getServer().getConsoleSender()
					.sendMessage("§7" + prefix + " §8> §f" + message);
		}
		if (cor == Cor.Verde) {
			Main.getPlugin().getServer().getConsoleSender()
					.sendMessage("§2" + prefix + " §a> §f" + message);
		}
		if (cor == Cor.Azul) {
			Main.getPlugin().getServer().getConsoleSender()
					.sendMessage("§9" + prefix + " §b> §f" + message);
		}
		if (cor == Cor.Roxo) {
			Main.getPlugin().getServer().getConsoleSender()
					.sendMessage("§5" + prefix + " §d> §f" + message);
		}
	}
	public static void setLobby(Location location) {
		lobby = location;
	}
}
