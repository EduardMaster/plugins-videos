package net.eduard.api.kits;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import net.eduard.api.API;
import net.eduard.api.game.Explosion;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.ClickType;
import net.eduard.api.gui.Kit;

public class HotPotato extends Kit {

	public int effectSeconds = 5;
	public HotPotato() {
		setIcon(Material.BAKED_POTATO, "");
		add(new ItemStack(Material.BAKED_POTATO));
		setTime(30);
		setExplosion( new Explosion(6, true, false));
		setMessage("§6");
		setClick(new Click(Material.BAKED_POTATO, new ClickEffect() {

			@Override
			public void effect(PlayerInteractEntityEvent e) {
				Player p = e.getPlayer();
				if (hasKit(p)) {
					if (e.getRightClicked() instanceof Player) {
						Player target = (Player) e.getRightClicked();
						PlayerInventory inv = target.getInventory();
						inv.setHelmet(new ItemStack(Material.TNT));
						sendMessage(target);
						API.TIME.delay( effectSeconds,new Runnable() {

							@Override
							public void run() {
								if (inv.getHelmet() != null) {
									if (inv.getHelmet()
											.getType() == Material.TNT) {
										makeExplosion(target);
									}
								}

							}
						});
					}
				}
			}

			@Override
			public void effect(PlayerInteractEvent e) {

			}
		}));
		getClick().setType(ClickType.ENTITY);

	}
	@Override
	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Arrow) {
			Arrow arrow = (Arrow) e.getDamager();
			if (arrow.getShooter() instanceof Player) {
				Player p = (Player) arrow.getShooter();
				if (hasKit(p)) {
					p.getInventory().addItem(new ItemStack(Material.ARROW));
				}

			}

		}
	}

}
