package net.eduard.kits;

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

import net.eduard.api.API;
import net.eduard.api.dev.Game;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.Kit;
import net.eduard.api.util.ClickEffect;
import net.eduard.api.util.ClickType;
import net.eduard.api.util.SimpleEffect;

public class EnderMage extends Kit {

	public boolean check(Player p, Location teleport) {
		boolean pulled = false;
		List<Entity> list = p.getNearbyEntities(range, hight, range);
		list.remove(p);
		for (Entity entity : list) {
			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity = (LivingEntity) entity;
				if (livingEntity instanceof Player) {
					Player player = (Player) entity;
					player.sendMessage(invulMessage);
					player.setNoDamageTicks(20 * invulTime);
					pulled = true;
					API.teleport(entity, teleport);
					p.setNoDamageTicks(20 * invulTime);
				}
			}

		}

		if (pulled) {
			p.sendMessage(invulMessage);
			p.setNoDamageTicks(20 * invulTime);
			API.teleport(p, teleport);
		}

		return pulled;
	}

	public double range;
	public double hight;
	public String invulMessage = "Voce esta invuneravel por 5 segundos";

	public int invulTime = 5;

	public EnderMage() {
		setIcon(Material.PORTAL, "Puxe os seus inimigos");
		add(Material.PORTAL);
		setTime(5);
		new Click(Material.PORTAL, new ClickEffect() {

			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (noAir(e.getClickedBlock())) {
						return;
					}
					e.setCancelled(true);
					BlockState state = e.getClickedBlock().getState();
					state.getBlock().setType(Material.ENDER_PORTAL_FRAME);

					p.setItemInHand(null);

					Game s = new Game(1);
					s.timer(new SimpleEffect() {

						public void effect() {
							int time = 5;

							if (check(p, e.getClickedBlock().getLocation().add(0, 1, 0))) {
								time = 0;
							}
							time--;
							if (time <= 0) {
								p.getInventory().addItem(getItems().get(0));
								state.update(true, true);
								s.stop();
							}
						}
					});

				}

			}

			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub

			}

		}).setType(ClickType.BLOCK_CLICK);
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
