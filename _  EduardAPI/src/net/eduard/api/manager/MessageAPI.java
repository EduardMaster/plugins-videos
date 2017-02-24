package net.eduard.api.manager;

import org.bukkit.entity.Player;

public final class MessageAPI extends RexAPI {

	public static Object sendMessage(String message, Player player) {
		try {
			Object component = getIChatBaseComponentText(message);
			Object packet = getNew(Rex.PLAY_OUT_CHAT, getParameters(Rex.ICHAT_BASE_COMPONENT), component);
			sendPacket(packet, player);
			return packet;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static Object sendFormat(String format, Player player) {
		try {
			Object component = getIChatBaseComponentI(format);
			Object packet = getNew(Rex.PLAY_OUT_CHAT, getParameters(Rex.ICHAT_BASE_COMPONENT), component);
			sendPacket(packet, player);
			return packet;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
