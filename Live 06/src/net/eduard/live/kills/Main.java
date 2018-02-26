package net.eduard.live.kills;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);

	}

	@EventHandler
	public void entrar(PlayerLoginEvent e) {
		Player p = e.getPlayer();
		if (e.getResult() == Result.KICK_BANNED) {
			e.setKickMessage("§cVoce foi banido! pelo servidor Seilaonome.com");
		}

	}

	@EventHandler
	public void minerar(BlockBreakEvent e) {
		Block bloco = e.getBlock();
		Material tipo = bloco.getType();
		if (tipo == Material.STONE) {
			bloco.setType(Material.AIR);
			e.setCancelled(true);
			int numeroAleatorio = 1 + new Random().nextInt(10);
			ItemStack drop = new ItemStack(Material.STONE, numeroAleatorio);
			bloco.getWorld().dropItem(bloco.getLocation(), drop);
			e.setExpToDrop(2 + new Random().nextInt(7));
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void colocarBloco(BlockPlaceEvent e) {
		e.setCancelled(true);
		Block bloco = e.getBlock();
		bloco.getWorld().spawnFallingBlock(bloco.getLocation(), bloco.getType(), bloco.getData());
	}
	@EventHandler
	public void ficticio(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/holograma")) {
			e.setCancelled(true);
			ArmorStand holo = (ArmorStand) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation(),
					EntityType.ARMOR_STAND);
			holo.setMarker(true);
			holo.setVisible(false);
			holo.setGravity(false);
			holo.setCustomNameVisible(true);
			holo.setCustomName("§aA linha é um holograma");
			holo.setCanPickupItems(false);
			holo.setRemoveWhenFarAway(false);

		}
	}

	public static HashMap<Player, Integer> killsStreak = new HashMap<>();

	@EventHandler
	public void morrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (p.getKiller() != null) {
			Player matador = p.getKiller();
			Integer streak = killsStreak.getOrDefault(matador, 0);
			streak++;

			killsStreak.put(matador, streak);
			if (streak == 2) {
				Bukkit.broadcastMessage("§aDouble kill");
			}
			if (streak == 3) {
				Bukkit.broadcastMessage("§aTripple kill");
			}
			if (streak == 4) {
				Bukkit.broadcastMessage("§aQuadra kill");
			}
			if (streak == 5) {
				Bukkit.broadcastMessage("§aPenta kill");
			}
			final Integer streakFinal = streak;
			new BukkitRunnable() {

				@Override
				public void run() {
					Integer novaStreak = killsStreak.getOrDefault(matador, 0);
					if (novaStreak <= streakFinal) {
						matador.sendMessage("§aSua sequencia de kills foi resetada.");
						killsStreak.put(matador, 0);
					}

				}
			}.runTaskLater(this, 20 * 15);

		}

	}

}
