package net.eduard.api.barapi;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class FakeDragon1_6 extends FakeDragon {

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
