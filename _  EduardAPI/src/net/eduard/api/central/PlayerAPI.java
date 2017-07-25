package net.eduard.api.central;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.eduard.api.API;
import net.eduard.api.config.ConfigSection;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.MinecraftServer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
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
//	private static final String uuidurl = "https://api.mojang.com/users/profiles/minecraft/";
//	  private static final String skinurl = "https://sessionserver.mojang.com/session/minecraft/profile/";
//	  private static final String altskinurl = "https://mcapi.ca/name/uuid/";
//	  private static final String altuuidurl = "http://mcapi.ca/uuid/player/";
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
	        final Player[] players = ConfigSection.toPlayersArray(API.getPlayers());
	        
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
	            respawnPlayer(player, ConfigSection.toPlayersArray(API.getPlayers()));
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
	            respawnPlayer(player, ConfigSection.toPlayersArray(API.getPlayers()));
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
	
	  
	 
	
	
	
	
	
}
