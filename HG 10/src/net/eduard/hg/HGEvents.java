
package net.eduard.hg;

import net.eduard.hg.manager.Kit;
import net.eduard.hg.manager.KitType;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class HGEvents implements Listener {
	
	public static void join(Player p) {
		
		HG.players.remove(p);
		HG.specs.remove(p);
		p.setNoDamageTicks(20*60*60);
		p.setAllowFlight(false);
		p.setGameMode(GameMode.SURVIVAL);
		p.setHealth(p.getMaxHealth());
		p.setFoodLevel(20);
		p.setSaturation(20);
		p.setExhaustion(0);
		PlayerInventory inv = p.getInventory();
		if (HG.players.size() >= HG.maxPlayers) {
			spec(p);
			
			
		} else {
			play(p);
			inv.setItem(5, Kit.kitSelector());
		}
	}
	@EventHandler
	public void event(PlayerDeathEvent e) {
		Player p = e.getEntity();
		HG.players.remove(p);
		HG.specs.remove(p);
	}
	@EventHandler
	public void event(PlayerRespawnEvent e) {
		Player p = e.getPlayer();
		e.setRespawnLocation(p.getLocation());
		new BukkitRunnable() {
			
			public void run() {
				spec(p);
				
			}
		}.runTaskLater(Main.plugin,1);
	}
	@EventHandler
	public void event(PlayerQuitEvent e) {

		Player p = e.getPlayer();
		e.setQuitMessage("");
		HG.players.remove(p);
		HG.specs.remove(p);
		HGMessages.broadcast(HGMessages.onLeaving);
		if (HG.state == HGState.STARTING) {
			if (HG.specs.size() >= 1) {
				Player d = HG.specs.get(0);
				join(d);
			}
		}

	}

	@EventHandler
	public void event(PlayerJoinEvent e) {

		Player p = e.getPlayer();
		e.setJoinMessage("");
		join(p);
	}

	public static void play(Player p) {
		HG.players.add(p);
		p.teleport(p.getWorld().getSpawnLocation()
			.setDirection(p.getLocation().getDirection()));

	}

	public static void spec(Player p) {

		HG.specs.add(p);
		p.teleport(p.getWorld().getSpawnLocation()
			.setDirection(p.getLocation().getDirection()));

	}

	public static void restart() {

		for (Player p : Bukkit.getOnlinePlayers()) {
			p.kickPlayer("§6O HG reiniciou!");
		}
		HG.inuvunerableTime = 2 * 60;
		regenerateBlocks();
		HG.players.clear();
		HG.specs.clear();
		HG.blocks.clear();
	}

	@SuppressWarnings("deprecation")
	public static void regenerateBlocks() {

		for (int x = HG.blocks.size() - 1; x >= 1; x--) {
			BlockState block = HG.blocks.get(x);
			block.getBlock().setTypeIdAndData(block.getTypeId(), block.getRawData(),
				true);
		}

	}

	public static void start() {

		for (Player p : HG.players) {
			p.teleport(p.getWorld().getSpawnLocation()
				.setDirection(p.getLocation().getDirection()));
			p.setNoDamageTicks(20*60*2);
			PlayerInventory inv = p.getInventory();
			p.getInventory().clear();
			if (HG.kits.get(p)== KitType.ARCHER) {
				inv.addItem(new ItemStack(Material.BOW));
				inv.addItem(new ItemStack(Material.ARROW,64));
			}
		}
	}

	public static void gameOver() {

	}

	public static void playing() {
		if (HG.inuvunerableTime != 0) {
			HG.inuvunerableTime--;
			if (HG.invunerabilityTimes.contains(HG.inuvunerableTime)) {
				HGMessages.broadcast(HGMessages.onInvunerabilityIsOver);
			}
			if (HG.inuvunerableTime == 0) {
				HGMessages.broadcast(HGMessages.onInvunerabilityOver);
			}
		}

	}

	public static void restarting() {

	}

	public static void starting() {

	}

	public static void wining(Player p) {

	}

	public static void noWiner() {

	}

	

	@EventHandler
	public void event(PlayerDropItemEvent e) {

		if (HG.state == HGState.STARTING | HG.state == HGState.RESTARTING) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void event(PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (HG.state == HGState.STARTING | HG.state == HGState.RESTARTING) {
			e.setCancelled(true);
		}else {
			if (HG.specs.contains(p)) {
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void event(PlayerInteractEvent e) {

		if (HG.state == HGState.STARTING | HG.state == HGState.RESTARTING) {
			e.setCancelled(true);
		}
	}

}
