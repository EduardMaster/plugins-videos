package net.eduard.api.tutorial.eduardapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import net.eduard.api.gui.Gui;
import net.eduard.api.gui.Slot;
import net.eduard.api.setup.ItemAPI;

public class CriarGui {

	private static Gui gui;
	public static void criar(Plugin plugin) {
		gui = new Gui(3, "§8Titulo");
		gui.set((Slot) new Slot(ItemAPI.newItem(Material.DIAMOND, "§6Nome"), 5)
				.setClearArmours(true).setClearHotBar(true));
		gui.setItem(new ItemStack(Material.EMERALD));
		gui.register(plugin);

	}
	public static void abrir(Player player) {
		gui.open(player);;
	}

}
