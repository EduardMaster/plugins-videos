package net.eduard.api.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import net.eduard.api.util.TitleType;

public final class TitleAPI extends RexAPI {

	public static void send(Player player, TitleType type, String message, int fadeIn, int stay, int fadeOut) {
		try {
			// PacketPlayOutTitle
			Object style = getValue(Rex.ENUM_TITLE_ACTION, type.name());
			Object text = getIChatBaseComponentI(message);
			Object packet = getNew(Rex.PLAY_OUT_TITLE, getParameters(style, Rex.ICHAT_BASE_COMPONENT), style,
					text);
			setValue(packet, "c", fadeIn);
			setValue(packet, "d", stay);
			setValue(packet, "e", fadeOut);
			RexAPI.sendPacket(packet, player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}



	public void sendActionBar(Player p, String msg) {
		if (msg == null) {
			msg = "";
		}
		try {
			Object ab = getMine("ChatComponentText").getConstructor(new Class[] { String.class })
					.newInstance(new Object[] { msg });
			Constructor<?> ac = getMine("PacketPlayOutChat")
					.getConstructor(new Class[] { getMine("IChatBaseComponent"), Byte.TYPE });
			Object abPacket = ac.newInstance(new Object[] { ab, Byte.valueOf((byte) 2) });
			
			sendPacket(abPacket, p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Deprecated
	public void sendOldActionBar(Player p, String msg) {
		if (msg == null) {
			msg = "";
		}
		try {
			Object ab = getMine("IChatBaseComponent").getDeclaredClasses()[0]
					.getMethod("a", new Class[] { String.class })
					.invoke(null, new Object[] { "{\"text\": \"" + msg + "\"}" });
			Constructor<?> cons = getMine("PacketPlayOutChat")
					.getConstructor(new Class[] { getMine("IChatBaseComponent"), Byte.TYPE });
			Object packet = cons.newInstance(new Object[] { ab, (short) 2 });
			sendPacket(p, packet);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Deprecated
	public static void sendAction(Player player, String message) {
		boolean useOldMethods = false;

		try {
			String nmsver = getVersion();
			Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
			Object p = c1.cast(player);

			Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
			Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");
			Object ppoc;
			if (useOldMethods) {
				Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatSerializer");
				Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
				Method m3 = c2.getDeclaredMethod("a", new Class[] { String.class });
				Object cbc = c3.cast(m3.invoke(c2, new Object[] { "{\"text\": \"" + message + "\"}" }));
				
				ppoc = c4.getConstructor(new Class[] { c3, Byte.TYPE }).newInstance(new Object[] { cbc, (short) 2 });
			} else {
				Class<?> c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
				Class<?> c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
				Object o = c2.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
				ppoc = c4.getConstructor(new Class[] { c3, Byte.TYPE }).newInstance(new Object[] { o, (short) 2 });
			}
			Method m1 = c1.getDeclaredMethod("getHandle", new Class[0]);
			Object h = m1.invoke(p, new Object[0]);
			Field f1 = h.getClass().getDeclaredField("playerConnection");
			Object pc = f1.get(h);
			Method m5 = pc.getClass().getDeclaredMethod("sendPacket", new Class[] { c5 });
			m5.invoke(pc, new Object[] { ppoc });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
