package net.eduard.template;

import net.eduard.api.setup.GuiAPI.SimpleClick;
import net.eduard.api.setup.GuiAPI.SimpleGui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.setup.ItemAPI;

public class ExemploMenuGUi extends SimpleGui{

	public ExemploMenuGUi() {
		super("§8Nome");
		
		
		setClick(new SimpleClick() {
			
			@Override
			public void onClick(InventoryClickEvent event, int page) {
				
				
//				event.setCancelled(false);
				if (event.getRawSlot() == 3) {
					Player p = (Player)event.getWhoClicked();
					abrirGui(p);
					
				}
				
				
				
				
			}
		});
		
		setCommand("abrirgui");
		setItem(new ItemStack(Material.CHEST));
		
//		setNextPage(nextPage);
//		setPreviosPage(previosPage);
		setNextPageSlot(2);
		setPreviousPageSlot(7);
		resetInventories();
		
		addSlot(ItemAPI.newItem(Material.GOLD_INGOT	, "§aGold"), 3, 6);
		
	}
	public static void abrirGui(Player player) {
		
	}

}
