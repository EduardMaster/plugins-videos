package me.leo.skywars;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class Texts {

	public static void sendTitle(Player player, String titulo, String subTitulo, Integer aparecer, Integer ficar,
			Integer desaparecer) {
		IChatBaseComponent title = ChatSerializer.a("{\"text\": \"" + titulo + "\"}");
		IChatBaseComponent subTitle = ChatSerializer.a("{\"text\": \"" + subTitulo + "\"}");
		
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, title));
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subTitle));
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(aparecer, ficar, desaparecer));
	}

}
