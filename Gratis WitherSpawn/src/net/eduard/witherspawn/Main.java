
package net.eduard.witherspawn;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.manager.TimeManager;
import net.eduard.witherspawn.command.WitherCommand;
import net.eduard.witherspawn.event.WitherSpawnEvent;

public class Main extends JavaPlugin  {

	public static Config config;
	public static Wither wither;
	public static JavaPlugin plugin;
	public static TimeManager time;
	
	
	@Override
	public void onDisable() {
		if (wither != null) {
			wither.remove();
			config.remove("WitherDeathTime");
		}
	}

	@Override
	public void onEnable() {
		plugin = this;
		config = new Config(this);
		time = new TimeManager(this);
		config.add("Spawned", "&cO Boss Wither ja esta vivo!");
		config.add("NoSpawn", "&6O spawn do Boss Wither nao foi setado!");
		config.add("SetSpawn", "&bO spawn do Boss Wither setado!");
		config.add("Spawn", "&bO Boss Wither foi spawnado!");
		config.add("RespawnTime", 4 * 60 * 60L);
		config.saveConfig();
		new WitherCommand();
		new WitherSpawnEvent();
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
		API.delay(this,20,new BukkitRunnable() {
			
			@Override
			public void run() {
				if (canSpawnWither()) {
					spawnWither();
				}
			}
		});
		time.timer(20,new BukkitRunnable() {
			
			@Override
			public void run() {
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
					long now_time = API.getNow();
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
		return Main.config.contains("WitherSpawn") ;
	}
}
