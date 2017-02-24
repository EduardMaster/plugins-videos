package net.eduard.api.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.entity.Player;

import net.eduard.api.API;

public final class PlayerAPI extends RexAPI {

	public static int getPing(Player player) {
		try {
			return API.toInt(getValue(getHandle(player), "ping"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public static List<Player> getPlayers() {
		List<Player> list = new ArrayList<>();
		try {

			Object object = getResult(Rex.BUKKIT, "getOnlinePlayers");
			if (object instanceof Collection) {
				Collection<?> players = (Collection<?>) object;
				for (Object obj : players) {
					if (obj instanceof Player) {
						Player p = (Player) obj;
						list.add(p);
					}
				}
			} else if (object instanceof Player[]) {
				Player[] players = (Player[]) object;
				for (Player p : players) {
					list.add(p);
				}
			}
		} catch (Exception e) {
		}

		return list;
	}
	public static void makeRespawn(Player player) {
		try {
			// Faz respawn automaticamente
			// Pega o pacote de Renascer
			Object packet = getNew(getMine("PacketPlayInClientCommand"),
					getValue(Rex.ENUM_CLIENT_COMMAND, "PERFORM_RESPAWN"));
			// Envia o pacote para o Jogador
			// "playerConnection.a(packetPlayInClientCommand);"
			getResult(getConnection(player), "a", packet);

		} catch (Exception ex) {

			ex.printStackTrace();
		}
	}

	public static void changeName(Player player, String displayName) {

		try {
			// Isso não altera o nome simplesmente modifica temporareamente o
			// nome para os outros jogadores isso inclue a Skin tbm

			// Crio o Pacote de troca de nome
			Object packet = getNew(getMine("PacketPlayOutNamedEntitySpawn"), getParameters(getMine("EntityHuman")),
					getHandle(player));
			// Pego a profile do jogador e troco o nome
			setValue(getValue(packet, "b"), "name", displayName);
			// Envio o pacote para todos jogadores menos pro Jogador
			sendPackets(packet, player);
			// Modifico o TabName do jogador
			// player.setPlayerListName(displayName);
			// Troco o ChatName do Jogador
			// player.setDisplayName(displayName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
