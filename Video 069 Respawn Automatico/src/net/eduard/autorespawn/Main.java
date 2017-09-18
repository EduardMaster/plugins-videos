package net.eduard.autorespawn;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand.EnumClientCommand;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class Main extends JavaPlugin implements Listener {

	public static Main getPlugin() {
		return JavaPlugin.getPlugin(Main.class);
	}
	private static Main plugin;
	public static Main getInstance() {
		return plugin;
	}
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(new Eventos(), plugin);
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	@EventHandler
	public void event(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		CraftPlayer cp = (CraftPlayer)p;
		EntityPlayer ep = cp.getHandle();
		PlayerConnection pc = ep.playerConnection;
		PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (!p.isDead())return;
				pc.a(packet);
				p.setFireTicks(0);
			}
		}.runTaskLater(this,1);
		
		
		
		
	}
	

}
