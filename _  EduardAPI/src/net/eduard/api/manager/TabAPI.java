package net.eduard.api.manager;

import org.bukkit.entity.Player;

import net.eduard.api.API;

public final class TabAPI extends RexAPI {
	
	
	public static void setTabList(Player player, String header, String footer) {
		try {
			Object packet = getNew(Rex.PLAY_OUT_PLAYER_LIST_HEADER_FOOTER, getParameters(Rex.ICHAT_BASE_COMPONENT),
					getIChatBaseComponentI(header));
			setValue(packet, "b", getIChatBaseComponentI(footer));
			sendPacket(packet, player);
			// net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
		} catch (Exception ex) {
			ex.printStackTrace();
			API.console("§bTabAPI §fRequisito para uso:§a Servidores 1.8++");
		}
	}
}
