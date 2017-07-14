
package net.eduard.soup.event;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.config.Config;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickCheck;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.ClickType;
import net.eduard.soup.Main;

public class SoupEvent extends Click {
	
	public SoupEvent() {
		setItem(new ItemStack(Material.MUSHROOM_SOUP));
		setType(ClickType.RIGHT);
		setCheck(ClickCheck.TYPE);
		setClick(new ClickEffect() {
			
			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				Config config = Main.getConfig(p.getWorld());
				if (config.getBoolean("enable")) {
					boolean remove = false;
					e.setCancelled(true);
					int value = config.getInt("soup-recover-value");
					if (p.getHealth() < p.getMaxHealth()) {
						
						double calc = p.getHealth() + value;
						p.setHealth(
							calc >= p.getMaxHealth() ? p.getMaxHealth() : calc);
						remove = true;
					}
					if (!config.getBoolean("no-change-food-level")) {
						if (p.getFoodLevel() < 20) {
							int calc = value + p.getFoodLevel();
							p.setFoodLevel(calc >= 20 ? 20 : calc);
							p.setSaturation(p.getSaturation() + 5);
							remove = true;
						}
					}
					if (remove) {
						e.setUseItemInHand(Result.DENY);
						p.getItemInHand().setType(Material.BOWL);
						if (config.getBoolean("enable-sound")) {
							config.getSound("sound").create(p);
						}
					}
				}
				
			}
			
			@Override
			public void effect(PlayerInteractEntityEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@EventHandler
	public void event(FoodLevelChangeEvent e) {

		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			if (Main.getConfig(p.getWorld()).getBoolean("NoChangeFood")) {
				if (e.getFoodLevel() < 20) {
					e.setFoodLevel(20);
					p.setExhaustion(0);
					p.setSaturation(20);
				}
			}
		}

	}

}
