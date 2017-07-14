package net.eduard.api.manager;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import net.eduard.api.API;
import net.eduard.api.config.Config;
import net.eduard.api.config.Section;
import net.eduard.api.util.Cs;
import net.eduard.api.util.KeyType;
import sun.net.www.protocol.file.FileURLConnection;
/**
 * 
 * API de Reflection para Minecraft desenvolvida por Eduard <br>
 * Array = Vetor -> String[] ou String... <br>
 * Constructor"cons" = Iniciador -> public RexAPI(){}; <br>
 * Parameters = Parametros -> Class[] ou Object[]
 * 
 * @author Eduard
 *
 */
public class RexAPI {

	public static String mEntityPlayer = "#mEntityPlayer";
	public static String cCraftPlayer = "#cCraftPlayer";
	public static String sPacketTitle = "#sProtocolInjector$PacketTitle";
	public static String sAction = "#sProtocolInjector$PacketTitle$Action";
	public static String sPacketTabHeader = "#sProtocolInjector$PacketTabHeader";
	public static String pPlayOutChat = "#pPlayOutChat";
	public static String pPlayOutTitle = "#pPlayOutTitle";
	public static String pPlayOutWorldParticles = "#pPlayOutWorldParticles";
	public static String pPlayOutPlayerListHeaderFooter = "#pPlayOutPlayerListHeaderFooter";
	public static String pPlayOutNamedEntitySpawn = "#pPlayOutNamedEntitySpawn";
	public static String pPlayInClientCommand = "#pPlayInClientCommand";
	public static String cEnumTitleAction = "#cEnumTitleAction";
	public static String pEnumTitleAction2 = "#pPlayOutTitle$EnumTitleAction";
	public static String mEnumClientCommand = "#mEnumClientCommand";
	public static String mEnumClientCommand2 = "#pPlayInClientCommand$EnumClientCommand";
	public static String mChatSerializer = "#mChatSerializer";
	public static String mIChatBaseComponent = "#mIChatBaseComponent";
	public static String mEntityHuman = "#mEntityHuman";
	public static String mNBTTagCompound = "#mNBTTagCompound";
	public static String mNBTBase = "#mNBTBase";
	public static String mNBTTagList = "#mNBTTagList";
	public static String pPacket = "#p";
	public static String cItemStack = "#cinventory.CraftItemStack";
	public static String mItemStack = "#mItemStack";
	public static String bItemStack = "#bItemStack";
	public static String bBukkit = "#bBukkit";
	public static String mChatComponentText = "#mChatComponentText";
	public static String mMinecraftServer = "#mMinecraftServer";

	/**
	 * Envia o pacote para o jogador
	 * 
	 * @param player
	 *            Jogador
	 * @param packet
	 *            Pacote
	 * @throws Exception
	 */
	public static void sendPacket(Object packet, Player player)
			throws Exception {

		getResult(getConnection(player), "sendPacket", getParameters(pPacket),
				packet);
	}

	public static int getCurrentTick() throws Exception {
		return (int) RexAPI.getValue(RexAPI.mMinecraftServer, "currentTick");
	}

	public static Double getTPS() {
		try {
			return Double.valueOf(
					Math.min(20.0D, Math.round(getCurrentTick() * 10) / 10.0D));
		} catch (Exception e) {
		}

		return 0D;
	}

	/**
	 * Envia o pacote para o jogador
	 * 
	 * @param player
	 *            Jogador
	 * @param packet
	 *            Pacote
	 * @throws Exception
	 */
	public static void sendPacket(Player player, Object packet)
			throws Exception {
		sendPacket(packet, player);
	}

	/**
	 * Envia o pacote para todos menos para o jogador
	 * 
	 * @param packet
	 *            Pacote
	 * @param target
	 *            Jogador
	 */
	public static void sendPackets(Object packet, Player target) {
		for (Player player : getPlayers()) {
			if (target == player)
				continue;
			try {
				sendPacket(packet, target);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Envia o pacote para todos jogadores
	 * 
	 * @param packet
	 *            Pacote
	 */
	public static void sendPackets(Object packet) {
		for (Player p : getPlayers()) {
			try {
				sendPacket(packet, p);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @param text
	 *            Texto
	 * @return "{\"text\":\"" + text + "\"}"
	 */
	public static String getIComponentText(String text) {
		return ("{\"text\":\"" + text + "\"}");

	}

	/**
	 * Inicia um ChatComponentText"IChatBaseComponent" pelo cons(String) da
	 * classe ChatComponentText
	 * 
	 * @param text
	 *            Texto
	 * @return ChatComponentText iniciado
	 * 
	 */
	public static Object getIChatText2(String text) throws Exception {
		return getNew(mChatComponentText, text);

	}

	/**
	 * Inicia um IChatBaseComponent pelo metodo a(String) da classe
	 * ChatSerializer adicionando componente texto
	 * 
	 * @return IChatBaseComponent iniciado
	 * @param text
	 *            Texto
	 */
	public static Object getIChatText(String text) throws Exception {
		return getIChatBaseComponent(getIComponentText(text));
	}

	/**
	 * Inicia um IChatBaseComponent pelo metodo a(String) da classe
	 * ChatSerializer
	 * 
	 * @param component
	 *            Componente (Texto)
	 * @return IChatBaseComponent iniciado
	 */
	public static Object getIChatBaseComponent(String component)
			throws Exception {
		return getResult(mChatSerializer, "a", component);
	}

	/**
	 * @param player
	 *            Jogador (CraftPlayer)
	 * @return EntityPlayer pelo metodo getHandle da classe CraftPlayer(Player)
	 * @exception Exception
	 */
	public static Object getHandle(Player player) throws Exception {
		return getResult(player, "getHandle");
	}

	/**
	 * Retorna Um PlayerConnection pela variavel playerConnection da classe
	 * EntityPlayer <Br>
	 * Pega o EntityPlayer pelo metodo getHandle(player)
	 * 
	 * @param player
	 *            Jogador (CraftPlayer)
	 * @return Conexão do jogador
	 */
	public static Object getConnection(Player player) throws Exception {
		return getValue(getHandle(player), "playerConnection");
	}

	/**
	 * - Se for uma classe retorna a mesma<br>
	 * - Se for um String retorna uma classe baseada no Texto<Br>
	 * - Se for um objeto qualquer retorna object.getClass()<br>
	 * 
	 * @param object
	 *            Objeto
	 * @return Uma classe pelo objeto
	 * @throws Exception
	 */
	public static Class<?> getClass(Object object) throws Exception {
		if (object instanceof Class) {
			return (Class<?>) object;
		}
		if (object instanceof String) {
			String string = (String) object;
			if (string.startsWith("#")) {
				string = string.replace("#s", "org.spigotmc.");
				string = string.replace("#a", "net.eduard.api.");
				string = string.replace("#e", "net.eduard.eduardapi.");
				string = string.replace("#k", "net.eduard.api.kits.");
				string = string.replace("#p", "#mPacket");
				string = string.replace("#m", "net.minecraft.server.#v.");
				string = string.replace("#c", "org.bukkit.craftbukkit.#v.");
				string = string.replace("#b", "org.bukkit.");
				string = string.replace("#v", getVersion());
				// string = string.replace("#v2", getVersion2());

				return get(string);
			}

		}
		try {
			return (Class<?>) object.getClass().getField("TYPE").get(0);
		} catch (Exception e) {
		}
		return object.getClass();
	}
	/**
	 * Invoca um Metodo
	 * 
	 * @param object
	 *            Objeto
	 * @param name
	 *            Nome do Metodo
	 * @param objects
	 *            Parametros
	 * @return Valor do metodo
	 * @throws Exception
	 */
	public static Object getResult(Object object, String name,
			Object... objects) throws Exception {
		Method method = getMethod(object, name, objects);
		method.setAccessible(true);
		return method.invoke(object, objects);
	}
	/**
	 * Inicia um objeto
	 * 
	 * @param object
	 *            Classe
	 * @param objects
	 *            Parametros
	 * @return Novo Objeto iniciado
	 * @throws Exception
	 */
	public static Object getNew(Object object, Object... objects)
			throws Exception {
		Constructor<?> constructor = getConstructor(object, objects);
		constructor.setAccessible(true);
		return constructor.newInstance(objects);
	}
	/**
	 * Inicia um objeto
	 * 
	 * @param object
	 *            Classe
	 * @param parameters
	 *            Parametros
	 * @param objects
	 *            Valores
	 * @return Novo objeto iniciado
	 * @throws Exception
	 */
	public static Object getNew(Object object, Object[] parameters,
			Object... objects) throws Exception {
		Constructor<?> constructor = getConstructor(object, parameters);
		constructor.setAccessible(true);
		return constructor.newInstance(objects);
	}
	/**
	 * @param object
	 *            Classe
	 * @param objects
	 *            Parametros
	 * @return Construtor
	 * @throws Exception
	 */
	public static Constructor<?> getConstructor(Object object,
			Object... objects) throws Exception {
		try {
			return getClass(object)
					.getDeclaredConstructor(getParameters(objects));
		} catch (Exception ex) {
			return getClass(object).getConstructor(getParameters(objects));
		}
	}
	/**
	 * Invoca um Metodo
	 * 
	 * @param object
	 *            Classe
	 * @param name
	 *            Nome do Metodo
	 * @param parameters
	 *            Parametros
	 * @param objects
	 *            Valores
	 * @return Valor do metodo
	 * @throws Exception
	 */
	public static Object getResult(Object object, String name,
			Object[] parameters, Object... objects) throws Exception {
		Method method = getMethod(object, name, parameters);
		method.setAccessible(true);
		return method.invoke(object, objects);
	}
	/**
	 * @param object
	 *            Classe
	 * @param name
	 *            Nome da variavel
	 * @return Valor da variavel
	 * @throws Exception
	 */
	public static Object getValue(Object object, String name) throws Exception {
		Field field = getField(object, name);
		field.setAccessible(true);
		return field.get(object);
	}
	/**
	 * Modifica uma variavel
	 * 
	 * @param object
	 *            Classe
	 * @param name
	 *            Nome da Variavel
	 * @param value
	 *            Valor
	 * @throws Exception
	 */
	public static void setValue(Object object, String name, Object value)
			throws Exception {
		Field field = getField(object, name);
		field.setAccessible(true);
		field.set(object, value);
	}
	/**
	 * @param object
	 *            Classe
	 * @param name
	 *            Nome da variavel
	 * @return Uma Variavel
	 * @throws Exception
	 */
	public static Field getField(Object object, String name) throws Exception {
		try {
			return getClass(object).getDeclaredField(name);
		} catch (Exception ex) {
			return getClass(object).getField(name);
		}
	}
	/**
	 * @param object
	 *            Classe
	 * @param name
	 *            Nome do Metodo
	 * @param objects
	 *            Parametros
	 * @return Um Metodo
	 * @throws Exception
	 */
	public static Method getMethod(Object object, String name,
			Object... objects) throws Exception {
		try {
			return getClass(object).getDeclaredMethod(name,
					getParameters(objects));

		} catch (Exception ex) {
			return getClass(object).getMethod(name, getParameters(objects));
		}
	}
	/**
	 * 
	 * @return Versão do Servidor
	 */
	public static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName()
				.replace('.', ',').split(",")[3];
	}
	/**
	 * (Não funciona)
	 * 
	 * @return Versão do Servidor
	 */
	@Deprecated
	public static String getVersion2() {
		return Bukkit.getServer().getClass().getPackage().getName()
				.split("\\")[3];
	}

	/**
	 * @param name
	 *            Nome
	 * @return Uma classe baseada no Nome
	 * @throws Exception
	 */
	public static Class<?> get(String name) throws Exception {
		return Class.forName(name);
	}
	/**
	 * @param objects
	 *            Valores"Parametros"
	 * @return Uma Array de Classes"Parametros" pela Array de Objetos"Valores"
	 * @throws Exception
	 */
	public static Class<?>[] getParameters(Object... objects) throws Exception {
		Class<?>[] parameters = new Class[objects.length];
		int index = 0;
		for (Object parameter : objects) {
			parameters[index] = getClass(parameter);
			index++;
		}
		return parameters;
	}

	/**
	 * Modifica a TabList do Jogador
	 * 
	 * @param player
	 *            Jogador
	 * @param header
	 *            Cabeçalho
	 * @param footer
	 *            Rodapé
	 */
	public static void setTabList(Player player, String header, String footer) {
		try {
			if (isAbove1_8(player)) {
				Object packet = getNew(sPacketTabHeader,
						getParameters(mIChatBaseComponent, mIChatBaseComponent),
						getIChatText(header), getIChatText(footer));
				sendPacket(packet, player);
				return;
			}

		} catch (Exception e) {
		}
		try {
			Object packet = getNew(pPlayOutPlayerListHeaderFooter,
					getParameters(mIChatBaseComponent), getIChatText(header));

			setValue(packet, "b", getIChatText(footer));
			sendPacket(packet, player);
		} catch (Exception e) {
		}
		try {
			Object packet = getNew(pPlayOutPlayerListHeaderFooter,
					getParameters(mIChatBaseComponent), getIChatText2(header));
			setValue(packet, "b", getIChatText2(footer));
			sendPacket(packet, player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	/**
	 * @param player
	 *            Jogador
	 * @return Se o Jogador esta na versão 1.8 ou pra cima
	 */
	public static boolean isAbove1_8(Player player) {
		try {
			return (int) getResult(
					getValue(getConnection(player), "networkManager"),
					"getVersion") == 47;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * Envia um Title para o Jogador
	 * 
	 * @param player
	 *            Jogador
	 * @param title
	 *            Titulo
	 * @param subTitle
	 *            SubTitulo
	 * @param fadeIn
	 *            Tempo de Aparececimento (Ticks)
	 * @param stay
	 *            Tempo de Passagem (Ticks)
	 * @param fadeOut
	 *            Tempo de Desaparecimento (Ticks)
	 */
	public static void sendTitle(Player player, String title, String subTitle,
			int fadeIn, int stay, int fadeOut) {
		try {
			if (isAbove1_8(player)) {

				// sendPacket(player, getNew(PacketTitle, getParameters(Action,
				// int.class, int.class, int.class),
				// getValue(Action, "TIMES"), fadeIn, stay, fadeOut));
				sendPacket(player, getNew(sPacketTitle,
						getValue(sAction, "TIMES"), fadeIn, stay, fadeOut));
				sendPacket(player, getNew(sPacketTitle,
						getParameters(sAction, mIChatBaseComponent),
						getValue(sAction, "TITLE"), getIChatText(title)));
				sendPacket(player, getNew(sPacketTitle,
						getParameters(sAction, mIChatBaseComponent),
						getValue(sAction, "SUBTITLE"), getIChatText(subTitle)));

				return;
			}

		} catch (Exception e) {
		}
		try {
			sendPacket(player, getNew(pPlayOutTitle, fadeIn, stay, fadeOut));
			sendPacket(player, getNew(pPlayOutTitle,
					getParameters(cEnumTitleAction, mIChatBaseComponent),
					getValue(cEnumTitleAction, "TITLE"), getIChatText(title)));
			sendPacket(player,
					getNew(pPlayOutTitle,
							getParameters(cEnumTitleAction,
									mIChatBaseComponent),
							getValue(cEnumTitleAction, "SUBTITLE"),
							getIChatText(subTitle)));
			return;
		} catch (Exception e) {
		}
		try {
			sendPacket(player, getNew(pPlayOutTitle, fadeIn, stay, fadeOut));
			sendPacket(player,
					getNew(pPlayOutTitle,
							getParameters(pEnumTitleAction2,
									mIChatBaseComponent),
							getValue(pEnumTitleAction2, "TITLE"),
							getIChatText2(title)));
			sendPacket(player,
					getNew(pPlayOutTitle,
							getParameters(pEnumTitleAction2,
									mIChatBaseComponent),
							getValue(pEnumTitleAction2, "SUBTITLE"),
							getIChatText2(subTitle)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Modifica a Action Bar do Jogador
	 * 
	 * @param player
	 *            Jogador
	 * @param text
	 *            Texto
	 */
	public static void sendActionBar(Player player, String text) {
		try {
			Object component = getIChatText(text);
			Object packet = getNew(pPlayOutChat,
					getParameters(mIChatBaseComponent, byte.class), component,
					(byte) 2);
			sendPacket(player, packet);
			return;
		} catch (Exception ex) {
		}
		try {
			Object component = getIChatText2(text);
			Object packet = getNew(pPlayOutChat,
					getParameters(mIChatBaseComponent, byte.class), component,
					(byte) 2);
			sendPacket(player, packet);
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage(
					"§bRexAPI §aNao foi possivel usar o 'setActionBar' pois o servidor esta na versao anterior a 1.8");

		}

	}

	public static String getText(Object... objects) {
		StringBuilder builder = new StringBuilder();
		for (Object object : objects) {
			builder.append(object);

		}
		return builder.toString();
	}
	/**
	 * @param player
	 *            Jogador
	 * @return Ping do jogador
	 */
	public static String getPing(Player player) {
		try {
			return getValue(getHandle(player), "ping").toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	/**
	 * @return Lista de jogadores do servidor
	 */
	public static List<Player> getPlayers() {
		List<Player> list = new ArrayList<>();
		try {

			Object object = getResult(bBukkit, "getOnlinePlayers");
			if (object instanceof Collection) {
				Collection<?> players = (Collection<?>) object;
				for (Object obj : players) {
					if (obj instanceof Player) {
						Player p = (Player) obj;
						list.add(p);
					}
				}
			} else if (object instanceof Player[]) {
				Player[] players = (Player[]) object;
				for (Player p : players) {
					list.add(p);
				}
			}
		} catch (Exception e) {
		}

		return list;
	}
	/**
	 * Força o Respawn do Jogador (Respawn Automatico)
	 * 
	 * @param player
	 *            Jogador
	 */
	public static void makeRespawn(Player player) {
		try {
			Object packet = getNew(pPlayInClientCommand,
					getValue(mEnumClientCommand, "PERFORM_RESPAWN"));
			getResult(getConnection(player), "a", packet);

		} catch (Exception ex) {
			try {
				Object packet = getNew(pPlayInClientCommand,
						getValue(mEnumClientCommand2, "PERFORM_RESPAWN"));
				getResult(getConnection(player), "a", packet);
			} catch (Exception e) {
			}

		}
	}
	/**
	 * Modifica o nome do Jogador para um Novo Nome e<br>
	 * Envia para Todos os outros Jogadores a alteração (Packet)
	 * 
	 * @param player
	 *            Jogador
	 * @param displayName
	 *            Novo Nome
	 */
	public static void changeName(Player player, String displayName) {

		try {
			Object packet = getNew(pPlayOutNamedEntitySpawn,
					getParameters(mEntityHuman), getHandle(player));
			setValue(getValue(packet, "b"), "name", displayName);
			sendPackets(packet, player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	public static void disableAI(Entity entity) {
		try {
			// net.minecraft.server.v1_8_R3.Entity NMS = ((CraftEntity)
			// entidade).getHandle();
			// NBTTagCompound compound = new NBTTagCompound();
			// NMS.c(compound);
			// compound.setByte("NoAI", (byte) 1);
			// NMS.f(compound);
			Object compound = getNew(mNBTTagCompound);
			Object getHandle = getResult(entity, "getHandle");
			getResult(getHandle, "c", compound);
			getResult(compound, "setByte", "NoAI", (byte) 1);
			getResult(getHandle, "f", compound);

		} catch (Exception e) {
		}

	}
	public static String getIp(Player p) {
		return p.getAddress().getAddress().getHostAddress();
	}
	/**
	 * Private helper method
	 * 
	 * @param directory
	 *            The directory to start with
	 * @param pckgname
	 *            The package name to search for. Will be needed for getting the
	 *            Class object.
	 * @param classes
	 *            if a file isn't loaded but still is in the directory
	 * @throws ClassNotFoundException
	 */
	private static void checkDirectory(File directory, String pckgname,
			ArrayList<Class<?>> classes) throws ClassNotFoundException {
		File tmpDirectory;

		if (directory.exists() && directory.isDirectory()) {
			final String[] files = directory.list();

			for (final String file : files) {
				if (file.endsWith(".class")) {
					try {
						classes.add(Class.forName(pckgname + '.'
								+ file.substring(0, file.length() - 6)));
					} catch (final NoClassDefFoundError e) {
						// do nothing. this class hasn't been found by the
						// loader, and we don't care.
					}
				} else if ((tmpDirectory = new File(directory, file))
						.isDirectory()) {
					checkDirectory(tmpDirectory, pckgname + "." + file,
							classes);
				}
			}
		}
	}

	/**
	 * Private helper method.
	 * 
	 * @param connection
	 *            the connection to the jar
	 * @param pckgname
	 *            the package name to search for
	 * @param classes
	 *            the current ArrayList of all classes. This method will simply
	 *            add new classes.
	 * @throws ClassNotFoundException
	 *             if a file isn't loaded but still is in the jar file
	 * @throws IOException
	 *             if it can't correctly read from the jar file.
	 */
	private static void checkJarFile(JarURLConnection connection,
			String pckgname, ArrayList<Class<?>> classes)
			throws ClassNotFoundException, IOException {
		final JarFile jarFile = connection.getJarFile();
		final Enumeration<JarEntry> entries = jarFile.entries();
		String name;

		for (JarEntry jarEntry = null; entries.hasMoreElements()
				&& ((jarEntry = entries.nextElement()) != null);) {
			name = jarEntry.getName();

			if (name.contains(".class")) {
				name = name.substring(0, name.length() - 6).replace('/', '.');

				if (name.contains(pckgname)) {
					classes.add(Class.forName(name));
				}
			}
		}
	}
	public static ArrayList<Class<?>> getClassesForPackage(JavaPlugin plugin,
			String pkgname) {
		ArrayList<Class<?>> classes = new ArrayList<>();

		CodeSource src = plugin.getClass().getProtectionDomain()
				.getCodeSource();
		if (src != null) {
			URL resource = src.getLocation();
			resource.getPath();
			processJarfile(resource, pkgname, classes);
		}
		return classes;
	}
	/**
	 * Attempts to list all the classes in the specified package as determined
	 * by the context class loader
	 * 
	 * @param pckgname
	 *            the package name to search
	 * @return a list of classes that exist within that package
	 * @throws ClassNotFoundException
	 *             if something went wrong
	 */
	public static ArrayList<Class<?>> getClassesForPackage(String pckgname)
			throws ClassNotFoundException {
		final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		try {
			final ClassLoader cld = Thread.currentThread()
					.getContextClassLoader();

			if (cld == null)
				throw new ClassNotFoundException("Can't get class loader.");

			final Enumeration<URL> resources = cld
					.getResources(pckgname.replace('.', '/'));
			URLConnection connection;

			for (URL url = null; resources.hasMoreElements()
					&& ((url = resources.nextElement()) != null);) {
				try {
					connection = url.openConnection();

					if (connection instanceof JarURLConnection) {
						checkJarFile((JarURLConnection) connection, pckgname,
								classes);
					} else if (connection instanceof FileURLConnection) {
						try {
							checkDirectory(new File(
									URLDecoder.decode(url.getPath(), "UTF-8")),
									pckgname, classes);
						} catch (final UnsupportedEncodingException ex) {
							throw new ClassNotFoundException(
									pckgname + " does not appear to be a valid package (Unsupported encoding)",
									ex);
						}
					} else
						throw new ClassNotFoundException(pckgname + " ("
								+ url.getPath()
								+ ") does not appear to be a valid package");
				} catch (final IOException ioex) {
					throw new ClassNotFoundException(
							"IOException was thrown when trying to get all resources for "
									+ pckgname,
							ioex);
				}
			}
		} catch (final NullPointerException ex) {
			throw new ClassNotFoundException(
					pckgname + " does not appear to be a valid package (Null pointer exception)",
					ex);
		} catch (final IOException ioex) {
			throw new ClassNotFoundException(
					"IOException was thrown when trying to get all resources for "
							+ pckgname,
					ioex);
		}

		return classes;
	}
	public static void saveEnum(Class<?> value) {
		saveEnum(value, false);
	}
	public static void saveClassLikeEnum(Class<?> value) {
		try {
			Config config = new Config(
					"DataBase/" + value.getSimpleName() + ".yml");
			for (Field field : value.getFields()) {
				if (field.getType().equals(value)) {
					Object obj = field.get(value);
					Section section = config.getSection(field.getName());
					for (Method method : obj.getClass().getDeclaredMethods()) {
						String name = method.getName();
						if ((method.getParameterCount() == 0)
								&& name.startsWith("get")
										| name.startsWith("is")
										| name.startsWith("can")) {
							method.setAccessible(true);
							Object test = method.invoke(obj);
							if (test instanceof Class)
								continue;
							section.add(method.getName(), test);
						}
					}
				}
			}
			config.saveConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void saveObject(String local, Object value) {
		try {
			Config config = new Config("DataBase/" + local + ".yml");
			Section section = config.getConfig();
			for (Method method : value.getClass().getDeclaredMethods()) {
				String name = method.getName();
				if ((method.getParameterCount() == 0) && name.startsWith("get")
						| name.startsWith("is") | name.startsWith("can")) {
					method.setAccessible(true);
					Object test = method.invoke(value);
					if (test == null)
						continue;
					if (test instanceof Class)
						continue;
					section.set(method.getName(), test);
				}
			}
			config.saveConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void saveEnum(Class<?> value, boolean perConfig) {
		try {
			if (perConfig) {

				for (Object part : value.getEnumConstants()) {

					try {
						Enum<?> obj = (Enum<?>) part;
						Config config = new Config(
								"DataBase/" + value.getSimpleName() + "/"
										+ obj.name() + ".yml");
						Section section = config.getConfig();
						section.set("number", obj.ordinal());
						for (Method method : obj.getClass()
								.getDeclaredMethods()) {
							String name = method.getName();
							if ((method.getParameterCount() == 0)
									&& name.startsWith("get")
											| name.startsWith("is")
											| name.startsWith("can")) {
								method.setAccessible(true);
								Object test = method.invoke(obj);
								if (test instanceof Class)
									continue;
								section.add(method.getName(), test);
							}
						}
						config.saveConfig();
					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}
				}

			} else {
				Config config = new Config(
						"DataBase/" + value.getSimpleName() + ".yml");
				boolean used = false;
				for (Object part : value.getEnumConstants()) {
					try {
						Enum<?> obj = (Enum<?>) part;
						Section section = config.add(obj.name(), obj.ordinal());

						for (Method method : obj.getClass()
								.getDeclaredMethods()) {
							String name = method.getName();
							if ((method.getParameterCount() == 0)
									&& name.startsWith("get")
											| name.startsWith("is")
											| name.startsWith("can")) {
								try {
									method.setAccessible(true);
									Object test = method.invoke(obj);
									if (test == null)
										continue;
									if (test instanceof Class)
										continue;
									section.add(method.getName(), test);
									used = true;
								} catch (Exception ex) {
									Cs.console("§bDataBase §fO metodo §c" + name
											+ "§f causou erro!");
									ex.printStackTrace();
									continue;
								}

							}
						}

					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}
				}
				if (!used)
					config.setIndent(0);
				config.saveConfig();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void saveObjects() {

		saveEnum(DamageCause.class);
		saveEnum(Material.class);
		saveEnum(Effect.class);
		saveEnum(EntityType.class);
		saveEnum(TargetReason.class);
		saveEnum(Sound.class);
		saveEnum(EntityEffect.class);
		saveEnum(Environment.class);
		saveEnum(PotionType.class);
		saveClassLikeEnum(PotionEffectType.class);
		if (API.PLUGIN.getConfig().getBoolean("save-worlds")) {
			for (World world : Bukkit.getWorlds()) {
				saveObject("Worlds/" + world.getName(), world);
			}
		}
		if (API.PLUGIN.getConfig().getBoolean("save-players")) {
			for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
				@SuppressWarnings("deprecation")
				String name = player.getName();
				saveObject("Players/" + name + " " + player.getUniqueId(),
						player);
			}
		}
		saveObject("Server", Bukkit.getServer());
	}

	@SuppressWarnings("resource")
	private static void processJarfile(URL resource, String pkgname,
			ArrayList<Class<?>> classes) {
		String relPath = pkgname.replace('.', '/');
		String resPath = resource.getPath().replace("%20", " ");
		String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar")
				.replaceFirst("file:", "");
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPath);
		} catch (IOException e) {
			throw new RuntimeException(
					"Unexpected IOException reading JAR File '" + jarPath
							+ "'. Do you have strange characters in your folders? Such as #?",
					e);
		}

		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = (JarEntry) entries.nextElement();
			String entryName = entry.getName();
			String className = null;
			if ((entryName.endsWith(".class"))
					&& (entryName.startsWith(relPath))
					&& (entryName.length() > relPath.length() + "/".length())) {
				className = entryName.replace('/', '.').replace('\\', '.')
						.replace(".class", "");
			}
			if (className != null) {
				try {
					Class<?> c = Class.forName(className);
					if (c != null)
						classes.add(c);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
	public static void unzip(String zipFilePath, String destDirectory)

	{
		try {
			File destDir = new File(destDirectory);
			if (!destDir.exists()) {
				destDir.mkdir();
			}
			ZipInputStream zipIn = new ZipInputStream(
					new FileInputStream(zipFilePath));
			ZipEntry entry = zipIn.getNextEntry();

			while (entry != null) {
				String filePath = destDirectory + File.separator
						+ entry.getName();
				if (!entry.isDirectory()) {
					extractFile(zipIn, filePath);
				} else {
					File dir = new File(filePath);
					dir.mkdir();
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@SafeVarargs
	public static void commands(Section section, CMD... cmds) {
		for (CMD cmd : cmds) {
			String name = cmd.getName();
			if (section != null) {
				if (section.contains(name)) {
					cmd = (CMD) section.get(name);

				}

			}
			cmd.register();
			if (cmd.hasEvents()) {
				cmd.register(cmd.getPlugin());
			}
			if (section != null) {
				section.add(name, cmd);

			}

		}
	}

	public static void extractFile(ZipInputStream zipIn, String filePath) {
		try {
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(filePath));
			byte[] bytesIn = new byte[4096];
			int read = 0;
			while ((read = zipIn.read(bytesIn)) != -1) {
				bos.write(bytesIn, 0, read);
			}
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static String newKey(KeyType type, int maxSize) {

		String key = "";
		if (type == KeyType.UUID) {
			key = UUID.randomUUID().toString();
		} else if (type == KeyType.LETTER) {
			final StringBuffer buffer = new StringBuffer();
			String characters = "";
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			final int charactersLength = characters.length();
			for (int i = 0; i < maxSize; ++i) {
				final double index = Math.random() * charactersLength;
				buffer.append(characters.charAt((int) index));
			}
			key = buffer.toString();
		} else if (type == KeyType.NUMERIC) {
			final StringBuffer buffer = new StringBuffer();
			String characters = "";
			characters = "0123456789";
			final int charactersLength = characters.length();
			for (int i = 0; i < maxSize; ++i) {
				final double index = Math.random() * charactersLength;
				buffer.append(characters.charAt((int) index));
			}
			key = buffer.toString();
		} else if (type == KeyType.ALPHANUMERIC) {
			final StringBuffer buffer = new StringBuffer();
			String characters = "";
			characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			final int charactersLength = characters.length();
			for (int i = 0; i < maxSize; ++i) {
				final double index = Math.random() * charactersLength;
				buffer.append(characters.charAt((int) index));
			}
			key = buffer.toString();
		}
		return key;

	}
	public static String getServerIp() {
		try {
			URLConnection connect = new URL("http://checkip.amazonaws.com/")
					.openConnection();
			connect.addRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
			Scanner scan = new Scanner(connect.getInputStream());
			StringBuilder sb = new StringBuilder();
			while (scan.hasNext()) {
				sb.append(scan.next());
			}
			scan.close();
			return sb.toString();

		} catch (Exception ex) {

			String ip = null;
			return ip;
		}
	}
	public static ItemStack[] itemFromBase64(final String data)
			throws IOException {
		try {
			final ByteArrayInputStream inputStream = new ByteArrayInputStream(
					Base64Coder.decodeLines(data));
			final BukkitObjectInputStream dataInput = new BukkitObjectInputStream(
					(InputStream) inputStream);
			final ItemStack[] stacks = new ItemStack[dataInput.readInt()];
			for (int i = 0; i < stacks.length; ++i) {
				stacks[i] = (ItemStack) dataInput.readObject();
			}
			dataInput.close();
			return stacks;
		} catch (ClassNotFoundException e) {
			throw new IOException("Não foi possivel retornar os itens", e);
		}
	}
	public static String itemtoBase64(final ItemStack[] contents) {
		try {
			final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			final BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(
					(OutputStream) outputStream);
			dataOutput.writeInt(contents.length);
			for (final ItemStack stack : contents) {
				dataOutput.writeObject((Object) stack);
			}
			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			throw new IllegalStateException("Não foi possivel salvar os itens",
					e);
		}
	}
}
