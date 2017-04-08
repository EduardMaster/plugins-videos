package net.eduard.api.kits;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.click.Click;
import net.eduard.api.click.ClickEffect;
import net.eduard.api.click.ClickType;
import net.eduard.api.gui.Kit;

public class Monk extends Kit {

	public Monk() {
		setIcon(Material.BLAZE_ROD, "§fBagunce o inventario do Inimigo");
		add(Material.BLAZE_ROD);
		setTime(15);
		setClick(new Click(Material.BLAZE_ROD, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (e.getRightClicked() instanceof Player) {
						Player target = (Player) e.getRightClicked();
						if (cooldown(p)) {
							PlayerInventory inv = target.getInventory();
							ItemStack item = target.getItemInHand();
							int value = new Random().nextInt(36);
							ItemStack replaced = inv.getItem(value);
							inv.setItemInHand(replaced);
							inv.setItem(value, item);
						}
					}

				}
			}

			@Override
			public void effect(PlayerInteractEvent e) {

			}
		}));
		getClick().setType(ClickType.ENTITY);
	}
}
