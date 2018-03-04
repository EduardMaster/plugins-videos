package net.eduard.live07;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	public void onEnable() {
		getCommand("vender").setExecutor(new ComandoVender());
		getCommand("compactar").setExecutor(new ComandoCompactar());
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	public void evento(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		e.setFormat(p.getDisplayName() + "§8: §f" + e.getMessage());
		if (p.hasPermission("tag.dono")) {
			e.setFormat("§4[DONO] §r" + p.getDisplayName() + "§8: §f" + e.getMessage());
		}
		e.setCancelled(true);
		int quantidadeDeJogadores = 0;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (p.getLocation().distanceSquared(player.getLocation()) < 100) {
				player.sendMessage(e.getFormat());
				quantidadeDeJogadores++;
			}
		}
		if (quantidadeDeJogadores==0) {
			p.sendMessage("§eNinguem esta perto para te ouvir");
		}

	}

	@EventHandler
	public void evento(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand() == null)
			return;
		if (p.getItemInHand().getType() == Material.DIAMOND_PICKAXE) {
			Block bloco = e.getBlock();
			for (BlockFace face : BlockFace.values()) {
				Block blocoFace = bloco.getRelative(face);
				blocoFace.breakNaturally(p.getItemInHand());
			}
		}

	}

	// @EventHandler
	public void evento(PlayerMoveEvent e) {

		if (!e.getFrom().getBlock().equals(e.getTo().getBlock())) {

			Firework fogos = (Firework) e.getFrom().getWorld().spawnEntity(e.getTo(), EntityType.FIREWORK);

			FireworkMeta meta = fogos.getFireworkMeta();

			FireworkEffect efeito = FireworkEffect.builder().flicker(true).withColor(Color.PURPLE).withFade(Color.AQUA)
					.trail(true).build();
			meta.addEffect(efeito);
			meta.setPower(5);
			fogos.setFireworkMeta(meta);

		}

	}
}
