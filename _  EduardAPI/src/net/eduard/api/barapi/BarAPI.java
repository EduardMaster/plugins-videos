
package net.eduard.api.barapi;

import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class BarAPI implements Listener {
	private static HashMap<UUID, FakeDragon> players = new HashMap<>();

	private static HashMap<UUID, Integer> timers = new HashMap<>();

	private static JavaPlugin plugin;
	private static BukkitTask task;
	private static boolean enabled;
	private static boolean useSpigotHack = false;

	public static void enable(JavaPlugin java) {
		if (!enabled) {
			plugin = java;
			enabled = true;
			Bukkit.getPluginManager().registerEvents(new BarAPI(), plugin);
			useSpigotHack = false;
			if ((!useSpigotHack) && (FakeDragon1_8Fake.isUsable())) {
				useSpigotHack = true;
				BarUtils.detectVersion();
				plugin.getLogger().info("Detected spigot hack, enabling fake 1.8");
			}

			if (useSpigotHack) {
				task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

					public void run() {
						for (UUID uuid : BarAPI.players.keySet()) {
							Player p = Bukkit.getPlayer(uuid);
							BarUtils.sendPacket(p, ((FakeDragon) BarAPI.players.get(uuid))
									.getTeleportPacket(BarAPI.getDragonLocation(p.getLocation())));
						}
					}
				}, 5, 5);

			}
		}

	}

	private BarAPI() {
	}

	public static void disable() {
		if (enabled) {
			if (task != null)
				task.cancel();
			for (Player player : Bukkit.getOnlinePlayers()) {
				quit(player);
			}
			players.clear();
			for (Integer timerID : timers.values()) {
				Bukkit.getScheduler().cancelTask(timerID);
			}

			timers.clear();
		}

	}

	@EventHandler
	public void event(PluginDisableEvent e) {
		if (e.getPlugin().equals(plugin)) {
			disable();
		}

	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void PlayerLoggout(PlayerQuitEvent event) {

		quit(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerKick(PlayerKickEvent event) {
		quit(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {

		handleTeleport(event.getPlayer(), event.getTo().clone());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerRespawnEvent event) {

		handleTeleport(event.getPlayer(), event.getRespawnLocation().clone());
	}

	private void handleTeleport(final Player player, final Location loc) {

		if (!hasBar(player)) {
			return;
		}
		new BukkitRunnable() {

			public void run() {
				if (!BarAPI.hasBar(player)) {
					return;
				}
				FakeDragon oldDragon = BarAPI.getDragon(player, "");

				float health = oldDragon.health;
				String message = oldDragon.name;

				BarUtils.sendPacket(player, BarAPI.getDragon(player, "").getDestroyPacket());

				BarAPI.players.remove(player.getUniqueId());

				FakeDragon dragon = BarAPI.addDragon(player, loc, message);
				dragon.health = health;

				BarAPI.sendDragon(dragon, player);

			}
		}.runTaskTimer(plugin, 2, 2);
	}

	private static void quit(Player player) {

		removeBar(player);
	}

	public static boolean useSpigotHack() {

		return useSpigotHack;
	}

	public static void setMessage(String message) {

		for (Player player : Bukkit.getOnlinePlayers())
			setMessage(player, message);
	}

	public static void setMessage(Player player, String message) {

		FakeDragon dragon = getDragon(player, message);

		dragon.name = cleanMessage(message);
		dragon.health = dragon.getMaxHealth();

		cancelTimer(player);

		sendDragon(dragon, player);
	}

	public static void setMessage(String message, float percent) {

		for (Player player : Bukkit.getOnlinePlayers())
			setMessage(player, message, percent);
	}

	public static void setMessage(Player player, String message, float percent) {

		Validate.isTrue((0.0F <= percent) && (percent <= 100.0F), "Percent must be between 0F and 100F, but was: ",
				percent);

		FakeDragon dragon = getDragon(player, message);

		dragon.name = cleanMessage(message);
		dragon.health = (percent / 100.0F * dragon.getMaxHealth());

		cancelTimer(player);

		sendDragon(dragon, player);
	}

	public static void setMessage(String message, int seconds) {

		for (Player player : Bukkit.getOnlinePlayers())
			setMessage(player, message, seconds);
	}

	public static void setMessage(Player player, String message, int seconds) {

		Validate.isTrue(seconds > 0, "Seconds must be above 1 but was: ", seconds);

		FakeDragon dragon = getDragon(player, message);

		dragon.name = cleanMessage(message);
		dragon.health = dragon.getMaxHealth();

		final float dragonHealthMinus = dragon.getMaxHealth() / seconds;

		cancelTimer(player);

		timers.put(player.getUniqueId(), Integer.valueOf(Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {

			public void run() {

				FakeDragon drag = BarAPI.getDragon(player, "");
				drag.health -= dragonHealthMinus;

				if (drag.health <= 1.0F) {
					BarAPI.removeBar(player);
					BarAPI.cancelTimer(player);
				} else {
					BarAPI.sendDragon(drag, player);
				}
			}
		}, 20L, 20L).getTaskId()));

		sendDragon(dragon, player);
	}

	public static boolean hasBar(Player player) {

		return players.get(player.getUniqueId()) != null;
	}

	public static void removeBar(Player player) {

		if (!hasBar(player)) {
			return;
		}
		BarUtils.sendPacket(player, getDragon(player, "").getDestroyPacket());

		players.remove(player.getUniqueId());

		cancelTimer(player);
	}

	public static void setHealth(Player player, float percent) {

		if (!hasBar(player)) {
			return;
		}
		FakeDragon dragon = getDragon(player, "");
		dragon.health = (percent / 100.0F * dragon.getMaxHealth());

		cancelTimer(player);

		if (percent == 0.0F)
			removeBar(player);
		else
			sendDragon(dragon, player);
	}

	public static float getHealth(Player player) {

		if (!hasBar(player)) {
			return -1.0F;
		}
		return getDragon(player, "").health;
	}

	public static String getMessage(Player player) {

		if (!hasBar(player)) {
			return "";
		}
		return getDragon(player, "").name;
	}

	private static String cleanMessage(String message) {

		if (message.length() > 64) {
			message = message.substring(0, 63);
		}
		return message;
	}

	private static void cancelTimer(Player player) {

		Integer timerID = (Integer) timers.remove(player.getUniqueId());

		if (timerID != null)
			Bukkit.getScheduler().cancelTask(timerID.intValue());
	}

	private static void sendDragon(FakeDragon dragon, Player player) {

		BarUtils.sendPacket(player, dragon.getMetaPacket(dragon.getWatcher()));
		BarUtils.sendPacket(player, dragon.getTeleportPacket(getDragonLocation(player.getLocation())));
	}

	private static FakeDragon getDragon(Player player, String message) {

		if (hasBar(player)) {
			return (FakeDragon) players.get(player.getUniqueId());
		}
		return addDragon(player, cleanMessage(message));
	}

	private static FakeDragon addDragon(Player player, String message) {

		FakeDragon dragon = BarUtils.newDragon(message, getDragonLocation(player.getLocation()));

		BarUtils.sendPacket(player, dragon.getSpawnPacket());

		players.put(player.getUniqueId(), dragon);

		return dragon;
	}

	private static FakeDragon addDragon(Player player, Location loc, String message) {

		FakeDragon dragon = BarUtils.newDragon(message, getDragonLocation(loc));

		BarUtils.sendPacket(player, dragon.getSpawnPacket());

		players.put(player.getUniqueId(), dragon);

		return dragon;
	}

	private static Location getDragonLocation(Location loc) {

		if (BarUtils.isBelowGround) {
			loc.subtract(0.0D, 300.0D, 0.0D);
			return loc;
		}

		float pitch = loc.getPitch();

		if (pitch >= 55.0F)
			loc.add(0.0D, -300.0D, 0.0D);
		else if (pitch <= -55.0F)
			loc.add(0.0D, 300.0D, 0.0D);
		else {
			loc = loc.getBlock().getRelative(getDirection(loc), plugin.getServer().getViewDistance() * 16)
					.getLocation();
		}

		return loc;
	}

	private static BlockFace getDirection(Location loc) {

		float dir = Math.round(loc.getYaw() / 90.0F);
		if ((dir == -4.0F) || (dir == 0.0F) || (dir == 4.0F))
			return BlockFace.SOUTH;
		if ((dir == -1.0F) || (dir == 3.0F))
			return BlockFace.EAST;
		if ((dir == -2.0F) || (dir == 2.0F))
			return BlockFace.NORTH;
		if ((dir == -3.0F) || (dir == 1.0F))
			return BlockFace.WEST;
		return null;
	}

	
	



	
}