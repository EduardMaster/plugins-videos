package net.eduard.curso.exemplos;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;

public class NMSTutorial implements CommandExecutor{
	public void createNPC(Player player, String name) {

		Location location = player.getLocation();
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), "§e§l" + name);
		EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
		changeSkin(gameProfile);
		npc.setLocation(location.getX(), location.getY(), location.getZ(), player.getLocation().getYaw(),
				player.getLocation().getPitch());

		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
		connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
		
	}

	private void changeSkin(GameProfile profile) {
		String texture = "";
		String signature = "";
		profile.getProperties().put("textures", new Property("textures", texture, signature));
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
		if (cmd.getName().equalsIgnoreCase("sendmesnsagem")) {
			if (sender instanceof Player) {

				Player p = (Player) sender;

				if (args.length == 0) {
					p.sendMessage("§cPor favor,use /sendmesnsagem <mensagem>");
				} else {

					String msg = Mine.getText(0, args);
//					ActionBarUtil.sendActionBarMessage(p, msg);

				}
			}

		}
		if (cmd.getName().equalsIgnoreCase("setnpc")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				ReflectionTutorial.getNewNpc(p);
				p.sendMessage("§aNpc setado com sucesso");
			}
		}
		return false;
	}

}
