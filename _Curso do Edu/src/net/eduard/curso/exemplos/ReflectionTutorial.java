package net.eduard.curso.exemplos;

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
import com.mojang.authlib.properties.Property;

import net.eduard.api.lib.Mine;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class ReflectionTutorial  {
	


	
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
	public static void getNewNpc(Player p) {
		try {
			Object servidor = Bukkit.getServer().getClass().getDeclaredMethod("getServer").invoke(Bukkit.getServer());
			World world = p.getWorld();
			Object mundo = world.getClass().getDeclaredMethod("getHandle").invoke(world);
			GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "TESTONA");
			Class<?> cplayerInteractPlaye = ReflectionTutorial.getNMSClass("PlayerInteractManager");
			Object interact = cplayerInteractPlaye.getDeclaredConstructor(ReflectionTutorial.getNMSClass("World")).newInstance(mundo);
			Class<?> centityPlayer = ReflectionTutorial.getNMSClass("EntityPlayer");
			Constructor<?> constructor = centityPlayer.getDeclaredConstructor(ReflectionTutorial.getNMSClass("MinecraftServer"), ReflectionTutorial.getNMSClass("WorldServer"),
					gameProfile.getClass(), cplayerInteractPlaye);
			Object npc = constructor.newInstance(servidor, mundo, gameProfile, interact);
			List<Object> lista = new ArrayList<>();
			lista.add(npc);
			System.out.println("NPC: " + npc.getClass());
//			System.out.println("" + npc.getClass());
			Method setLocation = ReflectionTutorial.getNMSClass("Entity").getDeclaredMethod("setLocation", double.class, double.class, double.class,
					float.class, float.class);
			setLocation.invoke(npc, p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), 0F, 0F);
			Class<?> cpacote = ReflectionTutorial.getNMSClass("PacketPlayOutPlayerInfo");
			Class<?> cenumPlayerInfo = ReflectionTutorial.getNMSClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
			Object enumer = cenumPlayerInfo.getField("ADD_PLAYER").get(cenumPlayerInfo);
			System.out.println("ENUMER: " + enumer.getClass());
//			System.out.println("" + enumer);
			Class<?> cEntityPlayer = ReflectionTutorial.getNMSClass("EntityPlayer");
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
			Object pacote2 = ReflectionTutorial.getNMSClass("PacketPlayOutNamedEntitySpawn")
					.getDeclaredConstructor(ReflectionTutorial.getNMSClass("EntityHuman")).newInstance(npc);
			sendPacket(pacote2, p);
			
			
			
			
			// centityPlayer.getDeclaredConstructor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		// WorldServer nmsWorld = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
		// EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, new
		// GameProfile(UUID.randomUUID(), "NC"), new PlayerInteractManager(nmsWorld));
		//// npc.setLocation(0.0, 70.0, 0.0, 0F, 0F);
		// npc.setLocation(p.getLocation().getX(), p.getLocation().getY(),
		// p.getLocation().getZ(), 0F, 0F);
		//
		// PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
		//// connection.sendPacket(new
		// PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
		// connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
	}

	public static void sendPacket(Object pacote, Player player) {
		try {
			Object getHandle = player.getClass().getDeclaredMethod("getHandle").invoke(player);
			Object playerConnection = getHandle.getClass().getDeclaredField("playerConnection").get(getHandle);
			playerConnection.getClass().getDeclaredMethod("sendPacket", ReflectionTutorial.getNMSClass("Packet"))
					.invoke(playerConnection, pacote);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	public static void makeRespawn(Player player) {
		try {
			// Object packet = Extra.getNew(claz_pPlayInClientCommand,
			// Extra.getValue(claz_mEnumClientCommand, "PERFORM_RESPAWN"));
			// Extra.getResult(getConnection(player), "a", packet);
			PacketPlayInClientCommand pacote = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
			CraftPlayer craftplayer = (CraftPlayer) player;
			craftplayer.getHandle().playerConnection.a(pacote);

			Constructor<PacketPlayInClientCommand> contructor = PacketPlayInClientCommand.class.getDeclaredConstructor(EnumClientCommand.class);
			PacketPlayInClientCommand objectPacote = contructor.newInstance(EnumClientCommand.class.getDeclaredField("ENUM_CLIENT_COMMAND"));
			PlayerConnection conexao = craftplayer.getHandle().playerConnection;
			Method metodo = conexao.getClass().getDeclaredMethod("a", PacketPlayInClientCommand.class);
			metodo.setAccessible(true);
			metodo.invoke(conexao, objectPacote);
			
			
		} catch (Exception ex) {
			try {
				// Object packet = Extra.getNew(claz_pPlayInClientCommand,
				// Extra.getValue(claz_mEnumClientCommand2, "PERFORM_RESPAWN"));
				// Extra.getResult(getConnection(player), "a", packet);
				PacketPlayInClientCommand pacote = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
				CraftPlayer craftplayer = (CraftPlayer) player;
				craftplayer.getHandle().playerConnection.a(pacote);

				Constructor<PacketPlayInClientCommand> contructor = PacketPlayInClientCommand.class.getDeclaredConstructor(EnumClientCommand.class);
				PacketPlayInClientCommand objectPacote = contructor.newInstance(EnumClientCommand.class.getDeclaredField("ENUM_CLIENT_COMMAND"));
				PlayerConnection conexao = craftplayer.getHandle().playerConnection;
				Method metodo = conexao.getClass().getDeclaredMethod("a", PacketPlayInClientCommand.class);
				metodo.setAccessible(true);
				metodo.invoke(conexao, objectPacote);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
