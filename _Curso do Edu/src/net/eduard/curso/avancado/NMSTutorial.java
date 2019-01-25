package net.eduard.curso.avancado;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class NMSTutorial implements CommandExecutor {
	public static void createNPC(Player p, String name) {

		Location location = p.getLocation();
	
//		String texture = "";
//		String signature = "";
		CraftPlayer cp = (CraftPlayer) p;
		EntityPlayer ep = cp.getHandle();
		PlayerConnection pc = ep.playerConnection;
		CraftWorld cw = (CraftWorld) cp.getWorld();
		WorldServer ws = cw.getHandle();
		CraftServer sc = ((CraftServer) Bukkit.getServer());
//		DedicatedPlayerList ds = sc.getHandle();
		MinecraftServer ms = sc.getServer();
		GameProfile profile = new GameProfile(UUID.fromString("92272eadfd3c425a80981034a8d4a31f"), name);
		EntityPlayer npc = new EntityPlayer(ms, ws, profile, new PlayerInteractManager(ws));
//		profile.getProperties().put("textures", new Property("textures", texture, signature));
		npc.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(),
				location.getPitch());

		pc.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
		pc.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));

	}



	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			createNPC(p,"Npc humano");
			p.sendMessage("§aNpc setado com sucesso");
		}
		return false;
	}



	
	public static Class<?> getNMSClass(String nmsClassString) throws ClassNotFoundException {
	    String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
	    String name = "net.minecraft.server." + version + nmsClassString;
	    Class<?> nmsClass = Class.forName(name);
	    return nmsClass;
	}

	public static Class<?> getNMSArray(String nmsClassString) throws ClassNotFoundException {
	    String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
	    String name = "[Lnet.minecraft.server." + version + nmsClassString + ";";
	    Class<?> nmsClass = Class.forName(name);
	    return nmsClass;
	}
	public static void createNPCWithReflection(Player p,String name) {
		try {
			Object servidor = Bukkit.getServer().getClass().getDeclaredMethod("getServer").invoke(Bukkit.getServer());
			World world = p.getWorld();
			Object mundo = world.getClass().getDeclaredMethod("getHandle").invoke(world);
			GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "TESTONA");
			Class<?> cplayerInteractPlaye = getNMSClass("PlayerInteractManager");
			Object interact = cplayerInteractPlaye.getDeclaredConstructor(getNMSClass("World")).newInstance(mundo);
			Class<?> centityPlayer = getNMSClass("EntityPlayer");
			Constructor<?> constructor = centityPlayer.getDeclaredConstructor(getNMSClass("MinecraftServer"), getNMSClass("WorldServer"),
					gameProfile.getClass(), cplayerInteractPlaye);
			Object npc = constructor.newInstance(servidor, mundo, gameProfile, interact);
			List<Object> lista = new ArrayList<>();
			lista.add(npc);
			System.out.println("NPC: " + npc.getClass());
//			System.out.println("" + npc.getClass());
			Method setLocation = getNMSClass("Entity").getDeclaredMethod("setLocation", double.class, double.class, double.class,
					float.class, float.class);
			setLocation.invoke(npc, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 0F, 0F);
			Class<?> cpacote = getNMSClass("PacketPlayOutPlayerInfo");
			Class<?> cenumPlayerInfo = getNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
			Object enumer = cenumPlayerInfo.getField("ADD_PLAYER").get(cenumPlayerInfo);
			System.out.println("ENUMER: " + enumer.getClass());
//			System.out.println("" + enumer);
			Class<?> cEntityPlayer = getNMSClass("EntityPlayer");
			for (Constructor<?> construtor : cpacote.getDeclaredConstructors()) {
				System.out.println(""+construtor);
			}
			Constructor<?> pacoteConstructor = cpacote.getDeclaredConstructors()[2];
//			System.out.println("" + pacoteConstructor);
			Object entidadeArray = Array.newInstance(cEntityPlayer, 10);
//			Array.set
			System.out.println("ARRAY: " + entidadeArray.getClass());
			Array.set(entidadeArray, 0, npc);
			Object pacote = pacoteConstructor.newInstance(enumer, entidadeArray);
//			Lists.newArrayList(npc)
//			new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, Lists.newArrayList(npc));
			sendPacket(pacote, p);
			Object pacote2 = getNMSClass("PacketPlayOutNamedEntitySpawn")
					.getDeclaredConstructor(getNMSClass("EntityHuman")).newInstance(npc);
			sendPacket(pacote2, p);
			
			
			
			
			// centityPlayer.getDeclaredConstructor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void sendPacket(Object pacote, Player player) {
		try {
			Object getHandle = player.getClass().getDeclaredMethod("getHandle").invoke(player);
			Object playerConnection = getHandle.getClass().getDeclaredField("playerConnection").get(getHandle);
			playerConnection.getClass().getDeclaredMethod("sendPacket", getNMSClass("Packet"))
					.invoke(playerConnection, pacote);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	

}
