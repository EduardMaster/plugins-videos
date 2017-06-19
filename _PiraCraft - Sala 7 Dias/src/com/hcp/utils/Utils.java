package com.hcp.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.hcp.daays.Difficulty;
import com.hcp.daays.MobSpawn;
import com.hcp.daays.Range;

import br.com.piracraft.api.Main;

public class Utils {
	
	public static void enviar(Player p, String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF(server);
		p.sendPluginMessage(com.hcp.Main.plugin, "BungeeCord", out.toByteArray());
	}

	public static Location getRandomLocation() {
		Location teleportLocation = null;
		int x = new Random().nextInt(400) + 1;
		int y = 150;
		int z = new Random().nextInt(400) + 1;
		boolean terra = false;
		while (terra == false) {

			teleportLocation = new Location(Bukkit.getWorld("world"), x, y, z);

			if (teleportLocation.getBlock().getType() != Material.AIR) {
				terra = true;
			} else
				y--;
		}
		return teleportLocation;
	}

	public static void spawnMobOnRandomLoc() {
		HashMap<Player, Boolean> sim = new HashMap<Player, Boolean>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			Location loc;
			if (new Random().nextInt(100) >= 50) {
				loc = p.getLocation().add(10, 0, 0);
			} else {
				loc = p.getLocation().add(0, 0, 10);
			}
			if (!sim.containsKey(p)) {
				sim.put(p, true);

				List<LivingEntity> a = MobSpawn.spawn(loc);
				Range.setTargetSec(p, 1.0, Difficulty.creeperRange, a);

				new BukkitRunnable() {
					public void run() {
						sim.remove(p);
					}
				}.runTaskLater(com.hcp.Main.plugin, 200);
			}
			if (Main.network.get(p) == 1) {
				p.sendMessage("§4§lHardcore§f§l» §eVarios mobs foram gerados a sua volta!");
			} else {
				p.sendMessage("§4§lHardcore§f§l» §eLots of mobs spawned around you!");
			}
		}

		Bukkit.getConsoleSender().sendMessage("§4§lMobs§f§l» §eMobs gerados!");
	}

}
