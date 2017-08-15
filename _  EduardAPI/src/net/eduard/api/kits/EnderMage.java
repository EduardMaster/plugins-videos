package net.eduard.api.kits;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.eduard.api.API;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.ClickType;
import net.eduard.api.gui.Kit;
import net.eduard.api.setup.GameAPI;

public class EnderMage extends Kit {

	public double range =2;
	public double high = 100;
	public int effectSeconds = 5;

	public EnderMage() {
		setIcon(Material.ENDER_PORTAL_FRAME, "§fPuxe os seus inimigos");
		add(Material.ENDER_PORTAL_FRAME);
		setTime(5);
		setMessage("§6Voce esta invuneravel por 5 segundos");
		setClick(new Click(Material.ENDER_PORTAL, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {

			}

			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (noAir(e.getClickedBlock())) {
						return;
					}
					e.setCancelled(false);
					BlockState state = e.getClickedBlock().getState();
					state.getBlock().setType(Material.ENDER_PORTAL_FRAME);

					p.setItemInHand(null);
					API.TIME.timer(20,new BukkitRunnable() {
						int x = effectSeconds;
						@Override
						public void run() {
							x--;
							if (check(p, e.getClickedBlock().getLocation()
									.add(0, 1, 0))) {
								x = 0;
							}
							if (x == 0) {
								p.getInventory().addItem(getItems().get(0));
								state.update(true, true);
								cancel();
							}
						}
					});

				}

			}

		}).setType(ClickType.BLOCK));
	}

	public boolean check(Player p, Location teleport) {
		boolean pulled = false;
		List<Entity> list = p.getNearbyEntities(range, high, range);
		list.remove(p);
		for (Entity entity : list) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				if (livingEntity instanceof Player) {
					Player player = (Player) entity;
					sendMessage(player);
					GameAPI.makeInvunerable(player, effectSeconds);
					GameAPI.makeInvunerable(p, effectSeconds);
					pulled = true;
					GameAPI.teleport(entity, teleport);
				}
			}

		}

		if (pulled) {
			sendMessage(p);
			GameAPI.makeInvunerable(p, effectSeconds);
			GameAPI.teleport(p, teleport);
		}

		return pulled;
	}

	public boolean noAir(Block block) {
		boolean noAir = false;
		for (int x = 0; x > 2; x++) {
			block = block.getRelative(BlockFace.UP);
			noAir = block.getType() != Material.AIR;
		}
		return noAir;
	}
}
