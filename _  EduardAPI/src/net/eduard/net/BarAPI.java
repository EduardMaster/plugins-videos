
package net.eduard.net;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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

import net.eduard.api.API;

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
			for (Player player : API.getPlayers()) {
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

	public static class BarUtils {
		public static boolean newProtocol = false;
		public static String version;
		public static Class<?> fakeDragonClass = FakeDragon1_6.class;
		public static boolean isBelowGround = true;

		public BarUtils() {
			super();
		}

		public static void detectVersion() {
			if (BarAPI.useSpigotHack()) {
				fakeDragonClass = FakeDragon1_8Fake.class;
				version = "v1_7_R4.";
				isBelowGround = false;
			} else {
				String name = Bukkit.getServer().getClass().getPackage().getName();
				String mcVersion = name.substring(name.lastIndexOf(46) + 1);
				String[] versions = mcVersion.split("_");

				if (versions[0].equals("v1")) {
					int minor = Integer.parseInt(versions[1]);

					if (minor == 7) {
						newProtocol = true;
						fakeDragonClass = FakeDragon1_7.class;
					} else if (minor == 8) {
						fakeDragonClass = FakeDragon1_8.class;
						isBelowGround = false;
					}
				}

				version = mcVersion + ".";
			}
		}

		public static FakeDragon newDragon(String message, Location loc) {
			FakeDragon fakeDragon = null;
			try {
				fakeDragon = (FakeDragon) fakeDragonClass.getConstructor(new Class[] { String.class, Location.class })
						.newInstance(new Object[] { message, loc });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return fakeDragon;
		}

		public static void sendPacket(Player p, Object packet) {
			try {
				Object nmsPlayer = getHandle(p);
				Field con_field = nmsPlayer.getClass().getField("playerConnection");
				Object con = con_field.get(nmsPlayer);
				Method packet_method = getMethod(con.getClass(), "sendPacket");
				packet_method.invoke(con, new Object[] { packet });
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}

		public static Class<?> getCraftClass(String ClassName) {
			String className = "net.minecraft.server." + version + ClassName;
			Class<?> c = null;
			try {
				c = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return c;
		}

		public static Object getHandle(World world) {
			Object nms_entity = null;
			Method entity_getHandle = getMethod(world.getClass(), "getHandle");
			try {
				nms_entity = entity_getHandle.invoke(world, new Object[0]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return nms_entity;
		}

		public static Object getHandle(Entity entity) {
			Object nms_entity = null;
			Method entity_getHandle = getMethod(entity.getClass(), "getHandle");
			try {
				nms_entity = entity_getHandle.invoke(entity, new Object[0]);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return nms_entity;
		}

		public static Field getField(Class<?> cl, String field_name) {
			try {
				return cl.getDeclaredField(field_name);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			return null;
		}

		public static Method getMethod(Class<?> cl, String method, Class<?>[] args) {
			for (Method m : cl.getMethods()) {
				if ((m.getName().equals(method)) && (ClassListEqual(args, m.getParameterTypes()))) {
					return m;
				}
			}
			return null;
		}

		public static Method getMethod(Class<?> cl, String method, Integer args) {
			for (Method m : cl.getMethods()) {
				if ((m.getName().equals(method)) && (args.equals(new Integer(m.getParameterTypes().length)))) {
					return m;
				}
			}
			return null;
		}

		public static Method getMethod(Class<?> cl, String method) {
			for (Method m : cl.getMethods()) {
				if (m.getName().equals(method)) {
					return m;
				}
			}
			return null;
		}

		public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
			boolean equal = true;

			if (l1.length != l2.length)
				return false;
			for (int i = 0; i < l1.length; i++) {
				if (l1[i] != l2[i]) {
					equal = false;
					break;
				}
			}

			return equal;
		}

		static {
			detectVersion();
		}
	}

	public static abstract class FakeDragon {
		private float maxHealth = 200.0F;
		private int x;
		private int y;
		private int z;
		private int pitch = 0;
		private int yaw = 0;
		private byte xvel = 0;
		private byte yvel = 0;
		private byte zvel = 0;
		public float health = 0.0F;
		private boolean visible = false;
		public String name;
		private Object world;

		public FakeDragon(String name, Location loc, int percent) {
			this.name = name;
			this.x = loc.getBlockX();
			this.y = loc.getBlockY();
			this.z = loc.getBlockZ();
			this.health = (percent / 100.0F * this.maxHealth);
			this.world = BarUtils.getHandle(loc.getWorld());
		}

		public FakeDragon(String name, Location loc) {
			this.name = name;
			this.x = loc.getBlockX();
			this.y = loc.getBlockY();
			this.z = loc.getBlockZ();
			this.world = BarUtils.getHandle(loc.getWorld());
		}

		public float getMaxHealth() {
			return this.maxHealth;
		}

		public void setHealth(int percent) {
			this.health = (percent / 100.0F * this.maxHealth);
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getX() {
			return this.x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return this.y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getZ() {
			return this.z;
		}

		public void setZ(int z) {
			this.z = z;
		}

		public int getPitch() {
			return this.pitch;
		}

		public void setPitch(int pitch) {
			this.pitch = pitch;
		}

		public int getYaw() {
			return this.yaw;
		}

		public void setYaw(int yaw) {
			this.yaw = yaw;
		}

		public byte getXvel() {
			return this.xvel;
		}

		public void setXvel(byte xvel) {
			this.xvel = xvel;
		}

		public byte getYvel() {
			return this.yvel;
		}

		public void setYvel(byte yvel) {
			this.yvel = yvel;
		}

		public byte getZvel() {
			return this.zvel;
		}

		public void setZvel(byte zvel) {
			this.zvel = zvel;
		}

		public boolean isVisible() {
			return this.visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public Object getWorld() {
			return this.world;
		}

		public void setWorld(Object world) {
			this.world = world;
		}

		public void setMaxHealth(float max) {
			this.maxHealth = max;
		}

		public abstract Object getSpawnPacket();

		public abstract Object getDestroyPacket();

		public abstract Object getMetaPacket(Object paramObject);

		public abstract Object getTeleportPacket(Location paramLocation);

		public abstract Object getWatcher();
	}

	private static class FakeDragon1_6 extends FakeDragon {

		private static final Integer EntityID = 6000;

		public FakeDragon1_6(String name, Location loc) {
			super(name, loc);
		}

		@SuppressWarnings("deprecation")
		public Object getSpawnPacket() {

			Class<?> mob_class = BarUtils.getCraftClass("Packet24MobSpawn");
			Object mobPacket = null;
			try {
				mobPacket = mob_class.newInstance();

				Field a = BarUtils.getField(mob_class, "a");
				a.setAccessible(true);
				a.set(mobPacket, FakeDragon1_6.EntityID);
				Field b = BarUtils.getField(mob_class, "b");
				b.setAccessible(true);
				b.set(mobPacket, Short.valueOf(EntityType.ENDER_DRAGON.getTypeId()));

				Field c = BarUtils.getField(mob_class, "c");
				c.setAccessible(true);
				c.set(mobPacket, Integer.valueOf(getX()));
				Field d = BarUtils.getField(mob_class, "d");
				d.setAccessible(true);
				d.set(mobPacket, Integer.valueOf(getY()));
				Field e = BarUtils.getField(mob_class, "e");
				e.setAccessible(true);
				e.set(mobPacket, Integer.valueOf(getZ()));
				Field f = BarUtils.getField(mob_class, "f");
				f.setAccessible(true);
				f.set(mobPacket, Byte.valueOf((byte) (int) (getPitch() * 256.0F / 360.0F)));
				Field g = BarUtils.getField(mob_class, "g");
				g.setAccessible(true);
				g.set(mobPacket, Byte.valueOf((byte) 0));

				Field h = BarUtils.getField(mob_class, "h");
				h.setAccessible(true);
				h.set(mobPacket, Byte.valueOf((byte) (int) (getYaw() * 256.0F / 360.0F)));
				Field i = BarUtils.getField(mob_class, "i");
				i.setAccessible(true);
				i.set(mobPacket, Byte.valueOf(getXvel()));
				Field j = BarUtils.getField(mob_class, "j");
				j.setAccessible(true);
				j.set(mobPacket, Byte.valueOf(getYvel()));
				Field k = BarUtils.getField(mob_class, "k");
				k.setAccessible(true);
				k.set(mobPacket, Byte.valueOf(getZvel()));

				Object watcher = getWatcher();
				Field t = BarUtils.getField(mob_class, "t");
				t.setAccessible(true);
				t.set(mobPacket, watcher);
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}

			return mobPacket;
		}

		public Object getDestroyPacket() {

			Class<?> packet_class = BarUtils.getCraftClass("Packet29DestroyEntity");
			Object packet = null;
			try {
				packet = packet_class.newInstance();

				Field a = BarUtils.getField(packet_class, "a");
				a.setAccessible(true);
				a.set(packet, new int[] { FakeDragon1_6.EntityID.intValue() });
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getMetaPacket(Object watcher) {

			Class<?> packet_class = BarUtils.getCraftClass("Packet40EntityMetadata");
			Object packet = null;
			try {
				packet = packet_class.newInstance();

				Field a = BarUtils.getField(packet_class, "a");
				a.setAccessible(true);
				a.set(packet, FakeDragon1_6.EntityID);

				Method watcher_c = BarUtils.getMethod(watcher.getClass(), "c");
				Field b = BarUtils.getField(packet_class, "b");
				b.setAccessible(true);
				b.set(packet, watcher_c.invoke(watcher, new Object[0]));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getTeleportPacket(Location loc) {

			Class<?> packet_class = BarUtils.getCraftClass("Packet34EntityTeleport");
			Object packet = null;
			try {
				packet = packet_class.newInstance();

				Field a = BarUtils.getField(packet_class, "a");
				a.setAccessible(true);
				a.set(packet, FakeDragon1_6.EntityID);
				Field b = BarUtils.getField(packet_class, "b");
				b.setAccessible(true);
				b.set(packet, Integer.valueOf((int) Math.floor(loc.getX() * 32.0D)));
				Field c = BarUtils.getField(packet_class, "c");
				c.setAccessible(true);
				c.set(packet, Integer.valueOf((int) Math.floor(loc.getY() * 32.0D)));
				Field d = BarUtils.getField(packet_class, "d");
				d.setAccessible(true);
				d.set(packet, Integer.valueOf((int) Math.floor(loc.getZ() * 32.0D)));
				Field e = BarUtils.getField(packet_class, "e");
				e.setAccessible(true);
				e.set(packet, Byte.valueOf((byte) (int) (loc.getYaw() * 256.0F / 360.0F)));
				Field f = BarUtils.getField(packet_class, "f");
				f.setAccessible(true);
				f.set(packet, Byte.valueOf((byte) (int) (loc.getPitch() * 256.0F / 360.0F)));
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return packet;
		}

		public Object getWatcher() {

			Class<?> watcher_class = BarUtils.getCraftClass("DataWatcher");
			Object watcher = null;
			try {
				watcher = watcher_class.newInstance();

				Method a = BarUtils.getMethod(watcher_class, "a", new Class[] { Integer.TYPE, Object.class });
				a.setAccessible(true);

				a.invoke(watcher, new Object[] { Integer.valueOf(0), Byte.valueOf((byte) (isVisible() ? 0 : 32)) });
				a.invoke(watcher, new Object[] { Integer.valueOf(6), Float.valueOf(this.health) });
				a.invoke(watcher, new Object[] { Integer.valueOf(7), Integer.valueOf(0) });
				a.invoke(watcher, new Object[] { Integer.valueOf(8), Byte.valueOf((byte) 0) });
				a.invoke(watcher, new Object[] { Integer.valueOf(10), this.name });
				a.invoke(watcher, new Object[] { Integer.valueOf(11), Byte.valueOf((byte) 1) });
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

			return watcher;
		}

	}

	private static class FakeDragon1_7 extends FakeDragon {

		private Object dragon;

		private int id;

		public FakeDragon1_7(String name, Location loc) {
			super(name, loc);
		}

		public Object getSpawnPacket() {

			Class<?> Entity = BarUtils.getCraftClass("Entity");
			Class<?> EntityLiving = BarUtils.getCraftClass("EntityLiving");
			Class<?> EntityEnderDragon = BarUtils.getCraftClass("EntityEnderDragon");
			Object packet = null;
			try {
				this.dragon = EntityEnderDragon.getConstructor(new Class[] { BarUtils.getCraftClass("World") })
						.newInstance(new Object[] { getWorld() });

				Method setLocation = BarUtils.getMethod(EntityEnderDragon, "setLocation",
						new Class[] { Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE });
				setLocation.invoke(this.dragon, new Object[] { Integer.valueOf(getX()), Integer.valueOf(getY()),
						Integer.valueOf(getZ()), Integer.valueOf(getPitch()), Integer.valueOf(getYaw()) });

				Method setInvisible = BarUtils.getMethod(EntityEnderDragon, "setInvisible",
						new Class[] { Boolean.TYPE });
				setInvisible.invoke(this.dragon, new Object[] { Boolean.valueOf(isVisible()) });

				Method setCustomName = BarUtils.getMethod(EntityEnderDragon, "setCustomName",
						new Class[] { String.class });
				setCustomName.invoke(this.dragon, new Object[] { this.name });

				Method setHealth = BarUtils.getMethod(EntityEnderDragon, "setHealth", new Class[] { Float.TYPE });
				setHealth.invoke(this.dragon, new Object[] { Float.valueOf(this.health) });

				Field motX = BarUtils.getField(Entity, "motX");
				motX.set(this.dragon, Byte.valueOf(getXvel()));

				Field motY = BarUtils.getField(Entity, "motY");
				motY.set(this.dragon, Byte.valueOf(getYvel()));

				Field motZ = BarUtils.getField(Entity, "motZ");
				motZ.set(this.dragon, Byte.valueOf(getZvel()));

				Method getId = BarUtils.getMethod(EntityEnderDragon, "getId", new Class[0]);
				this.id = ((Integer) getId.invoke(this.dragon, new Object[0])).intValue();

				Class<?> PacketPlayOutSpawnEntityLiving = BarUtils.getCraftClass("PacketPlayOutSpawnEntityLiving");

				packet = PacketPlayOutSpawnEntityLiving.getConstructor(new Class[] { EntityLiving })
						.newInstance(new Object[] { this.dragon });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getDestroyPacket() {

			Class<?> PacketPlayOutEntityDestroy = BarUtils.getCraftClass("PacketPlayOutEntityDestroy");

			Object packet = null;
			try {
				packet = PacketPlayOutEntityDestroy.newInstance();
				Field a = PacketPlayOutEntityDestroy.getDeclaredField("a");
				a.setAccessible(true);
				a.set(packet, new int[] { this.id });
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getMetaPacket(Object watcher) {

			Class<?> DataWatcher = BarUtils.getCraftClass("DataWatcher");

			Class<?> PacketPlayOutEntityMetadata = BarUtils.getCraftClass("PacketPlayOutEntityMetadata");

			Object packet = null;
			try {
				packet = PacketPlayOutEntityMetadata
						.getConstructor(new Class[] { Integer.TYPE, DataWatcher, Boolean.TYPE })
						.newInstance(new Object[] { Integer.valueOf(this.id), watcher, Boolean.valueOf(true) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getTeleportPacket(Location loc) {

			Class<?> PacketPlayOutEntityTeleport = BarUtils.getCraftClass("PacketPlayOutEntityTeleport");

			Object packet = null;
			try {
				packet = PacketPlayOutEntityTeleport.getConstructor(
						new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Byte.TYPE, Byte.TYPE })
						.newInstance(new Object[] { Integer.valueOf(this.id), Integer.valueOf(loc.getBlockX() * 32),
								Integer.valueOf(loc.getBlockY() * 32), Integer.valueOf(loc.getBlockZ() * 32),
								Byte.valueOf((byte) ((int) loc.getYaw() * 256 / 360)),
								Byte.valueOf((byte) ((int) loc.getPitch() * 256 / 360)) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getWatcher() {

			Class<?> Entity = BarUtils.getCraftClass("Entity");
			Class<?> DataWatcher = BarUtils.getCraftClass("DataWatcher");

			Object watcher = null;
			try {
				watcher = DataWatcher.getConstructor(new Class[] { Entity }).newInstance(new Object[] { this.dragon });
				Method a = BarUtils.getMethod(DataWatcher, "a", new Class[] { Integer.TYPE, Object.class });

				a.invoke(watcher, new Object[] { Integer.valueOf(0), Byte.valueOf((byte) (isVisible() ? 0 : 32)) });
				a.invoke(watcher, new Object[] { Integer.valueOf(6), Float.valueOf(this.health) });
				a.invoke(watcher, new Object[] { Integer.valueOf(7), Integer.valueOf(0) });
				a.invoke(watcher, new Object[] { Integer.valueOf(8), Byte.valueOf((byte) 0) });
				a.invoke(watcher, new Object[] { Integer.valueOf(10), this.name });
				a.invoke(watcher, new Object[] { Integer.valueOf(11), Byte.valueOf((byte) 1) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			return watcher;
		}
	}

	private static class FakeDragon1_8 extends FakeDragon {
		private Object dragon;
		private int id;

		public FakeDragon1_8(String name, Location loc) {
			super(name, loc);
		}

		public Object getSpawnPacket() {
			Class<?> Entity = BarUtils.getCraftClass("Entity");
			Class<?> EntityLiving = BarUtils.getCraftClass("EntityLiving");
			Class<?> EntityEnderDragon = BarUtils.getCraftClass("EntityEnderDragon");
			Object packet = null;
			try {
				this.dragon = EntityEnderDragon.getConstructor(new Class[] { BarUtils.getCraftClass("World") })
						.newInstance(new Object[] { getWorld() });

				Method setLocation = BarUtils.getMethod(EntityEnderDragon, "setLocation",
						new Class[] { Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE });
				setLocation.invoke(this.dragon, new Object[] { Integer.valueOf(getX()), Integer.valueOf(getY()),
						Integer.valueOf(getZ()), Integer.valueOf(getPitch()), Integer.valueOf(getYaw()) });

				Method setInvisible = BarUtils.getMethod(EntityEnderDragon, "setInvisible",
						new Class[] { Boolean.TYPE });
				setInvisible.invoke(this.dragon, new Object[] { Boolean.valueOf(true) });

				Method setCustomName = BarUtils.getMethod(EntityEnderDragon, "setCustomName",
						new Class[] { String.class });
				setCustomName.invoke(this.dragon, new Object[] { this.name });

				Method setHealth = BarUtils.getMethod(EntityEnderDragon, "setHealth", new Class[] { Float.TYPE });
				setHealth.invoke(this.dragon, new Object[] { Float.valueOf(this.health) });

				Field motX = BarUtils.getField(Entity, "motX");
				motX.set(this.dragon, Byte.valueOf(getXvel()));

				Field motY = BarUtils.getField(Entity, "motY");
				motY.set(this.dragon, Byte.valueOf(getYvel()));

				Field motZ = BarUtils.getField(Entity, "motZ");
				motZ.set(this.dragon, Byte.valueOf(getZvel()));

				Method getId = BarUtils.getMethod(EntityEnderDragon, "getId", new Class[0]);
				this.id = ((Integer) getId.invoke(this.dragon, new Object[0])).intValue();

				Class<?> PacketPlayOutSpawnEntityLiving = BarUtils.getCraftClass("PacketPlayOutSpawnEntityLiving");

				packet = PacketPlayOutSpawnEntityLiving.getConstructor(new Class[] { EntityLiving })
						.newInstance(new Object[] { this.dragon });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getDestroyPacket() {
			Class<?> PacketPlayOutEntityDestroy = BarUtils.getCraftClass("PacketPlayOutEntityDestroy");

			Object packet = null;
			try {
				packet = PacketPlayOutEntityDestroy.newInstance();
				Field a = PacketPlayOutEntityDestroy.getDeclaredField("a");
				a.setAccessible(true);
				a.set(packet, new int[] { this.id });
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getMetaPacket(Object watcher) {
			Class<?> DataWatcher = BarUtils.getCraftClass("DataWatcher");

			Class<?> PacketPlayOutEntityMetadata = BarUtils.getCraftClass("PacketPlayOutEntityMetadata");

			Object packet = null;
			try {
				packet = PacketPlayOutEntityMetadata
						.getConstructor(new Class[] { Integer.TYPE, DataWatcher, Boolean.TYPE })
						.newInstance(new Object[] { Integer.valueOf(this.id), watcher, Boolean.valueOf(true) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getTeleportPacket(Location loc) {
			Class<?> PacketPlayOutEntityTeleport = BarUtils.getCraftClass("PacketPlayOutEntityTeleport");
			Object packet = null;
			try {
				packet = PacketPlayOutEntityTeleport
						.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Byte.TYPE,
								Byte.TYPE, Boolean.TYPE })
						.newInstance(new Object[] { Integer.valueOf(this.id), Integer.valueOf(loc.getBlockX() * 32),
								Integer.valueOf(loc.getBlockY() * 32), Integer.valueOf(loc.getBlockZ() * 32),
								Byte.valueOf((byte) ((int) loc.getYaw() * 256 / 360)),
								Byte.valueOf((byte) ((int) loc.getPitch() * 256 / 360)), Boolean.valueOf(false) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getWatcher() {
			Class<?> Entity = BarUtils.getCraftClass("Entity");
			Class<?> DataWatcher = BarUtils.getCraftClass("DataWatcher");

			Object watcher = null;
			try {
				watcher = DataWatcher.getConstructor(new Class[] { Entity }).newInstance(new Object[] { this.dragon });
				Method a = BarUtils.getMethod(DataWatcher, "a", new Class[] { Integer.TYPE, Object.class });

				a.invoke(watcher, new Object[] { Integer.valueOf(5), Byte.valueOf((byte) (isVisible() ? 0 : 32)) });
				a.invoke(watcher, new Object[] { Integer.valueOf(6), Float.valueOf(this.health) });
				a.invoke(watcher, new Object[] { Integer.valueOf(7), Integer.valueOf(0) });
				a.invoke(watcher, new Object[] { Integer.valueOf(8), Byte.valueOf((byte) 0) });
				a.invoke(watcher, new Object[] { Integer.valueOf(10), this.name });
				a.invoke(watcher, new Object[] { Integer.valueOf(11), Byte.valueOf((byte) 1) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return watcher;
		}
	}

	private static class FakeDragon1_8Fake extends FakeDragon {
		private Object dragon;
		private int id;

		public FakeDragon1_8Fake(String name, Location loc) {
			super(name, loc);
		}

		public Object getSpawnPacket() {
			Class<?> Entity = BarUtils.getCraftClass("Entity");
			Class<?> EntityLiving = BarUtils.getCraftClass("EntityLiving");
			Class<?> EntityEnderDragon = BarUtils.getCraftClass("EntityEnderDragon");
			Object packet = null;
			try {
				this.dragon = EntityEnderDragon.getConstructor(new Class[] { BarUtils.getCraftClass("World") })
						.newInstance(new Object[] { getWorld() });

				Method setLocation = BarUtils.getMethod(EntityEnderDragon, "setLocation",
						new Class[] { Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE });
				setLocation.invoke(this.dragon, new Object[] { Integer.valueOf(getX()), Integer.valueOf(getY()),
						Integer.valueOf(getZ()), Integer.valueOf(getPitch()), Integer.valueOf(getYaw()) });

				Method setInvisible = BarUtils.getMethod(EntityEnderDragon, "setInvisible",
						new Class[] { Boolean.TYPE });
				setInvisible.invoke(this.dragon, new Object[] { Boolean.valueOf(true) });

				Method setCustomName = BarUtils.getMethod(EntityEnderDragon, "setCustomName",
						new Class[] { String.class });
				setCustomName.invoke(this.dragon, new Object[] { this.name });

				Method setHealth = BarUtils.getMethod(EntityEnderDragon, "setHealth", new Class[] { Float.TYPE });
				setHealth.invoke(this.dragon, new Object[] { Float.valueOf(this.health) });

				Field motX = BarUtils.getField(Entity, "motX");
				motX.set(this.dragon, Byte.valueOf(getXvel()));

				Field motY = BarUtils.getField(Entity, "motY");
				motY.set(this.dragon, Byte.valueOf(getYvel()));

				Field motZ = BarUtils.getField(Entity, "motZ");
				motZ.set(this.dragon, Byte.valueOf(getZvel()));

				Method getId = BarUtils.getMethod(EntityEnderDragon, "getId", new Class[0]);
				this.id = ((Integer) getId.invoke(this.dragon, new Object[0])).intValue();

				Class<?> PacketPlayOutSpawnEntityLiving = BarUtils.getCraftClass("PacketPlayOutSpawnEntityLiving");

				packet = PacketPlayOutSpawnEntityLiving.getConstructor(new Class[] { EntityLiving })
						.newInstance(new Object[] { this.dragon });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getDestroyPacket() {
			Class<?> PacketPlayOutEntityDestroy = BarUtils.getCraftClass("PacketPlayOutEntityDestroy");

			Object packet = null;
			try {
				packet = PacketPlayOutEntityDestroy.newInstance();
				Field a = PacketPlayOutEntityDestroy.getDeclaredField("a");
				a.setAccessible(true);
				a.set(packet, new int[] { this.id });
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getMetaPacket(Object watcher) {
			Class<?> DataWatcher = BarUtils.getCraftClass("DataWatcher");

			Class<?> PacketPlayOutEntityMetadata = BarUtils.getCraftClass("PacketPlayOutEntityMetadata");

			Object packet = null;
			try {
				packet = PacketPlayOutEntityMetadata
						.getConstructor(new Class[] { Integer.TYPE, DataWatcher, Boolean.TYPE })
						.newInstance(new Object[] { Integer.valueOf(this.id), watcher, Boolean.valueOf(true) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getTeleportPacket(Location loc) {
			Class<?> PacketPlayOutEntityTeleport = BarUtils.getCraftClass("PacketPlayOutEntityTeleport");
			Object packet = null;
			try {
				packet = PacketPlayOutEntityTeleport
						.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Byte.TYPE,
								Byte.TYPE, Boolean.TYPE, Boolean.TYPE })
						.newInstance(new Object[] { Integer.valueOf(this.id), Integer.valueOf(loc.getBlockX() * 32),
								Integer.valueOf(loc.getBlockY() * 32), Integer.valueOf(loc.getBlockZ() * 32),
								Byte.valueOf((byte) ((int) loc.getYaw() * 256 / 360)),
								Byte.valueOf((byte) ((int) loc.getPitch() * 256 / 360)), Boolean.valueOf(false),
								Boolean.valueOf(false) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			return packet;
		}

		public Object getWatcher() {
			Class<?> Entity = BarUtils.getCraftClass("Entity");
			Class<?> DataWatcher = BarUtils.getCraftClass("DataWatcher");

			Object watcher = null;
			try {
				watcher = DataWatcher.getConstructor(new Class[] { Entity }).newInstance(new Object[] { this.dragon });
				Method a = BarUtils.getMethod(DataWatcher, "a", new Class[] { Integer.TYPE, Object.class });

				a.invoke(watcher, new Object[] { Integer.valueOf(5), Byte.valueOf((byte) (isVisible() ? 0 : 32)) });
				a.invoke(watcher, new Object[] { Integer.valueOf(6), Float.valueOf(this.health) });
				a.invoke(watcher, new Object[] { Integer.valueOf(7), Integer.valueOf(0) });
				a.invoke(watcher, new Object[] { Integer.valueOf(8), Byte.valueOf((byte) 0) });
				a.invoke(watcher, new Object[] { Integer.valueOf(10), this.name });
				a.invoke(watcher, new Object[] { Integer.valueOf(11), Byte.valueOf((byte) 1) });
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			return watcher;
		}

		public static boolean isUsable() {
			Class<?> PacketPlayOutEntityTeleport = BarUtils.getCraftClass("PacketPlayOutEntityTeleport");
			try {
				PacketPlayOutEntityTeleport.getConstructor(new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE,
						Integer.TYPE, Byte.TYPE, Byte.TYPE, Boolean.TYPE, Boolean.TYPE });
			} catch (IllegalArgumentException e) {
				return false;
			} catch (SecurityException e) {
				return false;
			} catch (NoSuchMethodException e) {
				return false;
			}

			return true;
		}
	}
}