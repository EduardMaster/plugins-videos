package net.eduard.api.gui;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.dev.Cooldown;
import net.eduard.api.util.KitType;

public class Kit extends Cooldown implements Listener {

	private String name;
	private double price;
	private ItemStack icon;
	private boolean activeCooldownOnPvP;
	private int times = 1;
	private ArrayList<ItemStack> items = new ArrayList<>();
	private HashMap<Player, Integer> timesUsed = new HashMap<>();
	private ArrayList<String> kits = new ArrayList<>();

	
	public Kit() {
		setName(getClass().getSimpleName());
		setPermission(getClass().getSimpleName().toLowerCase());
		API.event(this);
	}
	@EventHandler
	public void event(EntityDamageByEntityEvent e) {
		if (activeCooldownOnPvP) {
			if (e.getEntity() instanceof Player) {
				Player p = (Player) e.getEntity();
				if (hasKit(p)) {
					set(p);
				}
			}
		}
	}

	public boolean cooldown(Player player) {
		if (onCooldown(player)) {
			sendCooldown(player);
			return false;
		}
		int time = 0;
		if (timesUsed.containsKey(player)) {
			time = timesUsed.get(player);
		}
		time++;
		if (time == times) {
			set(player);
			sendUse(player);
			timesUsed.remove(player);
		} else {
			timesUsed.put(player, time);
		}
		return true;
	}

	public Kit setIcon(Material material, String... lore) {
		return setIcon(material, 0, lore);
	}

	public Kit setIcon(Material material, int data, String... lore) {
		icon = new ItemStack(material);
		API.setName(icon, "§6Kit " + name);
		API.setLore(icon, lore);
		API.add(icon, Enchantment.DURABILITY, 10);
		return this;
	}

	public ItemStack add(Material material) {
		return add(material, 0);
	}

	public Kit add(Player player) {
		players.add(player);
		return this;
	}

	public ItemStack add(Material material, int data) {
		return add(new ItemStack(material, 1, (short) data));
	}

	public ItemStack add(ItemStack item) {
		items.add(API.setName(item, "§b" + name));
		return item;
	}

	public Kit add(KitType subKit) {
		kits.add(subKit.name());
		return this;
	}

	public Kit add(String kit) {
		kits.add(kit);
		return this;
	}

	public boolean hasKit(Player player) {
		return players.contains(player);
	}

	private ArrayList<Player> players = new ArrayList<>();

	public void save(Section section, Object value) {
	}

	public Object get(Section section) {
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isActiveCooldownOnPvP() {
		return activeCooldownOnPvP;
	}

	public void setActiveCooldownOnPvP(boolean activeCooldownOnPvP) {
		this.activeCooldownOnPvP = activeCooldownOnPvP;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public ArrayList<ItemStack> getItems() {
		return items;
	}

	public void setItems(ArrayList<ItemStack> items) {
		this.items = items;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<String> getKits() {
		return kits;
	}

}
