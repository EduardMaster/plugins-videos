package net.eduard.soup.event;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.config.CS;
import net.eduard.api.gui.Click;
import net.eduard.api.gui.ClickCheck;
import net.eduard.api.gui.ClickEffect;
import net.eduard.api.gui.ClickType;
import net.eduard.soup.Main;

public class SoupSignEvent extends Click{
	
	public SoupSignEvent() {
		setType(ClickType.BLOCK_RIGHT);
		setCheck(ClickCheck.ANY_ITEM);
		setClick(new ClickEffect() {
			
			@Override
			public void effect(PlayerInteractEvent e) {
				Player p = e.getPlayer();
				if (e.getClickedBlock().getState() instanceof Sign) {
					Sign sign = (Sign) e.getClickedBlock().getState();
					if (sign.getLine(0).equalsIgnoreCase(
							CS.toText(Main.config.getMessages("sign").get(0)))) {
						Inventory inv = API
								.newInventory(Main.config.message("sign-name"), 6*9);

						for (ItemStack item : inv) {
							if (item == null) {
								inv.addItem(Main.soup);
							}
						}
						p.openInventory(inv);
					}
				}
				
			}
			
			@Override
			public void effect(PlayerInteractEntityEvent e) {
				
			}
		});
	}
	@EventHandler
	public void event(SignChangeEvent e) {

		Player p = e.getPlayer();
		if (e.getLine(0).toLowerCase().contains("soup")) {
			int id = 0;
			for (String text : Main.config.getMessages("sign")) {
				e.setLine(id, CS.toText(text));
				id++;
			}
			p.sendMessage(Main.config.message("create-sign"));
		}
	}
}
