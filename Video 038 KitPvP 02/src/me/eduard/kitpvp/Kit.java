
package me.eduard.kitpvp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@SuppressWarnings("unused")
public class Kit {

	public static void getKit(Player p, KitType kit) {

		switch (kit) {
		case ARCHER:
			item(Material.BOW);
			item(Material.ARROW, 30);
			break;
		case ENDERMAGE:
			break;
		case STOMPER:
			break;
		default:
			break;

		}
		for (ItemStack i : p.getInventory().getContents()) {
			if (i == null) {
				item(Material.MUSHROOM_SOUP, 1, "§6Sopa", new String[] {
					"§eRecupera 6 de Vida!", "§eRecupera 4 de Comida!" });
			}
		}
	}

	private static ItemStack item(Material i) {

		return new ItemStack(i);
	}

	private static ItemStack item(Material i, int qnt) {

		return new ItemStack(i, qnt);
	}

	private static ItemStack item(Material i, int qnt, String n) {

		ItemStack item = new ItemStack(i, qnt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(n);
		item.setItemMeta(meta);
		return item;
	}

	private static ItemStack item(Material i, int qnt, String n, String[] s) {

		ItemStack item = new ItemStack(i, qnt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(n);
		meta.setLore(Arrays.asList(s));
		item.setItemMeta(meta);
		return item;
	}
}
