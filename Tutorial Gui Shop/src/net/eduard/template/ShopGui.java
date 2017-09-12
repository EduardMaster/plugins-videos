package net.eduard.template;

import org.bukkit.Material;

import net.eduard.api.setup.ItemAPI;
import net.eduard.api.setup.GuiAPI.SimpleShopGui;

public class ShopGui extends SimpleShopGui{

	public ShopGui() {
		super("§aAew", 2);
		addProduct(ItemAPI.newItem(Material.DIAMOND, "§aDima"), 1, 5, 1);
		setCommand("cu");
	}

}
