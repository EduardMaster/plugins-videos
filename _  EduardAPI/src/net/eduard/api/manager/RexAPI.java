package net.eduard.api.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public abstract class RexAPI {

	public static void sendPacket(Player player, Object packet) throws Exception {
		sendPacket(packet, player);
	}

	public static void sendPackets(Object packet, Player p) {
		for (Player player : PlayerAPI.getPlayers()) {
			if (p == player)
				continue;
			try {
				sendPacket(packet, p);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void sendPackets(Object packet) {
		for (Player p : PlayerAPI.getPlayers()) {
			try {
				sendPacket(packet, p);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String getIChatBaseComponentText(String text) {
		return ("{\"text\":\"" + text + "\"}");

	}

	public static Object getIChatBaseComponentI(String text) throws Exception {
		return getResult(Rex.CHAT_SERIALIZER, "a", getIChatBaseComponentText(text));
	}
	public static Object getIChatBaseComponent(String text) throws Exception {
		return getResult(Rex.CHAT_SERIALIZER, "a", text);
	}

	public static Object getHandle(Player player) throws Exception {
		return getResult(player, "getHandle");
	}

	public static Object getConnection(Player player) throws Exception {
		return getValue(getHandle(player), "playerConnection");
	}

	public static Class<?> getMine(String name) throws Exception {
		return getClass(RexType.SERVER.getName() + getVersion() + "." + name);
	}

	public static Class<?> getCraft(String name) throws Exception {
		return getClass(RexType.CRAFT.getName() + getVersion() + "." + name);
	}

	public static void sendPacket(Object packet, Player player) throws Exception {

		getResult(getConnection(player), "sendPacket", getParameters(Rex.PACKET), packet);
	}

	public static Class<?> getClass(Object object) throws Exception {

		if (object instanceof Class) {
			return (Class<?>) object;
		}

		if (object instanceof Rex) {
			return ((Rex) object).getRex();
		}
		return object.getClass();
	}

	public static Object getResult(Object object, String name, Object... objects) throws Exception {
		Method method = getMethod(object, name, objects);
		method.setAccessible(true);
		return method.invoke(object, objects);
	}

	public static Object getNew(Object object, Object... objects) throws Exception {
		Constructor<?> constructor = getConstructor(object, objects);
		constructor.setAccessible(true);
		return constructor.newInstance(objects);
	}

	public static Object getNew(Object object, Object[] parameters, Object... objects) throws Exception {
		Constructor<?> constructor = getConstructor(object, parameters);
		constructor.setAccessible(true);
		return constructor.newInstance(objects);
	}

	public static Constructor<?> getConstructor(Object object, Object... objects) throws Exception {
		try {
			return getClass(object).getDeclaredConstructor(getParameters(objects));
		} catch (Exception ex) {
			return getClass(object).getConstructor(getParameters(objects));
		}
	}

	public static Object getResult(Object object, String name, Object[] parameters, Object... objects)
			throws Exception {
		Method method = getMethod(object, name, parameters);
		method.setAccessible(true);
		return method.invoke(object, objects);
	}

	public static Object getValue(Object object, String name) throws Exception {
		Field field = getField(object, name);
		field.setAccessible(true);
		return field.get(object);
	}

	public static void setValue(Object object, String name, Object value) throws Exception {
		Field field = getField(object, name);
		field.setAccessible(true);
		field.set(object, value);
	}

	public static Field getField(Object object, String name) throws Exception {
		try {
			return getClass(object).getDeclaredField(name);
		} catch (Exception ex) {
			return getClass(object).getField(name);
		}
	}

	public static Method getMethod(Object object, String name, Object... objects) throws Exception {
		try {
			return getClass(object).getDeclaredMethod(name, getParameters(objects));

		} catch (Exception ex) {
			return getClass(object).getMethod(name, getParameters(objects));
		}
	}

	public static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().replace('.', ',').split(",")[3];
	}

	public static String getVersion2() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\")[3];
	}

	public static Class<?> getClass(String name) throws Exception {
		return Class.forName(name);
	}

	public static Class<?>[] getParameters(Object... objects) throws Exception {
		Class<?>[] parameters = new Class[objects.length];
		int index = 0;
		for (Object parameter : objects) {
			parameters[index] = getClass(parameter);
			index++;
		}
		return parameters;
	}

	public static enum RexType {
		BUKKIT("org.bukkit."), CRAFT("org.bukkit.craftbukkit."), SERVER("net.minecraft.server."), API(
				"net.eduard.api.");
		private RexType(String name) {
			this.name = name;
		}

		public String getName() {

			return name;
		}

		private String name;
	}

	public static enum Rex {
		ENUM_TITLE_ACTION("EnumTitleAction"), CHAT_SERIALIZER(
				"ChatSerializer"), PLAY_OUT_CHAT, PLAY_IN_CLIENT_COMMAND, PLAY_OUT_NAMED_ENTITY_SPAWN, PLAY_OUT_TITLE, PLAY_OUT_PLAYER_LIST_HEADER_FOOTER, PLAY_OUT_WORLD_PATICLES, MINE_ITEM(
						"ItemStack"), ENUM_CLIENT_COMMAND("EnumClientCommand"), CRAFT_ITEM("inventory.CraftItemStack",
								RexType.CRAFT), ICHAT_BASE_COMPONENT("IChatBaseComponent"), PACKET("Packet"), NBT_BASE(
										"NBTBase"), ENTITY_HUMAN("EntityHuman"), NBT_TAG_LIST(
												"NBTTagList"), NBT_TAG_COMPOUND("NBTTagCompound"),

		BUKKIT("Bukkit", RexType.BUKKIT, false),;
		private String name;

		private Rex() {
			name = RexType.SERVER.getName() + getVersion() + ".Packet" + API.toTitle(name(), "");
		}

		private Rex(String name) {
			this(name, RexType.SERVER);
		}

		private Rex(String name, RexType type) {
			this(name, type, true);

		}

		private Rex(String name, RexType type, boolean value) {
			if (value)
				this.name = type.getName() + getVersion() + "." + name;
			else
				this.name = type.getName() + name;
		}

		public Class<?> getRex() throws Exception {
			return RexAPI.getClass(name);
		}
	}

	public static void save(Section section, Save save) throws Exception {
		getResult(save, "save", getParameters(section, Object.class), section, save);
	}

	public static void get(Section section, Save save) throws Exception {
		getResult(save, "get", getParameters(section), section);
	}

}
