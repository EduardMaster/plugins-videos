package net.eduard.api.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.manager.EffectManager;
import net.eduard.api.setup.GuiAPI.SimpleClick;
import net.eduard.api.setup.GuiAPI.SimpleGui;

public class MyGUI extends SimpleGui{

	private Map<Integer, EffectManager> effects = new HashMap<>();
	
	public MyGUI(String name, int lines) {
		super(name, lines);
	}

	@Override
	public void resetInventories() {
		super.resetInventories();
		for (Entry<Integer, EffectManager> entry : effects.entrySet()) {
			getClicks()[entry.getKey()] = new SimpleClick() {
				
				@Override
				public void onClick(InventoryClickEvent event, int page) {
					// TODO Auto-generated method stub
					if (event.getWhoClicked() instanceof Player) {
						Player p = (Player) event.getWhoClicked();
						entry.getValue().effect(p);	
					}
					
				}
			};
		}
	}
	public void addSlot(ItemStack item, int page, int slot, EffectManager effect) {
		// TODO Auto-generated method stub
		addSlot(item, page, slot, new SimpleClick() {
			
			@Override
			public void onClick(InventoryClickEvent event, int page) {
				// TODO Auto-generated method stub
				if (event.getWhoClicked() instanceof Player) {
					Player p = (Player) event.getWhoClicked();
					effect.effect(p);	
				}
			}
		});
	}
	

}