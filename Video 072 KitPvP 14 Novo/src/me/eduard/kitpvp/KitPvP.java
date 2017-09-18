
package me.eduard.kitpvp;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

@SuppressWarnings("unused")
public class KitPvP {

	private static HashMap<String, KitType> kitpvp = new HashMap<>();

	private static void add(Inventory inv, ItemStack i) {

		inv.addItem(i);
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

	public void getKit(Player p, KitType kit) {

		PlayerInventory inv = p.getInventory();
		inv.clear();
		switch (kit) {
		case ARCHER:
			add(inv, item(Material.BOW));
			add(inv, item(Material.ARROW, 30));
			break;
		case ENDERMAGE:
			break;
		case STOMPER:
			break;
		case VIPER:
			break;
		default:
			break;

		}
		add(inv, item(Material.STONE));
		for (ItemStack i : inv.getContents()) {
			if (i == null) {
				add(inv, item(Material.MUSHROOM_SOUP, 1, "§6Sopa", new String[] {
					"§eRecupera 6 de Vida!", "§eRecupera 4 de Comida!" }));
			}
		}
	}

	public boolean hasKit(Player p) {

		return kitpvp.containsKey(p.getName());

	}

	public boolean hasKit(Player p, KitType kit) {

		if (kitpvp.containsKey(p.getName())) {
			for (Entry<String, KitType> i : kitpvp.entrySet()) {
				if (i.getKey() == p.getName() && i.getValue() == kit) {
					return true;
				}
			}
		}
		return false;
	}

	public void setKit(Player p, KitType kit) {

		kitpvp.put(p.getName(), kit);
	}
}
