package net.eduard.kit;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.eduard.api.API;
import net.eduard.api.config.Section;
import net.eduard.api.util.Save;

public class Kit implements Save{
	private static HashMap<String, Kit> kits = new HashMap<>();
	public static boolean exists(String name) {
		return kits.containsKey(name.toLowerCase());
	}
	public static boolean hasKits() {
		return !kits.isEmpty();
	}
	private String name;
	private long cooldown;
	private ItemStack icon;
	private ArrayList<ItemStack> items = new ArrayList<>();
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack boots;
	private ItemStack leggins;
	private boolean clearInventory;
	private boolean fillSoup;
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
	public ArrayList<ItemStack> getItems() {
		return items;
	}
	public void setItems(ArrayList<ItemStack> items) {
		this.items = items;
	}
	public ItemStack getHelmet() {
		return helmet;
	}
	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}
	public ItemStack getChestplate() {
		return chestplate;
	}
	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}
	public ItemStack getBoots() {
		return boots;
	}
	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}
	public ItemStack getLeggins() {
		return leggins;
	}
	public void setLeggins(ItemStack leggins) {
		this.leggins = leggins;
	}
	public boolean isClearInventory() {
		return clearInventory;
	}
	public void setClearInventory(boolean clearInventory) {
		this.clearInventory = clearInventory;
	}
	public boolean isFillSoup() {
		return fillSoup;
	}
	public void setFillSoup(boolean fillSoup) {
		this.fillSoup = fillSoup;
	}
	public void save(Section section, Object value) {
		// TODO Auto-generated method stub
		
	}
	public Object get(Section section) {
		// TODO Auto-generated method stub
		return null;
	}
	public long getCooldown() {
		return cooldown;
	}
	public void setCooldown(long cooldown) {
		this.cooldown = cooldown;
	}
	public static String getKits() {
		return API.toText(kits.keySet());
	}
	public static void set(Player p, String name, int cooldown) {
		
	}
	
}
