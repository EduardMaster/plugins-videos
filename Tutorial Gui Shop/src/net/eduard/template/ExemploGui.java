package net.eduard.template;

import org.bukkit.Material;

import net.eduard.api.setup.GuiAPI.SimpleGui;
import net.eduard.api.setup.ItemAPI;

public class ExemploGui extends SimpleGui{

	public ExemploGui() {
		super("§aTitulo da gui");
		setCommand("exemplo");
		addSlot(ItemAPI.newItem(Material.DIAMOND, "§aTeste")
				, 1, 4);
		
	}
	
	

}
