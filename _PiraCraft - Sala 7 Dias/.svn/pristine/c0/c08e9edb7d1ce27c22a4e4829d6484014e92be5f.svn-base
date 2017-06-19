package com.hcp.daays;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import br.com.piracraft.api.Main;

public class MobSpawn {

	public static List<LivingEntity> spawn(Location loc) {
		List<LivingEntity> entity = new ArrayList<LivingEntity>();
		for (int y = 0; y < Main.hard.size(); y++) {
			if (Main.hard.get(y).getIdDias() == Days.days) {
				LivingEntity le = (LivingEntity) Bukkit.getWorld("world").spawnEntity(loc,
						Main.hard.get(y).getNomeBukkit());
				entity.add(le);
			}
		}
		return entity;
	}

}
