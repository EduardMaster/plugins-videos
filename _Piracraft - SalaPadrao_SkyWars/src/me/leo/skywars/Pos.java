package me.leo.skywars;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Pos {

	public static Location tpLoc(int n) {
		Location loc = null;
		loc = new Location(Bukkit.getWorld("world"), Main.posicionamentoJaulas.get(n - 1).getX(),
				Main.posicionamentoJaulas.get(n - 1).getY(), Main.posicionamentoJaulas.get(n - 1).getZ());
		return loc;
	}

}