package net.eduard.api.manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_7_R4.EntityHorse;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EntityWitherSkull;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutAttachEntity;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.util.com.google.common.cache.Cache;
import net.minecraft.util.com.google.common.cache.CacheBuilder;
import net.minecraft.util.com.google.common.cache.CacheLoader;
import net.minecraft.util.com.google.common.cache.LoadingCache;
import net.minecraft.util.com.google.common.collect.Iterables;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParser;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;

public final class PlayerAPI {
private static final BukkitNameFetcher SKIN = new BukkitNameFetcher();
	
	public static NameFetcher getSkinController(){
		return SKIN;
	}
	
	public static abstract class NameFetcher
	{
	    private ArrayList<String> servers;
	    private HashMap<String, Integer> fails;
	    private int current;
	    
	    public NameFetcher() {
	        (this.servers = new ArrayList<String>()).add("https://sessionserver.mojang.com/session/minecraft/profile/%player-uuid%#name#id");
	        this.servers.add("https://craftapi.com/api/user/username/%player-uuid%#username#uuid");
	        this.servers.add("https://us.mc-api.net/v3/name/%player-uuid%#name#uuid");
	        this.servers.add("https://mcapi.ca/name/uuid/%player-uuid%#name#uuid");
	        this.fails = new HashMap<String, Integer>();
	        this.current = 0;
	    }
	    
	    private String getNextServer() {
	        if (this.current == this.servers.size() - 1) {
	            this.current = 0;
	        }
	        else {
	            ++this.current;
	        }
	        return this.servers.get(this.current);
	    }
	    
	    public String loadFromServers(final UUID id) {
	        String name = null;
	        String server1 = this.getNextServer();
	        name = this.load(id, server1);
	        if (name == null) {
	            name = this.load(id, this.getNextServer());
	            if (name != null) {
	                if (this.fails.containsKey(server1)) {
	                    this.fails.put(server1, this.fails.get(server1) + 1);
	                }
	                else {
	                    this.fails.put(server1, 1);
	                }
	            }
	        }
	        server1 = null;
	        return name;
	    }
	    
	    public abstract String load(final UUID p0, final String p1);
	    
	    public abstract String getUsername(final UUID p0);
	}
	public static class BukkitNameFetcher extends NameFetcher
	{
	    private JsonParser parser;
	    private Cache<UUID, String> uuidName;
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public BukkitNameFetcher() {
	        this.parser = new JsonParser();
	        this.uuidName = (Cache<UUID, String>)CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.DAYS).build((CacheLoader)new CacheLoader<UUID, String>() {
	            public String load(final UUID id) throws Exception {
	                return BukkitNameFetcher.this.loadName(id);
	            }
	        });
	    }
	    
	    private String loadName(final UUID id) {
	        String name = null;
	        Player p = Bukkit.getPlayer(id);
	        if (p != null) {
	            name = p.getName();
	            p = null;
	        }
	        else {
	            name = this.loadFromServers(id);
	        }
	        return name;
	    }
	    
	    public String load(UUID id, String server) {
	        String name = null;
	        try {
	            String[] infos = server.split("#");
	            String serverUrl = infos[0].replace("%player-uuid%", id.toString().replace("-", ""));
	            URL url = new URL(serverUrl);
	            java.io.InputStream is = url.openStream();
	            InputStreamReader streamReader = new InputStreamReader(is, Charset.forName("UTF-8"));
	            JsonObject object = this.parser.parse((Reader)streamReader).getAsJsonObject();
	            if (object.has(infos[1]) && object.has(infos[2]) && object.get(infos[2]).getAsString().equalsIgnoreCase(id.toString().replace("-", ""))) {
	                name = object.get(infos[1]).getAsString();
	            }
	            streamReader.close();
	            is.close();
	            object = null;
	            streamReader = null;
	            is = null;
	            url = null;
	            serverUrl = null;
	            infos = null;
	            server = null;
	            id = null;
	        }
	        catch (Exception ex) {
	            return null;
	        }
	        return name;
	    }
	    
	    public String getUsername(final UUID id) {
	        try {
	            return (String)this.uuidName.get(id, null);
	        }
	        catch (Exception e) {
	            return null;
	        }
	    }
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final LoadingCache<GameProfile, Property> Textures = (LoadingCache<GameProfile, Property>) CacheBuilder
			.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES)
			.build((CacheLoader) new CacheLoader<GameProfile, Property>() {
				public Property load(final GameProfile key) throws Exception {
					return loadTextures(key);
				}
			});;

	private static MinecraftServer nmsServer;

	static {
		nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final Property loadTextures(final GameProfile profile) {
		MinecraftServer.getServer().av().fillProfileProperties(profile, true);
		return (Property) Iterables.getFirst((Iterable) profile.getProperties().get((String) "textures"),
				(Object) null);
	}

	public static MinecraftServer getNmsServer() {
		return nmsServer;
	}

	public static WorldServer getNmsWorld(final World world) {
		return ((CraftWorld) world).getHandle();
	}

	public static void setHeadYaw(final Entity en, float yaw) {
		if (!(en instanceof EntityLiving)) {
			return;
		}
		final EntityLiving handle = (EntityLiving) en;
		while (yaw < -180.0f) {
			yaw += 360.0f;
		}
		while (yaw >= 180.0f) {
			yaw -= 360.0f;
		}
		handle.aO = yaw;
		if (!(handle instanceof EntityHuman)) {
			handle.aM = yaw;
		}
		handle.aP = yaw;
	}
	 public static void changePlayerName(final Player player, final String name) {
	        changePlayerName(player, name, true);
	    }
	    
		public static void changePlayerName(final Player player, final String name, final boolean respawn) {
	        final Player[] players = Bukkit.getServer().getOnlinePlayers();
	        final EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
	        final GameProfile playerProfile = entityPlayer.getProfile();
	        if (respawn) {
	            removeFromTab(player, players);
	        }
	        try {
	            final Field field = playerProfile.getClass().getDeclaredField("name");
	            field.setAccessible(true);
	            field.set(playerProfile, name);
	            field.setAccessible(false);
	            entityPlayer.getClass().getDeclaredField("displayName").set(entityPlayer, name);
	            entityPlayer.getClass().getDeclaredField("listName").set(entityPlayer, name);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        if (respawn) {
	            respawnPlayer(player, players);
	        }
	    }
	    
	    public static void removePlayerSkin(final Player player) {
	        removePlayerSkin(player, true);
	    }
	    
		public static void removePlayerSkin(final Player player, final boolean respawn) {
	        final EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
	        final GameProfile playerProfile = entityPlayer.getProfile();
	        playerProfile.getProperties().clear();
	        if (respawn) {
	            respawnPlayer(player, Bukkit.getServer().getOnlinePlayers());
	        }
	    }
	    
	    public static void changePlayerSkin(final Player player, final String name, final UUID uuid) {
	        changePlayerSkin(player, name, uuid, true);
	    }
	    
		public static void changePlayerSkin(final Player player, final String name, final UUID uuid, final boolean respawn) {
	        final EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
	        final GameProfile playerProfile = entityPlayer.getProfile();
	        playerProfile.getProperties().clear();
	        try {
	            playerProfile.getProperties().put("textures", (Property)Textures.get((GameProfile)new GameProfile(uuid, name)));
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	        if (respawn) {
	            respawnPlayer(player, Bukkit.getServer().getOnlinePlayers());
	        }
	    }
	    
	    public void addToTab(final Player player, final Collection<? extends Player> players) {
	        final PacketPlayOutPlayerInfo addPlayerInfo = PacketPlayOutPlayerInfo.addPlayer(((CraftPlayer)player).getHandle());
	        final PacketPlayOutPlayerInfo updatePlayerInfo = PacketPlayOutPlayerInfo.updateDisplayName(((CraftPlayer)player).getHandle());
	        for (final Player online : players) {
	            if (!online.canSee(player)) {
	                continue;
	            }
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)addPlayerInfo);
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)updatePlayerInfo);
	        }
	    }
	    
	    public static void removeFromTab(final Player player, final Player[] players) {
	        final PacketPlayOutPlayerInfo removePlayerInfo = PacketPlayOutPlayerInfo.removePlayer(((CraftPlayer)player).getHandle());
	        for (final Player online : players) {
	            if (!online.canSee(player)) {
	                continue;
	            }
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)removePlayerInfo);
	        }
	    }
	    
	    public static void respawnPlayer(final Player player, final Player[] players) {
	        final EntityPlayer entityPlayer = ((CraftPlayer)player).getHandle();
	        final PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(new int[] { entityPlayer.getId() });
	        final PacketPlayOutNamedEntitySpawn spawn = new PacketPlayOutNamedEntitySpawn((EntityHuman)entityPlayer);
	        final PacketPlayOutPlayerInfo addPlayerInfo = PacketPlayOutPlayerInfo.addPlayer(((CraftPlayer)player).getHandle());
	        final PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(entityPlayer.getId(), entityPlayer.getDataWatcher(), true);
	        final PacketPlayOutEntityHeadRotation headRotation = new PacketPlayOutEntityHeadRotation((net.minecraft.server.v1_7_R4.Entity)entityPlayer, (byte)MathHelper.d(entityPlayer.getHeadRotation() * 256.0f / 360.0f));
	        final PacketPlayOutPlayerInfo removePlayerInfo = PacketPlayOutPlayerInfo.removePlayer(((CraftPlayer)player).getHandle());
	        for (final Player online : players) {
	            if (!online.canSee(player)) {
	                continue;
	            }
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)removePlayerInfo);
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)addPlayerInfo);
	            if (online.getUniqueId() == player.getUniqueId()) {
	                continue;
	            }
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)destroy);
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)spawn);
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)metadata);
	            ((CraftPlayer)online).getHandle().playerConnection.sendPacket((Packet)headRotation);
	        }
	    }
	    
	    public static boolean validateName(final String username) {
	        final Pattern pattern = Pattern.compile("[a-zA-Z0-9_]{1,16}");
	        final Matcher matcher = pattern.matcher(username);
	        return matcher.matches();
	    }
	public final static class MobBarAPI
	{
	  private static MobBarAPI instance;
	  private Map<String, Dragon> dragonMap = new HashMap<>();

	  public static MobBarAPI getInstance()
	  {
	    if (instance == null) {
	      instance = new MobBarAPI();
	    }
	    return instance;
	  }

	  public void setStatus(Player player, String text, int percent, boolean reset) throws Exception
	  {
	    if (percent <= 0)
	    {
	      percent = 1;
	    }
	    else if (percent > 100)
	    {
	      throw new IllegalArgumentException("percent cannot be greater than 100, percent = " + percent);
	    }

	    Dragon dragon = null;

	    if ((this.dragonMap.containsKey(player.getName())) && (!reset))
	    {
	      dragon = (Dragon)this.dragonMap.get(player.getName());
	    }
	    else
	    {
	      dragon = new Dragon(text, player.getLocation().add(0.0D, -200.0D, 0.0D), percent);
	      Object mobPacket = dragon.getSpawnPacket();
	      sendPacket(player, mobPacket);
	      this.dragonMap.put(player.getName(), dragon);
	    }

	    if (text == "")
	    {
	      Object destroyPacket = dragon.getDestroyPacket();
	      sendPacket(player, destroyPacket);
	      this.dragonMap.remove(player.getName());
	    }
	    else
	    {
	      dragon.setName(text);
	      dragon.setHealth(percent);
	      Object metaPacket = dragon.getMetaPacket(dragon.getWatcher());
	      Object teleportPacket = dragon.getTeleportPacket(player.getLocation().add(0.0D, -200.0D, 0.0D));
	      sendPacket(player, metaPacket);
	      sendPacket(player, teleportPacket);
	    }
	  }


	  public static void sendPacketRadius(Location loc, int radius, Object packet)
	  {
	    for (Player p : loc.getWorld().getPlayers())
	    {
	      if (loc.distanceSquared(p.getLocation()) < radius * radius)
	      {
	        sendPacket(p, packet);
	      }
	    }
	  }

	  public static void sendPacket(List<Player> players, Object packet)
	  {
	    for (Player p : players)
	    {
	      sendPacket(p, packet);
	    }
	  }

	  public static void sendPacket(Player p, Object packet)
	  {
	    try
	    {
	      Object nmsPlayer = getHandle(p);
	      Field con_field = nmsPlayer.getClass().getField("playerConnection");
	      Object con = con_field.get(nmsPlayer);
	      Method packet_method = getMethod(con.getClass(), "sendPacket");
	      packet_method.invoke(con, new Object[] { packet });
	    }
	    catch (SecurityException e)
	    {
	      e.printStackTrace();
	    }
	    catch (IllegalArgumentException e)
	    {
	      e.printStackTrace();
	    }
	    catch (IllegalAccessException e)
	    {
	      e.printStackTrace();
	    }
	    catch (InvocationTargetException e)
	    {
	      e.printStackTrace();
	    }
	    catch (NoSuchFieldException e)
	    {
	      e.printStackTrace();
	    }
	  }

	  public static Class<?> getCraftClass(String ClassName)
	  {
	    String name = Bukkit.getServer().getClass().getPackage().getName();
	    String version = name.substring(name.lastIndexOf('.') + 1) + ".";
	    String className = "net.minecraft.server." + version + ClassName;
	    Class<?> c = null;
	    try
	    {
	      c = Class.forName(className);
	    }
	    catch (ClassNotFoundException e)
	    {
	      e.printStackTrace();
	    }
	    return c;
	  }

	  public static Class<?> getBukkitClass(String ClassName) {
	    String name = Bukkit.getServer().getClass().getPackage().getName();
	    String version = name.substring(name.lastIndexOf('.') + 1) + ".";
	    String className = "org.bukkit.craftbukkit." + version + ClassName;
	    Class<?> c = null;
	    try
	    {
	      c = Class.forName(className);
	    }
	    catch (ClassNotFoundException e)
	    {
	      e.printStackTrace();
	    }
	    return c;
	  }

	  public static Object getHandle(Entity entity)
	  {
	    Object nms_entity = null;
	    Method entity_getHandle = getMethod(entity.getClass(), "getHandle");
	    try
	    {
	      nms_entity = entity_getHandle.invoke(entity, new Object[0]);
	    }
	    catch (IllegalArgumentException e)
	    {
	      e.printStackTrace();
	    }
	    catch (IllegalAccessException e)
	    {
	      e.printStackTrace();
	    }
	    catch (InvocationTargetException e)
	    {
	      e.printStackTrace();
	    }
	    return nms_entity;
	  }

	  public static Object getHandle(Object entity)
	  {
	    Object nms_entity = null;
	    Method entity_getHandle = getMethod(entity.getClass(), "getHandle");
	    try
	    {
	      nms_entity = entity_getHandle.invoke(entity, new Object[0]);
	    }
	    catch (IllegalArgumentException e)
	    {
	      e.printStackTrace();
	    }
	    catch (IllegalAccessException e)
	    {
	      e.printStackTrace();
	    }
	    catch (InvocationTargetException e)
	    {
	      e.printStackTrace();
	    }
	    return nms_entity;
	  }

	  public static Field getField(Class<?> cl, String field_name)
	  {
	    try
	    {
	      return cl.getDeclaredField(field_name);
	    }
	    catch (SecurityException e)
	    {
	      e.printStackTrace();
	    }
	    catch (NoSuchFieldException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }

	  public static Method getMethod(Class<?> cl, String method, Class<?>[] args)
	  {
	    for (Method m : cl.getMethods())
	    {
	      if ((m.getName().equals(method)) && (ClassListEqual(args, m.getParameterTypes())))
	      {
	        return m;
	      }
	    }
	    return null;
	  }

	  public static Method getMethod(Class<?> cl, String method, Integer args)
	  {
	    for (Method m : cl.getMethods())
	    {
	      if ((m.getName().equals(method)) && (args.equals(Integer.valueOf(m.getParameterTypes().length))))
	      {
	        return m;
	      }
	    }
	    return null;
	  }

	  public static Method getMethod(Class<?> cl, String method)
	  {
	    for (Method m : cl.getMethods())
	    {
	      if (m.getName().equals(method))
	      {
	        return m;
	      }
	    }
	    return null;
	  }

	  public static void setValue(Object instance, String fieldName, Object value) throws Exception
	  {
	    Field field = instance.getClass().getDeclaredField(fieldName);
	    field.setAccessible(true);
	    field.set(instance, value);
	  }

	  public static Object getValue(Object instance, String fieldName) throws Exception
	  {
	    Field field = instance.getClass().getDeclaredField(fieldName);
	    field.setAccessible(true);
	    return field.get(instance);
	  }

	  public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
	    boolean equal = true;

	    if (l1.length != l2.length)
	      return false;
	    for (int i = 0; i < l1.length; i++)
	    {
	      if (l1[i] != l2[i])
	      {
	        equal = false;
	        break;
	      }
	    }

	    return equal;
	  }
	  
	  
	  /**
	   * 
	   * @author Eduard-PC
	   *
	   */
	  
	  
	  private static class Dragon { private static final int MAX_HEALTH = 200;
	    private int id;
	    private int x;
	    private int y;
	    private int z;
	    private int pitch = 0;
	    private int yaw = 0;
	    private byte xvel = 0;
	    private byte yvel = 0;
	    private byte zvel = 0;
	    private float health;
	    private boolean visible = false;
	    private String name;
	    private Object world;
	    private Object dragon;

	    public Dragon(String name, Location loc, int percent) { this.name = name;
	      this.x = loc.getBlockX();
	      this.y = loc.getBlockY();
	      this.z = loc.getBlockZ();
	      this.health = (percent / 100.0F * MAX_HEALTH);
	      this.world = getHandle(loc.getWorld());
	    }

	    public void setHealth(int percent)
	    {
	      this.health = (percent / 100.0F * 200.0F);
	    }

	    public void setName(String name)
	    {
	      this.name = name;
	    }

	    public Object getSpawnPacket()
	      throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	    {
	      Class<?> Entity = getCraftClass("Entity");
	      Class<?> EntityLiving = getCraftClass("EntityLiving");
	      Class<?> EntityEnderDragon = getCraftClass("EntityEnderDragon");
	      this.dragon = EntityEnderDragon.getConstructor(new Class[] { getCraftClass("World") }).newInstance(new Object[] { this.world });

	      Method setLocation = getMethod(EntityEnderDragon, "setLocation", 
	        new Class[] { Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE });
	      setLocation.invoke(this.dragon, new Object[] { Integer.valueOf(this.x), Integer.valueOf(this.y), Integer.valueOf(this.z), Integer.valueOf(this.pitch), Integer.valueOf(this.yaw) });

	      Method setInvisible = getMethod(EntityEnderDragon, "setInvisible", 
	        new Class[] { Boolean.TYPE });
	      setInvisible.invoke(this.dragon, new Object[] { Boolean.valueOf(this.visible) });

	      Method setCustomName = getMethod(EntityEnderDragon, "setCustomName", 
	        new Class[] { String.class });
	      setCustomName.invoke(this.dragon, new Object[] { this.name });

	      Method setHealth = getMethod(EntityEnderDragon, "setHealth", 
	        new Class[] { Float.TYPE });
	      setHealth.invoke(this.dragon, new Object[] { Float.valueOf(this.health) });

	      Field motX = getField(Entity, "motX");
	      motX.set(this.dragon, Byte.valueOf(this.xvel));

	      Field motY = getField(Entity, "motX");
	      motY.set(this.dragon, Byte.valueOf(this.yvel));

	      Field motZ = getField(Entity, "motX");
	      motZ.set(this.dragon, Byte.valueOf(this.zvel));

	      Method getId = getMethod(EntityEnderDragon, "getId", new Class[0]);
	      this.id = ((Integer)getId.invoke(this.dragon, new Object[0])).intValue();

	      Class<?> PacketPlayOutSpawnEntityLiving = getCraftClass("PacketPlayOutSpawnEntityLiving");

	      Object packet = PacketPlayOutSpawnEntityLiving.getConstructor(
	        new Class[] { EntityLiving }).newInstance(new Object[] { 
	        this.dragon });

	      return packet;
	    }

	    public Object getDestroyPacket()
	      throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException
	    {
	      Class<?> PacketPlayOutEntityDestroy = getCraftClass("PacketPlayOutEntityDestroy");

	      Object packet = PacketPlayOutEntityDestroy.getConstructor(new Class[0]).newInstance(new Object[0]);

	      Field a = PacketPlayOutEntityDestroy.getDeclaredField("a");
	      a.setAccessible(true);
	      a.set(packet, 
	        new int[] { this.id });

	      return packet;
	    }

	    public Object getMetaPacket(Object watcher)
	      throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	    {
	      Class<?> DataWatcher = getCraftClass("DataWatcher");

	      Class<?> PacketPlayOutEntityMetadata = getCraftClass("PacketPlayOutEntityMetadata");

	      Object packet = PacketPlayOutEntityMetadata.getConstructor(
	        new Class[] { Integer.TYPE, DataWatcher, Boolean.TYPE }).newInstance(new Object[] { 
	        Integer.valueOf(this.id), watcher, Boolean.valueOf(true) });

	      return packet;
	    }

	    public Object getTeleportPacket(Location loc)
	      throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	    {
	      Class<?> PacketPlayOutEntityTeleport = getCraftClass("PacketPlayOutEntityTeleport");

	      Object packet = PacketPlayOutEntityTeleport.getConstructor(
	        new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Byte.TYPE, Byte.TYPE }).newInstance(new Object[] { 
	        Integer.valueOf(this.id), Integer.valueOf(loc.getBlockX() * 32), 
	        Integer.valueOf(loc.getBlockY() * 32), Integer.valueOf(loc.getBlockZ() * 32), Byte.valueOf((byte)((int)loc.getYaw() * 256 / 360)), 
	        Byte.valueOf((byte)((int)loc.getPitch() * 256 / 360)) });

	      return packet;
	    }

	    public Object getWatcher()
	      throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	    {
	      Class<?> Entity = getCraftClass("Entity");
	      Class<?> DataWatcher = getCraftClass("DataWatcher");

	      Object watcher = DataWatcher.getConstructor(
	        new Class[] { Entity }).newInstance(new Object[] { 
	        this.dragon });

	      Method a = getMethod(DataWatcher, "a", 
	        new Class[] { Integer.TYPE, Object.class });

	      a.invoke(watcher, new Object[] { Integer.valueOf(0), Byte.valueOf((byte) (this.visible ? 0 : 32)) });
	      a.invoke(watcher, new Object[] { Integer.valueOf(6), Float.valueOf(this.health) });
	      a.invoke(watcher, new Object[] { Integer.valueOf(7), Integer.valueOf(0) });
	      a.invoke(watcher, new Object[] { Integer.valueOf(8), Byte.valueOf((byte) 0) });
	      a.invoke(watcher, new Object[] { Integer.valueOf(10), this.name });
	      a.invoke(watcher, new Object[] { Integer.valueOf(11), Byte.valueOf((byte) 1) });
	      return watcher;
	    }
	  
	  }
	  
	}
	
	public static class Holo
	{
	  private List<Object> destroyCache;
	  private List<Object> spawnCache;
	  private List<UUID> players;
	  private List<String> lines;
	  private Location loc;
	  private static String path = Bukkit.getServer().getClass().getPackage().getName();
	  private static String version = path.substring(path.lastIndexOf(".") + 1, path.length());
	  private static Class<?> armorStand;
	  private static Class<?> worldClass;
	  private static Class<?> nmsEntity;
	  private static Class<?> craftWorld;
	  private static Class<?> packetClass;
	  private static Class<?> entityLivingClass;
	  private static Constructor<?> armorStandConstructor;
	  private static Class<?> destroyPacketClass;
	  private static Constructor<?> destroyPacketConstructor;
	  private static Class<?> nmsPacket;
	  
	  static
	  {
	    try
	    {
	      armorStand = Class.forName("net.minecraft.server." + version + ".EntityArmorStand");
	      worldClass = Class.forName("net.minecraft.server." + version + ".World");
	      nmsEntity = Class.forName("net.minecraft.server." + version + ".Entity");
	      craftWorld = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
	      packetClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutSpawnEntityLiving");
	      entityLivingClass = Class.forName("net.minecraft.server." + version + ".EntityLiving");
	      armorStandConstructor = armorStand.getConstructor(new Class[] { worldClass });
	      
	      destroyPacketClass = Class.forName("net.minecraft.server." + version + ".PacketPlayOutEntityDestroy");
	      destroyPacketConstructor = destroyPacketClass.getConstructor(new Class[] { int[].class });
	      
	      nmsPacket = Class.forName("net.minecraft.server." + version + ".Packet");
	    }
	    catch (ClassNotFoundException|NoSuchMethodException|SecurityException ex)
	    {
	      System.err.println("Error - Classes not initialized!");
	      System.err.println("Hologramm is not supported in 1.7!");
	    }
	  }
	  
	  public void createHolo(Location loc, List<String> lines)
	  {
	    this.lines = lines;
	    this.loc = loc;
	    this.players = new ArrayList<UUID>();
	    this.spawnCache = new ArrayList<Object>();
	    this.destroyCache = new ArrayList<Object>();
	    
	    Location displayLoc = loc.clone().add(0.0D, 0.23D * lines.size() - 1.97D, 0.0D);
	    for (int i = 0; i < lines.size(); i++)
	    {
	      Object packet = getPacket(this.loc.getWorld(), displayLoc.getX(), displayLoc.getY(), displayLoc.getZ(), (String)this.lines.get(i));
	      this.spawnCache.add(packet);
	      try
	      {
	        Field field = packetClass.getDeclaredField("a");
	        field.setAccessible(true);
	        this.destroyCache.add(getDestroyPacket(new int[] { ((Integer)field.get(packet)).intValue() }));
	      }
	      catch (Exception ex)
	      {
	        ex.printStackTrace();
	      }
	      displayLoc.add(0.0D, -0.23D, 0.0D);
	    }
	  }
	  
	  public boolean displayHolo(Player p)
	  {
	    for (int i = 0; i < this.spawnCache.size(); i++) {
	      sendPacket(p, this.spawnCache.get(i));
	    }
	    this.players.add(p.getUniqueId());
	    return true;
	  }
	  
	  public boolean destroyHolo(Player p)
	  {
	    if (this.players.contains(p.getUniqueId()))
	    {
	      for (int i = 0; i < this.destroyCache.size(); i++) {
	        sendPacket(p, this.destroyCache.get(i));
	      }
	      this.players.remove(p.getUniqueId());
	      return true;
	    }
	    return false;
	  }
	  
	  private Object getPacket(World w, double x, double y, double z, String text)
	  {
	    try
	    {
	      Object craftWorldObj = craftWorld.cast(w);
	      Method getHandleMethod = craftWorldObj.getClass().getMethod("getHandle", new Class[0]);
	      Object entityObject = armorStandConstructor.newInstance(new Object[] { getHandleMethod.invoke(craftWorldObj, new Object[0]) });
	      Method setCustomName = entityObject.getClass().getMethod("setCustomName", new Class[] { String.class });
	      setCustomName.invoke(entityObject, new Object[] { text });
	      Method setCustomNameVisible = nmsEntity.getMethod("setCustomNameVisible", new Class[] { Boolean.TYPE });
	      setCustomNameVisible.invoke(entityObject, new Object[] { Boolean.valueOf(true) });
	      Method setGravity = null;
	      if (Bukkit.getVersion().contains("1.10"))
	      {
	        setGravity = entityObject.getClass().getMethod("setNoGravity", new Class[] { Boolean.TYPE });
	        setGravity.invoke(entityObject, new Object[] { Boolean.valueOf(false) });
	      }
	      else
	      {
	        setGravity = entityObject.getClass().getMethod("setGravity", new Class[] { Boolean.TYPE });
	        setGravity.invoke(entityObject, new Object[] { Boolean.valueOf(false) });
	      }
	      Method setLocation = entityObject.getClass().getMethod("setLocation", new Class[] { Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE });
	      setLocation.invoke(entityObject, new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), Float.valueOf(0.0F), Float.valueOf(0.0F) });
	      Method setInvisible = entityObject.getClass().getMethod("setInvisible", new Class[] { Boolean.TYPE });
	      setInvisible.invoke(entityObject, new Object[] { Boolean.valueOf(true) });
	      Constructor<?> cw = packetClass.getConstructor(new Class[] { entityLivingClass });
	      return cw.newInstance(new Object[] { entityObject });
	    }
	    catch (NoSuchMethodException|SecurityException|InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }
	  
	  private Object getDestroyPacket(int... id)
	  {
	    try
	    {
	      return destroyPacketConstructor.newInstance(new Object[] { id });
	    }
	    catch (InstantiationException|IllegalAccessException|IllegalArgumentException|InvocationTargetException e)
	    {
	      e.printStackTrace();
	    }
	    return null;
	  }
	  
	  private void sendPacket(Player p, Object packet)
	  {
	    try
	    {
	      Method getHandle = p.getClass().getMethod("getHandle", new Class[0]);
	      Object entityPlayer = getHandle.invoke(p, new Object[0]);
	      Object pConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
	      Method sendMethod = pConnection.getClass().getMethod("sendPacket", new Class[] { nmsPacket });
	      sendMethod.invoke(pConnection, new Object[] { packet });
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	  }
	}
	
	public static class Hologram {
		private List<String> lines = new ArrayList<>();
		private List<Integer> ids = new ArrayList<>();
		private boolean showing = false;
		private Location location;
		
		public Location getLocation(){
			return location;
		}

		public Hologram(String[] lines) {
			this.lines.addAll(Arrays.asList(lines));
		}

		public void show(OfflinePlayer p, Location loc) {
			if (this.showing) {
				try {
					throw new Exception("Is already showing!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			Location first = loc.clone().add(0.0D,
					this.lines.size() / 2 * 0.23D, 0.0D);
			for (int i = 0; i < this.lines.size(); i++) {
				this.ids.addAll(
						showLine(p, first.clone(), (String) this.lines.get(i)));
				first.subtract(0.0D, 0.23D, 0.0D);
			}
			this.showing = true;
			this.location = loc;
		}

		public void destroy() {
			if (!this.showing) {
				try {
					throw new Exception("Isn't showing!");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			int[] ints = new int[this.ids.size()];
			for (int j = 0; j < ints.length; j++) {
				ints[j] = ((Integer) this.ids.get(j)).intValue();
			}
			PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(
					ints);
			Player[] arrayOfPlayer;
			int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length;
			for (int i = 0; i < j; i++) {
				Player player = arrayOfPlayer[i];
				((CraftPlayer) player).getHandle().playerConnection
						.sendPacket(packet);
			}
			this.showing = false;
			this.location = null;
		}

		private static List<Integer> showLine(OfflinePlayer p, Location loc,
				String text) {
			WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
			EntityWitherSkull skull = new EntityWitherSkull(world);
			skull.setLocation(loc.getX(), loc.getY() + 1.0D + 55.0D, loc.getZ(),
					0.0F, 0.0F);
			((CraftWorld) loc.getWorld()).getHandle().addEntity(skull);

			EntityHorse horse = new EntityHorse(world);
			horse.setLocation(loc.getX(), loc.getY() + 55.0D, loc.getZ(), 0.0F,
					0.0F);
			horse.setAge(-1700000);
			horse.setCustomName(text);
			horse.setCustomNameVisible(true);
			PacketPlayOutSpawnEntityLiving packedt = new PacketPlayOutSpawnEntityLiving(
					horse);
			EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();
			nmsPlayer.playerConnection.sendPacket(packedt);

			PacketPlayOutAttachEntity pa = new PacketPlayOutAttachEntity(0,
					horse, skull);
			nmsPlayer.playerConnection.sendPacket(pa);
			return Arrays.asList(new Integer[]{Integer.valueOf(skull.getId()),
					Integer.valueOf(horse.getId())});
		}
	}
	
	
	
	
	public static class MojangAPI
	{
	  
	private static final String uuidurl = "https://api.mojang.com/users/profiles/minecraft/";
	  private static final String skinurl = "https://sessionserver.mojang.com/session/minecraft/profile/";
	  private static final String altskinurl = "https://mcapi.ca/name/uuid/";
	  private static final String altuuidurl = "http://mcapi.ca/uuid/player/";

	  public static Optional<String> getUUID(String name)
	  {
	    String output = readURL("https://api.mojang.com/users/profiles/minecraft/" + name);

	    if ((output == null) || (output.isEmpty()) || (output.contains("\"error\":\"TooManyRequestsException\"")))
	    {
	      output = readURL("http://mcapi.ca/uuid/player/" + name).replace(" ", "");

	      String idbeg = "\"uuid\":\"";
	      String idend = "\",\"id\":";

	      String response = getStringBetween(output, idbeg, idend);

	      if (response.startsWith("[{\"uuid\":null")){
	    	  // erro
	      }
	      return Optional.of(response);
	    }

	    return Optional.of(output.substring(7, 39));
	  }

	 
	  private static String readURL(String url) {
	    try {
	      HttpURLConnection con = (HttpURLConnection)new URL(url).openConnection();

	      con.setRequestMethod("GET");
	      con.setRequestProperty("User-Agent", "SkinsRestorer");
	      con.setConnectTimeout(5000);
	      con.setReadTimeout(5000);
	      con.setUseCaches(false);

	      StringBuilder output = new StringBuilder();
	      BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      String line;
	      while ((line = in.readLine()) != null)
	      {
	        output.append(line);
	      }
	      in.close();

	      return output.toString(); } catch (Exception e) {
	    }
	    return null;
	  }

	  private static String getStringBetween(String base, String begin, String end)
	  {
	    Pattern patbeg = Pattern.compile(Pattern.quote(begin));
	    Pattern patend = Pattern.compile(Pattern.quote(end));

	    int resbeg = 0;
	    int resend = base.length() - 1;

	    Matcher matbeg = patbeg.matcher(base);

	    while (matbeg.find()) {
	      resbeg = matbeg.end();
	    }
	    Matcher matend = patend.matcher(base);

	    while (matend.find()) {
	      resend = matend.start();
	    }
	    return base.substring(resbeg, resend);
	  }
	  public static class SkinRequestException extends Exception
	  {
	    private static final long serialVersionUID = 5969055162529998032L;
	    private String reason;

	    public SkinRequestException(String reason) {
	      this.reason = reason;
	    }

	    public String getReason() {
	      return this.reason;
	    }
	  }
	}
}
