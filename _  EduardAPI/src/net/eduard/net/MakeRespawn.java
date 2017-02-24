package net.eduard.net;

import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_7_R4.EnumClientCommand;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;

public class MakeRespawn {
	
	public static void makeRespawn(Player player) {

		((CraftPlayer) player).getHandle().playerConnection
				.a(new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN));
	}
}
