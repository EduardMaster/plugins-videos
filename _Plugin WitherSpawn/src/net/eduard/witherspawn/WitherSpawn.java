
package net.eduard.witherspawn;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.plugin.java.JavaPlugin;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.dev.Game;
import net.eduard.api.util.SimpleEffect;
import net.eduard.witherspawn.command.WitherCMD;
import net.eduard.witherspawn.event.WitherDeathEvent;

public class WitherSpawn extends JavaPlugin  {

	public static Config config;
	public static Wither wither;
	
	
	public void onDisable() {
		if (wither != null) {
			wither.remove();
			config.remove("WitherDeathTime");
		}
	}

	public void onEnable() {
		config = new Config(this);
		config.add("Spawned", "&cO Boss Wither ja esta vivo!");
		config.add("NoSpawn", "&6O spawn do Boss Wither nao foi setado!");
		config.add("SetSpawn", "&bO spawn do Boss Wither setado!");
		config.add("Spawn", "&bO Boss Wither foi spawnado!");
		config.add("RespawnTime", 4 * 60 * 60L);
		config.saveConfig();
		new WitherCMD();
		new WitherDeathEvent();
		autoSpawn();
		
	}
	public static void setWitherSpawn(Location location) {

		config.set("WitherSpawn", location);
		config.saveConfig();
	}

	public static void spawnWither() {

		Location spawn = getWitherSpawn();
		wither = spawn.getWorld().spawn(spawn, org.bukkit.entity.Wither.class);
	}
	private void autoSpawn() {

		if (config.contains("WitherSpawn")) {
			for (LivingEntity livingEntity : getWitherSpawn().getWorld()
				.getLivingEntities()) {
				if (livingEntity instanceof Wither) {
					org.bukkit.entity.Wither wither =
						(org.bukkit.entity.Wither) livingEntity;
					wither.remove();
				}
			}
		}

		if (canSpawnWither()) {
			spawnWither();
		}
		new Game(1).timer(new SimpleEffect() {
			
			public void effect() {
				if (canSpawnWither()) {
					spawnWither();
				}
				
			}
		});
	}

	public static boolean canSpawnWither() {
		if (config.contains("WitherSpawn")) {
			if (wither == null) {
				if (config.contains("WitherDeathTime")) {
					long death_time = config.getLong("WitherDeathTime");
					long respawn_time =
						config.getLong("RespawnTime") * 1000L;
					long now_time = API.getTime();
					long form = now_time - death_time;
					return form > respawn_time;
				} else {
					return true;
				}
			}
		}
		return false;

	}

	public static Location getWitherSpawn() {

		return config.getLocation("WitherSpawn");
	}

	public static boolean hasSpawn() {
		return WitherSpawn.config.contains("WitherSpawn") ;
	}
}
