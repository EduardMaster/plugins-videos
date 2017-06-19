package net.eduard.api.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.eduard.api.API;
import net.eduard.api.gui.Slot;
import net.eduard.api.util.Cs;
/**
 * Item , Inventory
 */
public final class ItemAPI {
	public static ItemStack getRandomItem(ItemStack... items) {

		return items[API.RANDOM.nextInt(items.length - 1)];
	}

	public static ItemStack getRandomItem(List<ItemStack> items) {

		return items.get(API.RANDOM.nextInt(items.size() - 1));
	}
	public static void clearArmours(LivingEntity entity) {
		entity.getEquipment().setArmorContents(null);
	}

	public static void clearHotBar(Player p) {
		for (int i = 0; i < 9; i++) {
			p.getInventory().setItem(i, null);
		}
	}

	public static void clearInventory(Player player) {
		clearItens(player);
		clearArmours(player);
	}

	public static void clearItens(LivingEntity entity) {
		entity.getEquipment().clear();

	}

	@SuppressWarnings("deprecation")
	public static void getItems(Player player) {
		if (API.INV_ITEMS.containsKey(player)) {
			player.getInventory().setContents(API.INV_ITEMS.get(player));
			player.updateInventory();
		}
		getArmours(player);

	}
	@SuppressWarnings("deprecation")
	public static void getArmours(Player player) {
		if (API.INV_ARMOURS.containsKey(player)) {
			player.getInventory().setArmorContents(API.INV_ARMOURS.get(player));
			player.updateInventory();
		}
	}

	public static int getItemsAmount(Inventory inventory) {
		int amount = 0;
		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				amount++;
			}
		}

		return amount;
	}

	public static void setHotBar(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		for (int i = 0; i < 8; i++) {
			inv.setItem(i, item);
		}
	}
	public static ItemStack setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public static int getTotalAmount(Inventory inventory) {
		int amount = 0;
		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				amount += item.getAmount();
			}
		}
		return amount;
	}
	public static int getTotalAmount(Inventory inventory, Material item) {
		int amount = 0;
		for (ItemStack id : inventory.all(item).values()) {
			amount += id.getAmount();
		}
		return amount;
	}
	public static int getTotalAmount(Inventory inventory, ItemStack item) {
		int amount = 0;

		for (ItemStack id : inventory.all(item.getType()).values()) {
			if (id.isSimilar(item)) {
				amount += id.getAmount();
			}
		}
		return amount;
	}
	public static void remove(Inventory inventory, ItemStack item) {
		for (Entry<Integer, ? extends ItemStack> map : inventory
				.all(item.getType()).entrySet()) {
			if (map.getValue().isSimilar(item)) {
				inventory.clear(map.getKey());
			}
		}
	}
	public static ItemStack addEnchant(ItemStack item, Enchantment type, int level) {
		item.addUnsafeEnchantment(type, level);
		return item;
	}

	public static void addHotBar(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		if (item == null)
			return;
		if (item.getType() == Material.AIR)
			return;
		int i;
		while ((i = inv.firstEmpty()) < 9) {
			inv.setItem(i, item);
		}
	}
	public static void setEquip(LivingEntity entity, Color color, String name) {
		EntityEquipment inv = entity.getEquipment();
		inv.setBoots(setName(
				setColor(new ItemStack(Material.LEATHER_BOOTS), color), name));
		inv.setHelmet(setName(
				setColor(new ItemStack(Material.LEATHER_HELMET), color), name));
		inv.setChestplate(setName(
				setColor(new ItemStack(Material.LEATHER_CHESTPLATE), color),
				name));
		inv.setLeggings(setName(
				setColor(new ItemStack(Material.LEATHER_LEGGINGS), color),
				name));
	}
	public static void give(Collection<ItemStack> items, Inventory inv) {
		for (ItemStack item : items) {
			inv.addItem(item);
		}
	}
	public static List<String> getLore(ItemStack item) {
		if (item != null) {
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
				return item.getItemMeta().getLore();
			}
		}
		return new ArrayList<String>();
	}
	public static void setSlot(Inventory inventory, Slot slot) {
		inventory.setItem(slot.getSlot(), slot.getItem());
	}
	public static boolean isFull(Inventory inventory) {
		return inventory.firstEmpty() == -1;
	}
	public static boolean isUsing(LivingEntity entity, Material material) {
		return (getHandType(entity) == material);
	}
	public static boolean isUsing(LivingEntity entity, String material) {
		return (Cs.contains(getHandType(entity).name(), (material)));
	}
	public static void drop(Entity entity, ItemStack item) {
		drop(entity.getLocation(), item);
	}
	public static Material getHandType(LivingEntity entity) {
		EntityEquipment inv = entity.getEquipment();
		if (inv == null) {
			return Material.AIR;
		}
		ItemStack item = inv.getItemInHand();
		if (item == null) {
			return Material.AIR;
		}

		return item.getType();
	}

	public static ItemStack getHead(String name) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwner(name);
		item.setItemMeta(meta);
		return item;
	}
	public static boolean isEmpty(Inventory inventory) {

		for (ItemStack item : inventory.getContents()) {
			if (item != null) {
				return false;
			}

		}
		return true;
	}
	public static ItemStack setColor(ItemStack item, Color color) {
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(color);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack setLore(ItemStack item, String... lore) {

		ItemMeta meta = item.getItemMeta();
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack resetName(ItemStack item) {
		setName(item, "");
		return item;
	}
	public static String getName(ItemStack item) {

		return item.hasItemMeta()
				? item.getItemMeta().hasDisplayName()
						? item.getItemMeta().getDisplayName()
						: ""
				: "";
	}
	public static void drop(Location location, ItemStack item) {
		location.getWorld().dropItemNaturally(location, item);
	}
	public static void fill(Inventory inv, ItemStack item) {
		int id;
		while ((id = inv.firstEmpty()) != -1) {
			inv.setItem(id, item);
		}
	}
	public static double getDamage(ItemStack item) {
		if (item == null)
			return 0;
		double damage = 0;
		String name = item.getType().name();
		for (int id = 0; id <= 4; id++) {
			String value = "";
			if (id == 0) {
				value = "DIAMOND_";
				damage += 3;
			}
			if (id == 1) {
				value = "IRON_";
				damage += 2;
			}
			if (id == 2) {
				value = "GOLD_";
			}
			if (id == 3) {
				value = "STONE_";
				damage++;
			}
			if (id == 4) {
				value = "WOOD_";
			}

			for (int x = 0; x <= 3; x++) {
				double newDamage = damage;
				if (x == 0) {
					value = "SWORD";
					newDamage += 4;
				}
				if (x == 1) {
					value = "AXE";
					newDamage += 3;
				}
				if (x == 2) {
					value = "PICKAXE";
					newDamage += 2;
				}
				if (x == 3) {
					value = "SPADE";
					newDamage++;
				}

				if (name.equals(value)) {
					return newDamage;
				}
			}
			damage = 0;
		}
		return damage;
	}
	public static void saveItems(Player player) {
		saveArmours(player);
		API.INV_ITEMS.put(player, player.getInventory().getContents());
	}
	public static void saveArmours(Player player) {
		API.INV_ARMOURS.put(player, player.getInventory().getArmorContents());
	}
	
}
