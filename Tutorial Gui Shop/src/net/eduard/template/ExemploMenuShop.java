package net.eduard.template;

import org.bukkit.Material;

import net.eduard.api.setup.ItemAPI;
import net.eduard.api.setup.GuiAPI.SimpleShopGui;

public class ExemploMenuShop extends SimpleShopGui{

	public ExemploMenuShop() {
		super("§8Loginha", 6);
		
		setCommand("loginha");
		addProduct(ItemAPI.newItem(Material.GOLDEN_APPLE, "§aMaça",1,1), 6, 2, 500);
		
	}

}
