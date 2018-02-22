package net.eduard.live.almas;

import org.bukkit.entity.Player;

public class AlmasAPI {
	public static void addAlmas(Player player, double almasQuantidade) {
		setAlmas(player, getAlmas(player) + almasQuantidade);
	}
	public static void removeAlmas(Player player, double almasQuantidade) {
		setAlmas(player, getAlmas(player) - almasQuantidade);
	}
	public static double getAlmas(Player player) {
		return Main.almas.getOrDefault(player, 0D);
	}

	public static void setAlmas(Player player, double almas) {
		Main.almas.put(player, almas);
	}
}
