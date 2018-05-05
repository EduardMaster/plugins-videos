
package me.eduard.kitpvp;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("unused")
public class Kit {

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
